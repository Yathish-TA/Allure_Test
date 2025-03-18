package mobileapp.stepdefinitions;

import dev.failsafe.internal.util.Assert;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import mobileapp.utils.MobileAppBaseClass;
import mobileapp.utils.MobileAppConfig;
import mobileweb.utils.MobileWebBaseClass;
import org.testng.asserts.SoftAssert;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CMA_Bulk_Quote_TestSteps extends MobileAppBaseClass {

    private final MobileAppConfig config = new MobileAppConfig();
    private final SoftAssert softAssert = new SoftAssert();
    private static String workbook;
    private static String worksheet;
    MobileWebBaseClass base;


    /**
     * Sets the workbook and worksheet to fetch test data from.
     *
     * @param workbookName  The name of the workbook.
     * @param worksheetName The name of the worksheet.
     */
    @Given("I fetch test data from {string} in the {string} sheet")
    public void iFetchTestDataFromInTheSheet(String workbookName, String worksheetName) {
        workbook = workbookName;
        worksheet = worksheetName;
    }


    /**
     * Logs in with valid credentials.
     *
     * @throws Exception If login fails.
     */

    @Given("User has logged in with valid credentials")
    public void iAmLoggedInWithValidCredentials() throws Exception {
        String username = config.getProperty("Username");
        String password = config.getProperty("Password");
        cmaBulKQuotePage.login(username, password);
    }


    /**
     * Verifies that the user is on the Home Page.
     */
    @And("User is on the Home Page")
    public void iAmOnTheHomePage() {
        cmaBulKQuotePage.isHomePageDisplayed();
    }

    /**
     * Searches for a product SKU.
     *
     * @param skuId The SKU ID to search for.
     * @throws InterruptedException If the search operation is interrupted.
     */
    @Given("User search for SKU {string}")
    public void iSearchForSKU(String skuId) throws InterruptedException {
        cmaBulKQuotePage.enterSKUValueAndSearch(skuId);
    }

    /**
     * Verifies that the user is on the Home Page.
     */
    @Then("User should see the Product Detail Page for the SKU")
    public void iShouldSeeTheProductDetailPageForTheSKU() {
        cmaBulKQuotePage.verifySKUIdDisplayed();
    }
    @And("User specifies a quantity of {string} to meet the Bulk Quote Request form requirements")
    public void iSpecifyAQuantityOfToMeetTheBulkQuoteRequestFormRequirements(String quantity) {
        cmaBulKQuotePage.enterValidQuantity(quantity);
    }
    @And("User click on the Request a Quote link")
    public void iClickOnTheRequestAQuoteLink() {
        cmaBulKQuotePage.clickRequestQuoteLink();
    }

    /**
     * Verifies that the user is on the Bulk Quote Page.
     */
    @Then("User should see the Bulk Quote Request form page")
    public void iShouldSeeTheBulkQuoteRequestFormPage() {
        cmaBulKQuotePage.verifyQuoteRequestLabelDisplayed();
    }

    /**
     * Fills out the Bulk Quote Request form with the given details.
     *
     * @param dataTable The details to enter in the form.
     */
    @And("User fills out the Bulk Quote Request form with the following details:")
    public void iFillOutTheBulkQuoteRequestFormWithTheFollowingDetails(DataTable dataTable) {
        Map<String, String> formDetails = dataTable.asLists(String.class).stream()
                .skip(2) // Skip the header row
                .filter(row -> row.size() == 2)
                .collect(Collectors.toMap(row -> row.get(0).trim(), row -> row.get(1).trim(), (existing, replacement) -> existing, LinkedHashMap::new));

        formDetails.forEach((field, value) -> cmaBulKQuotePage.enterFormField(field, value.isEmpty() ? "" : value));
    }

    /**
     * Chooses a delivery option from the available options.
     *
     * @param deliveryOption The delivery option to select.
     */
    @And("User should choose {string} as the Delivery option")
    public void iChooseAsTheDeliveryOption(String deliveryOption) {
        cmaBulKQuotePage.selectDeliveryOption(deliveryOption);
    }

    /**
     * Provides the Preferred Delivery Address based on the given details.
     *
     * @param dataTable The address details to enter.
     */
    @And("User provides the Preferred Delivery Address as:")
    public void iProvideThePreferredDeliveryAddressAs(DataTable dataTable) {
        // Convert the DataTable into a list of maps
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        // Get the address details from the first row (index 1) of the DataTable
        Map<String, String> addressDetails = data.get(1);
        // Enter each address detail in the form
        for (Map.Entry<String, String> entry : addressDetails.entrySet()) {
            cmaBulKQuotePage.enterFormField(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Completes the address verification by selecting the suggested option.
     */
    @And("User verifies the address by selecting the suggested option")
    public void iVerifyTheAddressBySelectingTheSuggestedOption() {
        cmaBulKQuotePage.completeAddressVerification();
    }


    /**
     * Enters additional information into the Additional Information field.
     *
     * @param additionalInformation The additional information to enter.
     */
    @And("User enters {string} in the Additional Information field")
    public void iEnterInTheAdditionalInformationField(String additionalInformation) {
        cmaBulKQuotePage.enterAdditionalInformation(additionalInformation);
    }

    /**
     * Clicks the Submit button to submit the form.
     */
    @When("User clicks the Submit button")
    public void iClickTheSubmitButton() {
        cmaBulKQuotePage.clickSubmitButton();
    }

    /**
     * Verifies that the loader with the specified description is displayed.
     *
     * @param loaderDescription The expected loader description.
     */
    @And("User should see the {string} loader")
    public void iShouldSeeTheLoader(String loaderDescription) {
        softAssert.assertEquals(cmaBulKQuotePage.getLoaderDescription(), loaderDescription, "Loader Description");
    }

    /**
     * Verifies that the loader disappears after form submission.
     */
    @And("the loader should disappear after submission")
    public void theLoaderShouldDisappearAfterSubmission() {
        softAssert.assertFalse(cmaBulKQuotePage.isLoaderDescriptionDisplayed());
    }

    /**
     * Verifies that a Submission Successful popup appears.
     */
    @And("a Submission Successful popup should appear")
    public void aSubmissionSuccessfulPopupShouldAppear() {
        Assert.isTrue(cmaBulKQuotePage.isSubmissionSuccessfulPopUpDisplayed(), "Submission Successful Pop Up");
    }

}
