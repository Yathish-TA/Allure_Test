package mobileapp.mobileapppageobjects;

import genericwrappers.ActionType;
import genericwrappers.GenericWrapper;
import genericwrappers.LocatorType;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class ShopPage extends GenericWrapper {
    private static String deviceType;
    @FindBy(xpath = "//*[@text='Shop']")
    WebElement SHOP_TAB_ANDROID;

    @FindBy(xpath="//XCUIElementTypeButton[@name='Shop']")
    WebElement SHOP_TAB_IOS;

    @FindBy(xpath = "//*[@text='All Categories']")
    WebElement ALL_CATEGORIES_LABEL_ANDROID;

    @FindBy(xpath="(//XCUIElementTypeStaticText[@name=\"All Categories\"])[2]")
    WebElement ALL_CATEGORIES_LABEL_IOS;

    @FindBy(xpath = "//android.view.View[@text='Shop By Category']")
    WebElement SHOP_BY_CATEGORY;

    @FindBy(xpath = "//android.view.View[@content-desc=\"Recommended Products\"]")
    WebElement RECOMMENDED_PRODUCTS_LABEL;

    @FindBy(xpath = "//android.view.View[@content-desc='Recommended Products']//following::android.widget.TextView[1]")
    WebElement FIRST_RECOMMENDED_PRODUCT;

    @FindBy(xpath = "(//android.widget.TextView[contains(@text,\"SKU\")]//preceding-sibling::*)[1]")
    WebElement PRODUCT_NAME;

    @FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"Back button\"]")
    WebElement BACK_ANDROID;

    @FindBy(xpath = "(//XCUIElementTypeOther[@name=\"Back button\"])[last()]")
    WebElement BACK_IOS;

    @FindBy(xpath = "//android.view.View[@content-desc='Recommended Products']//following::android.widget.ImageView[1]")
    WebElement FIRST_RECOMMENDED_PRODUCT_IMAGE;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@content-desc,\"Sales Tax\")]")
    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeStaticText[@name=\"Sales Tax\"])[2]")
    WebElement SALETAX_TEXT;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Total\"]")
    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeStaticText[contains(@name,'Total')])[1]")
    WebElement TOTAL_TEXT;

    @FindBy(xpath = "//android.widget.TextView[@content-desc=\"CHECKOUT\"]")
    WebElement CHECKOUT;

    private WebElement CategoryName(String categoryName) {
        String xpath = "//*[@text=\"" + categoryName + "\"]";
        return driver.findElement(By.xpath(xpath));
    }

    private By getRatingLocator(int productIndex) {
        return By.xpath("(//android.view.View[contains(@content-desc, 'Recommended Products')]//android.widget.TextView[contains(@text, '(')])[" + productIndex + "]");
    }

    private WebElement CategoryAndroid(String selectCategory) {
        String xpath = "//android.view.ViewGroup[contains(@content-desc, '" + selectCategory + "')]";
        return driver.findElement(By.xpath(xpath));
    }

    private WebElement CategoryIos(String selectCategory) {
        String xpath = "(//XCUIElementTypeOther[contains(@name,'" + selectCategory + "')])[last()]";
        return driver.findElement(By.xpath(xpath));
    }

    private WebElement subCategory(String subCategoryName) {
        String xpath = "//android.view.ViewGroup[contains(@content-desc, '" + subCategoryName + "')]";
        return driver.findElement(By.xpath(xpath));
    }

    @AndroidFindBy(xpath = "//android.view.View[@content-desc=\"Shop\" and @text=\"Shop\"]")
    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeStaticText[@name=\"Shop\"])[1]")
    public WebElement SHOP_PAGE_VERIFY;

    private String recommendedItemName;
    private String PdpItemName;

    public ShopPage(WebDriver driver, String deviceType) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        ShopPage.deviceType = deviceType;
    }

    public void clickOnShop() {
        switch(deviceType) {
            case "ANDROID":
                webdriverWaitElementToBeVisible(SHOP_TAB_ANDROID);
                clickByWebelement(SHOP_TAB_ANDROID, "Click on Shop Button");
                timeOut(2000);
                break;
            case "IOS":
                webdriverWaitElementToBeVisible(SHOP_TAB_IOS);
                clickByWebelement(SHOP_TAB_IOS, "Click on Shop Button");
                timeOut(2000);
                break;
        }
    }

    public void clickCategoryByName(String CategoryName) {
        timeOut(4000);
        scrollToElementAndPerformAction(LocatorType.TEXT,CategoryName, ActionType.SCROLL);
        timeOut(2000);
        CategoryName(CategoryName).click();
        timeOut(3000);
    }

    public void swipeLeftShop(String x_axis,String y_axis)
    {
        switch(deviceType) {
            case "ANDROID":
                timeOut(2000);
                CMAswipeHorizontal(x_axis, y_axis);
                break;
            case "IOS":
                timeOut(2000);
                CMAswipeHorizontal("20", "750");
                break;
        }
    }
    public void verifyShopPage()
    {
        switch(deviceType) {
            case "ANDROID":
                timeOut(1000);
                isElementDisplayed(ALL_CATEGORIES_LABEL_ANDROID, "All Categories Label");
                break;
            case "IOS":
                timeOut(1000);
                isElementDisplayed(ALL_CATEGORIES_LABEL_IOS, "All Categories Label");
                break;
        }
    }

    public void myCategory(String selectCategory) {
        switch(deviceType) {
            case "ANDROID":
                timeOut(8000);
                scrollToElementAndPerformAction(LocatorType.CONTENT_DESC, "Sporting Goods Navigation Button", ActionType.SCROLL, selectCategory);
                CategoryAndroid(selectCategory).click();
                timeOut(8000);
                break;
            case "IOS":
                timeOut(8000);
                scrollToElementAndPerformAction(LocatorType.CONTENT_DESC, "Sporting Goods Navigation Button", ActionType.SCROLL, selectCategory);
                CategoryIos(selectCategory).click();
                timeOut(8000);
                break;
        }
    }

    public void shopByCategoryIsDisplayed() {
        webdriverWaitElementToBeVisible(SHOP_BY_CATEGORY);
        checkIsDisplayed(SHOP_BY_CATEGORY);
    }

    public void mySubCategory(String selectSubCategory) {
        timeOut(8000);
        scrollToElementAndPerformAction(LocatorType.TEXT, selectSubCategory,ActionType.SCROLL);
        timeOut(2000);
        subCategory(selectSubCategory).click();
        timeOut(8000);
    }

    public void recommendedProductsIsDisplayed() {
        waitForElement(RECOMMENDED_PRODUCTS_LABEL,9);
        scrollToElementUntilVisible(FIRST_RECOMMENDED_PRODUCT);
        isElementDisplayed(RECOMMENDED_PRODUCTS_LABEL, "Recommendation product is displayed");
        timeOut(8000);
    }

    public void scrollTorecommendedItem(){
        waitForElement(FIRST_RECOMMENDED_PRODUCT, 9);
        scrollToElementUntilVisible(getRatingLocator(2));
        isElementDisplayed(FIRST_RECOMMENDED_PRODUCT, "First recommendation product is displayed");
    }

    public void getRecommendedItemName(){
        waitForElement(FIRST_RECOMMENDED_PRODUCT, 9);
        recommendedItemName = FIRST_RECOMMENDED_PRODUCT.getText();
    }

    public void clickOnFirstRecommendedItem()
    {
        clickByWebelement(FIRST_RECOMMENDED_PRODUCT_IMAGE,"Clicking on First Recommended Item");
    }

    public void getProductName(){
        waitForElement(PRODUCT_NAME, 9);
        PdpItemName = getText(PRODUCT_NAME);
    }

    public boolean verifyRecommendationContainsProductName() {
        boolean isMatch = recommendedItemName != null && PdpItemName != null && recommendedItemName.contains(PdpItemName);
        if (isMatch) {
            Allure.addAttachment("Verification Passed",
                    "The recommended item contains the product name.\n" +
                            "Recommended Item Name: " + recommendedItemName + "\n" +
                            "Product Name: " + PdpItemName);
        } else {
            Allure.addAttachment("Verification Failed",
                    "The recommended item does not contain the product name.\n" +
                            "Recommended Item Name: " + recommendedItemName + "\n" +
                            "Product Name: " + PdpItemName);
        }
        return isMatch;
    }

    public void clickOnBackButton()
    {
        switch (deviceType) {
            case "ANDROID":
                waitForElement(BACK_ANDROID, 9);
                clickByWebelement(BACK_ANDROID,"Clicking on Back Button");
                timeOut(8000);
                break;
            case "IOS":
                waitForElement(BACK_IOS, 9);
                clickByWebelement(BACK_IOS,"Clicking on Back Button");
                timeOut(8000);
                break;
        }
    }

    public double getRating(int productIndex) {
        WebElement ratingElement = driver.findElement(getRatingLocator(productIndex));
        scrollToElementUntilVisible(getRatingLocator(1));
        String ratingText = ratingElement.getText();
        String ratingValue = ratingText.split("\\(")[0].trim();
        return Double.parseDouble(ratingValue);
    }

    public double getFirstProductRating() {
        scrollToElementUntilVisible(getRatingLocator(1));
        timeOut(8000);
        return getRating(1);
    }

    public double getSecondProductRating() {
        return getRating(2);
    }

    public void verifyFirstRatingIsGreaterThanOrEqualToSecond() {
        double rating1 = getFirstProductRating();
        double rating2 = getSecondProductRating();
        if (rating1 < rating2) {
            Allure.addAttachment("Assertion Failed",
                    "Rating1 (" + rating1 + ") is not greater than or equal to Rating2 (" + rating2 + ").");
            throw new AssertionError("Assertion Failed: Rating1 (" + rating1 + ") is not greater than or equal to Rating2 (" + rating2 + ").");
        } else {
            Allure.addAttachment("Assertion Passed",
                    "Rating1 (" + rating1 + ") is greater than or equal to Rating2 (" + rating2 + ").");
        }
    }

    public void verifySaleTaxIsDisplayedOnThePage() {
        switch (deviceType){
            case "ANDROID":
                scrollToElementUntilVisible(SALETAX_TEXT);
                waitForElementVisible(LocatorType.TEXT, "Sale Tax Text Is Displayed");
                Assert.assertTrue(isElementDisplayed(SALETAX_TEXT,"Sale Tax Text is displayed"));
                break;
            case "IOS":
                scrollToElementUsingScrollIOS(SALETAX_TEXT);
                waitForElementVisible(LocatorType.TEXT, "Sale Tax Text Is Displayed");
                Assert.assertTrue(isElementDisplayed(SALETAX_TEXT,"Sale Tax Text is displayed"));
        }

    }

    public boolean verifyTotalAmountIsDisplayedOnThePage() {
        waitForElement(TOTAL_TEXT,10);
        return checkIsDisplayed(TOTAL_TEXT);
    }

    public void ClickOnSeeCheckoutButton() {
        waitForElement(CHECKOUT,20);
        scrollToElementUntilVisible(CHECKOUT);
        clickByWebelement(CHECKOUT,"Click on Check Button");
    }

    public void verifyShopPageIsDisplayed() {
        waitForElement(SHOP_PAGE_VERIFY, 30);
        Assert.assertTrue(checkIsDisplayed(SHOP_PAGE_VERIFY));
        Allure.addAttachment("Verification:", "Shop Page is Displayed");
    }
}
