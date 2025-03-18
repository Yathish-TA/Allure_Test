package mobileweb.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class MobileWebConfig {

    private static final String PROPERTIES_FILE_PATH = MobileWebBaseClass.MOBILE_WEB_PROPERTIES_FILE_PATH;
    private Properties properties;

    public MobileWebConfig() {
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