package mobileapp.mobileapppageobjects;

import genericwrappers.ActionType;
import genericwrappers.GenericWrapper;
import genericwrappers.LocatorType;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class SearchPage extends GenericWrapper {
    private static String deviceType;

    @FindBy(xpath = "//android.widget.TextView[@text='Sort & Filter'] | //android.widget.TextView[contains(@text,'Filter')]")
    WebElement SORT_AND_FILTER_ANDROID;

    @FindBy(xpath = "//*[@name=\"IconContainer Sort & Filter\"]")
    WebElement SORT_AND_FILTER_IOS;

    @FindBy(xpath = "//android.widget.TextView[@text='Sort By']")
    WebElement SORT_BY_ANDROID;

    @FindBy(xpath = "//XCUIElementTypeOther[@name=\"Sort By \uE96B\"]")
    WebElement SORT_BY_IOS;

    @FindBy(xpath = "//android.widget.TextView[@text='Apply']")
    WebElement APPLY_FILTER_ANDROID;

    @FindBy(xpath = "//XCUIElementTypeOther[@name=\"Apply\"]")
    WebElement APPLY_FILTER_IOS;

    @FindBy(xpath = "//android.widget.TextView[@content-desc=\"How do you want your items? \"]")
    WebElement SHOP_HOW_DO_YOU_WANT_ITEMS;

    @FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"dropDown button expand\"]/android.widget.ImageView")
    WebElement SHOP_HOW_DO_YOU_WANT_ITEMS_DROPBOX;

    @FindBy(xpath = "//android.view.ViewGroup[@content-desc='Free Pickup in Store']")
    WebElement HOW_DO_YOU_WANT_FREE_PICK_UP_OPTION;

    @FindBy(xpath = "//android.widget.Button[@content-desc='Close']")
    WebElement HOW_DO_YOU_WANT_CLOSE_BUTTON;

    @FindBy(xpath = "//android.view.ViewGroup[@content-desc='Home Delivery']")
    WebElement HOW_DO_YOU_WANT_HOME_DELIVERY_OPTION;

    @FindBy(xpath = "//android.widget.TextView[contains(@content-desc,'TSC of')]")
    WebElement HOW_DO_YOU_WANT_STORE_NAME;

    @FindBy(xpath = "//android.view.ViewGroup[@resource-id='GoToStore']")
    WebElement HOW_DO_YOU_WANT_GOTO_STORE_BUTTON;

    @FindBy(xpath = "//android.widget.EditText[@text='Brentwood, TN, 37027']")
    WebElement ENTER_STORE_TEXT_BOX;

    @FindBy(xpath = "(//android.widget.TextView[@text='SET AS MY STORE'])[1] ")
    WebElement SET_AS_MY_STORE_BUTTON;

    @FindBy(xpath = "//android.widget.TextView[@text='AGREE']")
    WebElement STORE_SET_CONFIRMATION;

    @FindBy(xpath = "//android.widget.ImageView[@content-desc='close stores tab button']")
    WebElement STORE_TAB_CLOSE_BUTTON;

    @FindBy(xpath = "//android.view.ViewGroup[@content-desc='Back Button']/android.view.ViewGroup/android.widget.ImageView")
    WebElement STORE_TEXT_LAYOVER_CLOSE_BUTTON;

    @FindBy(xpath = "//android.widget.ScrollView/android.view.ViewGroup//android.view.ViewGroup[contains(@content-desc,'')]")
    WebElement SUGGESTED_STORE_NAME;
    @FindBy(xpath = "//android.widget.TextView[@text='Close']")
    WebElement FILTER_CLOSE_BUTTON;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='Compare 2 items button enabled']")
    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeOther[@name=\"Compare 2 items button enabled\"])[2]")
    WebElement COMPARE_BUTTON;

    @FindBy(xpath="(//android.widget.ImageView[@content-desc=\"mainImage\"])[1]//following::android.widget.TextView[1]")
    WebElement FIRST_PRODUCT;

    @FindBy(xpath="//android.widget.TextView[contains(@text,'result')]")
    WebElement ITEMCOUNT;

    @FindBy(xpath="(//android.view.ViewGroup[contains(@content-desc,'Snoozer')]//android.widget.TextView)[last()]")
    WebElement BRANDCOUNT;

    @FindBy(xpath="(//android.widget.TextView[contains(@content-desc,'dollars')])[1]")
    WebElement FIRST_PRODUCT_PRICE;

    @FindBy(xpath="(//android.widget.TextView[contains(@content-desc,'dollars')])[2]")
    WebElement SECOND_PRODUCT_PRICE;

    @FindBy(xpath="//android.widget.TextView[@text=\"Clear All\"]")
    WebElement CLEAR_ALL;

    @FindBy(xpath="//android.widget.TextView[@text=\"Confirm\"]")
    WebElement CONFIRM;

    @FindBy(xpath = "//android.widget.EditText[@content-desc='Search Edit Box']/ancestor::android.view.ViewGroup[2]")
    WebElement SEARCH_TEXTBOX;

    @FindBy(xpath = "//android.widget.EditText[@resource-id='TextInput']")
    WebElement TEXTINPUT_TEXTBOX;

    @FindBy(xpath = "//android.widget.TextView[contains(@text,'DID YOU MEAN')]/following::android.widget.TextView[1]")
    WebElement FIRST_SEARCH_SUGGESTION_NAME;

    @FindBy(xpath = "//android.widget.TextView[contains(@text,'DID YOU MEAN')]/./following-sibling::*[2]/*[1]")
    WebElement FIRST_SEARCH_SUGGESTION_SEARCH_BAR;

    @AndroidFindBy(xpath = "(//*[contains(@content-desc,\"main\")])[1]")
    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeOther[@name=\"mainImage\"])[2]/XCUIElementTypeImage")
    WebElement FIRST_LIVEBIRD_SUGGESTION;

    @AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc=\"choose options button\"])[1]")
    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeOther[@name=\"CHOOSE OPTIONS\"])[1]")
    WebElement FIRST_CHOOSE_OPTION;

    @AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc=\"add to cart button\"])[1]")
    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeOther[@name=\"ADD TO CART\"])[1]")
    WebElement FIRST_ADD_TO_CART_PLP;

    @FindBy(xpath = "(//android.widget.ImageView[@content-desc='mainImage'])[1]//following::android.widget.TextView[1]")
    WebElement FIRST_ITEM_NAME;

    @FindBy(xpath = "(//android.widget.ImageView[@content-desc=\"mainImage\"])[2]//following::android.widget.TextView[1]")
    WebElement SECOND_ITEM_NAME;

    @FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"Search term\"]")
    WebElement SEARCH_TERM;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"eGift Card\"]")
    @iOSXCUITFindBy(xpath="//XCUIElementTypeButton[@name=\"Product name: eGift Card. , Product Availability Status: Email Delivery. Price range from $5 to $2000\"]")
    WebElement EGIFT_CARD;

    static String storeNumberBefore = null;
    static String ItemCount = null;
    static String ItemCountwithFilter= null;
    static String BrandCount = null;
    static String firstSuggestionName = null;
    static String firstitemname = null;
    static String seconditemname = null;
    static String searchterm = null;

    private WebElement compareLineNumber(String compareNumber) {
        String xpath="";
        if (deviceType.equals("ANDROID")) {
             xpath = "(//android.widget.Button[contains(@content-desc,'Compare')]/android.view.ViewGroup/android.widget.ImageView)[" + compareNumber + "]";
        }
        else if (deviceType.equals("IOS")) {
             xpath = "(//XCUIElementTypeButton[contains(@name,\"Compare\")])[" + compareNumber + "]";
        }
        return driver.findElement(By.xpath(xpath));
    }
    private WebElement productLineNumberANDROID(String lineNumber) {
        String xpath = "(//android.widget.ImageView[@content-desc='mainImage'])['" + lineNumber + "']//following::android.widget.TextView[1]";
        return driver.findElement(By.xpath(xpath));
    }

    private WebElement productLineNumberIOS(String lineNumber) {
        String xpath = "(//*[@name='importantForAccessibility'])[" + lineNumber + "]";
        return driver.findElement(By.xpath(xpath));
    }

    private WebElement filterByAndroid(String filterName) {
        String xpath = "(//*[@text='" + filterName + "'])[last()]";
        return driver.findElement(By.xpath(xpath));
    }

    private WebElement filterByIOS(String filterName) {
        String xpath = "(//XCUIElementTypeOther[contains(@name,'" + filterName + "')])[last()]";
        return driver.findElement(By.xpath(xpath));
    }

    private WebElement filterCheckbox(String filterCheckboxName) {
        String xpath="";
        if (deviceType.equals("ANDROID")) {
            xpath = "//*[contains(@text,'" + filterCheckboxName + "')]//preceding-sibling::*[1]";
        } else if (deviceType.equals("IOS")) {
            xpath = "(//XCUIElementTypeStaticText[@name=\"How fast do you want your items?\"])[1]//following-sibling::*[1]";
        }
        return driver.findElement(By.xpath(xpath));
    }
    private WebElement sortByIos(String sortName) {
        String xpath = "(//XCUIElementTypeOther[@name='" + sortName + "'])[1]";
        return driver.findElement(By.xpath(xpath));
    }
    private WebElement sortByAndroid(String sortName) {
        String xpath = "//android.view.ViewGroup[@content-desc='" + sortName + "']//android.widget.ImageView";
        return driver.findElement(By.xpath(xpath));
    }
    private WebElement newCategoryBubbleAndroid(String bubble1) {
        String xpath = "//android.widget.TextView[contains(@text, '" + bubble1 + "')]";
        return driver.findElement(By.xpath(xpath));
    }
    private WebElement newCategoryBubbleIOS(String bubble1) {
        String xpath = "//XCUIElementTypeButton[contains(@name,'" + bubble1 + "')]";
        return driver.findElement(By.xpath(xpath));
    }
    private WebElement sortSelectedAndroid(String sortNameSelected) {
        String xpath = "//android.view.ViewGroup[@content-desc='" + sortNameSelected + "' and @enabled='true']";
        return driver.findElement(By.xpath(xpath));
    }
    private WebElement sortSelectedIos(String sortNameSelected) {
        String xpath = "(//XCUIElementTypeOther[@name='" + sortNameSelected + "' and @enabled='true'])[1]";
        return driver.findElement(By.xpath(xpath));
    }
    private WebElement fulfillmentOptionFilter(String fulfillmentName) {
        String xpath = "//android.widget.TextView[@text='" + fulfillmentName + "']";
        return driver.findElement(By.xpath(xpath));
    }

    private WebElement chooseOptionPLPAndroid(String lineno) {
        String xpath ="//*[@text=\"CHOOSE OPTIONS\"]["+lineno+"]";
        return driver.findElement(By.xpath(xpath));
    }
    private WebElement chooseOptionPLPiOS(String lineno) {
        String xpath ="//*[@name=\"CHOOSE OPTIONS\"]["+lineno+"]";
        return driver.findElement(By.xpath(xpath));
    }

    public SearchPage(WebDriver driver, String deviceType) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        SearchPage.deviceType = deviceType;
    }
    public void clickonSortFilterButton() {
        switch (deviceType) {
            case "ANDROID":
                scrolldownByPixcel();
                webdriverWaitElementToBeVisible(SORT_AND_FILTER_ANDROID);
                clickByWebelement(SORT_AND_FILTER_ANDROID, "Click on Filter Button");
                waitForElement(SORT_BY_ANDROID,10);
                break;
            case "IOS":
                webdriverWaitElementToBeVisible(SORT_AND_FILTER_IOS);
                clickByWebelement(SORT_AND_FILTER_IOS, "Click on Filter Button");
                waitForElement(SORT_BY_IOS,10);
                break;
        }
    }
    public void clickFilterByName(String filterName) {
        switch (deviceType) {
            case "ANDROID":
                clickByWebelement(SORT_BY_ANDROID, "Click on Sort By");
                timeOut(1000);
                scrollToElement(filterByAndroid(filterName));
                filterByAndroid(filterName).click();
                timeOut(8000);
                break;
            case "IOS":
                clickByWebelement(SORT_BY_IOS, "Click on Sort By");
                scrollToElementUsingScrollIOS(filterByIOS(filterName));
                filterByIOS(filterName).click();
                break;
        }
    }
    public void clickFilterCheckboxByName(String checkboxName) {
        timeOut(5000);
        filterCheckbox(checkboxName).click();
        timeOut(5000);
    }

    public void clickApplyFilter() {
        switch (deviceType) {
            case "ANDROID":
                webdriverWaitElementToBeVisible(APPLY_FILTER_ANDROID);
                clickByWebelement(APPLY_FILTER_ANDROID, "Click on Apply Filter Button");
                timeOut(10000);
                break;
            case "IOS":
                webdriverWaitElementToBeVisible(APPLY_FILTER_IOS);
                clickByWebelement(APPLY_FILTER_IOS, "Click on Apply Filter Button");
                timeOut(2000);
                break;
        }
    }

    public void clickSortByName(String sortName) {
        switch(deviceType) {
            case "ANDROID":
                timeOut(5000);
                sortByAndroid(sortName).click();
                timeOut(5000);
                break;
            case "IOS":
                timeOut(5000);
                sortByIos(sortName).click();
                timeOut(5000);
                break;
        }
    }

    public void categoryBubble(String bubble1) {
        switch(deviceType) {
            case "ANDROID":
                timeOut(5000);
                newCategoryBubbleAndroid(bubble1).click();
                timeOut(5000);
                break;
            case "IOS":
                timeOut(5000);
                newCategoryBubbleIOS(bubble1).click();
                timeOut(5000);
                break;
        }
    }

    public void sortOptionIsEnabled(String sortNameSelected) {
        switch(deviceType) {
            case "ANDROID":
                timeOut(5000);
                sortSelectedAndroid(sortNameSelected).isDisplayed();
                break;
            case "IOS":
                timeOut(5000);
                sortSelectedIos(sortNameSelected).isDisplayed();
                break;
        }
    }

    public void clickProductNameWithLineNumber(String productLine) {
        switch (deviceType) {
            case "ANDROID":
                timeOut(2000);
                webdriverWaitElementToBeVisible(productLineNumberANDROID(productLine));
                timeOut(2000);
                productLineNumberANDROID(productLine).click();
                timeOut(2000);
                break;
            case "IOS":
                timeOut(2000);
                waitForElement(productLineNumberIOS(productLine),15);
                timeOut(2000);
                productLineNumberIOS(productLine).click();
                timeOut(2000);
                break;
        }

    }
    public void swipeLeftPDP(String x_axis,String y_axis)
    {
        switch(deviceType) {
            case "ANDROID":
                scrollToElementAndPerformAction(LocatorType.TEXT, "How Do You Want Your Items", ActionType.SCROLL);
                timeOut(2000);
                CMAswipeHorizontal(x_axis, y_axis);
                break;
            case "IOS":
                scrollToElementAndPerformAction(LocatorType.TEXT, "How Do You Want Your Items", ActionType.SCROLL);
                timeOut(2000);
                CMAswipeHorizontal("20", "750");
                break;
        }
    }
    public void verifySearchPage()
    {
        switch(deviceType) {
            case "ANDROID":
                timeOut(1000);
                isElementDisplayed(SORT_AND_FILTER_ANDROID, "Sort And Filter Button");
                break;
            case "IOS":
                timeOut(1000);
                isElementDisplayed(SORT_AND_FILTER_IOS, "Sort And Filter Button");
                break;
        }
    }
    public void verifyHowDoYouWantYourItemDropbox(){
        Assert.assertTrue(isElementDisplayed(SHOP_HOW_DO_YOU_WANT_ITEMS, "how do you want your items"));
    }

    public void clickOnHowDoYouWantYourItemDropbox(){
        webdriverWaitElementToBeVisible(SHOP_HOW_DO_YOU_WANT_ITEMS_DROPBOX);
        clickByWebelement(SHOP_HOW_DO_YOU_WANT_ITEMS_DROPBOX, "Click on How do you want your items dropbox");
    }

    public void clickOnFreePickupOption(){
        webdriverWaitElementToBeVisible(HOW_DO_YOU_WANT_FREE_PICK_UP_OPTION);
        clickByWebelement(HOW_DO_YOU_WANT_FREE_PICK_UP_OPTION, "Click on free pickup option button");
    }

    public void clickOnHowDoYouWantCloseButton(){
        webdriverWaitElementToBeVisible(HOW_DO_YOU_WANT_CLOSE_BUTTON);
        clickByWebelement(HOW_DO_YOU_WANT_CLOSE_BUTTON, "Click on close button");
    }

    public void clickOnHomeDeliveryOption(){
        webdriverWaitElementToBeVisible(HOW_DO_YOU_WANT_HOME_DELIVERY_OPTION);
        clickByWebelement(HOW_DO_YOU_WANT_HOME_DELIVERY_OPTION, "Click on home delivery option");
    }

    public void captureTheStoreNumberBeforeChangingStore(){
        storeNumberBefore= getText(HOW_DO_YOU_WANT_STORE_NAME);
    }

    public void clickOnGotoShopPageButton(){
        webdriverWaitElementToBeVisible(HOW_DO_YOU_WANT_GOTO_STORE_BUTTON);
        clickByWebelement(HOW_DO_YOU_WANT_GOTO_STORE_BUTTON, "Click on home delivery option");
    }

    public void changeTheStoreTo(String store){
        enterByWebelement(ENTER_STORE_TEXT_BOX, store, "Entering store number");
        webdriverWaitElementToBeVisible(SUGGESTED_STORE_NAME);
        clickByWebelement(SUGGESTED_STORE_NAME, "Click on suggested store name");
        webdriverWaitElementToBeVisible(SET_AS_MY_STORE_BUTTON);
        clickByWebelement(SET_AS_MY_STORE_BUTTON, "Click on set as my store");
        webdriverWaitElementToBeVisible(STORE_SET_CONFIRMATION);
        clickByWebelement(STORE_SET_CONFIRMATION, "Click on set as my store confirmation popup");
        webdriverWaitElementToBeVisible(STORE_TAB_CLOSE_BUTTON);
        clickByWebelement(STORE_TAB_CLOSE_BUTTON, "Click on close tab button for store");
        webdriverWaitElementToBeVisible( STORE_TEXT_LAYOVER_CLOSE_BUTTON);
        clickByWebelement( STORE_TEXT_LAYOVER_CLOSE_BUTTON, "Click on close for store enter text overlay");
    }

    public void verifyStoreNumberNotEqualsToPreviousStoreNumber(){
        Assert.assertFalse(storeNumberBefore.contains(getText(HOW_DO_YOU_WANT_STORE_NAME)));
    }
    public void verifyFilterSectionOptionIsNotAvailable(String opt)
    {
        try {
            webdriverWaitElementNotToBeVisible(fulfillmentOptionFilter(opt));
            Allure.addAttachment("verifyFilterSectionOptionIsNotAvailable", "Filter with given option is not displayed");
        } catch (Exception e) {
            Allure.addAttachment("verifyFilterSectionOptionIsNotAvailable- Failure",
                    "Filter with given option is displayed");
        }

    }

    public void clickOnFilterCloseButton() {
        webdriverWaitElementToBeVisible(FILTER_CLOSE_BUTTON);
        clickByWebelement(FILTER_CLOSE_BUTTON, "Click on Filter Button");
    }

    public void clickCompareWithLineNumber(String compareLine) {
        timeOut(3000);
        if(compareLine.equals("2"))
        {CMAswipeDown(1);}
        scrollToElementUntilVisible(compareLineNumber(compareLine));
        webdriverWaitElementToBeVisible(compareLineNumber(compareLine));
        compareLineNumber(compareLine).click();
    }
    public void clickCompareButton() {
        clickByWebelement(COMPARE_BUTTON, "Click on Compare Button");
    }
    public void verifyCompareButtonIsNotAvailable()
    {
        try {
        webdriverWaitElementNotToBeVisible(COMPARE_BUTTON);
        Allure.addAttachment("verifyCompareButtonIsNotAvailable", "Compare Button is not displayed");
    } catch (Exception e) {
        Allure.addAttachment("verifyCompareButtonIsNotAvailable- Failure",
                "Compare Button is displayed");
    }

    }
    public void verifyFirstpProductNameContains(String keyword){
        waitForElement(FIRST_PRODUCT,5);
        String FirstProductName= getText(FIRST_PRODUCT);
        verifyTextByXpath(FirstProductName,keyword);
    }

    public void captureTotalItemCount(){
        waitForElement(ITEMCOUNT,5);
        ItemCount=getText(ITEMCOUNT);
    }

    public void captureItemCountWithFilter(){
        waitForElement(ITEMCOUNT,5);
        ItemCountwithFilter=getText(ITEMCOUNT);
    }

    public void captureSpecificBrandCount(){
        waitForElement(BRANDCOUNT,5);
        BrandCount=getText(BRANDCOUNT);
    }

    public void verifyBrandCounts(){
        Assert.assertTrue(ItemCountwithFilter.contains(BrandCount));
        Allure.step("Brand Counts Are Matched", Status.PASSED );
    }

    public boolean verifyFirstTwoItemsSorted(){
        double FirstPrice = Double.parseDouble(FIRST_PRODUCT_PRICE.getText().trim());
        double SecondPrice = Double.parseDouble(SECOND_PRODUCT_PRICE.getText().trim());
        if(SecondPrice>FirstPrice){
            Allure.addAttachment("Prices are Not Sorted","By filter");
            return false;
        } else {
            Allure.addAttachment(FirstPrice+"First price Is Greater Than","Second price"+SecondPrice);
            return true;
        }
    }

    public void clickOnClearAllFilter(){
        waitForElement(CLEAR_ALL,6);
        clickByWebelement(CLEAR_ALL,"Need to clear all filters");
        waitForElement(CONFIRM,5);
        clickByWebelement(CONFIRM,"Click on confirm");
    }

    public void captureTheItemCountAfterfilter(){
        timeOut(3000);
        String ItemCountAfter=getText(ITEMCOUNT);
        Assert.assertTrue(ItemCount.contains(ItemCountAfter));
        Allure.step("Counts Matched Succesfully", Status.PASSED);
    }

    public void enterKeywordsSearchBar(String searchData) {
        CMAswipeUp(2);
        webdriverWaitElementToBeVisible(SEARCH_TEXTBOX);
        if (checkIsDisplayed(SEARCH_TEXTBOX)) {
            clickByWebelement(SEARCH_TEXTBOX, "Click On Home Page Search Bar");
            webdriverWaitElementToBeVisible(TEXTINPUT_TEXTBOX);
            enterByWebelement(TEXTINPUT_TEXTBOX, searchData, "Entering the Data in Search Bar");
        } else {
            Allure.addAttachment("Search Bar Is Not Present "," ");
        }
    }


    public void captureValueFromLabelFirstSuggestionSearchBar() {
        webdriverWaitElementToBeVisible(FIRST_SEARCH_SUGGESTION_NAME);
        firstSuggestionName = getText(FIRST_SEARCH_SUGGESTION_NAME);
    }

    public void clickOnTheFirstSuggestionSearchBarButton() {
        clickByWebelement(FIRST_SEARCH_SUGGESTION_SEARCH_BAR, "Clicked on first suggestion search bar");
    }

    public void verifyValueFromLabelFirstNameContainsFirstSuggestionName() {
        waitUntil(() -> FIRST_ITEM_NAME.isDisplayed(), 12);
        firstitemname = getText(FIRST_ITEM_NAME).trim();
        Assert.assertTrue(firstitemname.trim().toLowerCase().contains(firstSuggestionName.toLowerCase()),
                "Expected '" + firstitemname + "' to contain '" + firstSuggestionName + "'");
    }

    public void captureValueFromLabelSecondItemName() {
        webdriverWaitElementToBeVisible(SECOND_ITEM_NAME);
        seconditemname = getText(SECOND_ITEM_NAME);
    }

    public void captureValueFromSearchTerm() {
        webdriverWaitElementToBeVisible(SEARCH_TERM);
        searchterm = getText(SEARCH_TERM);
    }

    public void verifyValueFromLabelFirstNameContainsSearchTerm() {
        waitUntil(() -> FIRST_ITEM_NAME.isDisplayed(), 12);
        searchterm = getText(SEARCH_TERM);
        firstitemname = getText(FIRST_ITEM_NAME).trim();
        Assert.assertTrue(firstitemname.trim().toLowerCase().contains(searchterm.toLowerCase()),
                "Expected '" + firstitemname + "' to contain '" + searchterm + "'");
    }

    public void verifyValueFromLabelSecondNameContainsSearchTerm() {
        waitUntil(() -> SECOND_ITEM_NAME.isDisplayed(), 12);
        searchterm = getText(SEARCH_TERM);
        seconditemname = getText(SECOND_ITEM_NAME).trim();
        Assert.assertTrue(seconditemname.trim().toLowerCase().contains(searchterm.toLowerCase()),
                "Expected '" + seconditemname + "' to contain '" + searchterm + "'");
    }
    public void chooseOptionFromPLP(String lineno){
        switch (deviceType) {
            case "ANDROID":
                timeOut(3000);
                webdriverWaitElementToBeVisible(SORT_AND_FILTER_ANDROID);
                clickByWebelement(chooseOptionPLPAndroid(lineno), "Click on buy now");
                break;
            case "IOS":
                timeOut(3000);
                webdriverWaitElementToBeVisible(SORT_AND_FILTER_IOS);
                clickByWebelement(chooseOptionPLPiOS(lineno), "Click on buy now");
                break;
        }
    }

    public void clickOnTheFirstLiveBirdSearch() {
        waitForElement(FIRST_LIVEBIRD_SUGGESTION,5);
        if(deviceType.equalsIgnoreCase("android")){
            scrollToElementUntilVisible(FIRST_LIVEBIRD_SUGGESTION);
        } else if (deviceType.equalsIgnoreCase("ios")){
            scrollToElementUsingScrollIOS(FIRST_LIVEBIRD_SUGGESTION);
        }
        clickByWebelement(FIRST_LIVEBIRD_SUGGESTION, "Clicked on first LiveBird");
    }

    public void verifyChooseOptionExist() {
        waitForElement(FIRST_CHOOSE_OPTION,20);
        isElementDisplayed(FIRST_CHOOSE_OPTION, "Choose Option Button");
    }

    public void clickOnTheFirstChooseOption() {
        webdriverWaitElementToBeVisible(FIRST_CHOOSE_OPTION);
        clickByWebelement(FIRST_CHOOSE_OPTION, "Clicked on first Choose Option");
    }

    public void verifyAddToCartInPLP() {
        waitForElement(FIRST_ADD_TO_CART_PLP,20);
        isElementDisplayed(FIRST_ADD_TO_CART_PLP, "Add To Cart Button");
    }

    public void clickOnTheFirstAddToCartInPLP() {
        webdriverWaitElementToBeVisible(FIRST_ADD_TO_CART_PLP);
        clickByWebelement(FIRST_ADD_TO_CART_PLP, "Clicked on first Choose Option");
        timeOut(30000);
    }
    public void selecteGiftCardInPLP() {
        webdriverWaitElementToBeVisible(EGIFT_CARD);
        clickByWebelement(EGIFT_CARD, "Clicked on eGift Card");
        timeOut(2000);
    }
}