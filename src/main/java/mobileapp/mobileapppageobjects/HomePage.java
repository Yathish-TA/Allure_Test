package mobileapp.mobileapppageobjects;


import genericwrappers.ActionType;
import genericwrappers.GenericWrapper;
import genericwrappers.LocatorType;
import genericwrappers.LocatorTypeIOS;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.qameta.allure.Allure;
import mobileutils.PageObjectBaseClassMobile;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class HomePage extends GenericWrapper {
    private static String deviceType;

    public PageObjectBaseClassMobile getData = new PageObjectBaseClassMobile();

    public HomePage(WebDriver driver, String deviceType) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver),this);
        HomePage.deviceType = deviceType;
    }

    @FindBy(xpath = "//div[@id='more-btn']")
    WebElement MOBILE_MORE_BUTTON;

    @FindBy(xpath = "//span[@id='ncHader_navLink_1_link_text']")
    WebElement ORDER_STATUS;

    @FindBy(xpath = "//input[@id='order-number']")
    WebElement ORDER_NUM_FIELD;

    @FindBy(xpath = "//input[@id='last-name']")
    WebElement LAST_NAME_FIELD;

    @FindBy(xpath = "//button[@id='viewOrderBtn']")
    WebElement CLICK_ORDER_BUTTON;

    @FindBy(xpath = "//div[@class='d-flex justify-content-between border-bottom mt-7']/h1")
    WebElement ORDER_DETAILS_PAGE;

    @FindBy(xpath = "//button[@id='viewOrderBtn']")
    WebElement VERIFY_STATUS;

    @FindBy(id= "//android.view.View[@content-desc='MANAGE MY CARD Button']")
    WebElement MANAGE_MY_CARD_BUTTON_ANDROID;

    @FindBy(xpath = "//*[@name='MANAGE']")
    WebElement MANAGE_MY_CARD_BUTTON_IOS;

    @FindBy(xpath = "//android.view.ViewGroup[contains(@content-desc,'cart button')]")
    WebElement CART_BUTTON;

    @FindBy(xpath = "//android.widget.EditText[@content-desc='Search Edit Box']/ancestor::android.view.ViewGroup[2]")
    WebElement SEARCH_TEXTBOX_ANDROID;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Sign In']")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='Sign In']")
    WebElement SIGN_IN_FROM_PROFILE;

    @FindBy(xpath = "//android.widget.EditText[@resource-id='TextInput']")
    WebElement TEXTINPUT_TEXTBOX_ANDROID;

    @FindBy(xpath = "//XCUIElementTypeOther[@name=\"Search Edit Box\"]")
    WebElement SEARCH_TEXTBOX_IOS;

    @FindBy(xpath = "//XCUIElementTypeOther[@name=\"TextInput\"]")
    WebElement TEXTINPUT_TEXTBOX_IOS;

    @AndroidFindBy(xpath = "//android.widget.Button[@text='While using the app']")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='Allow While Using App']")
    WebElement ALLOW_WHILE_USING_THIS_APP;

    @FindBy(xpath = "//XCUIElementTypeButton[@name=\"Return\"]")
    WebElement CLICK_ENTER_IOS;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@resource-id='Store Details Navigation1']")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name='Store Details Navigation1']")
    WebElement PROFILE_STORE_LOCATOR;

    @AndroidFindBy(xpath = "(//android.view.View[@content-desc='Profile'])")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='Profile']")
    WebElement MY_PROFILE_TAB;

    @FindBy(xpath = "//android.view.View[@content-desc='Home']")
    WebElement HOME_BUTTON_ANDROID;

    @FindBy(xpath = "//XCUIElementTypeButton[@name=\"Home\"]")
    WebElement HOME_BUTTON_IOS;

    @FindBy(xpath = "//android.widget.TextView[@text=\"Profile\"]")
    WebElement PROFILE_ICON_ANDROID;

    @FindBy(xpath = "//*[@name='Profile']")
    WebElement PROFILE_ICON_IOS;

    @FindBy(xpath = "//android.view.ViewGroup[contains(@content-desc,'cart button')]")
    WebElement SHOPPING_CART_ITEM;

    @FindBy(xpath = "//android.widget.ImageView[@content-desc='Back']")
    WebElement CANCEL_BUTTON;

    @FindBy(xpath = "//android.view.ViewGroup[@content-desc='Back Button']")
    WebElement BACK_ANDROID;

    @FindBy(xpath = "//android.view.ViewGroup[@content-desc='Back button']/android.widget.ImageView")
    WebElement STORE_BACK_ANDROID;

    @FindBy(xpath = "//android.view.ViewGroup[@content-desc='Agree button']/android.widget.TextView")
    WebElement AGREE_ANDROID;

    @FindBy(xpath = "//XCUIElementTypeOther[@name=\"Agree button\"]")
    WebElement AGREE_IOS;

    @FindBy(xpath = "//android.widget.TextView[@text='SET AS MY STORE']")
    WebElement SET_MY_STORE_ANDROID;

    @FindBy(xpath = "//XCUIElementTypeOther[@name=\"ANIMATED_HORIZONTAL_LIST\"]/*[1]/*[1]/*[1]/*[1]/*[1]/*[1]/*[1]/*[contains(@name,'Set as My Store')]")
    WebElement SET_MY_STORE_IOS;

    @FindBy(xpath = "//android.widget.ImageView[@content-desc='close stores tab button']")
    WebElement CLOSE_STORES;

    @FindBy(xpath = "//android.view.View[@content-desc='My Orders']")
    WebElement MY_ORDERS;

    @FindBy(xpath = "//android.widget.TextView[contains(@text,'My Store : ')]")
    WebElement MY_STORE;

    @FindBy(xpath = "//android.view.ViewGroup[@content-desc='Search button']")
    WebElement ZIP_SEARCH_BUTTON;

    @FindBy(xpath = "//android.view.ViewGroup[@content-desc='Back Button']/following-sibling::android.widget.EditText")
    WebElement SEARCH_ZIPCODE_ANDROID;

    @FindBy(xpath = "//XCUIElementTypeTextField[@type=\"XCUIElementTypeTextField\"]")
    WebElement SEARCH_BAR_INPUT_IOS;

    @FindBy(xpath = "//android.widget.TextView[@content-desc='SEE STORES']")
    WebElement SEE_STORES_BUTTON_ANDROID;

    @FindBy(xpath = "//XCUIElementTypeOther[@name=\"SEE STORES button\"]")
    WebElement SEE_STORES_BUTTON_IOS;

    @FindBy(xpath = "//android.widget.Image[@text=\"Tractor Supply Company\"]")
    WebElement MY_PLCC_PAGE_VERIFY_ANDROID;

    @FindBy(xpath = "//*[@name=\"Tractor Supply Company\"]")
    WebElement MY_PLCC_PAGE_VERIFY_IOS;

    @FindBy(xpath = "//android.widget.TextView[@text=\"LEARN MORE\"]")
    WebElement LEARN_MORE_BUTTON_ANDROID;

    @FindBy(xpath = "//*[@name=\"LEARN\"]")
    WebElement LEARN_MORE_BUTTON_IOS;

    @FindBy(xpath = "//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup[3]")
    WebElement PLCC_OFFERS_PAGE_ANDROID;

    @FindBy(xpath = "(//*[@name='About TSC Credit Cards'])[1]")
    WebElement PLCC_OFFERS_PAGE_IOS;

    @FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"Close button\"]/android.widget.ImageView")
    WebElement CLOSE_PLCC_PAGE_ANDROID;

    @FindBy(xpath = "(//*[@name='Close button'])[2]")
    WebElement CLOSE_PLCC_PAGE_IOS;

    @FindBy(xpath = "//android.view.View[@content-desc=\"Recently Viewed\"]")
    WebElement RECENTLY_VIEWED;

    @FindBy(xpath = "//android.view.View[@content-desc=\"Recommended Categories For You\"]")
    WebElement RECOMMENDED_CATEGORIES;

    public String searchSKUAndroid(String SKU) {
        return "//android.view.ViewGroup[@content-desc='" + SKU + "']";
    }

    public String searchSKUIOS(String SKU) {
        return "//XCUIElementTypeOther[@name='" + SKU + "']";
    }

    @FindBy(xpath = "//android.widget.TextView[@content-desc=\"Thank You for being a  Preferred Plus Neighbor\"]")
    WebElement NC_PROGRESS_SECTION_HEADER_ANDROID;

    @FindBy(xpath = "(//XCUIElementTypeStaticText[@name=\"Thank You for being a  Preferred Plus Neighbor\"])[2]")
    WebElement NC_PROGRESS_SECTION_HEADER_IOS;

    @FindBy(xpath = "//android.widget.ImageView[@content-desc=\"Preferred Plus Neighbor logo\"]")
    WebElement NC_LOGO_ANDROID;

    @FindBy(xpath = "(//XCUIElementTypeOther[@name=\"Preferred Plus Neighbor logo\"])[2]")
    WebElement NC_LOGO_IOS;

    @FindBy(xpath = "//android.widget.TextView[@text=\"VIEW REWARDS\"]")
    WebElement VIEW_REWARDS_ANDROID;

    @FindBy(xpath = "//XCUIElementTypeOther[@name='VIEW REWARDS']")
    WebElement VIEW_REWARDS_IOS;

    @FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"VIEW NEIGHBORS CLUB ACCOUNT\"]")
    WebElement VIEW_NEIGHBORS_CLUB_ACCOUN_ANDROID;

    @FindBy(xpath = "//*[@name=\"VIEW NEIGHBORS CLUB ACCOUNT\"]")
    WebElement VIEW_NEIGHBORS_CLUB_ACCOUNT_IOS;

    @FindBy(xpath = "//android.widget.TextView[contains(@content-desc,\"You have earned\")]")
    WebElement ACTIVE_POINTS_ANDROID;

    @FindBy(xpath = "//XCUIElementTypeOther[@name=\"Tier Progress\"]")
    WebElement ACTIVE_POINTS_IOS;

    @FindBy(xpath = "//android.widget.TextView[@content-desc=\"Your Reward Progress\"]")
    WebElement YOU_REWARD_PROGRESS;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name=\"SavedPayments\"]")
    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc=\"Wallet\"]")
    WebElement WALLET;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='Clear Text button']/android.widget.ImageView")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name=\"Clear Text button\"]")
    WebElement CLEAR_TEXT_BOX_SEARCH;

    @FindBy(xpath = "//android.widget.TextView[@text=\"MY STORE\"]")
    WebElement EXISTING_MY_STORE_ANDROID;

    @FindBy(xpath = "//XCUIElementTypeStaticText[@name=\"MY STORE\"]")
    WebElement EXISTING_MY_STORE_IOS;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"My Pet\"]")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"My Pet\"]")
    WebElement MY_PET_TAB;

    @AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc='Tractor supply Co logo']")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeImage[@name=\"assets/app/resources/Images/appLogo/TSC_home_logo@3x.png\"] | //XCUIElementTypeOther[@name='Tractor supply Co logo']")
    public WebElement TRACTOR_SUPPLY_CO_LOGO;

    @AndroidFindBy(xpath="//*[@text=\"HIDE\"]")
    @iOSXCUITFindBy(xpath="(//XCUIElementTypeButton[@name=\"HIDE\"])[1]")
    public WebElement HIDE;

    @FindBy(xpath = "//*[contains(@text,'NO THANKS')]")
    WebElement NO_THANKS;

    public void clickMoreButton() {
        clickByWebelement(MOBILE_MORE_BUTTON, "More Options is displayed");
    }

    public void clickOrderStatus() {
        clickByWebelement(ORDER_STATUS, "Order Status page is Loaded");
    }

    public void enterOrderInfo() {
        enterByWebelement(ORDER_NUM_FIELD, getData.getDatavalue("orderNumber"), "Passing the Value for Order Number");
        enterByWebelement(LAST_NAME_FIELD, getData.getDatavalue("orderName"), "Passing the Value for Last Name");
    }


    public void clickOrderButton() {
        scrollToElement(VERIFY_STATUS);
        clickByWebelement(VERIFY_STATUS, "Clicking on View Order");

    }

    public void verifyOrderDetailsPage() {
        clickByWebelement(VERIFY_STATUS, "Order details page loaded");
        String text = ORDER_DETAILS_PAGE.getText();
        validationWithDatabase(getData.returnDataMap(),text);
    }

    public void isHomePageDisplayed() {
        timeOut(20000);
        if(waitUntil(()->checkIsDisplayed(ALLOW_WHILE_USING_THIS_APP), 5))
            clickByWebelement(ALLOW_WHILE_USING_THIS_APP, "Clicking on Allow option in Location access dialog");
        webdriverWaitElementToBeVisible(TRACTOR_SUPPLY_CO_LOGO);
        Assert.assertTrue(isElementDisplayed(TRACTOR_SUPPLY_CO_LOGO,"Tractor supply Co logo"));
    }

    public void verifyManageMyPlcCardIsDisplayed()
    {
        switch (deviceType) {
            case "ANDROID":
                scrollToElementAndPerformAction(LocatorType.CONTENT_DESC,"MANAGE MY CARD Button", ActionType.SCROLL);
                isElementDisplayed(MANAGE_MY_CARD_BUTTON_ANDROID, "MANAGE MY CARD Button");
                break;
            case "IOS":
                scrollToElementUsingScrollIOS(MANAGE_MY_CARD_BUTTON_IOS);
                isElementDisplayed(MANAGE_MY_CARD_BUTTON_IOS,"MANAGE MY CARD Button");
                break;
        }

    }

    public void clickManageMyCardButton(){
        switch (deviceType){
            case "ANDROID":
                scrollToElementAndPerformAction(LocatorType.CONTENT_DESC,"MANAGE MY CARD Button", ActionType.CLICK);
                break;
            case "IOS":
                scrollToElementUsingScrollIOS(MANAGE_MY_CARD_BUTTON_IOS);
                clickByWebelement(MANAGE_MY_CARD_BUTTON_IOS, "Click on Manage My Card button");
        }
    }

    public void verifySignInIsDisplayed() {
        Assert.assertTrue(checkIsDisplayed(SIGN_IN_FROM_PROFILE));
    }

    public void clickOnSignInButtonFromProfile() {
        clickElementWithFluentWait(SIGN_IN_FROM_PROFILE, "Clicking on Sign In button");
    }

    public void enterSKUValueAndSearch(String skuId){
        switch (deviceType) {
            case "ANDROID":
                if (checkIsDisplayed(SEARCH_TEXTBOX_ANDROID)) {
                    clickByWebelement(SEARCH_TEXTBOX_ANDROID, "Clicked on Text Input Box");
                }
                if (checkIsDisplayed(CLEAR_TEXT_BOX_SEARCH)) {
                    clickByWebelement(CLEAR_TEXT_BOX_SEARCH, "Clicked on Clear Text Input Box");
                }
                waitForElementVisible(LocatorType.RESOURCEID, "TextInput");
                enterByWebelement(TEXTINPUT_TEXTBOX_ANDROID, skuId, "Entered SKU value");
                pressKey(TEXTINPUT_TEXTBOX_ANDROID, Keys.ENTER);
                timeOut(5000);
                break;
            case "IOS":
                waitForElement(SEARCH_TEXTBOX_IOS,6);
                if (checkIsDisplayed(SEARCH_TEXTBOX_IOS)) {
                    clickByWebelement(SEARCH_TEXTBOX_IOS, "Clicked on Text Input Box");
                }
                if (checkIsDisplayed(CLEAR_TEXT_BOX_SEARCH)) {
                    clickByWebelement(CLEAR_TEXT_BOX_SEARCH, "Clicked on Clear Text Input Box");
                }
                waitForElementVisible(LocatorTypeIOS.NAME, "TextInput");
                if(waitUntil(()->checkIsDisplayed(ALLOW_WHILE_USING_THIS_APP), 5))
                    clickByWebelement(ALLOW_WHILE_USING_THIS_APP, "Clicking on Allow option in Location access dialog");
                enterByWebelement(TEXTINPUT_TEXTBOX_IOS, skuId, "Entered SKU value");
                clickElementWithFluentWait(CLICK_ENTER_IOS, "Click on enter");
                break;
        }
    }

    public void clickOnCartButton() {
        clickByWebelement(CART_BUTTON, "Clicked on Cart Button");
    }

    public String profileStore(){
        clickByWebelement(MY_PROFILE_TAB,"Going to my profile");
        webdriverWaitElementToBeVisible(PROFILE_STORE_LOCATOR);
        String profile_store=getText(PROFILE_STORE_LOCATOR);
        return profile_store;
    }

    public void goToProfile(){
        clickByWebelement(MY_PROFILE_TAB,"Going to my profile");
    }

    public void clickhomePage(){
        switch (deviceType){
            case "ANDROID":
                webdriverWaitElementToBeVisible(HOME_BUTTON_ANDROID);
                clickByWebelement(HOME_BUTTON_ANDROID, "Clicking on Home");
                break;
            case "IOS":
                clickByWebelement(HOME_BUTTON_IOS,"Clicking on Home");
                break;
        }
    }

    public void clickOnProfileIcon () {
        switch (deviceType){
            case "ANDROID":
                clickByWebelement(PROFILE_ICON_ANDROID, "Clicked on Profile");
                break;
            case "IOS":
                clickByWebelement(PROFILE_ICON_IOS, "Clicked on Profile");
                break;
        }
    }

    public void removeCartItems(){
        clickByWebelement(SHOPPING_CART_ITEM,"Clicking on cart icon");
        waitForElementVisible(LocatorType.TEXT, "SAVE FOR LATER");
        clickElementsByXpath("//android.view.ViewGroup[contains(@content-desc,'Delete Button')]");
        clickByWebelement(CANCEL_BUTTON,"Coming out of shopping cart");
    }
    public void clickMyOrders(){
        clickByWebelement(MY_ORDERS,"Clicking on My Orders");
    }

    public void changeStore(String zipcode){
        switch (deviceType){
            case "ANDROID":
                timeOut(5000);
                scrollToElementUntilVisible(MY_STORE);
                clickByWebelement(MY_STORE,"Clicking on current store");
                clickByWebelement(ZIP_SEARCH_BUTTON, "Clicked on search button");
                clickByWebelement(SEARCH_ZIPCODE_ANDROID, "Clicked on Text Input Box");
                enterByWebelement(SEARCH_ZIPCODE_ANDROID, zipcode, "Entering ZIP value");
                pressKey(SEARCH_ZIPCODE_ANDROID, Keys.ENTER);
                webdriverWaitElementToBeVisible(SET_MY_STORE_ANDROID);
                if(checkIsDisplayed(EXISTING_MY_STORE_ANDROID))
                {
                    clickByWebelement(HOME_BUTTON_ANDROID,"Clicking on Home");
                }
                else {
                    clickByWebelement(SET_MY_STORE_ANDROID, "setting my store as west monroe");
                    clickByWebelement(AGREE_ANDROID, "Clicking on agree button");
                    clickByWebelement(CLOSE_STORES, "Clicking on close stores");
                    webdriverWaitElementToBeVisible(BACK_ANDROID);
                    clickByWebelement(BACK_ANDROID, "Clicking back");
                    clickByWebelement(STORE_BACK_ANDROID, "Clicking back");
                }
                CMAswipeUp(2);
                break;
            case "IOS":
                scrollToElementUsingScrollIOS(SEE_STORES_BUTTON_IOS);
                clickByWebelement(SEE_STORES_BUTTON_IOS,"Clicking on See Stores");
                waitForElement(SEARCH_TEXTBOX_IOS,10);
                clickByWebelement(SEARCH_TEXTBOX_IOS,"Clicking on search bar");
                enterByWebelement(SEARCH_BAR_INPUT_IOS,zipcode,"Entering Zip code");
                KeyBoardEnterIOS();
                timeOut(6000);
                if(checkIsDisplayed(EXISTING_MY_STORE_IOS))
                {
                    clickByWebelement(HOME_BUTTON_IOS,"Clicking on Home");
                }
                else
                {
                    clickByWebelement(SET_MY_STORE_IOS,"Clicking on Set My Store");
                    if(waitUntil(()->checkIsDisplayed(AGREE_IOS), 5))
                        clickByWebelement(AGREE_IOS, "Clicking on agree button");
                    clickByWebelement(HOME_BUTTON_IOS,"Clicking on Home");
                }
                CMAswipeUp(2);
                break;
        }
    }

    public void verifySeeStoresButton()
    {
     checkIsDisplayed(SEE_STORES_BUTTON_ANDROID);
    }

    public void clickOnSeeStores() {
        switch (deviceType) {
                case "ANDROID":
                waitForElement(SEE_STORES_BUTTON_ANDROID,10);
                scrollToElementAndPerformAction(LocatorType.CONTENT_DESC,"SEE STORES",ActionType.SCROLL);
                clickByWebelement(SEE_STORES_BUTTON_ANDROID,"Clicking on See Stores");
                waitForElementVisible(LocatorType.TEXT,"DETAILS");
                break;
            case "IOS":
                scrollToElementUsingScrollIOS(SEE_STORES_BUTTON_IOS);
                clickByWebelement(SEE_STORES_BUTTON_IOS,"Clicking on See Stores");
                break;
        }
    }

    public void verifyMyPLCCPageIsDisplayed()
    {
        switch (deviceType) {
            case "ANDROID":
                if(checkIsDisplayed(MY_PLCC_PAGE_VERIFY_ANDROID)) {
                    checkIsDisplayed(MY_PLCC_PAGE_VERIFY_ANDROID);
                }
                else {
                    Allure.step("Took more than Expected time to Load My PLCC Page");
                }
                break;
            case "IOS":
                if(checkIsDisplayed(MY_PLCC_PAGE_VERIFY_IOS)) {
                    checkIsDisplayed(MY_PLCC_PAGE_VERIFY_IOS);
                }
                else {
                    Allure.step("Took more than Expected time to Load My PLCC Page");
                }
                break;
        }

    }

    public void verifyLearnMoreButtonDisplayed()
    {
        switch (deviceType) {
            case "ANDROID":
                checkIsDisplayed(LEARN_MORE_BUTTON_ANDROID);
                break;
            case "IOS":
                checkIsDisplayed(LEARN_MORE_BUTTON_IOS);
                break;
        }

    }

    public void clickOnLearnMoreButton()
    {
        switch (deviceType){
            case "ANDROID":
                scrollToElementAndPerformAction(LocatorType.TEXT, "LEARN MORE",ActionType.CLICK);
                webdriverWaitElementToBeVisible(PLCC_OFFERS_PAGE_ANDROID);
                break;
            case "IOS":
                scrollToElementUsingScrollIOS(LEARN_MORE_BUTTON_IOS);
                clickByWebelement(LEARN_MORE_BUTTON_IOS, "Click on Learn More Button");
                webdriverWaitElementToBeVisible(PLCC_OFFERS_PAGE_IOS);
                break;
        }

    }

    public void verifyPLCCOffersDisplayed()
    {
        switch (deviceType) {
            case "ANDROID":
                checkIsDisplayed(PLCC_OFFERS_PAGE_ANDROID);
                break;
            case "IOS":
                checkIsDisplayed(PLCC_OFFERS_PAGE_IOS);
                break;
        }
    }

    public void clickOnClosePLCC()
    {
        switch (deviceType) {
            case "ANDROID":
                clickByWebelement(CLOSE_PLCC_PAGE_ANDROID,"Clicking on PLCC page Close icon");
                break;
            case "IOS":
                clickByWebelement(CLOSE_PLCC_PAGE_IOS,"Clicking on PLCC page Close icon");
                break;
        }

    }


    public void verifyRecentlyViewedDisplayed()
    {
        switch (deviceType) {
            case "ANDROID":
                scrollToElementAndPerformAction(LocatorType.CONTENT_DESC,"Recently Viewed",ActionType.SCROLL);
                checkIsDisplayed(RECENTLY_VIEWED);
                break;
            case "IOS":
                break;
        }

    }

    public void verifyRecommendedCategoriesDisplayed()
    {
        switch (deviceType) {
            case "ANDROID":
                scrollToElementAndPerformAction(LocatorType.CONTENT_DESC,"Recommended Categories For You",ActionType.SCROLL);
                checkIsDisplayed(RECOMMENDED_CATEGORIES);
                break;
            case "IOS":
                break;
        }
    }

    public void swipeUpToTopPage()
    {
        switch (deviceType) {
            case "ANDROID":
                scrollToElementAndPerformAction(LocatorType.CONTENT_DESC,"My Orders",ActionType.SCROLL);
                webdriverWaitElementToBeVisible(SEARCH_TEXTBOX_ANDROID);
                break;
            case "IOS":
                scrollToElementUsingScrollIOS(SEARCH_TEXTBOX_IOS);
                webdriverWaitElementToBeVisible(SEARCH_TEXTBOX_IOS);
                break;
        }
    }

    public void clickOnSearchBar()
    {
        switch (deviceType) {
            case "ANDROID":
                clickByWebelement(SEARCH_TEXTBOX_ANDROID, "Clicked on Text Input Box");
                break;
            case "IOS":
                clickByWebelement(SEARCH_TEXTBOX_IOS, "Clicked on Text Input Box");
                break;
        }

    }

    public void verifyTextIsDisplayedInRecentSearch(String SKU)
    {
        switch (deviceType) {
            case "ANDROID":
                WebElement SKU_SEARCHED_ANDROID = getElementByXpath(searchSKUAndroid(SKU));
                checkIsDisplayed(SKU_SEARCHED_ANDROID);
                break;
            case "IOS":
                WebElement SKU_SEARCHED_IOS = getElementByXpath(searchSKUIOS(SKU));
                checkIsDisplayed(SKU_SEARCHED_IOS);
                break;
        }

    }

    public void verifyNCProgressSectionDisplayed()
    {
        switch (deviceType) {
            case "ANDROID":
                scrollToElementAndPerformAction(LocatorType.CONTENT_DESC,"Thank You for being a  Preferred Plus Neighbor",ActionType.SCROLL);
                checkIsDisplayed(NC_PROGRESS_SECTION_HEADER_ANDROID);
                break;
            case "IOS":
                scrollToElementUsingScrollIOS(NC_PROGRESS_SECTION_HEADER_IOS);
                checkIsDisplayed(NC_PROGRESS_SECTION_HEADER_IOS);
                break;
        }

    }

    public void clickOnViewRewards()
    {
        switch (deviceType) {
            case "ANDROID":
                scrollToElementAndPerformAction(LocatorType.CONTENT_DESC,"VIEW REWARDS",ActionType.SCROLL);
                clickByWebelement(VIEW_REWARDS_ANDROID,"Clicking on View Rewards");
                break;
            case "IOS":
                scrollToElementUsingScrollIOS(VIEW_REWARDS_IOS);
                clickByWebelement(VIEW_REWARDS_IOS,"Clicking on View Rewards");
                break;
        }

    }


    public void clickOnNCAccount(){
        switch (deviceType) {
            case "ANDROID":
                scrollToElementAndPerformAction(LocatorType.CONTENT_DESC,"VIEW NEIGHBORS CLUB ACCOUNT",ActionType.CLICK);
                break;
            case "IOS":
                scrollToElementUsingScrollIOS(VIEW_NEIGHBORS_CLUB_ACCOUNT_IOS);
                clickByWebelement(VIEW_NEIGHBORS_CLUB_ACCOUNT_IOS,"Click on NC Account");
                break;
        }
    }

    public void verifyNCLogoIsDisplayed()
    {
        switch (deviceType) {
            case "ANDROID":
                checkIsDisplayed(NC_LOGO_ANDROID);
                break;
            case "IOS":
                checkIsDisplayed(NC_LOGO_IOS);
                break;
        }
    }

    public void getActivePoints()
    {
        switch (deviceType) {
            case "ANDROID":
                String Active_Points_Android = getText(ACTIVE_POINTS_ANDROID);
                Allure.addAttachment("Active Points:", Active_Points_Android);
                break;
            case "IOS":
                String Active_Points_IOS = getText(ACTIVE_POINTS_IOS);
                Allure.addAttachment("Active Points:", Active_Points_IOS);
                break;
        }
    }

    public void userClickOnRewardButton(){
        scrollToElementAndPerformAction(LocatorType.CONTENT_DESC,"VIEW REWARDS",ActionType.CLICK);
    }

    public void clickOnMyPetTab()
    {
        clickByWebelement(MY_PET_TAB,"Clicking on My Pet Page tab");
    }

    public void verifyHide(){
        switch(deviceType){
            case "ANDROID":
                timeOut(3000);
                scrollToElementUntilVisible(HIDE);
                Assert.assertTrue(HIDE.isDisplayed());
                break;
            case "IOS":
                timeOut(3000);
                scrollToElementUsingScrollIOS(HIDE);
                Assert.assertTrue(HIDE.isDisplayed());
        }
    }

    public void closeFeedbackPopup() {
        timeOut(3000);
        if (checkIsDisplayed(NO_THANKS)) {
            clickByWebelement(NO_THANKS, "Feedback is closed");
        }
    }

    public void swipeUpAndClickWallet() {
        switch (deviceType) {
            case "ANDROID":
                timeOut(12000);
                CMAswipeUp(2);
                timeOut(9000);
                if (checkIsDisplayed(WALLET)) {
                    clickByWebelement(WALLET, "Click on Wallet");
                } else {
                    scrollToElementUntilVisible(WALLET);
                    clickByWebelement(WALLET, "Click on Wallet");
                }
                break;
            case "IOS":
                timeOut(9000);
                if (checkIsDisplayed(WALLET)) {
                    clickByWebelement(WALLET, "Click on Wallet");
                } else {
                    scrollToElementUsingScrollIOS(WALLET);
                    clickByWebelement(WALLET, "Click on Wallet");
                }
                break;
        }
    }
}
