package web.pageobjects;

import genericwrappers.GenericWrapper;
import helperfunctions.CsvDataReader;
import helperfunctions.CsvRowData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;
import webutils.LoadProperties;

public class TSCProdPage extends GenericWrapper {

    CsvDataReader csvDataReader = new CsvDataReader();

    public TSCProdPage(WebDriver driver){
        super(driver); // Pass the WebDriver instance to the GenericWrapper constructor
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Cookies pop up or iFrame locator's
    @FindBy(xpath = "//*[@id='onetrust-close-btn-container']/button")
    WebElement CLOSE_COOKIES_POP_UP;

    @FindBy(xpath = "//*[@id=\"attentive_creative\"]")
    WebElement SIGN_UP_POP_UP_IFRAME;

    @FindBy(xpath = "//*[@id=\"closeIconSvg\"]")
    WebElement CLOSE_SIGN_UP_IFRAME;

    @FindBy(xpath = "//div//label[contains(@class,'MuiFormLabel-root MuiFormLabel')]")
    WebElement NAV_BAR_INNER_TEXT;

    @FindBy(xpath = "//div//label[contains(@class,'MuiFormLabel-root MuiFormLabel')]//following-sibling::a")
    WebElement SHOP_ALL_TEXT;

    @FindBy(xpath = "//button[@data-testid=\"moreCategoriesButton\"]")
    WebElement MORE_CATEGORY_BUTTON;

    @FindBy(xpath="//a[@aria-label=\"Tractor Supply Company\"]")
    WebElement HOME_LOGO;

    @FindBy(xpath = "//div[@class='MuiBox-root css-19s7gcj']")
    WebElement MY_STORE_LINK;

    @FindBy(xpath = "(//button[@aria-label=\"Search\"])[1]")
    WebElement SEARCH_ICON;

    @FindBy(xpath = "//input[@placeholder='What can we help you find?']")
    WebElement SEARCH_BAR;

    @FindBy(xpath = "(//button[@aria-label=\"Search by Voice\"])[1]")
    WebElement SEARCH_BY_VOICE;

    @FindBy(xpath = "//button[@id='userButton']")
    WebElement SIGN_IN_LOGO;

    @FindBy(xpath = "//a[@aria-label=\" item in cart\"]")
    WebElement CART_LOGO;


    private WebElement moreCategoryItems(String item){
        String xpath="//ul//li//div[text()='"+item+"']";
        return  driver.findElement(By.xpath(xpath));
    }

    private WebElement getNavBarButton(String navbarLink) {
        String xpath = "//button[@data-analytics-link='" + navbarLink + "']";
        return driver.findElement(By.xpath(xpath));
    }

    private WebElement footerLinksName(String item){
        String xpath="(//a[@data-analytics-link='"+item+"'])[1]";
        return  driver.findElement(By.xpath(xpath));
    }

    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }

    public void scrollToFooter(){
        scrolldownTillEnd();
        timeOut(3000);
    }

    public void verifyNavBarIsVisible(String testCaseId) {
        String[] navbarItems = csvDataReader.getTestData("TSCTestDatafilePath",testCaseId,"NavBarName");
        for (int i = 0; i < navbarItems.length; i++) {
            webdriverWaitElementToBeVisible(getNavBarButton(navbarItems[i]));
            clickByWebelement(getNavBarButton(navbarItems[i]), "NavBarName");
            Assert.assertEquals(getTextByWebElement(NAV_BAR_INNER_TEXT), navbarItems[i]);
            Assert.assertEquals(getTextByWebElement(SHOP_ALL_TEXT), "Shop All");
        }
    }


    public void clickMoreCategoryButton(){
        clickByWebelement(MORE_CATEGORY_BUTTON,"MoreCategoryButton");
    }

    public void verifyMoreCategoryItemsIsVisible(String testCaseId){
        String[] categories = csvDataReader.getTestData("TSCTestDatafilePath",testCaseId,"MoreCategory");
        for (int i = 0; i < categories.length; i++) {
            Assert.assertEquals(getTextByWebElement(moreCategoryItems(categories[i])), categories[i]);
        }
    }

    public void verifyFooterIcon(String testCaseId) {
        String[] footerName = csvDataReader.getTestData("TSCTestDatafilePath",testCaseId,"FooterLinksName");
        for (int i = 0; i < footerName.length; i++) {
            webdriverWaitElementToBeVisible(footerLinksName(footerName[i]));
        }
    }

    public void isCompanyLogoDisplayed(){ elementIsDisplayed(HOME_LOGO, "Company logo is displayed"); }

    public void isStoreLinkDisplayed(){ elementIsDisplayed(MY_STORE_LINK, "My store link is available"); }

    public void isSearchBarDisplayed(){
        elementIsDisplayed(SEARCH_ICON, "Search icon link is available");
        elementIsDisplayed(SEARCH_BAR, "Search bar is available");
        elementIsDisplayed(SEARCH_BY_VOICE, "Search by voice is available");
    }
    public void isSignInLogoDisplayed(){ elementIsDisplayed(SIGN_IN_LOGO, "Sign in link is available"); }

    public void isCartLogoDisplayed(){ elementIsDisplayed(CART_LOGO, "Cart link is available"); }

    public void verifyFooterIcon(String[] footerName){
        for(int i = 0; i< footerName.length; i++){
            webdriverWaitElementToBeVisible(footerLinksName(footerName[i]));
        }
    }


    // Handling Cookies and Sign Up Pop_up windows
    public void closeCookies(){
        try{
            if(CLOSE_COOKIES_POP_UP.isDisplayed() || CLOSE_COOKIES_POP_UP.isEnabled()){
                clickElementWithFluentWait(CLOSE_COOKIES_POP_UP,"Closing the Cookies Pop-Up");
            }
        }catch (Exception e){
            Reporter.log("Cookies pop-up not populated");
        }
    }

    public void closeSignUpPopUp(){
        try {
            waitForPopupAndCloseIfPresent(SIGN_UP_POP_UP_IFRAME, CLOSE_SIGN_UP_IFRAME);
        }catch(Exception e){
            Reporter.log("iFrame Pop-up is Not displayed");
        }
    }

}
