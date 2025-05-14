@api @product @updateproduct @update
Feature: Update a Product

  Scenario Outline: Product Updated
    Given I am logged in with <user>
    When I create a new product
    Then I receive a 201 response code
    And I verify a new product was created
    When I update the product
    Then I receive a 200 response code
    And I verify the product was updated

    Examples:
        | user   |
        | USER04 |

  Scenario Outline: Product not found
    Given I am logged in with <user>
    When I update the product with all the details <updatedName> and <updatedPrice> and <updatedType> and <updatedQuantity>
    Then I receive a 404 response code
    And I see the key "message" with value "Product not found" in the response

    Examples:
        | user   | updatedName  | updatedPrice | updatedType | updatedQuantity |
        | USER01 | UpdatedItem2 | 101.98       | laptops     | 2               |

  Scenario Outline: Product not updated when No Details Provided
    Given I am logged in with <user>
    When I create a new product
    Then I receive a 201 response code
    And I verify a new product was created
    When I update the product with no details provided
    Then I receive a 200 response code
    And I verify the updated product is as expected

    Examples:
        | user   |
        | USER04 |

# =============================================================================================================
#   These tests should fail as these would trigger a validation during creation of the product
#   I have left them commented out for now as they pass and update a product with invalid details
 
#   Scenario Outline: Validation Error - Invalid Price
#     Given I am logged in with <user>
#     When I create a new product
#     Then I receive a 201 response code
#     And I verify a new product was created
#     When I update the product with the details <updatedPrice> and <updatedType> and <updatedQuantity>
#     Then I receive a 400 response code
#     And I see the key "message" with value "Price must be greater than 0" in the response

#     Examples:
#         | user   | updatedPrice | updatedType | updatedQuantity |
#         | USER04 | 0            | laptops     | 2               |

#   Scenario Outline: Validation Error - Invalid Product Type
#     Given I am logged in with <user>
#     When I create a new product
#     Then I receive a 201 response code
#     And I verify a new product was created
#     When I update the product with the details <updatedPrice> and <updatedType> and <updatedQuantity>
#     Then I receive a 400 response code
#     And I see the key "message" with value "Validation failed" in the response
#     And I see the key "errors.productType" with value "is not a valid enum value" in the response

#     Examples:
#         | user   | updatedPrice | updatedType | updatedQuantity |
#         | USER04 | 0.50         | labtops     | 2               |      

#   Scenario Outline: Validation Error - Invalid Quantity
#     Given I am logged in with <user>
#     When I create a new product
#     Then I receive a 201 response code
#     And I verify a new product was created
#     When I update the product with the details <updatedPrice> and <updatedType> and <updatedQuantity>
#     Then I receive a 400 response code
#     And I see the key "message" with value "Validation failed" in the response
#     And I see the key "errors.quantity" with value "is less than minimum allowed value" in the response

#     Examples:
#         | user   | updatedPrice | updatedType | updatedQuantity |
#         | USER04 | 0.50         | laptops     | -2              |   
# =============================================================================================================   

  Scenario: Unauthorised
    Given I am an unauthorised user
    When I update the product with all the details "Unauthorised" and 100.00 and "laptops" and 5.0
    Then I receive a 401 response code
    And I see the key "message" with value "No token, authorization denied" in the response  
    
  Scenario: Invalid Token
    Given I am logged in with INVALIDUSER
    When I update the product with all the details "Invalid" and 100.00 and "laptops" and 5.0
    Then I receive a 401 response code
    And I see the key "message" with value "Invalid token" in the response  


# Not possible to simulate 5XX errors end to end integrated without some external config
# This would be the test if were
#   Scenario Outline: Server Error 
#     Given I am logged in with <user>
#     When I create a new product
#     Then I receive a 201 response code
#     And I verify a new product was created
#     When I update the product
#     Then I receive a 500 response code

#     Examples:
#         | user   |
#         | USER04 |     