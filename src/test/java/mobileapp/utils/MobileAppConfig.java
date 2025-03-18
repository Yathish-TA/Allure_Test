package mobileapp.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class MobileAppConfig {

    private static final String PROPERTIES_FILE_PATH = MobileAppBaseClass.MOBILE_APP_PROPERTIES_FILE_PATH;
    private Properties properties;

    public MobileAppConfig() {
        properties = loadProperties();
    }

    /**
     * Loads properties from the configuration file.
     *
     * @return Loaded properties.
     */
    private Properties loadProperties() {
        Properties props = new Properties();
        try {
            props.load(Files.newBufferedReader(Paths.get(PROPERTIES_FILE_PATH)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties from file: " + PROPERTIES_FILE_PATH, e);
        }
        return props;
    }

    /**
     * Retrieves a property value from the loaded configuration.
     *
     * @param key Property key.
     * @return Property value.
     */
    public String getProperty(String key) {
        return System.getProperty(key, properties.getProperty(key));
    }
}