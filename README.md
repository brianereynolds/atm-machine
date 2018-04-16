# ATM Machine Project

## Introduction
Software for an ATM machine. The software is responsible for validation customer account details
and performing basic operations including balance inquires and cash withdrawals.

## Quick start

### Gradle
```
gradle bootRun
```

### Docker
```
docker pull brianereynolds/atm-machine
docker run -p 7000:7000 docker.io/brianereynolds/atm-machine
```

## CI/CD
* Github: https://github.com/brianereynolds/atm-machine
* Travis CI: https://travis-ci.org/brianereynolds/atm-machine
* Sonar Cloud: https://sonarcloud.io/dashboard?id=brianereynolds%3Aatm-machine
* Docker Hub: https://hub.docker.com/r/brianereynolds/atm-machine/

## Swagger
Once the application is running, a Swagger definition is available here: http://localhost:7000/swagger-ui.html.
This can be used to call the API, or [build a client](https://github.com/swagger-api/swagger-codegen) in wide range of languages.

## Details
### Interface
The best place to look for interface specification is the Swagger file. At a high-level, there are 2 API operations
 - Balance: Retrieve account balance and max withdrawal amount
 - Withdraw: Withdraw cash from ATM

If an error is enountered, a JSON response and HTTP 400 is returned. The JSON message itself has the most detailed information.
The following table illustrates the error code mapping.

|Code   |Message                            | Example                |
|-------|-----------------------------------| ---------------------- |
|Pnnnn  | Related to PIN service            | Invalid PIN entered    |
|Annnn  | Related to Account service        | Not enough funds       |
|Snnnn  | Related to ATM service            | Insufficient ATM funds |

### Database
[H2](https://en.wikipedia.org/wiki/H2_(DBMS)) has been used as the backend database. This is sufficient
for dev/unit testing, but this should not be used in production. There are 2 tables

 - Account: Account information with number, PIN, balance and overdraft
 - Safe: Monetary safe for maintaining the count of the notes in the ATM.

These tables have been populated from [application data.sql](src/main/resources/data.sql).
When running tests, data is also loaded from [test data.sql](src/test/resources/data.sql)
I have used [Spring Data JPA](https://projects.spring.io/spring-data-jpa/) to automatically wire a CRUD interface.

### Configuration
The application (including logging) is configured using [application.yml](src/main/resources/application.yml)

### Testing
I have tried to keep the test coverage above 90%. No tests have specifically been marked as "unit" vs "integration", although the [AtmControllerTest.java](src/test/java/com/mybank/atm/controller/AtmControllerTest.java) mostly closely resembles a integration test.
_Most_ tests leverage the test data that has been populated by the scripts. I have also used Mockito to create specialized test conditions.

All are run via
```gradle test```.

Test coverage is created using [jacoco](http://www.eclemma.org/jacoco/).
Any manual tests have been run via the [Swagger HTML client](http://localhost:7000/swagger-ui.html).
Given more time I would create some cucumber-based "true" integration tests.

### Continuous Quality/Integration/Deployment
I have used SonarLint (integration in Intellij) and
[SonarCloud](https://sonarcloud.io/dashboard?id=brianereynolds%3Aatm-machine),
via [Travis](https://travis-ci.org/brianereynolds/atm-machine) CI server. Travis will also build a docker image and push to [hub.docker.com](https://hub.docker.com).

### Other Considerations
