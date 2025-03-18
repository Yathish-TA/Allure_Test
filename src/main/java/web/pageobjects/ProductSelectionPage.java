package web.pageobjects;

import genericwrappers.GenericWrapper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

public class ProductSelectionPage extends GenericWrapper{
    static PageObjectBaseClass getwebdata = new PageObjectBaseClass();

    public ProductSelectionPage(WebDriver driver){
        super(driver); // Pass the WebDriver instance to the GenericWrapper constructor
        this.driver = driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//*[@class='categorymenu__icon-hamburger']")
    WebElement SHOP_CATEGORY;
    
    @FindBy(xpath = "//div//a[text()='Farm & Ranch']")
    WebElement FARM_AND_RANCH ;

    @FindBy(xpath = "//a[contains(text(), 'Farm & Ranch Savings ')]")
    WebElement FARM_AND_RANCH_SAVINGS ;

    @FindBy(xpath = "//button[@id='add2CartBtn_23732']")
    WebElement CHOOSE_OPTION ;

    @FindBy(xpath = "//button[@id='secure_checkout']")
    WebElement CHECKOUT ;

    @FindBy(xpath = "//input[@name='altemail1']")
    WebElement EMAIL_ADDRESS ;

    @FindBy(xpath = "//input[@name='altphone1']")
    WebElement PHONE_NUMBER ;

    @FindBy(xpath = "//input[@name='altfirstName']")
    WebElement FIRSTNAME ;

    @FindBy(xpath = "//input[@name='altlastName']")
    WebElement LASTNAME ;

    @FindBy(xpath = "//button[@data-analytics-link='Edit Pickup Person Address Continue']")
    WebElement SAVE_THE_DETAILS ;

    @FindBy(xpath = "//button[@data-analytics-link='Pickup Address Continue']")
    WebElement CONTINUE_BILLING ;

    @FindBy(xpath = "//input[@name='emailBill1']")
    WebElement EMAIL_ADDRESS_BILL ;

    @FindBy(xpath = "//input[@name='phoneBill1']" )
    WebElement PHONE_NUMBER_BILL ;

    @FindBy(xpath = "//input[@name='fnameBill']" )
    WebElement FIRSTNAME_BILL ;

    @FindBy(xpath = "//input[@name='lnameBill']" )
    WebElement LASTNAME_BILL ;

    @FindBy(xpath = "//input[@name='addressBill1']")
    WebElement BILLING_ADDRESS ;

    @FindBy(xpath = "//input[@name='cityBill']")
    WebElement BILLING_CITY ;

    @FindBy(xpath = "//input[@name='zipBill']")
    WebElement BILLING_ZIPCODE ;

    @FindBy(xpath = "//button[@id='billingAddressFormSaveBtn']")
    WebElement CONTINUE ;

    @FindBy(xpath = "//input[@id='creditCard-number']")
    WebElement CARD_NUMBER ;
    @FindBy(xpath = "//input[@id='creditCard-cvv']")
    WebElement CARD_CVV ;
    @FindBy(xpath = "//input[@id='creditCard-exp']")
    WebElement CARDEXP ;
    @FindBy(xpath = "//button[@id='place-order']")
    WebElement PLACE_ORDER ;
    @FindBy(xpath = "//button[@id='newCardContinue']")
    WebElement CONTINUE_PAYMENT ;
    @FindBy(xpath = "//span[@class='step-text']")
    WebElement ORDER_INFO ;

    public void clickShopCategory() {
        clickByWebelement(SHOP_CATEGORY,"Click on shop category to choose from");
    }

    public void clickFarmAndRanch() {
        clickByWebelement(FARM_AND_RANCH,"Click to choose FarmandRanch category");
    }

    public void clickFarmAndRanchSavings() {
        scrollToElement(FARM_AND_RANCH_SAVINGS);
        clickByWebelement(FARM_AND_RANCH_SAVINGS,"Click to choose farmandranchsaving from FarmandRanch category");
    }

    public void clickChooseOption() {
        clickByWebelement(CHOOSE_OPTION,"Click to choose options further");
    }

    public void clickCheckout(){
        clickByWebelement(CHECKOUT,"Click to continue to checkout");
    }

    public void paymentDetails() throws InterruptedException {
        Thread.sleep(3000);
        enterByWebelement(EMAIL_ADDRESS,getwebdata.getDatavalue("Email"), "Sending the Email_id");
        enterByWebelement(PHONE_NUMBER,getwebdata.getDatavalue("Phone"), "Sending the Phonenumber");
        enterByWebelement(FIRSTNAME,getwebdata.getDatavalue("FirstName"), "Sending the firstname");
        enterByWebelement(LASTNAME,getwebdata.getDatavalue("LastName"), "Sending the lastname");
        clickByWebelement(SAVE_THE_DETAILS,"Click on the save details button");
        clickByWebelement(CONTINUE_BILLING,"Click to continue to billing");
    }

    public void billingDetails() {
        enterByWebelement(EMAIL_ADDRESS_BILL,getwebdata.getDatavalue("Email"), "Filling in the Email_id");
        enterByWebelement(PHONE_NUMBER_BILL,getwebdata.getDatavalue("Phone"), "Filling in the Phonenumber");
        enterByWebelement(FIRSTNAME_BILL,getwebdata.getDatavalue("FirstName"), "Filling in the firstname");
        enterByWebelement(LASTNAME_BILL,getwebdata.getDatavalue("LastName"), "Filling in the lastname");
        enterByWebelement(BILLING_ADDRESS,getwebdata.getDatavalue("Address"), "Filling in the address");
        enterByWebelement(BILLING_CITY,getwebdata.getDatavalue("City"), "Filling in the City");
        enterByWebelement(BILLING_ZIPCODE,getwebdata.getDatavalue("Zipcode"), "Filling in the Zipcode");
        clickByWebelement(CONTINUE,"Click on the continue button");
    }
    public void productPayment() {
        enterByWebelement(CARD_NUMBER,getwebdata.getDatavalue("CardNumber"), "Filling in the card number");
        enterByWebelement(CARD_CVV,getwebdata.getDatavalue("Cvv"), "Filling in the card cvv");
        enterByWebelement(CARDEXP,getwebdata.getDatavalue("Exp"), "Filling in the card expiration");
        clickByWebelement(CONTINUE_PAYMENT,"Click on the continue button");
        clickByWebelement(PLACE_ORDER,"Click on the place order");
    }

    public String getOrderInfo(){
        String text = getText(ORDER_INFO);
        return text;
    }

    public void closeBrowserStep(){
        closeBrowser();
    }



}
