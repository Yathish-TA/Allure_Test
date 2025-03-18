package web.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import web.resources.testrunner.WebTestRun;
import web.utils.BaseClass;
import webutils.WebExcelDatafetcher;

import java.util.HashMap;
import java.util.Map;

public class DemoTest extends BaseClass {

    private BaseClass base;

    public DemoTest() {
        super(WebTestRun.getBaseInstance());
        this.base=WebTestRun.getBaseInstance();
    }

    @Given("Login with {string} and {string}")
    public void loginWithValidCredentials(String username, String password) {
       getDemoSite().login(username,password);
    }

    @When("Verify able to login and perform logout")
    public void performLogoutAction() {
        // Browser setup already handled in BaseClass
    }


}
