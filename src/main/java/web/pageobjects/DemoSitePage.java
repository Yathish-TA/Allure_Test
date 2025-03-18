package web.pageobjects;

import genericwrappers.GenericWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DemoSitePage extends GenericWrapper {
    public DemoSitePage(WebDriver driver){
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

    @FindBy(xpath = "//*[@id=\"item_4_title_link\"]/div")
    WebElement SELECT_PRODUCT;

    @FindBy(xpath = "//*[@id=\"inventory_item_container\"]/div/div/div/button")
    WebElement ADD_TO_CART_BUTTON;

    @FindBy(xpath = "//*[@id=\"shopping_cart_container\"]")
    WebElement CART_ICON;

    @FindBy(xpath = "//*[@id=\"contents_wrapper\"]/div[2]")
    WebElement CART_PAGE_TEXT;

    @FindBy(xpath = "//*[@class='bm-burger-button']")
    WebElement HAMBURGER_MENU;

    @FindBy(xpath = "//*[@id=\"logout_sidebar_link\"]")
    WebElement LOGOUT_LINK;


    public void login(String un, String pwd){
        enterByWebelement(USER_NAME,un,"Entering User Name");
        enterByWebelement(PWD_FIELD,pwd,"Entering Password");
        clickByWebelement(LOGIN_FIELD,"Clicking on Login Button");
    }

    public void productSelections(){
        clickByWebelement(SELECT_PRODUCT,"");
        clickByWebelement(ADD_TO_CART_BUTTON,"");
        clickByWebelement(CART_ICON,"");
        System.out.println("Cart Page Text is = " + CART_PAGE_TEXT.getText());
        CART_PAGE_TEXT.isDisplayed();
    }

    public void logout(){
        clickByWebelement(HAMBURGER_MENU,"");
        clickByWebelement(LOGOUT_LINK,"");
    }

}
