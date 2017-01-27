/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.gamification.spec.steps;

import ch.heigvd.gamification.ApiException;
import ch.heigvd.gamification.ApiResponse;
import ch.heigvd.gamification.api.DefaultApi;
import ch.heigvd.gamification.api.dto.Credentials;
import ch.heigvd.gamification.api.dto.PointScaleDTO;
import ch.heigvd.gamification.api.dto.Registration;
import ch.heigvd.gamification.api.dto.Token;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author user
 */
public class PointScaleSteps {

   private Token token;

   private PointScaleDTO pointScale;

   private PointScaleDTO newPointScale;

   private int applicationsCounter = 1;

   private int pointScalesCounter = 1;

   private String randomPointScaleName;

   private Long pointScaleId;

   private int nbrDePoints = 0;

   private String description = "Some description";

   private Credentials credentials;

   private int statusCode;

   private List<PointScaleDTO> pointScales;

   private final DefaultApi api = new DefaultApi();

   final static String DUMMY_PASSWORD = "dummyPassword";

   @Given("^I have a token for a new gamified application$")
   public void i_have_a_token_for_a_new_gamified_application() throws Throwable {
      String randomApplicationName = "App-name-" + (applicationsCounter++) + '-' + System.currentTimeMillis();
      Registration applicationRegistration = new Registration();
      applicationRegistration.setApplicationName(randomApplicationName);
      applicationRegistration.setPassword(DUMMY_PASSWORD);
      api.applicationsPost(applicationRegistration);

      Credentials credentials = new Credentials();
      credentials.setApplicationName(randomApplicationName);
      credentials.setPassword(DUMMY_PASSWORD);

      token = api.authenticateApplicationAndGetToken(credentials);
   }

   @Given("^I have a pointScale payload$")
   public void i_have_a_pointScale_payload() throws Throwable {
      randomPointScaleName = "pointScale-name-" + (pointScalesCounter++) + '-' + System.currentTimeMillis();
      pointScale = new PointScaleDTO();
      pointScale.setNbrDePoints(nbrDePoints);
      pointScale.setName(randomPointScaleName);
      pointScale.setDescription(description);
   }

   @When("^I POST it to the /pointScales endpoint$")
   public void i_POST_it_to_the_pointScales_endpoint() throws Throwable {
      try {
         ApiResponse response = api.pointScalesPostWithHttpInfo(token.getApplicationName(), pointScale);

         Map<String, List<String>> headers = response.getHeaders();
         String locationHeader = headers.get("Location").get(0);
         String[] locationParts = locationHeader.split("\\/");
         String pointScaleIdString = locationParts[locationParts.length - 1];
         pointScale.setId(Long.parseLong(pointScaleIdString));

         statusCode = response.getStatusCode();

      } catch (ApiException e) {
         statusCode = e.getCode();
      }
   }

   @Then("^I receive (\\d+) code status$")
   public void i_receive_code_status(int arg1) throws Throwable {
      assertEquals(arg1, statusCode);
   }

   @When("^I ask for a list of pointScales with a GET on the /pointScales endpoint$")
   public void i_ask_for_a_list_of_pointScales_with_a_GET_on_the_pointScales_endpoint() throws Throwable {
      if (token.getApplicationName() != "A bad token") {
         pointScales = api.pointScalesGet(token.getApplicationName());
      } else {
         try {
            ApiResponse<List<PointScaleDTO>> response = api.pointScalesGetWithHttpInfo(token.getApplicationName());
            statusCode = response.getStatusCode();
         } catch (ApiException e) {
            statusCode = e.getCode();
         }
      }
   }

   @When("^I see my pointScale in the list$")
   public void i_see_my_pointScale_in_the_list() throws Throwable {
      assertThat(pointScales).extracting("name").contains(pointScale.getName());
   }

   @When("^pointScale name already exists$")
   public void pointscale_name_already_exists() throws Throwable {
      PointScaleDTO somePointScale = new PointScaleDTO();
      somePointScale.setName(pointScale.getName());
      somePointScale.setDescription(description);
      somePointScale.setNbrDePoints(10);

      try {
         ApiResponse response = api.pointScalesPostWithHttpInfo(token.getApplicationName(), somePointScale);
         statusCode = response.getStatusCode();
      } catch (ApiException e) {
         statusCode = e.getCode();
      }
   }

   @When("^I have the id of that pointScale$")
   public void i_have_the_id_of_that_pointScale() throws Throwable {
      pointScaleId = pointScale.getId();
   }

   @When("^I send a DELETE on the /pointScales endpoint$")
   public void i_send_a_DELETE_on_the_pointScales_endpoint() throws Throwable {
      try {
         ApiResponse response = api.pointScalesPointScaleIdDeleteWithHttpInfo(token.getApplicationName(), pointScaleId);
         statusCode = response.getStatusCode();
      } catch (ApiException e) {
         statusCode = e.getCode();
      }
   }

   @When("^I have a new pointScale payload$")
   public void i_have_a_new_pointScale_payload() throws Throwable {
      newPointScale = new PointScaleDTO();
      newPointScale.setName("random-newPointScale-" + (pointScalesCounter++) + "-" + System.currentTimeMillis());
      newPointScale.setNbrDePoints(20);
      newPointScale.setDescription("new description");
   }

   @When("^I send a PUT on the /pointScales endpoint$")
   public void i_send_a_PUT_on_the_pointScales_endpoint() throws Throwable {
      try {
         ApiResponse response = api.pointScalesPointScaleIdPutWithHttpInfo(token.getApplicationName(), pointScaleId, newPointScale);
         statusCode = response.getStatusCode();
      } catch (ApiException e) {
         statusCode = e.getCode();
      }
   }

   @When("^I have a wrong id of pointScale$")
   public void i_have_a_wrong_id_of_pointScale() throws Throwable {
      pointScaleId = 999999999999999999L;
   }

   @When("^I send a GET on the /pointScales endpoint$")
   public void i_send_a_GET_on_the_pointScales_endpoint() throws Throwable {
      try {
         ApiResponse<PointScaleDTO> response = api.pointScalesPointScaleIdGetWithHttpInfo(token.getApplicationName(), pointScaleId);
         statusCode = response.getStatusCode();
      } catch (ApiException e) {
         statusCode = e.getCode();
      }
   }

   @When("^I have a wrong token$")
   public void i_have_a_wrong_token() throws Throwable {
      token.setApplicationName("A bad token");
   }

}
