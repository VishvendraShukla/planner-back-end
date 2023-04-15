# PLANNER Backend - Spring Boot with MySQL Database.

This is a simple backend application for managing TODOs using Spring Boot and MySQL database. It
provides APIs for creating, retrieving, updating, and deleting TODOs. It can also support the
administration of users using admin panel(under progress).

## Prerequisites

To run this application, you need to have the following installed in your development environment:

* Java Development Kit (JDK) 1.8 or higher
* Spring Boot 2.5.4 or higher
* MySQL database 5.7 or higher

## Getting Started

Follow the steps below to set up and run the TODO backend:

* Clone the repository to your local machine.

* Create a MySQL database and configure the database connection properties in
  the `application.properties` file, which is located in the src/main/resources folder. Update
  the `spring.datasource.url`, `spring.datasource.username`, and `spring.datasource.password`
  properties with your MySQL database URL, username, and password respectively.

* Run the application using an IDE or by executing the following command in the terminal from the
  root directory of the project:
  ./mvnw spring-boot:run The application will start running on http://localhost:8080 by default.

## API Documentation

- ### Authentication
    - #### Verify Username
        - URL: `/access/generate`
        - Method: POST
        - Request Body: JSON representing the object with the following properties:
            - username: username of the user (String, required)
        - Response: JSON representing the success message `Verified` and `token` with unique
          identifier in response headers.
    - #### Generate Bearer Token
        - URL: `/access/generate/token`
        - Method: POST
        - Request Body: JSON representing the object with the following properties:
            - username: username of the user (String, required)
            - password: password of the user (String, required)
            - token: token received from verify username API response headers (String, required)
        - Response: JSON representing the success message `Authenticated` and `Authorization` with
          bearer token in response headers.
    - #### Sign Up
        - URL: `/access/sign-up`
        - Method: POST
        - Request Body: JSON representing user details.
        - Response: JSON representing the success message `Successfully signed up!`.

### Note: All APIs now require Bearer token for authentication.

- ### Access
    - #### Login
        - URL: `/access/login`
        - Method: POST
        - Request Body: JSON representing the object with the following properties:
            - username: username of the user (String, required)
            - password: password of the user (String, required)
        - Response: JSON representing the user details.
- ### Create TODO Category
    - #### Task Category
        - URL: `/task-category`
        - Method: POST
        - Request Body:
            - categoryType: type of category (String, required)
            - String categoryName: name of category (String, optional)
            - taskVOList: tasks associated with category (List<TaskVO>, optional)
        - Response: JSON representing the success message `Task Category Created Successfully`.

# Contact

If you have any questions or suggestions, please feel free to contact the author:

- Name: Vishvendra Vijay Shukla
- Email: shukla.vishvendra14@gmail.com



