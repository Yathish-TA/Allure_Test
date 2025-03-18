Feature: Mobile Demo Scenarios Execution

  Background: User is Loaded Data In and Launch driver
    Given Fetching Mobile data "MobileWeb_TestData.xlsx" and "Mobile_Q4_ECOMM"

  @smoke @test1
  Scenario Outline: Search product in search bar
    Given Loading testcase data: "<TestCaseID>"
    When Product is searched in the search bar
    Then Verify product is displayed

    Examples:
      | TestCaseID       |
      | mobile_fr1_tc002 |
      | mobile_fr1_tc003 |

  @smoke @regression @sanity
  Scenario: TSC Login test scenario
    Given Loading testcase data: "mobile_fr1_tc004"
    When User tries to login with email and password
    Then Verify Sign in error message

  @smoke @sanity
  Scenario: Check the order Status details
    Given Loading testcase data: "mobile_fr1_tc001"
    When Check order status with order number and name
    Then Verify the order details status

  @smoke
  Scenario: Product category page scenario
    Given User selects a product category from the menu
    Then The respective product category page should be displayed

  @smoke @sanity
  Scenario: TSC Forgot password test scenario
    Given On login page user navigates to the Forgot your password page
    Then The Change password page should be displayed
