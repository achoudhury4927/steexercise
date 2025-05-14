package starter.api.steplibrary.httprequests;

import java.util.Map;

import org.json.JSONObject;

import io.restassured.specification.RequestSpecification;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;


/*
 * Class to simplify building requests
 */
public class DWPRequestBuilder extends Request{

    private static final String AUTHORIZATION = "Authorization";

    private RequestSpecification requestSpecification;

    private DWPRequestBuilder(){
        this.requestSpecification = SerenityRest.given().log().uri();
        this.requestSpecification.baseUri("https://apiforshopsinventorymanagementsystem.onrender.com/");
    }

    public static DWPRequestBuilder given() {return new DWPRequestBuilder();}

    public DWPRequestBuilder withEndpoint(String endpoint){
        requestSpecification.basePath(endpoint);
        return this;
    }

    public DWPRequestBuilder withAuthentication(){
        requestSpecification.header(AUTHORIZATION, "Bearer "+apiTestContext.getToken());
        return this;
    }

    public DWPRequestBuilder withQueryParams(Map<String, ?> paramsMap) {
        requestSpecification.queryParams(paramsMap);
        return this;
    }

    public DWPRequestBuilder withContentType(String contentType){
        requestSpecification.contentType(contentType);
        return this;
    }

    public DWPRequestBuilder withContentType(ContentType contentType){
        requestSpecification.contentType(contentType);
        return this;
    }

    public DWPRequestBuilder withBody(String body){
        requestSpecification.body(body);
        return this;
    }

    public DWPRequestBuilder withBody(JSONObject jsonBody){
        return withBody(jsonBody.toString());
    }

    public Response post(){
        return requestSpecification.post();
    }

    public Response get(){
        return requestSpecification.get();
    }

    public Response delete(){
        return requestSpecification.delete();
    }

    public Response put(){
        return requestSpecification.put();
    }
}
