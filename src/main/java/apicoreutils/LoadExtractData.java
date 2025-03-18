package apicoreutils;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.util.Properties;

public class LoadExtractData {

    public static final String EXTRACTED_PATH = "src/test/java/api/resources/extracted.properties";
    public static Properties prop = new Properties();

    static {
        loadBaseURI(EXTRACTED_PATH);
    }
    /**
     *  Load properties file
     * @param pathToFile - file to the path
     */
    private static void loadBaseURI(String pathToFile){
        if(StringUtils.isEmpty(pathToFile))
            throw new RuntimeException("Path to the properties file is invalid! Please check -> "+pathToFile);
        try {
            prop.load(new FileInputStream(pathToFile));
        } catch (Exception e) {
            Allure.step(e.getMessage(), Status.FAILED);
        }
    }
}