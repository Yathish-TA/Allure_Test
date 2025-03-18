package mobileapp.mobileapppageobjects;

import genericwrappers.GenericWrapper;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ComparePage extends GenericWrapper {
    private static String deviceType;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Compare']")
    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeStaticText[@name=\"Compare\"])[1]")
    WebElement COMPARE_HEADER_LABEL;

    private WebElement comparePriceLineNumber(String comparePriceNumber) {
        String xpath="";
        if (deviceType.equals("ANDROID")) {
            xpath= "(//android.widget.TextView[@text='Price']/following-sibling::*[contains(@content-desc,'Price')])[" + comparePriceNumber + "]";
        }
        else if(deviceType.equals("IOS"))
        {
            xpath="(//XCUIElementTypeStaticText[@name=\"Price\"]//following-sibling::*/*[1])[" + comparePriceNumber + "]";
        }
            return driver.findElement(By.xpath(xpath));
    }
    private WebElement compareATCLineNumber(String comparePriceNumber) {
        String xpath="";
        if (deviceType.equals("ANDROID")) {
            xpath = "(//*[@text='Add To Cart'])[" + comparePriceNumber + "]";
        }
        else if(deviceType.equals("IOS"))
        {
            xpath="(//XCUIElementTypeOther[@name=\"Add To Cart\"])[" + comparePriceNumber + "]";
        }
        return driver.findElement(By.xpath(xpath));
    }
    private WebElement compareRemoveLineNumber(String comparePriceNumber) {
        String xpath="";
        if (deviceType.equals("ANDROID")) {
             xpath = "(//android.view.View[contains(@content-desc,'Remove button')]/android.view.ViewGroup/android.widget.ImageView)[" + comparePriceNumber + "]";
        }
        else if(deviceType.equals("IOS"))
        {
             xpath = "(//XCUIElementTypeOther[@name=\"Close button Compare\"]//following-sibling::*[contains(@name,'Remove')])[" + comparePriceNumber + "]";
        }
        return driver.findElement(By.xpath(xpath));
    }
    private WebElement compareChoopseOptLineNumber(String comparePriceNumber) {
        String xpath="";
        if (deviceType.equals("ANDROID")) {
             xpath = "(//*[@text='Choose Options'])[" + comparePriceNumber + "]";
        }
        else if (deviceType.equals("IOS")) {
            xpath = "(//XCUIElementTypeOther[@name=\"Choose Options\"])[" + comparePriceNumber + "]";
        }
        return driver.findElement(By.xpath(xpath));
    }
    private WebElement compareFindInStoresLineNumber(String comparePriceNumber) {
        String xpath="";
        if (deviceType.equals("ANDROID")) {
             xpath = "(//*[@text='Find In Stores '])[" + comparePriceNumber + "]";
        }
        else if (deviceType.equals("IOS")) {
            xpath = "(//XCUIElementTypeOther[@name=\"Find In Stores \"])[" + comparePriceNumber + "]";
        }
        return driver.findElement(By.xpath(xpath));
    }
    public ComparePage(WebDriver driver, String deviceType) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        ComparePage.deviceType = deviceType;
    }

    public void verifyComparePage()
    {
        webdriverWaitElementToBeVisible(COMPARE_HEADER_LABEL);
        isElementDisplayed(COMPARE_HEADER_LABEL, "Compare Header Label");
    }
    public void verifyPriceInCompareLineNumber(String compareLine) {
        webdriverWaitElementToBeVisible(comparePriceLineNumber(compareLine));
        isElementDisplayed(comparePriceLineNumber(compareLine), "Compare Header Label");
    }
    public void verifyAddToCartInCompareLineNumber(String compareLine) {
        webdriverWaitElementToBeVisible(compareATCLineNumber(compareLine));
        isElementDisplayed(compareATCLineNumber(compareLine), "ATC Button");
    }
    public void clickAddToCartInCompareLineNumber(String compareLine) {
        webdriverWaitElementToBeVisible(compareATCLineNumber(compareLine));
        clickByWebelement(compareATCLineNumber(compareLine),"Clicking on ATC");
    }
    public void verifyRemoveInCompareLineNumber(String compareLine) {
        webdriverWaitElementToBeVisible(compareRemoveLineNumber(compareLine));
        isElementDisplayed(compareRemoveLineNumber(compareLine), "Remove Button");
    }
    public void clickRemoveInCompareLineNumber(String compareLine) {
        webdriverWaitElementToBeVisible(compareRemoveLineNumber(compareLine));
        clickByWebelement(compareRemoveLineNumber(compareLine),"Clicking Remove Button");
    }
    public void verifyChooseOptInCompareLineNumber(String compareLine) {
        try {
            webdriverWaitElementToBeVisible(compareChoopseOptLineNumber(compareLine));
            isElementDisplayed(compareChoopseOptLineNumber(compareLine), "Choose Option Button");
            Allure.addAttachment("verifyChooseOptInCompareLineNumber", "Choose Option Button is displayed");
        } catch (Exception e) {
            Allure.addAttachment("verifyChooseOptInCompareLineNumber- Failure",
                    "Choose Option Button is not displayed");
        }
    }
    public void clickChooseOptInCompareLineNumber(String compareLine) {
        webdriverWaitElementToBeVisible(compareChoopseOptLineNumber(compareLine));
        clickByWebelement(compareChoopseOptLineNumber(compareLine),"Clicking Choose Option Button");
    }
    public void verifyFindInStoresInCompareLineNumber(String compareLine) {
        try {
            webdriverWaitElementToBeVisible(compareFindInStoresLineNumber(compareLine));
            isElementDisplayed(compareFindInStoresLineNumber(compareLine), "Find In Stores Button");
            Allure.addAttachment("compareFindInStoresLineNumber", "Find In Stores Button is displayed");
        } catch (Exception e) {
            Allure.addAttachment("compareFindInStoresLineNumber- Failure",
                    "Find In Stores Button is not displayed");
        }
    }
    public void clickFindInStoresInCompareLineNumber(String compareLine) {
        webdriverWaitElementToBeVisible(compareFindInStoresLineNumber(compareLine));
        clickByWebelement(compareFindInStoresLineNumber(compareLine),"Clicking Find In Stores Button");
    }

}
