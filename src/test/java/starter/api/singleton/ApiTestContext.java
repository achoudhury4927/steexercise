package starter.api.singleton;

import java.util.Date;

import io.restassured.response.Response;
import lombok.*;
import starter.api.model.Users;
import starter.api.model.schemas.Product;

/*
 * This class can be used to save data between test steps to be used later such as user tokens
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class ApiTestContext {

    private static ApiTestContext singleton;
    private Users user;
    private Response response;
    private String token;
    private Product product;
    private boolean unauthorisedRequest;
    public String productId;
    public String name = "";
    public double price;
    public String productType;
    public double quantity;
    public Date createdAt;
    private boolean mandatoryFieldsOnly;
    private boolean noCredentials;
    private boolean noDetailsOnUpdate;

    public static ApiTestContext getInstance() {
        if (singleton == null) {
            singleton = new ApiTestContext();
        }
        return singleton;
    }

    public void setUserContext(Users user){
        setUser(user);
    }

    public void setToken(){
        this.token = response.path("token");
    }

    /*
     * This method would be removed if each test was deployed to its own pod so context wouldnt be shared between tests
     */
    public void resetContext(){
        response = null;
        product = null;
        unauthorisedRequest = false;
        productId = null;
        name = "";
        price = Double.NaN;
        productType = null;
        quantity = Double.NaN;
        createdAt = null;
        mandatoryFieldsOnly = false;
        noCredentials = false;
        noDetailsOnUpdate = false;
    }
}
