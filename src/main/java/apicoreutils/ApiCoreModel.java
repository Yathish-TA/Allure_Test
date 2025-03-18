package apicoreutils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.testng.Assert;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

import static azurevault.VaultData.getVaultData;
import static database.DBC.getValueFromMultipleDB;
import static io.restassured.RestAssured.config;


public class ApiCoreModel {
    private static final Logger logger = Logger.getLogger(ApiCoreModel.class);

    /**
     * Constructs the complete URL for an API call by combining the base URI and the endpoint.
     * It also appends additional parts to the endpoint from the dataDictionary keys that start with "EndPoint-".
     *
     * @param dataDictionary A map containing the base URI and endpoint details.
     * @return The complete URL as a String.
     */
    public String getCompleteUrl(Map<String, Object> dataDictionary) {
        String BaseURI;
        String ConstBase = (String) dataDictionary.get("BASE_URI");

        if (ConstBase.contains("http")) {
            BaseURI = ConstBase;
        } else {
            logger.debug(ConstBase + "." + System.getProperty("env"));
            BaseURI = LoadProp.prop.getProperty(ConstBase + "." + System.getProperty("env", "QA"));
        }
        logger.debug("BaseURI = " + BaseURI);

        String endpoint = (String) dataDictionary.getOrDefault("EndPoint", "");
        // Track replaced keys to avoid appending them again
        Set<String> replacedKeys = new HashSet<>();

        // Replace placeholders in the endpoint, e.g., $ID$, $NAME$, etc.
        for (Map.Entry<String, Object> entry : dataDictionary.entrySet()) {
            if (entry.getKey().startsWith("EndPoint-")) {
                String placeholder = "$" + entry.getKey().substring("EndPoint-".length()) + "$";
                if (endpoint.contains(placeholder)) {
                    if(entry.getValue().toString().toLowerCase().startsWith("attr-")){
                        String attribute = entry.getValue().toString().substring(5); // Remove "Attr-" prefix
                        String env = System.getProperty("env", "QA");
                        if (LoadProp.prop.containsKey(attribute + "." + env)) {
                            endpoint = endpoint.replace(placeholder, LoadProp.prop.getProperty(attribute + "." + env));
                            replacedKeys.add(entry.getKey());
                        }else {
                            System.err.println("Property key '" + attribute + "' not found in the properties file.");
                        }
                    }
                    endpoint = endpoint.replace(placeholder, entry.getValue().toString());
                    replacedKeys.add(entry.getKey());
                }
            }
        }

        StringBuilder completeEndpoint = new StringBuilder(endpoint);

        // Append additional EndPoint-* keys only if they were not placeholders
        for (Map.Entry<String, Object> entry : dataDictionary.entrySet()) {
            if (entry.getKey().startsWith("EndPoint-") && !replacedKeys.contains(entry.getKey())) {
                completeEndpoint.append("/").append(entry.getValue());
            }
        }

        return BaseURI + completeEndpoint.toString();
    }


    /**
     * Extracts headers from the dataDictionary where keys start with "Headers-" and constructs a header map.
     * Handles different authentication types (No Auth, Basic Auth, Bearer Token, OAuth 2.0).
     *
     * @param dataDictionary A map containing headers with keys starting with "Headers-".
     * @return A map containing the headers, including authentication details if applicable.
     */
    public Map<String, String> getHeader(Map<String, Object> dataDictionary) {
        Map<String, String> extractedHeaders = new HashMap<>();
        String authType = (String) dataDictionary.getOrDefault("Headers-AuthType", "NoAuth");

        // Extract common headers
        for (Map.Entry<String, Object> entry : dataDictionary.entrySet()) {
            if (entry.getKey().startsWith("Headers-") && !entry.getKey().equals("Headers-AuthType")) {
                String headerKey = entry.getKey().split("-", 2)[1];
                extractedHeaders.put(headerKey, (String) entry.getValue());
            }
        }

        // Handle authentication-specific headers
        switch (authType) {
            case "NoAuth":
                // No additional headers needed
                break;
            case "Basic":
                String username = (String) dataDictionary.get("Headers-Username");
                String password = (String) dataDictionary.get("Headers-Password");
                if (username != null && password != null) {
                    String basicAuth = Base64.getEncoder().encodeToString((getVaultData(username) + ":" + getVaultData(password)).getBytes());
                    extractedHeaders.put("Authorization", "Basic " + basicAuth);
                }
                break;
            case "Bearer":
                String token = (String) dataDictionary.get("Headers-Token");
                if (token != null) {
                    extractedHeaders.put("Authorization", "Bearer " + token);
                }
                break;
            case "OAuth2":
                String accessToken = (String) dataDictionary.get("Headers-AccessToken");
                if (accessToken != null) {
                    extractedHeaders.put("Authorization", "Bearer " + accessToken);
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported AuthType: " + authType);
        }

        return extractedHeaders;
    }


    /**
     * Extracts expected output values from the dataDictionary where keys start with "Expected-" and constructs a map of these expected values.
     *
     * @param dataDictionary A map containing expected output keys and values.
     * @return A map containing the expected outputs.
     */
    public Map<String, String> getExpectedOutput(Map<String, Object> dataDictionary) {
        Map<String, String> extractedExpectedResult = new HashMap<>();

        // Iterate over the entries of the original map
        for (Map.Entry<String, Object> entry : dataDictionary.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            // Check if the key starts with "Expected-" and if the value is a String
            if (key.startsWith("Expected-")) {
                // Extract the part after "Expected-"
                String expectedKey = key.split("-", 2)[1];
                // Add the extracted key and value to the new map
                extractedExpectedResult.put(expectedKey, String.valueOf(value));
            }
        }

        // Return the extracted expected result map
        return extractedExpectedResult;
    }

    /**
     * Extracts SQLRequest input values from the dataDictionary where keys start with "SQLRequest-" and constructs a map of these expected values.
     *
     * @param dataDictionary A map containing expected output keys and values.
     * @return A map containing the expected outputs.
     */
    public Map<String, String> getSQLQueryInput(Map<String, Object> dataDictionary) {
        Map<String, String> sqlQueryInputs = new HashMap<>();

        // Iterate over the entries of the original map
        for (Map.Entry<String, Object> entry : dataDictionary.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            // Check if the key starts with "Expected-" and if the value is a String
            if (key.startsWith("SQLRequest-")) {
                // Extract the part after "Expected-"
                String expectedKey = key.split("-", 2)[1];
                // Add the extracted key and value to the new map
                sqlQueryInputs.put(expectedKey, String.valueOf(value));
            }
        }

        // Return the extracted expected result map
        return sqlQueryInputs;
    }

    /**
     * Extracts a value from a JSON object based on a given JSON path.
     *
     * @param data The JSON object as a String.
     * @param path The JSON path expression to extract the value.
     * @return The extracted value as an Object.
     */

    public Object getValueFromPath(Object data, String path) {
        return JsonPath.read(data, path);
    }


    /**
     * Constructs a JSON object using a template file specified by `JsonName` in the `dataDictionary` or directly from keys in the `dataDictionary` starting with `Request-`.
     * It replaces placeholders in the template with values from the dictionary.
     *
     * @param dataDictionary A map containing key-value pairs for constructing the JSON object.
     * @return The constructed JSON object.
     * @throws IOException If an I/O error occurs while reading the JSON template file.
     */
    public JSONObject returnUpdatedJson(Map<String, Object> dataDictionary) throws IOException {
        JSONObject jsonTemplate = new JSONObject();

        // Check if "JsonName" key exists in dataDictionary
        if (dataDictionary.containsKey("JsonName")) {
            String jsonFilePath = "src/test/java/api/resources/json/" + dataDictionary.get("JsonName");
            File file = new File(jsonFilePath);

            if (file.exists()) {
                // Read JSON template from the file
                try (FileReader reader = new FileReader(file)) {
                    char[] buffer = new char[(int) file.length()];
                    reader.read(buffer);
                    jsonTemplate = new JSONObject(new String(buffer));
                }
            } else {
                logger.info("JSON file not found. Constructing JSON from keys starting with 'Request-'.");
            }
        }

        // Recursively replace placeholders in the template
        replacePlaceholders(jsonTemplate, dataDictionary);

        // Add additional request data to the JSON
        for (Map.Entry<String, Object> entry : dataDictionary.entrySet()) {
            if (entry.getKey().startsWith("Request-")) {
                String jsonKey = entry.getKey().substring("Request-".length());
                if (!jsonTemplate.has(jsonKey)) {
                    jsonTemplate.put(jsonKey, entry.getValue());
                }
            }
        }

        // Log the constructed JSON
        logger.info("Constructed JSON: " + jsonTemplate);

        return jsonTemplate;
    }


    /**
     * Recursively replaces placeholders in a JSON object with values from the `dataDictionary`.
     *
     * @param json           The JSON object containing placeholders.
     * @param dataDictionary A map containing key-value pairs to replace placeholders in the JSON object.
     */
    private void replacePlaceholders(JSONObject json, Map<String, Object> dataDictionary) {
        Iterator<String> keys = json.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = json.get(key);

            if (value instanceof JSONObject) {
                replacePlaceholders((JSONObject) value, dataDictionary);
            } else if (value instanceof String) {
                String valueStr = (String) value;
                if (valueStr.startsWith("$") && valueStr.endsWith("$")) {
                    String placeholderKey = valueStr.substring(1, valueStr.length() - 1);
                    if (dataDictionary.containsKey("Request-" + placeholderKey)) {
                        json.put(key, dataDictionary.get("Request-" + placeholderKey));
                    }
                }
            }
        }
    }

    private void replacePlaceholdersInArray(JSONArray jsonArray, Map<String, Object> dataDictionary) {
        for (int i = 0; i < jsonArray.length(); i++) {
            Object item = jsonArray.get(i);

            if (item instanceof JSONObject) {
                // Recursively handle JSON objects inside the array
                replaceJSONPlaceholders((JSONObject) item, dataDictionary);
            } else if (item instanceof String) {
                String valueStr = (String) item;

                // Check if it's a placeholder
                if (valueStr.startsWith("$") && valueStr.endsWith("$")) {
                    String placeholderKey = valueStr.substring(1, valueStr.length() - 1);
                    // Implement the Logic
                }
            }
        }
    }

    private void replaceJSONPlaceholders(JSONObject json, Map<String, Object> dataDictionary) {
        Iterator<String> keys = json.keys();

        while (keys.hasNext()) {
            String key = keys.next();
            Object value = json.get(key);

            if (value instanceof JSONObject) {
                // Recursive call for nested JSON objects
                replaceJSONPlaceholders((JSONObject) value, dataDictionary);
            } else if (value instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) value;
                for (int i = 0; i < jsonArray.length(); i++) {
                    Object item = jsonArray.get(i);
                    if (item instanceof JSONObject) {
                        // Recursively handle JSON objects inside the array
                        replaceJSONPlaceholders((JSONObject) item, dataDictionary);
                    }
                }
            } else if (value instanceof String) {
                String valueStr = (String) value;

                // Check if it's a placeholder
                if (valueStr.startsWith("$") && valueStr.endsWith("$")) {
                    String placeholderKey = valueStr.substring(1, valueStr.length() - 1);

                    if (dataDictionary.containsKey("Request-" + placeholderKey)) {
                        String replacementValue = dataDictionary.get("Request-" + placeholderKey).toString();

                        // Check for the "Grab-" prefix
                        /*if (replacementValue instanceof String && ((String) replacementValue).startsWith("Grab-")) {
                            String propertyKey = ((String) replacementValue).substring(5); // Extract key after "Grab-"
                            String env = System.getProperty("env", "QA");
                            if (LoadProp.prop.containsKey(propertyKey + "." + env)) {
                                json.put(key, LoadProp.prop.getProperty(propertyKey + "." + env));
                            } else {
                                System.err.println("Property key '" + propertyKey + "' not found in the properties file.");
                            }
                        }*/

                        if (replacementValue.toLowerCase().startsWith("vault-")) {
                            String secretKey = replacementValue.substring(6); // Remove "Vault-" prefix
                            //Make vaultKey is dynamic based on env
                            secretKey = secretKey + "-" + System.getProperty("env");
                            String vaultValue = getVaultData(secretKey);
                            json.put(key, vaultValue);
                        } else if (replacementValue.toLowerCase().startsWith("attr-")) {
                            String attribute = replacementValue.substring(5); // Remove "Attr-" prefix
                            String env = System.getProperty("env", "QA");
                            if (LoadProp.prop.containsKey(attribute + "." + env)) {
                                json.put(key, LoadProp.prop.getProperty(attribute + "." + env));
                            } else if (LoadProp.prop.containsKey(attribute)) {
                                json.put(key, LoadProp.prop.getProperty(attribute));
                            } else {
                                System.err.println("Property key '" + attribute + "' not found in the properties file.");
                            }
                        } else {
                            json.put(key, replacementValue);
                        }
                    }
                }
            }
        }
    }


    public Object constructRequestJSONBody(Map<String, Object> dataDictionary) throws IOException {
        String bodyType = (String) dataDictionary.getOrDefault("BodyFormat", "nodata");
        Object jsonTemplate = new JSONObject();

        switch (bodyType.toLowerCase()) {
            case "raw-json":
                jsonTemplate = handleRawBody(dataDictionary);
                break;

            case "form-data":
                return handleFormData(dataDictionary);

            case "x-www-form-urlencoded":
                return handleUrlEncoded(dataDictionary);

            case "nodata":
                return null;  // No body needed for GET/DELETE

            default:
                throw new IllegalArgumentException("Unsupported BodyType: " + bodyType);
        }
        return jsonTemplate;
    }

    /**
     * Handles raw JSON body and JSON array body.
     */
    private Object handleRawBody(Map<String, Object> dataDictionary) throws IOException {
        Object jsonTemplate = null;

        // Check if a JSON file is provided in the data dictionary
        if (dataDictionary.containsKey("JsonName")) {
            String jsonFilePath = "src/test/java/api/resources/json/" + dataDictionary.get("JsonName");
            File file = new File(jsonFilePath);
            if (file.exists()) {
                try (FileReader reader = new FileReader(file)) {
                    char[] buffer = new char[(int) file.length()];
                    reader.read(buffer);
                    String fileContent = new String(buffer);

                    // Parse as JSON Object or JSON Array
                    if (fileContent.trim().startsWith("{")) {
                        jsonTemplate = new JSONObject(fileContent);
                    } else if (fileContent.trim().startsWith("[")) {
                        jsonTemplate = new JSONArray(fileContent);
                    }
                }
            } else {
                logger.info("JSON file not found. Using inline raw data.");
            }
        }

        // Check if inline raw JSON data is provided
        if (dataDictionary.containsKey("AddRawData")) {
            String rawData = (String) dataDictionary.get("AddRawData");
            if (rawData.trim().startsWith("{")) {
                jsonTemplate = new JSONObject(rawData);
            } else if (rawData.trim().startsWith("[")) {
                jsonTemplate = new JSONArray(rawData);
            }
        }

        if (jsonTemplate == null) {
            throw new IllegalArgumentException("No valid JSON template provided in the data dictionary.");
        }

        // Handle additional request data for JSON object
        if (jsonTemplate instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) jsonTemplate;

            for (Map.Entry<String, Object> entry : dataDictionary.entrySet()) {
                if (entry.getKey().startsWith("AddRequest-")) {
                    String jsonKey = entry.getKey().substring("AddRequest-".length());
                    boolean keyExists = hasKey(jsonObject, jsonKey);
                    if (!keyExists) {
                        jsonObject.put(jsonKey, entry.getValue());
                    }
                }
            }

            // Replace placeholders
            replaceJSONPlaceholders(jsonObject, dataDictionary);
            System.out.println("Constructed JSON Object Body: " + jsonObject.toString(4));
            return jsonObject;
        }

        // Handle additional request data for JSON array
        if (jsonTemplate instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) jsonTemplate;

            //When JsonArray is not empty
            for (int i = 0; i < jsonArray.length(); i++) {
                Object element = jsonArray.get(i);

                // Process only if the element is a JSONObject
                if (element instanceof JSONObject) {
                    JSONObject jsonObject = (JSONObject) element;

                    for (Map.Entry<String, Object> entry : dataDictionary.entrySet()) {
                        if (entry.getKey().startsWith("AddRequest-")) {
                            String jsonKey = entry.getKey().substring("AddRequest-".length());
                            boolean keyExists = hasKey(jsonObject, jsonKey);
                            if (!keyExists) {
                                jsonObject.put(jsonKey, entry.getValue());
                            }
                        }
                    }
                    // Replace placeholders in the JSONObject
                    replaceJSONPlaceholders(jsonObject, dataDictionary);
                }
            }


            // Check if the JSONArray is empty
            if (jsonArray.isEmpty()) {
                for (Map.Entry<String, Object> entry : dataDictionary.entrySet()) {
                    if (entry.getKey().startsWith("AddRequest-")) {
                        String jsonKey = entry.getKey().substring("AddRequest-".length());

                        // Create a new JSONObject to store the key-value pair
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put(jsonKey, entry.getValue());

                        // Add the new JSONObject to the JSONArray
                        jsonArray.put(jsonObject);
                    }
                }
            }

            System.out.println("Constructed JSON Array Body: " + jsonArray.toString(4));
            return jsonArray;
        }

        throw new IllegalArgumentException("The provided JSON template is neither a JSONObject nor a JSONArray.");
    }

    /**
     * Recursively checks if the key exists in a JSONObject or JSONArray.
     *
     * @param jsonObjectOrArray The JSON object or array to search.
     * @param key               The key to search for.
     * @return True if the key exists, false otherwise.
     */
    private boolean hasKey(Object jsonObjectOrArray, String key) {
        if (jsonObjectOrArray instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) jsonObjectOrArray;
            if (jsonObject.has(key)) {
                return true;
            }
            for (String nestedKey : jsonObject.keySet()) {
                Object value = jsonObject.get(nestedKey);
                if (value instanceof JSONObject || value instanceof JSONArray) {
                    if (hasKey(value, key)) {
                        return true;
                    }
                }
            }
        } else if (jsonObjectOrArray instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) jsonObjectOrArray;
            for (int i = 0; i < jsonArray.length(); i++) {
                Object value = jsonArray.get(i);
                if (value instanceof JSONObject || value instanceof JSONArray) {
                    if (hasKey(value, key)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * Handles form-data (key-value pairs).
     */
    private Map<String, String> handleFormData(Map<String, Object> dataDictionary) {
        Map<String, String> formData = new HashMap<>();
        for (Map.Entry<String, Object> entry : dataDictionary.entrySet()) {
            if (entry.getKey().startsWith("FormData-")) {
                formData.put(entry.getKey().substring("FormData-".length()), entry.getValue().toString());
            }
        }
        return formData;
    }

    /**
     * Handles x-www-form-urlencoded body type.
     */
    private String handleUrlEncoded(Map<String, Object> dataDictionary) {
        StringBuilder encodedData = new StringBuilder();

        for (Map.Entry<String, Object> entry : dataDictionary.entrySet()) {
            if (entry.getKey().startsWith("UrlEncoded-")) {
                try {
                    if (encodedData.length() > 0) {
                        encodedData.append("&");
                    }
                    // Encode the key
                    String key = URLEncoder.encode(entry.getKey().substring("UrlEncoded-".length()), "UTF-8");

                    // Preserve XML structure in value, only encode spaces and reserved characters
                    String value = entry.getValue().toString();
                    if (value.startsWith("<") && value.endsWith(">")) {
                        // If the value is XML, preserve its structure
                        encodedData.append(key).append("=").append(value);
                    } else {
                        // Encode non-XML values normally
                        encodedData.append(key).append("=").append(URLEncoder.encode(value, "UTF-8"));
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("URL_EncodedData: " + encodedData);
        return encodedData.toString();
    }


    /**
     * Retrieves the parent schema JSON object from a file specified in the `dataDictionary`.
     *
     * @param dataDictionary A map containing key-value pairs, including the "Parent_Schema" key specifying the schema file name.
     * @return The JSON object representing the parent schema.
     * @throws IOException If an I/O error occurs while reading the schema file.
     */

    public JSONObject getParentSchema(Map<String, String> dataDictionary) throws IOException {
        String schemaFilePath = "json/" + dataDictionary.get("Parent_Schema");
        try (FileReader reader = new FileReader(schemaFilePath)) {
            char[] buffer = new char[(int) new File(schemaFilePath).length()];
            reader.read(buffer);
            return new JSONObject(new String(buffer));
        }
    }

    /**
     * Asserts that the status code of the response matches the expected status code specified in the `dataDictionary`.
     *
     * @param response       The response object to validate.
     * @param dataDictionary A map containing key-value pairs, including the "Expected-StatusCode" key specifying the expected status code.
     */

    public void statusCodeAssertion(Response response, Map<String, String> dataDictionary) {
        String expectedStatusCodeStr = dataDictionary.get("StatusCode");
        if (expectedStatusCodeStr != null) {
            int expectedStatusCode = (int) Float.parseFloat(expectedStatusCodeStr);
            assert response.statusCode() == expectedStatusCode : "Expected status code: " + expectedStatusCode + ", but got: " + response.statusCode();
            Allure.step("Actual and Expected StatusCode are Matching: Expected Status Code to be " + expectedStatusCode + ", and Actual Status Code is " + response.statusCode());
        }
    }

    /**
     * Extracts a value from a JSON response based on a given JSON path expression.
     *
     * @param response The response object containing the JSON data.
     * @param jsonPath The JSON path expression to extract the value.
     */
    public void extractValueFromJson(Response response, String jsonPath) {
        Object value = JsonPath.read(response.asString(), jsonPath);
    }

    /**
     * Stores a key-value pair in a properties file. The key is constructed using the test case name from the `dataDictionary`.
     *
     * @param key            The key to store in the properties file.
     * @param value          The value to store in the properties file.
     * @param dataDictionary A map containing key-value pairs, including the "Test_Case_Name" key specifying the test case name.
     * @throws IOException If an I/O error occurs while writing to the properties file.
     */

    public void storeValueInFile(String key, String value, Map<String, Object> dataDictionary) throws IOException {
        // Construct the key using the Test_Case_Name from dataDictionary
        String testCaseName = (String) dataDictionary.get("DataBinding");
        String newKey = testCaseName + "_" + key;

        // Load existing properties file or create a new one
        Properties props = new Properties();
        String filePath = "src/test/java/api/resources/extracted.properties";  // Update the file path accordingly
        try {
            props.load(new FileReader(filePath));
        } catch (IOException e) {
            // File not found or unable to read, create a new properties object
        }

        // Append the new key-value pair to the properties
        props.setProperty(newKey, value);

        // Write the updated properties back to the file
        try (FileWriter writer = new FileWriter(filePath)) { // 'true' enables append mode
            props.store(writer, "Updated extracted values");
            writer.flush(); // Ensures immediate write
        } catch (IOException e) {
            System.err.println("Error writing properties file: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Extracts data from a JSON response based on keys in the `dataDictionary` that start with "Extract-",
     * and stores the extracted values in a properties file.
     *
     * @param dataDictionary A map containing key-value pairs, including keys starting with "Extract-" specifying JSON path expressions.
     * @param response       The response object containing the JSON data.
     * @throws IOException If an I/O error occurs while storing extracted values.
     */
    public void extractingData(Map<String, Object> dataDictionary, Response response) throws IOException {
        String responseAsString = response.getBody().asString(); // Extract the response body as a string

        for (Map.Entry<String, Object> entry : dataDictionary.entrySet()) {
            if (entry.getKey().startsWith("Extract-")) {
                String jsonPath = (String) entry.getValue();
                String extractedKey = entry.getKey().substring("Extract-".length());
                logger.debug("JSONPath Expression: " + jsonPath);

                try {
                    // Extract value using JsonPath
                    Object extractedValue = JsonPath.read(responseAsString, jsonPath);

                    if (extractedValue != null) {
                        String normalizedValue;

                        // Normalize the extracted value
                        if (extractedValue instanceof List) {
                            // If it's a list (from JSON array), join the elements into a comma-separated string
                            normalizedValue = String.join(",", ((List<?>) extractedValue).stream()
                                    .map(Object::toString)
                                    .collect(Collectors.toList()));
                        } else {
                            // For single values, convert to a string directly
                            normalizedValue = extractedValue.toString();
                        }

                        // Store the normalized value in the file
                        storeValueInFile(extractedKey, normalizedValue, dataDictionary);
                        logger.debug("Value '" + normalizedValue + "' stored in file with key '" + extractedKey + "'");
                    } else {
                        logger.info("Value not found for JSONPath: " + jsonPath);
                    }
                } catch (Exception e) {
                    logger.info("Error while extracting data with JSONPath: " + jsonPath);
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Compares expected output values from `expectedOutputDict` with actual values in the JSON response.
     * Skips keys that are equal to "statuscode" and performs SQL validation if specified.
     *
     * @param expectedOutputDict A map containing expected output values.
     * @param response           The response object containing the JSON data.
     */
    public void expectedOutputCompare(Map<String, String> expectedOutputDict, Response response) {
        for (Map.Entry<String, String> entry : expectedOutputDict.entrySet()) {
            if (!entry.getKey().equalsIgnoreCase("statuscode")) {
                String[] texts = entry.getValue().split(" \\|\\| ");
                String text1 = texts[0];
                String text2 = texts[1];
                if (!text2.toLowerCase().contains("sqlvalidation")) {
                    String expectedValue = text2;
                    String actualValue = String.valueOf(JsonPath.read(response.asString(), text1));
                    assert expectedValue.equals(actualValue) : "Mismatch: Expected value: " + expectedValue + " | Actual value: " + actualValue;
                }
            }
        }
    }

    /**
     * Prints the details of a request in JSON format.
     *
     * @param requestDetails A map containing key-value pairs representing request details.
     */
    public void printRequestDetails(Map<String, Object> requestDetails) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String requestDetailsJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDetails);
            System.out.println("Request Details: " + requestDetailsJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the JSON body for a request based on the HTTP method specified in the `dataDictionary`.
     * Constructs the JSON body using the `returnUpdatedJson` method if the HTTP method is POST or PUT.
     *
     * @param dataDictionary A map containing key-value pairs, including the "HTTP_Method" key specifying the HTTP method.
     * @param requestDetails A map to store the constructed JSON body.
     * @return The updated `requestDetails` map with the JSON body.
     * @throws IOException If an I/O error occurs while constructing the JSON body.
     */

    public Map<String, Object> setRequestJsonBody(Map<String, Object> dataDictionary, Map<String, Object> requestDetails) throws IOException {
        String httpMethod = dataDictionary.get("HTTP_Method").toString().toUpperCase();
        JSONObject updatedJson = null;

        if (httpMethod.equals("POST") || httpMethod.equals("PUT")) {
            try {
                updatedJson = returnUpdatedJson(dataDictionary);
                requestDetails.put("json", updatedJson);
            } catch (Exception e) {
                e.printStackTrace();
                throw new IOException("Error while setting request JSON body", e);
            }
        }
        return requestDetails;
    }

    /**
     * Attaches the given response to the Allure report for better reporting and debugging.
     *
     * @param response The response to attach, as a String.
     */

    public void attachResponseToAllure(Response response) {
        Allure.addAttachment("API Response", response.asString());
    }

    /**
     * Validates the API response JSON against the expected output specified in the data dictionary.
     *
     * @param dataDictionary A map containing key-value pairs, including the expected output values.
     * @param responseJson   The JSON object representing the API response.
     */
    public void responseValidation(Map<String, Object> dataDictionary, JSONObject responseJson) {
        Map<String, String> expectedResult = getExpectedOutput(dataDictionary);
        Map<String, Object> filteredExpectedOutputMap = new HashMap<>();
        for (Map.Entry<String, String> entry : expectedResult.entrySet()) {
            if (!entry.getKey().equals("StatusCode")) {
                filteredExpectedOutputMap.put(entry.getKey(), entry.getValue());
            }
        }
        JSONObject responsebody = responseJson;
        logger.debug("Response: " + responsebody.toString(4));
        validateResponse(filteredExpectedOutputMap, responseJson.toMap(), dataDictionary);
    }

    /**
     * Makes an API call based on the data dictionary and returns the response JSON object.
     *
     * @param dataDictionary A map containing key-value pairs required for making the API call, such as the HTTP method, URL, headers, and request body.
     * @return The JSON object representing the API response.
     */
    public JSONObject makeApiCall(Map<String, Object> dataDictionary, String dataref) {
        //Allure.suite("Test Case Details: " +  dataDictionary.get("Test_Case_Name"));// To add the name allure status under suite allure status level
        ObjectMapper objectMapper = new ObjectMapper();
        String combinedUrl = getCompleteUrl(replaceFetchValues(dataDictionary));
        Allure.addAttachment("API - URL", combinedUrl);
        Map<String, String> extractedHeaders = getHeader(dataDictionary);
        Map<String, String> expectedOutput = getExpectedOutput(dataDictionary);
        Response response = null;
        Map<String, Object> requestDetails = new HashMap<>();
        requestDetails.put("method", dataDictionary.get("HTTP_Method").toString().toUpperCase());
        requestDetails.put("url", combinedUrl);
        requestDetails.put("headers", extractedHeaders);

        JSONObject responseJson = null;
        JSONArray responseJsonArray = null;
        try {
            printRequestDetails(requestDetails);
//            requestDetails = setRequestJsonBody(dataDictionary, requestDetails);

            String httpMethod = dataDictionary.get("HTTP_Method").toString().toUpperCase();
            Object body = constructRequestJSONBody(dataDictionary);

            config = config().httpClient(
                    HttpClientConfig.httpClientConfig()
                            .setParam("http.connection.timeout", 30000)
                            .setParam("http.socket.timeout", 30000)
            );

            switch (httpMethod) {
                case "GET":
                case "READ":
                    response = RestAssured.given().headers(extractedHeaders).get(combinedUrl);
                    break;
                case "POST":
                case "CREATE":
                    if (body instanceof JSONObject) {
                        response = RestAssured.given().headers(extractedHeaders).contentType(ContentType.JSON).body(body.toString()).post(combinedUrl);
                    } else if (body instanceof JSONArray) {
                        response = RestAssured.given().headers(extractedHeaders).contentType(ContentType.JSON).body(body.toString()).post(combinedUrl);
                    } else if (body instanceof Map) {
                        RequestSpecification requestSpec = RestAssured.given().headers(extractedHeaders).contentType(ContentType.MULTIPART);  // Form-Data Type
                        // Add each key-value pair as multipart
                        for (Map.Entry<String, ?> entry : ((Map<String, ?>) body).entrySet()) {
                            requestSpec.multiPart(entry.getKey(), entry.getValue());
                        }
                        response = requestSpec.post(combinedUrl);
                    } else if (body instanceof String) {
                        response = RestAssured.given().headers(extractedHeaders).contentType(ContentType.URLENC).body(body).post(combinedUrl);
                    } else {
                        response = RestAssured.given().headers(extractedHeaders).post(combinedUrl);
                    }
                    break;
                case "PUT":
                case "UPDATE":
                    if (body instanceof JSONObject) {
                        response = RestAssured.given().headers(extractedHeaders).contentType(ContentType.JSON).body(body.toString()).put(combinedUrl);
                    } else if (body instanceof JSONArray) {
                        response = RestAssured.given().headers(extractedHeaders).contentType(ContentType.JSON).body(body.toString()).put(combinedUrl);
                    } else if (body instanceof Map) {
                        RequestSpecification requestSpec = RestAssured.given().headers(extractedHeaders).contentType(ContentType.MULTIPART);  // Form-Data Type
                        // Add each key-value pair as multipart
                        for (Map.Entry<String, ?> entry : ((Map<String, ?>) body).entrySet()) {
                            requestSpec.multiPart(entry.getKey(), entry.getValue());
                        }
                        response = requestSpec.put(combinedUrl);
                    } else if (body instanceof String) {
                        response = RestAssured.given().headers(extractedHeaders).contentType(ContentType.URLENC).body((String) body).put(combinedUrl);
                    } else {
                        response = RestAssured.given().headers(extractedHeaders).put(combinedUrl);
                    }
                    break;
                case "PATCH":
                    if (body instanceof JSONObject) {
                        response = RestAssured.given().headers(extractedHeaders).contentType(ContentType.JSON).body(body.toString()).patch(combinedUrl);
                    } else if (body instanceof JSONArray) {
                        response = RestAssured.given().headers(extractedHeaders).contentType(ContentType.JSON).body(body.toString()).patch(combinedUrl);
                    } else if (body instanceof Map) {
                        RequestSpecification requestSpec = RestAssured.given().headers(extractedHeaders).contentType(ContentType.MULTIPART);  // Form-Data Type
                        // Add each key-value pair as multipart
                        for (Map.Entry<String, ?> entry : ((Map<String, ?>) body).entrySet()) {
                            requestSpec.multiPart(entry.getKey(), entry.getValue());
                        }
                        response = requestSpec.patch(combinedUrl);
                    } else if (body instanceof String) {
                        response = RestAssured.given().headers(extractedHeaders).contentType(ContentType.URLENC).body((String) body).patch(combinedUrl);
                    } else {
                        response = RestAssured.given().headers(extractedHeaders).patch(combinedUrl);
                    }
                    break;
                case "DELETE":
                    response = RestAssured.given().headers(extractedHeaders).delete(combinedUrl);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid HTTP method: " + httpMethod);
            }

            if (response != null) {
                String responseBody = response.asString();
                if (responseBody.trim().startsWith("{")) {
                    // Handle JSON response
                    responseJson = new JSONObject(responseBody);
                    System.out.println("Response JSON Object: " + responseJson.toString(4));
                } else if (responseBody.trim().startsWith("[")) {
                    // Handle JSON array response
                    responseJsonArray = new JSONArray(responseBody);
                    System.out.println("Response JSON Array: " + responseJsonArray.toString(4));
                } else if (responseBody.trim().startsWith("<?xml")) {
                    // Handle XML response and convert to JSON
                    JSONObject jsonFromXml = XML.toJSONObject(responseBody);
                    responseJson = jsonFromXml;
                    System.out.println("Converted JSON from XML: " + jsonFromXml.toString(4));
                } else {
                    System.err.println("Unknown response format: " + responseBody);
                }

                System.out.println("Response StatusCode: " + response.statusCode());
                logger.info(combinedUrl + "  -> Request has been sent to the base URL ");
                Allure.addAttachment("Request Details", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDetails));
                if (body != null)
                    Allure.addAttachment("Request Body:", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body.toString()));
                if (responseJson != null)
                    Allure.addAttachment("Response JSON", responseJson.toString(4));
                else if (responseJsonArray != null)
                    Allure.addAttachment("Response JSON Array", responseJsonArray.toString(4));

                Allure.addAttachment("Response StatusCode", String.valueOf(response.statusCode()));
                Allure.addAttachment("Result: API Response ", response.asString());
                extractingData(dataDictionary, response);
                statusCodeAssertion(response, expectedOutput);
            }
        } catch (Exception e) {
            logger.error("Error while making API call: ", e);
            Allure.addAttachment("Exception", e.toString());
            throw new RuntimeException(e);
        }
        if (responseJson != null) {
            responseJson.put("DataBinding", dataref);
            return responseJson;
        } else if (responseJsonArray != null) {
            JSONObject resultObject = new JSONObject();
            resultObject.put("array", responseJsonArray); // Put the entire JSON array under a key
            resultObject.put("DataBinding", dataref);
            return resultObject;
        }
        throw new IllegalArgumentException("The API Response is neither a JSONObject nor a JSONArray.");
    }

    /**
     * Checks if a given string is a valid JSON object or array.
     *
     * @param str The string to be checked.
     * @return True if the string is a valid JSON object or array, false otherwise.
     */
    private boolean isValidJson(String str) {
        try {
            new JSONObject(str);
        } catch (JSONException ex) {
            try {
                new JSONArray(str);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }


    /**
     * Retrieves a nested value from a map based on a dot-separated key.
     *
     * @param data     The map containing the data.
     * @param jsonPath The nested key representing the path to the nested value.
     * @return The nested value, or null if not found.
     */
    public static Object getNestedValue(Map<String, Object> data, String jsonPath) {
        try {
            // Convert Map to JSON string
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(data);

            // Use the JSON string with JsonPath
            Object result = JsonPath.parse(jsonResponse)
                    .read(jsonPath);

            // If the result is a List, extract the first element
            if (result instanceof List) {
                List<?> resultList = (List<?>) result;
                if (!resultList.isEmpty()) {
                    result = resultList.get(0);
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Replaces placeholders in the data dictionary with values fetched from an INI file.
     *
     * @param dataDictionary A map containing key-value pairs, including placeholders to be replaced.
     * @return The updated data dictionary with fetched values.
     */
    public Map<String, Object> replaceFetchValues(Map<String, Object> dataDictionary) {
        String iniFile = "src/test/java/api/resources/extracted.properties";
        Properties config = new Properties();

        try (FileReader reader = new FileReader(iniFile)) {
            config.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception if needed
        }

        for (Map.Entry<String, Object> entry : dataDictionary.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof String && ((String) value).contains("||")) {
                String[] parts = ((String) value).split("\\|\\|");
                String directValue = parts[0].trim();
                String fetchKey = parts[1].trim();
                if (fetchKey.startsWith("Fetch-")) {
                    fetchKey = fetchKey.substring(fetchKey.indexOf("-") + 1);
                    if (config.containsKey(fetchKey)) {
                        dataDictionary.put(key, directValue+"||"+config.getProperty(fetchKey));
                    } else {
                        logger.info("Error: Key '" + fetchKey + "' not found in " + iniFile);
                    }
                }
            } else if (value instanceof String && ((String) value).startsWith("Fetch-")) {
                String fetchKey = ((String) value).substring(((String) value).indexOf("-") + 1);
                if (config.containsKey(fetchKey)) {
                    dataDictionary.put(key, config.getProperty(fetchKey));
                } else {
                    logger.info("Error: Key '" + fetchKey + "' not found in " + iniFile);
                }
            }
        }
        return dataDictionary;
    }


    /**
     * Validates the actual API response against the expected output.
     * <p>
     * //     * @param expectedOutput A map containing the expected output values.
     * //     * @param response A map representing the actual API response.
     */
    public void validateResponse(Map<String, Object> expectedOutput, Map<String, Object> response, Map<String, Object> dataDictionary) {
        // Convert response to JSON object if it's a string
        JSONObject responseJson;
        try {
            responseJson = new JSONObject(response);
        } catch (JSONException e) {
            System.err.println("Error parsing response JSON: " + e.getMessage());
            return;
        }

        System.out.println("Expected Response: " + new JSONObject(expectedOutput).toString(4));
        logger.debug("Actual Response: " + responseJson.toString(4));

        // Keys to exclude from validation
        List<String> keysToExclude = Arrays.asList("statuscode");
        // Validation logic

        for (Map.Entry<String, Object> entry : expectedOutput.entrySet()) {
            String key = entry.getKey();
            Object keyValue = entry.getValue();

            if (keysToExclude.contains(key.toLowerCase())) {
                continue;
            }

            // Handle SQLQuery keys
            if (key.toLowerCase().contains("sqlquery")) {
                validationWithDatabase(response, convertToStringMap(expectedOutput), dataDictionary);
                continue;
            }


            if (keyValue instanceof String) {
                String keyValueStr = (String) keyValue;
                if (keyValueStr.contains("||")) {
                    String[] parts = keyValueStr.split("\\|\\|", 2);
                    String nestedKey = parts[0].trim();
                    String expectedValue = parts[1].trim();
                    logger.info("LHS :"+nestedKey + " & RHS :"+expectedValue);
                    // Check if the expected value contains "Attr-" prefix
                    if (expectedValue.toLowerCase().startsWith("attr-")) {
                        String propKey = expectedValue.substring(5).trim();
                        propKey = propKey+"."+System.getProperty("env");
                        String propValue = LoadProp.prop.getProperty(propKey);
                        // If the property exists, use it as the expected value
                        if (propValue != null) {
                            expectedValue = propValue;
                        } else {
                            System.err.println("Property key " + propKey + " not found in properties file.");
                        }
                    }else if (expectedValue.toLowerCase().startsWith("fetch-")) {
                        String iniFile = "src/test/java/api/resources/extracted.properties";
                        Properties config = new Properties();

                        try (FileReader reader = new FileReader(iniFile)) {
                            config.load(reader);
                        } catch (IOException e) {
                            e.printStackTrace();
                            // Handle the exception if needed
                        }
                        expectedValue = expectedValue.substring(expectedValue.indexOf("-") + 1);
                        if (config.containsKey(expectedValue)) {
                            expectedValue = config.getProperty(expectedValue);
                        }
                    }

                    Object actualValue = getNestedValue(response, nestedKey); // Pass JSONObject
                    Allure.step("Actual Value for " + nestedKey + ": " + actualValue); // Debugging print statement
                    try {
                        Assert.assertEquals(expectedValue, String.valueOf(actualValue));
                        Allure.step("Actual and Expected Values are Matching: Expected " + nestedKey + " value to be " + expectedValue + ", and Actual value is " + actualValue);
                    } catch (AssertionError e) {
                        System.err.println("Actual and Expected Values are Not Matching: Expected " + nestedKey + " value to be " + expectedValue + ", but got " + actualValue);
                        throw e;
                    }
                } else {
                    String actualKey = key.replace("expectedoutput-", "").trim();
                    Object actualValue = getNestedValue(response, actualKey); // Pass JSONObject
                    Allure.step("Actual Value for " + actualKey + ": " + actualValue); // Debugging print statement
                    try {
                        Assert.assertEquals(keyValueStr, String.valueOf(actualValue));
                        Allure.step("Actual and Expected Values are Matching: Expected " + actualKey + " value to be " + keyValueStr + ", and Actual value is " + actualValue);
                    } catch (AssertionError e) {
                        System.err.println("Actual and Expected Values are Not Matching: Expected " + actualKey + " value to be " + keyValueStr + ", but got " + actualValue);
                        throw e;
                    }
                }
            } else {
                logger.info("Skipping non-string key value pair: " + keyValue);
            }
        }
    }

    /**
     * Converts a map with values of type Object to a map with values of type String.
     * Only entries with String values are included in the resulting map.
     *
     * @param map The original map with values of type Object.
     * @return A new map containing only the entries from the original map that have String values.
     */
    private Map<String, String> convertToStringMap(Map<String, Object> map) {
        Map<String, String> stringMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof String) {
                stringMap.put(entry.getKey(), (String) entry.getValue());
            }
        }
        return stringMap;
    }

    /**
     * Validates the API response against values fetched from the database based on a SQL query specified in the data dictionary.
     *
     * @param response        A map representing the actual API response.
     * @param expectedOutputs A map containing key-value pairs, including the SQL query and expected outputs.
     * @param dataDictionary  A map containing key-value pairs, including the SQL query and all other values.
     */
    public void validationWithDatabase(Map<String, Object> response, Map<String, String> expectedOutputs, Map<String, Object> dataDictionary) {
        String dbType = System.getProperty("dbType", "mysql");
        Object expectedValue = null;

        for (Map.Entry<String, String> entry : expectedOutputs.entrySet()) {
            String keyValue = entry.getValue();
            if (keyValue != null) {
                if (keyValue.contains("||")) {
                    String[] parts = keyValue.split("\\|\\|", 2);
                    String nestedKey = parts[0].trim();
                    expectedValue = getNestedValue(response, nestedKey); // Pass JSONObject
                } else {
                    continue;
                }
            }
            logger.info("Expected Value for Query from Test data sheet = " + expectedValue);

            String sqlQuery = replaceQueryPlaceholders(readQueryFromProperties(expectedOutputs), dataDictionary);

//            String sqlQuery = getApiSqlQuery(dataDictionary);
            if (sqlQuery != null) {
                String valueFromDatabase = getValueFromMultipleDB(sqlQuery, dbType);

                logger.info("Actual Value form database: " + valueFromDatabase); // Debugging print statement
                assert valueFromDatabase.equals(expectedValue) : "Expected  value to be " + expectedValue + ", but got: " + valueFromDatabase;
                Allure.step("Actual and Expected Values are Matching: Expected  value to be " + expectedValue + ", and Actual value is " + valueFromDatabase);
                Allure.addAttachment("DBAssertionResults: ", "Actual and Expected Values are Matching: Expected  value to be " + expectedValue + ", and Actual value is " + valueFromDatabase);


            } else {
                throw new RuntimeException("SQL query not found in data dictionary.");
            }
        }
    }

    /*
     * Retrieves the SQL query from the data dictionary.
     * @param dataDictionary A map containing key-value pairs, including the SQL query.
     * @return The SQL query string, or null if not found.
     */
    public String getApiSqlQuery(Map<String, String> dataDictionary) {
        for (Map.Entry<String, String> entry : dataDictionary.entrySet()) {
            if (entry.getKey().toLowerCase().contains("sqlquery")) {
                return entry.getValue();
            }
        }
        return null;
    }

    // Method to read the query template from the properties file
    public static String readQueryFromProperties(Map<String, String> dataDictionary) {
        String value = null;
        String queryTemplate = null;
        String propertiesFilePath = "src/test/resources/sqlQueries.properties"; // Adjust path as needed

        for (Map.Entry<String, String> entry : dataDictionary.entrySet()) {
            if (entry.getKey().toLowerCase().contains("sqlquery")) {
                value = entry.getValue();
            }
        }

        try {
            // Load the properties file containing the queries
            Properties properties = new Properties();
            properties.load(new FileInputStream(propertiesFilePath));

            // Retrieve the query template by key
            if (properties.containsKey(value)) {
                queryTemplate = properties.getProperty(value);
            } else {
                throw new IllegalArgumentException("SQL Key not found: " + value);
            }
        } catch (IOException e) {
            System.err.println("Error reading properties file: " + e.getMessage());
        }

        return queryTemplate;
    }

    // Method to replace '{{key}}' placeholders in the query with actual values
    public String replaceQueryPlaceholders(String queryTemplate, Map<String, Object> dataDictionary) {
        if (queryTemplate == null || queryTemplate.isEmpty()) {
            throw new IllegalArgumentException("Query template cannot be null or empty.");
        }

        // Convert the dataDictionary into a map of String key-value pairs
        Map<String, String> sqlQueryInputs = getSQLQueryInput(dataDictionary);

        // Iterate over each key-value pair in the map
        for (Map.Entry<String, String> entry : sqlQueryInputs.entrySet()) {
            String placeholder = "{{" + entry.getKey() + "}}"; // Convert to the new format {{key}}
            String value = entry.getValue();

            if (queryTemplate.contains(placeholder)) {
                // Replace the placeholder with the actual value
                queryTemplate = queryTemplate.replace(placeholder, value);
            } else {
                logger.info("Warning: Placeholder for key '" + entry.getKey() + "' not found in query.");
            }
        }

        Allure.step("Final Query: " + queryTemplate);
        return queryTemplate;
    }

    public static void clearExtractedProperties() {
        String filePath = "src/test/java/api/resources/extracted.properties";  // Update the file path accordingly
        try (FileWriter writer = new FileWriter(filePath, false)) {
            // Overwrite the file with an empty string
            writer.write("");
            logger.info("Properties file cleared.");
        } catch (IOException e) {
            System.err.println("Error clearing properties file: " + e.getMessage());
        }
    }

}