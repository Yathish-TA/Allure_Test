package mobileapp.wrappers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import reports.ReportManager;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class MobileAppDriverInvoke extends ReportManager {

    public static AppiumDriverLocalService service;
    private static AppiumDriver driver;
    private static String deviceType;

    public void invokeMobileApp(
            String mobile, String browser, String platform, String platformVersion, String udid,
            String automatorname, String chromeDriverPath, String ipAddress, String port,
            String appiumJsPath, String appPath, String appPackage, String appActivity) {

        try {

            String userName = System.getProperty("browserstack.username");
            String password = System.getProperty("browserstack.accesskey");

            String cloudData = System.getProperty("browserstack.config");
            if (cloudData != null && cloudData.contains("android")) {
                UiAutomator2Options options = new UiAutomator2Options();
                String browserStackURL = "https://" + userName + ":" + password + "@" + "hub.browserstack.com/wd/hub";
                String option = String.valueOf(options);
                driver = new AndroidDriver(new URL(browserStackURL), options);
                this.logReport("pass", "The android native app with package " + appPath + " and activity " + appActivity
                        + " is launched successfully");
            }

            if (cloudData != null && cloudData.contains("ios")) {
                XCUITestOptions options = new XCUITestOptions();
                options.setCapability("autoAcceptAlerts", true);
                String browserStackURL = "https://" + userName + ":" + password + "@" + "hub.browserstack.com/wd/hub";
                driver = new IOSDriver(new URL(browserStackURL), options);
                this.logReport("pass", "The ios native app with package " + appPath + " and activity " + appActivity
                        + " is launched successfully");
            }

            //startAppiumServer(appiumJsPath, ipAddress, Integer.parseInt(port)); // This needs be on when the when testing locally
            if (platform.equalsIgnoreCase("android") && cloudData == null) {
                UiAutomator2Options options = new UiAutomator2Options();
                options.setDeviceName(mobile);
                options.setCapability(MobileCapabilityType.PLATFORM_NAME, platform);
                options.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
                options.setCapability(MobileCapabilityType.UDID, udid);
                options.setCapability(MobileCapabilityType.AUTOMATION_NAME, automatorname);

                if (appPackage != null && appActivity != null && cloudData == null) {
                    options.setCapability("appPackage", appPackage);
                    options.setCapability("appActivity", appActivity);
                    options.setCapability("app", appPath);
                    options.setCapability("autoGrantPermissions", true);
                    driver = new AndroidDriver(new URL("http://" + ipAddress + ":" + port), options);
                    this.logReport("pass", "The native app with package " + appPackage + " and activity " + appActivity
                            + " is launched successfully");
                }
            }
            this.logReport("FAIL", "Platform " + platform + " is not supported.");
            deviceType = driver.getCapabilities().getCapability(MobileCapabilityType.PLATFORM_NAME).toString();
        }
        catch (SessionNotCreatedException e) {
            logReport("FAIL", "The session could not be created: " + e.getMessage());

        } catch (
                WebDriverException e) {
            logReport("FAIL", "The driver encountered an error: " + e.getMessage());

        } catch (
                MalformedURLException e) {
            throw new RuntimeException("Malformed URL: " + e.getMessage(), e);
        }
    }

    public static WebDriver callDriver() {
        return driver;
    }

    public static String getDeviceType() {return  deviceType;}

    public void startAppiumServer(String appiumJsPath, String ipAddress, int port) {
        service = new AppiumServiceBuilder()
                .withAppiumJS(new File(appiumJsPath))
                .withIPAddress(ipAddress)
                .usingPort(port)
                .build();
        service.start();
    }

    public void stopService() {
        try {
            service.stop();
            logReport("PASS", "Appium Service closed successfully");

        } catch (WebDriverException e) {
            logReport("FAIL", "The Appium service got closed due to unknown error");
        }
    }
}
