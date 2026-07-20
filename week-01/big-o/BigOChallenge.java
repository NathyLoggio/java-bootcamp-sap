import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * My Objective: Benchmark HashMap and TreeMap while reviewing advanced O(n log n) concepts.
 *
 * Concepts I am exploring:
 * - HashMap vs TreeMap performance
 * - O(1) and O(log n) lookups
 * - O(n log n) comparison-based sorting
 *
 * Expected output:
 * The exact times will vary by computer.
 * HashMap get(): usually faster for direct lookup.
 * TreeMap firstKey(): shows sorted-key behavior.
 *
 * My step-by-step logic:
 * 1. I map invoice numbers to invoice amounts using a HashMap.
 * 2. I do the same with a TreeMap to keep the keys automatically sorted.
 * 3. I measure direct lookup speeds using get().
 * 4. I print out practical meanings of common Big-O categories for my future reference.
 *
 * Big-O Complexity (My notes):
 * - HashMap get(key): average O(1).
 * - TreeMap get(key): O(log n).
 * - Iterating all entries: O(n).
 * - Sorting n values with comparison-based sorting: O(n log n).
 *
 * Memory & Performance considerations:
 * While both Maps store keys and values, I must account for TreeMap storing additional 
 * tree links and ordering information, consuming more memory.
 *
 * SAP enterprise use case:
 * SAP applications frequently retrieve business objects by ID. If sorted keys are not required, 
 * HashMap is the best default. Sorted reporting by document number justifies a TreeMap.
 */
public class BigOChallenge {
    private static final int ITEM_COUNT = 50_000;
    private static final int LOOKUPS = 100_000;

    /**
     * Runs my Map lookup benchmark and prints Big-O explanations.
     *
     * @param args command-line arguments (not used in this exercise)
     */
    public static void main(String[] args) {
        Map<String, BigDecimal> hashMap = new HashMap<>();
        TreeMap<String, BigDecimal> treeMap = new TreeMap<>();

        for (int index = 0; index < ITEM_COUNT; index++) {
            String invoiceNumber = "INV-" + index;
            BigDecimal amount = BigDecimal.valueOf(index).add(new BigDecimal("0.99"));
            hashMap.put(invoiceNumber, amount);
            treeMap.put(invoiceNumber, amount);
        }

        String targetInvoice = "INV-42000";
        long hashMapTime = measureGets(hashMap, targetInvoice);
        long treeMapTime = measureGets(treeMap, targetInvoice);

        System.out.println("HashMap get(): " + hashMapTime + " ns");
        System.out.println("TreeMap get(): " + treeMapTime + " ns");
        System.out.println("TreeMap first key: " + treeMap.firstKey());
        printBigOStudyGuide();
    }

    private static long measureGets(Map<String, BigDecimal> invoicesByNumber, String targetInvoice) {
        long startTime = System.nanoTime();
        BigDecimal checksum = BigDecimal.ZERO;

        for (int lookup = 0; lookup < LOOKUPS; lookup++) {
            checksum = checksum.add(invoicesByNumber.get(targetInvoice));
        }

        long endTime = System.nanoTime();
        preventOptimization(checksum);
        return endTime - startTime;
    }

    private static void printBigOStudyGuide() {
        System.out.println("O(1): read invoice by key from a HashMap.");
        System.out.println("O(log n): read invoice by key from a TreeMap.");
        System.out.println("O(n): calculate total value across all invoices.");
        System.out.println("O(n log n): sort invoices by amount using comparison sorting.");
    }

    private static void preventOptimization(BigDecimal checksum) {
        if (checksum.signum() < 0) {
            System.out.println("Impossible checksum: " + checksum);
        }
    }
}