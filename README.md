# Medical Appointments Backend

This is the backend service for the Medical Appointments application. It provides APIs for managing medical appointments, doctors, clients, and other related entities.

## Table of Contents

- [Project Description](#project-description)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup Instructions](#setup-instructions)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

## Project Description

The Medical Appointments Backend is a Spring Boot application that serves as the backend for managing medical appointments. It includes functionalities for user authentication, doctor scheduling, client management, and more.

## Features

- User authentication and authorization using JWT.
- CRUD operations for doctors, clients, and appointments.
- Scheduling and managing doctor appointments.
- Integration with Swagger for API documentation.

## Technologies Used

- Java
- Spring Boot
- Spring Security
- JPA/Hibernate
- Swagger/OpenAPI

## Setup Instructions

### Prerequisites

- Java 17 or higher
- Maven
- PostgreSQL (or any other preferred database)

### Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/konradcz2001/medical-appointments-backend.git
    cd medical-appointments-backend
    ```

2. Configure the database:
    - Update the `application.properties` file with your database configuration.

3. Build the project:
    ```bash
    mvn clean install
    ```

4. Run the application:
    ```bash
    mvn spring-boot:run
    ```

## Usage

Once the application is running, you can access the APIs at `http://localhost:8080`.

## API Documentation

The API documentation is available via Swagger. You can access it at `http://localhost:8080/swagger-ui.html`.

`Source: src/main/java/com/github/konradcz2001/medicalappointments/swagger/OpenApiConfig.java`

## Testing

To run the tests, use the following command:
```bash
mvn test
```
Source: src/test/java/com/github/konradcz2001/medicalappointments/MedicalAppointmentsApplicationTests.java

## Contributing
Contributions are welcome! Please fork the repository and create a pull request with your changes.

## License
This project is licensed under the MIT License.



This README file provides a clear and structured overview of the project, including setup instructions, usage, and other relevant information. It references the provided code and documentation where applicable.