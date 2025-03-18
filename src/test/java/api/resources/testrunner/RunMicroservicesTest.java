package api.resources.testrunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import qtestmanager.QTestUpdater;

import static allurereportgeneration.AllureReportGeneration.*;
import static apicoreutils.ApiCoreModel.clearExtractedProperties;

@CucumberOptions(
        features = "src/test/java/api/resources/features",
        glue = {"api/stepdefinitions"},
        tags = "@Smoke1",
        plugin = {
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "pretty",
                "html:target/Reports/report_api.html",
                "json:target/Reports/report_api.json",
                "junit:target/Reports/report_api.xml"
        }
)

public class RunMicroservicesTest extends AbstractTestNGCucumberTests {
        private static final String ALLURE_HTML_REPORT_PATH = "allure-report/index.html";

        @Override
        @DataProvider(parallel = true)
        public Object[][] scenarios() {
                return super.scenarios();
        }

        @BeforeClass
        public void generateReportStartTime() {
                CleanAllureReport();
                saveExecutionStartTime();
        }

        @AfterClass
        public void Generatereport() throws Exception {
                // Conditional QTest status update
                String updateStatus = System.getProperty("updateStatus", "No");
                if (updateStatus.equalsIgnoreCase("Yes")) {
                        QTestUpdater.updateQTestStatus();
                }
                loadProperties();
                reportGeneration();
                clearExtractedProperties();
        }

        @AfterSuite
        public void uploadReportAfterSuite() {
                // Conditional report upload
                String updateStatus = System.getProperty("updateStatus", "No");
                if (updateStatus.equalsIgnoreCase("Yes")) {
                        QTestUpdater.uploadReportToQtest(ALLURE_HTML_REPORT_PATH);
                }
        }

}
