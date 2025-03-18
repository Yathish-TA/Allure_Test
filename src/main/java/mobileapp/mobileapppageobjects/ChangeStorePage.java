package mobileapp.mobileapppageobjects;

import genericwrappers.GenericWrapper;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class ChangeStorePage extends GenericWrapper {
    private static String deviceType;

    @FindBy(xpath = "//android.widget.Button[@content-desc=\"Continue\"]")
    WebElement CONTINUE_ANDROID;

    @FindBy(xpath = "//XCUIElementTypeButton[@name=\"Continue\"]")
    WebElement CONTINUE_IOS;

    @FindBy(xpath = "//android.widget.TextView[@text=\"Add a Credit or Debit Card\"]")
    WebElement CREDITCARD_ANDROID;

    @FindBy(xpath = "//XCUIElementTypeOther[@name=\"Add a Credit or Debit Card\"]")
    WebElement CREDITCARD_IOS;

    public ChangeStorePage(WebDriver driver, String deviceType) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        ChangeStorePage.deviceType = deviceType;
    }

    public void clickOnContinue() {
        switch (deviceType){
            case "ANDROID":
                waitForElement(CONTINUE_ANDROID,10);
                scrollToElementUntilVisible(CONTINUE_ANDROID);
                clickByWebelement(CONTINUE_ANDROID, "Click on Continue");
                timeOut(6000);
                break;
            case "IOS":
                scrollToElementUsingScrollIOS(CONTINUE_IOS);
                webdriverWaitElementToBeVisible(CONTINUE_IOS);
                clickByWebelement(CONTINUE_IOS, "Click on Continue");
                timeOut(6000);
                break;
        }
    }

    public void clickOnCreditcard() {
        switch (deviceType){
            case "ANDROID":
                scrollToElementUntilVisible(CREDITCARD_ANDROID);
                clickByWebelement(CREDITCARD_ANDROID, "Click on Continue");
                break;
            case "IOS":
                clickByWebelement(CREDITCARD_IOS, "Click on Continue");
                break;
        }
    }

}