package mobileweb.mobilewebpageobjects.mweb;

import genericwrappers.GenericWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MobileWebProductListPage extends GenericWrapper {

    public MobileWebProductListPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[contains(text(),'Add to cart')]")
    WebElement ADD_TO_CART_BUTTON;
}
