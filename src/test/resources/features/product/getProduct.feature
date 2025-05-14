@api @product @getproduct @get
Feature: Get Product Details

  Scenario Outline: Get a product by its ID
    Given I am logged in with <user>
    When I create a new product
    Then I receive a 201 response code
    When I request a product
    Then I receive a 200 response code
    And I verify the response contains the expected product

    Examples:
        | user   |
        | USER01 |

  Scenario Outline: Product not found
    Given I am logged in with <user>
    When I request a product
    Then I receive a 404 response code
    And I see the key "message" with value "Product not found" in the response

    Examples:
        | user   |
        | USER01 |

# Not possible to simulate 5XX errors end to end integrated without some external config
# This would be the test if were
# Scenario Outline: Get a product by its ID
  #   Given I am logged in with <user>
  #   When I create a new product with the details <name> and <price> and <type> and <quantity>
  #   Then I receive a 201 response code
  #   When I request a product
  #   Then I receive a 500 response code