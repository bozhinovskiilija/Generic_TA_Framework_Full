Feature: Scenario-Outline-Example

  @Example1
  Scenario Outline: Login with phone, username and email
    Given I am on the login page
    When I submit the form with the following "<user>" and "<password>"
    Then The "<message>" should be displayed
    Examples:
      |user          |password |message |
      |223305        |lozinka  |Invalid username and password. |
      |myusername    |pa$sword |poraka2 |
      |mail@mail.com |secret   |poraka3 |


#  Scenario Outline
#  The Scenario Outline keyword can be used to run the same Scenario multiple times,
#  with different combinations of values.


#  A Scenario Outline must contain one or more Examples (or Scenarios) section(s).
#  Its steps are interpreted as a template which is never directly run. Instead,
#  the Scenario Outline is run once for each row in the Examples section beneath it
#  (not counting the first header row).
#
#  The steps can use <> delimited parameters that reference headers in the examples table.
#  Cucumber will replace these parameters with values from the table before it tries to match
#  the step against a step definition




