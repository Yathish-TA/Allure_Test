Feature: API Demo Execution1

  Background: User is Loaded Data In
    Given Assigning Payload source "TestAPI.xlsx" and "API"
    And Load Data into Methods

  @Smoke @Test1
  Scenario Outline: Test the endpoints for Reqres Api's
    When Make API call to run "<DataBinding>"
    Then Validate the json response with the expected output "<DataBinding>"
    Examples:
      | DataBinding |
      | Test_003    |
      | Test_006    |
      | Test_007    |
      | Test_008    |
