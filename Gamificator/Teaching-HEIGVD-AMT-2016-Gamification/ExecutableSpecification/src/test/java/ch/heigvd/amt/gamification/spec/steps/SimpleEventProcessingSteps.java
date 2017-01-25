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
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author user
 */
public class SimpleEventProcessingSteps {
   
   private Registration registration;
   
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
   
   private Credentials credentials;

   private BadgeDTO badge;
   
   final static String DUMMY_PASSWORD = "dummyPassword";
   
   private int statusCode;
   
   private RuleDTO ruleBadge;
   
   private RuleDTO rulePointScale;
   
   private EventDTO eventBadge;
   
   private EventDTO eventPointScale;
   
   private EventDTO event;
   
   @Given("^I have one application payload$")
public void i_have_one_application_payload() throws Throwable {
    registration = new Registration();
      randomApplicationName = "random-app-" + (applicationsCounter++) + "-" + System.currentTimeMillis();
      registration.setApplicationName(randomApplicationName);
      registration.setPassword(DUMMY_PASSWORD);
}

@When("^I POST it to /applications endpoint$")
public void i_POST_it_to_applications_endpoint() throws Throwable {
    try {
         ApiResponse response = api.applicationsPostWithHttpInfo(registration);
         statusCode = response.getStatusCode();
      } catch (ApiException e) {
         statusCode = e.getCode();
      }
}

@Then("^I receive a (\\d+) code status$")
public void i_receive_a_code_status(int arg1) throws Throwable {
     assertEquals(arg1, statusCode);
}

@When("^I POST it to /authentications endpoint$")
public void i_POST_it_to_authentications_endpoint() throws Throwable {
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

@Then("^I receive token$")
public void i_receive_token() throws Throwable {
    token = api.authenticateApplicationAndGetToken(credentials);
}

@Given("^I have one pointScale payload$")
public void i_have_one_pointScale_payload() throws Throwable {
    randomPointScaleName = "pointScale-name-" + (pointScalesCounter++) + '-' + System.currentTimeMillis();
      pointScale = new PointScaleDTO();
      pointScale.setNbrDePoints(nbrDePoints);
      pointScale.setName(randomPointScaleName);
      pointScale.setDescription(description);
}

@When("^I POST it to /pointScales endpoint$")
public void i_POST_it_to_pointScales_endpoint() throws Throwable {
    try {
         ApiResponse response = api.pointScalesPostWithHttpInfo(token.getApplicationName(), pointScale);
          
        Map<String,List<String>> headers = response.getHeaders();
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
   randomBadgeName = "badge-name-" + (badgesCounter++) + '-' + System.currentTimeMillis();
      badge = new BadgeDTO();
      badge.setImageURI(imageURI);
      badge.setName(randomBadgeName);
      badge.setDescription(description);
}

@When("^I POST it to /badges endpoint$")
public void i_POST_it_to_badges_endpoint() throws Throwable {
     try {
         ApiResponse response = api.badgesPostWithHttpInfo(badge, token.getApplicationName());
         Map<String,List<String>> headers = response.getHeaders();
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
    ruleBadge.setAction("ActionBadge");
    ruleBadge.setActionId(Long.MIN_VALUE);
    ruleBadge.setBadgeId(badge.getId());
    ruleBadge.setEvent("Concern badge");
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

@Given("^I have a rule payload to give a pointScale$")
public void i_have_a_rule_payload_to_give_a_pointScale() throws Throwable {
   rulePointScale = new RuleDTO();
   rulePointScale.setAction("ActionPoints");
rulePointScale.setActionId(Long.MAX_VALUE);
rulePointScale.setEvent("concern points");
rulePointScale.setPointScale(pointScale.getName());
rulePointScale.setPoints(nbrDePoints);
}

@Given("^I have an event payload concerning a badge$")
public void i_have_an_event_payload_concerning_a_badge() throws Throwable {
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
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
    // Write code here that turns the phrase above into concrete actions
    throw new PendingException();
}
   
}
