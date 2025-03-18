package web.stepdefinitions;

import browserstack.UpdateSessionName;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;

import io.qameta.allure.Allure;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.testng.Assert;
import org.testng.Reporter;
import qtestmanager.Hooks;
import web.pageobjects.PageObjectBaseClass;
import web.resources.testrunner.WebTestRun;
import web.utils.BaseClass;
import webutils.LoadProperties;

import static azurevault.VaultData.getVaultData;


public class WebTestSteps extends BaseClass {
    private BaseClass base;

    public WebTestSteps() {
        super(WebTestRun.getBaseInstance());
        this.base=WebTestRun.getBaseInstance();
        //Allure.addAttachment("Browser Type (Step Definitions)",WebTestRun.getBaseInstance().getBrowserType());
    }

//    @Before
    public void beforeScenario(Scenario scenario) {
        URI featureUri = scenario.getUri();
        String featureFileName = Paths.get(featureUri).getFileName().toString().replace(".feature", "");
        String scenarioName = featureFileName + "_" + scenario.getName();
        SessionId sessionId = ((RemoteWebDriver) this.base.getDriver()).getSessionId();
        UpdateSessionName.updateBS_SessionName(sessionId, scenarioName, "Automate");
    }


    @After
    public void afterScenario(Scenario scenario) {
        Hooks.collectScenarioStatus(scenario);
        String scenarioName = scenario.getName();
        String customName = scenarioName + " - Browser Type: " + getBrowserType();
        getAlluresetTestcase().setAllureTestcaseName(customName);
    }

    @Given("Open Browser and Click URL")
    public void open_browser_and_click_url() {
        // Browser setup already handled in BaseClass
    }

    @Given("Data fetching {string} and {string}")
    public void fetchingData(String Workbook, String Worksheet) {
        WebTestSteps.Workbook = Workbook;
        WebTestSteps.Worksheet = Worksheet;
    }

    @Given("Test Case Number {string}")
    public void testCaseNumber(String Testcasenum) {
        this.datavalues = testdata.getExcelData(Testcasenum, WebTestSteps.Workbook, WebTestSteps.Worksheet);
        PageObjectBaseClass.setDatavalue(this.datavalues);
    }

    @When("product category is chosen")
    public void productCategoryIsChosen() {
        getProductSelectionPage().clickShopCategory();
        getProductSelectionPage().clickFarmAndRanch();
        getProductSelectionPage().clickFarmAndRanchSavings();
        getProductSelectionPage().clickChooseOption();
        getProductSelectionPage().clickCheckout();
    }

    @Then("product should be ordered")
    public void productShouldBeOrdered() throws IOException, InterruptedException {
        getProductSelectionPage().paymentDetails();
        getProductSelectionPage().billingDetails();
        getProductSelectionPage().productPayment();
        String text = getProductSelectionPage().getOrderInfo();
        getGenericWrapper().validationWithDatabase(datavalues, text);
        getSoftAssert().assertEquals(text, datavalues.get("expectedOutput"));
        getSoftAssert().assertAll();
    }

    @Given("Launch tsc website and login with {string} and {string}")
    public void launch_tsc_and_login_with_and(String username, String password) {
        getProductSearchPage().closeCookies();
        getProductSearchPage().closeSignUpPopUp();
        getSignInPage().signInNow(username, password);
    }

    @When("product is searched in the search bar with product name {string}")
    public void productSearchedInSearchBarWithProductName(String productname) {
        productname = getVaultData(productname);
        getProductSearchPage().searchProduct(productname);
    }

    @Then("product is displayed")
    public void productIsDisplayed() {
        getProductSearchPage().verifyProduct();
    }

    @When("order status is checked with order number and name")
    public void orderStatusCheckedWithOrderNumberAndName() {
        getOrderStatusPage().orderStatus();
    }

    @Then("Verify the status of the order")
    public void verifyStatusOrder() {
        String text = getOrderStatusPage().orderStatusConfirmation();
        boolean status = AullureTestValidation.ValidateAssert("Order Details", text, getDriver());
        if (!status) {
            getSoftAssert().fail("Assertion Failed as text values are not matching");
        }
    }

    @When("user signs in with email and password")
    public void userSignIn() throws Exception {
        getSignInPage().signIn();
    }

    @Then("user is navigated to the tsc page")
    public void userNavigatedToTscPage() {
        // Add necessary steps for navigating to the TSC page
    }

    @When("Tractor supply app icon is clicked")
    public void tractorSupplyAppIconClicked() {
        getTractorSupplyAppPage().clickTractorSupplyApp();
    }

    @Then("user is navigated to the application page")
    public void userNavigatedToApplicationPage() {
        getTractorSupplyAppPage().verifyApp();
    }

    @When("Services with TSC is clicked")
    public void servicesWithTscClicked() {
        getTractorServicePage().serviceWeb();
    }

    @Then("description of the services are displayed")
    public void descriptionOfServicesDisplayed() {
        getTractorServicePage().verifyService();
    }

    @Given("launch the site and perform login")
    public void demo1() {
        getDemoSite().login("standard_user", "secret_sauce");
        Allure.addAttachment("Browser Type: ", getBrowserType());
    }


    @Given("perform logout and login again")
    public void demo2() {
        getDemoSite().logout();
        getDemoSite().login("locked_out_user", "secret_sauce");
    }

    @Given("Perform add to cart action")
    public void demo3() {
        getDemoSite().productSelections();
    }


    // This is Demo Scenario of TSC Prod

    @Given("User is on homepage")
    public void user_is_on_homepage() {
        try {
            String url = getTscProdPage().getCurrentURL();
            Assert.assertEquals(url, LoadProperties.prop.getProperty("wcs.prod"));
        }catch (Exception e){
            Reporter.log("Failing to get Current Url");
        }
        getTscProdPage().closeCookies();
        getTscProdPage().closeSignUpPopUp();
    }

    @Then("Verifies navbar in the application {string}")
    public void user_validates_navbar_in_the_application(String testCaseId) {
        getTscProdPage().verifyNavBarIsVisible(testCaseId);
    }

    @And("Verifies more categories {string}")
    public void user_validates_more_categories(String testCaseId) {
        getTscProdPage().clickMoreCategoryButton();
        getTscProdPage().verifyMoreCategoryItemsIsVisible(testCaseId);
    }

    @Then("Verify header links")
    public void Verify_header_links(){
        getTscProdPage().isCompanyLogoDisplayed();
        getTscProdPage().isSearchBarDisplayed();
        getTscProdPage().isSignInLogoDisplayed();
        getTscProdPage().isCartLogoDisplayed();
        getTscProdPage().isStoreLinkDisplayed();
    }

    @And("Verify footer links {string}")
    public void Verify_footer_links(String testCaseId){
        getTscProdPage().scrollToFooter();
        getTscProdPage().verifyFooterIcon(testCaseId);
    }
}
