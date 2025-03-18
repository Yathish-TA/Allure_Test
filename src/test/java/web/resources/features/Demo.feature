Feature: Saucedemo

  Background: User is Loaded Data In
    Given  Data fetching "tsc_ecomm_test_selection.xlsx" and "Q4_ECOMM"

  @Demo
  Scenario Outline: Login with valid credentials and do logout
    Given Login with "<username>" and "<pwd>"
    When Verify able to login and perform logout
#    Then Close Browser

    Examples:
      | username      | pwd          |
      | standard_user | secret_sauce |


#  @Demo
#  Scenario: Login without credentials
#    Given Login without username and password
#    When Verify not able to login with error message
#    Then Close Browser

