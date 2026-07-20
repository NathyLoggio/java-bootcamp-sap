import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * My Objective: Practice writing generic methods and observe firsthand the difference between 
 * type-safe generic types and unsafe raw types.
 *
 * Concepts I am exploring:
 * - Generic methods
 * - Building Type-safe Lists
 * - The practical differences between raw types and generic types
 *
 * Expected output:
 * First purchase order: PO-9001 | Buyer: SAP Labs Brazil | Amount: 7800.00
 * First contract: C-2026-01 | Vendor: Cloud Partner | Active: true
 * Generic list protected us from mixing unrelated business objects.
 *
 * My step-by-step logic:
 * 1. I created the firstItem method to receive a List<T> and return a T.
 * 2. I rely on Java to infer T from the specific list I pass in.
 * 3. Passing a List<PurchaseOrder> returns a PurchaseOrder without needing manual casting.
 * 4. I noted that a raw List would allow mixed types and cause runtime failures later.
 *
 * My ideas for future improvement:
 * - Refactor to return an Optional<T> instead of throwing an exception when the list is empty.
 * - Add a generic printAll method that works for any SAP document type.
 *
 * SAP enterprise use case:
 * Building reusable services that need to process different document types using the same core logic 
 * (like validation, logging, audit history, or routing).
 */
public class GenericsExercise02 {

    /**
     * Runs my generic method example.
     *
     * @param args command-line arguments (not used in this exercise)
     */
    public static void main(String[] args) {
        List<PurchaseOrder> purchaseOrders = new ArrayList<>();
        purchaseOrders.add(new PurchaseOrder("PO-9001", "SAP Labs Brazil", new BigDecimal("7800.00")));
        purchaseOrders.add(new PurchaseOrder("PO-9002", "SAP Ariba", new BigDecimal("1450.50")));

        List<Contract> contracts = new ArrayList<>();
        contracts.add(new Contract("C-2026-01", "Cloud Partner", true));

        PurchaseOrder firstPurchaseOrder = firstItem(purchaseOrders);
        Contract firstContract = firstItem(contracts);

        System.out.println("First purchase order: " + firstPurchaseOrder.formatForDisplay());
        System.out.println("First contract: " + firstContract.formatForDisplay());
        System.out.println("Generic list protected us from mixing unrelated business objects.");

        // My notes on raw types:
        // List rawDocuments = new ArrayList();
        // rawDocuments.add(new PurchaseOrder("PO-1", "Buyer", BigDecimal.TEN));
        // rawDocuments.add("This string should not be in a purchase order list");
        //
        // The raw list compiles with warnings, but it removes type safety.
        // In professional SAP code, warnings like this are not "noise"; they are design signals.
    }

    private static <T> T firstItem(List<T> items) {
        if (items.isEmpty()) {
            throw new IllegalArgumentException("The list must contain at least one item.");
        }
        return items.get(0);
    }

    private record PurchaseOrder(String purchaseOrderNumber, String buyerName, BigDecimal amount) {
        private String formatForDisplay() {
            return purchaseOrderNumber + " | Buyer: " + buyerName + " | Amount: " + amount;
        }
    }

    private record Contract(String contractId, String vendorName, boolean active) {
        private String formatForDisplay() {
            return contractId + " | Vendor: " + vendorName + " | Active: " + active;
        }
    }
}