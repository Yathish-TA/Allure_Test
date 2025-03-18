package mobileapp.utils;

import allurereportgeneration.AllureReportGeneration;
import genericwrappers.GenericWrapper;
import mobileapp.mobileapppageobjects.*;
import mobileapp.wrappers.MobileAppDriverInvoke;
import mobileutils.MobileExcelDatafetcher;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

import static azurevault.VaultData.vaultConnect;

public class MobileAppBaseClass {
    public static final String MOBILE_APP_PROPERTIES_FILE_PATH = "src/test/java/mobileapp/resources/MobileAppParams.properties";
    public MobileAppDriverClass driverCall = new MobileAppDriverClass();
    public AllureReportGeneration alluresetTestcase = new AllureReportGeneration();
    public Map<String, String> datavalues = new HashMap<>();
    public static MobileExcelDatafetcher testdata = new MobileExcelDatafetcher();
    public static String Workbook;
    public static String Worksheet;
    public static WebDriver driver;
    public static GenericWrapper genericWrapper;
    public static MobileTestContext mobileTestContext;

    //SauceLab Pages
    public static DemoPage sauce;
    public static CMA_BulK_Quote_Page cmaBulKQuotePage;

    //CMA Pages
    public static LoginPage loginPage;
    public static HomePage homePage;
    public static ProductSearchBarPage productSearchPage;
    public static ChangeStorePage changeStorePage;
    public static SearchPage searchPage;
    public static ShopPage shopPage;
    public static ComparePage comparePage;


    public void setupMobile() {
        vaultConnect();
        driverCall.setupBrowser();
        driver = MobileAppDriverInvoke.callDriver();
        String deviceType = MobileAppDriverInvoke.getDeviceType();
        genericWrapper = new GenericWrapper(driver);
        mobileTestContext = new MobileTestContext();

        //Pages
        sauce = new DemoPage(driver);
        cmaBulKQuotePage = new CMA_BulK_Quote_Page(driver);


        loginPage = new LoginPage(driver, deviceType);
        homePage = new HomePage(driver, deviceType);
        productSearchPage = new ProductSearchBarPage(driver, deviceType);
        changeStorePage = new ChangeStorePage(driver, deviceType);
        searchPage = new SearchPage(driver, deviceType);
        shopPage = new ShopPage(driver, deviceType);
        comparePage=new ComparePage(driver, deviceType);


    }
}
