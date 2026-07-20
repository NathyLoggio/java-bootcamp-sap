import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * My Objective: Practice contains(), set(), and remove() operations using employee expense reports 
 * to understand how lists are modified dynamically.
 *
 * Concepts I am exploring:
 * - contains() for searching
 * - set() for updating
 * - remove() for deleting
 * - How equals() behaves automatically with Java Records
 *
 * Expected output:
 * Contains taxi expense? true
 * Updated second expense: EXP-002 | Hotel | 610.00
 * Removed meal expense? true
 * Remaining expenses:
 * - EXP-001 | Taxi | 38.90
 * - EXP-002 | Hotel | 610.00
 *
 * My step-by-step logic:
 * 1. I use contains() to check whether an equal object already exists in the list.
 * 2. I use set(index, value) to replace an item at a specific, known position.
 * 3. I use remove(object) to delete the first matching object it finds.
 * 4. I note that Records automatically implement equals() using their fields, making contains() work seamlessly.
 *
 * Big-O Complexity (My notes):
 * - contains(element): O(n), because the ArrayList may need to scan every item.
 * - set(index, element): O(1).
 * - remove(element): O(n), because it searches and then shifts all subsequent items left.
 * - remove(index): O(n), because later items must shift left to fill the gap.
 *
 * Memory & Performance considerations:
 * I realized that removing an item does not always shrink the internal capacity of the array immediately.
 *
 * Why I chose this collection for the exercise:
 * Expense reports are commonly shown in order and edited row by row. ArrayList fits this 
 * spreadsheet-like workflow very well.
 *
 * SAP enterprise use case:
 * Expense and invoice line items almost always appear as ordered, editable rows in enterprise applications.
 */
public class ArrayListExercise02 {

    /**
     * Runs my intermediate ArrayList expense report example.
     *
     * @param args command-line arguments (not used in this exercise)
     */
    public static void main(String[] args) {
        List<ExpenseReport> expenses = new ArrayList<>();
        ExpenseReport taxi = new ExpenseReport("EXP-001", "Taxi", new BigDecimal("38.90"));
        ExpenseReport hotel = new ExpenseReport("EXP-002", "Hotel", new BigDecimal("590.00"));
        ExpenseReport meal = new ExpenseReport("EXP-003", "Meal", new BigDecimal("72.30"));

        expenses.add(taxi);
        expenses.add(hotel);
        expenses.add(meal);

        System.out.println("Contains taxi expense? " + expenses.contains(taxi));

        expenses.set(1, new ExpenseReport("EXP-002", "Hotel", new BigDecimal("610.00")));
        System.out.println("Updated second expense: " + expenses.get(1).formatForDisplay());

        boolean removedMeal = expenses.remove(meal);
        System.out.println("Removed meal expense? " + removedMeal);

        System.out.println("Remaining expenses:");
        for (ExpenseReport expense : expenses) {
            System.out.println("- " + expense.formatForDisplay());
        }
    }

    private record ExpenseReport(String expenseId, String category, BigDecimal amount) {
        private String formatForDisplay() {
            return expenseId + " | " + category + " | " + amount;
        }
    }
}