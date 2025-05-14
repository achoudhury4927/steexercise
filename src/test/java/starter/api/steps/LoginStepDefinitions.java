package starter.api.steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;
import starter.api.model.Users;
import starter.api.singleton.ApiTestContext;
import starter.api.steplibrary.httprequests.Post;
import starter.api.steplibrary.httpresponses.HttpResponseValidation;

public class LoginStepDefinitions {

    @Steps
    private Post post;

    @Steps
    private HttpResponseValidation httpResponses;

    private static final Logger logger = LoggerFactory.getLogger(LoginStepDefinitions.class);
    private ApiTestContext apiTestContext = ApiTestContext.getInstance();

    @Given("^the user to test with is ([^\"]*)$")
    public void theUserToTestWithIs(String username) {
        logger.info("Setting User to {}", username);
        apiTestContext.setUserContext(Users.valueOf(username));
    }

    @When("I request to log in")
    public void iRequestToLogIn(){
        logger.info("Sending Login Request");
        post.login();
    }

    @When("I request to log in with no credentials")
    public void iRequestToLogInWithNoCredentials(){
        logger.debug("Set Context to provide no credentials");
        apiTestContext.setNoCredentials(true);
        logger.info("Sending Login Request");
        post.login();
    }

    @And("I see the token in the response")
    public void iSeeTheTokenInTheResponse(){
        logger.info("Asserting token is present in response");
        httpResponses.verifyTokenIsPresent();
    }

    @And("I do not see the token in the response")
    public void iDoNotSeeTheTokenInTheResponse(){
        logger.info("Asserting no token in response");
        httpResponses.verifyTokenIsNotPresent();
    }

    @Given("^I am logged in with ([^\"]*)$")
    public void iAmLoggedInWithUser(String username) {
        //logger.info("Setting User to {}", username);
        apiTestContext.setUserContext(Users.valueOf(username));
        logger.info("Sending Login Request");
        post.login();

        //Thess lines would be unnecessary if each test was deployed to its own pod
        logger.debug("Resetting API Test Context");
        apiTestContext.resetContext();
    }

}
