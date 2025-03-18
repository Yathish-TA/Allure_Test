# TSC One Platform Java Automation Framework
This repository contains test automation scripts for Web, Mobile (Web And App) and REST API testing using TestNG with Cucumber BDD approach.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Setup](#setup)
- [Run all tests](#run-all-tests)
- [Allure Manual Installation](#allure-manual-installation)
- [Reporting](#running-tests)
- [Azure Vault](#how-to-use-vault)
- [Browser Stack](#how-to-run-scripts-on-browser-stack)
- [Q-Test](#how-to-update-status-to-q-test)
- [Additional Notes](#additional-notes)



## Prerequisites

Before you begin, ensure you have the following installed on your machine:
- For Executing Web and API slice:
  - Java JDK 21 or higher (set env variable)
  - Maven 3.6.0 or higher
- For Executing Mobile (Web and App) Locally:
  - Node.js and npm (for Appium server and set env variable)
  - Android Studio / Android SDK (set env variable for Mobile testing)
  - Appium server (can be installed via npm)
- For Executing scripts on Browser Stack:
  - Active Browser Stack account must and should



## Project Structure

```plaintext
.github
├── workflows
│   └── gitHubAction.yml                                              # GitHub Action Config file
allure-report
├── index.html                                                        # Shareable Allure Report(Single file)
src
├── main
│   └── java
│       ├── allurereportgeneration                                    # Report utilities
│       ├── apicoreutils                                              # Api utilities
│       ├── azurevault                                                # Vault Secrets utilities
│       ├── browserstack                                              # Browser Stack utilities
│       ├── database                                                  # DataBase utilities
│       ├── genericwrappers                                           # Common Methods & Enum Class for Mobile
│       ├── helperfunctions                                           # Helper utilities
│       ├── listeners                                                 # Listeners classes
│       ├── mobileapp                                                 # Page Object Models for Mobile APP
│       ├── mobileutils                                               # Mobile utility classes
│       ├── mobileweb                                                 # Page Object Models for Mobile WEB
│       ├── qtestmanager                                              # Q-Test utilities
│       ├── web                                                       # Page Object Models for Web
│       ├── webutils                                                  # Web utility classes
│                        
├── test
│   ├── java
│   │   ├── api                                                       # API - specific tests
│   │   │   ├── resources\testrunner\Microservices_TestNG.xml         # Run this xml file for api suite
│   │   ├── mobileapp                                                 # Mobile App - specific tests
│   │   │   ├── resources\testrunner\MobileApp_TestNG.xml             # Run this xml file for mobileapp suite
│   │   ├── mobileweb                                                 # Mobile Web - specific tests
│   │   │   ├── resources\testrunner\MobileWeb_TestNG.xml             # Run this xml file for mobileweb suite
│   │   ├── web                                                       # Web - specific tests    
│   │   │   ├── resources\testrunner\Single_Browser_Sequential.xml    # Run this xml file for web suite
│   │               
│   └── resources                                                     # Configuration files
│       └── allure.properties                                         
│       └── cucumber.properties
│       └── DBConfing.properties
│       └── environment.properties
│       └── Executionconfig.properties
│       └── sqlQueries.properties
│  
├── .env                                                              # Azure Vault Config file
├── browserstack_web_chrome.yml                                       # Web BrowserStack Config file
├── bs_app_android.yml                                                # Android Mobile App BrowserStack Config file
├── bs_app_ios.yml                                                    # IOS Mobile App BrowserStack Config file
├── bs_web_android.yml                                                # Android Mobile Web BrowserStack Config file
├── bs_web_android_parallel.yml                                       # Parallel Android Mobile Web BrowserStack Config file
├── bs_web_ios.yml                                                    # IOS Mobile Web BrowserStack Config file
├── bs_web_ios_parallel.yml                                           # Parallel IOS Mobile Web BrowserStack Config file
├── pom.xml                                                           # Maven configuration file
└── README.md
```
## Setup

#### 1. Clone the repository

> git clone https://github.com/Tractor-Supply-Engineering-Productivity/OnePlatform-Java.git

#### 2. Start IntelliJ IDE & Load the Project/Framework

> File > Open > Browse Project

#### 3. Install dependencies

> mvn clean install

#### 4. Configure Appium
###### Install Appium globally using npm:
> npm install -g appium

###### Start the Appium server:
> appium

#### 5. Set up Android SDK and Environment Variables
> Ensure Android Studio / SDK is installed and environment variables ANDROID_HOME and PATH are set correctly.

## Run all tests
Execute the TestNG.Xml under each package.

1. Go to your OnePlatform-Java folder and run below command for Microservices (API) test cases
> run Microservices_TestNG.xml under .\src\test\java\api\resources\testrunner\Microservices_TestNG.xml
2. Go to your OnePlatform-Java folder and run below command for MobileApp test cases
> run MobileApp_TestNG.xml under .\src\test\java\mobileapp\resources\testrunner\MobileApp_TestNG.xml
3. Go to your OnePlatform-Java folder and run below command for MobileWeb test cases
> run MobileWeb_TestNG.xml under .\src\test\java\mobileweb\resources\testrunner\MobileWeb_TestNG.xml
4. Go to your OnePlatform-Java folder and run below command for Web test cases
> run Single_Browser_Sequential.xml under .\src\test\java\web\resources\testrunner\Single_Browser_Sequential.xml


### Allure Manual Installation
1. Download the allure latest version as zip archive from Maven Central.
2. Unpack the archive to allure-commandline directory.
3. Navigate to bin directory.
4. Use allure.bat for Windows or allure for other Unix platforms.
5. Add allure to system PATH.

#### Check the installation
    > allure --version
    
#### Generate allure report
    > allure serve target/allure-results

## Reporting
1. Shareable Allure Report:
    - After running tests, a Single file Allure report gets generated in the allure-report directory on the Framework level.
2. Non-Shareable Allure Report:
    - After running tests, reports are generated in the target/allure-reports directory.
3. Cucumber HTML Reports:
    - After running tests, cucumber HTML reports are generated in the target/Reports directory.

### How to use Vault?
1. Store secrets / passwords in Azure vault using a unique key
   - Example: prod-login-pwd = 'Original_Password'
2. Use getVaultData() to use your password from vault
   - Example: getVaultData("prod-login-pwd");
3. For more details, refer to the below document on sharepoint
[](https://tractorsupply.sharepoint.com/:w:/s/QEAutoPOVs-01_COE/EV6b_yAdFeRHgRCZBo9cLXgBE24KiB6elji0pt8bLw-b9g?e=gpLzkc)


### How to run scripts on Browser Stack?
1. **Using below Maven Command on IDE Terminal Command Prompt:**
    > mvn test -DsuiteXmlFile="src/test/java/web/resources/testrunner/SingleBrowserExecute.xml" -Dbrowserstack.username=<USER> -Dbrowserstack.accesskey=<ACCESS_KEY> -Dbrowserstack.config="browserstack_web_chrome.yml" -Dcucumber.filter.tags="@ProdTSC"
   
2. **Form IntelliJ IDEA**:
   1. Go to `Run` > `Edit Configurations`.
   2. Select your test configuration or create a new one.
   3. In the `VM Options` section, click the `...` button.
   4. Add the following variables:
   - `-Dbrowserstack.username=<USER>`.
   - `-Dbrowserstack.accesskey=<ACCESS_KEY>`.
   - `-Dbrowserstack.config="bs_<DEVICE>.yml"`.
   5. Click `OK` to save and apply the changes.
   6. Run the Configured TestNG / Runner file

**Notes:**
1. Don't Provide your BrowserStack credentials on BrowserStack YAML files
   1) Provide a Username & Access key from the VM Options of Run configurations or from the Maven command like below 

      `-Dbrowserstack.username=your_username -Dbrowserstack.accesskey=your_accesskey`
2. Change a test runner .xml file based on your requirement
3. Change the tags to specify a group of scenarios based on your requirement
4. After all, your changes, run the above maven command on Command Prompt


### How to Update Status to Q-Test?
1) With the Qtest integration, the status of test scenarios is now updated automatically in qTest, provided the test case ID needs to be configured in the feature file.
   - We Need to pass the below Arguments along with the above-mentioned maven Command If user wishes to update the status.
     - -DupdateStatus=Yes
     - -DprojectName="<{qTest Project Name}>"
     - -DtestSuiteIdAndName="<{TestSuite ID} {TestSuite Name}>"
     - Example: `-DupdateStatus=Yes  -DprojectName="Omnichannel"  -DtestSuiteIdAndName="TS-145 Ymg qtest Suite"`

## Additional Notes:
- Update `pom.xml` to add any additional dependencies or plugins required for the project.
- Don't push your code to repo with any exposed credentials/secrets over the code.
- Don't push `.env` file to git repo, make sure you added `.env` in the `.gitignore` file.
- Each Slice has its own `README.md` file with detailed view for more understanding.
