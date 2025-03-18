package mobileapp.mobileapppageobjects;

import database.DBC;
import genericwrappers.ActionType;
import genericwrappers.GenericWrapper;
import genericwrappers.LocatorType;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.qameta.allure.Allure;
import mobileutils.PageObjectBaseClassMobile;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;
import webutils.LoadProperties;

import java.util.Objects;

import static azurevault.VaultData.getVaultData;

public class LoginPage extends GenericWrapper {
    private static String deviceType;

    public SoftAssert softAssert = new SoftAssert();
    public PageObjectBaseClassMobile getData = new PageObjectBaseClassMobile();
    DBC database = new DBC();
    String AESKey = "AES_key";

    public LoginPage(WebDriver driver,String deviceType) {
        super(driver); // Pass the WebDriver instance to the GenericWrapper constructor
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        LoginPage.deviceType = deviceType;
    }

    @FindBy(xpath = "//button[contains(text(),'Copy')]")
    WebElement COPY;

    @FindBy(id = "home-email")
    WebElement FAKE_EMAIL;

    @FindBy(xpath = "//span[@id='domain']")
    WebElement FAKE_DOMAIN;

    @FindBy(xpath = "//button[@id='userButton']")
    WebElement LOGIN_USER_BUTTON;

    @FindBy(xpath = "//input[@name='cp_email-id']")
    WebElement EMAIL_FIELD;

    @FindBy(xpath = "//input[@name='cp_password']")
    WebElement PASSWORD_FIELD;

    @AndroidFindBy(xpath = "//android.widget.EditText[contains(@content-desc, 'Email Edit Box')]")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeTextField[@name='Email Edit Box ']")
    WebElement CMA_EMAIL_FIELD_ANDROID;

    @AndroidFindBy(xpath = "//android.widget.EditText[contains(@content-desc, 'Password Edit Box')]")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeSecureTextField[@name='Password Edit Box ']")
    WebElement CMA_PASSWORD_FIELD_ANDROID;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='SIGN IN']")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='Sign in']")
    WebElement LOGIN_BUTTON_ANDROID;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@resource-id='LOGIN_BUTTON']")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name='LOGIN_BUTTON']")
    WebElement LOGIN_BUTTON;

    @FindBy(xpath = "//XCUIElementTypeTextField[@name=\"Email Edit Box \"]")
    WebElement CMA_EMAIL_FIELD_IOS;

    @FindBy(xpath = "//XCUIElementTypeSecureTextField[@name=\"Password Edit Box \"]")
    WebElement CMA_PASSWORD_FIELD_IOS;

    @FindBy(xpath = "//XCUIElementTypeOther[@name=\"LOGIN_BUTTON\"]")
    WebElement LOGIN_BUTTON_IOS;

    @AndroidFindBy(xpath = "//*[contains(@text,'SKIP')]")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name='SKIP_BUTTON']")
    WebElement SKIP_BUTTON;

    @FindBy(xpath = "//*[contains(@name, \"SKIP_BUTTON\")]")
    WebElement SKIP_BUTTON_IOS;

    @FindBy(id = "android:id/button1")
    WebElement PHONE_LEVEL_ENABLEMENT_OK_BUTTON_ANDROID;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Allow Tractor Supply to access this device’s location?']")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Allow “Tractor Supply” to use your location?\"]")
    public WebElement DEVICE_LOCATION_ALERT_TEXT;

    @FindBy(xpath = "//XCUIElementTypeButton[@name=\"OK\"]")
    WebElement PHONE_LEVEL_ENABLEMENT_OK_BUTTON_IOS;

    @AndroidFindBy(xpath = "//android.widget.Button[@text='While using the app']")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Allow While Using App\"]")
    public WebElement ALLOW_DEVICE_LOCATION_BUTTON;

    @FindBy(xpath = "//XCUIElementTypeButton[@name=\"Allow Once\"]")
    WebElement ALLOW_MAPS_IOS;

    @FindBy(xpath = "//button[@id='cp_sign-in-button']")
    WebElement SIGNIN_BUTTON;

    @FindBy(xpath = "//div[@class='session-error']/span")
    WebElement SIGNIN_ERROR_MSG;

    @FindBy(xpath = "//form[@id='Signin']/div[3]/a")
    WebElement FORGOT_PASSWORD_LINK;

    @FindBy(xpath = "//button[@id='WC_PasswordResetForm_Link_2']")
    WebElement CHANGE_PASSWORD_BUTTON;

    @AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc='Tractor supply Co logo']")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeImage[@name=\"assets/app/resources/Images/appLogo/TSC_home_logo@3x.png\"]")
    WebElement TRACTOR_SUPPLY_CO_LOGO;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Sign in with Google']")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name='GOOGLE_LOGIN_BUTTON']")
    WebElement GOOGLE_SIGN_IN;

    @AndroidFindBy(xpath = "//android.widget.Button[@text='Next']")
    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeButton[@name='Next'])[1]")
    WebElement GOOGLE_NEXT_BUTTON;

    @FindBy(xpath = "//XCUIElementTypeOther[@name=\"GOOGLE_LOGIN_BUTTON\"]")
    WebElement GOOGLE_SIGNIN_IOS;

    @FindBy(xpath = "//android.view.ViewGroup[@resource-id='switchLoginView']")
    WebElement SIGN_IN_WITH_PASSWORD;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='Sign In with One-Time Passcode']")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@label='Sign In with One-Time Passcode']")
    WebElement SIGN_IN_WITH_OTP;

    @AndroidFindBy(xpath = "//android.widget.EditText[@resource-id=\"identifierId\"]")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='Forgot email?']/preceding::*[contains(@label,'Email or phone')]")
    WebElement GOOGLE_EMAIL;

    @FindBy(xpath = "//android.widget.ImageView[@content-desc=\"TSC For Life Out Here Logo\"]")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name=\"TSC For Life Out Here Logo\"]")
    WebElement TSC_SIGNIN;

    @AndroidFindBy(xpath = "//android.widget.EditText")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeSecureTextField[@name=\"Enter your password\"]")
    WebElement GOOGLE_EMAIL_PASSWORD_ANDROID;

    @FindBy(xpath = "//android.widget.Button[@text=\"Next\"]")
    WebElement NEXT_ANDROID;

    @FindBy(xpath = "//XCUIElementTypeTextField[@name=\"Email or phone\"]")
    WebElement GOOGLE_EMAIL_IOS;

    @FindBy(xpath = "//XCUIElementTypeSecureTextField[@name=\"Enter your password\"]")
    WebElement GOOGLE_EMAIL_PASSWORD_IOS;

    @FindBy(xpath = "//XCUIElementTypeButton[@name=\"Next\"]")
    WebElement NEXT_IOS;

    @FindBy(xpath = "//android.widget.Button[@text=\"I agree\"]")
    WebElement IAGREE;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='Continue']")
    WebElement CONTINUE_IOS;

    @FindBy(xpath = "//android.widget.TextView[@text=\"CREATE ACCOUNT\"]")
    WebElement CREATEACCOUNT_SHOPPINGCART_ANDROID;

    @FindBy(xpath = "//android.widget.TextView[@text=\"CREATE NEW ACCOUNT\"]")
    WebElement CREATENEWACCOUNT_ANDROID;

    @FindBy(xpath = "//XCUIElementTypeOther[@name=\"CREATE_ACCOUNT_BUTTON\"]")
    WebElement CREATENEWACCOUNT_IOS;

    @FindBy(xpath = "//android.widget.TextView[@text=\"Verify Password*\"]")
    WebElement VERIFY_PASSWORD_ANDROID;

    @FindBy(xpath = "//XCUIElementTypeSecureTextField[@name=\"Verify Password, Required, secure  Edit Box \"]")
    WebElement VERIFY_PASSWORD_IOS;

    @FindBy(xpath = "//android.widget.EditText[@content-desc=\"Password, Required, secure  Edit Box \"]")
    WebElement PASSWORD_FILED_ANDROID;

    @FindBy(xpath = "//XCUIElementTypeSecureTextField[@name=\"Password, Required, secure  Edit Box \"]")
    WebElement PASSWORD_FILED_IOS;

    @FindBy(xpath = "//android.widget.EditText[@content-desc=\"Verify Password, Required, secure  Edit Box \"]")
    WebElement VERIFY_PASSWORD_FILED_ANDROID;

    @FindBy(xpath = "//XCUIElementTypeSecureTextField[@name=\"Verify Password, Required, secure  Edit Box \"]")
    WebElement VERIFY_PASSWORD_FILED_IOS;

    @FindBy(xpath = "//android.widget.TextView[@content-desc=\"Password Strength: Very Weak\"]")
    WebElement PASSWORD_STRENGTH1_ANDROID;

    @FindBy(xpath = "(//XCUIElementTypeStaticText[@name=\"Password Strength: Very Weak\"])[1]")
    WebElement PASSWORD_STRENGTH1_IOS;

    @FindBy(xpath = "//android.widget.TextView[@content-desc=\"Password Strength: Medium\"]")
    WebElement PASSWORD_STRENGTH2_ANDROID;

    @FindBy(xpath = "(//XCUIElementTypeStaticText[@name=\"Password Strength: Medium\"])[1]")
    WebElement PASSWORD_STRENGTH2_IOS;

    @FindBy(xpath = "//android.widget.TextView[@content-desc=\"Password Strength: Very Strong\"]")
    WebElement PASSWORD_STRENGTH3_ANDROID;

    @FindBy(xpath = "(//XCUIElementTypeStaticText[@name=\"Password Strength: Very Strong\"])[1]")
    WebElement PASSWORD_STRENGTH3_IOS;

    @FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"Password Meter\"]")
    WebElement STRENGTH_METER_ANDROID;

    @FindBy(xpath = "//XCUIElementTypeOther[@name=\"Password Meter\"]")
    WebElement STRENGTH_METER_IOS;

    @FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"Passed\"]")
    WebElement CONDTIONALCHECKPASSED_ANDROID;

    @FindBy(xpath = "//XCUIElementTypeOther[@name=\"Passed\"]")
    WebElement CONDTIONALCHECKPASSED_IOS;

    @FindBy(xpath = "(//android.view.ViewGroup[@content-desc=\"Passed\"])[1]")
    WebElement CONDTIONALCHECKPASSED1_ANDROID;

    @FindBy(xpath = "(//XCUIElementTypeOther[@name=\"Passed\"])[1]")
    WebElement CONDTIONALCHECKPASSED1_IOS;

    @FindBy(xpath = "(//android.view.ViewGroup[@content-desc=\"Passed\"])[2]")
    WebElement CONDTIONALCHECKPASSED2_ANDROID;

    @FindBy(xpath = "(//XCUIElementTypeOther[@name=\"Passed\"])[2]")
    WebElement CONDTIONALCHECKPASSED2_IOS;

    @FindBy(xpath = "(//android.view.ViewGroup[@content-desc=\"Passed\"])[3]")
    WebElement CONDTIONALCHECKPASSED3_ANDROID;

    @FindBy(xpath = "(//XCUIElementTypeOther[@name=\"Passed\"])[3]")
    WebElement CONDTIONALCHECKPASSED3_IOS;

    @FindBy(xpath = "(//android.view.ViewGroup[@content-desc=\"Passed\"])[4]")
    WebElement CONDTIONALCHECKPASSED4_ANDROID;

    @FindBy(xpath = "(//XCUIElementTypeOther[@name=\"Passed\"])[4]")
    WebElement CONDTIONALCHECKPASSED4_IOS;

    @FindBy(xpath = "(//android.view.ViewGroup[@content-desc=\"Failed\"])[1]")
    WebElement CONDTIONALCHECKFAILED1_ANDROID;

    @FindBy(xpath = "(//XCUIElementTypeOther[@name=\"Failed\"])[1]")
    WebElement CONDTIONALCHECKFAILED1_IOS;

    @FindBy(xpath = "(//android.view.ViewGroup[@content-desc=\"Failed\"])[2]")
    WebElement CONDTIONALCHECKFAILED2_ANDROID;

    @FindBy(xpath = "(//XCUIElementTypeOther[@name=\"Failed\"])[2]")
    WebElement CONDTIONALCHECKFAILED2_IOS;

    @FindBy(xpath = "(//android.view.ViewGroup[@content-desc=\"Failed\"])[3]")
    WebElement CONDTIONALCHECKFAILED3_ANDROID;

    @FindBy(xpath = "(//XCUIElementTypeOther[@name=\"Failed\"])[3]")
    WebElement CONDTIONALCHECKFAILED3_IOS;

    @FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"Back button\"]")
    WebElement BACKBUTTON_ANDROID;

    @FindBy(xpath = "//XCUIElementTypeOther[@name=\"Back button\"]")
    WebElement BACKBUTTON_IOS;

    @FindBy(xpath = "//android.widget.TextView[@text=\"JOIN NOW\"]")
    WebElement JOINNOWBUTTON_ANDROID;

    @FindBy(xpath = "//XCUIElementTypeOther[@name=\"SUBMIT_BUTTON\"]")
    WebElement JOINNOWBUTTON_IOS;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc=\"Close\"]")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Close\"]")
    public WebElement CLOSE_BUTTON;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Account Created\"]")
    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeStaticText[@name=\"Account Created\"])[2]")
    public WebElement ACCOUNT_CREATED;

    @AndroidFindBy(xpath = "//android.widget.Button[@resource-id=\"android:id/button1\"]")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"OK\"]")
    public WebElement  PHONE_LEVEL_ENABLEMENT_OK_BUTTON;


    @AndroidFindBy(xpath = "//android.view.View[@content-desc=\"Create an Account\"]")
    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeStaticText[@name=\"Create an Account\"])[last()]")
    WebElement CREATE_ACCOUNT_PAGE_VERIFY_ANDROID;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Yes! Enroll me in Neighbor’s Club.\"]")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name=\"NEIGHBOURCLUB_ENROLL\"]")
    WebElement UNCHECK_NC_ENROLL_BUTTON_ANDROID;

    @FindBy(xpath = "//XCUIElementTypeStaticText[@name=\"“Tractor Supply” Would Like to Send You Notifications\"]")
    WebElement TSC_NOTIFICATION_IOS;

    @FindBy(xpath = "//XCUIElementTypeButton[@name=\"Allow\"]")
    WebElement ALLOW_NOTIFICATIONS_IOS;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Cancel\"]")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name='SKIP_BUTTON']")
    WebElement DELIVERY_ZIP_CANCEL;


    /**
     * This function returns GMail account element for an IOS device
     *  (Added recursive strategy, because control would move from app to browser and there might be a chance of unknown exceptions
     *  To handle that exception, we wait for 60 seconds by trying to identify element after 1s of wait time continuously
     *  If no element is identified after 60s, this method would return NULL as an element)
     */
    private int counter = 0;
    private WebElement SELECT_GMAIL_ACCOUNT(String email){
        WebElement ele = null;
        int limit = 60; // MAXIMUM 60 SECS (1s per call)

        if(counter > limit)
            return ele; // WHEN LOOP REACHES THE LIMIT, IT WOULD RETURN THE ELE VALUE

        try {
            if (deviceType.equalsIgnoreCase("IOS")){
                ele = driver.findElements(By.xpath("//XCUIElementTypeLink[contains(@name,'" + email + "')]")).getFirst();
            }
        }catch (Exception e){
            timeOut(1000); //WAIT FOR 1 SEC
            counter++;
            SELECT_GMAIL_ACCOUNT(email); // RECURSIVE CALL
        }
        return ele;
    }

    public void loginAction() {
        clickByWebelement(LOGIN_USER_BUTTON, "Login Page is Loaded");
    }

    public void loginDetails() throws Exception {
        enterByWebelement(EMAIL_FIELD, getData.getDatavalue("Email"), "Passing the Email address as a login credentials");
        String password = getData.getDatavalue("Password");
        System.out.println("Encrypted Pwd = " + password);
//        String decryptedPassword =database.decrypt(password,AESKey);
        enterByWebelement(PASSWORD_FIELD, database.decrypt(password,AESKey), "Entering the Password as a login credentials");
        clickByWebelement(SIGNIN_BUTTON, "Clicking on Sign In");
    }

    public void verifySigninErrorMsg() throws InterruptedException {
        String text = SIGNIN_ERROR_MSG.getText();
        softAssert.assertTrue(SIGNIN_ERROR_MSG.isDisplayed());
    }

    public void clickForgotLink() {
        clickByWebelement(FORGOT_PASSWORD_LINK, "Forgot password link clicked and page loaded");
    }

    public void verifyChangePwdPage() {
        softAssert.assertTrue(CHANGE_PASSWORD_BUTTON.isDisplayed());
    }

    private void handlePhoneBiometricAlert() {
        switch (deviceType) {
            case "ANDROID":
                clickByWebelement(PHONE_LEVEL_ENABLEMENT_OK_BUTTON_ANDROID, "Handled Phone Level Biometric Enablement alert");
                timeOut(5000);
                break;
            case "IOS":
                clickByWebelement(PHONE_LEVEL_ENABLEMENT_OK_BUTTON_IOS, "Handled Phone Level Biometric Enablement alert");
                timeOut(5000);
                break;
        }

    }

    public void handleLocationAccessAlert() {
        switch (deviceType){
            case "ANDROID":
                waitUntil(() ->DEVICE_LOCATION_ALERT_TEXT.isDisplayed(),6);
                if (isElementDisplayed(DEVICE_LOCATION_ALERT_TEXT, "Notification Alert"))
                {
                    clickByWebelement(ALLOW_DEVICE_LOCATION_BUTTON, "Allowed notifications");
                }
                break;
            case "IOS":
                waitUntil(() ->ALLOW_MAPS_IOS.isDisplayed(),6);
                if (isElementDisplayed(ALLOW_MAPS_IOS, "Notification Alert"))
                {
                    clickByWebelement(ALLOW_MAPS_IOS, "Allowed notifications");
                }
                break;
        }
    }

    public void CMALoginDetails(String email, String password)
    {
        switch (deviceType) {
            case "ANDROID":
                enterByWebelement(CMA_EMAIL_FIELD_ANDROID, email, "Entering email address");
                enterByWebelement(CMA_PASSWORD_FIELD_ANDROID, getVaultData(password), "Entering password");
                clickByWebelement(LOGIN_BUTTON_ANDROID, "Clicking on Login Button");
                waitForElement(PHONE_LEVEL_ENABLEMENT_OK_BUTTON_ANDROID,60);
                if(checkIsDisplayed(PHONE_LEVEL_ENABLEMENT_OK_BUTTON_ANDROID)) {
                    webdriverWaitElementToBeVisible(PHONE_LEVEL_ENABLEMENT_OK_BUTTON_ANDROID);
                    handlePhoneBiometricAlert();
                }
                waitForElement(DELIVERY_ZIP_CANCEL,25);
                if(checkIsDisplayed(DELIVERY_ZIP_CANCEL)) {
                    clickByWebelement(DELIVERY_ZIP_CANCEL, "Clicking on Login Button");
                }
                break;
            case "IOS":
                enterByWebelement(CMA_EMAIL_FIELD_IOS, email, "Entering email address");
                enterByWebelement(CMA_PASSWORD_FIELD_IOS, getVaultData(password), "Entering password");
                clickByWebelement(LOGIN_BUTTON_IOS, "Clicking on Login Button");
                waitForElement(PHONE_LEVEL_ENABLEMENT_OK_BUTTON_IOS,60);
                if(checkIsDisplayed(PHONE_LEVEL_ENABLEMENT_OK_BUTTON_IOS)) {
                    webdriverWaitElementToBeVisible(PHONE_LEVEL_ENABLEMENT_OK_BUTTON_IOS);
                    handlePhoneBiometricAlert();
                }
                break;
        }
    }

    public void guestLogin() {
        switch (deviceType) {
            case "ANDROID":
                clickElementWithFluentWait(SKIP_BUTTON, "Click on skip");
                handleLocationAccessAlert();
                waitForElement(DELIVERY_ZIP_CANCEL,25);
                if(checkIsDisplayed(DELIVERY_ZIP_CANCEL)) {
                    clickByWebelement(DELIVERY_ZIP_CANCEL, "Clicking on Login Button");
                }
                break;
            case "IOS":
                clickElementWithFluentWait(SKIP_BUTTON_IOS, "Click on skip");
                clickElementWithFluentWait(ALLOW_MAPS_IOS, "Click on allow once for maps");
                break;
        }
    }

    public void clickOnTheSkipLogin(){
        clickElementWithFluentWait(SKIP_BUTTON, "Click on skip");
    }

    public void ClicksOnSigninbutton() throws Exception {
        webdriverWaitElementToBeVisible(LOGIN_BUTTON_ANDROID);
        clickByWebelement(LOGIN_BUTTON_ANDROID, "Click on Sign In Button");
    }

    public boolean verifyGoogleSignInButtonExists(){
        waitForElementToBeDisplayed(GOOGLE_SIGN_IN);
        return checkIsDisplayed(GOOGLE_SIGN_IN);
    }

    public void enterGoogleEmail(String email){
        clickByWebelement(GOOGLE_EMAIL, "Clicking on the input field");
        enterByWebelement(GOOGLE_EMAIL, email, "Entering email address "+email);
    }

    public void clickOnNextButton() {
        if (waitForElementToBeDisplayed(GOOGLE_NEXT_BUTTON)) {
            clickByWebelement(GOOGLE_NEXT_BUTTON, "Clicking Next button in the google form");
        }
    }

    public void enterGoogleEmailPassword(String password){
        clickByWebelement(GOOGLE_EMAIL_PASSWORD_ANDROID, "Clicking on the input field");
        enterByWebelement(GOOGLE_EMAIL_PASSWORD_ANDROID, getVaultData(password),"Enter Google Email Password");
    }

    public void enterGoogleIAgreeButton(){
        if(!deviceType.equalsIgnoreCase("IOS") && waitForElementToBeDisplayed(IAGREE)) {
            clickByWebelement(IAGREE, "Click on I Agree");
        }else if(waitForElementToBeDisplayed(CONTINUE_IOS)) {
            clickByWebelement(CONTINUE_IOS, "Click on Continue button");
        }
    }

    public void ClicksOnGoogleSignInButton() throws Exception {
        waitForElementToBeDisplayed(GOOGLE_SIGN_IN);
        clickByWebelement(GOOGLE_SIGN_IN, "Click on Google Sign In Button");
    }

    public void ClicksOnSignInWithPasswordButton() {
        if(!waitForElementToBeDisplayed(SIGN_IN_WITH_OTP)){
            webdriverWaitElementToBeVisible(SIGN_IN_WITH_PASSWORD);
            clickByWebelement(SIGN_IN_WITH_PASSWORD, "Click on Sign In with Password Button");
        }
    }

    public void enterGoogleEmailId(String email,String Password ) throws Exception {
        switch (deviceType) {
            case "ANDROID":
                enterByWebelement(GOOGLE_EMAIL, email, "Entering email address");
                webdriverWaitElementToBeVisible(NEXT_ANDROID);
                clickByWebelement(NEXT_ANDROID, "Click on Next Button");
                enterByWebelement(GOOGLE_EMAIL_PASSWORD_ANDROID, getVaultData(Password),"Enter Google Email Password");
                webdriverWaitElementToBeVisible(NEXT_ANDROID);
                clickByWebelement(NEXT_ANDROID, "Click on Next Button");
                break;
            case "IOS":
                waitForElement(GOOGLE_EMAIL_IOS,30);
                clickByWebelement(GOOGLE_EMAIL_IOS, "Click on Email");
                enterByWebelement(GOOGLE_EMAIL_IOS, email, "Entering email address");
                webdriverWaitElementToBeVisible(NEXT_IOS);
                clickByWebelement(NEXT_IOS, "Click on Next Button");
                clickByWebelement(GOOGLE_EMAIL_PASSWORD_IOS, "Click on Password");
                enterByWebelement(GOOGLE_EMAIL_PASSWORD_IOS, getVaultData(Password),"Enter Google Email Password");
                webdriverWaitElementToBeVisible(NEXT_IOS);
                clickByWebelement(NEXT_IOS, "Click on Next Button");
                break;
        }
    }

    public void clicksOnAgreebutton() throws Exception {

        switch (deviceType) {
            case "ANDROID":
                webdriverWaitElementToBeVisible(IAGREE);
                clickByWebelement(IAGREE, "Click on I Agree");
                break;
            case "IOS":
                webdriverWaitElementToBeVisible(CONTINUE_IOS);
                clickByWebelement(CONTINUE_IOS, "Click on Continue");
                break;
        }
    }

    public void createNewAccountbutton() throws Exception {
        switch (deviceType){
            case "ANDROID":
                webdriverWaitElementToBeVisible(CREATENEWACCOUNT_ANDROID);
                clickByWebelement(CREATENEWACCOUNT_ANDROID, "User click on Create New Account button");
                break;
            case "IOS":
                webdriverWaitElementToBeVisible(CREATENEWACCOUNT_IOS);
                clickByWebelement(CREATENEWACCOUNT_IOS, "User click on Create New Account button");
                break;
        }
    }

    public void checkTestBoxVerifyPasswordIsVisible() throws Exception {
        switch (deviceType){
            case "ANDROID":
                scrollToElementAndPerformAction(LocatorType.CONTENT_DESC,"Verify Password*", ActionType.SCROLL);
                isElementDisplayed(VERIFY_PASSWORD_ANDROID, "Verify Password element should display");
                break;
            case "IOS":
                scrollToElementUsingScrollIOS(VERIFY_PASSWORD_IOS);
                isElementDisplayed(VERIFY_PASSWORD_IOS, "Verify Password element should display");
                break;
        }
    }

    public void displayedTscSigninPage() throws Exception {
        waitForElement(TSC_SIGNIN, 10);
        Assert.assertTrue(isElementDisplayed(TSC_SIGNIN, "TSC Sign in Page is displayed"));
    }

    public void enterWrongPassword(String Password ) throws Exception {
        switch (deviceType){
            case "ANDROID":
                enterByWebelement(PASSWORD_FILED_ANDROID, getVaultData(Password),"Enter Wrong Password at Verify Password Filled");
                pressKey(PASSWORD_FILED_ANDROID, Keys.ENTER);
                break;
            case "IOS":
                scrollToElementUsingScrollIOS(JOINNOWBUTTON_IOS);
                enterByWebelement(PASSWORD_FILED_IOS, getVaultData(Password),"Enter Wrong Password at Verify Password Filled");
                KeyBoardEnterIOS();
                break;
        }
    }

    public void displayedPasswordStrength1( ) throws Exception {
        switch (deviceType) {
            case "ANDROID":
                scrollToElementUntilVisible(VERIFY_PASSWORD_ANDROID);
                webdriverWaitElementToBeVisible(PASSWORD_STRENGTH1_ANDROID);
                break;
            case "IOS":
                scrollToElementUsingScrollIOS(JOINNOWBUTTON_IOS);
                webdriverWaitElementToBeVisible(PASSWORD_STRENGTH1_IOS);
                break;
        }
    }

    public void displayedPasswordStrength1OnCreateNewAccount( ) throws Exception {
        switch (deviceType) {
            case "ANDROID":
                scrollToElementUntilVisible(CREATEACCOUNT_SHOPPINGCART_ANDROID);
                webdriverWaitElementToBeVisible(PASSWORD_STRENGTH1_ANDROID);
                break;
            case "IOS":
                scrollToElementUsingScrollIOS(CREATENEWACCOUNT_IOS);
                webdriverWaitElementToBeVisible(PASSWORD_STRENGTH1_IOS);
                break;
        }
    }

    public void displayedPasswordStrength2( ) throws Exception {
        switch (deviceType){
            case "ANDROID":
                scrollToElementUntilVisible(VERIFY_PASSWORD_ANDROID);
                webdriverWaitElementToBeVisible(PASSWORD_STRENGTH2_ANDROID);
                break;
            case "IOS":
                scrollToElementUsingScrollIOS(JOINNOWBUTTON_IOS);
                webdriverWaitElementToBeVisible(PASSWORD_STRENGTH2_IOS);
                break;
        }
    }

    public void displayedPasswordStrength3( ) throws Exception {
        switch (deviceType){
            case "ANDROID":
                webdriverWaitElementToBeVisible(PASSWORD_STRENGTH3_ANDROID);
                break;
            case "IOS":
                webdriverWaitElementToBeVisible(PASSWORD_STRENGTH3_IOS);
                break;
        }
    }

    public void displayedPasswordStrengthMeter( ) throws Exception {
        switch (deviceType){
            case "ANDROID":
                webdriverWaitElementToBeVisible(STRENGTH_METER_ANDROID);
                break;
            case "IOS":
                webdriverWaitElementToBeVisible(STRENGTH_METER_IOS);
                break;
        }
    }

    public void conditonalCheckPassed( ) throws Exception {
        switch (deviceType){
            case "ANDROID":
                webdriverWaitElementToBeVisible(CONDTIONALCHECKPASSED_ANDROID);
                break;
            case "IOS":
                webdriverWaitElementToBeVisible(CONDTIONALCHECKPASSED_IOS);
                break;
        }
    }

    public void conditonalCheck1Passed( ) throws Exception {
        switch (deviceType){
            case "ANDROID":
                webdriverWaitElementToBeVisible(CONDTIONALCHECKPASSED1_ANDROID);
                webdriverWaitElementToBeVisible(CONDTIONALCHECKPASSED2_ANDROID);
                webdriverWaitElementToBeVisible(CONDTIONALCHECKPASSED3_ANDROID);
                webdriverWaitElementToBeVisible(CONDTIONALCHECKPASSED4_ANDROID);
                break;
            case "IOS":
                webdriverWaitElementToBeVisible(CONDTIONALCHECKPASSED1_IOS);
                webdriverWaitElementToBeVisible(CONDTIONALCHECKPASSED2_IOS);
                webdriverWaitElementToBeVisible(CONDTIONALCHECKPASSED3_IOS);
                webdriverWaitElementToBeVisible(CONDTIONALCHECKPASSED4_IOS);
                break;
        }
    }

    public void conditonalCheckFailed( ) throws Exception {
        switch (deviceType){
            case "ANDROID":
                scrollToElementAndPerformAction(LocatorType.TEXT ,"JOIN NOW",ActionType.SCROLL);
                webdriverWaitElementToBeVisible(CONDTIONALCHECKFAILED1_ANDROID);
                webdriverWaitElementToBeVisible(CONDTIONALCHECKFAILED2_ANDROID);
                webdriverWaitElementToBeVisible(CONDTIONALCHECKFAILED3_ANDROID);
                break;
            case "IOS":
                scrollToElementUsingScrollIOS(JOINNOWBUTTON_IOS);
                webdriverWaitElementToBeVisible(CONDTIONALCHECKFAILED1_IOS);
                webdriverWaitElementToBeVisible(CONDTIONALCHECKFAILED2_IOS);
                webdriverWaitElementToBeVisible(CONDTIONALCHECKFAILED3_IOS);
                break;
        }
    }

    public void clickOnBackButton( ) throws Exception {
        switch (deviceType){
            case "ANDROID":
                webdriverWaitElementToBeVisible(BACKBUTTON_ANDROID);
                clickByWebelement(BACKBUTTON_ANDROID, "User click on back button");
                break;
            case "IOS":
                webdriverWaitElementToBeVisible(BACKBUTTON_IOS);
                clickByWebelement(BACKBUTTON_IOS, "User click on back button");
                break;
        }
    }

    public void userEnterPassword(String Password) throws Exception {
        switch (deviceType){
            case "ANDROID":
                enterByWebelement(PASSWORD_FILED_ANDROID, getVaultData(Password),"Enter Wrong Password at Verify Password Filled");
                pressKey(PASSWORD_FILED_ANDROID, Keys.ENTER);
                break;
            case "IOS":
                scrollToElementUsingScrollIOS(JOINNOWBUTTON_IOS);
                enterByWebelement(PASSWORD_FILED_IOS, getVaultData(Password),"Enter Wrong Password at Verify Password Filled");
                KeyBoardEnterIOS();
                break;
        }
    }

    public void userEnterVerifyPassword(String VerifyPassword) throws Exception {
        switch (deviceType){
            case "ANDROID":
                scrollToElementUntilVisible(JOINNOWBUTTON_ANDROID);
                enterByWebelement(VERIFY_PASSWORD_FILED_ANDROID, getVaultData(VerifyPassword),"Enter Wrong Password at Verify Password Filled");
                break;
            case "IOS":
                scrollToElementUsingScrollIOS(JOINNOWBUTTON_IOS);
                enterByWebelement(VERIFY_PASSWORD_FILED_IOS, getVaultData(VerifyPassword),"Enter Wrong Password at Verify Password Filled");
                KeyBoardEnterIOS();
                break;
        }
    }

    public void clickOnJoinNowButton( ) throws Exception {
        switch (deviceType){
            case "ANDROID":
                scrollToElementUntilVisible(JOINNOWBUTTON_ANDROID);
                webdriverWaitElementToBeVisible(JOINNOWBUTTON_ANDROID);
                clickByWebelement(JOINNOWBUTTON_ANDROID, "User click on Join Now Button");
                waitForElement(PHONE_LEVEL_ENABLEMENT_OK_BUTTON_ANDROID,20);
                if(checkIsDisplayed(PHONE_LEVEL_ENABLEMENT_OK_BUTTON_ANDROID))
                {
                    clickByWebelement(PHONE_LEVEL_ENABLEMENT_OK_BUTTON_ANDROID, "Handled Phone Level Biometric Enablement alert");
                }
                handleLocationAccessAlert();
                if(checkIsDisplayed(CLOSE_BUTTON))
                {
                    clickByWebelement(CLOSE_BUTTON, "User click on close Button");
                }
                waitForElement(DELIVERY_ZIP_CANCEL,25);
                if(checkIsDisplayed(DELIVERY_ZIP_CANCEL)) {
                    clickByWebelement(DELIVERY_ZIP_CANCEL, "Clicking on Login Button");
                }
                break;
            case "IOS":
                scrollToElementUsingScrollIOS(JOINNOWBUTTON_IOS);
                webdriverWaitElementToBeVisible(JOINNOWBUTTON_IOS);
                clickByWebelement(JOINNOWBUTTON_IOS, "User click on Join Now Button");
                waitForElement(PHONE_LEVEL_ENABLEMENT_OK_BUTTON_IOS,20);
                if(checkIsDisplayed(PHONE_LEVEL_ENABLEMENT_OK_BUTTON_IOS))
                {
                    clickByWebelement(PHONE_LEVEL_ENABLEMENT_OK_BUTTON_IOS, "Handled Phone Level Biometric Enablement alert");
                }
                handleLocationAccessAlert();
                timeOut(30000);
                if(checkIsDisplayed(CLOSE_BUTTON))
                {
                    clickByWebelement(CLOSE_BUTTON, "User click on close Button");
                }
                break;
        }
    }

    public void verifyCreateNewAccountPageDisplayed()
    {
        waitForElement(CREATE_ACCOUNT_PAGE_VERIFY_ANDROID,10);
        checkIsDisplayed(CREATE_ACCOUNT_PAGE_VERIFY_ANDROID);
    }

    public void unCheckNCEnroll()
    {
      clickByWebelement(UNCHECK_NC_ENROLL_BUTTON_ANDROID,"Clicking on Uncheck NC Enroll");
    }
    public String getEmailFromTempMailSite() {
        String url = LoadProperties.prop.getProperty("mobile.fakemail.url");
        openNewTab(driver, url, 1);
        waitForElement(COPY,30);
        String Email = getAttributeValue(FAKE_EMAIL, "value") + getText(FAKE_DOMAIN);
        Email=Email.replaceAll("\\s", "");
        Allure.addAttachment(Email, "Copied mail fron the tempmail site");
        return Email;
    }

    public void userEntersValidPassword(String Password) throws Exception {
        switch (deviceType){
            case "ANDROID":
                scrollToElementAndPerformAction(LocatorType.TEXT ,"CANCEL",ActionType.SCROLL);
                enterByWebelement(PASSWORD_FILED_ANDROID, getVaultData(Password),"Enter valid password in the password field");
                break;
            case "IOS":
                scrollToElementUsingScrollIOS(PASSWORD_FILED_IOS);
                enterByWebelement(PASSWORD_FILED_IOS, getVaultData(Password),"Enter valid password in the password field");
                break;
        }
    }

    public void clickOnJoinNowButtonToCreateNewAccount( ) throws Exception {
        switch (deviceType){
            case "ANDROID":
                clickByWebelement(JOINNOWBUTTON_ANDROID, "User click on Join Now Button");
                break;
            case "IOS":
                clickByWebelement(JOINNOWBUTTON_IOS, "User click on Join Now Button");
                break;
        }
    }

   public void clickOnPhoneLevelEnablementPopup() {
       clickByWebelement(PHONE_LEVEL_ENABLEMENT_OK_BUTTON, "Handled Phone Level Biometric Enablement alert");
   }

   public void verifyNewUserAccountIsCreated() {
               waitForElement(ACCOUNT_CREATED, 15);
               clickByWebelement(CLOSE_BUTTON, "Click on close button");
   }

   public void login(String email, String password){
       enterByWebelement(CMA_EMAIL_FIELD_ANDROID, email, "Entering email address");
       enterByWebelement(CMA_PASSWORD_FIELD_ANDROID, getVaultData(password), "Entering password");
       clickByWebelement(LOGIN_BUTTON, "Click on on sign-in button");
   }

   public void allowNotificationsPopUpIOS() {
        if (deviceType.equalsIgnoreCase("IOS")) {
            waitForElement(TSC_NOTIFICATION_IOS, 10);
            clickByWebelement(ALLOW_NOTIFICATIONS_IOS, "click on Allow to receive notifications from TSC");
        }
       Reporter.log("TSC Allow notifications popup displayed only on IOS");
   }

   public void selectGMailAccount(String email) {
        if(deviceType.equalsIgnoreCase("IOS")){
            timeOut(5000);
            waitForElementToBeDisplayed(SELECT_GMAIL_ACCOUNT(email));clickByWebelement(Objects.requireNonNull(SELECT_GMAIL_ACCOUNT(email)),"Select google account in the prompt "+email);
            enterGoogleIAgreeButton();
        }
   }
}
