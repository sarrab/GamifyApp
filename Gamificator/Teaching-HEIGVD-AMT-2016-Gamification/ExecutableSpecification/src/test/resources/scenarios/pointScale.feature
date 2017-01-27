Feature: Testing PointScale endpoint

Background:
   Given I have a token for a new gamified application

Scenario: create a pointScale for the given application
Given I have a pointScale payload
When I POST it to the /pointScales endpoint
Then I receive 201 code status 

Scenario: Check that the pointScale has been created
Given I have a pointScale payload
When I POST it to the /pointScales endpoint
And I ask for a list of pointScales with a GET on the /pointScales endpoint
And  I see my pointScale in the list

Scenario: Check that it is not possible to create two  pointScales with the same name
Given I have a pointScale payload
When I POST it to the /pointScales endpoint
And pointScale name already exists
Then I receive 422 code status 

Scenario: Delete a pointScale
Given I have a pointScale payload
When I POST it to the /pointScales endpoint
And I have the id of that pointScale
When I send a DELETE on the /pointScales endpoint
Then I receive 200 code status 

Scenario: Modify a pointScale
Given I have a pointScale payload
When I POST it to the /pointScales endpoint
And I have the id of that pointScale
And I have a new pointScale payload
When I send a PUT on the /pointScales endpoint
Then I receive 200 code status 

Scenario: Modify a pointScale that do not exists
Given I have a pointScale payload
When I POST it to the /pointScales endpoint
And I have a wrong id of pointScale
And I have a new pointScale payload
When I send a PUT on the /pointScales endpoint
Then I receive 404 code status 

Scenario: Delete a pointScale that do not exists
Given I have a pointScale payload
When I POST it to the /pointScales endpoint
And I have a wrong id of pointScale
When I send a DELETE on the /pointScales endpoint
Then I receive 404 code status 

Scenario: Get a pointScale that do not exists
Given I have a pointScale payload
When I POST it to the /pointScales endpoint
And I have a wrong id of pointScale
When I send a GET on the /pointScales endpoint
Then I receive 404 code status 

Scenario: Check that it is not possible to see pointScales with a wrong application's token
Given I have a pointScale payload
And I have a wrong token
When I POST it to the /pointScales endpoint
And I ask for a list of pointScales with a GET on the /pointScales endpoint
Then I receive 401 code status 

Scenario: Check that it is not possible to create a pointScale with a wrong application's token
Given I have a pointScale payload
And I have a wrong token
When I POST it to the /pointScales endpoint
Then I receive 401 code status

Scenario: Check that is not possible to see a pointScales with a wrong application's token
Given I have a pointScale payload
And I have a wrong token
When I POST it to the /pointScales endpoint 
And I ask for a list of pointScales with a GET on the /pointScales endpoint
Then I receive 401 code status 

Scenario: Check that it is not possible to delete a pointScale with a wrong application's token
Given I have a pointScale payload
When I POST it to the /pointScales endpoint
And I have the id of that pointScale
And I have a wrong token
When I send a DELETE on the /pointScales endpoint
Then I receive 401 code status

Scenario: Check that it is not possible to modify a pointScale with a wrong application's token
Given I have a pointScale payload
When I POST it to the /pointScales endpoint
And I have the id of that pointScale
And I have a new pointScale payload
And I have a wrong token
When I send a PUT on the /pointScales endpoint
Then I receive 401 code status 


