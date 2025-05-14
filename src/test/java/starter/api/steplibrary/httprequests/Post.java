package starter.api.steplibrary.httprequests;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import starter.api.model.endpoints.LoginEndpoints;
import starter.api.model.endpoints.ProductEndpoints;
import starter.api.model.endpoints.StockEndpoints;
import starter.api.model.schemas.Product;
import starter.common.RandomUtils;

public class Post extends Request{

    private static final Logger logger = LoggerFactory.getLogger(Post.class);

    public Post get() {return this;}

    public void login(){
        JSONObject body = new JSONObject()
            .put(USERNAME,apiTestContext.getUser().getUsername())
            .put(PASSWORD,apiTestContext.getUser().getPassword()); 

        String loginEndpoint = LoginEndpoints.LOGIN.getPath();

        logger.debug("No Credentials is set to {}", apiTestContext.isNoCredentials());
        Response response = apiTestContext.isNoCredentials() ?
            DWPRequestBuilder.given().withEndpoint(loginEndpoint).withContentType(ContentType.JSON).post() : 
            DWPRequestBuilder.given().withEndpoint(loginEndpoint).withContentType(ContentType.JSON).withBody(body).post();
        response.prettyPrint();
        apiTestContext.setResponse(response);
        apiTestContext.setToken();
    }

    public void aProduct(){
        String uniqueName = RandomUtils.randomString(10);
        String productType = RandomUtils.randomElementFromList(productTypeList);
        double price = RandomUtils.randomDouble();
        int quantity = RandomUtils.randomInt();
        
        apiTestContext.setName(uniqueName);
        apiTestContext.setPrice(price);
        apiTestContext.setProductType(productType);
        apiTestContext.setQuantity(quantity);

        postProduct(uniqueName, price, productType, quantity);  
    }

    public void aProductWithDetails(double price, String type, double quantity){
        String name = apiTestContext.getName().equals("") ? RandomUtils.randomString(10) : apiTestContext.getName(); 
        apiTestContext.setName(name);
        postProduct(name, price, type, quantity);   
    }

    public void aProductWithDetails(String name, double price, String type, double quantity){
        postProduct(name, price, type, quantity);
    }

    public void postProduct(String name, double price, String type, double quantity){
        JSONObject body = new JSONObject()
            .put(NAME,name)
            .put(PRICE,price)
            .put(PRODUCT_TYPE,type);

        logger.debug("Mandatory fields only is set to {}", apiTestContext.isMandatoryFieldsOnly());    
        if(!apiTestContext.isMandatoryFieldsOnly()) { body.put(QUANTITY,quantity); }    
            
        String productEndpoint = ProductEndpoints.PRODUCTS.getPath();

        logger.debug("Unauthorised request is set to {}", apiTestContext.isUnauthorisedRequest());
        Response response = apiTestContext.isUnauthorisedRequest() ? 
            DWPRequestBuilder.given().withEndpoint(productEndpoint).withContentType(ContentType.JSON).withBody(body).post() : 
            DWPRequestBuilder.given().withEndpoint(productEndpoint).withAuthentication().withContentType(ContentType.JSON).withBody(body).post();

        apiTestContext.setResponse(response);
        apiTestContext.setProduct(response.getBody().as(Product.class));
        response.prettyPrint();  
    }

    public void postProductWithNoMandatoryDetails(){
        JSONObject body = new JSONObject()
            .put(QUANTITY,RandomUtils.randomInt());    

        String productEndpoint = ProductEndpoints.PRODUCTS.getPath();

        Response response = 
            DWPRequestBuilder.given().withEndpoint(productEndpoint).withAuthentication().withContentType(ContentType.JSON).withBody(body).post();

        apiTestContext.setResponse(response);
        response.prettyPrint();  
    }

    public void anOrder(String orderType, int quantity){
        String productId = apiTestContext.getProduct() != null ? apiTestContext.getProduct().productId : "NoId";

        JSONObject body = new JSONObject()
            .put(ORDERTYPE,orderType)
            .put(PRODUCTID,productId)
            .put(QUANTITY,quantity);

        String stockEndpoint = StockEndpoints.ORDERS.getPath();

        logger.debug("Unauthorised request is set to {}", apiTestContext.isUnauthorisedRequest());
        Response response = apiTestContext.isUnauthorisedRequest() ? 
            DWPRequestBuilder.given().withEndpoint(stockEndpoint).withContentType(ContentType.JSON).withBody(body).post() : 
            DWPRequestBuilder.given().withEndpoint(stockEndpoint).withAuthentication().withContentType(ContentType.JSON).withBody(body).post();

        apiTestContext.setResponse(response);
        response.prettyPrint();    
    }
}
