Feature: Datatable-Example

  @Example2
  Scenario: Login with wrong username and password
    Given I am on the login page
    When I submit the form with the following
      |username      |password |
      |223305        |lozinka  |
    #  |myusername    |pa$sword |
    Then The "Invalid username and password." should be displayed


# if we want to repeat just one test step we can use "Data Table"
