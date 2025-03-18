# TSC One Platform Java Mobile Web Automation Slice
This slice contains test automation scripts for Mobile Web(Chrome, Edge etc.) with BrowserStack integration testing using TestNG with Cucumber BDD approach.

## Prerequisites

Before you begin, ensure you have the following installed on your machine:
- Java JDK 20 or higher (set env variable)
- Maven 3.6.0 or higher


## BrowserStack Test Execution


### Overview
This Slice/Directory contains configuration files and guidelines for executing tests on different devices and browsers using BrowserStack. The tests leverage the `bs_web_android.yml` and `bs_web_ios.yml` files, which define the configurations for Android and iOS devices, respectively. Test execution is controlled via the `browserstack.config` environment variable, which must hold the value `bs_web_android.yml` or `bs_web_ios.yml`.


### BrowserStack Prerequisites
Before executing tests, ensure the following:


1. **BrowserStack Credentials**:
    - You must supply the BrowserStack username and access key via environment variables.
    - If these credentials are not found, BrowserStack will throw an error during initialization.

2. **BrowserStack SDK**:
   - The SDK will load the `.yml` configuration files prior to test-specific initializations and class files.


### Configuration Files


#### For Mobile Web Testing: - `bs_web_android.yml` and `bs_web_ios.yml`
- These files can be used to control the devices and browsers under test for Android and iOS platforms.
- Set the `browserstack.config` environment variable to either `bs_web_android.yml` or `bs_web_ios.yml` to specify the platform.


#### For Mobile Web Parallel Testing: - `bs_web_android_parallel.yml` and `bs_web_ios_parallel.yml`
- Use these files to execute scripts in parallel.
- They support concurrent execution across multiple devices and browsers.

**NOTE:** Please ensure that the `app` and `autoGrantPermissions` capabilities are NOT included in the `.yml` file for mobile web testing.
```yaml
# For Mobile Web Testing, Remove the following two capabilities from .yml file:
 app: "your_app_path_here"
 autoGrantPermissions: true
```

## Execution Steps

1. **Set Environment Variables**:
    - **For Linux/macOS**:
        - Export your BrowserStack username and access key as environment variables:
          ```bash
          export BROWSERSTACK_USERNAME="<your_username>"
          export BROWSERSTACK_ACCESS_KEY="<your_access_key>"
          ```
        - Set the `browserstack.config` environment variable:
          ```bash
          export BROWSERSTACK_CONFIG="bs_web_android.yml" # or "bs_web_ios.yml"
          ```

    - **For IntelliJ IDEA**:
        1. Go to `Run` > `Edit Configurations`.
        2. Select your test configuration or create a new one.
        3. In the `VM Options` section, click the `...` button.
        4. Add the following variables:
            - `-Dbrowserstack.username=<USER>`.
            - `-Dbrowserstack.accesskey=<ACCESS_KEY>`.
            - `-Dbrowserstack.config="bs_web_<DEVICE>.yml"`.
        5. Click `OK` to save and apply the changes.
        6. Run the Configured TestNG file


2. **Run Tests from CLI**:
    - Use the `MobileWeb_TestNG.xml` file to initiate test execution:
      ```bash
      mvn test -DsuiteXmlFile=MobileWeb_TestNG.xml -Dbrowserstack.username=<USER> -Dbrowserstack.accesskey=<ACCESS_KEY> -Dbrowserstack.config="bs_web_<DEVICE>.yml"
      ```


3. **Parallel Execution**:
    - For Android parallel execution, use the `bs_web_android_parallel.yml` file:
      ```bash
      mvn test -DsuiteXmlFile=MobileWeb_TestNG.xml -Dbrowserstack.username=<USER> -Dbrowserstack.accesskey=<ACCESS_KEY> -Dbrowserstack.config="bs_web_android_parallel.yml"
      ```
    - For IOS parallel execution, use the `bs_web_ios_parallel.yml` file:

      ```bash
      mvn test -DsuiteXmlFile=MobileWeb_TestNG.xml -Dbrowserstack.username=<USER> -Dbrowserstack.accesskey=<ACCESS_KEY> -Dbrowserstack.config="bs_web_ios_parallel.yml"
      ```


4. **Local Execution with Android Studio**:
    - To execute tests locally using Android Studio, you need the following pieces of software:
      - Node.js and npm (for Appium server and set env variable)
      - Android SDK (set env variable for Mobile testing)
      - Appium server (can be installed via npm) 
    - Do not set the `browserstack.config` environment variable.
    - Set the Android studio Mobile device configuration like `Platform, PlatformVersion, MobileDeviceName etc.` in your MobileParams.properties file.


## Error Handling
- Ensure that the BrowserStack username and access key are set as environment variables before starting the tests.
- If these credentials are missing, the BrowserStack SDK will throw an error and terminate the test initialization process.


## Customization
- Modify the `.yml` files to specify the devices, browsers, and configurations as per your test requirements.
- Adjust the `MobileWeb_TestNG.xml` file to include or exclude specific test suites.


## Notes
- Ensure that the `.yml` files are properly formatted to avoid runtime errors.
- Below is an example of proper formatted `.yml` file for mobile web testing:
- ```yaml
    framework: testng
    platforms:
      - platformName: android
        browserName: Chrome
        deviceName: Samsung Galaxy S24 Ultra
        platformVersion: 14.0
    browserstackLocal: true
    browserStackLocalOptions:
      forceLocal: true
    buildName: TSC_OnePlatform_Android
    projectName: TSC_OnePlatform_Mobile
    testContextOptions:
      skipSessionName: true
    debug: false
    networkLogs: true
    interactiveDebugging: true
    testObservability: true
    source: cucumber-testng:appium-intellij:v1.1.6
    idleTimeout: 300
   ```