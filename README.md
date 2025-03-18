# Customer Management API

## Description
Spring Boot RESTful APIs to manage customer data. This API supports basic CRUD operations and includes logic to calculate a customer’s membership tier based on their annual spend.

## Getting Started
1. Clone the repository.
2. Build the project using Maven: `./mvnw clean install`
3. Run the application: `./mvnw spring-boot:run`

## Technologies Used
- Spring Boot
- Java
- Maven
- H2 (in memory database)


## Available endpoints:-


##### 1. Create a new customer:-

## POST /customers

##### Sample request:

http://localhost:8080/customers

{
  "name": "Sumit",
  "email": "Sumit@gmail.com",
  "annualSpend": 100,
  "lastPurchaseDate": "2025-01-20T18:30:00"
}

##### 2. Retrieve a customer by their unique ID:-

## GET /customers/{id}

##### Sample request:

http://localhost:8080/customers/8210b723-44ac-4b17-9360-69fbb34e75ba


##### 3. Retrieve a customer by name:-

## GET /customers?name={name}

##### Sample request:

http://localhost:8080/customers?name=Sumit


##### 4. Retrieve a customer by email:-

## GET /customers?email={email}

##### Sample request:

http://localhost:8080/customers?email=Sumit@gmail.com

##### 5. Update customer details by ID:-

## PUT /customers/{id}

##### Sample request:

http://localhost:8080/customers/7603c8db-2e0b-46b9-8009-f0559152b83a

{  
  "name": "sumitra",
  "email": "sumit@tcs.com",
  "annualSpend": 67876,
  "lastPurchaseDate": "2025-01-20T18:30:00"
  }


##### 6. Delete a customer by ID:-

## DELETE /customers/{id}

##### Sample request:

http://localhost:8080/customers/b1cd77c3-22a2-4e21-9077-f69001fa82f6


## To access the H2 database console:-

http://localhost:8080/h2-console/

jdbc URL: jdbc:h2:mem:devdb

_Check username and password from application.properties_

## To access the OpenAPI specification:-

http://localhost:8080/api-docs

## To access swagger ui:-

http://localhost:8080/swagger-ui/index.html

## Docker deployment:-

https://hub.docker.com/repository/docker/sumitmitra/customermanagement-rest-api/general

##### docker commands:

docker pull sumitmitra/customermanagement-rest-api:dockerfile1

docker run -d -p 8080:8080 sumitmitra/customermanagement-rest-api:dockerfile1

