import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * My Objective: Benchmark indexed reads in ArrayList and LinkedList to demonstrate 
 * the real-world impact of Big-O on performance.
 *
 * Concepts I am exploring:
 * - O(1) vs O(n) access times
 * - Using System.nanoTime() for performance testing
 * - Understanding why benchmark results require careful interpretation
 *
 * Expected output:
 * The exact times will vary by computer.
 * ArrayList indexed reads should usually be much faster than LinkedList indexed reads.
 *
 * My step-by-step logic:
 * 1. I load both lists with the same amount of invoice IDs.
 * 2. I run a benchmark that repeatedly reads elements by their index.
 * 3. I observe that ArrayList jumps directly to an index (fast).
 * 4. I observe that LinkedList must walk node by node (slow).
 *
 * Big-O Complexity (My notes):
 * - ArrayList get(index): O(1)
 * - LinkedList get(index): O(n)
 *
 * Memory & Performance considerations:
 * I note that an ArrayList uses a single backing array of references, whereas a LinkedList 
 * uses multiple node objects. I chose these collections to clearly expose the difference 
 * between fast indexed access and pointer-based traversal.
 *
 * SAP enterprise use case:
 * Search result pages and invoice grids frequently read items by index, making ArrayList 
 * a better default choice than LinkedList.
 */
public class BigOExercise01 {
    private static final int ITEM_COUNT = 20_000;
    private static final int READS = 5_000;

    /**
     * Runs my indexed-read benchmark.
     *
     * @param args command-line arguments (not used in this exercise)
     */
    public static void main(String[] args) {
        List<String> arrayList = new ArrayList<>();
        List<String> linkedList = new LinkedList<>();

        for (int index = 0; index < ITEM_COUNT; index++) {
            String invoiceNumber = "INV-" + index;
            arrayList.add(invoiceNumber);
            linkedList.add(invoiceNumber);
        }

        long arrayListTime = measureIndexedReads(arrayList);
        long linkedListTime = measureIndexedReads(linkedList);

        System.out.println("ArrayList indexed reads: " + arrayListTime + " ns");
        System.out.println("LinkedList indexed reads: " + linkedListTime + " ns");
        System.out.println("O(1) means direct access does not grow with list size.");
        System.out.println("O(n) means work grows linearly with the number of elements.");
    }

    private static long measureIndexedReads(List<String> invoices) {
        long startTime = System.nanoTime();
        int checksum = 0;

        for (int index = 0; index < READS; index++) {
            checksum += invoices.get(index).length();
        }

        long endTime = System.nanoTime();
        preventOptimization(checksum);
        return endTime - startTime;
    }

    private static void preventOptimization(int checksum) {
        if (checksum == -1) {
            System.out.println("Impossible checksum: " + checksum);
        }
    }
}