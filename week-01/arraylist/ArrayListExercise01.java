import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * My Objective: Learn basic ArrayList operations with an invoice list to understand 
 * why it is so effective for indexed access.
 *
 * Concepts I am exploring:
 * - The add() and get() methods
 * - Iterating through lists
 * - Why ArrayList is the go-to choice for indexed access
 *
 * Expected output:
 * First invoice: INV-1001 from Office Supplies Ltd
 * All invoices:
 * - INV-1001 | Office Supplies Ltd | 120.50
 * - INV-1002 | Cloud Hosting SA | 899.99
 * - INV-1003 | Logistics Partner | 450.00
 *
 * My step-by-step logic:
 * 1. I observe that ArrayList naturally stores elements in insertion order.
 * 2. I use add() to append an invoice to the end of the list.
 * 3. I use get(0) to directly read the first invoice by its index.
 * 4. I use an enhanced for-loop to visit and process each invoice.
 *
 * Big-O Complexity (My notes):
 * - add(element) at the end: usually O(1), but occasionally O(n) during internal resizing.
 * - get(index): O(1).
 * - iteration over all items: O(n).
 *
 * Memory & Performance considerations:
 * I note that ArrayList stores references inside a backing array. It keeps extra capacity to avoid 
 * resizing on every new add operation. This extra memory space is a worthwhile tradeoff for fast reads.
 *
 * Why I chose this collection for the exercise:
 * Invoice lists are often read by position, displayed sequentially, and iterated for totals. 
 * ArrayList handles this specific access pattern perfectly.
 *
 * SAP enterprise use case:
 * Displaying invoice search results in the UI is a natural and frequent ArrayList use case.
 */
public class ArrayListExercise01 {

    /**
     * Runs my beginner ArrayList invoice example.
     *
     * @param args command-line arguments (not used in this exercise)
     */
    public static void main(String[] args) {
        List<Invoice> invoices = new ArrayList<>();
        invoices.add(new Invoice("INV-1001", "Office Supplies Ltd", new BigDecimal("120.50")));
        invoices.add(new Invoice("INV-1002", "Cloud Hosting SA", new BigDecimal("899.99")));
        invoices.add(new Invoice("INV-1003", "Logistics Partner", new BigDecimal("450.00")));

        Invoice firstInvoice = invoices.get(0);
        System.out.println("First invoice: " + firstInvoice.invoiceNumber() + " from " + firstInvoice.supplierName());

        System.out.println("All invoices:");
        for (Invoice invoice : invoices) {
            System.out.println("- " + invoice.formatForDisplay());
        }
    }

    private record Invoice(String invoiceNumber, String supplierName, BigDecimal amount) {
        private String formatForDisplay() {
            return invoiceNumber + " | " + supplierName + " | " + amount;
        }
    }
}