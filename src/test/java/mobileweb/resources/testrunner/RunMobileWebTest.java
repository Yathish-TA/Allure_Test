package mobileweb.resources.testrunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import mobileweb.utils.MobileWebBaseClass;
import org.testng.annotations.*;
import qtestmanager.QTestUpdater;

import static allurereportgeneration.AllureReportGeneration.*;

@CucumberOptions(
        features = "src/test/java/mobileweb/resources/features",
        glue = {"mobileweb/stepdefinitions"},
        tags = "@qtest",
        plugin = {
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "pretty"
        }
)
public class RunMobileWebTest extends AbstractTestNGCucumberTests {

    private static ThreadLocal<MobileWebBaseClass> base = new ThreadLocal<>();
    private static final String ALLURE_HTML_REPORT_PATH = "allure-report/index.html";

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @BeforeSuite
    public void cleanReport(){
        CleanAllureReport();
    }

    @BeforeClass
    public void noteStartExecutionTime() {
        saveExecutionStartTime();
    }

    @BeforeMethod
    public void setup() {
        MobileWebBaseClass mobileBase = new MobileWebBaseClass();
        mobileBase.initialMobileSetup();
        base.set(mobileBase);
    }

    @AfterMethod
    public void close() {
        if (base.get() != null) {
            base.get().closeBrowser();
            base.remove();
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
    public static MobileWebBaseClass getBaseInstance() {
        return base.get();
    }
}
