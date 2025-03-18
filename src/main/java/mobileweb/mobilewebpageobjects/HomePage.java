package mobileweb.mobilewebpageobjects;


import genericwrappers.GenericWrapper;
import mobileutils.PageObjectBaseClassMobile;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

public class HomePage extends GenericWrapper {

    public PageObjectBaseClassMobile getData = new PageObjectBaseClassMobile();

    public HomePage(WebDriver driver) {
        super(driver); // Pass the WebDriver instance to the GenericWrapper constructor
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Cookies pop up or iFrame locator's
    @FindBy(xpath = "//*[@id='onetrust-close-btn-container']/button")
    WebElement CLOSE_COOKIES_POP_UP;

    @FindBy(xpath = "//*[@id=\"attentive_creative\"]")
    WebElement SIGN_UP_POP_UP_IFRAME;

    @FindBy(xpath = "//*[@id=\"closeIconSvg\"]")
    WebElement CLOSE_SIGN_UP_IFRAME;

    @FindBy(xpath = "//div[@id='more-btn']")
    WebElement MOBILE_MORE_BUTTON;

    @FindBy(xpath = "//span[@id='ncHader_navLink_1_link_text']")
    WebElement ORDER_STATUS;

    @FindBy(xpath = "//input[@id='order-number']")
    WebElement ORDER_NUM_FIELD;

    @FindBy(xpath = "//input[@id='last-name']")
    WebElement LAST_NAME_FIELD;

    @FindBy(xpath = "//button[@id='viewOrderBtn']")
    WebElement CLICK_ORDER_BUTTON;

    @FindBy(xpath = "//div[@class='d-flex justify-content-between border-bottom mt-7']/h1")
    WebElement ORDER_DETAILS_PAGE;

    @FindBy(xpath = "//button[@id='viewOrderBtn']")
    WebElement VERIFY_STATUS;


    public void clickMoreButton() {
        clickByWebelement(MOBILE_MORE_BUTTON, "More Options is displayed");
    }

    public void clickOrderStatus() {
        clickByWebelement(ORDER_STATUS, "Order Status page is Loaded");
    }

    public void enterOrderInfo() {
        enterByWebelement(ORDER_NUM_FIELD, getData.getDatavalue("orderNumber"), "Passing the Value for Order Number");
        enterByWebelement(LAST_NAME_FIELD, getData.getDatavalue("orderName"), "Passing the Value for Last Name");
    }


    public void clickOrderButton() {
        scrollToElement(VERIFY_STATUS);
        clickByWebelement(VERIFY_STATUS, "Clicking on View Order");

    }

    public void verifyOrderDetailsPage() {
        clickByWebelement(VERIFY_STATUS, "Order details page loaded");
        String text = ORDER_DETAILS_PAGE.getText();
        validationWithDatabase(getData.returnDataMap(),text);

//        softAssert.assertEquals(text, getData.getDatavalue("ExpectedOutcome"), "Text does not match.");
//        softAssert.assertAll();
    }

    // Handling Cookies and Sign Up Pop_up windows
    public void closeCookiesMobile(){
        try{
            if(CLOSE_COOKIES_POP_UP.isDisplayed() || CLOSE_COOKIES_POP_UP.isEnabled()){
                clickElementWithFluentWait(CLOSE_COOKIES_POP_UP,"Closing the Cookies Pop-Up");
            }
        }catch (Exception e){
            Reporter.log("Cookies pop-up not populated");
        }
    }

    public void closeSignUpPopUpMobile(){
        try {
            waitForPopupAndCloseIfPresent(SIGN_UP_POP_UP_IFRAME, CLOSE_SIGN_UP_IFRAME);
        }catch(Exception e){
            Reporter.log("iFrame Pop-up is Not displayed");
        }
    }


}
