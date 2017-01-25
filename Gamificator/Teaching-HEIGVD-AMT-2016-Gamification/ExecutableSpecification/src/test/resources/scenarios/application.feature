Feature: Application registration

Scenario: Register a new application
Given I have an application payload
When I POST it to the /applications endpoint
Then I receive a 201 status code

Scenario: Authenticate a new registered application
Given I have an application payload
When I POST it to the /applications endpoint
Then I receive a 201 status code
When I POST it to the /authentications endpoint
Then I receive a 200 status code
And I receive a token

Scenario: Check that the application has been registered
Given I have an application payload
And I POST it to the /applications endpoint
When I ask for a list of registered apps with a GET on the /applications endpoint
Then I see my app in the list

Scenario: Wrong authentication of an application
Given I have a bad application payload
When I POST it to the /authentications endpoint
Then I receive a 401 status code

Scenario: Check that the name of the application is unique
Given I have an application payload
When I POST it to the /applications endpoint
And application name already exists
Then I receive a 422 status code

Scenario: Delete an application
Given I have an application payload
And I POST it to the /applications endpoint
And I have the name of that application
When I send a DELETE on the /applications endpoint
Then I receive a 200 status code

Scenario: Modify an application
Given I have an application payload
And I POST it to the /applications endpoint
And I have the name of that application
And I have a new application payload
When I send a PUT on the /applications endpoint
Then I receive a 200 status code

Scenario: Modify an application that do not exists
Given I have the name of an unregistred application
And I have a new application payload
When I send a PUT on the /applications endpoint
Then I receive a 404 status code

Scenario: Delete an application that do not exists
Given I have the name of an unregistred application
When I send a DELETE on the /applications endpoint
Then I receive a 404 status code

Scenario: Get an application that do not exists
Given I have the name of an unregistred application
When I send a GET on the /applications endpoint
Then I receive a 404 status code

