Feature: Bulk Quote Form Submission Confirmation

  Background:
    Given User has logged in with valid credentials
    And User is on the Home Page

  @cma @TCM-25785 @qTestTC-210686
  Scenario: Submit a Bulk Quote Request with Complete Details and Verify Confirmation
    Given User search for SKU "137468299"
    Then User should see the Product Detail Page for the SKU
    And User specifies a quantity of "1000" to meet the Bulk Quote Request form requirements
    And User click on the Request a Quote link
    Then User should see the Bulk Quote Request form page
    And User fills out the Bulk Quote Request form with the following details:
      | Field           | Value                           |
      | --------------- | ------------------------------- |
      | Tax Exemption   | Yes                             |
      | Payment Type    | Credit Card                     |
      | Business Name   | Test Corp                       |
      | First Name      | Muhammed                        |
      | Last Name       | S                               |
      | Phone Number    | 887-766-5544                    |
      | Email           | msrinivasan@tractorsupply.com   |
    And User should choose "Pickup" as the Delivery option
    And User enters "Please deliver by next week" in the Additional Information field
    When User clicks the Submit button
    Then User should see the 'Submitting your request...' loader
    And the loader should disappear after submission
    And a Submission Successful popup should appear

