package starter.api.steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;
import starter.api.steplibrary.httprequests.Get;
import starter.api.steplibrary.httprequests.Post;
import starter.api.steplibrary.httpresponses.HttpResponseValidation;

public class StockStepDefinitions {

    @Steps
    private Get get;

    @Steps
    private Post post;

    @Steps
    private HttpResponseValidation httpResponses;

    private static final Logger logger = LoggerFactory.getLogger(StockStepDefinitions.class);

    @When("I create a {string} order for {int} products")
    @When("^I create a ([^\\\"]*) order for ([^\\\"]*) products$")
    public void iCreateANewOrderForProducts(String orderType, int quantity){
        logger.info("Creating a {} order with quantity {}", orderType, quantity);
        post.anOrder(orderType, quantity);
    }

    @When("I request the stock information for the product")
    public void iRequestTheStockInformation(){
        logger.info("Sending Get Order request");
        get.getOrder();
    }

    @And("I verify the stock information")
    public void iVerifyTheStockInformation(){
        logger.info("Verifying response body contains all expected order details");
        httpResponses.verifyResponseContainsExpectedOrderResponsePaths();
    }

}
