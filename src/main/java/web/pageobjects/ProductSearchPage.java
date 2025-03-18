package web.pageobjects;

import genericwrappers.GenericWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;


public class ProductSearchPage extends GenericWrapper {


    public ProductSearchPage(WebDriver driver){
        super(driver); // Pass the WebDriver instance to the GenericWrapper constructor
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@aria-label='search']")
    WebElement SEARCH_BAR;

    @FindBy(xpath = "//*[@aria-label='Search']")
    WebElement SEARCH_ICON ;

    @FindBy(xpath = "//span[contains(text(),'Purina Moist & Meaty')]")
    WebElement VERIFY_SEARCH ;

    @FindBy(xpath = "//*[@id='onetrust-close-btn-container']/button")
    WebElement CLOSE_COOKIES_POP_UP;

    @FindBy(xpath = " //*[@id='attentive_creative']")
    WebElement SIGN_UP_POP_UP_IFRAME;

    @FindBy(xpath = "//*[@id='closeIconContainer']")
    WebElement CLOSE_SIGN_UP_IFRAME;

    public void searchProduct(String search_item){
        System.out.println("This is the product stored in Azure Vault*** : "+ search_item);
        enterByWebelement(SEARCH_BAR,search_item, "Entering the item that needs to be searched");
        clickByWebelement(SEARCH_ICON,"Click on the search Icon");
    }
    public void closeCookies() {
        try {
            if (CLOSE_COOKIES_POP_UP.isDisplayed() || CLOSE_COOKIES_POP_UP.isEnabled()) {
                clickElementWithFluentWait(CLOSE_COOKIES_POP_UP, "Closing the Cookies Pop-Up");
            }
        } catch (Exception e) {
            Reporter.log("Cookies pop-up not populated");
        }
    }

    public void closeSignUpPopUp() {
        try {
            waitForPopupAndCloseIfPresent(SIGN_UP_POP_UP_IFRAME, CLOSE_SIGN_UP_IFRAME);
        } catch (Exception e) {
            Reporter.log("iFrame Pop-up is Not displayed");
        }
    }

    public void verifyProduct() {
        VERIFY_SEARCH.isDisplayed();
    }
}
