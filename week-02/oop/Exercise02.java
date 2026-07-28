package oop;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

/**
 * My Objective: Use abstract classes and interfaces to clearly define and separate account behaviors and capabilities.
 *
 * Concepts I am exploring:
 * - Abstract classes for shared base state
 * - Interfaces for assigning optional capabilities
 * - Distinguishing inheritance from interface implementation
 *
 * Expected output:
 * Checking fee: 15.00
 * Savings monthly interest: 20.00
 * Total taxable amount: 15.00
 *
 * My step-by-step logic:
 * 1. I created Account as an abstract class because a generic account should not be instantiated directly.
 * 2. I made CheckingAccount and SavingsAccount inherit their shared state (account number, balance) from Account.
 * 3. I defined Taxable as an interface because tax behavior is a capability that can apply to many unrelated types.
 * 4. I created the InterestBearing interface because only specific types of accounts earn interest.
 *
 * Big-O Complexity (My notes):
 * - calculateTaxableTotal(): O(n), where n is the number of taxable items.
 *
 * SAP enterprise use case:
 * Enterprise systems often share common behavior in an abstract base type while exposing
 * optional capabilities through interfaces. This structure helps me build scalable, modular financial models.
 */
public class Exercise02 {

    /**
     * Runs my abstract class and interface example.
     *
     * @param args command-line arguments (not used in this exercise)
     */
    public static void main(String[] args) {
        CheckingAccount checkingAccount = new CheckingAccount("CHK-1001", new BigDecimal("1000.00"));
        SavingsAccount savingsAccount = new SavingsAccount("SAV-1001", new BigDecimal("5000.00"), new BigDecimal("0.004"));

        System.out.println("Checking fee: " + checkingAccount.calculateMonthlyFee());
        System.out.println("Savings monthly interest: " + savingsAccount.calculateMonthlyInterest());

        List<Taxable> taxableItems = List.of(checkingAccount);
        System.out.println("Total taxable amount: " + calculateTaxableTotal(taxableItems));
    }

    private static BigDecimal calculateTaxableTotal(List<Taxable> taxableItems) {
        BigDecimal total = BigDecimal.ZERO;
        for (Taxable taxableItem : taxableItems) {
            total = total.add(taxableItem.calculateTax());
        }
        return total;
    }

    private abstract static class Account {
        private final String accountNumber;
        private BigDecimal balance;

        private Account(String accountNumber, BigDecimal openingBalance) {
            if (accountNumber == null || accountNumber.isBlank()) {
                throw new IllegalArgumentException("accountNumber must not be blank");
            }
            Objects.requireNonNull(openingBalance, "openingBalance must not be null");
            if (openingBalance.signum() < 0) {
                throw new IllegalArgumentException("openingBalance must not be negative");
            }
            this.accountNumber = accountNumber;
            this.balance = openingBalance;
        }

        protected String getAccountNumber() {
            return accountNumber;
        }

        protected BigDecimal getBalance() {
            return balance;
        }
    }

    private interface Taxable {
        BigDecimal calculateTax();
    }

    private interface InterestBearing {
        BigDecimal calculateMonthlyInterest();
    }

    private static final class CheckingAccount extends Account implements Taxable {
        private static final BigDecimal MONTHLY_FEE = new BigDecimal("15.00");

        private CheckingAccount(String accountNumber, BigDecimal openingBalance) {
            super(accountNumber, openingBalance);
        }

        private BigDecimal calculateMonthlyFee() {
            return MONTHLY_FEE;
        }

        @Override
        public BigDecimal calculateTax() {
            return calculateMonthlyFee();
        }
    }

    private static final class SavingsAccount extends Account implements InterestBearing {
        private final BigDecimal monthlyInterestRate;

        private SavingsAccount(String accountNumber, BigDecimal openingBalance, BigDecimal monthlyInterestRate) {
            super(accountNumber, openingBalance);
            this.monthlyInterestRate = Objects.requireNonNull(monthlyInterestRate, "monthlyInterestRate must not be null");
        }

        @Override
        public BigDecimal calculateMonthlyInterest() {
            return getBalance().multiply(monthlyInterestRate).setScale(2, RoundingMode.HALF_UP);
        }
    }
}