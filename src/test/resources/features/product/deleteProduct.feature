@api @product @deleteproduct @delete
Feature: Delete a Product

  Scenario Outline: Product Removed
    Given I am logged in with <user>
    When I create a new product
    Then I receive a 201 response code
    When I delete the product
    Then I receive a 200 response code
    And I see the key "message" with value "Product removed" in the response

    Examples:
        | user   |
        | USER03 |

  Scenario Outline: Product not found
    Given I am logged in with <user>
    When I delete the product
    Then I receive a 404 response code
    And I see the key "message" with value "Product not found" in the response

    Examples:
        | user   |
        | USER01 |

  Scenario: Unauthorised
    Given I am an unauthorised user
    When I delete the product
    Then I receive a 401 response code
    And I see the key "message" with value "No token, authorization denied" in the response

  Scenario: Invalid Token
    Given I am logged in with INVALIDUSER
    When I delete the product
    Then I receive a 401 response code
    And I see the key "message" with value "Invalid token" in the response  

# Not possible to simulate 5XX errors end to end integrated without some external config
# This would be the test if were
#   Scenario Outline: Server Error 
#     Given I am logged in with <user>
#     When I create a new product
    # Then I receive a 201 response code
    # When I delete the product
    # Then I receive a 500 response code

#     Examples:
#         | user   |
#         | USER01 |    