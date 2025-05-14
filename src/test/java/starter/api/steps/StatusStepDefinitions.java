package starter.api.steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;
import starter.api.steplibrary.httprequests.Get;

public class StatusStepDefinitions {

    @Steps
    private Get get;

    private static final Logger logger = LoggerFactory.getLogger(StatusStepDefinitions.class);

    @When("I check the status of the API")
    public void iCheckTheStatusOfTheAPI(){
        logger.info("Sending Get Status Request");
        get.healthcheck();
    }

}
