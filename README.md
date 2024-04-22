# **Basic Sales Tax Calculator**

This application calculates the receipt details for shopping baskets based on specified rules for sales tax and import duty.

## **Overview**

Basic sales tax is applicable at a rate of 10% on all goods, except books, food, and medical products that are exempt. Import duty is an additional sales tax applicable on all imported goods at a rate of 5%, with no exemptions.

When a user purchases items, they receive a receipt listing the name of each item, its price (including tax), the total cost of the items, and the total amount of sales taxes paid. The rounding rules for sales tax are applied according to specific guidelines.

This application has been implemented using Spring Boot and Gradle. The application was coded using the Intelij text editor.

## **Prerequisites**

- Java 17

## **Running the Application in local**

1. Clone this repository.
2. Navigate to the project directory.
3. Run the following command to start the application:

```bash
./gradlew bootRun

```

1. The application will start on port 8080.
2. Access the Swagger UI at http://localhost:8080/ to explore the available endpoint.

## **Running the Application with Docker**

To run the application in a Docker container:

1. Ensure Docker is installed on your system.
2. Execute the **`start.sh`** script:

```bash
./start.sh

```

1. To stop and remove the Docker container, execute the **`stop.sh`** script:

```bash
./stop.sh

```

## **Testing**

This application is thoroughly tested using both unit tests and integration tests. The integration tests verify the correct functionality of the application using example inputs.

1. To run all tests, execute the following command:

```bash
./gradlew test

```

A Postman collection is also provided to test the application with the three example inputs. In case of errors, refer to the error message for troubleshooting. This collection can be imported into Postman for testing with the file "Sales_Taxes_Collection.postman_collection.json" in the project root.
