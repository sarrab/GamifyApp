Feature: A simple event processing

Background:
   Given a token for a registred application

Scenario: create a badge for that application
Given I have one badge payload
When I POST it to /badges endpoint
Then I receive a 201 code status

Scenario: create a pointScale for that application
Given I have one pointScale payload
When I POST it to /pointScales endpoint
Then I receive a 201 code status 

Scenario: create a rule to give a badge
Given I have one badge payload
When I POST it to /badges endpoint
Then I receive a 201 code status
Given I have a rule payload to give a badge
When I POST it to /rules endpoint
Then I receive a 201 code status

Scenario: create a rule to give a pointScale
Given I have one pointScale payload
When I POST it to /pointScales endpoint
Then I receive a 201 code status
Given I have a rule payload to give a pointScale
When I POST it to the /rules endpoint
Then I receive a 201 code status

Scenario: post an event to earn a badge
Given I have one badge payload
When I POST it to /badges endpoint
Then I receive a 201 code status
Given I have a rule payload to give a badge
When I POST it to /rules endpoint
Then I receive a 201 code status
Given I have an event payload concerning a badge
When I POST it to /events endpoint
Then I receive a 200 code status

Scenario: post an event to earn points
Given I have one pointScale payload
When I POST it to /pointScales endpoint
Then I receive a 201 code status
Given I have a rule payload to give a pointScale
When I POST it to /rules endpoint
Then I receive a 201 code status
Given I have an event payload concerning a pointScale
When I POST it to /events endpoint
Then I receive a 200 code status




