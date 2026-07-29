package lambdas;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * My Objective: Learn core functional interfaces with account data.
 *
 * Concepts I am exploring:
 * - Functional interfaces for validation, transformation, filtering, and event handling.
 * - Predicate, Consumer, Supplier, and Function.
 *
 * Expected console output:
 * Active account: ACC-1001
 * Alert for ACC-1001 balance 1500.00
 * Default message: No approval comment provided.
 * Display label: ACC-1001 - Nathaly
 *
 * My step-by-step logic:
 * 1. I use Predicate<Account> to answer a yes/no question.
 * 2. I use Consumer<Account> to perform an action without returning a value.
 * 3. I use Supplier<String> to create a value without input.
 * 4. I use Function<Account, String> to convert one type into another.
 *
 * Big-O Complexity (My notes):
 * - Filtering all accounts with a loop: O(n).
 *
 * SAP enterprise use case:
 * Functional interfaces appear frequently in Java backend code for validation, transformation, 
 * filtering, and event handling.
 */
public class Exercise01 {

    /**
     * Runs my core functional interface example.
     *
     * @param args command-line arguments (not used in this exercise)
     */
    public static void main(String[] args) {
        List<Account> accounts = List.of(
                new Account("ACC-1001", "Nathaly", new BigDecimal("1500.00"), true),
                new Account("ACC-1002", "Rafael", BigDecimal.ZERO, false));

        Predicate<Account> isActive = account -> account.active();
        Consumer<Account> printBalanceAlert = account ->
                System.out.println("Alert for " + account.accountNumber() + " balance " + account.balance());
        Supplier<String> defaultApprovalMessage = () -> "No approval comment provided.";
        Function<Account, String> toDisplayLabel = account -> account.accountNumber() + " - " + account.ownerName();

        for (Account account : accounts) {
            if (isActive.test(account)) {
                System.out.println("Active account: " + account.accountNumber());
                printBalanceAlert.accept(account);
                System.out.println("Display label: " + toDisplayLabel.apply(account));
            }
        }

        System.out.println("Default message: " + defaultApprovalMessage.get());
    }

    private record Account(String accountNumber, String ownerName, BigDecimal balance, boolean active) {
    }
}