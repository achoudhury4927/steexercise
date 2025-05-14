package starter.api.steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import net.serenitybdd.annotations.Steps;
import starter.api.singleton.ApiTestContext;
import starter.api.steplibrary.httpresponses.HttpResponseValidation;

public class HttpRequestStepDefinitions {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestStepDefinitions.class);
    private ApiTestContext apiTestContext = ApiTestContext.getInstance();

    @Steps
    private HttpResponseValidation httpResponses;

    @Then("I receive a {int} response code")
    public void iReceiveAResponseCodeOf(int responseCode){
        logger.info("Verifying Response code is {}", responseCode);
        httpResponses.verifyResponseCode(responseCode);
    }

    @And("I see the key {string} with value {string} in the response")
    public void iSeeTheKeyWithValueInTheResponse(String jsonPath, String value){
        logger.info("Verifying The Response contains {} in the response", value);
        httpResponses.verifyPathHasValue(jsonPath,value);
    }

    @Given("I am an unauthorised user")
    public void iSetTheUnauthorisedContext() {
        logger.debug("Resetting API Test Context");
        apiTestContext.resetContext();
        logger.info("Set Context to unauthorised user");
        apiTestContext.setUnauthorisedRequest(true);
    }

}
