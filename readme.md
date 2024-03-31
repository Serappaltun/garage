# Automated Ticketing System for Garage

This project implements an automated ticketing system for a garage that can accommodate up to 10 slots. The system allows customers to park their vehicles without human intervention and provides functionalities for parking, leaving, and checking the status of the garage.

## Technologies Used
- Java 17
- Spring Boot
- JUnit (for unit testing)

## Project Structure
The project consists of the following main components:

- **Garage**: Represents the garage with parking slots and provides methods for parking, leaving, and checking the status.
- **GarageService**: Acts as a service layer to handle requests from the REST controller and interact with the Garage class.
- **GarageController**: Exposes REST endpoints for parking, leaving, and checking the status of the garage.
- **Ticket**: Represents a parking ticket with information about the vehicle and the allocated slot.
- **GarageSlot**: Represents a parking slot in the garage with information about its availability and the type of vehicle parked (if any).

## Usage
To use the automated ticketing system, follow these steps:

1. Run the Spring Boot application.
2. Use the provided REST endpoints to interact with the system:
   - `/garage/park`: POST endpoint to park a vehicle. Requires parameters `plate`, `color`, and `type`.
   - `/garage/leave`: DELETE endpoint to leave the garage. Requires parameter `ticketNo`.
   - `/garage/status`: GET endpoint to check the current status of the garage.
3. The system will allocate parking slots automatically and provide parking tickets to the customers.
4. Customers can leave the garage by providing their parking ticket, which marks the corresponding slots as available again.
5. There is postman collection in the project; GarageApi.postman_collection.json

## Unit Testing
The project includes unit tests for the GarageService class using JUnit. These tests ensure the correctness of the parking, leaving, and status functionalities.

