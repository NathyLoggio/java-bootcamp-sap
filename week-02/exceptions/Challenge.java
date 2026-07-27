package exceptions;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Objective: Combine custom exceptions in a realistic account-transfer workflow.
 *
 * Difficulty: Advanced
 *
 * Business scenario:
 * A banking service must create accounts and transfer money between them safely.
 *
 * SAP enterprise scenario:
 * Enterprise financial applications separate infrastructure failures from business failures.
 * Business-specific exception names improve logs, support tickets, and API contracts.
 *
 * Step-by-step explanation:
 * 1. BankService stores accounts in a HashMap by account number.
 * 2. createAccount() rejects duplicate IDs.
 * 3. transfer() validates source and target accounts.
 * 4. Business exceptions propagate to main(), where they are handled centrally.
 *
 * Expected console output:
 * Transfer failed: Account ACC-9999 was not found.
 * Closing audit resources.
 * Alice balance: 500.00
 *
 * Time complexity:
 * - find account in HashMap: average O(1)
 * - transfer(): average O(1)
 *
 * Common beginner mistakes:
 * - Throwing generic Exception.
 * - Hiding the original business reason.
 * - Mixing console printing inside domain logic.
 *
 * Possible interview questions:
 * - Why create AccountNotFoundException instead of using null?
 * - Why does DuplicateAccountException improve maintainability?
 * - How would Spring Boot convert these exceptions into HTTP responses?
 *
 * Suggested improvements:
 * - Add transaction rollback behavior.
 * - Add logging with correlation IDs.
 * - Add tests for every failure path.
 *
 * Mentor question:
 * What would happen if transfer withdrew from the source before confirming the target exists?
 */
public class Challenge {

    /**
     * Runs the custom exception transfer challenge.
     *
     * @param args command-line arguments, not used in this exercise
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
