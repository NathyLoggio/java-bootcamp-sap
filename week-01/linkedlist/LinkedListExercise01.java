import java.util.LinkedList;

/**
 * My Objective: Practice addFirst(), addLast(), removeFirst(), and removeLast() 
 * to strengthen my understanding of double-ended queue (Deque) operations.
 *
 * Concepts I am exploring:
 * - LinkedList functioning as a Deque
 * - addFirst() and addLast()
 * - removeFirst() and removeLast()
 *
 * Expected output:
 * First approval processed: Security review
 * Last approval removed: Archive contract
 * Remaining approval steps:
 * - Legal review
 * - Finance approval
 *
 * My step-by-step logic:
 * 1. I am using a LinkedList because it can work natively as a double-ended queue.
 * 2. I apply addFirst() to insert an urgent review step at the front.
 * 3. I apply addLast() to append standard steps at the end of the flow.
 * 4. I use removeFirst() and removeLast() to process items from opposite ends efficiently.
 *
 * Big-O Complexity (My notes):
 * - addFirst(): O(1)
 * - addLast(): O(1)
 * - removeFirst(): O(1)
 * - removeLast(): O(1)
 * - get(index): O(n), because the list must be traversed node by node.
 *
 * Memory & Performance considerations:
 * I note that each node stores the value plus links to the previous and next nodes. 
 * I must keep in mind that this consumes more memory than an ArrayList for the same number of elements.
 *
 * Why I chose this collection for the exercise:
 * It perfectly simulates approval workflows that require fast, O(1) operations at both ends of a sequence.
 *
 * SAP enterprise use case:
 * Contract approval flows where urgent review steps can be added at the beginning without shifting an underlying array.
 */
public class LinkedListExercise01 {

    /**
     * Runs my LinkedList approval workflow example.
     *
     * @param args command-line arguments (not used in this exercise).
     */
    public static void main(String[] args) {
        LinkedList<String> approvalSteps = new LinkedList<>();
        
        approvalSteps.addLast("Legal review");
        approvalSteps.addLast("Finance approval");
        approvalSteps.addLast("Archive contract");
        approvalSteps.addFirst("Security review");

        String firstProcessedStep = approvalSteps.removeFirst();
        String removedLastStep = approvalSteps.removeLast();

        System.out.println("First approval processed: " + firstProcessedStep);
        System.out.println("Last approval removed: " + removedLastStep);

        System.out.println("Remaining approval steps:");
        for (String approvalStep : approvalSteps) {
            System.out.println("- " + approvalStep);
        }
    }
}