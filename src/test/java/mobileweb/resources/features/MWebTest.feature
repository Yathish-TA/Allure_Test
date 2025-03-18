Feature: Mobile Web Test Example Scenarios


  @mweb @regression @sanity @qtest @TC-16014
  Scenario: Verify the 'Buy Now' button and Express Checkout is ineligible for Firearm Ammo items from PDP page
    Given User is on Mobile Homepage
    And User click on sign in link
    When User enters email and password
    And Select "EXETER, NH, 03833" zipcode in Store Location
    And Firearm Ammo Items "Dig Defence Xl Animal Barrier 5 Pack, DDXL152405" setup for online sale
    Then verify that Buy Now button is not displayed

  @mweb @qtest @TC-16013
  Scenario Outline: Search product As a Guest User
    Given User is on Mobile Homepage
    When Enter "<SearchKeyWord>" in Search bar
    Then Verify product is displayed
    Examples:
      | SearchKeyWord |
      | Machines      |

  @demo
  Scenario: Demo scenario to make a test fail
    Given User click on sign in link
    When User enters email and password
    Then make test to fail

  @smoke @qtest @TC-16015
  Scenario Outline: Verify Searched Item Header on PLP page
    Given User is on Mobile Homepage
    And User click on sign in link
    When User enters email and password
    And Select "EXETER, NH, 03833" zipcode in Store Location
    And Enter "<SearchKeyWord>" in Search bar
    Then Verify product is displayed
    Examples:
      | SearchKeyWord                                    |
      | Dig Defence Xl Animal Barrier 5 Pack, DDXL152405 |

  @mweb @regression @sanity
  Scenario: Verify age verification disclaimer is displayed when placing an ammo order
    Given Select "EXETER, NH, 03833" zipcode in Store Location
    And Firearm Ammo Items "1000731" setup for online sale
    When   Those Items are present in Checkout (MPCO) page with delivery address "437 Route 125_Brentwood_NH_03833"
    And   Add payment details "5196054186077368_123_05_2027"
    When  Age verification copy displayed in MPCO:Payment page
    And   Customer selects I accept checkbox
    And   Clicks Place Order
    Then  Customer should be allowed to place Order successfully

