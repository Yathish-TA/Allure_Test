package mobileweb.mobilewebpageobjects.mweb;

import genericwrappers.GenericWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

public class MobileWebPaymentPage extends GenericWrapper {

    public MobileWebPaymentPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "credit_num_txtbox")
    WebElement CREDIT_CARD_NUMBER_INPUT_FIELD;

    @FindBy(css = "div[class='cvv'] input[name='cc_cvc_1']")
    WebElement CVV_INPUT_FIELD;

    @FindBy(css = "select[name='expire_month']")
    WebElement EXPIRE_MONTH_SELECT;

    @FindBy(css = "select[name='expire_year']")
    WebElement EXPIRE_YEAR_SELECT;

    @FindBy(xpath = "//span[@class='checkmark ammo_check']")
    WebElement I_ACCEPT_CHECK_BOX;

    @FindBy(xpath = "//span[contains(text(),'Ammunition Disclaimer')]")
    WebElement AGE_RESTRICTION_DISCLAIMER;

    @FindBy(id = "payment_continueBtn2")
    WebElement PLACE_ORDER_BUTTON;

    @FindBy(css = ".error-txt")
    WebElement AGE_ACKNOWLEDGE_ERROR_MESSAGE;

    @FindBy(css = "#accountErrMsg")
    WebElement PAYMENT_ERROR_MESSAGE;

    @FindBy(xpath = "(//span[contains(text(),'Continue to Payment')])[2]")
    WebElement CONTINUE_TO_PAYMENT;

    @FindBy(id = "payment-options-display")
    WebElement ALTERNATE_PAYMENT;

    @FindBy(id = "checkoutAmmoItem")
    WebElement AMMO_AGE_VERIFICATION_SECTION;

    public void enterCreditCardDetails(List<String> ccDetails){
        enterByWebelement(waitForElementToBeInteractable(CREDIT_CARD_NUMBER_INPUT_FIELD),ccDetails.get(0), "Entering the CC number");
        enterByWebelement(waitForElementToBeInteractable(CVV_INPUT_FIELD),ccDetails.get(1), "Entering the cvv");
        selectByValueByWebElement(EXPIRE_MONTH_SELECT, ccDetails.get(2));
        selectByValueByWebElement(EXPIRE_YEAR_SELECT, ccDetails.get(3));
    }

    public void verifyAgeRestrictionDisclaimer(){
        Assert.assertTrue(verifyExpectedAndActualTextOfElement(AGE_RESTRICTION_DISCLAIMER, "Ammunition Disclaimer", "Verify age restriction disclaimer"));
    }

    public void verifyAgeRestrictionDisclaimerIsNotDisplayed(){
        Assert.assertTrue(verifyExpectedElementIsPresent(AGE_RESTRICTION_DISCLAIMER, false, "Age verification disclaimer is not displayed"),
                "Age verification disclaimer is displayed for non anno items");
    }


    public void clickOnIAcceptCheckBox(){
        scrollAndClick(I_ACCEPT_CHECK_BOX, "Click on I Accept checkbox");
    }

    public void clickOnPlaceOrderButton(){
        waitUntil(()->getAttributeValue(PLACE_ORDER_BUTTON, "class").contains("green"));
        scrollAndClick(PLACE_ORDER_BUTTON, "Clicking on place order button");
    }

    public void verifyAgeAcknowledgeErrorMessage(){
        Assert.assertTrue(verifyExpectedAndActualTextOfElement(AGE_ACKNOWLEDGE_ERROR_MESSAGE, "Please acknowledge that you are at least 18 years or older to proceed", "Verify age restriction error message", false));
    }

    public void verifyPaymentErrorMessage(){
        scrollToElement(PAYMENT_ERROR_MESSAGE);
        Assert.assertTrue(verifyExpectedAndActualTextOfElement(PAYMENT_ERROR_MESSAGE, "Please enter a valid credit card number.", "Verify payment error", false));
    }

    public void clickContinueToPayment(){
        scrollAndClick(CONTINUE_TO_PAYMENT,"Click on Continue to Payment");
    }

    public void selectIAcceptCheckBox(){
        Assert.assertTrue(verifyExpectedElementIsPresent(I_ACCEPT_CHECK_BOX, true,"Verify Select I Accept Check Box"));
        clickOnIAcceptCheckBox();
    }

    public void verifyAgeVerificationSection() {
        Assert.assertTrue(AMMO_AGE_VERIFICATION_SECTION.isDisplayed());
    }

    public void reloadToSPCO_Url() {
        waitUntil(()-> driver.getCurrentUrl().contains("Payment"), 10);
        String[] currentUrl = driver.getCurrentUrl().split("\\?");
        driver.get(currentUrl[0].split("/TSC")[0]+"/TSCSinglePageCheckoutView?"+currentUrl[1]);
        System.out.println(currentUrl[0].split("/TSC")[0]+"/TSCSinglePageCheckoutView?"+currentUrl[1]);
    }

    public void alternatePaymentOptionMessageNotDisplayed() {
        Assert.assertTrue(verifyExpectedElementIsPresent(ALTERNATE_PAYMENT, false, "Verify payment option is not displayed"));
    }
}
