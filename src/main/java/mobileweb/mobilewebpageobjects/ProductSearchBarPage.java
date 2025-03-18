package mobileweb.mobilewebpageobjects;

import genericwrappers.GenericWrapper;
import mobileutils.PageObjectBaseClassMobile;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

public class ProductSearchBarPage extends GenericWrapper {

    public SoftAssert softAssert = new SoftAssert();
    public PageObjectBaseClassMobile getData = new PageObjectBaseClassMobile();


    public ProductSearchBarPage(WebDriver driver) {
        super(driver); // Pass the WebDriver instance to the GenericWrapper constructor
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "(//input[@aria-label='search'])[3]")
    WebElement SEARCH_BAR;

    @FindBy(xpath = "(//*[@aria-label='Search'])[3]")
    WebElement SEARCH_ICON;

    @FindBy(xpath = "//*[@class=\"breadcrumb\"]/div/h2")
    WebElement VERIFY_HEADER;

    @FindBy(xpath = "(//*[@data-testid='HM_icon'])[2]")
    WebElement HAMBURGER_ICON;

    @FindBy(xpath = "(//a[@data-analytics-link='Lawn & Garden'])[3]")
    WebElement LAWN_AND_GARDEN_LINK;

    @FindBy(xpath = "//a[@data-analytics-link='Lawn & Garden Savings']")
    WebElement LAWN_AND_GARDEN_SAVINGS_LINK;

    @FindBy(xpath = "//div[@data-slot-id='heading-breadcrumb']/h1")
    WebElement VERIFY_LAWN_AND_GARDEN_PAGE_HEADER;

    public void searchProduct() {
        SEARCH_BAR.sendKeys(PageObjectBaseClassMobile.getDatavalue("SearchText"));
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
