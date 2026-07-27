package exceptions;

import java.math.BigDecimal;

/**
 * Objective: Use checked exceptions for recoverable business failures.
 *
 * Difficulty: Intermediate
 *
 * Business scenario:
 * A withdrawal should fail gracefully when the account does not have enough money.
 *
 * SAP enterprise scenario:
 * Financial systems often use explicit business exceptions so service layers can convert them
 * into user-facing messages, workflow decisions, or integration responses.
 *
 * Step-by-step explanation:
 * 1. withdraw() declares throws InsufficientFundsException.
 * 2. The compiler forces callers to handle or propagate the checked exception.
 * 3. main() catches it and prints a clear message.
 * 4. The account balance remains unchanged after the failed withdrawal.
 *
 * Expected console output:
 * Withdrawal failed: Account ACC-4001 has only 100.00 available.
 * Current balance: 100.00
 *
 * Time complexity:
 * - withdraw(): O(1)
 *
 * Common beginner mistakes:
 * - Making every exception checked.
 * - Swallowing checked exceptions without action.
 * - Losing useful context in the exception message.
 *
 * Possible interview questions:
 * - When should Exception be extended instead of RuntimeException?
 * - What does throws mean?
 * - How does exception propagation work?
 *
 * Suggested improvements:
 * - Include account number and requested amount as exception fields.
 * - Add a transaction record for failed attempts if audit requires it.
 *
 * Mentor question:
 * Why might insufficient funds be modeled as a checked exception in a banking workflow?
 */
public class Exercise02 {

    /**
     * Runs the checked exception withdrawal example.
     *
     * @param args command-line arguments, not used in this exercise
     */
    public static void main(String[] args) {
        Account account = new Account("ACC-4001", new BigDecimal("100.00"));

        try {
            account.withdraw(new BigDecimal("250.00"));
        } catch (InsufficientFundsException exception) {
            System.out.println("Withdrawal failed: " + exception.getMessage());
        }

        System.out.println("Current balance: " + account.getBalance());
    }

    private static final class Account {
        private final String accountNumber;
        private BigDecimal balance;

        private Account(String accountNumber, BigDecimal openingBalance) {
            this.accountNumber = accountNumber;
            this.balance = openingBalance;
        }

        private BigDecimal getBalance() {
            return balance;
        }

        private void withdraw(BigDecimal amount) throws InsufficientFundsException {
            if (amount == null || amount.signum() <= 0) {
                throw new IllegalArgumentException("amount must be greater than zero");
            }
            if (balance.compareTo(amount) < 0) {
                throw new InsufficientFundsException(
                        "Account " + accountNumber + " has only " + balance + " available.");
            }
            balance = balance.subtract(amount);
        }
    }

    private static final class InsufficientFundsException extends Exception {
        private InsufficientFundsException(String message) {
            super(message);
        }
    }
}
