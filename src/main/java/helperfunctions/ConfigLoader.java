package helperfunctions;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class ConfigLoader {

    public static Map<String, Object> loadYamlFile(String filePath) {
        Yaml yaml = new Yaml();
        try (InputStream in = ConfigLoader.class.getClassLoader().getResourceAsStream(filePath)) {
            if (in == null) {
                throw new RuntimeException("Unable to find YAML file: " + filePath);
            }
            return yaml.load(in);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load YAML file: " + filePath, e);
        }
    }

    public static Properties loadYamlFileAsProperties(String filePath) {
        Yaml yaml = new Yaml();
        Properties properties = new Properties();
        try (InputStream in = ConfigLoader.class.getClassLoader().getResourceAsStream(filePath)) {
            if (in == null) {
                throw new RuntimeException("Unable to find YAML file: " + filePath);
            }
            Map<String, Object> yamlMap = yaml.load(in);
            yamlMap.forEach((key, value) -> {
                if (value != null) {
                    properties.setProperty(key, value.toString());
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Failed to load YAML file: " + filePath, e);
        }
        return properties;
    }

}
