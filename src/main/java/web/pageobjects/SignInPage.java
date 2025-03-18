package web.pageobjects;

import genericwrappers.GenericWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static azurevault.VaultData.getVaultData;


public class SignInPage extends GenericWrapper {
    static PageObjectBaseClass getwebdata = new PageObjectBaseClass();


    public SignInPage(WebDriver driver){
        super(driver); // Pass the WebDriver instance to the GenericWrapper constructor
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[contains(text(), 'Sign In / Join')]")
    WebElement SIGNIN;

    @FindBy(xpath ="//input[@name='cp_email-id']")
    WebElement EMAIL_ICON ;

    @FindBy(xpath = "//input[@name='cp_password']")
    WebElement PASSWORD_ICON;

    @FindBy(xpath = "//button[@id='cp_sign-in-button']")
    WebElement SIGNIN_BTN ;

    @FindBy(xpath = "//*[@data-testid='test_notEnrolledLogo']")
    WebElement SIGN_IN_ICON;


    @FindBy(xpath = "//input[@id='email']")
    WebElement EMAIL_FIELD;


    @FindBy(xpath = "//input[@id='password']")
    WebElement PASSWORD_FIELD;

    @FindBy(xpath = "(//div[@data-testid='Signin-panel']//button)[1]")
    WebElement SIGN_IN_BUTTON;

    public void signIn() throws Exception {
        clickByWebelement(SIGNIN,"Click to signin");
        enterByWebelement(EMAIL_ICON,getwebdata.getDatavalue("Email"), "Sending the Email_id");
        String password = getwebdata.getDatavalue("Password"); //Here password is encrypted
        clickByWebelement(SIGNIN_BTN,"Click to signin button");
    }

    public void signInNow(String Email, String Password) {
        timeOut(3000);
        clickByWebelement(SIGN_IN_ICON, "Opening Sign In link");
        enterByWebelement(EMAIL_FIELD, Email, "Adding Email Address as username");
        enterByWebelement(PASSWORD_FIELD,getVaultData(Password), "Adding Password");
//        storePage.closeSignUpPopUp();
//        storePage.closeCookies();
        clickElementWithFluentWait(SIGN_IN_BUTTON, "Clicking on Sign In button");
        timeOut(5000);
    }

}
