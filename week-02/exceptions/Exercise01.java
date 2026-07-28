package exceptions;

import java.math.BigDecimal;

/**
 * My Objective: Use unchecked exceptions to handle programming and validation mistakes effectively.
 *
 * Concepts I am exploring:
 * - Unchecked exceptions (RuntimeException)
 * - Validating inputs before changing state (Fail-fast principle)
 * - The role of the finally block
 *
 * Expected output:
 * Deposit failed: Deposit amount must be greater than zero.
 * Audit finished.
 *
 * My step-by-step logic:
 * 1. I validate the amount inside the deposit() method before any state changes occur.
 * 2. If the value is invalid, I throw a custom InvalidTransactionException (unchecked).
 * 3. In main(), I catch the exception and print a business-readable message.
 * 4. I use a finally block to ensure the audit runs regardless of whether the operation succeeds or fails.
 *
 * Big-O Complexity (My notes):
 * - deposit(): O(1)
 *
 * SAP enterprise use case:
 * Backend services must validate incoming data before modifying business state.
 * Invalid requests should fail early with clear messages, which is exactly what I simulated here.
 */
public class Exercise01 {

    /**
     * Runs my unchecked exception validation example.
     *
     * @param args command-line arguments (not used in this exercise)
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