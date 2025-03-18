package web.pageobjects;

import genericwrappers.GenericWrapper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

public class OrderStatusPage extends GenericWrapper {

    static PageObjectBaseClass getwebdata = new PageObjectBaseClass();

    public OrderStatusPage(WebDriver driver){
        super(driver); // Pass the WebDriver instance to the GenericWrapper constructor
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "(//span[contains(text(),'Order Status')])[1]")
    WebElement ORDER_STATUS;

    @FindBy(xpath = "//input[@id='order-number']")
    WebElement ORDER_NUMBER ;

    @FindBy(xpath = "//input[@name='CustomerLastName']")
    WebElement ORDER_NAME ;

    @FindBy(xpath = "//button[@id='viewOrderBtn']")
    WebElement VERIFY_STATUS ;

    @FindBy(xpath = "//div[@class='d-flex justify-content-between border-bottom mt-7']")
    WebElement ORDER_STATUS_MSG ;


    public void orderStatus(){
        clickByWebelement(ORDER_STATUS,"Click on order status");
        clickByWebelement(ORDER_NUMBER,"");
        enterByWebelement(ORDER_NUMBER,getwebdata.getDatavalue("orderNumber"), "Entering the orderNumber");
        enterByWebelement(ORDER_NAME,getwebdata.getDatavalue("orderName"), "Entering the orderName");
        clickByWebelement(VERIFY_STATUS,"Click to verify the status");
    }
    public String orderStatusConfirmation(){
        String text = getText(ORDER_STATUS_MSG);
        return text;
    }

}
