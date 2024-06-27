# Medical Appointments Backend

This is the backend service for the <a href="https://medical-appointments.pl" target="_blank">Medical Appointments</a> application. It provides APIs for managing medical appointments, doctors, clients, and other related entities.

## Table of Contents

- [Project Description](#project-description)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [API Documentation](#api-documentation)
- [Hosting](#hosting)
- [Setup Instructions](#setup-instructions)
- [Contributing](#contributing)
- [License](#license)

## Project Description

The Medical Appointments Backend is a Spring Boot application that serves as the backend for managing medical appointments. It includes functionalities for user authentication, doctor scheduling, client management, and more.

## Features

- User authentication and authorization using JWT.
- CRUD operations for doctors, clients, appointments, and related data.
- Scheduling and managing doctor appointments.
- Integration with Swagger for API documentation.
- Flyway migrations populates the database with generated sample data for presentation purposes.

## Technologies Used

- Java
- Spring Boot
- Spring Security
- JPA/Hibernate
- Swagger/OpenAPI
- Flyway

## API Documentation

The API documentation is available via Swagger. You can access it <a href="https://api.medical-appointments.pl/swagger-ui/index.html" target="_blank">here</a>.
<br>For some operations you must log in and provide a token.

## Hosting
You can access the application via the links:
###### The entire application with frontend:<br> <a href="https://medical-appointments.pl" target="_blank">https://medical-appointments.pl</a>
###### Swagger documentation for backend:<br> <a href="https://api.medical-appointments.pl/swagger-ui/index.html" target="_blank">https://api.medical-appointments.pl/swagger-ui/index.html</a>


## Setup Instructions

### Prerequisites

- Java 17 or higher
- Maven
- PostgreSQL

### Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/konradcz2001/medical-appointments-backend.git
    cd medical-appointments-backend
    ```

2. Add to the `application.properties` file your configuration:<br><br>
    - Database configuration:
      - spring.datasource.url
      - spring.datasource.username
      - spring.datasource.password<br><br>
      
    - Email configuration:
       - spring.mail.password (16-character password generated by Gmail, providing access to the sending email)
       - spring.mail.to.username (target email address to which user questions are to be sent)<br><br>
   
    - Cross Origin configuration:
       - cross.origin.site.url<br><br>

   - JWT configuration:
      - app.jwt.secret.key<br><br>
     
   - Remove `spring.profiles.active`<br><br>

3. Build the project:
    ```bash
    mvn clean install
    ```

4. Run the application:
    ```bash
    mvn spring-boot:run
    ```

### Usage

Once the application is running, you can access the APIs at `http://localhost:8080`.

### Testing

To run the tests, use the following command:
```bash
mvn test
```

## Contributing
Contributions are welcome! Please fork the repository and create a pull request with your changes. You can also contact me: konradcz2001@gmail.com

## License
This project is licensed under the [MIT License](LICENSE).<br><br>


