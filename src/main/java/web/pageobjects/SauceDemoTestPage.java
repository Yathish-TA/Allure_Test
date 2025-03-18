package web.pageobjects;

import genericwrappers.GenericWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SauceDemoTestPage extends GenericWrapper {
    public SauceDemoTestPage(WebDriver driver){
        super(driver); // Pass the WebDriver instance to the GenericWrapper constructor
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@id=\"user-name\"]")
    WebElement USER_NAME;

    @FindBy(xpath = "//*[@id=\"password\"]")
    WebElement PWD_FIELD;

    @FindBy(xpath = "//*[@id=\"login-button\"]")
    WebElement LOGIN_FIELD;

    @FindBy(xpath = "//*[@class='bm-burger-button']")
    WebElement HAMBURGER_MENU;

    @FindBy(xpath = "//*[@id=\"logout_sidebar_link\"]")
    WebElement LOGOUT_LINK;

    public void login(String Un, String Pwd){
        enterByWebelement(USER_NAME,Un,"");
        enterByWebelement(PWD_FIELD,Pwd,"");
        clickByWebelement(LOGIN_FIELD,"");
    }

    public void logout(){
        clickByWebelement(HAMBURGER_MENU,"");
    }

}
