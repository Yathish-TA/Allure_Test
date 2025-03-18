package mobileweb.utils;

import allurereportgeneration.AllureReportGeneration;
import genericwrappers.GenericWrapper;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import mobileweb.mobilewebpageobjects.HomePage;
import mobileweb.mobilewebpageobjects.LoginPage;
import mobileweb.mobilewebpageobjects.ProductSearchBarPage;
import mobileweb.mobilewebpageobjects.mweb.*;
import mobileweb.wrappers.MobileWebDriverInvoke;
import mobileutils.MobileExcelDatafetcher;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

public class MobileWebBaseClass extends MobileWebDriverInvoke {

    public static final String MOBILE_WEB_PROPERTIES_FILE_PATH = "src/test/java/mobileweb/resources/MobileWebParams.properties";

    public ThreadLocal<MobileWebDriverClass> driverCall = ThreadLocal.withInitial(MobileWebDriverClass::new);
    public ThreadLocal<MobileWebDriverInvoke> driverInvoke = ThreadLocal.withInitial(MobileWebDriverInvoke::new);
    public ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public AllureReportGeneration alluresetTestcase = new AllureReportGeneration();
    public Map<String, String> datavalues = new HashMap<>();
    public static MobileExcelDatafetcher testdata = new MobileExcelDatafetcher();
    public static MobileWebConfig mobProperties = new MobileWebConfig();
    public static String Workbook;
    public static String Worksheet;
    public GenericWrapper genericWrapper;

    public HomePage homePage;
    public ProductSearchBarPage productSearchPage;
    public LoginPage loginPage;


    //Mobile Web
    public  MobileWebLoginPage mWebLoginPage;
    public  MobileWebHomePage mWebHomePage;
    public  MobileWebFulfillmentPage mWebFulfillmentPage;
    public  MobileWebPaymentPage mWebPaymentPage;
    public  MobileWebProductListPage mWebPLPage;
    public  MobileWebProductDetailsPage mWebPDPage;
    public  MobileWebOrderConfirmationPage mWebConfirmationPage;
    public  MobileWebCartPage mwebCartPage;

    public MobileWebBaseClass(MobileWebBaseClass base){
        if (base != null) {
            this.genericWrapper = base.getGenericWrapper();
            this.loginPage = base.getLoginPage();
            this.homePage = base.getHomePage();
            this.productSearchPage = base.getProductSearchPage();

            //Mobile Web
            this.mWebLoginPage = base.getMWebLoginPage();
            this.mWebHomePage = base.getMWebHomePage();
            this.mWebFulfillmentPage = base.getMWebFulfillmentPage();
            this.mWebPaymentPage = base.getMWebPaymentPage();
            this.mWebPLPage = base.getMWebPLPage();
            this.mWebPDPage = base.getMWebPDPage();
            this.mWebConfirmationPage = base.getMWebConfirmationPage();
            this.mwebCartPage = base.getMWebCartPage();
        }
    }

    public MobileWebBaseClass() {
    }

    public GenericWrapper getGenericWrapper() {
        return genericWrapper;
    }
    public LoginPage getLoginPage() { return loginPage; };

    public HomePage getHomePage() { return homePage; };

    public ProductSearchBarPage getProductSearchPage() { return productSearchPage; };

    public MobileWebLoginPage getMWebLoginPage() {
        return mWebLoginPage;
    }

    public MobileWebHomePage getMWebHomePage() {
        return mWebHomePage;
    }

    public MobileWebFulfillmentPage getMWebFulfillmentPage() {
        return mWebFulfillmentPage;
    }

    public MobileWebPaymentPage getMWebPaymentPage() {
        return mWebPaymentPage;
    }

    public MobileWebProductListPage getMWebPLPage() {
        return mWebPLPage;
    }

    public MobileWebProductDetailsPage getMWebPDPage() {
        return mWebPDPage;
    }

    public MobileWebOrderConfirmationPage getMWebConfirmationPage() {
        return mWebConfirmationPage;
    }

    public MobileWebCartPage getMWebCartPage() {
        return mwebCartPage;
    }

    /**
     * Sets up the mobile environment by initializing the browser, driver, and page objects.
     */
    public void initialMobileSetup() {
        driverCall.get().setupBrowser();
        driver.set(getDriver());
        genericWrapper = new GenericWrapper(getDriver());

        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
        productSearchPage = new ProductSearchBarPage(getDriver());

        //Mobile Web
        mWebLoginPage = new MobileWebLoginPage(getDriver());
        mWebHomePage = new MobileWebHomePage(getDriver());
        mWebFulfillmentPage = new MobileWebFulfillmentPage(getDriver());
        mWebPaymentPage = new MobileWebPaymentPage(getDriver());
        mWebPLPage = new MobileWebProductListPage(getDriver());
        mWebPDPage = new MobileWebProductDetailsPage(getDriver());
        mWebConfirmationPage = new MobileWebOrderConfirmationPage(getDriver());
        mwebCartPage = new MobileWebCartPage(getDriver());
    }

    public void closeBrowser() {
        try {
            if (getDriver() != null) {
                getDriver().quit();
                driver.remove();
            }
        } catch (WebDriverException e) {
            Allure.step("FAIL: The browser got closed due to an unknown error", Status.FAILED);
            Assert.fail("FAIL: The browser got closed due to an unknown error");
        }
    }

    public WebDriver getDriver() {
        return driverInvoke.get().callDriver();
    }
}
