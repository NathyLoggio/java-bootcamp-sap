package lambdas;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

/**
 * My Objective: Use method references, forEach(), and Comparator.comparing() to process 
 * collections cleanly.
 *
 * Concepts I am exploring:
 * - Method references (::).
 * - Sorting collections with Comparator.
 * - Stream filtering.
 *
 * Expected console output:
 * Invoices requiring approval:
 * INV-3002 amount 9900.00
 * INV-3003 amount 5200.00
 *
 * My step-by-step logic:
 * 1. I use Comparator.comparing() to create a readable sorting rule.
 * 2. I apply reversed() to change ascending order into descending order.
 * 3. I use stream().filter() to select invoices that match a specific business rule.
 * 4. I use forEach(System.out::println) to print the results using a method reference.
 *
 * Big-O Complexity (My notes):
 * - Sorting: O(n log n).
 * - Filtering after sorting: O(n).
 *
 * SAP enterprise use case:
 * Backend services often sort and transform collections before returning data to a UI or API.
 */
public class Exercise02 {

    /**
     * Runs my comparator and method reference example.
     *
     * @param args command-line arguments (not used in this exercise)
     */
    public static void main(String[] args) {
        List<Invoice> invoices = List.of(
                new Invoice("INV-3001", "Office Supplies", new BigDecimal("250.00")),
                new Invoice("INV-3002", "Cloud Infrastructure", new BigDecimal("9900.00")),
                new Invoice("INV-3003", "Consulting Partner", new BigDecimal("5200.00")));

        BigDecimal approvalThreshold = new BigDecimal("5000.00");

        System.out.println("Invoices requiring approval:");
        invoices.stream()
                .sorted(Comparator.comparing(Invoice::amount).reversed())
                .filter(invoice -> invoice.amount().compareTo(approvalThreshold) > 0)
                .map(Invoice::formatForApproval)
                .forEach(System.out::println);
    }

    private record Invoice(String invoiceNumber, String supplierName, BigDecimal amount) {
        private String formatForApproval() {
            return invoiceNumber + " amount " + amount;
        }
    }
}