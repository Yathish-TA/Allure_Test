package mobileapp.resources.testrunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import mobileapp.utils.MobileAppBaseClass;
import mobileapp.wrappers.MobileAppDriverInvoke;
import org.testng.annotations.*;
import qtestmanager.QTestUpdater;

import static allurereportgeneration.AllureReportGeneration.*;
import static mobileapp.utils.MobileAppBaseClass.genericWrapper;

@CucumberOptions(
        features = "src/test/java/mobileapp/resources/features",
        glue = {"mobileapp/stepdefinitions"},
        tags = "@qtest",
//        dryRun = true,
        plugin = {
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "pretty"
        }
)

public class RunMobileAppTest extends AbstractTestNGCucumberTests {


    private final MobileAppBaseClass base = new MobileAppBaseClass();
    private static final String ALLURE_HTML_REPORT_PATH = "allure-report/index.html";

    @Override
    @DataProvider(parallel = true)
    public  Object[][] scenarios() {
        return super.scenarios();
    }

    @BeforeClass
    public void noteStartExecutionTime() {
        CleanAllureReport();
        saveExecutionStartTime();
    }

    @BeforeMethod
    public void setup() {
        base.setupMobile();
    }

    @AfterMethod
    public void close() {
        try {
            if (genericWrapper != null) {
                genericWrapper.closeAllBrowsers();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error during cleanup", e);
        }
    }

    @AfterClass
    public void generateReport() throws Exception {
        String updateStatus = System.getProperty("updateStatus", "No");
        if (updateStatus.equalsIgnoreCase("Yes")) {
            QTestUpdater.updateQTestStatus();
        }
        loadProperties();
        reportGeneration();
    }

    @AfterSuite
    public void uploadReportAfterSuite() {
        String updateStatus = System.getProperty("updateStatus", "No");
        if (updateStatus.equalsIgnoreCase("Yes")) {
            QTestUpdater.uploadReportToQtest(ALLURE_HTML_REPORT_PATH);
        }
    }
}
