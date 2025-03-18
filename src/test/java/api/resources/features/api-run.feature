Feature: API Demo Execution

  Background: User is Loaded Data In
    Given Assigning Payload source "API_TestData.xlsx" and "Valid_TestData"
    And Load Data into Methods

  @Smoke @qtest @TC-15981 @TC-16018 #API Chaining
  Scenario Outline: Testing the API Chaining endpoints of Browser Stack
    When Make API call to run "<DataBinding>"
    Then Validate the json response with the expected output "<DataBinding>"
    When Make API call to run "<SessionStatus>"
    Then Validate the json response with the expected output "<SessionStatus>"
    When Make API call to run "<UpdateSession>"
    Then Validate the json response with the expected output "<UpdateSession>"

    Examples:
      | DataBinding | SessionStatus | UpdateSession |
      | BS_Test_001 | BS_Test_002   | BS_Test_003   |


  @Smoke @qtest @TC-16020  #DB Case
  Scenario Outline: Testing the Reqres Api endpoints and Validating the DB results
    When Make API call to run "<DataBinding>"
    Then Validate the json response with the expected output "<DataBinding>"
    And Establish the DB connection "WCS"
    Then Validate the DB results for query "<Query>" against "<ExpectedValue>"
    Examples:
      | DataBinding | Query          | ExpectedValue          |
      | Test_DB_002 | Sql_BopisQuery | Test_DB_002_first_name |


  @Smoke1 @Test #UAT Case
  Scenario Outline: Test the endpoints for UAT Api's
    When Make API call to run "<DataBinding>"
    Then Validate the json response with the expected output "<DataBinding>"
    When Make API call to run "<CartStatus>"
    Then Validate the json response with the expected output "<CartStatus>"
    Examples:
      | DataBinding  | CartStatus   |
#      | Test_Api_001 | Test_Api_002 |
#      | ResponseValidation_A | ResponseValidation_B |
      | JSONArray_Api_001 | StoreAvailability_Api |

  @Smoke @Test
  Scenario Outline: Test the endpoints for Reqres Api's
    When Make API call to run "<DataBinding>"
    Then Validate the json response with the expected output "<DataBinding>"
    Examples:
      | DataBinding          |
#      | Test_EP_001 |
      | ResponseValidation_A |
      | ResponseValidation_B |
#      | No_Json_Template     |


  @Smoke @qtest @TC-16019
  Scenario Outline: Test the Different Body Type of API's
    When Make API call to run "<DataBinding>"
    Then Validate the json response with the expected output "<DataBinding>"
    Examples:
      | DataBinding  |
      | Test_Api_001 |
#      | RAW_JSON_Object_Input |
#      | RAW_JSON_Array_Input |
#      | ADD_OR_UPDATE_JSON |
#      | FORM_DATA_CHECK |
#      | URLENCODED_CHECK |

