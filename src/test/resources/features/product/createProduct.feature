@api @product @createproduct @create
Feature: Create a Product

  Scenario Outline: New product  
    Given I am logged in with <user>
    When I create a new product
    Then I receive a 201 response code
    And I verify a new product was created

    Examples:
        | user  |
        | USER06 | 

  Scenario Outline: New product - Mandatory details only
    Given I am logged in with <user>
    When I create a new product with mandatory details only
    Then I receive a 201 response code
    And I verify a new product was created

    Examples:
        | user   |
        | USER01 |

  Scenario Outline: New Product - Price can be number with no decimals
    Given I am logged in with <user>
    When I create a new product with the details <price> and <type> and <quantity>
    Then I receive a 201 response code
    And I verify a new product was created

    Examples:
        | user   | price | type   | quantity |
        | USER01 | 21    | mobile | 0        |      

  Scenario Outline: Validation Error - No Mandatory details provided
    Given I am logged in with <user>
    When I try to create a new product with no mandatory details
    Then I receive a 400 response code
    And I see the key "message" with value "Validation failed" in the response
    And I see the key "errors.name" with value "Path `name` is required." in the response
    And I see the key "errors.price" with value "Path `price` is required." in the response

    Examples:
        | user   |
        | USER01 |

  Scenario Outline: Validation Error - Product Already Exists
    Given I am logged in with <user>
    When I create a new product with the details <price> and <type> and <quantity>
    Then I receive a 201 response code
    When I create a new product with the details <price> and <type> and <quantity>
    Then I receive a 400 response code
    And I see the key "message" with value "Product with this name and type already exists" in the response

    Examples:
        | user   | price | type   | quantity |
        | USER01 | 21.99 | mobile | 0        |

  Scenario Outline: Validation Error - Invalid Price
    Given I am logged in with <user>
    When I create a new product with the details <price> and <type> and <quantity>
    Then I receive a 400 response code
    And I see the key "message" with value "Price must be greater than 0" in the response

    Examples:
        | user   | price  | type   | quantity |
        | USER01 | 0      | mobile | 0        | 

  Scenario Outline: Validation Error - Invalid Quantity
    Given I am logged in with <user>
    When I create a new product with the details <price> and <type> and <quantity>
    Then I receive a 400 response code
    And I see the key "message" with value "Validation failed" in the response
    And I see the key "errors.quantity" with value "is less than minimum allowed value" in the response

    Examples:
        | user   | price  | type   | quantity |
        | USER01 | 10.01  | mobile | -10      | 

  Scenario Outline: Validation Error - Invalid Product Type
    Given I am logged in with <user>
    When I create a new product with the details <price> and <type> and <quantity>
    Then I receive a 400 response code
    And I see the key "message" with value "Validation failed" in the response
    And I see the key "errors.productType" with value "is not a valid enum value" in the response

    Examples:
        | user   | price  | type    | quantity |
        | USER01 | 21.99  | mobilez | 0        |    

  Scenario Outline: Unauthorised
    Given I am an unauthorised user
    When I create a new product with all the details <name> and <price> and <type> and <quantity>
    Then I receive a 401 response code
    And I see the key "message" with value "No token, authorization denied" in the response  

    Examples:
        | name         | price  | type   | quantity |
        | Unauthorised | 21.99  | mobile | 0        | 

  Scenario Outline: Invalid Token
    Given I am logged in with INVALIDUSER
    When I create a new product with all the details <name> and <price> and <type> and <quantity>
    Then I receive a 401 response code
    And I see the key "message" with value "Invalid token" in the response  

    Examples:
        | user   | name    | price  | type   | quantity |
        | USER01 | Invalid | 21.99  | mobile | 0        | 

# Not possible to simulate 5XX errors end to end integrated without some external config
# This would be the test if were
#   Scenario Outline: Server Error 
#     Given I am logged in with <user>
#     When I create a new product
#     Then I receive a 500 response code

#     Examples:
#         | user   |
#         | USER01 |     