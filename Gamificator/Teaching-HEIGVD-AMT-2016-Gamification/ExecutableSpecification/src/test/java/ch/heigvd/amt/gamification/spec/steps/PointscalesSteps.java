///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package ch.heigvd.amt.gamification.spec.steps;
//
//import ch.heigvd.gamification.ApiException;
//import ch.heigvd.gamification.ApiResponse;
//import ch.heigvd.gamification.api.DefaultApi;
//import ch.heigvd.gamification.api.dto.PointScaleDTO;
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
//public class PointscalesSteps {
//    private final DefaultApi api = new DefaultApi(); 
//     /*
//   * Variables used to generate test data
//   */
//  final static String DUMMY_PASSWORD = "dummyPassword";
//  int applicationsCounter = 1;
//  int usersCounter = 1;
//    private int pointscalesCounter = 1;  
//    private int statusCode;
//    private PointScaleDTO pointscale;
//    private List<PointScaleDTO> poinscales; 
//    private String token;
//    Long pointScaleId= new Long(0);
//    
//
//
//    @Given("^the application's token$")
//public void the_application_s_token() throws Throwable {
//     
//    String randomApplicationName = "app-name-" + (applicationsCounter++) + '-' + System.currentTimeMillis();
//    Registration applicationRegistration = new Registration();
//    applicationRegistration.setApplicationName(randomApplicationName);
//    applicationRegistration.setPassword(DUMMY_PASSWORD);
//    api.registrationsPost(applicationRegistration); // register the application
//
//    Credentials credentials = new Credentials();
//    credentials.setApplicationName(randomApplicationName);
//    credentials.setPassword(DUMMY_PASSWORD);
//    Token token = api.authenticateApplicationAndGetToken(credentials); // and immediately authenticate to get a token
//
//}
//@Given("^I have a pointscale payload$")
//public void i_have_a_pointscale_payload() throws Throwable {
//    pointscale = new PointScaleDTO();
//    String randomPointscaleName = "random-pointscale-" + (pointscalesCounter++) + "-" + System.currentTimeMillis();
//    pointscale.setName(randomPointscaleName);
//    
//}
//
//@When("^I POST it to the /pointscales endpoint$")
//public void i_POST_it_to_the_pointscales_endpoint() throws Throwable {
//try {
//    
//      ApiResponse response = api.updatePointscaleWithHttpInfo(token,pointScaleId, pointscale);
//      statusCode = response.getStatusCode();
//    } catch (ApiException e) {
//      statusCode = e.getCode();
//    }
//}
//
//@When("^I ask for a list of pointscales with a GET on the /pointscales endpoint$")
//public void i_ask_for_a_list_of_pointscales_with_a_GET_on_the_pointscales_endpoint() throws Throwable {
//    //poinscales = api.f
//}
//
//@Then("^I see my pointscale in the list$")
//public void i_see_my_pointscales_in_the_list() throws Throwable {
//        PointScaleDTO expected = new PointScaleDTO();
//    expected.setName(pointscale.getName());
//
//    assertThat(poinscales).extracting("name").contains(pointscale.getName());
//}
//
//
//@Given("^I have wrong token$")
//public void i_have_wrong_token() throws Throwable {
//    // Write code here that turns the phrase above into concrete actions
//    throw new PendingException();
//}
//
//@Then("^I receive (\\d+) status code$")
//public void i_receive_status_code(int arg1) throws Throwable {
//assertEquals(arg1, statusCode);
//}
//
//
//@When("^I ask for a pointscale with a GET on the /pointscales endpoint with id$")
//public void i_ask_for_a_pointscale_with_a_GET_on_the_pointscale_endpoint_with_id() throws Throwable {
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
//@When("^I PUT it on /pointscale endpoint with id$")
//public void i_PUT_it_on_pointscale_endpoint_with_id() throws Throwable {
//        String oldName = pointscale.getName();
//        if(oldName!= null)
//             //pointscale.setName("updated_" + );
//        try{
//            ApiResponse response = api.updatePointscaleWithHttpInfo(token, pointScaleId, pointscale);
//            statusCode =response.getStatusCode();
//        }catch (ApiException e){
//            statusCode = e.getCode();
//        }
//}
//
//@Then("^the pointscale is modified$")
//public void the_pointscale_is_modified() throws Throwable {
//   // assertNotEquals(api.findBadgeById(token, badgeID),pointscale.getName());
//}
//
//@When("^I send a DELETE to the /pointscales endpoint with id$")
//public void i_send_a_DELETE_to_the_pointscales_endpoint_with_id() throws Throwable {
//    // Write code here that turns the phrase above into concrete actions
//    throw new PendingException();
//}
//
//@Then("^I don't see the badge in the list$")
//public void i_don_t_see_the_badge_in_the_list() throws Throwable {
//    // Write code here that turns the phrase above into concrete actions
//    throw new PendingException();
//}
//    
//}
