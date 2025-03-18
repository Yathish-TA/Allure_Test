package mobileapp.mobileapppageobjects;

import genericwrappers.GenericWrapper;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import mobileutils.PageObjectBaseClassMobile;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

public class ProductSearchBarPage extends GenericWrapper {
    public static String deviceType;

    public SoftAssert softAssert = new SoftAssert();
    public PageObjectBaseClassMobile getData = new PageObjectBaseClassMobile();


    public ProductSearchBarPage(WebDriver driver, String deviceType) {
        super(driver); // Pass the WebDriver instance to the GenericWrapper constructor
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        ProductSearchBarPage.deviceType = deviceType;
    }

    @FindBy(xpath = "//input[@class='searchbar__input']")
    WebElement SEARCH_BAR;

    @FindBy(xpath = "//*[@aria-label='Search icon']")
    WebElement SEARCH_ICON;

    @FindBy(xpath = "//div[@data-slot-id='heading-breadcrumb']/h1")
    WebElement VERIFY_HEADER;

    @FindBy(xpath = "//*[@class='categorymenu__icon-hamburger']")
    WebElement HAMBURGER_ICON;

    @FindBy(xpath = "//a[@data-analytics-link='Lawn & Garden']")
    WebElement LAWN_AND_GARDEN_LINK;

    @FindBy(xpath = "//a[@data-analytics-link='Lawn & Garden Savings']")
    WebElement LAWN_AND_GARDEN_SAVINGS_LINK;

    @FindBy(xpath = "//div[@data-slot-id='heading-breadcrumb']/h1")
    WebElement VERIFY_LAWN_AND_GARDEN_PAGE_HEADER;

    public void searchProduct() {
        SEARCH_BAR.sendKeys(getData.getDatavalue("SearchText"));
        clickByWebelement(SEARCH_ICON, "Searching product for the user input data");
    }

    public void verifyProduct() {
        softAssert.assertTrue(VERIFY_HEADER.isDisplayed());
    }

    public void clickHamburgerMenu() {
        clickByWebelement(HAMBURGER_ICON, "Opening Menu");
    }

    public void chooseProduct() {
        clickByWebelement(LAWN_AND_GARDEN_LINK, "Selecting the option from Menu");
        clickByWebelement(LAWN_AND_GARDEN_SAVINGS_LINK, "");
    }

    public void verifyCategoryPageHeader() {
        softAssert.assertTrue(VERIFY_LAWN_AND_GARDEN_PAGE_HEADER.isDisplayed());
    }


}
