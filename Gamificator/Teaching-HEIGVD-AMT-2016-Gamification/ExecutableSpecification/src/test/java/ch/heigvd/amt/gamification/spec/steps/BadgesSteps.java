///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package ch.heigvd.amt.gamification.spec.steps;
//
//
//import ch.heigvd.gamification.ApiException;
//import ch.heigvd.gamification.ApiResponse;
//import ch.heigvd.gamification.api.DefaultApi;
//import ch.heigvd.gamification.api.dto.BadgeDTO;
//import ch.heigvd.gamification.api.dto.Credentials;
//import ch.heigvd.gamification.api.dto.Registration;
//import ch.heigvd.gamification.api.dto.Token;
//import cucumber.api.PendingException;
//import cucumber.api.java.en.Given;
//import cucumber.api.java.en.Then;
//import cucumber.api.java.en.When;
//import static org.junit.Assert.*;
//import static org.assertj.core.api.Assertions.*;
//import java.util.List;
//
///**
// *
// * @author Sarra
// */
//public class BadgesSteps {
//    private final DefaultApi api = new DefaultApi(); 
//     /*
//   * Variables used to generate test data
//   */
//  final static String DUMMY_PASSWORD = "dummyPassword";
//  int applicationsCounter = 1;
//  int usersCounter = 1;
//    private int badgesCounter = 1;  
//    private int statusCode;
//    private BadgeDTO badge;
//    private List<BadgeDTO> badges; 
//    private Token token;
//    
//    
//
//
//    @Given("^the application's token$")
//public void the_application_s_token() throws Throwable {
//     
//    String randomApplicationName = "app-name-" + (applicationsCounter++) + '-' + System.currentTimeMillis();
//    Registration applicationRegistration = new Registration();
//    applicationRegistration.setName(randomApplicationName);
//    applicationRegistration.setPassword(DUMMY_PASSWORD);
//    api.registerApp(applicationRegistration); // register the application
//
//    Credentials credentials = new Credentials();
//    credentials.setApplicationName(randomApplicationName);
//    credentials.setPassword(DUMMY_PASSWORD);
//    token = api.authenticateApplicationAndGetToken(credentials); // and immediately authenticate to get a token
//    
//
//}
//@Given("^I have a badge payload$")
//public void i_have_a_badge_payload() throws Throwable {
//    badge = new BadgeDTO();
//    String randomBadgeName = "random-badge-" + (badgesCounter++) + "-" + System.currentTimeMillis();
//    badge.setName(randomBadgeName);
//    
//}
//
//@When("^I POST it to the /badges endpoint$")
//public void i_POST_it_to_the_badges_endpoint() throws Throwable {
//try {
//    
//      ApiResponse response = api.createBadgeWithHttpInfo(badge, token.getApplicationName());
//      statusCode = response.getStatusCode();
//    } catch (ApiException e) {
//      statusCode = e.getCode();
//    }
//}
//
//@When("^I ask for a list of badges with a GET on the /badges endpoint$")
//public void i_ask_for_a_list_of_badges_with_a_GET_on_the_badges_endpoint() throws Throwable {
//    badges = api.badgesGet(token.getApplicationName());
//}
//
//@Then("^I see my badge in the list$")
//public void i_see_my_badge_in_the_list() throws Throwable {
//
//    assertThat(badges).extracting("name").contains(badge.getName());
//}
//
//
//@Given("^I have wrong token$")
//public void i_have_wrong_token() throws Throwable {
//token.setApplicationName("wrong");
//}
//
//@Then("^I receive (\\d+) status code$")
//public void i_receive_status_code(int arg1) throws Throwable {
//assertEquals(arg1, statusCode);
//}
//
//
//@When("^I ask for a badge with a GET on the /badges endpoint with id$")
//public void i_ask_for_a_badge_with_a_GET_on_the_badges_endpoint_with_id() throws Throwable {
//    // Write code here that turns the phrase above into concrete actions
//    throw new PendingException();
//}
//
//@When("^I have a wrong id$")
//public void i_have_a_wrong_id() throws Throwable {
//    // Write code here that turns the phrase above into concrete actions
//    throw new PendingException();
//}
//
//@When("^I PUT it on /badges endpoint with id$")
//public void i_PUT_it_on_badges_endpoint_with_id() throws Throwable {
//        String oldName = badge.getName();
//        if(oldName!= null)
//             //badge.setName("updated_" + );
//        try{
//            ApiResponse response = api.updateBadgeWithHttpInfo(token.getApplicationName(),badge.getId(), badge);
//            statusCode =response.getStatusCode();
//        }catch (ApiException e){
//            statusCode = e.getCode();
//        }
//}
//
//@Then("^the badge is modified$")
//public void the_badge_is_modified() throws Throwable {
//    //assertNotEquals(api.findBadgeById(token, badgeID),badge.getName());
//}
//
//@When("^I send a DELETE to the /badges endpoint with id$")
//public void i_send_a_DELETE_to_the_badges_endpoint_with_id() throws Throwable {
//ApiResponse response = api.deleteBadgeWithHttpInfo(token.getApplicationName(), badge.getId());
//            statusCode =response.getStatusCode();
//}
//
//@Then("^I don't see the badge in the list$")
//public void i_don_t_see_the_badge_in_the_list() throws Throwable {
//    assertThat(badges).extracting("name").doesNotContain(badge.getName());
//    
//}
//    
//}
