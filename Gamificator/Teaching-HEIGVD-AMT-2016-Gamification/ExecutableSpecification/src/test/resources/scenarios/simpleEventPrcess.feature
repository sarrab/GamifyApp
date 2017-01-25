Feature: A simple event processing

Scenario: Register an application
Given I have one application payload
When I POST it to /applications endpoint
Then I receive a 201 code status

Scenario: Authenticate that application
Given I have one application payload
When I POST it to /applications endpoint
Then I receive a 201 code status
When I POST it to the /authentications endpoint
Then I receive a 200 code status
And I receive token

Scenario: create a pointScale for that application
Given I have one pointScale payload
When I POST it to /pointScales endpoint
Then I receive a 201 code status

Scenario: create a badge for that application
Given I have one badge payload
When I POST it to /badges endpoint
Then I receive 201 code status
