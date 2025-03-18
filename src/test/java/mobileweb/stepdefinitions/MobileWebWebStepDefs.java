package mobileweb.stepdefinitions;

import browserstack.UpdateSessionName;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import mobileweb.resources.testrunner.RunMobileWebTest;
import mobileweb.utils.MobileWebBaseClass;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.testng.Assert;
import qtestmanager.Hooks;

import java.util.List;

import static azurevault.VaultData.getVaultData;

public class MobileWebWebStepDefs extends MobileWebBaseClass {

    MobileWebBaseClass base;

    public MobileWebWebStepDefs() {
        super(RunMobileWebTest.getBaseInstance());
        this.base= RunMobileWebTest.getBaseInstance();
    }

    @After
    public void afterScenario(Scenario scenario){
        Hooks.collectScenarioStatus(scenario);
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        String scenarioName = scenario.getName();
        SessionId sessionId = ((RemoteWebDriver) this.base.getDriver()).getSessionId();
        UpdateSessionName.updateBS_SessionName(sessionId, scenarioName, "Automate");
    }

    @Given("User is on Mobile Homepage")
    public void user_is_on_homepage() {
        getHomePage().closeCookiesMobile();
        getHomePage().closeSignUpPopUpMobile();
    }

    @Then("Verify user is successfully logged in and home page is displayed")
    public void verifyUserIsSuccessfullyLoggedInAndHomePageIsDisplayed() {
        Assert.assertTrue(getMWebLoginPage().verifyUserIsLoggedIn("Utwotest"), "Failed to log in");
    }

    @When("User click on sign in link")
    public void userClickOnSignInLink() {
        getMWebLoginPage().clickSignInLink();
    }

    @Then("User enters email and password")
    public void userEntersEmailAndPassword() {
        getMWebLoginPage().enterUserName(mobProperties.getProperty("Username1"));
        getMWebLoginPage().enterPassword(getVaultData(mobProperties.getProperty("Password1")));
        getMWebLoginPage().clickSignInButton();
    }

    @Given("Firearm Ammo Items {string} setup for online sale")
    public void firearmAmmoItemsSetupForOnlineSale(String item) {
        getMWebHomePage().searchItem(item);
        getMWebPDPage().selectItemFromPLP(item);
        if(StringUtils.isNumeric(item))
            getMWebPDPage().verifyTheProductIsAvailable(item);
    }

    @And("Those Items are present in Checkout \\(MPCO) page with delivery address {string}")
    public void thoseItemsArePresentInCheckoutMPCO(String arg) {
        getMWebPDPage().clickOnAddToCartButton();
        getMWebPDPage().clickOnSecureCheckoutButton();
        getMWebFulfillmentPage().enterDeliveryInformation(List.of(arg.split("_")));
        getMWebFulfillmentPage().clickOnContinueToPaymentButton();
    }

    @And("Those Items are present in Checkout \\(MPCO) when user is logged in")
    public void thoseItemsArePresentInCheckoutMPCOUserLoggedIn() {
        getMWebPDPage().clickOnAddToCartButton();
        getMWebPDPage().clickOnSecureCheckoutButton();
    }

    @And("Add payment details {string}")
    public void addPaymentDetails(String paymentDetails){
        getMWebPaymentPage().enterCreditCardDetails(List.of(paymentDetails.split("_")));
    }


    @When("Age verification copy displayed in MPCO:Payment page")
    public void ageVerificationCopyDisplayedInMPCOPaymentPage() {
        getMWebPaymentPage().verifyAgeRestrictionDisclaimer();
    }

    @And("Customer selects I accept checkbox")
    public void customerSelectsIAcceptCheckbox() {
        getMWebPaymentPage().clickOnIAcceptCheckBox();
    }

    @And("Clicks Place Order")
    public void clicksPlaceOrder() {
        getMWebPaymentPage().clickOnPlaceOrderButton();
    }

    @Then("Customer should be allowed to place Order successfully")
    public void customerShouldBeAllowedToPlaceOrderSuccessfully() {
        mWebConfirmationPage.verifyOrderIsPlaced();
    }

    @And("those Items {string} are NOT present in Checkout \\(MPCO)")
    public void thoseItemsAreNOTPresentInCheckoutMPCO(String item) {
        getMWebHomePage().searchItem(item);
        getMWebPDPage().selectItemFromPLP(item);
        if(StringUtils.isNumeric(item))
            getMWebPDPage().verifyTheProductIsAvailable(item);
    }

    @When("Customer proceeds to MPCO:Payment page with delivery address {string}")
    public void customerProceedsToMPCOPaymentPage(String address) {
        getMWebPDPage().clickOnAddToCartButton();
        getMWebPDPage().clickOnSecureCheckoutButton();
        getMWebFulfillmentPage().enterDeliveryInformation(List.of(address.split("_")));
        getMWebFulfillmentPage().clickOnContinueToPaymentButton();
    }

    @Then("there should NOT be any age verification")
    public void thereShouldNOTBeAnyAgeVerification() {
        getMWebPaymentPage().verifyAgeRestrictionDisclaimerIsNotDisplayed();
    }

    @When("MPCO Orders submitted as {string}, {string}, {string}")
    public void mpcoOrdersSubmittedAs(String arg0, String arg1, String arg2) {
    }

    @Then("there Orders should reach OMS successfully and be seen in the OMS front-end")
    public void thereOrdersShouldReachOMSSuccessfullyAndBeSeenInTheOMSFrontEnd() {
    }

    @And("customer clicks Place Order without selection of I accept checkbox")
    public void customerClicksPlaceOrderWithoutSelectionOfIAcceptCheckbox() {
        getMWebPaymentPage().clickOnPlaceOrderButton();
    }

    @Then("an error must be displayed for Customer to select the I accept checkbox")
    public void anErrorMustBeDisplayedForCustomerToSelectTheIAcceptCheckbox() {
        getMWebPaymentPage().verifyAgeAcknowledgeErrorMessage();
    }

    @And("clicks Place Order and payment or inventory error occurs")
    public void clicksPlaceOrderAndPaymentOrInventoryErrorOccurs() {
        getMWebPaymentPage().clickOnPlaceOrderButton();
    }

    @Then("the respective error must be displayed")
    public void theRespectiveErrorMustBeDisplayed() {
        getMWebPaymentPage().verifyPaymentErrorMessage();
    }

    @And("User Remove all items from the cart")
    public void userRemoveAllItemsFromTheCart() {
        getMWebCartPage().removeAllProductsFromCart();
    }

    @Then("User Search Non FireArm Ammo item {string} from Search Bar and navigate to PDP Page")
    public void userSearchNonFireArmAmmoItemFromSearchBarAndNavigateToPDPPage(String productid) {
        getMWebHomePage().searchItem(productid);
    }

    @Then("Verify that Buy Now Button is Displayed on PDP Page")
    public void verifyThatBuyNowButtonIsDisplayedOnPDPPage() {
        getMWebPDPage().verifyBuyNowButtonIsDisplayedInPDP();
    }

    @And("Verify Express checkout is Eligible in PDP Page")
    public void verifyExpressCheckoutIsEligibleInPDPPage() {
        getMWebPDPage().clickBuyNowButton();
    }

    @Then("Verify that Buy Now Button is not Displayed on PDP Page")
    public void verifyThatBuyNowButtonIsNotDisplayedOnPDPPage() {
        getMWebHomePage().scrollByPixcel("600");
        getMWebPDPage().verifyBuyNowButtonIsNotDisplayedInPDP();
    }

    @And("Verify Express checkout is InEligible in PDP Page")
    public void verifyExpressCheckoutIsInEligibleInPDPPage() {
        getMWebPDPage().verifyExpressCheckoutIsNotDisplayed();
    }

    @And("Add a Firearm Ammo Item to cart and navigate to cart page")
    public void addAFirearmAmmoItemToCartAndNavigateToCartPage() {
        getMWebPDPage().clickOnAddToCartButton();
        getMWebPDPage().navigateToCartPage();
    }

    @And("Click on Edit Zip link of the added item")
    public void clickOnEditZipLinkOfTheAddedItem() {
        getMWebCartPage().changeZipCodeButton();

    }

    @And("Enter a restricted zip {string} code of the item and verify")
    public void enterARestrictedZipCodeOfTheItemAndVerify(String zip) {
        getMWebCartPage().enterRestrictedZipCode(zip);
        getMWebCartPage().verifyRestrictedZipCode();
    }

    @And("Select {string} zipcode in Store Location")
    public void selectZipcodeInStoreLocation(String zipcode) {
        getMWebHomePage().chooseStoreBasedOnZip(zipcode);
    }


    @Then("verify delivery options are not displayed")
    public void verifyDeliveryOptionsAreNotDisplayed() {
        getMWebPDPage().verifyDeliveryOptionsNotDisplayed();
    }

    @And("Select Restricted {string} zipcode in Store Location")
    public void selectRestrictedZipcodeInStoreLocation(String zipcode) {
        getMWebHomePage().chooseStoreBasedOnZip(zipcode);
    }

    @And("verify shipping restriction verbiage has been displayed")
    public void verifyShippingRestrictionVerbiageHasBeenDisplayed() {
        getMWebPDPage().shippingRestrictionVerbiage();
    }

    @Then("verify delivery options has been displayed")
    public void verifyDeliveryOptionsHasBeenDisplayed() {
        getMWebPDPage().verifyDeliveryOptionsDisplayed();
    }

    @And("Navigate to Checkout Page and Click on Edit Address Link")
    public void navigateToCheckoutPageAndClickOnEditAddressLink() {
        getMWebFulfillmentPage().clickCheckOutPage();
    }

    @When("User enters different Address Details {string} in Checkout Page")
    public void userEntersDifferentAddressDetailsInCheckoutPage(String address) {
        getMWebFulfillmentPage().editBillingAddress(List.of(address.split("_")));
    }

    @Then("Verify the Restricted ZipCode Error Message Displayed to prevent the zipCode change")
    public void verifyTheRestrictedZipCodeErrorMessageDisplayedToPreventTheZipCodeChange() {
        getMWebFulfillmentPage().verifyErrorMessageToPreventZipChange();
    }

    @Then("User Search FireArm Ammo item {string} from Search Bar and navigate to PLP Page")
    public void userSearchFireArmAmmoItemFromSearchBarAndNavigateToPLPPage(String productid) {
        getMWebHomePage().searchItem(productid);
    }

    @Then("User Search Non FireArm Ammo item {string} from Search Bar and navigate to PLP Page")
    public void userSearchNonFireArmAmmoItemFromSearchBarAndNavigateToPLPPage(String productId) {
        getMWebHomePage().searchItem(productId);
    }

    @Then("User Search FireArm Ammo item {string} from Search Bar and navigate to PDP Page")
    public void userSearchFireArmAmmoItemFromSearchBarAndNavigateToPDPPage(String productid) {
        getMWebHomePage().searchItem(productid);
    }

    @And("Verify Express checkout is Eligible")
    public void verifyExpressCheckoutIsEligible() {
        getMWebPDPage().verifyExpressCheckoutIsDisplayed();
    }

    @And("Verify Express checkout is InEligible")
    public void verifyExpressCheckoutIsInEligible() {
        getMWebPDPage().verifyExpressCheckoutIsNotDisplayed();
    }

    @Then("Verify that Buy Now Button is Displayed on PLP Page")
    public void verifyThatBuyNowButtonIsDisplayedOnPLPPage() {
        getMWebPDPage().verifyBuyNowDisplayedInPLP();
    }

    @Then("Verify that Buy Now Button is not Displayed on PLP Page")
    public void verifyThatBuyNowButtonIsNotDisplayedOnPLPPage() {
        getMWebPDPage().verifyBuyNowNotDisplayedInPLP();
    }

    @Then("Verify Buy Now button should not be displayed for Firearm Ammo Item in PDP")
    public void verifyBuyNowButtonShouldNotBeDisplayedForFirearmAmmoItemsInPDP() {
        getMWebPDPage().verifyBuyNowButtonDisplayedForFirearmAmmoItemsInPDP();
    }

    @And("User Adds the Product to Cart from Product Detail Page")
    public void userAddsTheProductToCartFromProductDetailPage() {
        getMWebPDPage().clickAddCartButton();
    }

    @And("User Verify Edit Cart")
    public void userVerifyEditCart() {
       // getMWebPDPage().clickEditCart();
    }

    @Then("User click on checkout box")
    public void userClickOnCheckoutBox() {
        getMWebPDPage().clickCheckoutBox();
    }

    @And("User click on Continue to Payment")
    public void userClickOnContinueToPayment() {
        getMWebFulfillmentPage().clickOnContinueToPaymentButton();
    }

    @Then("Verify User Select the I accept checkbox")
    public void verifyUserSelectTheIAcceptCheckbox() {
        getMWebPaymentPage().selectIAcceptCheckBox();
    }

    @Then("verify that Buy Now button is not displayed")
    public void verifyThatBuyNowButtonIsNotDisplayed() {
        getMWebPDPage().verifyBuyNowButtonIsNotDisplayed();
    }

    @And("verify Express checkout is ineligible")
    public void verifyExpressCheckoutIsIneligible() {
        getMWebPDPage().verifyExpressCheckoutIsNotDisplayed();
    }

    @Then("verify that Buy Now button is displayed")
    public void verifyThatBuyNowButtonIsDisplayed() {
        getMWebPDPage().verifyBuyNowButtonIsDisplayed();
    }

    @And("click on Buy Now button and verify Express checkout is eligible")
    public void clickOnBuyNowButtonAndVerifyExpressCheckoutIsEligible() {
        getMWebPDPage().clickBuyNowButton();
        getMWebPDPage().verifyExpressCheckoutIsDisplayed();
    }

    @Then("User Click on place order button without selecting the checkbox")
    public void userClickOnPlaceOrderButtonWithoutSelectingTheCheckbox() {
        getMWebPaymentPage().clickOnPlaceOrderButton();
    }

    @And("Reload to SPCO URL")
    public void reloadToSPCOURL() {
        getMWebPaymentPage().reloadToSPCO_Url();
    }

    @Then("Verify alternate payment options messages are not be displayed")
    public void verifyAlternatePaymentOptionsMessagesAreNotBeDisplayed() {
        getMWebPaymentPage().alternatePaymentOptionMessageNotDisplayed();
    }

    @Then("verify the Age Verification Section displayed for Fire Ammo Item")
    public void verifyAgeVerificationSectionOnPaymentPage() {
        getMWebPaymentPage().verifyAgeVerificationSection();
    }

    @Given("make test to fail")
    public void makeTestToFail() {
        getMWebHomePage().verifyTitle("Some Random Title");
    }
}
