package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * My Objective: Model a financial event as an immutable audit record within an account history.
 *
 * Concepts I am exploring:
 * - Java Records for immutability and data encapsulation.
 * - Static factory methods for expressive object creation.
 * - Strict validation inside the compact constructor (Fail-fast principle).
 * - Separation of concerns (recording an event vs. executing the business logic).
 *
 * My step-by-step logic:
 * 1. I defined this class as a Record to guarantee the transaction is 100% immutable once created.
 * 2. I built static factory methods (deposit, withdrawal, transfer) to clearly express the intent of the transaction.
 * 3. I implemented strict validation in the compact constructor so an invalid state can never be instantiated.
 * 4. I intentionally separated the act of recording a transaction from the account logic. This object only records 
 *    that an operation happened, it does not change balances itself.
 *
 * SAP enterprise use case:
 * In enterprise financial systems, transactions are strictly immutable audit logs. They do not execute 
 * state changes themselves; they serve as historical proof of operations, ensuring compliance and traceability.
 *
 * @param transactionId unique transaction identifier
 * @param type transaction category
 * @param amount transaction amount
 * @param createdAt date and time when the transaction was created
 * @param sourceAccountNumber account where money came from, when applicable
 * @param destinationAccountNumber account where money went, when applicable
 * @param description human-readable explanation for statements and audit logs
 */
public record Transaction(
        String transactionId,
        TransactionType type,
        BigDecimal amount,
        LocalDateTime createdAt,
        String sourceAccountNumber,
        String destinationAccountNumber,
        String description) {

    /**
     * Validates transaction state to prevent invalid objects from existing in memory.
     */
    public Transaction {
        requireText(transactionId, "transactionId");
        Objects.requireNonNull(type, "type must not be null");
        validatePositiveAmount(amount);
        Objects.requireNonNull(createdAt, "createdAt must not be null");
        requireText(description, "description");
        validateAccountNumbers(type, sourceAccountNumber, destinationAccountNumber);
    }

    /**
     * Creates a deposit transaction.
     *
     * @param destinationAccountNumber account receiving the money
     * @param amount deposited amount
     * @param description transaction description
     * @return deposit transaction
     */
    public static Transaction deposit(String destinationAccountNumber, BigDecimal amount, String description) {
        return new Transaction(
                UUID.randomUUID().toString(),
                TransactionType.DEPOSIT,
                amount,
                LocalDateTime.now(),
                null,
                destinationAccountNumber,
                description);
    }

    /**
     * Creates a withdrawal transaction.
     *
     * @param sourceAccountNumber account providing the money
     * @param amount withdrawn amount
     * @param description transaction description
     * @return withdrawal transaction
     */
    public static Transaction withdrawal(String sourceAccountNumber, BigDecimal amount, String description) {
        return new Transaction(
                UUID.randomUUID().toString(),
                TransactionType.WITHDRAWAL,
                amount,
                LocalDateTime.now(),
                sourceAccountNumber,
                null,
                description);
    }

    /**
     * Creates a transfer transaction.
     *
     * @param sourceAccountNumber account providing the money
     * @param destinationAccountNumber account receiving the money
     * @param amount transferred amount
     * @param description transaction description
     * @return transfer transaction
     */
    public static Transaction transfer(
            String sourceAccountNumber,
            String destinationAccountNumber,
            BigDecimal amount,
            String description) {
        return new Transaction(
                UUID.randomUUID().toString(),
                TransactionType.TRANSFER,
                amount,
                LocalDateTime.now(),
                sourceAccountNumber,
                destinationAccountNumber,
                description);
    }

    /**
     * Formats this transaction for console statements.
     *
     * @return formatted statement line
     */
    public String formatForStatement() {
        return createdAt.toLocalDate()
                + " | " + type
                + " | " + amount
                + " | from=" + valueOrDash(sourceAccountNumber)
                + " | to=" + valueOrDash(destinationAccountNumber)
                + " | " + description;
    }

    private static void validatePositiveAmount(BigDecimal amount) {
        Objects.requireNonNull(amount, "amount must not be null");
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
    }

    private static void validateAccountNumbers(
            TransactionType type,
            String sourceAccountNumber,
            String destinationAccountNumber) {
        switch (type) {
            case DEPOSIT -> requireText(destinationAccountNumber, "destinationAccountNumber");
            case WITHDRAWAL -> requireText(sourceAccountNumber, "sourceAccountNumber");
            case TRANSFER -> {
                requireText(sourceAccountNumber, "sourceAccountNumber");
                requireText(destinationAccountNumber, "destinationAccountNumber");
                if (sourceAccountNumber.equals(destinationAccountNumber)) {
                    throw new IllegalArgumentException("source and destination accounts must be different");
                }
            }
        }
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return value;
    }

    private static String valueOrDash(String value) {
        return value == null ? "-" : value;
    }
}