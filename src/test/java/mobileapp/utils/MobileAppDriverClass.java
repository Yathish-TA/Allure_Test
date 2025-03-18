package mobileapp.utils;

import mobileapp.wrappers.MobileAppDriverInvoke;

public class MobileAppDriverClass {

    private MobileAppConfig config;

    public MobileAppDriverClass() {
        config = new MobileAppConfig();
    }

    /**
     * Initializes and sets up the mobile browser and driver.
     */
    public void setupBrowser() {
        // Retrieve configuration values using MobileAppConfig
        String browser = config.getProperty("BrowserName");
        String mobileDevice = config.getProperty("MobileDeviceName");
        String platform = config.getProperty("PlatformName");
        String platformVersion = config.getProperty("PlatformVersion");
        String phoneUdid = config.getProperty("PhoneUDID");
        String automationName = config.getProperty("AutomationName");
        String chromeDriverPath = config.getProperty("ChromeDriverPath");
        String ipAddress = config.getProperty("IpAddress");
        String port = config.getProperty("Port");
        String appiumJsPath = System.getProperty("user.home")+config.getProperty("AppiumJsPath");
        String appPath = config.getProperty("AppPath");
        String appPackage = config.getProperty("AppPackage");
        String appActivity = config.getProperty("AppActivity");

        // Initialize and invoke mobile driver
        MobileAppDriverInvoke mobileAppDriverInvoke = new MobileAppDriverInvoke();
        mobileAppDriverInvoke.invokeMobileApp(
                mobileDevice, browser, platform, platformVersion, phoneUdid, automationName,
                chromeDriverPath, ipAddress, port, appiumJsPath, appPath, appPackage, appActivity
        );
    }
}
