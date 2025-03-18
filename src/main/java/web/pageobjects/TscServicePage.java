package web.pageobjects;

import genericwrappers.GenericWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TscServicePage extends GenericWrapper {
    public WebDriver driver;

    public TscServicePage(WebDriver driver){
        super(driver); // Pass the WebDriver instance to the GenericWrapper constructor
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "(//*[contains(text(),'Services')])[4]")
    WebElement SERVICES_BUTTON;

    @FindBy(xpath = "//a[@data-analytics-link='Propane Refill-More Info']")
    WebElement PROPANE_REFIL_BTN ;

    @FindBy(xpath = "//div/h2[contains(text(),'Description')]")
    WebElement PROPANE_DESCRIPTION ;

    public void serviceWeb(){
        clickByWebelement(SERVICES_BUTTON,"Click the service button");
       clickByWebelement(PROPANE_REFIL_BTN,"Click the propane refill button");
    }

    public void verifyService() {
        PROPANE_DESCRIPTION.isDisplayed();
    }
}
