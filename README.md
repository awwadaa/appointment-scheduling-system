# Appointment Scheduling System

## Project Overview
The Appointment Scheduling System is a Java-based application developed as part of the Software Engineering course.

It allows users to:
- View available appointment slots
- Book appointments
- Modify or cancel future appointments
- Receive reminders

The system also allows administrators to manage the scheduling process through a command-line interface (CLI).

---

## Student Name
Awwad Awwad

---

## Features
- Administrator login and logout
- View available appointment slots
- Book an appointment
- Enforce appointment duration rules
- Enforce participant capacity rules
- Modify future appointments only
- Cancel future appointments only
- Send appointment reminders
- Support multiple appointment types (individual / group)
- Apply validation rules during booking

---

## Design Patterns Used

### Strategy Pattern
Used to apply different validation rules:
- DurationRule
- CapacityRule
- AppointmentTypeRule

### Observer Pattern
Used to send notifications:
- EmailNotificationObserver
- SMSNotificationObserver

---

## Project Structure
src/main/java/com/appointment
├── application
├── domain/entities
├── domain/enums
├── domain/valueobjects
├── observer
├── persistence
├── presentation
└── strategy

---

## Technologies Used
- Java
- Maven
- JUnit 5
- Mockito
- JaCoCo

---

## How to Run the Project
1. Open the project in Eclipse or any Java IDE.
2. Make sure Maven dependencies are installed.
3. Run the main class:

com.appointment.presentation.MainApp

4. Use the console menu to interact with the system.

---

## Default Administrator Credentials
Username: admin  
Password: admin123  

---

## How to Run Tests
Run:
mvn test

Or from Eclipse:
- Right click project → Run As → Maven test

---

## Test Coverage
The project achieves high test coverage:
93%

---

## Documentation
The project includes documentation (Javadoc) for service classes such as:
- AppointmentService
- AuthService
- ScheduleService
- ReminderService

---

## Notes
- The system uses a Command Line Interface (CLI)
- Data is stored in memory
- This project fulfills Phase 1 requirements