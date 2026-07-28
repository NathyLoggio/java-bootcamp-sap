package oop;

import java.math.BigDecimal;
import java.util.List;

/**
 * My Objective: Apply polymorphism and SOLID principles to build a flexible banking model 
 * that calculates monthly financial effects.
 *
 * Concepts I am exploring:
 * - Polymorphism
 * - SOLID principles (Open/Closed Principle)
 * - Avoiding massive if/else chains
 * - Delegation and Composition
 *
 * Expected output:
 * Account summary:
 * - CHK-2001 balance 900.00
 * - SAV-2001 balance 2500.00
 * Total monthly charges: 20.00
 *
 * My step-by-step logic:
 * 1. I designed MonthlyChargeable as an interface to represent a specific capability, not a family tree.
 * 2. I implemented CheckingAccount to provide a specific monthly charge.
 * 3. I configured SavingsAccount with no monthly charge, yet it still seamlessly participates as an Account.
 * 4. I composed the Bank class with a list of accounts, delegating the calculation logic to the individual account behaviors via polymorphism.
 *
 * Big-O Complexity (My notes):
 * - printSummary(): O(n)
 * - calculateMonthlyCharges(): O(n)
 *
 * SAP enterprise use case:
 * SAP models large business objects by separating stable identity data, reusable value objects, 
 * and behavior-oriented services. I am using polymorphism here to allow the system to process 
 * different account types flexibly without needing hardcoded conditional logic.
 */
public class Challenge {

    /**
     * Runs my polymorphism and SOLID challenge.
     *
     * @param args command-line arguments (not used in this exercise)
     */
    public static void main(String[] args) {
        Bank bank = new Bank(List.of(
                new CheckingAccount("CHK-2001", new BigDecimal("900.00")),
                new SavingsAccount("SAV-2001", new BigDecimal("2500.00"))));

        bank.printSummary();
        System.out.println("Total monthly charges: " + bank.calculateMonthlyCharges());
    }

    private interface MonthlyChargeable {
        BigDecimal calculateMonthlyCharge();
    }

    private abstract static class Account {
        private final String accountNumber;
        private final BigDecimal balance;

        private Account(String accountNumber, BigDecimal balance) {
            if (accountNumber == null || accountNumber.isBlank()) {
                throw new IllegalArgumentException("accountNumber must not be blank");
            }
            if (balance == null || balance.signum() < 0) {
                throw new IllegalArgumentException("balance must not be null or negative");
            }
            this.accountNumber = accountNumber;
            this.balance = balance;
        }

        private String getAccountNumber() {
            return accountNumber;
        }

        private BigDecimal getBalance() {
            return balance;
        }
    }

    private static final class CheckingAccount extends Account implements MonthlyChargeable {
        private CheckingAccount(String accountNumber, BigDecimal balance) {
            super(accountNumber, balance);
        }

        @Override
        public BigDecimal calculateMonthlyCharge() {
            return new BigDecimal("20.00");
        }
    }

    private static final class SavingsAccount extends Account {
        private SavingsAccount(String accountNumber, BigDecimal balance) {
            super(accountNumber, balance);
        }
    }

    private record Bank(List<Account> accounts) {
        private void printSummary() {
            System.out.println("Account summary:");
            for (Account account : accounts) {
                System.out.println("- " + account.getAccountNumber() + " balance " + account.getBalance());
            }
        }

        private BigDecimal calculateMonthlyCharges() {
            BigDecimal total = BigDecimal.ZERO;
            for (Account account : accounts) {
                if (account instanceof MonthlyChargeable chargeable) {
                    total = total.add(chargeable.calculateMonthlyCharge());
                }
            }
            return total;
        }
    }
}