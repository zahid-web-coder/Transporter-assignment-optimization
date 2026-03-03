# 🚛 Transporter Assignment Optimization System

A Spring Boot application that optimizes transporter assignments to lanes across India based on:

- ✅ Cost Minimization  
- ✅ Maximum Transporter Utilization (up to a limit)  
- ✅ Full Lane Coverage  

---

## 📌 Problem Statement

Given:

- A list of lanes (origin → destination)  
- A list of transporters with quoted prices per lane  
- A maximum number of transporters allowed  

The system must:

1. Minimize total transportation cost  
2. Maximize transporter usage (up to the specified limit)  
3. Ensure every lane is assigned to at least one transporter  

---

## 🛠 Tech Stack

- Java 17  
- Spring Boot 3.5.11  
- Spring Data JPA (Hibernate)  
- PostgreSQL  
- Maven  
- JUnit 5 (Unit Testing)  
- Swagger / OpenAPI  

---

## 🏗 Architecture

Layered clean structure:

```
controller
service
repository
model
dto
algorithm
exception
config
```

**Responsibilities:**

- Controller → Handles API requests  
- Service → Business logic  
- Repository → Database interaction  
- Algorithm → Optimization engine  
- DTO → Request/Response objects  

---

## 🚀 APIs

### 1️⃣ Submit Input Data

**POST**  
`/api/v1/transporters/input`

#### Request Body

```json
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
```

#### Response

```json
{
  "status": "success",
  "message": "Input data saved successfully."
}
```

---

### 2️⃣ Get Optimized Assignment

**POST**  
`/api/v1/transporters/assignment`

#### Request Body

```json
{
  "maxTransporters": 3
}
```

#### Response Example

```json
{
  "status": "success",
  "totalCost": 60139,
  "assignments": [
    { "laneId": 1, "transporterId": 5 }
  ],
  "selectedTransporters": [1, 4, 5]
}
```

---

## 🧠 Algorithm Approach

The optimizer:

1. Generates all transporter combinations up to `maxTransporters`
2. For each combination:
   - Validates full lane coverage
   - Selects minimum quote per lane
   - Computes total cost
3. Chooses the best combination based on:
   - Lowest total cost  
   - If tie → Maximum transporter usage  

This ensures:

- ✔ Global cost minimization  
- ✔ Constraint satisfaction  
- ✔ Deterministic tie-breaking  

Time complexity is acceptable for moderate assignment-scale inputs.

---

## 🧪 Unit Testing

Implemented using **JUnit 5**.

Tests cover:

- Cost minimization correctness  
- Tie-break logic (maximize transporter usage)  
- Failure case when coverage not possible  

### Run Tests

```bash
./mvnw test
```

All tests passing ✔

---

## 🗄 Database Configuration

Configured for PostgreSQL:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/freightfox
    username: postgres
    password: postgres
```

Tables are auto-created using:

```
spring.jpa.hibernate.ddl-auto=update
```

---

## ▶ Running the Application

### 1️⃣ Create Database

```sql
CREATE DATABASE freightfox;
```

### 2️⃣ Run Application

```bash
./mvnw spring-boot:run
```

### 3️⃣ Open Swagger UI

```
http://localhost:8080/swagger-ui.html
```

---

## 🧹 Edge Case Handling

The system safely handles:

- No lanes available  
- No transporters available  
- `maxTransporters <= 0`  
- Impossible coverage scenarios  

Example failure response:

```json
{
  "status": "failed",
  "totalCost": 0,
  "assignments": [],
  "selectedTransporters": []
}
```

---

## 📂 Submission Highlights

- Clean layered architecture  
- Well-structured DTO design  
- Combination-based optimization engine  
- Deterministic tie-break logic  
- Proper validation and exception handling  
- Fully unit-tested algorithm  
- Swagger API documentation  

---

## ✅ Assignment Objectives Covered

| Requirement | Status |
|------------|--------|
| Cost Minimization | ✔ |
| Maximize Transporter Usage | ✔ |
| Full Lane Coverage | ✔ |
| Input API | ✔ |
| Result API | ✔ |
| Unit Tests | ✔ |

---

## 🎯 Final Status

The project is fully functional, tested, and submission-ready.
