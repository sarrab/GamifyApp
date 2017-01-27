/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt.gamification.spec.steps;

import ch.heigvd.gamification.ApiException;
import ch.heigvd.gamification.ApiResponse;
import ch.heigvd.gamification.api.DefaultApi;
import ch.heigvd.gamification.api.dto.BadgeDTO;
import ch.heigvd.gamification.api.dto.Credentials;
import ch.heigvd.gamification.api.dto.EventDTO;
import ch.heigvd.gamification.api.dto.PointScaleDTO;
import ch.heigvd.gamification.api.dto.Registration;
import ch.heigvd.gamification.api.dto.RuleDTO;
import ch.heigvd.gamification.api.dto.Token;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.io.Console;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import org.joda.time.DateTime;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author user
 */
public class SimpleEventProcessingSteps {

   private int applicationsCounter = 1;

   private int badgesCounter = 1;

   private String randomBadgeName;

   private String imageURI = "www.image.com";

   private String randomApplicationName;

   private String randomPointScaleName;

   private PointScaleDTO pointScale;

   private int nbrDePoints = 0;

   private String description = "Some description";

   private int pointScalesCounter = 1;

   private final DefaultApi api = new DefaultApi();

   private Token token;

   private Long actionIdBadge = 5L;

   private Long actionIdPointScale = 9L;

   private BadgeDTO badge;

   final static String DUMMY_PASSWORD = "dummyPassword";

   private int statusCode;

   private RuleDTO ruleBadge;

   private RuleDTO rulePointScale;

   private EventDTO eventBadge;

   private EventDTO eventPointScale;

   private EventDTO event = new EventDTO();

   private RuleDTO rule;

   private DateTime timeStamp;
   private List<BadgeDTO> badges;
   private List<PointScaleDTO> pointScales;

   @Given("^a token for a registred application$")
   public void a_token_for_a_registred_application() throws Throwable {

      Registration applicationRegistration = new Registration();
      randomApplicationName = "random-app-toto" + (applicationsCounter++) + "-" + System.currentTimeMillis();
      applicationRegistration.setApplicationName(randomApplicationName);
      applicationRegistration.setPassword(DUMMY_PASSWORD);
      api.applicationsPost(applicationRegistration);

      Credentials credentials = new Credentials();
      credentials.setApplicationName(randomApplicationName);
      credentials.setPassword(DUMMY_PASSWORD);

      token = api.authenticateApplicationAndGetToken(credentials);
   }

   @Then("^I receive a (\\d+) code status$")
   public void i_receive_a_code_status(int arg1) throws Throwable {
      assertEquals(arg1, statusCode);
   }

   @Given("^I have one pointScale payload$")
   public void i_have_one_pointScale_payload() throws Throwable {
      randomPointScaleName = "pointScale-name-toto" + (pointScalesCounter++) + '-' + System.currentTimeMillis();
      pointScale = new PointScaleDTO();
      pointScale.setNbrDePoints(nbrDePoints);
      pointScale.setName(randomPointScaleName);
      pointScale.setDescription(description);
   }

   @When("^I POST it to /pointScales endpoint$")
   public void i_POST_it_to_pointScales_endpoint() throws Throwable {
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

   @Given("^I have one badge payload$")
   public void i_have_one_badge_payload() throws Throwable {
      randomBadgeName = "badge-name" + (badgesCounter++) + '-' + System.currentTimeMillis();
      badge = new BadgeDTO();
      badge.setImageURI(imageURI);
      badge.setName(randomBadgeName);
      badge.setDescription(description);
   }

   @When("^I POST it to /badges endpoint$")
   public void i_POST_it_to_badges_endpoint() throws Throwable {
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

   @Given("^I have a rule payload to give a badge$")
   public void i_have_a_rule_payload_to_give_a_badge() throws Throwable {
      ruleBadge = new RuleDTO();
      ruleBadge.setAction("ActionPoints");
      ruleBadge.setActionId(actionIdBadge);
      ruleBadge.setBadgeId(badge.getId());
      ruleBadge.setEvent("Concern badge");
      ruleBadge.setPointScale("name");
     ruleBadge.setPoints(5);
   }

   @When("^I POST it to /rules endpoint$")
   public void i_POST_it_to_rules_endpoint() throws Throwable {

      try {
         ApiResponse response = api.rulesPostWithHttpInfo(token.getApplicationName(), ruleBadge);
         statusCode = response.getStatusCode();
      } catch (ApiException e) {
         statusCode = e.getCode();
      }
   }
   
   @When("^I POST it to the /rules endpoint$")
   public void i_POST_it_to_the_rules_endpoint() throws Throwable {

      try {
         ApiResponse response = api.rulesPostWithHttpInfo(token.getApplicationName(), rulePointScale);
         statusCode = response.getStatusCode();
      } catch (ApiException e) {
         statusCode = e.getCode();
      }
   }

   @Given("^I have a rule payload to give a pointScale$")
   public void i_have_a_rule_payload_to_give_a_pointScale() throws Throwable {
      rulePointScale = new RuleDTO();
      rulePointScale.setAction("ActionPoints");
      rulePointScale.setActionId(actionIdPointScale);
      rulePointScale.setEvent("concern points");
      rulePointScale.setPointScale(pointScale.getName());
      rulePointScale.setPoints(nbrDePoints);
      rulePointScale.setBadgeId(actionIdBadge);//.....
   }

   @Given("^I have an event payload concerning a badge$")
   public void i_have_an_event_payload_concerning_a_badge() throws Throwable {
      eventBadge = new EventDTO();
      eventBadge.setTimeStamp(timeStamp);
      eventBadge.setType(rule.getEvent());
      event = eventBadge;
   }

   @When("^I POST it to /events endpoint$")
   public void i_POST_it_to_events_endpoint() throws Throwable {
      try {
         ApiResponse response = api.reportEventWithHttpInfo(token.getApplicationName(), event);
         statusCode = response.getStatusCode();
      } catch (ApiException e) {
         statusCode = e.getCode();
      }
   }

   @Given("^I have an event payload concerning a pointScale$")
   public void i_have_an_event_payload_concerning_a_pointScale() throws Throwable {
      eventPointScale = new EventDTO();
      eventPointScale.setTimeStamp(timeStamp);
      eventPointScale.setType(rule.getEvent());
      event = eventPointScale;
   }

   @Then("^I ask for list of badges with a GET on the /badges endpoint$")
   public void i_ask_for_list_of_badges_with_a_GET_on_the_badges_endpoint() throws Throwable {
      badges = api.badgesGet(token.getApplicationName());
   }

   @Then("^I see my badge in list$")
   public void i_see_my_badge_in_list() throws Throwable {
      assertThat(badges).extracting("name").contains(badge.getName());
   }

   @When("^I ask for list of pointScales with a GET on the /pointScales endpoint$")
   public void i_ask_for_list_of_pointScales_with_a_GET_on_the_pointScales_endpoint() throws Throwable {

      pointScales = api.pointScalesGet(token.getApplicationName());
   }

   @When("^I see my pointScale in list$")
   public void i_see_my_pointScale_in_list() throws Throwable {
      assertThat(pointScales).extracting("id").contains(pointScale.getId());
   }
}
