package starter.api.steplibrary.httpresponses;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import starter.api.singleton.ApiTestContext;

public class HttpResponseValidation {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponseValidation.class);
    private ApiTestContext apiTestContext = ApiTestContext.getInstance();

    public void verifyResponseCode(int responseCode){
        logger.debug("Response code received is {}", apiTestContext.getResponse().statusCode());
        assertEquals(responseCode, apiTestContext.getResponse().statusCode());
    }

    public void verifyPathHasValue(String jsonPath, String value){
        logger.debug("Looking for {} in received response", value);
        assertTrue("Did not receive " + value + " in the " + jsonPath + " field of the response", apiTestContext.getResponse().jsonPath().get(jsonPath).toString().contains(value));
    }

    public void verifyTokenIsPresent(){
        assertNotNull("No token received", apiTestContext.getResponse().jsonPath().get("token"));
    }

    public void verifyTokenIsNotPresent(){
        assertNull("Token received", apiTestContext.getResponse().jsonPath().get("token"));
    }
    
    public void verifyResponseContainsListOfProducts(){
        assertTrue("No Products Received", apiTestContext.getResponse().jsonPath().getList("$").size() > 0);
    }

    public void verifyResponseContainsExpectedProductResponsePaths(){
        assertTrue("No ProductId in response", apiTestContext.getResponse().jsonPath().getList("$").get(0).toString().contains("productId"));
        assertTrue("No name in response", apiTestContext.getResponse().jsonPath().getList("$").get(0).toString().contains("name"));
        assertTrue("No price in response", apiTestContext.getResponse().jsonPath().getList("$").get(0).toString().contains("price"));
        assertTrue("No productType in response", apiTestContext.getResponse().jsonPath().getList("$").get(0).toString().contains("productType"));
        assertTrue("No quantity in response", apiTestContext.getResponse().jsonPath().getList("$").get(0).toString().contains("quantity"));
        assertTrue("No createdAt in response", apiTestContext.getResponse().jsonPath().getList("$").get(0).toString().contains("createdAt"));
    }

    public void verifyNewProductCreatedWithProvidedDetails(String name, double price, String type, int quantity){
        logger.debug("Asserting provided name {}, price {}, type {}, and quantity {} are the same as the product saved from the response", name, price, type , quantity);
        assertEquals(name, apiTestContext.getProduct().name);
        assertEquals(price, apiTestContext.getProduct().price,0);
        assertEquals(type, apiTestContext.getProduct().productType);
        assertEquals(quantity, apiTestContext.getProduct().quantity);
        assertNotNull(apiTestContext.getProduct().productId);
        assertNotNull(apiTestContext.getProduct().createdAt);
    }

    public void verifyNewProductCreatedWithDetailsFromContext(){
        assertEquals(apiTestContext.getName(), apiTestContext.getProduct().name);
        assertEquals(apiTestContext.getPrice(), apiTestContext.getProduct().price,0);
        assertEquals(apiTestContext.getProductType(), apiTestContext.getProduct().productType);

        logger.debug("Mandatory Fields Only is set to: {}", apiTestContext.isMandatoryFieldsOnly());
        if(apiTestContext.isMandatoryFieldsOnly()) {
            assertEquals(0, apiTestContext.getProduct().quantity, 0);
        } else {
            assertEquals(apiTestContext.getQuantity(), apiTestContext.getProduct().quantity, 0);
        }

        assertNotNull(apiTestContext.getProduct().productId);
        assertNotNull(apiTestContext.getProduct().createdAt);
    }

    public void verifyProductDetailsInResponseMatchesProduct(){
        assertTrue("The name was not the same", apiTestContext.getResponse().jsonPath().get("name").toString().contains(apiTestContext.getProduct().name));
        assertTrue("The price was not that same", apiTestContext.getResponse().jsonPath().get("price").toString().contains(String.valueOf(apiTestContext.getProduct().price)));
        assertTrue("The type was not that same", apiTestContext.getResponse().jsonPath().get("productType").toString().contains(apiTestContext.getProduct().productType));
        assertTrue("The quantity was not that same", apiTestContext.getResponse().jsonPath().get("quantity").toString().contains(String.valueOf(apiTestContext.getProduct().quantity)));
        assertTrue("The product id was not the same", apiTestContext.getResponse().jsonPath().get("productId").toString().contains(apiTestContext.getProduct().productId));
    }


    public void verifyUpdatedProductDetailsInResponseMatches(){
        assertTrue("The name was not updated", apiTestContext.getResponse().jsonPath().get("name").toString().contains(apiTestContext.getName()));
        assertTrue("The price was not updated", apiTestContext.getResponse().jsonPath().get("price").toString().contains(String.valueOf(apiTestContext.getPrice())));
        assertTrue("The type was not updated", apiTestContext.getResponse().jsonPath().get("productType").toString().contains(apiTestContext.getProductType()));
        assertTrue("The quantity was not updated", apiTestContext.getResponse().jsonPath().get("quantity").toString().contains(String.valueOf((int) apiTestContext.getQuantity())));
    }

    public void verifyResponseContainsExpectedOrderResponsePaths(){
        assertTrue("The product id was not the same", apiTestContext.getResponse().jsonPath().get("productId").toString().contains(apiTestContext.getProduct().productId));
        assertTrue("No total buys in response", apiTestContext.getResponse().jsonPath().getMap("$").toString().contains("totalBuys"));
        assertTrue("No total sells in response", apiTestContext.getResponse().jsonPath().getMap("$").toString().contains("totalSells"));
        assertTrue("No current stock in response", apiTestContext.getResponse().jsonPath().getMap("$").toString().contains("currentStock"));
        assertTrue("No total transactions in response", apiTestContext.getResponse().jsonPath().getMap("$").toString().contains("totalTransactions"));
    }

}
