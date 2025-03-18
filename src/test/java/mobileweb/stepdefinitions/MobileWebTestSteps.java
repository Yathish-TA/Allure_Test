package mobileweb.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import mobileweb.resources.testrunner.RunMobileWebTest;
import mobileweb.utils.MobileWebBaseClass;
import mobileutils.PageObjectBaseClassMobile;

public class MobileWebTestSteps extends MobileWebBaseClass {

    MobileWebBaseClass base;

    public MobileWebTestSteps() {
        super(RunMobileWebTest.getBaseInstance());
        this.base= RunMobileWebTest.getBaseInstance();
    }

    // Background
    @Given("Fetching Mobile data {string} and {string}")
    public void loadpayloadpath(String Workbook, String Worksheet) {
        MobileWebTestSteps.Workbook = Workbook;
        MobileWebTestSteps.Worksheet = Worksheet;
    }


    @Given("Loading testcase data: {string}")
    public void testCaseNumber(String Testcasenum) {
        this.datavalues = testdata.getExcelData(Testcasenum, MobileWebTestSteps.Workbook, MobileWebTestSteps.Worksheet);
        PageObjectBaseClassMobile.setDatavalue(this.datavalues);
        alluresetTestcase.setAllureTestcaseName(datavalues.get("Test_Case_Name"));
    }

    @When("Check order status with order number and name")
    public void enterOrderDetails() {
        getHomePage().clickMoreButton();
        getHomePage().clickOrderStatus();
        getHomePage().enterOrderInfo();
        getHomePage().clickOrderButton();
    }

    @Then("Verify the order details status")
    public void verifyOrderDetailsStatus() {
        getHomePage().verifyOrderDetailsPage();
    }

    @When("Product is searched in the search bar")
    public void productSearchBar() {
        getProductSearchPage().searchProduct();
    }

    @Then("Verify product is displayed")
    public void isProductDisplayed() {
        getProductSearchPage().verifyProduct();
    }

    @Given("Enter {string} in Search bar")
    public void searchKeyWord(String keyWord) {
        getMWebHomePage().searchItem(keyWord);
    }

    @When("User tries to login with email and password")
    public void loginDetails() throws Exception {
        getLoginPage().loginAction();
        getLoginPage().loginDetails();
    }


    @Then("Verify Sign in error message")
    public void verifyLoginError() throws InterruptedException {
        getLoginPage().verifySigninErrorMsg();
    }

    @Given("On login page user navigates to the Forgot your password page")
    public void clickForgotPassword() {
        getLoginPage().loginAction();
        getLoginPage().clickForgotLink();
    }

    @Then("The Change password page should be displayed")
    public void verifyChangePasswordPage() {
        getLoginPage().verifyChangePwdPage();
    }


    @When("User selects a product category from the menu")
    public void chooseProductCategory() {
        getProductSearchPage().clickHamburgerMenu();
        getProductSearchPage().chooseProduct();
    }

    @Then("The respective product category page should be displayed")
    public void verifyProductCategoryPage() {
        getProductSearchPage().verifyCategoryPageHeader();
    }


}





