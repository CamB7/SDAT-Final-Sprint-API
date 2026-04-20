# SkyBeacon API

A Spring Boot REST API backend for a flight dashboard and admin interface. It serves flight statuses, gate assignments, airports, aircraft, and handles basic admin authentication for a React frontend.

## Tech Stack
* **Java & Spring Boot:** REST controllers, JPA/Hibernate
* **Database:** MySQL
* **Testing:** JUnit + Mockito
* **Packaging:** Maven (jar) & Docker 

## Core Endpoints
*Base URL: `http://localhost:8080`*

### Flights
* `GET`, `POST`, `PUT`, `DELETE` at `/flights`
* `POST /flights/{id}/assign-departure-gate/{gateId}` (and arrival-gate)
* `PUT /flights/{id}/status?status={status}`
* `GET /airports/{id}/arrivals/at-gate` (and departures, in-flight)

### Infrastructure
* **Airports:** `GET`, `POST`, `PUT`, `DELETE` at `/airports`
* **Gates:** `GET`, `PUT` at `/gates` (and `/airports/{id}/gates`)
* **Aircraft:** `GET`, `POST`, `PUT`, `DELETE` at `/aircrafts`

### Authentication
* `POST /auth/login` (Body: `{ "username":"...", "password":"..." }`)
* `GET /auth/me` | `GET /auth/adminConfirmation` | `POST /auth/logout`
*(Credentials defined via application.properties or ENV vars).*

## Database & Data
The project uses **MySQL**. Connection info is configured in `src/main/resources/application.properties`.

To seed your database, run the SQL script located at `src/main/resources/data.sql`. It includes ready-to-use airports, gates, aircraft, flights, and associations.

## Running Locally

**1. Build the app:**

Clone the repository and run the RestServiceApplication with mySQL open and a schema name S4FinalSprintAPI.
Use the data.sql file in the project resources folder for database inserts.

***Docker Note:*** If running via Docker, make sure to build the .jar locally first (mvn package) so the Dockerfile can locate the artifact in the target/ directory. 
