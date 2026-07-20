import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * My Objective: Understand internal resizing and capacity management while handling purchase orders 
 * to optimize memory during bulk operations.
 *
 * Concepts I am exploring:
 * - ArrayList initial capacity manipulation
 * - The ensureCapacity() method
 * - The hidden costs of array resizing
 * - Choosing ArrayList for read-heavy enterprise screens
 *
 * Expected output:
 * Purchase orders loaded: 5
 * First PO after correction: PO-1001 | SAP Labs Brazil | 1550.00
 * Large import prepared with expected capacity.
 * Total value: 10300.00
 *
 * My step-by-step logic:
 * 1. I explicitly start the ArrayList with an initial capacity of 2.
 * 2. I note that adding more than 2 items forces the internal array to create a new, larger array and copy data over.
 * 3. I use ensureCapacity(1_000) to prepare room before a simulated large import, preventing multiple expensive resizing hits.
 * 4. I learned that capacity (the allocated memory slots) is not the same thing as size (the actual number of elements).
 *
 * Big-O Complexity (My notes):
 * - add(element): amortized O(1).
 * - resizing: O(n), because all existing references must be copied into the new, larger array.
 * - get(index): O(1).
 * - set(index, element): O(1).
 * - full total calculation: O(n).
 *
 * Memory & Performance considerations:
 * While extra capacity means the backing array reserves more slots than currently used (costing memory), 
 * it drastically improves future add() performance.
 *
 * Why I chose this collection for the exercise:
 * Purchase orders loaded from search results are usually displayed and read many times. 
 * ArrayList gives predictable iteration order and fast indexed access for these read-heavy screens.
 *
 * SAP enterprise use case:
 * Bulk imports of purchase orders benefit significantly from pre-sizing when the expected record count is known in advance.
 */
public class ArrayListChallenge {

    /**
     * Runs my ArrayList capacity challenge.
     *
     * @param args command-line arguments (not used in this exercise)
     */
    public static void main(String[] args) {
        List<PurchaseOrder> purchaseOrders = new ArrayList<>(2);
        purchaseOrders.add(new PurchaseOrder("PO-1001", "SAP Labs Brazil", new BigDecimal("1500.00")));
        purchaseOrders.add(new PurchaseOrder("PO-1002", "SAP Ariba", new BigDecimal("2800.00")));
        purchaseOrders.add(new PurchaseOrder("PO-1003", "Customer Success", new BigDecimal("900.00")));
        purchaseOrders.add(new PurchaseOrder("PO-1004", "Procurement Team", new BigDecimal("4200.00")));
        purchaseOrders.add(new PurchaseOrder("PO-1005", "Finance Team", new BigDecimal("850.00")));

        purchaseOrders.set(0, new PurchaseOrder("PO-1001", "SAP Labs Brazil", new BigDecimal("1550.00")));

        if (purchaseOrders instanceof ArrayList<PurchaseOrder> arrayList) {
            arrayList.ensureCapacity(1_000);
        }

        System.out.println("Purchase orders loaded: " + purchaseOrders.size());
        System.out.println("First PO after correction: " + purchaseOrders.get(0).formatForDisplay());
        System.out.println("Large import prepared with expected capacity.");
        System.out.println("Total value: " + calculateTotalValue(purchaseOrders));
    }

    private static BigDecimal calculateTotalValue(List<PurchaseOrder> purchaseOrders) {
        BigDecimal total = BigDecimal.ZERO;
        for (PurchaseOrder purchaseOrder : purchaseOrders) {
            total = total.add(purchaseOrder.amount());
        }
        return total;
    }

    private record PurchaseOrder(String purchaseOrderNumber, String ownerTeam, BigDecimal amount) {
        private String formatForDisplay() {
            return purchaseOrderNumber + " | " + ownerTeam + " | " + amount;
        }
    }
}