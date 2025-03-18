package mobileweb.mobilewebpageobjects.mweb;

import genericwrappers.GenericWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MobileWebLoginPage extends GenericWrapper {

    public MobileWebLoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "(//div[@data-testid='test_accountMobile']//span/p)[1]")
    WebElement SIGN_IN_LINK;

    @FindBy(id = "email")
    WebElement EMAIL_INPUT_FIELD;

    @FindBy(id = "password")
    WebElement PASSWORD_INPUT_FIELD;

    @FindBy(css = "button[data-testid='Signin-button']")
    WebElement SIGN_IN_BUTTON;

    @FindBy(xpath = "//div[@data-testid='test_accountMobile']//p[contains(text(),'Hey ')]")
    WebElement USER_NAME_LABEL;

    public void clickSignInLink(){
        clickByWebelement(SIGN_IN_LINK, "Clicking on Sign In link to enter the credentials");
    }

    public void enterUserName(String userName){
        enterByWebelement(EMAIL_INPUT_FIELD, userName, "Entering the email");
    }

    public void enterPassword(String password){
        enterByWebelement(PASSWORD_INPUT_FIELD, password, "Entering the password");
    }

    public void clickSignInButton(){
        clickByJavascript(SIGN_IN_BUTTON, "Clicking on Sign in button");
    }

    public boolean verifyUserIsLoggedIn(String userName){
        return verifyExpectedAndActualTextOfElement(USER_NAME_LABEL, userName, "Verify the user is logged in successfully.", false);
    }

}
