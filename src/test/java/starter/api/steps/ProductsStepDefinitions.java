package starter.api.steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;

import starter.api.singleton.ApiTestContext;
import starter.api.steplibrary.httprequests.Delete;
import starter.api.steplibrary.httprequests.Get;
import starter.api.steplibrary.httprequests.Post;
import starter.api.steplibrary.httprequests.Put;
import starter.api.steplibrary.httpresponses.HttpResponseValidation;

public class ProductsStepDefinitions {

    @Steps
    private Get get;

    @Steps
    private Post post;

    @Steps
    private Delete delete;

    @Steps
    private Put put;

    @Steps
    private HttpResponseValidation httpResponses;

    private static final Logger logger = LoggerFactory.getLogger(ProductsStepDefinitions.class);
    private ApiTestContext apiTestContext = ApiTestContext.getInstance();

    @When("I request a list of products")
    public void iRequestAListOfProducts(){
        logger.info("Sending Get Products Request");
        get.getProducts();
    }

    @When("I request a product")
    public void iRequestAProduct(){
        logger.info("Sending Get Single Product Request");
        get.getProduct();
    }

    @And("I receive a list of products in the response")
    public void iReceiveAListOfProductsInTheResponse(){
        logger.info("Verifying a list of products is returned");
        httpResponses.verifyResponseContainsListOfProducts();
    }

    @And("I verify the details of a product")
    public void iVerifyTheDetailsOfAProduct(){
        logger.info("Verifying the response contains products paths");
        httpResponses.verifyResponseContainsExpectedProductResponsePaths();
    }

    @When("^I create a new product with the details ([^\"]*) and ([^\"]*) and ([^\"]*)$")
    public void iCreateANewProductWithTheDetails(double price, String type, double quantity){
        logger.info("Creating a product with a randomly generated name and provided details of price {}, product type {} and quantity {}", price, type, quantity);
        post.aProductWithDetails(price, type, quantity);
    }

    @When("I try to create a new product with no mandatory details")
    public void iTryToCreateANewProductWithNoMandatoryDetails(){
        logger.info("Creating a product with no name, price or type");
        post.postProductWithNoMandatoryDetails();
    }

    /*
     * This specific step is for tests where we don't expect a file to be created such as in:
     * - Unauthorized Error
     * - Invalid Token
     * A 400 error would be thrown in such a scenario allowing us to debug the product returned in the response
     */
    @When("^I create a new product with all the details ([^\"]*) and ([^\"]*) and ([^\"]*) and ([^\"]*)$")
    public void iCreateANewProductWithAllDetails(String name, double price, String type, double quantity){
        logger.info("Creating a product with provided details of name {}, price {}, product type {} and quantity {}", name, price, type, quantity);
        post.aProductWithDetails(name, price, type, quantity);
    }

    @When("I create a new product")
    public void iCreateANewProductRandomDetails(){
        logger.info("Creating a product with a randomly generated name, price, product type and quantity");
        post.aProduct();
    }

    
    @When("I create a new product with mandatory details only")
    public void iCreateANewProductRandomDetailsAndNoPrice(){
        logger.debug("Set Context to mandatory product fields only");
        apiTestContext.setMandatoryFieldsOnly(true);
        logger.info("Creating a product with a randomly generated name, price, product type but no quantity");
        post.aProduct();
    }

    @When("^I verify a new product was created with the details ([^\"]*) and ([^\"]*) and ([^\"]*) and ([^\"]*)$")
    public void iVerifyANewProductWasCreatedWithTheDetails(String name, double price, String type, int quantity){
        logger.info("Verifying response returned contains provided details of name {}, price {}, product type {} and quantity {}", name, price, type, quantity);
        httpResponses.verifyNewProductCreatedWithProvidedDetails(name, price, type, quantity);
    }

    @When("I verify a new product was created")
    public void iVerifyANewProductWasCreated(){
        logger.info("Verifying response returned and saved as product contains generated details");
        httpResponses.verifyNewProductCreatedWithDetailsFromContext();
    }

    @When("I verify the response contains the expected product")
    public void iVerifyTheResponseContainsTheExpectedProduct(){
        logger.info("Verifying response body contains details from product created in previous step");
        httpResponses.verifyProductDetailsInResponseMatchesProduct();

    }

    @When("I delete the product")
    public void iDeleteTheProduct(){
        logger.info("Sending Delete request");
        delete.deleteProduct();
    }

    @When("I update the product with all the details {string} and {double} and {string} and {double}")
    @When("^I update the product with all the details ([^\"]*) and ([^\"]*) and ([^\"]*) and ([^\"]*)$")
    public void iUpdateTheProductWithAllTheDetails(String name, double price, String type, double quantity){
        logger.info("Sending Update product request with provided details of name {}, price {}, product type {} and quantity {}", name, price, type, quantity);
        put.updateProductWithDetails(name, price, type, quantity);
    }

    @When("I update the product with the details {double} and {string} and {double}")
    @When("^I update the product with the details ([^\\\"]*) and ([^\\\"]*) and ([^\\\"]*)$")
    public void iUpdateTheProductWithTheDetails(double price, String type, double quantity){
        logger.info("Sending Update product request with product name from context and provided details of price {}, product type {} and quantity {}",  price, type, quantity);
        put.updateProductWithDetails(price, type, quantity);
    }

    @When("I update the product")
    public void iUpdateTheProduct(){
        logger.info("Sending Update request with randomly generated name, price, product type and quantity");
        put.updateProduct();
    }

    @When("I update the product with no details provided")
    public void iUpdateTheProductWithNoDetailsProvided(){
        logger.debug("Set context to provide no details for update request");
        apiTestContext.setNoDetailsOnUpdate(true);
        logger.info("Sending update request with previously set product details");
        put.updateProductWithNoNewDetails();
    }

    @And("I verify the updated product is as expected")
    @When("I verify the product was updated")
    public void iVerifyTheProductWasUpdated(){
        logger.info("Verifying response body matches details generated during update request");
        httpResponses.verifyUpdatedProductDetailsInResponseMatches();
    }
}
