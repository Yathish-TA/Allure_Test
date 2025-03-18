package mobileweb.wrappers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import reports.ReportManager;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;

public class MobileWebDriverInvoke extends ReportManager {

    public static AppiumDriverLocalService service;
    private static AppiumDriver driver1;
    private static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
    private static String deviceType;

    public void invokeMobile(
            String mobile, String browser, String platform, String platformVersion, String udid,
            String automatorName, String url, String chromeDriverPath, String ipAddress, String port,
            String appiumJsPath, String appPath, String appPackage, String appActivity) {
        try {

//            String userName = getVaultData("BrowserStackUserName");
//            String password = getVaultData("BrowserStackAccessKey");
            String userName = System.getProperty("browserstack.username");
            String password = System.getProperty("browserstack.accesskey");
            String browserStackData = System.getProperty("browserstack.config");

            if (!StringUtils.isEmpty(browserStackData)) {
                if (browserStackData.contains("android")) {
                    setupAndroidDriverForBrowserStack(userName, password);
                } else if (browserStackData.contains("ios")) {
                    setupIOSDriverForBrowserStack(userName, password);
                } else {
                    logReport("FAIL", "Unsupported browser stack configuration!!!");
                    Assert.fail("Unsupported browser stack configuration!!!");
                }
            } else {
                // Local Appium Server setup - Android Studio is applicable, not ios
                if (!StringUtils.isEmpty(platform) && "android".equalsIgnoreCase(platform)) {
                    setupAndroidDriverLocally(mobile, platform, platformVersion, udid, automatorName, ipAddress, port, appPath, appPackage, appActivity);
                } else {
                    logReport("FAIL", "Unsupported platform : " + platform);
                    Assert.fail("Unsupported platform : " + platform);
                }
            }

            driver.get().manage().deleteAllCookies();
            driver.get().get(url);
            driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            driver.get().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

            // Set device type
            deviceType = driver.get().getCapabilities().getCapability(MobileCapabilityType.PLATFORM_NAME).toString();
        } catch (Exception e) {
            logReport("FAIL", "Session could not be created: " + e.getMessage());
            Assert.fail("Session could not be created:(Check Your .Yml file once) " + e.getMessage());
        }
    }

    /**
     * Set up Android Driver for Android Devices
     *
     * @param userName userName
     * @param password password
     * @throws MalformedURLException MalformedURLException
     */
    private void setupAndroidDriverForBrowserStack(String userName, String password) {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setCapability("autoAcceptAlerts", true);
        String browserStackURL = createBrowserStackURL(userName, password);
        try {
            driver.set(new AndroidDriver(new URL(browserStackURL), options));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        logReport("PASS", "Browser is launched in the browserstack android device ");
    }

    /**
     * Set up IOS Driver for IOS Devices
     *
     * @param userName userName
     * @param password password
     * @throws MalformedURLException MalformedURLException
     */
    private void setupIOSDriverForBrowserStack(String userName, String password)
            throws MalformedURLException {
        XCUITestOptions options = new XCUITestOptions();
        options.setCapability("autoAcceptAlerts", true);
        String browserStackURL = createBrowserStackURL(userName, password);
        driver.set(new IOSDriver(new URL(browserStackURL), options));
        logReport("PASS", "Browser is launched in the browserStack IOS device");
    }

    private void setupAndroidDriverLocally(String mobile, String platform, String platformVersion, String udid,
                                           String automatorName, String ipAddress, String port,
                                           String appPath, String appPackage, String appActivity)
            throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(mobile);
        options.setCapability(MobileCapabilityType.PLATFORM_NAME, platform);
        options.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
        options.setCapability(MobileCapabilityType.UDID, udid);
        options.setCapability(MobileCapabilityType.AUTOMATION_NAME, automatorName);

        if (appPackage != null && appActivity != null) {
            options.setCapability("appPackage", appPackage);
            options.setCapability("appActivity", appActivity);
            options.setCapability("app", appPath);
            options.setCapability("autoGrantPermissions", true);
        }


        driver.set(new AndroidDriver(new URL("http://" + ipAddress + ":" + port), options));
        logReport("PASS", "Native Android app launched locally with package " + appPackage + " and activity " + appActivity);
    }

    private String createBrowserStackURL(String userName, String password) {
        return "https://" + userName + ":" + password + "@" + "hub.browserstack.com/wd/hub";
    }

    /**
     * To initiate chrome browser based on the device metrics
     *
     * @param browser       chrome
     * @param url           app
     * @param deviceMetrics metrics
     */
    public void invokeMobile(String browser, String url, String chromeDriverPath, List<String> deviceMetrics) {
        WebDriver driver;
        try {
            if (browser.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--incognito", "ignore-certificate-errors", "--disable-infobars");
                options.setExperimentalOption("mobileEmulation", getDeviceMetrics(deviceMetrics));
                driver = new ChromeDriver(options);
                webDriver.set(driver);
            }
            webDriver.get().manage().deleteAllCookies();
            webDriver.get().get(url);
            webDriver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        } catch (Exception e) {
            handleExceptionAndFailureLogging(e, "The driver encountered an error");
        }
    }

    /**
     * To get the mobile device resolution - Width , height & pixel of any device
     *
     * @param widthHeightPixel list
     * @return map of metrics
     */
    public Map<String, Object> getDeviceMetrics(List<String> widthHeightPixel) {
        Map<String, Object> metric = new HashMap<>();
        metric.put("width", Integer.parseInt(widthHeightPixel.get(0)));
        metric.put("height", Integer.parseInt(widthHeightPixel.get(1)));
        metric.put("pixelRatio", Float.parseFloat(widthHeightPixel.get(2)));
        Map<String, Object> emulation = new HashMap<>();
        emulation.put("deviceMetrics", metric);
        emulation.put("userAgent", "Mozilla/5.0 (iPhone; CPU iPhone OS 16_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.0 Mobile/15E148 Safari/604.1");
        return emulation;
    }

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
            handleExceptionAndFailureLogging(null, "Appium Service closed successfully", Status.PASSED);
        } catch (Exception e) {
            handleExceptionAndFailureLogging(e, "The Appium service got closed due to unknown error");
        }
    }

    public WebDriver callDriver() {
        webDriver.set(driver.get());
        return webDriver.get();
    }

    public static String getDeviceType() {
        return deviceType;
    }

    // Handle exceptions and log error details
    private void handleExceptionAndFailureLogging(Exception e, String message) {
        handleExceptionAndFailureLogging(e, message, Status.FAILED);
    }

    private void handleExceptionAndFailureLogging(Exception e, String message, Status status) {
        String exceptionMessage;
        if (e == null)
            exceptionMessage = "No Exception!!!";
        else
            exceptionMessage = e.getMessage();
        Allure.step(message + exceptionMessage, status);

        if (status == Status.FAILED)
            Assert.fail(message + exceptionMessage);
    }
}
