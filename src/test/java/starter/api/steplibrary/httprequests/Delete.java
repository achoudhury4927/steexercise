package starter.api.steplibrary.httprequests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.restassured.response.Response;
import starter.api.model.endpoints.ProductEndpoints;

public class Delete extends Request{

    private static final Logger logger = LoggerFactory.getLogger(Delete.class);

    public Delete get() {return this;}

    public void deleteProduct(){
        String productEndpoint = apiTestContext.getProduct() != null ? 
            ProductEndpoints.PRODUCTID.formatProductId(apiTestContext.getProduct().productId) : 
            ProductEndpoints.PRODUCTID.formatProductId("NoId");
        
        logger.debug("Unauthorised Request is set to {}", apiTestContext.isUnauthorisedRequest());    
        Response response = apiTestContext.isUnauthorisedRequest() ? 
            DWPRequestBuilder.given().withEndpoint(productEndpoint).delete() : 
            DWPRequestBuilder.given().withEndpoint(productEndpoint).withAuthentication().delete();

        apiTestContext.setResponse(response);
        response.prettyPrint();
    }
    
}
