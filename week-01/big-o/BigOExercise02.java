import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * My Objective: Benchmark search behavior in HashSet and TreeSet to understand 
 * when to prioritize fast lookups versus sorted data.
 *
 * Concepts I am exploring:
 * - O(1) average-case lookup
 * - O(log n) lookup
 * - HashSet vs TreeSet implementations
 *
 * Expected output:
 * The exact times will vary by computer.
 * HashSet contains() is usually faster.
 * TreeSet keeps values sorted.
 *
 * My step-by-step logic:
 * 1. I use a HashSet to store values in hash buckets for speed.
 * 2. I use a TreeSet to store values in a sorted tree structure.
 * 3. I test the contains() method many times for the same contract ID.
 * 4. I can clearly see that HashSet optimizes lookup speed, while TreeSet optimizes sorted order.
 *
 * Big-O Complexity (My notes):
 * - HashSet contains(): average O(1) (though the worst case can be slower).
 * - TreeSet contains(): O(log n).
 * - Building either set from n items: commonly O(n) for HashSet, O(n log n) for TreeSet.
 *
 * Memory & Performance considerations:
 * I understand that HashSet uses hashing structures while TreeSet uses tree nodes. Both add 
 * memory overhead beyond storing the values themselves.
 *
 * SAP enterprise use case:
 * Contract IDs need uniqueness checks. If the enterprise UI also requires sorted IDs, 
 * TreeSet may be the better choice despite the slight lookup penalty.
 */
public class BigOExercise02 {
    private static final int ITEM_COUNT = 50_000;
    private static final int LOOKUPS = 100_000;

    /**
     * Runs my Set lookup benchmark.
     *
     * @param args command-line arguments (not used in this exercise)
     */
    public static void main(String[] args) {
        Set<String> hashSet = new HashSet<>();
        Set<String> treeSet = new TreeSet<>();

        for (int index = 0; index < ITEM_COUNT; index++) {
            String contractId = "C-" + index;
            hashSet.add(contractId);
            treeSet.add(contractId);
        }

        String targetContract = "C-42000";
        long hashSetTime = measureContains(hashSet, targetContract);
        long treeSetTime = measureContains(treeSet, targetContract);

        System.out.println("HashSet contains(): " + hashSetTime + " ns");
        System.out.println("TreeSet contains(): " + treeSetTime + " ns");
        System.out.println("HashSet focuses on fast lookup.");
        System.out.println("TreeSet keeps values sorted with O(log n) operations.");
    }

    private static long measureContains(Set<String> contracts, String targetContract) {
        long startTime = System.nanoTime();
        int foundCount = 0;

        for (int lookup = 0; lookup < LOOKUPS; lookup++) {
            if (contracts.contains(targetContract)) {
                foundCount++;
            }
        }

        long endTime = System.nanoTime();
        preventOptimization(foundCount);
        return endTime - startTime;
    }

    private static void preventOptimization(int foundCount) {
        if (foundCount == -1) {
            System.out.println("Impossible found count: " + foundCount);
        }
    }
}