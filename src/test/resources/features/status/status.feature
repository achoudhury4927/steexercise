@api @status
Feature: Status

  Scenario: Api is available
    When I check the status of the API
    Then I receive a 200 response code
    And I see the key "status" with value "OK" in the response
    And I see the key "dbStatus" with value "Connected" in the response

# Not possible to simulate 5XX errors end to end integrated without some external config
# This would be the test if were
#   Scenario: Api is unavailable
#     When I check the status of the API
#     Then I receive a 503 response code
#     And I see the status "Unhealthy" in the response
#     And I see the dbStatus "Disconnected" in the response
