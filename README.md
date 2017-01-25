##Teaching-HEIGVD-AMT-2016-Gamification-Platform

###Collaborators: Togue Kamga Thibaut, Norah Jeannine et Sarra Berich

##Objectives

The objectif of this project is to develop and test a gamified application platform which attribute badges or points scales to users who post events, accordingly to rules established. You can learn more about a gamified platform [Here](https://en.wikipedia.org/wiki/Gamification). 

##Introduction
###High level architecture

![](architecture.JPG) 

As you can see on this architecture, applications which are registrered on our platform can create, get, update and delete **badges**, **pointScales** and **rules**. The users of an application can post events on our platform, and accordingly to the type of the event posted, it is possible to attribute a badge or a point scale to the user. If the user posting the event doesn't exists, we create the user in the application. It is also possible for the application to ask for the leaderboards (list of best users of the application).

##Technologies

This project was realized mainly with Spring Boot and Swagger. Indeed, a top-down approach was used in general. At the beginning we started with a swagger specification but we made adjustments to generate only the DtoS classes. The bottom-up approach was also used to expose the documentation of specification within SpringFox.
The following technologies were used:

-Spring Boot
-Swagger
-Spring Fox
-JPA
-Cucumber
-Postman

##Specification API

We have used [Swagger editor](http://editor.swagger.io/#/) to write the specification of our API Rest.  
You can see the documentation on the ``/docs`` dirctory of this repo.

##Test
In order to test our platform, we created a Java project. It is in this repo at the same level as the implementation of the platform.
The purpose of automated testing is to test API endpoints.
To do this, we used the framework [Cucumber](https: //cucumber.io/).
1-check that the application has been started successfully.
2-Open the project with an IDE (Netbeans for example).
3-click Test
