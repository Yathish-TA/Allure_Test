package mobileweb.mobilewebpageobjects.mweb;

import genericwrappers.GenericWrapper;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import java.util.List;

public class MobileWebCartPage extends GenericWrapper {
    public MobileWebCartPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "(//a[@data-testid='test_Cart_Component'])[2]")
    WebElement CART_ICON;
    @FindBy(xpath = "//button[@title='Start Shopping']")
    WebElement START_SHOPPING;
    @FindBy(xpath = "//button[@title='Remove Item']")
    List<WebElement> ITEMS_QUANTITY;
    @FindBy(xpath = "//*[@id='WC_OrderItemDetailsf_links_2_1']|(// a[text()='Remove from cart'])[1]")
    WebElement REMOVE_BUTTON;
    @FindBy(xpath = "(//button[contains(text(),'Edit Zip')])[1]")
    WebElement EDIT_ZIP_CODE_BUTTON;
    @FindBy(css = "#input_zipcode_modwindow")
    WebElement ENTER_ZIP_INPUT_FIELD;
    @FindBy(css = "button[class='btn green']")
    WebElement SEARCH_ZIP_CODE;
    @FindBy(id = "delivery_option_error")
    WebElement RESTRICTED_ZIP_CODE_ERROR;

    public void clickCartButton() {
        scrollAndClick(CART_ICON, "Click on cart icon");
    }
    public void removeItemFromCart() {
        scrollAndClick(REMOVE_BUTTON, "Removing item from cart");
    }

    public void removeAllProductsFromCart() {
        webdriverWaitElementToBeVisible(waitForElementToRefresh(CART_ICON));
        clickCartButton();
        try {
            if (START_SHOPPING.isDisplayed()) {
                Allure.step("Cart is empty");
            }
        } catch (Exception e) {
            int quantity = ITEMS_QUANTITY.size();
            if (quantity >= 1) {
                for (int i = 1; i <= quantity; i++) {
                    removeItemFromCart();
                }
            }
            Allure.step("Items have been removed from the cart");
        }
    }
    public void changeZipCodeButton(){
        scrollAndClick(EDIT_ZIP_CODE_BUTTON,"Clicking on Edit Zip Code");
    }

    public void enterRestrictedZipCode(String zip){
        enterByWebelement(ENTER_ZIP_INPUT_FIELD,zip,"Entering Restricted Zipcode");
        clickByJavascript(SEARCH_ZIP_CODE,"Searching the given zip");
    }

    public void verifyRestrictedZipCode(){
        Assert.assertTrue(verifyExpectedAndActualTextOfElement(RESTRICTED_ZIP_CODE_ERROR, "There are no delivery options for the zipcode submitted. Try another zipcode or select Pickup in Store.", "Verify restricted zip code", false));
    }
}
