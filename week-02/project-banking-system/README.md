# 🏦 Banking System (Java)

A mini banking system built as a personal learning project to consolidate Object-Oriented Programming (OOP), clean code principles, and enterprise Java architecture.

## 🚧 Status: Work in Progress (WIP)
This repository follows an incremental development approach. I am currently building the **Domain Model layer**, so some services and integrations are still under active development.

## 🛠️ Technologies & Core Concepts
- **Language:** Java
- **Design:** OOP, Encapsulation, and Domain-Driven Design basics.
- **Best Practices:** Defensive programming (`Objects.requireNonNull`), fail-fast validation, and strict use of `BigDecimal` for monetary accuracy.
- **Error Handling:** Custom exception hierarchy to separate business rules from system failures.

## 🚀 Roadmap & Progress
- [x] **Domain Models (Core):** Abstract Account, Transaction record, and Enums with strict state validation.
- [ ] **Customer Model:** Identity data encapsulation.
- [ ] **Custom Exceptions:** Implementation of domain-specific errors.
- [ ] **Repository Layer:** Data persistence abstraction.
- [ ] **Service Layer:** Orchestrating deposits, withdrawals, and transfers.
- [ ] **Console Application:** User interaction menu.
- [ ] **Unit Tests:** Ensuring business logic reliability.

## 📂 Project Structure
```text
project-banking-system/
├── exception/    # Custom business exceptions
├── model/        # Domain entities (Account, Transaction, Customer)
├── repository/   # Data storage interfaces and implementations
├── service/      # Business logic orchestration
├── util/         # Shared helpers
├── Main.java     # Application entry point
└── README.md