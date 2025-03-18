package web.utils;

import helperfunctions.ConfigLoader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;
import webutils.LoadProperties;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class DriverCall {

    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public void setupBrowser(String browser, String runMode, String os, String osVersion, String browserVersion) {
        try {
            if ("local".equalsIgnoreCase(runMode)) {
                System.out.println("Browser Initialized");
                setupLocalBrowser(browser);
            } else if ("remote".equalsIgnoreCase(runMode)) {
                System.out.println("Running in Remote browser");
                setupRemoteBrowser(browser, os, osVersion, browserVersion);
            } else {
                throw new IllegalArgumentException("Unsupported run mode: " + runMode);
            }
        } catch (Exception e) {
            System.out.println("Other Exception: " + e.getMessage());
        }
    }

    private void setupLocalBrowser(String browser) {
        try {
            WebDriver webDriver = null;
            if ("chrome".equalsIgnoreCase(browser)) {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized", "ignore-certificate-errors", "--disable-infobars");
                WebDriverManager.chromedriver().setup();
                webDriver = new ChromeDriver(chromeOptions);
            } else if ("edge".equalsIgnoreCase(browser)) {
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--start-maximized", "ignore-certificate-errors", "--disable-infobars");
                WebDriverManager.edgedriver().setup();
                webDriver = new EdgeDriver(edgeOptions);
            } else if ("firefox".equalsIgnoreCase(browser)) {
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                FirefoxProfile profile = new FirefoxProfile();
                profile.setPreference("browser.cache.disk.enable", false);
                profile.setPreference("browser.cache.memory.enable", false);
                profile.setPreference("browser.cache.offline.enable", false);
                profile.setPreference("network.cookie.cookieBehavior", 2);
                firefoxOptions.setProfile(profile);
                WebDriverManager.firefoxdriver().setup();
                webDriver = new FirefoxDriver(firefoxOptions);
            } else {
                throw new IllegalArgumentException("Unsupported browser: " + browser);
            }
            // Set the WebDriver instance to the ThreadLocal
            driver.set(webDriver);
            driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        } catch (Exception e) {
            System.out.println("Other Exception: " + e.getMessage());
        }
    }


    @SuppressWarnings("unchecked")
    private synchronized void setupRemoteBrowser(String browser, String os, String osVersion, String browserVersion) {
        try {
            WebDriver webDriver = null;

            MutableCapabilities capabilities = new MutableCapabilities();
            Map<String, Object> bstackOptions = new HashMap<>();

            System.out.println("Loading configuration from YAML file");
            Map<String, Object> ymlProperties = ConfigLoader.loadYamlFile("browserstack.yml");

            String browserstackUsername = (String) ymlProperties.get("username");
            String browserstackAccessKey = (String) ymlProperties.get("accessKey");

            // Append the time(HH:mm) as a unique identifier to the build name
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String currentTime = LocalDateTime.now().format(formatter);
            String baseBuildName = getString(ymlProperties.get("buildName"));
            String uniqueBuildName = baseBuildName + "-" + currentTime;

            bstackOptions.put("userName", browserstackUsername);
            bstackOptions.put("accessKey", browserstackAccessKey);
            bstackOptions.put("buildName", uniqueBuildName);
            bstackOptions.put("projectName", getString(ymlProperties.get("projectName")));
            bstackOptions.put("networkLogs", "true");
            // Load Different Browser, BrowserVersion, Os, and OsVersion
            bstackOptions.put("os", os);
            bstackOptions.put("osVersion", osVersion);
            bstackOptions.put("browserName", browser);
            bstackOptions.put("browserVersion", browserVersion);
            capabilities.setCapability("bstack:options", bstackOptions);

            URL browserStackUrl = new URL(LoadProperties.prop.getProperty("bstackUrl"));
            webDriver = new RemoteWebDriver(browserStackUrl, capabilities);
            // Set the WebDriver instance to the ThreadLocal
            driver.set(webDriver);
            webDriver.manage().window().maximize();
            driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

        } catch (MalformedURLException e) {
            System.out.println("Invalid Selenium Grid URL");
        } catch (Exception e) {
            System.out.println("Other Exception: " + e.getMessage());
        }
    }

    private String getString(Object value) {
        return value != null ? value.toString() : "";
    }

    public WebDriver callDriver() {
        return driver.get();
    }

}