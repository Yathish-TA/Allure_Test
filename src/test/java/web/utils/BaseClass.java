package web.utils;

import allurereportgeneration.AllureReportGeneration;
import genericwrappers.GenericWrapper;
import listeners.AllureTestNgListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.asserts.SoftAssert;
import web.pageobjects.*;
import webutils.WebExcelDatafetcher;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static azurevault.VaultData.vaultConnect;

public class BaseClass {
    private ThreadLocal<DriverCall> driverCall = ThreadLocal.withInitial(DriverCall::new);
    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private GenericWrapper genericWrapper;
    private SoftAssert softAssert;
    public AllureTestNgListener AullureTestValidation = new AllureTestNgListener();
    public static String Workbook;
    public static String Worksheet;
    public Map<String, String> datavalues = new HashMap<>();
    public static WebExcelDatafetcher testdata = new WebExcelDatafetcher();

    private String browserType;
    private static final String WEB_URL_PROPERTY_PATH = "src/test/java/web/resources/urls.properties";
    private static Properties prop;
    byte[] snapshot;

    private ProductSelectionPage productSelection;
    private OrderStatusPage orderStatus;
    private ProductSearchPage productSearch;
    private TscServicePage tractorService;
    private TractorSupplyAppPage tractorSupplyApp;
    private SignInPage signIn;
    private DemoSitePage demo;
    private AllureReportGeneration alluresetTestcase;
    private SauceDemoTestPage saucedemo;

    private TSCProdPage tscProdPage;


    static {
        prop = new Properties();
        try {
            prop.load(new FileInputStream(WEB_URL_PROPERTY_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Default Values no changes required
    public synchronized String getBrowserType() {return browserType;}
    public AllureReportGeneration getAlluresetTestcase() {return alluresetTestcase;}
    public WebDriver getDriver() {return driver.get();}
    public GenericWrapper getGenericWrapper() {
        return genericWrapper;
    }
    public SoftAssert getSoftAssert() {return softAssert;}


    //Pages instance methods
    public DemoSitePage getDemoSite() {
        return demo;
    }

    public ProductSelectionPage getProductSelectionPage() {return productSelection;}

    public OrderStatusPage getOrderStatusPage() {
        return orderStatus;
    }

    public ProductSearchPage getProductSearchPage() {
        return productSearch;
    }

    public TscServicePage getTractorServicePage() {
        return tractorService;
    }

    public TractorSupplyAppPage getTractorSupplyAppPage() {
        return tractorSupplyApp;
    }

    public SignInPage getSignInPage() {
        return signIn;
    }

    public SauceDemoTestPage getSaucedemo() {return saucedemo;}

    public TSCProdPage getTscProdPage(){return tscProdPage;}

    // Default constructor
    public BaseClass() {
    }

    // Constructor accepting another BaseClass instance
    public BaseClass(BaseClass base) {
        if (base != null) {
//            vaultConnect();
            this.browserType = base.getBrowserType();  // Shares WebDriver instance
            this.alluresetTestcase = base.getAlluresetTestcase(); // Shares AllureReportGeneration instance

            // Initialize page objects with the shared driver instance
            this.productSelection = base.getProductSelectionPage();
            this.orderStatus = base.getOrderStatusPage();
            this.productSearch = base.getProductSearchPage();
            this.tractorSupplyApp = base.getTractorSupplyAppPage();
            this.tractorService = base.getTractorServicePage();
            this.signIn = base.getSignInPage();
            this.genericWrapper = base.getGenericWrapper();
            this.softAssert = base.getSoftAssert();
            this.demo = base.getDemoSite();
            this.saucedemo = base.getSaucedemo();

            this.tscProdPage = base.getTscProdPage();
        }
    }

    public void initialSetup(String browser, String mode, String os, String osVersion, String browserVersion, String appUnderTest) {
        this.browserType = browser;
        driverCall.get().setupBrowser(browser, mode , os, osVersion, browserVersion);
        driver.set(driverCall.get().callDriver());
        alluresetTestcase = new AllureReportGeneration();

        // Initialize page objects with the driver instance
        productSelection = new ProductSelectionPage(getDriver());
        orderStatus = new OrderStatusPage(getDriver());
        signIn = new SignInPage(getDriver());
        productSearch = new ProductSearchPage(getDriver());
        tractorSupplyApp = new TractorSupplyAppPage(getDriver());
        tractorService = new TscServicePage(getDriver());
        genericWrapper = new GenericWrapper(getDriver());
        softAssert = new SoftAssert();
        demo = new DemoSitePage(getDriver());
        saucedemo = new SauceDemoTestPage(getDriver());
        tscProdPage = new TSCProdPage(getDriver());

        // Loading url here from prop file or on run time
//        getDriver().get(System.getProperty("URL", prop.getProperty(appUnderTest)));
//        getDriver().get("https://www.saucedemo.com/v1/index.html");
        getDriver().get("https://www.tractorsupply.com/");
    }

    public void closeBrowser() {
        try {
            if (driver.get() != null) {
                driver.get().quit();
                driver.remove();
                System.out.println("PASS: Browser closed successfully");
            }
        } catch (WebDriverException e) {
            System.out.println("FAIL: The browser got closed due to an unknown error");
        }
    }

}
