@api @product @management
Feature: Product Management

  Scenario: Get All Products
    When I request a list of products
    Then I receive a 200 response code
    And I receive a list of products in the response
    And I verify the details of a product

# Not possible to simulate 5XX errors end to end integrated without some external config
# This would be the test if were
#   Scenario: Get All Products
#     When I request a list of products
#     Then I receive a 500 response code  