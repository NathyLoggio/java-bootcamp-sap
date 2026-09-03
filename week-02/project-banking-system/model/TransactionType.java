package model;

/**
 * My Objective: Define the supported transaction categories in my banking system securely.
 *
 * Concepts I am exploring:
 * - Using Enums to enforce type safety at compile time.
 * - Avoiding fragile String values (like "deposit" vs "DEPOSITT") that cause runtime bugs.
 *
 * My step-by-step logic:
 * 1. I use an enum because transaction types represent a finite, fixed set of known values.
 * 2. This guarantees that any method accepting a TransactionType will only receive valid inputs, 
 *    protecting the core domain from invalid data.
 *
 * SAP enterprise use case:
 * Financial transactions strictly rely on standardized, immutable types (e.g., DEPOSIT, WITHDRAWAL, TRANSFER) 
 * to trigger specific business logic, routing rules, and audit trail processes without ambiguity.
 */
public enum TransactionType {
    DEPOSIT,
    WITHDRAWAL,
    TRANSFER
}