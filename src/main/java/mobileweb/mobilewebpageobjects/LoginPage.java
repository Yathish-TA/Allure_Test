package mobileweb.mobilewebpageobjects;

import database.DBC;
import genericwrappers.GenericWrapper;
import mobileutils.PageObjectBaseClassMobile;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

public class LoginPage extends GenericWrapper {
//    public WebDriver driver;
    public SoftAssert softAssert = new SoftAssert();
    public PageObjectBaseClassMobile getData = new PageObjectBaseClassMobile();
    DBC database = new DBC();
    String AESKey = "C8LogYU54lOz30/kks/+5i9F7HsHYivrRMzIxr2C+Ys\\=";


    public LoginPage(WebDriver driver) {
        super(driver); // Pass the WebDriver instance to the GenericWrapper constructor
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[@id='userButton']")
    WebElement LOGIN_USER_BUTTON;

    @FindBy(xpath = "//input[@name='cp_email-id']")
    WebElement EMAIL_FIELD;

    @FindBy(xpath = "//input[@name='cp_password']")
    WebElement PASSWORD_FIELD;

    @FindBy(xpath = "//button[@id='cp_sign-in-button']")
    WebElement SIGNIN_BUTTON;

    @FindBy(xpath = "//div[@class='session-error']/span")
    WebElement SIGNIN_ERROR_MSG;

    @FindBy(xpath = "//form[@id='Signin']/div[3]/a")
    WebElement FORGOT_PASSWORD_LINK;

    @FindBy(xpath = "//button[@id='WC_PasswordResetForm_Link_2']")
    WebElement CHANGE_PASSWORD_BUTTON;


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

}
