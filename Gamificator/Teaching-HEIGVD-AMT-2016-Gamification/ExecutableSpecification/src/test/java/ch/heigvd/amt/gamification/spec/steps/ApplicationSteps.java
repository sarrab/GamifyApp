package ch.heigvd.amt.gamification.spec.steps;

import ch.heigvd.gamification.ApiException;
import ch.heigvd.gamification.ApiResponse;
import ch.heigvd.gamification.api.DefaultApi;
import ch.heigvd.gamification.api.dto.ApplicationDTO;
import ch.heigvd.gamification.api.dto.Credentials;
import ch.heigvd.gamification.api.dto.Registration;
import ch.heigvd.gamification.api.dto.Token;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class ApplicationSteps {

   private Registration registration;

   private Registration newRegistration;

   private Credentials credentials;

   private Token token;

   private int applicationsCounter = 1;

   private final DefaultApi api = new DefaultApi();

   private int statusCode;

   private String applicationName;

   private String randomApplicationName;

   private List<ApplicationDTO> applications;

   final static String DUMMY_PASSWORD = "dummyPassword";

   @Given("^I have an application payload$")
   public void i_have_an_application_payload() throws Throwable {
      registration = new Registration();
      randomApplicationName = "random-app-" + (applicationsCounter++) + "-" + System.currentTimeMillis();
      registration.setApplicationName(randomApplicationName);
      registration.setPassword(DUMMY_PASSWORD);
   }

   @When("^I POST it to the /applications endpoint$")
   public void i_POST_it_to_the_applications_endpoint() throws Throwable {
      try {
         ApiResponse response = api.applicationsPostWithHttpInfo(registration);
         statusCode = response.getStatusCode();
      } catch (ApiException e) {
         statusCode = e.getCode();
      }
   }

   @Then("^I receive a (\\d+) status code$")
   public void i_receive_a_status_code(int arg1) throws Throwable {
      assertEquals(arg1, statusCode);
   }

   @When("^I POST it to the /authentications endpoint$")
   public void i_POST_it_to_the_authentications_endpoint() throws Throwable {
      credentials = new Credentials();
      credentials.setApplicationName(registration.getApplicationName());
      credentials.setPassword(DUMMY_PASSWORD);
      try {
         ApiResponse<Token> response = api.authenticateApplicationAndGetTokenWithHttpInfo(credentials);
         statusCode = response.getStatusCode();
      } catch (ApiException e) {
         statusCode = e.getCode();
      }
   }

   @Then("^I receive a token$")
   public void i_receive_a_token() throws Throwable {
      token = api.authenticateApplicationAndGetToken(credentials);
   }

   @When("^I ask for a list of registered apps with a GET on the /applications endpoint$")
   public void i_ask_for_a_list_of_registered_apps_with_a_GET_on_the_applications_endpoint() throws Throwable {
      applications = api.applicationsGet();
   }

   @Then("^I see my app in the list$")
   public void i_see_my_app_in_the_list() throws Throwable {
      assertThat(applications).extracting("applicationName").contains(registration.getApplicationName());
   }

   @Given("^I have a bad application payload$")
   public void i_have_a_bad_application_payload() throws Throwable {
      registration = new Registration();
      registration.setApplicationName("bad name");
      registration.setPassword("bad pwd");
   }

   @When("^application name already exists$")
   public void application_name_already_exists() throws Throwable {
      Registration newRegistration = new Registration();
      newRegistration.setApplicationName(registration.getApplicationName());
      newRegistration.setPassword(DUMMY_PASSWORD);
      try {
         ApiResponse response = api.applicationsPostWithHttpInfo(newRegistration);
         statusCode = response.getStatusCode();
      } catch (ApiException e) {
         statusCode = e.getCode();
      }
   }

   @Given("^I have the name of that application$")
   public void i_have_the_name_of_that_application() throws Throwable {
      applicationName = registration.getApplicationName();
   }

   @When("^I send a DELETE on the /applications endpoint$")
   public void i_send_a_DELETE_on_the_applications_endpoint() throws Throwable {
      try {
         ApiResponse response = api.applicationsApplicationNameDeleteWithHttpInfo(applicationName);
         statusCode = response.getStatusCode();
      } catch (ApiException e) {
         statusCode = e.getCode();
      }
   }

   @Given("^I have a new application payload$")
   public void i_have_a_new_application_payload() throws Throwable {
      newRegistration = new Registration();
      newRegistration.setApplicationName("random-newApp-" + (applicationsCounter++) + "-" + System.currentTimeMillis());
      newRegistration.setPassword("newPassword");
   }

   @When("^I send a PUT on the /applications endpoint$")
   public void i_send_a_PUT_on_the_applications_endpoint() throws Throwable {
      try {
         ApiResponse response = api.applicationsApplicationNamePutWithHttpInfo(applicationName, newRegistration);
         statusCode = response.getStatusCode();
      } catch (ApiException e) {
         statusCode = e.getCode();
      }
   }

   @Given("^I have the name of an unregistred application$")
   public void i_have_the_name_of_an_unregistred_application() throws Throwable {
      applicationName = "unregistredApplicationName";
   }

   @When("^I send a GET on the /applications endpoint$")
   public void i_send_a_GET_on_the_applications_endpoint() throws Throwable {
      try {
         ApiResponse<ApplicationDTO> response = api.applicationsApplicationNameGetWithHttpInfo("applicationName");
         statusCode = response.getStatusCode();
      } catch (ApiException e) {
         statusCode = e.getCode();
      }
   }
}
