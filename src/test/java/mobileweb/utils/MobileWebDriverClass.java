package mobileweb.utils;
import mobileweb.wrappers.MobileWebDriverInvoke;
import webutils.LoadProperties;

import java.util.List;
import java.util.Optional;

import static azurevault.VaultData.vaultConnect;

public class MobileWebDriverClass {

    private final MobileWebConfig config;
    private final MobileWebDriverInvoke mobileWebDriverInvoke;

    public MobileWebDriverClass() {
        this.config = new MobileWebConfig();
        this.mobileWebDriverInvoke =new MobileWebDriverInvoke();
    }

    /**
     * Initializes and sets up the mobile browser and driver.
     */
    public void setupBrowser() {
        vaultConnect();
        // Retrieve configuration values using MobileWebConfig
        String browser = config.getProperty("BrowserName");
        String mobileDevice = config.getProperty("MobileDeviceName");
        String platform = config.getProperty("PlatformName");
        String platformVersion = config.getProperty("PlatformVersion");
        String phoneUdid = config.getProperty("PhoneUDID");
        String automationName = config.getProperty("AutomationName");
        String url = LoadProperties.prop.getProperty(System.getProperty("mWebUrl"),config.getProperty("Mobile_URL"));
        String chromeDriverPath = config.getProperty("ChromeDriverPath");
        String ipAddress = config.getProperty("IpAddress");
        String port = config.getProperty("Port");
        String appiumJsPath = config.getProperty("AppiumJsPath");
        String appPath = config.getProperty("AppPath");
        String appPackage = config.getProperty("AppPackage");
        String appActivity = config.getProperty("AppActivity");
        String useDevTools = config.getProperty("UseDevTools");
        String deviceMetrics = config.getProperty("DeviceMetrics");

        if (isDevToolsEnabled(useDevTools)) {
            mobileWebDriverInvoke.invokeMobile(browser, url, chromeDriverPath, List.of(deviceMetrics.split(",")));
        } else {
            mobileWebDriverInvoke.invokeMobile(
                    mobileDevice, browser, platform, platformVersion, phoneUdid, automationName,
                    url, chromeDriverPath, ipAddress, port, appiumJsPath, appPath, appPackage, appActivity
            );
        }
    }

    /**
     * Check if DevTools is enabled
     */
    private boolean isDevToolsEnabled(String useDevTools) {
        return Optional.ofNullable(useDevTools)
                .map(val -> val.equalsIgnoreCase("true"))
                .orElse(false);
    }
}