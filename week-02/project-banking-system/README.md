# Banking System (Java)

A mini banking system developed in Java as a learning project.

The goal of this project is to practice Object-Oriented Programming (OOP), exception handling, collections, clean code principles, and software architecture commonly used in enterprise Java applications.🚀

## Current Status

🚧 Work in Progress (WIP)

The project is under active development and is **not yet functional**. 
Some compilation errors are expected because several classes and modules are still being implemented.

## Features Implemented

### Account (Abstract Class)

Current implementation includes:

- Account validation during creation
- Account activation status
- Deposit operation
- Withdraw operation
- Balance validation
- Transaction history support
- Immutable transaction list exposure
- Account closing functionality
- Custom validation methods
- Use of `BigDecimal` for monetary values
- Defensive programming using `Objects.requireNonNull()`

## Planned Features

- Customer model
- Transaction model
- Savings Account
- Checking Account
- Bank Repository
- Bank Service
- Money transfer between accounts
- Account search
- Custom exception hierarchy
- Console menu
- Unit tests

## Progress

- [x] Project structure
- [x] Abstract Account model
- [ ] Customer model
- [ ] Transaction model
- [ ] Custom exceptions
- [ ] Repository layer
- [ ] Service layer
- [ ] Console application
- [ ] Unit tests

## Project Structure

```
project-banking-system/
│
├── exception/
├── model/
├── repository/
├── service/
├── util/
├── Main.java
└── README.md
```

## Technologies

- Java
- Object-Oriented Programming (OOP)
- Collections Framework
- BigDecimal
- Exception Handling

## Development Notes

This repository follows an incremental development approach.
Each layer is implemented separately:

1. Model ✅
2. Exceptions
3. Repository
4. Service
5. Main Application

Compilation errors may occur until all dependencies are implemented.

## Author

Nathaly Loggiovini 👩‍💻
Developed as a personal Java learning project.

## 📬 Contact
- **LinkedIn:** [Nathaly Loggiovini] (https://linkedin.com/in/nathaly-loggiovini-b5a160220)