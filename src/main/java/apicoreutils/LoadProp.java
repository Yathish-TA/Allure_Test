package apicoreutils;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.util.Properties;

public class LoadProp {
    public static final String DB_CONFIG_PATH = "src/test/resources/DBConfig.properties";
    public static final String SQL_QUERY_PATH = "src/test/resources/sqlQueries.properties";
    public static final String ENV_PROPERTY_PATH = "src/test/java/api/resources/env.properties";
    public static final String ENV_TEST_DATA_PATH = "src/test/java/api/resources/envTestData.properties";
    public static Properties prop=new Properties();


    static {
        loadProperties(DB_CONFIG_PATH);
        loadProperties(SQL_QUERY_PATH);
        loadProperties(ENV_PROPERTY_PATH);
        loadProperties(ENV_TEST_DATA_PATH);
    }

    /**
     *  Load properties file
     * @param pathToFile - file to the path
     */
    private static void loadProperties(String pathToFile){
        if(StringUtils.isEmpty(pathToFile))
            throw new RuntimeException("Path to the properties file is invalid! Please check -> "+pathToFile);
        try {
            prop.load(new FileInputStream(pathToFile));
        } catch (Exception e) {
            Allure.step(e.getMessage(), Status.FAILED);
        }
    }
}
