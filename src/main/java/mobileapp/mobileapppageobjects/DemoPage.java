package mobileapp.mobileapppageobjects;

import genericwrappers.GenericWrapper;
import mobileutils.PageObjectBaseClassMobile;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

public class DemoPage extends GenericWrapper {

    PageObjectBaseClassMobile getSheetData = new PageObjectBaseClassMobile();
    SoftAssert softAssert = new SoftAssert();

    public DemoPage(WebDriver driver){
        super(driver);
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(id = "user-name")
    WebElement USER_NAME_FIELD;

    @FindBy(id = "password")
    WebElement PASSWORD_FIELD;

    @FindBy(id = "login-button")
    WebElement LOGIN_BUTTON;

    @FindBy(id = "item_4_title_link")
    WebElement SELECT_PRODUCT;

    @FindBy(id = "add-to-cart")
    WebElement ADD_TO_CART_BUTTON;

    @FindBy(id = "shopping_cart_container")
    WebElement CART_ICON;

    @FindBy(xpath = "//div[@id='header_container']/div[2]/span")
    WebElement CART_PAGE;


    public void loginDetails(){
        enterByWebelement(USER_NAME_FIELD, getSheetData.getDatavalue("Username"),"Enter the Username");
        enterByWebelement(PASSWORD_FIELD, getSheetData.getDatavalue("Password"),"Enter the Password");

    }

    public void clickLogin(){
        clickByWebelement(LOGIN_BUTTON,"Click on Login button");
    }

    public void selectProduct(){
        clickByWebelement(SELECT_PRODUCT,"Select the item");
        clickByWebelement(ADD_TO_CART_BUTTON,"Click on Add to cart button");
    }

    public void cartPage(){
        clickByWebelement(CART_ICON,"Open Cart Page");
        System.out.println(CART_PAGE.getText());
        softAssert.assertTrue(CART_PAGE.isDisplayed());
        softAssert.assertAll();
    }

}
