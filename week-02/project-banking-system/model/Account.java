package model;

import exceptions.InsufficientFundsException;
import exceptions.IvalidTransactionException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class Account {
    private final String accountNumber;
    private final Customer customer;
    private final List<Transaction> transactions = new ArrayList<>();
    private BigDecimal balance;
    private boolean active = true;

    /**
     * Creates an account with validated state.
     *
     * @param accountNumber unique account number
     * @param customer owner of the account
     * @param openingBalance initial balance
     * @throws InvalidTransactionException when opening balance is invalid
     */

      protected Account(String accountNumber, Customer customer, BigDecimal openingBalance)
            throws InvalidTransactionException {
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("accountNumber must not be blank");
        }
        this.accountNumber = accountNumber;
        this.customer = Objects.requireNonNull(customer, "customer must not be null");
        validateNonNegative(openingBalance, "openingBalance");
        this.balance = openingBalance;
    }

     /**
     * Deposits money into this account.
     *
     * @param amount amount to deposit
     * @throws InvalidTransactionException when account is closed or amount is invalid
     */
    public void deposit(BigDecimal amount) throws InvalidTransactionException {
        ensureActive();
        validatePositive(amount, "amount");
        balance = balance.add(amount);
    }

     /**
     * Withdraws money from this account.
     *
     * @param amount amount to withdraw
     * @throws InvalidTransactionException when account is closed or amount is invalid
     * @throws InsufficientFundsException when balance is not enough
     */
    public void withdraw(BigDecimal amount) throws InvalidTransactionException, InsufficientFundsException {
        ensureActive();
        validatePositive(amount, "amount");
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientFundsException("Account " + accountNumber + " has insufficient funds.");
        }
        balance = balance.subtract(amount);
    }

    /**
     * Adds a transaction to the account history.
     *
     * @param transaction transaction to add
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(Objects.requireNonNull(transaction, "transaction must not be null"));
    }

    /**
     * Closes this account.
     */
    public void close() {
        active = false;
    }

    /**
     * Returns the account type.
     *
     * @return account type
     */
    public abstract String getAccountType();

    /**
     * Returns the account number.
     *
     * @return account number
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Returns the customer.
     *
     * @return customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Returns current balance.
     *
     * @return account balance
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Returns whether the account is active.
     *
     * @return true when active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Returns an immutable view of transaction history.
     *
     * @return transaction history
     */
    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    private void ensureActive() throws InvalidTransactionException {
        if (!active) {
            throw new InvalidTransactionException("Account " + accountNumber + " is closed.");
        }
    }

    private static void validatePositive(BigDecimal amount, String fieldName) throws InvalidTransactionException {
        validateNonNegative(amount, fieldName);
        if (amount.signum() == 0) {
            throw new InvalidTransactionException(fieldName + " must be greater than zero.");
        }
    }

    private static void validateNonNegative(BigDecimal amount, String fieldName) throws InvalidTransactionException {
        if (amount == null || amount.signum() < 0) {
            throw new InvalidTransactionException(fieldName + " must not be null or negative.");
        }
    }
}