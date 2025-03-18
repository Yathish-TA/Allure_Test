Feature: TSC Q4 Shop Web Testing
  User should be able to order a product from category based on selection

 Background: User is Loaded Data In
   #Given Open Browser and Click URL
   Given  Data fetching "tsc_ecomm_test_selection.xlsx" and "Q4_ECOMM"


  Scenario: Add personal details and billing details to the product order
    Given Test Case Number "web_fr1_tc001"
    When product category is chosen
    Then product should be ordered

  @Regression @Sanity
  Scenario: Check order status
    Given Test Case Number "web_fr1_tc002"
    When order status is checked with order number and name
    Then Verify the status of the order

  @Regression @Sanity @Vault
  Scenario Outline: Search product in search bar
    Given Launch tsc website and login with "<UNAME>" and "<PWD>"
    When product is searched in the search bar with product name "<Product>"
    Then product is displayed

    Examples:
      | UNAME                                         | PWD                | Product |
      | post_production_automation4@tractorsupply.com | ppva-auto4password | Product |


  @Regression
  Scenario: Services with TSC
    Given Test Case Number "web_fr1_tc004"
    When Services with TSC is clicked
    Then description of the services are displayed

  @Regression
  Scenario: TSC application for Mobile phone
    Given Test Case Number "web_fr1_tc005"
    When Tractor supply app icon is clicked
    Then user is navigated to the application page

  Scenario: TSC application login
    Given Test Case Number "web_fr1_tc006"
    When user signs in with email and password
    Then user is navigated to the tsc page


#  @Smoke
  Scenario: Demo site 1
    Given launch the site and perform login
    When Perform add to cart action
#    And Close Browser

  @Smoke
  Scenario: Demo site 1.1
    Given launch the site and perform login
    When perform logout and login again
#    And Close Browser


  @ProdTSC @qtest @TC-16009
  Scenario: Verify links under Shop Products menu
    Given User is on homepage
    #For "TSC001" plz refer to the CSV file present under web/testdata path
    Then Verifies navbar in the application "TSC001"
    And Verifies more categories "TSC001"

  @ProdTSC @qtest @TC-16010
  Scenario: Verify header and footer links
    When User is on homepage
    Then Verify header links
    And Verify footer links "TSC015"
