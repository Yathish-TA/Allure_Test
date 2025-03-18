package database;

import apicoreutils.LoadExtractData;
import apicoreutils.LoadProp;
import org.testng.Assert;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Base64;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static azurevault.VaultData.getVaultData;

public class DBC {
    Connection dbConnection = null;

    private static Properties loadDBConfig(String fileName) throws IOException {
        Properties properties = new Properties();
        try (FileInputStream reader = new
                FileInputStream("src/test/resources/" + fileName)) {
            properties.load(reader);
            reader.close();
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static String decrypt(String encryptedText, String keyBase64) throws Exception {
        // Remove backslashes from encryptedText and keyBase64 strings
        encryptedText = encryptedText.replace("\\", "");
        keyBase64 = keyBase64.replace("\\", "");

        byte[] decodedKey = Base64.getDecoder().decode(keyBase64);
        SecretKeySpec keySpec = new SecretKeySpec(decodedKey, "AES");
        Cipher cipher = Cipher.getInstance(getVaultData("cipher-key"));
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decodedText = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(decodedText);
        return new String(decryptedBytes);
    }

    public static String getValueFromDatabase(String sqlQuery) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Properties dbConfig = loadDBConfig("DBConfig.properties");

            String user = dbConfig.getProperty("DBUserName");
            String password = getVaultData(dbConfig.getProperty("DBPassword")); //Password must be set in vault
            String url = dbConfig.getProperty("DBConnectionURL");
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);

            if (resultSet.next()) {
                String value = resultSet.getString(1);
                System.out.println("Value retrieved from database: " + value);
                return value;
            } else {
                System.out.println("No results found in the database for query: " + sqlQuery);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
                System.out.println("MySQL connection is closed.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getValueFromMultipleDB(String sqlQuery, String dbType) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Properties dbConfig = loadDBConfig("DBConfig.properties");
            // Get DB-specific properties
            String url = dbConfig.getProperty(dbType + ".url");
            String user = dbConfig.getProperty(dbType + ".username");
            String password = getVaultData(dbConfig.getProperty(dbType + ".password")); //Password must be set in vault
            String driverClassName = dbConfig.getProperty(dbType + ".driverClassName");

            // Load the driver class
            Class.forName(driverClassName);

            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);

            if (resultSet.next()) {
                String value = resultSet.getString(1);
                System.out.println("Value retrieved from database: " + value);
                return value;
            } else {
                System.out.println("No results found in the database for query: " + sqlQuery);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
                System.out.println("MySQL connection is closed.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    // Method to replace placeholders with values from a .properties file
    public String replacePlaceholdersWithValues(String queryTemplate) {

        if (queryTemplate == null || queryTemplate.isEmpty()) {
            throw new IllegalArgumentException("Query template cannot be null or empty.");
        }
        System.out.println("Before updation Query :"+queryTemplate);

        // Regex to match placeholders like {{key}}
        Pattern pattern = Pattern.compile("\\{\\{(.*?)\\}\\}");
        Matcher matcher = pattern.matcher(queryTemplate);

        StringBuffer updatedQuery = new StringBuffer();

        // Find and replace placeholders
        while (matcher.find()) {
            String placeholderKey = matcher.group(1); // Extract key inside {{ }}
            String value = LoadExtractData.prop.getProperty(placeholderKey);

            if (value == null) {
                throw new IllegalArgumentException("No value found for placeholder key: " + placeholderKey);
            }
            // Replace placeholder with the value
            matcher.appendReplacement(updatedQuery, value);
        }

        // Append the remaining part of the query
        matcher.appendTail(updatedQuery);
        System.out.println("Updated Final Query :"+updatedQuery);

        return updatedQuery.toString();
    }


    public static Connection establishDBConnection(String url, String username, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        if (connection == null) {
            throw new SQLException("Failed to create a database connection.");
        }
        return connection;
    }

    public void setDbConnection(String app_db) {
        String env = System.getProperty("env"); // e.g., -Denv=WCS or -Denv=OMS
        String key = app_db + "." + env;
        try {
            switch (key) {
                case "WCS.UAT":
                    dbConnection = establishDBConnection(LoadProp.prop.getProperty(key+".url"), LoadProp.prop.getProperty(key+".username"), getVaultData(LoadProp.prop.getProperty(key+".password")));
                    System.out.println(key + " - Database connection established successfully.");
                    break;
                case "WCS.QA2":
                    dbConnection = establishDBConnection(LoadProp.prop.getProperty(key+".url"), LoadProp.prop.getProperty(key+".username"), getVaultData(LoadProp.prop.getProperty(key+".password")));
                    System.out.println(key + " - Database connection established successfully.");
                    break;
                default:
                    throw new IllegalStateException("Unexpected environment value: " + key);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Failed to establish DB connection: " + e.getMessage());
        }
    }

    public String executeTheQuery(String finalQuery) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            // Ensure DB connection is not null
            if (dbConnection == null) {
                throw new IllegalStateException("DB Connection is not established. Call 'Establish the DB connection' first.");
            }

            // Execute the query
            statement = dbConnection.createStatement();
            resultSet = statement.executeQuery(finalQuery);

            // Fetch the result (assuming single-row, single-column result for simplicity)
            String value = null;
            if (resultSet.next()) {
                value = resultSet.getString(1);
                return value;
            } else {
                System.out.println("No results found in the database for query: " + finalQuery);
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Failed to validate DB results: " + e.getMessage());
            return null;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (dbConnection != null) dbConnection.close();
                System.out.println("App_DB connection is closed.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void validateTheDbResults(String finalQuery, String input) {
        try {
            // Execute the query and get the result
            String queryResult = executeTheQuery(finalQuery);
            System.out.println("Query output = " + queryResult);
            // Get the expected value from the properties file
            String expectedValue = LoadExtractData.prop.getProperty(input);
            if (expectedValue == null) {
                throw new IllegalArgumentException("Expected value not found for input key: " + input);
            }
            System.out.println("Extracted Output: " + expectedValue);

            // Validate the result
            Assert.assertEquals(queryResult, expectedValue, "DB validation failed!");
            System.out.println("DB validation passed: Expected = " + expectedValue + ", Actual = " + queryResult);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred during DB validation: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("DB validation encountered an unexpected error.", e);
        }
    }

}