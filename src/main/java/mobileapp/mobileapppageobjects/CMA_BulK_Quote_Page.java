package mobileapp.mobileapppageobjects;

import genericwrappers.ActionType;
import genericwrappers.GenericWrapper;
import genericwrappers.LocatorType;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class CMA_BulK_Quote_Page extends GenericWrapper {

    public CMA_BulK_Quote_Page(WebDriver driver) {
        super(driver); // Pass the WebDriver instance to the GenericWrapper constructor
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//android.widget.EditText[contains(@content-desc, 'Email Edit Box')]")
    WebElement EMAIL_FIELD;

    @FindBy(xpath = "//android.widget.EditText[contains(@content-desc, 'Password Edit Box')]")
    WebElement PASSWORD_FIELD;

    @FindBy(xpath = "//android.widget.TextView[@text='SIGN IN']")
    WebElement LOGIN_BUTTON;

    @FindBy(xpath = "//android.widget.TextView[@text='Phone Level Enablement']")
    WebElement PHONE_LEVEL_BIOMETRIC_ENABLEMENT_ALERT;

    @FindBy(xpath = "//android.widget.Button[@text='OK']")
    WebElement PHONE_LEVEL_ENABLEMENT_OK_BUTTON;

    @FindBy(xpath = "//android.widget.TextView[@text='Allow Tractor Supply to send you notifications?']")
    WebElement NOTIFICATION_ALERT_TEXT;

    @FindBy(xpath = "//android.widget.Button[@text='Allow']")
    WebElement ALLOW_NOTIFICATION_BUTTON;

    @FindBy(xpath = "//android.widget.ImageView[@content-desc='Tractor supply Co logo']")
    WebElement TRACTOR_SUPPLY_CO_LOGO;

    @FindBy(xpath = "//android.view.ViewGroup[contains(@content-desc,'cart button')]")
    WebElement CART_BUTTON;

    @FindBy(xpath = "//android.widget.EditText[@content-desc='Search Edit Box']/ancestor::android.view.ViewGroup[2]")
    WebElement SEARCH_TEXTBOX;

    @FindBy(xpath = "//android.widget.EditText[@resource-id='TextInput']")
    WebElement TEXTINPUT_TEXTBOX;

    // Define elements using @FindBy annotation
    @FindBy(xpath = "//android.widget.TextView[contains(@text, 'SKU:')]")
    WebElement SKU_ID_LABEL;

    @FindBy(xpath = "//android.view.View[contains(@content-desc, 'Quantity Edit Box')]")
    WebElement QUANTITY_TEXTBOX;

    @FindBy(xpath = "//android.view.ViewGroup[@content-desc='Request a quote']\n")
    WebElement REQUEST_QUOTE_LINK;

    @FindBy(xpath = "//android.widget.TextView[@text='Quote Request']")
    WebElement QUOTE_REQUEST_LABEL;

    @FindBy(xpath = "//android.view.ViewGroup[@content-desc='Delivery']")
    WebElement DELIVERY_OPTION;

    @FindBy(xpath = "//android.view.ViewGroup[@content-desc='Pickup']")
    WebElement PICKUP_OPTION;

    WebElement ZIP_CODE_TEXTBOX;

    @FindBy(xpath = "//android.widget.TextView[@text='Address Verification']")
    WebElement POP_UP_ADDRESS_VERIFICATION;

    @FindBy(xpath = "//android.widget.Button[@content-desc='Use Suggested Address']")
    WebElement USE_SUGGESTED_ADDRESS_BUTTON;

    @FindBy(xpath = "//android.view.ViewGroup[@content-desc='Select all that apply']")
    WebElement EQUIPMENT_TYPE_DROPDOWN;

    @FindBy(xpath = "//android.widget.CheckBox[@content-desc='Loading Dock']")
    WebElement EQUIPMENT_TYPE_LOADING_DOCK_OPTION;

    @FindBy(xpath = "//android.widget.CheckBox[@content-desc='Pallet Jack']")
    WebElement EQUIPMENT_TYPE_PALLET_JACK_OPTION;

    @FindBy(xpath = "//android.widget.CheckBox[@content-desc='Fork Lift']")
    WebElement EQUIPMENT_TYPE_FORK_LIFT_OPTION;

    @FindBy(xpath = "//android.widget.TextView[@text='Additional information or special instructions']//following-sibling::android.view.ViewGroup//android.widget.EditText")
    WebElement ADDITIONAL_INFORMATION_TEXTAREA;

    @FindBy(xpath = "//android.widget.Button[@content-desc='Submit']")
    WebElement SUBMIT_BUTTON;

    @FindBy(xpath = "//android.widget.TextView[@text='Submitting your request...']")
    WebElement SUBMISSION_LOADER;

    @FindBy(xpath = "//android.widget.TextView[@text='Your request has been submitted.']")
    WebElement SUBMISSION_SUCCESSFUL_POP_UP;


    // Maps for field elements, error messages, and field handlers
    private final Map<String, WebElement> fieldMap = new HashMap<>();
    private final Map<String, WebElement> errorMessageElements = new HashMap<>();
    private final Map<String, Consumer<String>> fieldHandlers = new HashMap<>();

    private void handlePhoneBiometricAlert() {
        waitForElementVisible(LocatorType.TEXT, "Phone Level Enablement");
        if (isElementDisplayed(PHONE_LEVEL_BIOMETRIC_ENABLEMENT_ALERT, "Phone Level Biometric Enablement Alert")) {
            clickByWebelement(PHONE_LEVEL_ENABLEMENT_OK_BUTTON, "Handled Phone Level Biometric Enablement alert");
        }
    }

    private void handleNotificationAlert() {
        waitForElementVisible(LocatorType.TEXT, "Allow Tractor Supply to send you notifications?");
        if (isElementDisplayed(NOTIFICATION_ALERT_TEXT, "Notification Alert")) {
            clickByWebelement(ALLOW_NOTIFICATION_BUTTON, "Allowed notifications");
        }
    }

    public void login(String email, String password) throws Exception {
        // Enter email and password, then click login
        enterByWebelement(EMAIL_FIELD, email, "Entering email address");
        enterByWebelement(PASSWORD_FIELD, password, "Entering password");
        clickByWebelement(LOGIN_BUTTON, "Clicking on Login Button");

        // Handle alerts before entering login details
        handlePhoneBiometricAlert();
        handleNotificationAlert();
    }

    public void isHomePageDisplayed() {
        waitForElementVisible(LocatorType.CONTENT_DESC, "Tractor supply Co logo");
        isElementDisplayed(TRACTOR_SUPPLY_CO_LOGO, "Tractor supply Co logo");
    }

    public void enterSKUValueAndSearch(String skuId) throws InterruptedException {
        //waitForElementVisible(LocatorType.TEXT, "Search Edit Box");
        clickByWebelement(SEARCH_TEXTBOX, "Clicked on Text Input Box");
        waitForElementVisible(LocatorType.RESOURCEID, "TextInput");
        enterByWebelement(TEXTINPUT_TEXTBOX, skuId, "Entered SKU value");
        pressKey(TEXTINPUT_TEXTBOX, Keys.ENTER);
    }

    public void verifySKUIdDisplayed() {
        waitForElementVisible(LocatorType.TEXT,"SKU:");
        isElementDisplayed(SKU_ID_LABEL, "SKU ID");
    }

    public void enterValidQuantity(String quantity) {
        scrollToElementAndPerformAction(LocatorType.CONTENT_DESC, "Quantity Edit Box", ActionType.SET_TEXT,quantity);
        pressKey(QUANTITY_TEXTBOX, Keys.TAB);
    }

    public void clickRequestQuoteLink() {
        scrollToElementAndPerformAction(LocatorType.CONTENT_DESC, "Request a quote",ActionType.CLICK,"Request Quote Link");
    }

    /**
     * Verifies that the Quote Request label is displayed on the page.
     */
    public void verifyQuoteRequestLabelDisplayed() {
        waitForElementVisible(LocatorType.TEXT, "Quote Request");
        scrollToElementAndPerformAction(LocatorType.TEXT, "Quote Request", ActionType.SCROLL, "Quote Request Label");
        isElementDisplayed(QUOTE_REQUEST_LABEL, "Quote Request Label");
    }

    /**
     * Enters the provided value into the specified form field.
     *
     * @param field The name of the field to be filled.
     * @param value The value to be entered.
     */
    public void enterFormField(String field, String value) {
        Optional.ofNullable(fieldHandlers.get(field))
                .ifPresentOrElse(
                        handler -> {
                            handler.accept(value);
                            logReport(String.format("Processed field: %s with value: %s", field, value), "INFO");
                        },
                        () -> {
                            String errorMsg = "Invalid field: " + field;
                            logReport(errorMsg, "ERROR");
                            throw new IllegalArgumentException(errorMsg);
                        }
                );
    }


    /**
     * Selects the delivery option from the available options.
     *
     * @param deliveryOption The delivery option to select.
     */
    public void selectDeliveryOption(String deliveryOption) {
        WebElement optionElement = switch (deliveryOption.toLowerCase()) {
            case "delivery" -> DELIVERY_OPTION;
            case "pickup" -> PICKUP_OPTION;
            default -> throw new IllegalArgumentException("Invalid option: " + deliveryOption);
        };
        clickByWebelement(optionElement, "Selected " + deliveryOption + " Option");
        // scrollToElement(LocatorType.TEXT, "Additional information or special instructions");
    }


    /**
     * Completes the address verification process by either using the suggested address or activating the zip code field.
     * Retries if the address verification pop-up is not immediately displayed.
     */
    public void completeAddressVerification() {
        int maxRetries = 2;  // Number of retries
        int retryCount = 0;  // Current retry count

        while (retryCount <= maxRetries) {
            try {
                // Wait for the address verification text to be visible
                waitForElementVisible(LocatorType.TEXT, "Address Verification");

                if (isElementDisplayed(POP_UP_ADDRESS_VERIFICATION, "Address Verification Pop-Up")) {
                    // Click the "Use Suggested Address" button
                    clickByWebelement(USE_SUGGESTED_ADDRESS_BUTTON, "Use Suggested Address Button");
                    return;  // Exit method after successfully clicking the button
                } else {
                    // Log an error if the address verification pop-up is not displayed
                    logReport("Address Verification Pop-Up not displayed", "ERROR");
                    throw new RuntimeException("Address Verification Pop-Up not displayed");
                }
            } catch (Exception e) {
                // Log the exception if an error occurs
                logReport("Exception occurred: " + e.getMessage(), "ERROR");

                // Increment retry count
                retryCount++;

                // On final retry, press the TAB key if the element is still not visible
                if (retryCount > maxRetries) {
                    pressKey(this.ZIP_CODE_TEXTBOX, Keys.TAB);
                    return;  // Exit method after pressing the TAB key
                }

                // Optionally, wait a short period before retrying
                // Adjust the waiting method as per your needs or remove if not necessary
                waitForElementVisible(LocatorType.TEXT, "Address Verification");
            }
        }
    }

    /**
     * Selects the equipment type from the dropdown.
     *
     * @param equipmentType The equipment type to select.
     */
    public void selectEquipmentType(String equipmentType) {
        // Ignore if equipmentType is null or empty
        if (equipmentType == null || equipmentType.isEmpty()) {
            return; // Exit the method if equipmentType is empty or null
        }

        // Convert equipmentType to lowercase and select the appropriate element
        WebElement equipmentTypeOption = switch (equipmentType.toLowerCase()) {
            case "loading dock" -> EQUIPMENT_TYPE_LOADING_DOCK_OPTION;
            case "pallet jack" -> EQUIPMENT_TYPE_PALLET_JACK_OPTION;
            case "fork lift" -> EQUIPMENT_TYPE_FORK_LIFT_OPTION;
            default -> throw new IllegalArgumentException("Invalid equipment type: " + equipmentType);
        };

        // Select the option from the dropdown
        selectOptionFromDropDown(EQUIPMENT_TYPE_DROPDOWN, equipmentTypeOption, "Equipment Type", equipmentType);
    }

    /**
     * Enters additional information into the provided textarea.
     *
     * @param info The additional information to enter.
     */
    public void enterAdditionalInformation(String info) {
        scrollToElementAndPerformAction(LocatorType.TEXT, "Additional information or special instructions", ActionType.SCROLL, info);
        enterByWebelement(ADDITIONAL_INFORMATION_TEXTAREA, info, "Entered Additional Information");
        //pressKey(ADDITIONAL_INFORMATION_TEXTAREA, Keys.TAB);
    }

    /**
     * Clicks the submit button to submit the quote request form.
     */
    public void clickSubmitButton() {
        scrollToElementAndPerformAction(LocatorType.TEXT, "Submit", ActionType.SCROLL, "Submit");
        clickByWebelement(SUBMIT_BUTTON, "Submit Button");
    }

    /**
     * Retrieves the description from the submission loader.
     *
     * @return The loader description text.
     */
    public String getLoaderDescription() {
        return getTextByWebElement(SUBMISSION_LOADER);
    }

    /**
     * Checks if the submission loader is displayed.
     *
     * @return True if the loader is displayed, otherwise false.
     */
    public boolean isLoaderDescriptionDisplayed() {
        return isElementDisplayed(SUBMISSION_LOADER, "Submission loader");
    }

    /**
     * Checks if the submission successful popup is displayed.
     *
     * @return True if the popup is displayed, otherwise false.
     */
    public boolean isSubmissionSuccessfulPopUpDisplayed() {
        waitForElementVisible(LocatorType.TEXT, "Your request has been submitted.");
        return isElementDisplayed(SUBMISSION_SUCCESSFUL_POP_UP, "Submission Successful Pop-Up");
    }


}
