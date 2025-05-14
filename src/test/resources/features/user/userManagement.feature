@api @login
Feature: User Login

  Scenario Outline: User logged in successfully
    Given the user to test with is <user>
    When I request to log in
    Then I receive a 200 response code
    And I see the key "message" with value "logged in successful" in the response
    And I see the token in the response

    Examples:
        | user   |
        | USER02 | 

  Scenario Outline: No Credentials
    Given the user to test with is <user>
    When I request to log in with no credentials
    Then I receive a 400 response code
    And I see the key "message" with value "Invalid credentials" in the response
    And I do not see the token in the response

    Examples:
        | user   |
        | USER02 |  

  Scenario Outline: Invalid credentials
    Given the user to test with is <user>
    When I request to log in
    Then I receive a 400 response code
    And I see the key "message" with value "Invalid credentials" in the response
    And I do not see the token in the response

    Examples:
        | user        |
        | INVALIDUSER |

# Not possible to simulate 5XX errors end to end integrated without some external config
# This would be the test if were
  # Scenario Outline: Server Error
  #   Given the user to test with is <user>
  #   When I request to log in
  #   Then I receive a 500 response code

  #   Examples:
  #       | user   |
  #       | USER02 | 

