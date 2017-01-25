Feature: A simple event processing

Scenario: Register an application
Given I have one application payload
When I POST it to /applications endpoint
Then I receive a 201 code status

Scenario: Authenticate that application
Given I have one application payload
When I POST it to /applications endpoint
Then I receive a 201 code status
When I POST it to /authentications endpoint
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

Scenario: create a rule to give a badge
Given I have a rule payload to give a badge
When I POST it to /rules endpoint
Then I receive 201 code status

Scenario: create a rule to give a pointScale
Given I have a rule payload to give a pointScale
When I POST it to /rules endpoint
Then I receive 201 code status

Scenario: post an event to earn a badge
Given I have an event payload concerning a badge
When I POST it to /events endpoint
Then I receive 200 code status

Scenario: post an event to earn a badge
Given I have an event payload concerning a badge
When I POST it to /events endpoint
Then I receive 200 code status

Scenario: post an event to earn points
Given I have an event payload concerning a pointScale
When I POST it to /events endpoint
Then I receive 200 code status



