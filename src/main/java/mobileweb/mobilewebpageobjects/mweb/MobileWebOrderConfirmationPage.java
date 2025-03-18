package mobileweb.mobilewebpageobjects.mweb;

import genericwrappers.GenericWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class MobileWebOrderConfirmationPage extends GenericWrapper {

    public MobileWebOrderConfirmationPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "order-confirm-message")
    WebElement ORDER_CONFIRMATION_MESSAGE;

    public void verifyOrderIsPlaced(){
        Assert.assertTrue(verifyExpectedAndActualTextOfElement(ORDER_CONFIRMATION_MESSAGE, "Your order has been completed.", "Verify order is placed", false));
    }
}
