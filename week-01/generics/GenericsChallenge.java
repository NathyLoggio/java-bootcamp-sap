import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * My Objective: Master the use of wildcards to read and write to generic collections safely.
 *
 * Concepts I am exploring:
 * - Upper-bounded wildcards: ? extends Type
 * - Lower-bounded wildcards: ? super Type
 * - Applying the PECS rule: Producer Extends, Consumer Super
 *
 * Expected output:
 * Documents ready for audit:
 * - INV-3001 amount 2500.00
 * - PO-7001 amount 8800.00
 * Approval queue size: 2
 *
 * My step-by-step logic:
 * 1. I structured both Invoice and PurchaseOrder to extend the BusinessDocument abstract class.
 * 2. I configured printAuditPreview to read from a List<? extends BusinessDocument>.
 * 3. I configured addInvoiceApproval to write Invoice objects safely into a List<? super Invoice>.
 * 4. By applying these wildcards, I made my APIs highly flexible without compromising type safety.
 *
 * My ideas for future improvement:
 * - Add state transitions (e.g., COMPOSING, APPROVED, REJECTED) to the documents.
 * - Implement specific validation rules for each document type. (paper invoice, online invoice, carbon copy invoice, etc.)
 *
 * SAP enterprise use case:
 * Designing a shared approval engine that audits multiple document subtypes, while maintaining 
 * specialized queues that only accept specific document families.
 */
public class GenericsChallenge {

    /**
     * Runs my wildcard challenge.
     *
     * @param args command-line arguments (not used in this exercise)
     */
    public static void main(String[] args) {
        List<Invoice> invoices = List.of(new Invoice("INV-3001", new BigDecimal("2500.00")));
        List<PurchaseOrder> purchaseOrders = List.of(new PurchaseOrder("PO-7001", new BigDecimal("8800.00")));
        List<BusinessDocument> approvalQueue = new ArrayList<>();

        System.out.println("Documents ready for audit:");
        printAuditPreview(invoices);
        printAuditPreview(purchaseOrders);

        addInvoiceApproval(approvalQueue, new Invoice("INV-3002", new BigDecimal("410.00")));
        addInvoiceApproval(approvalQueue, new Invoice("INV-3003", new BigDecimal("920.00")));

        System.out.println("Approval queue size: " + approvalQueue.size());
    }

    private static void printAuditPreview(List<? extends BusinessDocument> documents) {
        for (BusinessDocument document : documents) {
            System.out.println("- " + document.documentNumber() + " amount " + document.amount());
        }
    }

    private static void addInvoiceApproval(List<? super Invoice> approvalQueue, Invoice invoice) {
        approvalQueue.add(invoice);
    }

    private abstract static class BusinessDocument {
        private final String documentNumber;
        private final BigDecimal amount;

        private BusinessDocument(String documentNumber, BigDecimal amount) {
            this.documentNumber = documentNumber;
            this.amount = amount;
        }

        private String documentNumber() {
            return documentNumber;
        }

        private BigDecimal amount() {
            return amount;
        }
    }

    private static final class Invoice extends BusinessDocument {
        private Invoice(String documentNumber, BigDecimal amount) {
            super(documentNumber, amount);
        }
    }

    private static final class PurchaseOrder extends BusinessDocument {
        private PurchaseOrder(String documentNumber, BigDecimal amount) {
            super(documentNumber, amount);
        }
    }
}