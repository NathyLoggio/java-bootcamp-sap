package exceptions;

import java.math.BigDecimal;

/**
 * Objective: Use unchecked exceptions for programming and validation mistakes.
 *
 * Difficulty: Beginner
 *
 * Business scenario:
 * A deposit request must be rejected if the amount is zero, negative, or missing.
 *
 * SAP enterprise scenario:
 * Backend services validate incoming data before changing business state. Invalid requests
 * should fail early with clear messages.
 *
 * Step-by-step explanation:
 * 1. deposit() validates the amount.
 * 2. Invalid values throw InvalidTransactionException.
 * 3. main() catches the exception and prints a business-readable message.
 * 4. finally runs whether the operation succeeds or fails.
 *
 * Expected console output:
 * Deposit failed: Deposit amount must be greater than zero.
 * Audit finished.
 *
 * Time complexity:
 * - deposit(): O(1)
 *
 * Common beginner mistakes:
 * - Catching Exception too broadly.
 * - Ignoring exceptions with an empty catch block.
 * - Using exceptions for normal successful control flow.
 *
 * Possible interview questions:
 * - When is RuntimeException appropriate?
 * - Why should validation happen before updating balance?
 * - What does finally guarantee?
 *
 * Suggested improvements:
 * - Add structured logging.
 * - Return an error response object in a future REST API.
 *
 * Mentor question:
 * What would happen if deposit updated the balance before validating the amount?
 */
public class Exercise01 {

    /**
     * Runs the unchecked exception validation example.
     *
     * @param args command-line arguments, not used in this exercise
     */
    public static void main(String[] args) {
        Account account = new Account("ACC-3001");

        try {
            account.deposit(new BigDecimal("-10.00"));
        } catch (InvalidTransactionException exception) {
            System.out.println("Deposit failed: " + exception.getMessage());
        } finally {
            System.out.println("Audit finished.");
        }
    }

    private static final class Account {
        private final String accountNumber;
        private BigDecimal balance = BigDecimal.ZERO;

        private Account(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        private void deposit(BigDecimal amount) {
            if (amount == null || amount.signum() <= 0) {
                throw new InvalidTransactionException("Deposit amount must be greater than zero.");
            }
            balance = balance.add(amount);
        }
    }

    private static final class InvalidTransactionException extends RuntimeException {
        private InvalidTransactionException(String message) {
            super(message);
        }
    }
}
