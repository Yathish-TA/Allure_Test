package mobileweb.mobilewebpageobjects.mweb;

import genericwrappers.GenericWrapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MobileWebHomePage extends GenericWrapper {

    public MobileWebHomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@id='tsc-react-header-mobile']//input[@aria-label='search']")
    WebElement SEARCH_FIELD;

    @FindBy(xpath = "(//*[@aria-label='Search'])[3]")
    WebElement SEARCH_ICON;

    @FindBy(xpath = "(//div[@id='tsc-react-header-mobile']//button[contains(@class, 'MuiIconButton-sizeMedium') and @aria-label='Search']//div[contains(@class,'MuiBox-root css-4g6ai3')])[1]")
    WebElement SEARCH_MAGNIFIER_ICON;

    @FindBy(id = "onetrust-accept-btn-handler")
    WebElement ACCEPT_COOKIES;

    @FindBy(css = "#onetrust-close-btn-container button[aria-label=\'Close\']")
    WebElement CLOSE_COOKIES;

    @FindBy(xpath = "//button[@aria-label=\"Close banner\"]")
    WebElement CLOSE_BANNER;

    @FindBy(css = "div#tsc-react-header-mobile button[aria-controls='storeLocation']")
    WebElement STORE_LOGO;

    @FindBy(xpath = "(//input[@placeholder='Search by Zip, City or State'])[2]")
    WebElement SEARCH_BOX;

    @FindBy(xpath = "//*[@id='tsc-react-header-mobile']/header/div/a[1]")
    WebElement SELECT_MENU_BUTTON;

    @FindBy(css = "#zip-city-state")
    WebElement ZIP_CODE_TEXT_BOX;

    @FindBy(xpath = "(//a[text()=' Make My TSC Store'])[1]")
    WebElement CLICK_MAKE_MY_STORE_LINK;

    @FindBy(xpath = "(//button[@title=\"submit\"])[5]")
    WebElement CLICK_POPUP;

    @FindBy(xpath = "//a[@data-analytics-link=\"store locator|view map\"]")
    WebElement CLICK_VIEW_MAP;

    @FindBy(xpath = "//button[text()='Find']")
    WebElement CLICK_FIND;
    @FindBy(xpath = "//*[name()='svg']//*[local-name()='g' and @id=\"TSC_primary logo_2023\"]")
    WebElement HOME_PAGE_LOGO;

    @FindBy(css = "div#tsc-react-header-mobile button[aria-controls='storeLocation']")
    WebElement SELECT_STORE;

    @FindBy(xpath = "(//a[@data-analytics-link='store locator|view map'])[2]")
    WebElement VIEW_MAP_LINK;

    @FindBy(xpath = "(//div[@aria-label='Search']/following-sibling::div/div/div)[2]")
    WebElement SELECT_FIRST_SUGGESTION;

    @FindBy(xpath = "(//span[contains(@class,'MuiRadio-colorPrimary')])[11]")
    WebElement RADIO_BUTTON_FIRST;

    @FindBy(xpath = "(//button[contains(text(),'Update')])[2]")
    WebElement UPDATE_BUTTON;

    public void searchItem(String productToSearch){
        closeCookies();
        scrollUpByPixel();
        scrollToElement(waitForElementToRefresh(SEARCH_FIELD), true);
        enterByWebelement(waitForElementToRefresh(SEARCH_FIELD), productToSearch, "Entering the product name or sku to search");
        clickByWebelement(SEARCH_ICON, "Searching product for the user input data");
//        SEARCH_FIELD.sendKeys(Keys.ENTER);
    }

    public void menuButton(){
        webdriverWaitElementToBeVisible(SELECT_MENU_BUTTON);
        clickByWebelement(SELECT_MENU_BUTTON,"Clicking on Menu Button");
    }

    public void closeCookies(){
        if(waitUntil(() -> ACCEPT_COOKIES.isDisplayed(), 2))
            clickByJavascript(ACCEPT_COOKIES, "Closing cookies");
    }

    public void chooseStoreBasedOnZip(String Zipcode) {
        scrollUpByPixel();
        clickOnStoreIcon();
        enterTheDefaultStore(Zipcode);
        clickOnSuggestedStore();
        clickOnFirstRadioButton();
        clickOnUpdateButton();
    }


    public void clickOnStoreIcon(){
        if(waitUntil(()->SELECT_STORE.isDisplayed(), 5)){
            clickByWebelement(waitForElementToRefresh(SELECT_STORE),"Clicking on store icon from the banner");
            waitUntil(()-> VIEW_MAP_LINK.isDisplayed(), 3);
        }
    }

    public void enterTheDefaultStore(){
        enterTheDefaultStore("");
    }
    public void enterTheDefaultStore(String zip){
        if(StringUtils.isEmpty(zip))
            zip = "Exeter, NH, 03833"; //Default address
        enterByWebelement(waitForElementToRefresh(SEARCH_BOX),zip.trim(),"Entering store information");
        SEARCH_BOX.sendKeys(Keys.SPACE);
        SEARCH_BOX.sendKeys(Keys.BACK_SPACE);
    }

    public void clickOnSuggestedStore(){
        wait(1);
        scrollAndClick(SELECT_FIRST_SUGGESTION,"Select suggested store");
    }

    public void clickOnFirstRadioButton(){
        wait(2);
        scrollAndClick(RADIO_BUTTON_FIRST,"Clicking on Radio button");
    }

    public void clickOnUpdateButton(){
        scrollAndClick(UPDATE_BUTTON,"Clicking on Update button");
    }

    public void selectDefaultStore(){
        clickOnStoreIcon();
        enterTheDefaultStore();
        clickOnSuggestedStore();
        clickOnFirstRadioButton();
        clickOnUpdateButton();
    }

    public void verifyTitle(String title){
        Assert.assertTrue(verifyExpectedAndActualString(driver.getTitle(), title,"Verify the title",true));
    }
}
