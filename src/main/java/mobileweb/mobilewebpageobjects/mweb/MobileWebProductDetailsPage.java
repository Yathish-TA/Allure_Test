package mobileweb.mobilewebpageobjects.mweb;

import genericwrappers.GenericWrapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MobileWebProductDetailsPage extends GenericWrapper {

    public MobileWebProductDetailsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[contains(text(),'Add to cart')]")
    WebElement ADD_TO_CART_BUTTON;

    @FindBy(css = "label[for='shipping0']")
    WebElement SHIP_TO_STORE_RADIO_BUTTON;

    @FindBy(id = "secure_checkout")
    WebElement SECURE_CHECKOUT_BUTTON;

    @FindBy(xpath = "(//span[@id='sku'])[1]")
    WebElement ITEM_SKU;

    @FindBy(css = ".sku")
    WebElement ITEM_SKU_NO_LOGIN;

    @FindBy(xpath = "(//button[@buttontext='Add to cart']/following-sibling::div/button)[1]")//"buy-now-pdp")
    WebElement BUY_NOW_BUTTON;

    @FindBy(xpath = "//button[contains(@id,'buy-now-sku')]")
    WebElement BUY_NOW_BUTTON_PLP;

    @FindBy(xpath = "//h3[contains(text(),'Express Checkout')]")
    WebElement EXPRESS_CHECKOUT;

    @FindBy(css = "#delivery-header")
    WebElement HOME_DELIVERY_TEXT;

    @FindBy(id = "non-returnable-dialog-section")
    WebElement VERBIAGE_RESTRICTION;

    @FindBy(xpath = "//a[contains(text(),'View Cart')]")
    WebElement VIEW_CART_LINK_SECURE_CHECKOUT;


    public WebElement CHOOSE_PRODUCT_FROM_PLP_SPECIFIC_ITEM(String skuId){
        return driver.findElement(By.xpath(
                //"//a[contains(@href,'/tsc/product/') and following::p[contains(@aria-label,'"+skuId+"')]]/parent::div/following-sibling::div/div/a"
                "//div[contains(text(), '"+skuId.trim()+"')]/parent::div/parent::div//img"
        ));
    }

    @FindBy(xpath = "//span[contains(text(),'Search for')]")
    WebElement SEARCH_FOR_LABEL;

    @FindBy(xpath="(//a[@title='cart'])[1]")
    WebElement EDIT_CART;

    public void verifyTheProductIsAvailable(String productToSearch){
        boolean isProductListed;
        try {
            isProductListed = verifyExpectedAndActualTextOfElement((ITEM_SKU_NO_LOGIN), productToSearch, "Verify searched item is returned", false);
        } catch (Exception e) {
            isProductListed = verifyExpectedAndActualTextOfElement(waitForElementToBeInteractable(ITEM_SKU, 3), productToSearch, "Verify searched item is returned", false);
        }
        Assert.assertTrue("Unable to find expected item through search!", isProductListed);
    }

    public void selectItemFromPLP(String skuId){
        if(StringUtils.isNumeric(skuId) && waitUntil(()-> SEARCH_FOR_LABEL.isDisplayed(), 2)) {
            waitUntil(() -> CHOOSE_PRODUCT_FROM_PLP_SPECIFIC_ITEM(skuId.trim()).isDisplayed(), 10);
            scrollAndClick(CHOOSE_PRODUCT_FROM_PLP_SPECIFIC_ITEM(skuId.trim()), "Click on the product from the PLP");
        }
    }

    public void clickOnAddToCartButton(){
        scrollAndClick(ADD_TO_CART_BUTTON, "Adding an item to the cart");
    }

    public void clickOnSecureCheckoutButton(){
        scrollAndClick(SECURE_CHECKOUT_BUTTON, "Click on secure checkout button");
    }

    public void verifyBuyNowButtonIsDisplayedInPDP() {
        Assert.assertTrue(verifyExpectedElementIsPresent(BUY_NOW_BUTTON, true, "Verify BUY NOW button is displayed"));
    }

    public void verifyExpressCheckoutIsDisplayed() {
        Assert.assertTrue(verifyExpectedElementIsPresent(SECURE_CHECKOUT_BUTTON, true, "Verify Express Checkout is displayed"));
    }
    public void verifyBuyNowButtonIsNotDisplayedInPDP() {
        Assert.assertTrue(verifyExpectedElementIsPresent(BUY_NOW_BUTTON, false, "Verify BUY NOW button is not displayed"));
    }

    public void verifyExpressCheckoutIsNotDisplayed() {
        Assert.assertFalse(checkIsDisplayed(EXPRESS_CHECKOUT));
    }

    public void clickBuyNowButton() {
        scrollAndClick(BUY_NOW_BUTTON, "Clicking on BuyNow Button");
    }

    public void navigateToCartPage() {
        scrollAndClick(VIEW_CART_LINK_SECURE_CHECKOUT, "Clicking on View Cart link in Secure Checkout");
    }

    public void verifyDeliveryOptionsDisplayed() {
        Assert.assertTrue(verifyExpectedElementIsPresent(HOME_DELIVERY_TEXT, true, "Delivery Options should be displayed"));
    }

    public void shippingRestrictionVerbiage() {
        Assert.assertTrue(verifyExpectedAndActualTextOfElement(VERBIAGE_RESTRICTION, "This Item cannot be shipped to AK, CA, CT, DE, DC, HI, IL, MD, MA, NJ, NY, NC, RI, SC",
                "Shipping restricted state are displayed", true));
    }

    public void verifyDeliveryOptionsNotDisplayed() {
        Assert.assertTrue(verifyExpectedElementIsPresent(HOME_DELIVERY_TEXT, false, "Delivery Options should not be displayed"));
    }

    public void verifyBuyNowButtonDisplayedForFirearmAmmoItemsInPDP() {
        Assert.assertTrue(verifyExpectedAndActualTextOfElement(BUY_NOW_BUTTON, "Buy Now", "Verify Buy Now", false));
    }

    public void clickAddCartButton() {
        scrollAndClick(ADD_TO_CART_BUTTON, "Click Add to cart button");
    }

    public void clickEditCart(){
        scrollAndClick(EDIT_CART,"Click on Edit Cart");
    }

    public void clickCheckoutBox(){
        scrollAndClick(SECURE_CHECKOUT_BUTTON,"Checkout Box");
    }

    public void verifyBuyNowButtonIsNotDisplayed() {
        Assert.assertTrue(verifyExpectedElementIsPresent(BUY_NOW_BUTTON, false,"Verify Buy Now button"));
    }

    public void verifyBuyNowButtonIsDisplayed() {
        waitUntil(()-> BUY_NOW_BUTTON.isDisplayed(), 20);
        scrolldownByPixcel();
        Assert.assertTrue(verifyExpectedElementIsPresent(BUY_NOW_BUTTON, true, "Verify Buy Now button"));
    }

    public void verifyBuyNowNotDisplayedInPLP(){
        Assert.assertTrue(verifyExpectedElementIsPresent(BUY_NOW_BUTTON_PLP, false, "BUY NOW button should not be displayed"));
    }

    public void verifyBuyNowDisplayedInPLP(){
        Assert.assertTrue(verifyExpectedElementIsPresent((BUY_NOW_BUTTON_PLP), true, "BUY NOW button should be displayed"));
    }
}