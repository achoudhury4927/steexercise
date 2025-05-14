package starter.api.steplibrary.httprequests;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import starter.api.model.endpoints.ProductEndpoints;
import starter.api.model.schemas.Product;
import starter.utils.RandomUtils;

public class Put extends Request{

    private static final Logger logger = LoggerFactory.getLogger(Put.class);

    public Put get() {return this;}

    public void updateProductWithDetails(String name, double price, String type, double quantity){
        putProduct(name, price, type, quantity);
    }

    public void updateProductWithDetails(double price, String type, double quantity){
        putProduct(apiTestContext.getProduct().name, price, type, quantity);
    }

    public void updateProductWithNoNewDetails(){
        putProduct(apiTestContext.getProduct().name, apiTestContext.getProduct().price, apiTestContext.getProduct().productType, apiTestContext.getProduct().quantity);
    }

    public void updateProduct(){
        String uniqueName = RandomUtils.randomString(10);
        String productType = RandomUtils.randomElementFromList(productTypeList);
        double price = RandomUtils.randomDouble();
        int quantity = RandomUtils.randomInt();
        
        apiTestContext.setName(uniqueName);
        apiTestContext.setPrice(price);
        apiTestContext.setProductType(productType);
        apiTestContext.setQuantity(quantity);

        putProduct(uniqueName, price, productType, quantity); 

    }

    public void putProduct(String name, double price, String type, double quantity){
        JSONObject body = new JSONObject()
            .put(NAME,name)
            .put(PRICE,price)
            .put(PRODUCT_TYPE,type)
            .put(QUANTITY,quantity);

        String productEndpoint = apiTestContext.getProduct() != null ? 
            ProductEndpoints.PRODUCTID.formatProductId(apiTestContext.getProduct().productId) : 
            ProductEndpoints.PRODUCTID.formatProductId("NoId");
        
        logger.debug("Unauthorised request is set to {}", apiTestContext.isUnauthorisedRequest());
        logger.debug("No Details is set to {}", apiTestContext.isNoDetailsOnUpdate());    
        Response response = apiTestContext.isUnauthorisedRequest() ? 
            DWPRequestBuilder.given().withEndpoint(productEndpoint).withContentType(ContentType.JSON).withBody(body).put() : 
                apiTestContext.isNoDetailsOnUpdate() ? 
                    DWPRequestBuilder.given().withEndpoint(productEndpoint).withAuthentication().withContentType(ContentType.JSON).put() :
                    DWPRequestBuilder.given().withEndpoint(productEndpoint).withAuthentication().withContentType(ContentType.JSON).withBody(body).put();

        apiTestContext.setResponse(response);
        apiTestContext.setProduct(response.getBody().as(Product.class));
        response.prettyPrint();    
    }
    
}
