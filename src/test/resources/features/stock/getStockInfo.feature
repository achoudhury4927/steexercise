@api @order @getorder @get
Feature: Get current stock level for a specific product

  Scenario Outline: Get product  
    Given I am logged in with <user>
    When I create a new product
    Then I receive a 201 response code
    When I create a "buy" order for <orderQuantity> products
    Then I receive a 201 response code
    When I request the stock information for the product
    Then I receive a 200 response code
    And I verify the stock information

    Examples:
        | user   | orderQuantity |
        | USER01 | 1             |

  Scenario Outline: No Orders Found 
    Given I am logged in with <user>
    When I create a new product
    Then I receive a 201 response code
    When I request the stock information for the product
    Then I receive a 404 response code
    And I see the key "message" with value "No orders found for this product" in the response

    Examples:
        | user   | orderQuantity |
        | USER01 | 1             |    

  Scenario Outline: Insufficient Stock
    Given I am logged in with <user>
    When I create a new product
    Then I receive a 201 response code
    When I create a <order> order for <orderQuantity> products
    Then I receive a 400 response code
    And I see the key "message" with value "Insufficient stock for sale" in the response

    Examples:
        | user   | order | orderQuantity |
        | USER02 | sell  | 3             |

  Scenario Outline: Product not found
    Given I am logged in with <user>
    When I create a <order> order for <orderQuantity> products
    Then I receive a 404 response code
    And I see the key "message" with value "Product not found" in the response

    Examples:
        | user   | order | orderQuantity |
        | USER01 | buy   | 1             |  
        | USER02 | sell  | 1             |

  Scenario: Unauthorised
    Given I am an unauthorised user
    When I request the stock information for the product
    Then I receive a 401 response code
    And I see the key "message" with value "No token, authorization denied" in the response


  Scenario: Invalid Token
    Given I am logged in with INVALIDUSER
    When I request the stock information for the product
    Then I receive a 401 response code
    And I see the key "message" with value "Invalid token" in the response

# Not possible to simulate 5XX errors end to end integrated without some external config
# This would be the test if were
# Scenario Outline: Server Error
  #   Given I am logged in with <user>
  #   When I create a new product with the details <name> and <price> and <type> and <quantity>
  #   Then I receive a 201 response code
  #   When I create a "buy" order for <orderQuantity> products
  #   Then I receive a 500 response code
  #   When I create a "sell" order for <orderQuantity> products
  #   Then I receive a 500 response code

  #   Examples:
  #       | user   | name     | price | type   | quantity | orderQuantity |
  #       | USER01 | ToSell2  | 21.99 | mobile | 5        | 1             |  