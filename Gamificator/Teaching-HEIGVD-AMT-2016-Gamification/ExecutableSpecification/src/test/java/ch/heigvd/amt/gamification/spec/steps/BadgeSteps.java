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
import ch.heigvd.gamification.api.dto.Registration;
import ch.heigvd.gamification.api.dto.BadgeDTO;
import ch.heigvd.gamification.api.dto.Token;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class BadgeSteps {

   private Token token;

   private BadgeDTO badge;

   private BadgeDTO newBadge;

   private List<BadgeDTO> badges;

   private int applicationsCounter = 1;

   private int badgesCounter = 1;

   private String randomBadgeName;

   private Long badgeId;

   private String imageURI = "www.image.com";

   private String description = "Some description";

   private Credentials credentials;

   private int statusCode;

   private final DefaultApi api = new DefaultApi();

   final static String DUMMY_PASSWORD = "dummyPassword";

   @Given("^a token for a new gamified application$")
   public void a_token_for_a_new_gamified_application() throws Throwable {
      String randomApplicationName = "app-name-" + (applicationsCounter++) + '-' + System.currentTimeMillis();
      Registration applicationRegistration = new Registration();
      applicationRegistration.setApplicationName(randomApplicationName);
      applicationRegistration.setPassword(DUMMY_PASSWORD);
      api.applicationsPost(applicationRegistration);

      Credentials credentials = new Credentials();
      credentials.setApplicationName(randomApplicationName);
      credentials.setPassword(DUMMY_PASSWORD);

      token = api.authenticateApplicationAndGetToken(credentials);
   }

   @Given("^I have a badge payload$")
   public void i_have_a_badge_payload() throws Throwable {
      randomBadgeName = "badge-name-" + (badgesCounter++) + '-' + System.currentTimeMillis();
      badge = new BadgeDTO();
      badge.setImageURI(imageURI);
      badge.setName(randomBadgeName);
      badge.setDescription(description);

   }

   @When("^I POST it to the /badges endpoint$")
   public void i_POST_it_to_the_badges_endpoint() throws Throwable {
      try {
         ApiResponse response = api.badgesPostWithHttpInfo(badge, token.getApplicationName());
         Map<String, List<String>> headers = response.getHeaders();
         String locationHeader = headers.get("Location").get(0);
         String[] locationParts = locationHeader.split("\\/");
         String badgeIdString = locationParts[locationParts.length - 1];
         badge.setId(Long.parseLong(badgeIdString));

         statusCode = response.getStatusCode();
      } catch (ApiException e) {
         statusCode = e.getCode();
      }
   }

   @Then("^I receive (\\d+) status code$")
   public void i_receive_status_code(int arg1) throws Throwable {
      assertEquals(arg1, statusCode);
   }

   @When("^I ask for a list of badges with a GET on the /badges endpoint$")
   public void i_ask_for_a_list_of_badges_with_a_GET_on_the_badges_endpoint() throws Throwable {
      if (token.getApplicationName() != "A bad token") {
         badges = api.badgesGet(token.getApplicationName());
      } else {
         try {
            ApiResponse<List<BadgeDTO>> response = api.badgesGetWithHttpInfo(token.getApplicationName());
            statusCode = response.getStatusCode();
         } catch (ApiException e) {
            statusCode = e.getCode();
         }
      }
   }

   @Then("^I see my badge in the list$")
   public void i_see_my_badge_in_the_list() throws Throwable {
      assertThat(badges).extracting("name").contains(badge.getName());
   }

   @When("^badge name already exists$")
   public void badge_name_already_exists() throws Throwable {
      BadgeDTO someBadge = new BadgeDTO();
      someBadge.setName(badge.getName());
      someBadge.setDescription(description);
      someBadge.setImageURI(imageURI);

      try {
         ApiResponse response = api.badgesPostWithHttpInfo(someBadge, token.getApplicationName());
         statusCode = response.getStatusCode();
      } catch (ApiException e) {
         statusCode = e.getCode();
      }
   }

   @Given("^I have the id of that badge$")
   public void i_have_the_id_of_that_badge() throws Throwable {
      badgeId = badge.getId();
   }

   @When("^I send a DELETE on the /badges endpoint$")
   public void i_send_a_DELETE_on_the_badges_endpoint() throws Throwable {
      try {
         ApiResponse response = api.badgesBadgeIdDeleteWithHttpInfo(token.getApplicationName(), badgeId);
         statusCode = response.getStatusCode();
      } catch (ApiException e) {
         statusCode = e.getCode();
      }
   }

   @Given("^I have a new badge payload$")
   public void i_have_a_new_badge_payload() throws Throwable {
      newBadge = new BadgeDTO();
      newBadge.setName("random-newBadge-" + (badgesCounter++) + "-" + System.currentTimeMillis());
      newBadge.setImageURI("www.new.com");
      newBadge.setDescription("new description");
   }

   @When("^I send a PUT on the /badges endpoint$")
   public void i_send_a_PUT_on_the_badges_endpoint() throws Throwable {
      try {
         ApiResponse response = api.badgesBadgeIdPutWithHttpInfo(token.getApplicationName(), badgeId, newBadge);
         statusCode = response.getStatusCode();
      } catch (ApiException e) {
         statusCode = e.getCode();
      }
   }

   @Given("^I have a wrong id of badge$")
   public void i_have_a_wrong_id_of_badge() throws Throwable {
      badgeId = 999999999999999999L;
   }

   @When("^I send a GET on the /badges endpoint$")
   public void i_send_a_GET_on_the_badges_endpoint() throws Throwable {
      try {
         ApiResponse<BadgeDTO> response = api.badgesBadgeIdGetWithHttpInfo(token.getApplicationName(), badgeId);
         statusCode = response.getStatusCode();
      } catch (ApiException e) {
         statusCode = e.getCode();
      }
   }

   @When("^I have wrong token$")
   public void i_have_wrong_token() throws Throwable {
      token.setApplicationName("A bad token");
   }
}
