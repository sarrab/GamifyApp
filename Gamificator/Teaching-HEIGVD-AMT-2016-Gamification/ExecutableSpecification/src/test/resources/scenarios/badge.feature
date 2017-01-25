Feature: Testing Badges endpoint

Background:
   Given a token for a new gamified application

Scenario: create a badge for the given application
Given I have a badge payload
When I POST it to the /badges endpoint
Then I receive 201 status code

Scenario: Check that the badge has been created
Given I have a badge payload
When I POST it to the /badges endpoint
And I ask for a list of badges with a GET on the /badges endpoint
Then I see my badge in the list

Scenario: Check that it is not possible to create two  badges with the same name
Given I have a badge payload
When I POST it to the /badges endpoint
And badge name already exists
Then I receive 422 status code

Scenario: Delete a badge
Given I have a badge payload
When I POST it to the /badges endpoint
And I have the id of that badge
When I send a DELETE on the /badges endpoint
Then I receive 200 status code

Scenario: Modify a badge
Given I have a badge payload
When I POST it to the /badges endpoint
And I have the id of that badge
And I have a new badge payload
When I send a PUT on the /badges endpoint
Then I receive 200 status code

Scenario: Modify a badge that do not exists
Given I have a badge payload
When I POST it to the /badges endpoint
And I have a new badge payload
And I have a wrong id of badge
When I send a PUT on the /badges endpoint
Then I receive 404 status code

Scenario: Delete a badge that do not exists
Given I have a badge payload
When I POST it to the /badges endpoint
And I have a wrong id of badge
When I send a DELETE on the /badges endpoint
Then I receive 404 status code

Scenario: Get a badge that do not exists
Given I have a badge payload
When I POST it to the /badges endpoint
And I have a wrong id of badge
When I send a GET on the /badges endpoint
Then I receive 404 status code

Scenario: Check that it is not possible to see badges with a wrong application's token
Given I have a badge payload
And I have wrong token
When I POST it to the /badges endpoint
And I ask for a list of badges with a GET on the /badges endpoint
Then I receive 401 status code

Scenario: Check that it is not possible to create a badge with a wrong application's token
Given I have a badge payload
And I have wrong token
When I POST it to the /badges endpoint
Then I receive 401 status code

Scenario: Check that it is not possible to delete a badge with a wrong application's token
Given I have a badge payload
When I POST it to the /badges endpoint
And I have the id of that badge
And I have wrong token
When I send a DELETE on the /badges endpoint
Then I receive 401 status code

Scenario: Check that it is not possible to modify a badge with a wrong application's token
Given I have a badge payload
When I POST it to the /badges endpoint
And I have the id of that badge
And I have a new badge payload
And I have wrong token
When I send a PUT on the /badges endpoint
Then I receive 401 status code

