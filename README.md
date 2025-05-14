# Candidate Details

Role Reference: 392590
Application ID number: 13795139

# Requirements

Maven
Java 19

# Run Project

```json
$ mvn clean verify
```

# Notes

Assumptions:
When run on a pipeline each test would be deployed in its own pod thus have its own context

I started with https://github.com/serenity-bdd/serenity-cucumber-starter then added lombok, RestAssured, slf4j for logging and a JSON parser package.

# Test Notes

- I have received 500 errors with a html body response stating cloudflare when running on a vpn or when running the framework multiple times but I couldnt recreate these issues. It is very unlikely but this may introduce some test failures.
- Updating a product allows you to input invalid data that would be rejected during creations. There are three tests in updateProduct.feature that are commented out due to them returning 200 response codes when based on creating a product they should return 400 response codes.
- 500 error tests: I have added but left commented out on all the features a 500 response code error test which would require external config to pass. Or updating the API to simulate a 500 error based on some test data.
- Ideally when run on a pipeline like jenkins each test would be deployed in its own pod

These are some other tests I identified that during testing I would check manually but not include in automation

- Delete request on /auth/login
- Put request on /auth/login
- Get request on /auth/login
- Delete request on /products
- Put request on /products
- Post request on /products/{id}
- Delete request on /orders
- Put request on /orders
- Get request on /orders
- Delete request on /orders/product/{productId}
- Put request on /orders/product/{productId}
- Post request on /orders/product/{productId}
- Delete request on /status
- Put request on /status
- Post request on /status
