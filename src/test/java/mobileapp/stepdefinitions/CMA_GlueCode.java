package mobileapp.stepdefinitions;

import browserstack.UpdateSessionName;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import mobileapp.utils.MobileAppBaseClass;
import mobileapp.wrappers.MobileAppDriverInvoke;
import mobileutils.PageObjectBaseClassMobile;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import qtestmanager.Hooks;

public class CMA_GlueCode extends MobileAppBaseClass {
    private MobileAppDriverInvoke base;

    @Before
    public void beforeScenario(Scenario scenario){
        String scenarioName = scenario.getName();
        SessionId sessionId = ((RemoteWebDriver) this.base.callDriver()).getSessionId();
        UpdateSessionName.updateBS_SessionName(sessionId, scenarioName, "App Automate");
    }

    @After
    public void afterScenario(Scenario scenario){
        Hooks.collectScenarioStatus(scenario);
    }

    // Background
    @Given("Fetching Mobile App data {string} and {string}")
    public void posAppLoadPath(String Workbook, String Worksheet) {
        MobileAppBaseClass.Workbook = Workbook;
        MobileAppBaseClass.Worksheet = Worksheet;
    }

    @Given("Loading App testcase Data: {string}")
    public void posTestCaseNumber(String Testcasenum) {
        this.datavalues = testdata.getExcelData(Testcasenum, MobileAppBaseClass.Workbook, MobileAppBaseClass.Worksheet);
        PageObjectBaseClassMobile.setDatavalue(this.datavalues);
        alluresetTestcase.setAllureTestcaseName(datavalues.get("Test_Case_Name"));
    }

    @When("Select table and number of guest for Dine in")
    public void selectTableAndGuestForDineIn() {

    }

    @Given("A User has logged in using email:{string} & password:{string}")
    public void userHasLoggedInUsingEmailPassword(String email, String password) {
        loginPage.CMALoginDetails(email,password);
    }

    @And("A User is on the Home Page")
    public void iAmOnTheHomePage() throws InterruptedException {
        homePage.isHomePageDisplayed();
    }

    @Then("A User Verifies the button Manage My PLCC Card is displayed on the home page")
    public void userVerifiesTheButtonManageMyPLCCCardIsDisplayedOnTheHomePage() {
        homePage.verifyManageMyPlcCardIsDisplayed();
    }

    @And("A Click on Manage My PLCC Card button on the page")
    public void clickOnManageMyPLCCCardButtonOnThePage() throws InterruptedException {
        homePage.clickManageMyCardButton();
    }

    @And("A Verify the My PLCC page is displayed after clicking Manage My PLCC Card button")
    public void verifyTheMyPLCCPageIsDisplayedAfterClickingManageMyPLCCCardButton() {
        homePage.verifyMyPLCCPageIsDisplayed();
    }

    @When("A User has logged in as guest user")
    public void userHasLoggedInAsGuestUser() throws Exception {
        loginPage.guestLogin();
    }

    @Given("A User search for SKU {string}")
    public void iSearchForSKU(String skuId) throws InterruptedException {
        homePage.enterSKUValueAndSearch(skuId);
    }

    @Then("A Click on Product Name Link for LineItem {string}")
    public void click_on_product_name_with_name_of(String productLine) {
        searchPage.clickProductNameWithLineNumber(productLine);
    }

    @Then("A Swipe left to right on pixel {string} and {string} from PDP")
    public void swipe_left_to_right_pdp(String X_axis,String Y_axis) {
        searchPage.swipeLeftPDP(X_axis,Y_axis);
    }

    @And("A Verify Search Page is visible")
    public void verify_search_page() {
        searchPage.verifySearchPage();
    }

    @Then("A Click on the SHOP tab")
    public void click_on_shop_tab() {
        shopPage.clickOnShop();
    }

    @And("A Click on Shop Category with name of {string}")
    public void click_on_shop_category_with_name_of(String selectCategory) {
        shopPage.myCategory(selectCategory);
    }

    @Then("A Swipe left to right on pixel {string} and {string} from SHOP")
    public void swipe_left_to_right(String X_axis,String Y_axis) {
        shopPage.swipeLeftShop(X_axis,Y_axis);
    }

    @And("A Verify Shop Page is visible")
    public void verify_shop_page() {
        shopPage.verifyShopPage();
    }


}
