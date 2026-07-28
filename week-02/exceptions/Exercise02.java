package exceptions;

import java.math.BigDecimal;

/**
 * My Objective: Use checked exceptions to model recoverable business failures, 
 * forcing callers to handle specific scenarios.
 *
 * Concepts I am exploring:
 * - Checked exceptions (extending Exception)
 * - The throws keyword and exception propagation
 * - Graceful failure recovery without corrupting state
 *
 * Expected output:
 * Withdrawal failed: Account ACC-4001 has only 100.00 available.
 * Current balance: 100.00
 *
 * My step-by-step logic:
 * 1. I declare the withdraw() method with 'throws InsufficientFundsException'.
 * 2. This forces the compiler to ensure callers handle or propagate the checked exception.
 * 3. In main(), I catch it and provide a clear, graceful error message.
 * 4. I ensure the account balance remains completely unchanged after the failed withdrawal.
 *
 * Big-O Complexity (My notes):
 * - withdraw(): O(1)
 *
 * SAP enterprise use case:
 * Financial systems use explicit business exceptions so service layers can confidently convert them 
 * into user-facing messages, workflow decisions, or API integration responses.
 */
public class Exercise02 {

    /**
     * Runs my checked exception withdrawal example.
     *
     * @param args command-line arguments (not used in this exercise)
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