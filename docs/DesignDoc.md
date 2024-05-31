---
geometry: margin=1in
---
# PROJECT Design Documentation

> _The following template provides the headings for your Design
> Documentation.  As you edit each section make sure you remove these
> commentary 'blockquotes'; the lines that start with a > character
> and appear in the generated PDF in italics._

## Team Information
* Team name: Sam and Friends
* Team members
  * Ashton Michelstein
  * Christian Morgado
  * Sam Ruan
  * Laurena Roberts

## Executive Summary

We are trying to deliver a functional website that encompasses a fully tested and architectually sound front end and back end. Over the course of 4 Sprints, we will use incremental development achieve the highest quality possible.

### Purpose
We are creating a platform to help improve the quality of life of orhpans. Helpers can choose to donate needs such as toys and art supplies, or even basic necessities such as comfortable bedding to help orphans live a better life.

### Glossary and Acronyms
> _**[Sprint 2 & 4]** Provide a table of terms and acronyms._

| Term | Definition |
|------|------------|
| MVP | A version of the product that meets the essential requirements and functionality; Minimum Viable Product |
| Need | A "product" created by the U-fund Manager; Helpers select needs and fund them to support the company's cause |
| Helper | A user who funds a need; not a U-fund Manager |
| U-fund Manager | The person in control of the website; can edit, add, and remove needs |
| Cupboard | A page where all the needs that require funding are displayed |
| Funding Basket | A page where needs can be funded by the helper |


## Requirements

This section describes the features of the application.

> _In this section you do not need to be exhaustive and list every
> story.  Focus on top-level features from the Vision document and
> maybe Epics and critical Stories._

### Definition of MVP
> _**[Sprint 2 & 4]**_

The Minimum Viable Product is not focused on how the website looks, but rather delivering the basic functionality of the website.. Helpers will be able to pick needs from the cupboard and add them to the funding basket. The U-fund manager will be able to add, delete, and edit needs in the cupboard. The manager will not have access to the funding basket. All users will be able to log in with a username.

### MVP Features
>  _**[Sprint 4]**_

Log in / Log out, Create Helper, Add Needs to Funding Basket, Create Funding Basket.

### Enhancements
> _**[Sprint 4]**_

For our two enhancements, we implemented full admin control over the dashboard and a feature that counts the total funds a helper has donated in their current session.


## Application Domain

This section describes the application domain.

![SWEN261 Domain Analysis](https://github.com/RIT-SWEN-261-04/team-project-2231-swen-261-04-sam-and-friends/assets/143859287/b9d59c42-d744-4c1d-b6e4-4485c240ada0)


> _**[Sprint 2 & 4]**_

The most important domain entities in this project are the Helper, Cupboard, Funding Basket, Needs, and U-Fund Manager. The U-Fund manager will have full control of the cupboard and the needs placed in the cupboard. A helper can only access the cupboard and pick needs they want from the cupboard. They will pick a need and add it to the funding basket. The U-Fund Manager will not be able to see the funding basket.


## Architecture and Design

This section describes the application architecture.

### Summary

The following Tiers/Layers model shows a high-level view of the webapp's architecture. 
**NOTE**: detailed diagrams are required in later sections of this document. (_When requested, replace this diagram with your **own** rendition and representations of sample classes of your system_.) 

<img width="672" alt="architecture-tiers-layers-1" src="https://github.com/RIT-SWEN-261-04/team-project-2231-swen-261-04-sam-and-friends/assets/143859287/4f363a5e-9bae-4ebe-bb50-64badf37a54c">


The web application, is built using the Model–View–ViewModel (MVVM) architecture pattern. 

The Model stores the application data objects including any functionality to provide persistance. 

The View is the client-side SPA built with Angular utilizing HTML, CSS and TypeScript. The ViewModel provides RESTful APIs to the client (View) as well as any logic required to manipulate the data objects from the Model.

Both the ViewModel and Model are built using Java and Spring Framework. Details of the components within these tiers are supplied below.


### Overview of User Interface

This section describes the web interface flow; this is how the user views and interacts with the web application.

Upon launching the website, the user is brought to the log in page. From there, the user can enter a username and press the log in button, which will take them to the dashboard. The dashboard contains a list of the top needs, as well as a search bar to search for needs. On the top of the page is a navigation bar with buttons for the Dashboard and Cupboard. The User's funding basket is displayed next to the cupboard on the Cupboard page. The user can get to any page on the website from anywhere. There is a log out button on the top right of the page, which, when pressed, will log the user out and bring them back to the login page.


### View Tier
> _**[Sprint 4]** Provide a summary of the View Tier UI of your architecture.
> Describe the types of components in the tier and describe their
> responsibilities.  This should be a narrative description, i.e. it has
> a flow or "story line" that the reader can follow._

Our View Tier UI contains various components that work together to create the UI. For each page the user can visit, we have 2 components. The reason behind this is that if the logged in user is an admin, they will be brought to version of the page where the can edit the page. If the user is logged in as a helper, they cannot edit the page. We have a component for the funding basket, which shows which needs the helper has selected and wants to fund. We also have a component for the cupboard, which shows all the needs the helper can choose to fund. We also have a search bar component that allows the user to search for needs. This component will bring the user to the right page depending if they are an admin or helper.

> _**[Sprint 4]** You must  provide at least **2 sequence diagrams** as is relevant to a particular aspects 
> of the design that you are describing.  (**For example**, in a shopping experience application you might create a 
> sequence diagram of a customer searching for an item and adding to their cart.)
> As these can span multiple tiers, be sure to include an relevant HTTP requests from the client-side to the server-side 
> to help illustrate the end-to-end flow._
>
![Add Need Sequence Diagram drawio (1)](https://github.com/RIT-SWEN-261-04/team-project-2231-swen-261-04-sam-and-friends/assets/143859287/f2905aab-6fec-4de8-8cb7-0c364aed22bf)


> _**[Sprint 4]** To adequately show your system, you will need to present the **class diagrams** where relevant in your design. Some additional tips:_
 >* _Class diagrams only apply to the **ViewModel** and **Model** Tier_
>* _A single class diagram of the entire system will not be effective. You may start with one, but will be need to break it down into smaller sections to account for requirements of each of the Tier static models below._
 >* _Correct labeling of relationships with proper notation for the relationship type, multiplicities, and navigation information will be important._
 >* _Include other details such as attributes and method signatures that you think are needed to support the level of detail in your discussion._

### ViewModel Tier
> _**[Sprint 4]** Provide a summary of this tier of your architecture. This
> section will follow the same instructions that are given for the View
> Tier above._

Our ViewModel Tier consists of the controller files (FundingBasketController, HelperController, NeedController). The FundingBasketController manages all operations related to FundingBasket objects. It interacts with the FundingBasketDAO, which is part of the Model layer, to create a Funding Basket, get a Funding Basket, add needs to a Funding Basket, and delete Needs from a Funding Basket. Similarly, the HelperController interacts with the HelperDAO to receive and create Helper objects. The NeedController interacts with the CupboardDAO to allow us to update, delete, and add needs. Together, these files work together to allow the data to be processed and displayed between the Model and View Tiers.

> _At appropriate places as part of this narrative provide **one** or more updated and **properly labeled**
> static models (UML class diagrams) with some details such as critical attributes and methods._
>
![FundingBasketController drawio](https://github.com/RIT-SWEN-261-04/team-project-2231-swen-261-04-sam-and-friends/assets/143859287/cbca6775-92fd-4ad7-97c4-7936cfff6227)


### Model Tier
> _**[Sprint 2, 3 & 4]**_

Our Model Tier consists Need.java, CupboardDAO, HelperDAO, FundingBasketDAO, and all of their implementations (CupboardFileDAO, HelperFileDAO, FundingBasketFileDAO). CupboardFileDAO is primarily responsible for managing Need objects. It uses a map (Map<String, Need>) to store individual Need objects, where each Need is uniquely identified by its name. Similar to CupboardFileDAO, FundingBasketFileDAO uses a map (Map<String, FundingBasket>) for data storage, with each Funding Basket being identified by a helper's username. Both of these files have methods for adding and removing their respective objects. The HelperFileDAO class stores helpers by their unique username.

> _At appropriate places as part of this narrative provide **one** or more updated and **properly labeled**
> static models (UML class diagrams) with some details such as critical attributes and methods._
> 
![ModelUmlDiagram (1)](https://github.com/RIT-SWEN-261-04/team-project-2231-swen-261-04-sam-and-friends/assets/143859287/3933f6b1-8f8a-4255-8c4a-6e404be311f9)

## OO Design Principles
> _**[Sprint 2, 3 & 4]** Will eventually address up to **4 key OO Principles** in your final design. Follow guidance in augmenting those completed in previous Sprints as indicated to you by instructor. Be sure to include any diagrams (or clearly refer to ones elsewhere in your Tier sections above) to support your claims._

We implemented Low Coupling, Dependency Inversion, Single Responsibility, and Controller in our code. An example of how Low Coupling is used in our code is the DAO classes (FundingBasketDAO, HelperDAO, CupboardDAO). These classes are designed to reduce dependencies bewteen other parts of the code. These classes can be tested indivudally from the other parts of the code, which increased modularity. One example of Dependency Inversion in our code is this: \
public FundingBasketController(FundingBasketDAO basketDAO) { \
    this.basketDAO = basketDAO; \
} \
The FundingBasketController doesn't know exactly how FundingBasketDAO works, rather, it sticks to the expected interface. Our code utilizes Single Responsibility, since the controller classes (FundingBasketController, NeedController, HelperController) have a single task of handling the HTTP requests for their respective objects. Finally, the Controller Principle is used in our code with these same classes. The controllers act as a middle man between the View and the Model tiers, and are responsible for processing requests and returning the right responses


## Static Code Analysis/Future Design Improvements
> _**[Sprint 4]**_ With the results from the Static Code Analysis exercise, 
> **Identify 3-4** areas within your code that have been flagged by the Static Code 
> Analysis Tool (SonarQube) and provide your analysis and recommendations.  
> Include any relevant screenshot(s) with each area.

When we tested our code with SonarQube, no problems emerged. We received an A in reliability (0 Bugs), an A in security (0 Vulnerabilities), an A in security seview (0 Security Hotspots), and an A in maintainability (42 code smells). If we had to improve upon certain areas, we would improve on our code smells. This would consist of remove commented out blocks of code. Additionally, SonarQube recommended that we invoke methods conditionally, so we could also implement this to decrease our code smells. SonarQube also recommended that we use built in formatting to make arguments, which is not a serious issue, but could be implemented to improve efficiency._
![analysis](https://github.com/RIT-SWEN-261-04/team-project-2231-swen-261-04-sam-and-friends/assets/143859287/2abf5562-c3a2-45b2-9b0c-c765deec75e6)


> _**[Sprint 4]**_

If we had more time, our team would try to get all tiers to 100% coverage instead of just over 90%. We would also try to get rid of all our code smells reported by SonarQube.

## Testing

We used JUnit assserts to test each method in all three tiers of our code. We used expected and actual values combined with JUnit asserts to ensure that each of our methods was working as intended._

### Acceptance Testing
> _**[Sprint 2 & 4]**_

Currently in Sprint 4, we have had 17 user stories pass their acceptance criteria. We only have user stories for our sprint 4 presentation left, but all our programming user stories have passed their acceptance criteria.._

### Unit Testing and Code Coverage
> _**[Sprint 4]**_

Our unit testing strategy is to create a method, then create the unit test for it right after. We have consistently had above 90% coverage. Our teams coverage target was 90%, and we have exceeded that. Our persistence tier has 98%, our model tier has 92%, and our controller tier has 83%. Originally, we had out controller tier at 93%, but before the submission for sprint 3, we ran into merge conflicts and lost some unit tests. We plan to implement these tests to get above 90% again.

>_**[Sprint 2 & 4]** **Include images of your code coverage report.**_
><img width="411" alt="controller-tier" src="https://github.com/RIT-SWEN-261-04/team-project-2231-swen-261-04-sam-and-friends/assets/143859287/1ec72ca8-fcc2-4f78-ba66-0e0f1573480d">
<img width="421" alt="persistence-tier" src="https://github.com/RIT-SWEN-261-04/team-project-2231-swen-261-04-sam-and-friends/assets/143859287/d82754d5-1d49-48db-9361-e2039c31102f">
<img width="420" alt="model-tier" src="https://github.com/RIT-SWEN-261-04/team-project-2231-swen-261-04-sam-and-friends/assets/143859287/2c1c6c5f-9936-4bac-bbd3-f452c48916f8">






