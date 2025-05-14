@api @order @createorder @create
Feature: Create a new Order

  Scenario Outline: Buy Order  
    Given I am logged in with <user>
    When I create a new product
    Then I receive a 201 response code
    When I create a "buy" order for <orderQuantity> products
    Then I receive a 201 response code
    And I see the key "success" with value "true" in the response
    And I see the key "orderType" with value "buy" in the response

    Examples:
        | user   | orderQuantity |
        | USER01 | 1             | 

  Scenario Outline: Sell Order
    Given I am logged in with <user>
    When I create a new product
    Then I receive a 201 response code
    When I create a "buy" order for <orderQuantity> products
    Then I receive a 201 response code
    When I create a "sell" order for <orderQuantity> products
    Then I receive a 201 response code
    And I see the key "success" with value "true" in the response
    And I see the key "orderType" with value "sell" in the response

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

  Scenario Outline: Validation Error - Invalid Order Type
    Given I am logged in with <user>
    When I create a new product
    Then I receive a 201 response code
    When I create a "wrong" order for <orderQuantity> products
    Then I receive a 400 response code
    And I see the key "message" with value "Invalid order type. Must be \"buy\" or \"sell\"" in the response

    Examples:
        | user   | orderQuantity |
        | USER01 | 1             | 

    Scenario Outline: Validation Error - Invalid Quantity
      Given I am logged in with <user>
      When I create a new product
      Then I receive a 201 response code
      When I create a "buy" order for <orderQuantity> products
      Then I receive a 400 response code
      And I see the key "message" with value "Quantity must be a positive number" in the response

      Examples:
          | user   | orderQuantity |
          | USER01 | -1            |     
          | USER01 | 0             | 

  Scenario: Unauthorised
    Given I am an unauthorised user
    When I create a <order> order for <orderQuantity> products
    Then I receive a 401 response code
    And I see the key "message" with value "No token, authorization denied" in the response

    Examples:
        | order | orderQuantity |
        | buy   | 1             |
        | sell  | 1             |

  Scenario Outline: Invalid Token
    Given I am logged in with INVALIDUSER
    When I create a <order> order for <orderQuantity> products
    Then I receive a 401 response code
    And I see the key "message" with value "Invalid token" in the response

    Examples:
        | order | orderQuantity |
        | buy   | 1             |
        | sell  | 1             |

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