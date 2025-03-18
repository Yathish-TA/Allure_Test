package mobileweb.mobilewebpageobjects.mweb;

import genericwrappers.GenericWrapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

public class MobileWebFulfillmentPage extends GenericWrapper {

    public MobileWebFulfillmentPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//label[contains(text(),'Email Address*')]/following-sibling::input")
    WebElement EMAIL_INPUT_FIELD;

    @FindBy(xpath = "//label[contains(text(),'Phone Number*')]/following-sibling::input")
    WebElement PHONE_INPUT_FIELD;

    @FindBy(xpath = "//label[contains(text(),'First Name*')]/following-sibling::input")
    WebElement FIRSTNAME_INPUT_FIELD;

    @FindBy(xpath = "//label[contains(text(),'Last Name*')]/following-sibling::input")
    WebElement LAST_NAME_INPUT_FIELD;

    @FindBy(xpath = "//label[contains(text(),'Street Address*')]/following-sibling::input")
    WebElement ADDRESS_INPUT_FIELD;

    @FindBy(xpath = "//label[contains(text(),'Zip*')]/following-sibling::input")
    WebElement ZIP_INPUT_FIELD;

    @FindBy(xpath = "//label[contains(text(),'City*')]/following-sibling::input")
    WebElement CITY_INPUT_FIELD;

    @FindBy(xpath = "//label[contains(text(),'State*')]/following-sibling::select")
    WebElement STATE_SELECT;

    @FindBy(css = "#cart-sticky #secure_checkout")
    WebElement CONTINUE_TO_PAYMENT_BUTTON;

    @FindBy(xpath ="//a[@onclick=\"CheckoutActionsJS.showEditAndAddAddressModal('addNewAddress');\"]")
    WebElement ADD_NEW_ADDRESS;

    @FindBy(css = "#cart-sticky #secure_checkout")
    WebElement CONTINUE_TO_PAYMENT_BUTTON_ATTRIBUTE;

    @FindBy(css = ".address-button.address-choice #avs_select_sugg")
    WebElement USE_THIS_DELIVERY_ADDRESS_BUTTON;

    @FindBy(css = "#secure_checkout")
    WebElement CHECKOUT_PAGE;

    @FindBy(css = "#subtotal_value")
    WebElement SUBTOTAL_TEXT;

    @FindBy(css = ".red.account_err_message.font-bold")
    WebElement RESTRICTED_STATE_ERROR_MESSAGE;

    /**
     * If delivery address is available already then this function does not provide inputs as the fields itself are not available
     */
    public void enterDeliveryInformation(List<String> deliverAddress){
        scrolldownByPixcel();
        if(!waitUntil(()->CONTINUE_TO_PAYMENT_BUTTON_ATTRIBUTE.getAttribute("class").equalsIgnoreCase("green"), 2)){
            if(waitUntil(()-> ZIP_INPUT_FIELD.isDisplayed(), 2)) {
                enterByWebelement((ZIP_INPUT_FIELD), deliverAddress.get(3).trim(), "Entering the Zip");
                inputText((CITY_INPUT_FIELD), deliverAddress.get(1), "Entering the city");
                selectByValueByWebElement((STATE_SELECT), deliverAddress.get(2));
            }
            if(waitUntil(()-> ADDRESS_INPUT_FIELD.isDisplayed(), 1)){
                inputText((ADDRESS_INPUT_FIELD), deliverAddress.get(0), "Entering the street address");
            }
            if(waitUntil(()-> LAST_NAME_INPUT_FIELD.isDisplayed(), 1)){
                inputText((LAST_NAME_INPUT_FIELD), RandomStringUtils.randomAlphabetic(8), "Entering the Last name");
            }
            if(waitUntil(()-> EMAIL_INPUT_FIELD.isDisplayed(), 1)){
                inputText((EMAIL_INPUT_FIELD), RandomStringUtils.randomAlphanumeric(15) + "@gmail.com", "Entering the email");
            }
            if(waitUntil(()-> PHONE_INPUT_FIELD.isDisplayed(), 1)){
                inputText((PHONE_INPUT_FIELD), "89" + RandomStringUtils.randomNumeric(8), "Entering the phone number");
            }
            if(waitUntil(()-> FIRSTNAME_INPUT_FIELD.isDisplayed(), 1)){
                inputText((FIRSTNAME_INPUT_FIELD), RandomStringUtils.randomAlphabetic(8), "Entering the first name");
            }
            try{
                SUBTOTAL_TEXT.click();
            }catch(Exception ignore){}
        }
    }

    public void clickOnContinueToPaymentButton(){
        waitUntil(()-> CONTINUE_TO_PAYMENT_BUTTON_ATTRIBUTE.getAttribute("class").equalsIgnoreCase("green"));
        scrollAndClick(CONTINUE_TO_PAYMENT_BUTTON_ATTRIBUTE, "Click on continue to payment button");
        //selectAddress();
    }

    public void selectAddress(){
        if(waitUntil(()-> USE_THIS_DELIVERY_ADDRESS_BUTTON.isDisplayed(), 3))
            clickByWebelement(waitForElementToBeInteractable(USE_THIS_DELIVERY_ADDRESS_BUTTON), "Clicking on use this address suggestion");
    }

    public void clickCheckOutPage(){
        scrollAndClick(CHECKOUT_PAGE,"Clicking On Checkout Page");
    }

    public void clickOnAddNewAddressButton(){
        scrollAndClick(ADD_NEW_ADDRESS, "Clicking on Add New Address Button");
    }

    public void editBillingAddress(List<String> address){
        enterDeliveryInformation(address);
    }

    public void verifyErrorMessageToPreventZipChange(){
        Assert.assertTrue(verifyExpectedAndActualTextOfElement(RESTRICTED_STATE_ERROR_MESSAGE, "Your cart contains item that cannot be shipped to",
                "Verify error message to prevent zip change", false));
    }
}
