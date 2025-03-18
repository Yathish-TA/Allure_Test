package web.resources.testrunner;

import io.cucumber.java.After;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.qameta.allure.Allure;
import org.testng.annotations.*;
import qtestmanager.QTestUpdater;
import web.utils.BaseClass;
import static allurereportgeneration.AllureReportGeneration.*;

@CucumberOptions(
        features = "src/test/java/web/resources/features",
        glue = "web.stepdefinitions",
        tags = "@Demo",
        plugin = {
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "pretty"
        }
)

//@Listeners({AllureTestNg.class})
public class WebTestRun extends AbstractTestNGCucumberTests {
        private static ThreadLocal<BaseClass> base = new ThreadLocal<>();
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
        public void generateReportStartTime() {
                saveExecutionStartTime();
        }

        @Parameters({"browserType","runMode", "os", "osVersion", "browserVersion", "appUnderTest"})
        @BeforeMethod
        public void getBrowserName(@Optional("chrome")String browser, @Optional("local")String runmode, @Optional("windows")String os, @Optional("11")String osVersion, @Optional("latest")String browserVersion, @Optional("wcs.prod") String appUnderTest) {
                // Get values from system properties first, if available
                browser = System.getProperty("browser", browser); // Get from system property or default to passed parameter
                runmode = System.getProperty("runMode", runmode);
                os = System.getProperty("os", os);
                osVersion = System.getProperty("osVersion", osVersion);
                browserVersion = System.getProperty("browserVersion", browserVersion);
                appUnderTest = System.getProperty("aut", appUnderTest);

                BaseClass baseClass = new BaseClass();
                baseClass.initialSetup(browser, runmode, os, osVersion, browserVersion, appUnderTest);
                base.set(baseClass);

                Allure.addAttachment("Browser Type: ", browser);
                System.out.println("Browser setup completed for: " + browser);
        }

        @AfterMethod
        public void tearDown() {
                if (base.get() != null) {
                        base.get().closeBrowser();
                        base.remove();
                }
        }

        @After
        public void tearDownAfter() {
                try {
                        Allure.getLifecycle().stopTestCase("your-test-uuid");
                        Allure.getLifecycle().writeTestCase("your-test-uuid");
                } catch (Exception e) {
                        // Handle exceptions if any
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

        public static BaseClass getBaseInstance() {
                return base.get();
        }

}
