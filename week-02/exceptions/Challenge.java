package exceptions;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * My Objective: Combine multiple custom exceptions in a realistic account-transfer workflow 
 * to build a resilient and safe banking service.
 *
 * Concepts I am exploring:
 * - Structuring custom checked and unchecked exceptions
 * - Centralized exception handling
 * - Separating infrastructure failures from business failures
 *
 * Expected output:
 * Transfer failed: Account ACC-9999 was not found.
 * Closing audit resources.
 * Alice balance: 500.00
 *
 * My step-by-step logic:
 * 1. I use BankService to store accounts in a HashMap by account number.
 * 2. The createAccount() method validates and rejects duplicate IDs to maintain integrity.
 * 3. The transfer() method strictly validates both source and target accounts before touching balances.
 * 4. I let business exceptions propagate up to main(), where they are caught and handled centrally.
 *
 * Big-O Complexity (My notes):
 * - Finding an account in the HashMap: average O(1)
 * - transfer(): average O(1)
 *
 * SAP enterprise use case:
 * Enterprise financial applications must clearly separate business failures from technical errors. 
 * Creating distinct, business-specific exception names drastically improves log readability, 
 * support ticketing, and API contract clarity.
 */
public class Challenge {

    /**
     * Runs my custom exception transfer challenge.
     *
     * @param args command-line arguments (not used in this exercise)
     */
    public static void main(String[] args) {
        BankService bankService = new BankService();

        try {
            bankService.createAccount("ACC-5001", "Alice", new BigDecimal("500.00"));
            bankService.transfer("ACC-5001", "ACC-9999", new BigDecimal("100.00"));
        } catch (DuplicateAccountException
                 | AccountNotFoundException
                 | InsufficientFundsException
                 | InvalidTransactionException exception) {
            System.out.println("Transfer failed: " + exception.getMessage());
        } finally {
            System.out.println("Closing audit resources.");
        }

        System.out.println("Alice balance: " + bankService.getBalance("ACC-5001"));
    }

    private static final class BankService {
        private final Map<String, Account> accountsByNumber = new HashMap<>();

        private void createAccount(String accountNumber, String ownerName, BigDecimal openingBalance)
                throws DuplicateAccountException, InvalidTransactionException {
            if (accountsByNumber.containsKey(accountNumber)) {
                throw new DuplicateAccountException("Account " + accountNumber + " already exists.");
            }
            validatePositiveOrZero(openingBalance);
            accountsByNumber.put(accountNumber, new Account(accountNumber, ownerName, openingBalance));
        }

        private void transfer(String sourceAccountNumber, String targetAccountNumber, BigDecimal amount)
                throws AccountNotFoundException, InsufficientFundsException, InvalidTransactionException {
            validatePositive(amount);
            Account source = findAccount(sourceAccountNumber);
            Account target = findAccount(targetAccountNumber);
            source.withdraw(amount);
            target.deposit(amount);
        }

        private BigDecimal getBalance(String accountNumber) {
            Account account = accountsByNumber.get(accountNumber);
            return account == null ? BigDecimal.ZERO : account.balance();
        }

        private Account findAccount(String accountNumber) throws AccountNotFoundException {
            Account account = accountsByNumber.get(accountNumber);
            if (account == null) {
                throw new AccountNotFoundException("Account " + accountNumber + " was not found.");
            }
            return account;
        }

        private static void validatePositive(BigDecimal amount) throws InvalidTransactionException {
            if (amount == null || amount.signum() <= 0) {
                throw new InvalidTransactionException("Amount must be greater than zero.");
            }
        }

        private static void validatePositiveOrZero(BigDecimal amount) throws InvalidTransactionException {
            if (amount == null || amount.signum() < 0) {
                throw new InvalidTransactionException("Opening balance must not be negative.");
            }
        }
    }

    private static final class Account {
        private final String accountNumber;
        private final String ownerName;
        private BigDecimal balance;

        private Account(String accountNumber, String ownerName, BigDecimal balance) {
            this.accountNumber = accountNumber;
            this.ownerName = ownerName;
            this.balance = balance;
        }

        private BigDecimal balance() {
            return balance;
        }

        private void deposit(BigDecimal amount) {
            balance = balance.add(amount);
        }

        private void withdraw(BigDecimal amount) throws InsufficientFundsException {
            if (balance.compareTo(amount) < 0) {
                throw new InsufficientFundsException("Account " + accountNumber + " has insufficient funds.");
            }
            balance = balance.subtract(amount);
        }
    }

    private static final class InsufficientFundsException extends Exception {
        private InsufficientFundsException(String message) {
            super(message);
        }
    }

    private static final class InvalidTransactionException extends Exception {
        private InvalidTransactionException(String message) {
            super(message);
        }
    }

    private static final class AccountNotFoundException extends Exception {
        private AccountNotFoundException(String message) {
            super(message);
        }
    }

    private static final class DuplicateAccountException extends Exception {
        private DuplicateAccountException(String message) {
            super(message);
        }
    }
}