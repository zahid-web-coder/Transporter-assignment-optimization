🚛 Transporter Assignment Optimization System

A Spring Boot application that optimizes transporter assignments to lanes across India based on:

✅ Cost Minimization

✅ Maximum Transporter Utilization (up to a limit)

✅ Full Lane Coverage

📌 Problem Statement

Given:

A list of lanes (origin → destination)

A list of transporters with quoted prices per lane

A maximum number of transporters allowed

The system must:

Minimize total transportation cost

Maximize transporter usage (up to the specified limit)

Ensure every lane is assigned to at least one transporter

🛠 Tech Stack

Java 17

Spring Boot 3.5.11

Spring Data JPA (Hibernate)

PostgreSQL

Maven

JUnit 5 (Unit Testing)

Swagger / OpenAPI

🏗 Architecture

Layered clean structure:

controller
service
repository
model
dto
algorithm
exception
config

Controller → Handles API requests

Service → Business logic

Repository → DB interaction

Algorithm → Optimization engine

DTO → Request/Response objects

🚀 APIs
1️⃣ Submit Input Data

POST
/api/v1/transporters/input

Request Body
{
  "lanes": [
    { "id": 1, "origin": "Mumbai", "destination": "Delhi" }
  ],
  "transporters": [
    {
      "id": 1,
      "name": "Transporter T1",
      "laneQuotes": [
        { "laneId": 1, "quote": 5000 }
      ]
    }
  ]
}
Response
{
  "status": "success",
  "message": "Input data saved successfully."
}
2️⃣ Get Optimized Assignment

POST
/api/v1/transporters/assignment

Request Body
{
  "maxTransporters": 3
}
Response Example
{
  "status": "success",
  "totalCost": 60139,
  "assignments": [
    { "laneId": 1, "transporterId": 5 }
  ],
  "selectedTransporters": [1, 4, 5]
}
🧠 Algorithm Approach

The optimizer:

Generates all transporter combinations up to maxTransporters

For each combination:

Validates full lane coverage

Selects minimum quote per lane

Computes total cost

Chooses the best combination based on:

Lowest total cost

If tie → maximum transporter usage

Time complexity is acceptable for moderate input sizes and aligns with assignment scope.

🧪 Unit Testing

Implemented using JUnit 5.

Tests cover:

Cost minimization correctness

Tie-break logic (maximize transporter usage)

Failure case when coverage not possible

Run tests:

./mvnw test

All tests passing ✔

🗄 Database Configuration

Configured for PostgreSQL:

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/freightfox
    username: postgres
    password: postgres

Tables are auto-created using JPA (ddl-auto: update).

▶ Running the Application
1️⃣ Start PostgreSQL

Ensure database exists:

CREATE DATABASE freightfox;
2️⃣ Run Application
./mvnw spring-boot:run
3️⃣ Open Swagger
http://localhost:8080/swagger-ui.html
🧹 Edge Case Handling

The system handles:

No lanes available

No transporters available

maxTransporters <= 0

Impossible coverage scenario

Returns structured failure response:

{
  "status": "failed",
  "totalCost": 0,
  "assignments": [],
  "selectedTransporters": []
}
📂 Submission Notes

Clean layered architecture

Unit tested algorithm

Proper validation and error handling

Swagger documentation enabled

Production-ready structure for 0–2 YOE role

✅ Assignment Objectives Covered

Requirement	Status
Cost Minimization	✔
Maximize Transporter Usage	✔
Full Lane Coverage	✔
Input API	✔
Result API	✔
Unit Tests	✔
🎯 Final Status

Project is fully functional, tested, and submission-ready.