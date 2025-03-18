Feature: TSC CMA Mobile Application Testing on Both Android and IOS devices

  Background: Loading Data from the Excel Sheet
    Given Fetching Mobile App data "MobileApp_TestData.xlsx" and "POS_APP_Data"

  @smoke @sanity
  Scenario Outline: Order Items and Make Payment
    Given Loading App testcase Data: "<TestCaseID>"
    When Select table and number of guest for Dine in
    Examples:
      | TestCaseID    |
      | pos_fr1_tc001 |

  @Smoke @ymg @qtest @TC-16016
  Scenario Outline: HomePage Testing - Manage Card in Home page for CBCC Register User
    Given A User has logged in using email:"<email>" & password:"<password>"
    And A User is on the Home Page
    Then A User Verifies the button Manage My PLCC Card is displayed on the home page
    And A Click on Manage My PLCC Card button on the page
    And A Verify the My PLCC page is displayed after clicking Manage My PLCC Card button

    Examples:
      | email                  | password              |
      | eartha85@ayqtellsu.com | wcs-uat2-userpassword |

  @Smoke @ymg @qtest @TC-16017
  Scenario Outline: PLP_Page Testing - Validate left swipe functionality from PLP and PDP
    When A User has logged in as guest user
    And A User is on the Home Page
    Given A User search for SKU "<keyword>"
    And A Click on Product Name Link for LineItem "<lineitemnumber>"
    Then A Swipe left to right on pixel "<X_axis>" and "<Y_axis>" from PDP
    And A Verify Search Page is visible
    Then A Click on the SHOP tab
    And A Click on Shop Category with name of "<shopBycategory>"
    Then A Swipe left to right on pixel "<X_axis>" and "<Y_axis>" from SHOP
    And A Verify Shop Page is visible

    Examples:
      | keyword | lineitemnumber | shopBycategory | X_axis | Y_axis |
      | Pan     | 1              | Farm & Ranch   | 20     | 1500   |