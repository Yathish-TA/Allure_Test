package api.stepdefinitions;

import api.utils.TestBaseApi;
import apicoreutils.LoadProp;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import qtestmanager.Hooks;

import java.util.Map;


public class ApiTestSteps extends TestBaseApi {

    public ApiTestSteps(){
        super();
    }

    protected String Workbook;
    protected String Worksheet;

    @After
    public void afterScenario(Scenario scenario){
        Hooks.collectScenarioStatus(scenario);
    }

    @Given("Assigning Payload source {string} and {string}")
    public void loadpayloadpath(String workbook, String worksheet) {
        Workbook = workbook;
        Worksheet = worksheet;
    }


    @And("Load Data into Methods")
    public void loadDataIntoMethods() {
        datavalues = excelDatafetcher.getExcelData(Workbook, Worksheet);
    }


    @When("Make API call to run {string}")
    public void makeApiCallToRun(String dataref) throws Exception {
        boolean flag = false;
        try {
            for (Map<String, Object> map : datavalues) {
                if (map.containsKey("DataBinding") && map.get("DataBinding").equals(dataref)) {
                    System.out.println("Test_DataBinding: "+map);
                    // Make the API call with the current map
                    responseJsonList.add(apiCoreModel.makeApiCall(map, dataref));
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                throw new RuntimeException("No more data sets available to run.");
            }
        } catch (Exception e) {
            throw new RuntimeException("No more data sets available to run - " + e);
        }
    }

    @Then("Validate the json response with the expected output {string}")
    public void validateResponse(String dataref) {
        boolean flag = false;
        try {
            // Iterate through both lists simultaneously
            for (Map<String, Object> map : datavalues) {
                if (map.containsKey("DataBinding") && map.get("DataBinding").equals(dataref)) {
                    flag = true;
                    for (JSONObject res : responseJsonList) {
                        if (res.has("DataBinding") && dataref.equals(res.getString("DataBinding")) && flag == true) {
                            System.out.println("The key 'DataBinding' exists and has the value " + dataref);
                            apiCoreModel.responseValidation(map, res);
                            break;
                        }
                        //else {flag=false; System.out.println("The key 'Databinding' does not exist or does not have the value 'TC_001'.");}
                    }
                }
                if (flag) {
                    break;
                }
            }
            if (!flag) {
                throw new RuntimeException("No more data sets available to run.");
            }
        } catch (Exception e) {
            throw new RuntimeException("No more data sets available to run." + e);
        }
    }

    @Then("Establish the DB connection {string}")
    public void establish_the_db_connection(String app_db) {
        db.setDbConnection(app_db);
    }

    @Then("Validate the DB results for query {string} against {string}")
    public void validate_the_db_results_for_query_against(String queryKey, String expected) {
        String Updated_Query = db.replacePlaceholdersWithValues(LoadProp.prop.getProperty(queryKey));
        db.validateTheDbResults(Updated_Query,expected);
    }

}
