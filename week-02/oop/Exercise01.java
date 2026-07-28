package oop;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * My Objective: Model customers, accounts, and transactions using strict classes and 
 * encapsulation to build a robust foundation.
 *
 * Concepts I am exploring:
 * - Encapsulation and data hiding
 * - Immutability (using Java Records)
 * - Composition ("has-a" relationship)
 * - Using BigDecimal for accurate financial calculations
 *
 * Expected output:
 * Customer: Nathaly Silva
 * Current balance: 250.00
 * Transactions:
 * - DEPOSIT 250.00 | Internship stipend deposit
 *
 * My step-by-step logic:
 * 1. I made Customer an immutable record because customer identity should not change casually.
 * 2. I hid the Account balance (making it private) and validate all balance changes strictly through the deposit() method.
 * 3. I used composition by making Account contain a Customer and a list of Transaction objects.
 * 4. I intentionally used BigDecimal because I know financial systems must avoid floating-point rounding problems.
 *
 * Big-O Complexity (My notes):
 * - deposit(): O(1)
 * - printTransactions(): O(n), where n is the number of transactions
 *
 * SAP enterprise use case:
 * In SAP financial products, business entities are modeled with clear responsibilities. 
 * I am applying this by ensuring Customer owns identity data, Account owns balance rules, 
 * and Transaction represents immutable audit data.
 */
public class Exercise01 {

    /**
     * Runs my beginner OOP account example.
     *
     * @param args command-line arguments (not used in this exercise)
     */
    public static void main(String[] args) {
        Customer customer = new Customer("CUS-1001", "Nathaly Silva");
        Account account = new Account("ACC-1001", customer);

        account.deposit(new BigDecimal("250.00"), "Internship stipend deposit");

        System.out.println("Customer: " + account.getCustomer().name());
        System.out.println("Current balance: " + account.getBalance());
        System.out.println("Transactions:");
        account.printTransactions();
    }

    private static final class Account {
        private final String accountNumber;
        private final Customer customer;
        private final List<Transaction> transactions = new ArrayList<>();
        private BigDecimal balance = BigDecimal.ZERO;

        private Account(String accountNumber, Customer customer) {
            this.accountNumber = requireText(accountNumber, "accountNumber");
            this.customer = Objects.requireNonNull(customer, "customer must not be null");
        }

        private Customer getCustomer() {
            return customer;
        }

        private BigDecimal getBalance() {
            return balance;
        }

        private void deposit(BigDecimal amount, String description) {
            validatePositiveAmount(amount);
            balance = balance.add(amount);
            transactions.add(new Transaction(accountNumber, "DEPOSIT", amount, description, LocalDateTime.now()));
        }

        private void printTransactions() {
            for (Transaction transaction : transactions) {
                System.out.println("- " + transaction.formatForStatement());
            }
        }

        private static void validatePositiveAmount(BigDecimal amount) {
            Objects.requireNonNull(amount, "amount must not be null");
            if (amount.signum() <= 0) {
                throw new IllegalArgumentException("amount must be greater than zero");
            }
        }

        private static String requireText(String value, String fieldName) {
            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException(fieldName + " must not be blank");
            }
            return value;
        }
    }

    private record Customer(String customerId, String name) {
        private Customer {
            if (customerId == null || customerId.isBlank()) {
                throw new IllegalArgumentException("customerId must not be blank");
            }
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("name must not be blank");
            }
        }
    }

    private record Transaction(
            String accountNumber,
            String type,
            BigDecimal amount,
            String description,
            LocalDateTime createdAt) {

        private String formatForStatement() {
            return type + " " + amount + " | " + description;
        }
    }
}