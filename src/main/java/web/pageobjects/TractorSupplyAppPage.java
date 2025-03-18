package web.pageobjects;

import genericwrappers.GenericWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TractorSupplyAppPage extends GenericWrapper {


    public TractorSupplyAppPage(WebDriver driver){
        super(driver); // Pass the WebDriver instance to the GenericWrapper constructor
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    @FindBy(xpath = "//*[contains(text(),'Tractor Supply App')]")
    WebElement TRACTOR_SUPPLY_APP;

    @FindBy(xpath = "//*[@src='/tsc/cms/app/_jcr_content/root/container/container_copy_73337684/image.coreimg.90.480.jpeg/1628618056044/20200713-tsc-apppage-lp-apple-app-store-tile.jpeg']")
    WebElement VERIFY_IMAGE ;

    public void clickTractorSupplyApp(){
        clickByWebelement(TRACTOR_SUPPLY_APP,"Click tractorsupplyapp icon");
    }

    public void verifyApp() {
        VERIFY_IMAGE.isDisplayed();
    }

}
