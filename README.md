# Spring Boot Demo Project

## Introduction
The Spring Boot Demo Project is a guiding application designed to facilitate the learning of Spring Boot, a powerful Java application development framework. Spring Boot simplifies the creation of enterprise applications by providing predefined configurations and an easy-to-follow project structure.

This example project uses Spring Boot 3.1.4, a stable and reliable version of Spring Boot at the time of its development. Additionally, it employs additional technologies and tools such as Maven for dependency management and JAR packaging, and Java 17 as the primary programming language.

For the development of this application, it is recommended to use the IntelliJ IDEA development environment, known for its extensive set of features and ease of use for Java application development. It is also suggested to install some plugins, including Docker for container management, JPA Buddy to enhance the development experience with JPA, and Database Navigator for exploring and managing databases directly from the IDE.


## Software Used

In this project, the following technologies are employed:

Spring Boot 3.1.4: Spring Boot is a Java application development framework that simplifies the creation of enterprise applications. Version 3.1.4 is the one used in this project.

Maven: Maven is a dependency management and project building tool. It is used to manage project dependencies and package the application into JAR format.

JAR Packaging: JAR packaging is a common way to distribute Java applications. In this project, JAR packaging is utilized to package the application and make it easily executable.

Java 17: Java 17 is the version of Java used in this project. Java is a widely used programming language for application development.

## Running the Project in IntelliJ

Follow these steps to run the EjemplospringbootApplication project in IntelliJ:

### 1. Instalar IntelliJ IDEA

1. Open IntelliJ IDEA and select "Open" on the home screen.
2. Navigate to the downloaded project folder and click "Open" to open it in IntelliJ.
3. Wait for IntelliJ to import and configure the project.
4. Once the project has been successfully loaded, locate the SpringbootApplication class.
5. Right-click on the SpringbootApplication class and select "Run 'SpringbootApplication'" to execute the application.
6. IntelliJ will compile and build the project, and the application will start.
7. Make sure to configure the necessary environment variables with the database details, as described earlier.
8. Once the application has started successfully, you can access it through a web browser at http://localhost:8080 (or the specified port in the configuration).

Enjoy exploring and testing the project in IntelliJ!


# Principles of Spring Boot and Java Programming Applied in the Application

In this application, several principles of Spring Boot and Java programming have been applied to achieve a robust architecture and clean, maintainable code. Below are some key principles that have been utilized in the creation of the mentioned interfaces and abstract classes.

## Inversion of Control (IoC)
Inversion of Control is a fundamental principle in Spring Boot that enables decoupling dependencies and shifting the responsibility of object creation and management to an IoC container, such as the Spring container. In the application, this principle has been applied by using annotations like `@Autowired` to inject dependencies, such as repositories, into the corresponding classes. This allows the Spring container to handle the creation and management of the necessary dependency instances.

## Dependency Injection (DI)

Dependency Injection is a pattern used to provide dependencies to an object rather than having the object create or search for them itself. In the application, Dependency Injection has been employed in various classes. By injecting dependencies, such as services and repositories, through constructors or methods annotated with `@Autowired`, a low coupling between classes is achieved. This approach facilitates testing and the interchangeability of implementations.

## Abstraction Principle

The Abstraction Principle is a common practice in object-oriented programming that aims to abstract the internal details of an implementation and provide a generic and reusable interface. In the application, this principle has been applied. These interfaces define generic methods for performing CRUD operations on entities. By adhering to these interfaces, code reuse is facilitated in different parts of the application without the need to know the specific implementation details.


## Separation of Concerns

The Separation of Concerns is an essential principle for maintaining clean and maintainable code. In the application, efforts have been made to separate responsibilities by creating abstract classes. These classes provide a base implementation for common functionalities. Abstract classes encapsulate data access logic and CRUD operations, allowing specialized concrete classes to focus on specific aspects of the application.

## Inheritance and Polymorphism

The use of inheritance and polymorphism is a key principle in object-oriented programming. In the application, inheritance and polymorphism are employed by creating abstract classes. These abstract classes define common methods and provide a base structure for concrete classes to inherit from and extend their functionality. This promotes code reuse and consistency in the implementation of concrete classes.

## SOLID Design Principles

Some principles of the SOLID design have been applied in the creation of interfaces and abstract classes. Among them:

#### Single Responsibility Principle (SRP)

Each class has a single responsibility and a single reason to change. Interfaces are responsible for defining generic CRUD operations, while concrete classes handle specific implementations.

#### Open/Closed Principle (OCP)

Interfaces and abstract classes are designed to be open for extension and closed for modification. They can be extended by concrete classes to add additional functionality without modifying existing code.

# Error Handling in the Application

In the application, a custom error handling mechanism has been implemented to provide an appropriate response to exceptions that may occur during execution. This is achieved through the use of the classes `CustomExceptionHandler` and `ErrorController`.

## CustomExceptionHandler

`CustomExceptionHandler` is a class annotated with `@ControllerAdvice`, allowing it to handle specific exceptions and provide a custom response. The implemented exception handling methods are:

- `handleEntityNotFoundException`: Handles the `EntityNotFoundException` exception and adds necessary attributes to the model to display a descriptive error message.

- `handleMiEntidadNoEncontradaException`: Handles the `MiEntidadNoEncontradaException` exception and adds necessary attributes to the model to display a specific error message.

- `handleParametrosIncorrectosException`: Handles the `ParametrosIncorrectosException` exception and adds necessary attributes to the model to display a message indicating that the parameters are incorrect.

- `handleException`: Handles uncontrolled exceptions and adds necessary attributes to the model to display a generic error message.

Additionally, the class contains the inner class `ErrorResponse`, used to represent the error response. This class has attributes for the error message, descriptive message, and the cause of the exception.

## ErrorController

`ErrorHandlerController` implements the Spring Boot `ErrorController` interface and handles errors generated during application execution. The `handleError` method processes exceptions and displays a custom error page.

In case of a `TemplateInputException`, necessary logic is added to handle it, such as adding attributes to the model to display a specific error message related to template loading.

For any other unhandled exception, a generic response is provided with a descriptive error message.

These classes and their error handling contribute to the architecture of the Spring Boot application, adhering to the principles of Inversion of Control (IoC) and the Abstraction Principle. Additionally, the `@ControllerAdvice` annotation is utilized to centralize exception handling, resulting in cleaner and more


# Repository Query Methods in JPA Spring Hibernate

Repository Query Methods are a feature provided by Spring Hibernate to simplify the creation of database queries using repository methods. These methods allow for the declarative and concise definition of queries without the need to write complete SQL queries.

## Language and Structure of Repository Query Methods

Repository Query Methods use a query language based on the names of repository methods. The structure of a Repository Query Method consists of three main parts:

1. Method Prefix: The method prefix describes the operation to be performed on the database. Some common examples of prefixes are:

    - `findBy`: retrieves a list of entities that match the search criteria.
    - `getBy`: retrieves a single entity that matches the search criteria.
    - `countBy`: counts the number of entities that match the search criteria.

2. Properties and Conditions: Following the method prefix, entity properties and search conditions are specified. This is done using a syntax that combines the name of the entity property and a conditional operator. For example, `findByFirstName(String firstName)` will search for entities with the `firstName` field equal to the provided value.

3. Data Return: The method signature also indicates the type of data that will be returned. It can be a list of entities, a single entity, an integer (for counting operations), or other data types as needed.


# Implementation of Security in the Project with Spring Security

In this project, we have implemented security using Spring Security to protect our resources and control user access. Below is a detailed explanation of the procedure and the classes involved in the security implementation.

## Security Configuration

The main class responsible for security configuration is `SecurityConfig`. This class is annotated with `@Configuration` and `@EnableWebSecurity`, indicating that it is a Spring configuration class enabling Spring Security's web security functionality.

The `SecurityConfig` class defines a bean named `securityFilterChain`, which is an object of type `SecurityFilterChain`. This bean is used to configure Spring Security behavior and define authorization rules for incoming requests. The `HttpSecurity` object is used to configure the authorization filter chain.

Inside the `securityFilterChain` method, authorization rules are defined using the `authorizeHttpRequests` method of the `HttpSecurity` object. The following rules are set:

- Access is allowed to the root of the site and the home page ("/", "") for everyone (`permitAll()`).
- Access is allowed to error pages ("/error/**") for everyone (`permitAll()`).
- Authentication is required for any other request (`anyRequest().authenticated()`).

Additionally, the custom login page is configured using the `formLogin` method of the `HttpSecurity` object. The login page ("/login"), the URL where the login request will be automatically processed ("/login"), the default URL after successful login ("/"), and access to everyone (`permitAll()`) are specified.

Furthermore, the application's logout system is configured using the `logout` method of the `HttpSecurity` object. Access is allowed to everyone (`permitAll()`).

## Password Encoding

The `SecurityConfig` class defines a bean named `passwordEncoder`, which uses `BCryptPasswordEncoder` to encode user passwords. This bean is used to encode and verify passwords stored in the database. It is recommended to use a secure encoding algorithm like bcrypt to store passwords securely.

## Conclusions

In summary, to implement security in our project with Spring Security, we configured the `SecurityConfig` class to define authorization rules and the custom login page.

Additionally, we used `BCryptPasswordEncoder` to encode user passwords, ensuring their security.

This implementation allows us to protect our resources, control user access, and manage authentication and authorization in our project using Spring Security.
