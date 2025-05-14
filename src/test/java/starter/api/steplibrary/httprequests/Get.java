package starter.api.steplibrary.httprequests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.restassured.response.Response;
import starter.api.model.endpoints.StatusEndpoints;
import starter.api.model.endpoints.StockEndpoints;
import starter.api.model.endpoints.ProductEndpoints;

public class Get extends Request{

    private static final Logger logger = LoggerFactory.getLogger(Get.class);

    public Get get() {return this;}

    public void healthcheck(){
        String statusEndpoint = StatusEndpoints.STATUS.getPath();

        Response response = DWPRequestBuilder.given().withEndpoint(statusEndpoint).get();
        apiTestContext.setResponse(response);
    }

    public void getProducts(){
        String productEndpoint = ProductEndpoints.PRODUCTS.getPath();
        getProductWithEndpoint(productEndpoint);
    }

    public void getProduct(){
        String productEndpoint = apiTestContext.getProduct() != null ? 
            ProductEndpoints.PRODUCTID.formatProductId(apiTestContext.getProduct().productId) : 
            ProductEndpoints.PRODUCTID.formatProductId("NoId");

        getProductWithEndpoint(productEndpoint);
    }

    public void getProductWithEndpoint(String productEndpoint){
        Response response = DWPRequestBuilder.given().withEndpoint(productEndpoint).get();
        apiTestContext.setResponse(response);
        response.prettyPrint();
    }

    public void getOrder(){
        String stockEndpoint = apiTestContext.getProduct() != null ?
            StockEndpoints.ORDERSID.formatProductId(apiTestContext.getProduct().productId):
            StockEndpoints.ORDERSID.formatProductId("NoId");
        
        logger.debug("Unauthorised Request is set to {}", apiTestContext.isUnauthorisedRequest()); 
        Response response = apiTestContext.isUnauthorisedRequest() ? 
            DWPRequestBuilder.given().withEndpoint(stockEndpoint).get() : 
            DWPRequestBuilder.given().withEndpoint(stockEndpoint).withAuthentication().get();

        apiTestContext.setResponse(response);
        response.prettyPrint();
    }

}
