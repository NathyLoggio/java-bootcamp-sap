import java.util.Deque;
import java.util.LinkedList;

/**
 * My Objective: Practice using a LinkedList as a Deque to manage urgent and routine approvals, 
 * simulating priority behavior in enterprise systems.
 *
 * Concepts I am exploring:
 * - Deque interface behavior
 * - addFirst() for urgent work items
 * - addLast() for normal work items
 * - removeFirst() to process the next work item
 *
 * Expected output:
 * Approval order:
 * - URGENT: CFO approval for high-value contract
 * - NORMAL: Manager approval for invoice
 * - NORMAL: Legal approval for contract
 *
 * My step-by-step logic:
 * 1. I add normal approvals to the back of the deque using addLast().
 * 2. I insert urgent approvals to the front using addFirst().
 * 3. I process the items by always removing from the front with removeFirst().
 * 4. This approach creates a simple but effective priority behavior.
 *
 * Big-O Complexity (My notes):
 * - addFirst(): O(1)
 * - addLast(): O(1)
 * - removeFirst(): O(1)
 * - searching for a specific approval: O(n)
 *
 * Memory & Performance considerations:
 * I must keep in mind that LinkedList is not cache-friendly because its nodes may be spread 
 * randomly across memory.
 *
 * When I would use a LinkedList:
 * - When I truly need frequent additions/removals at both ends of the collection.
 * - When Queue/Deque behavior is my primary requirement for the algorithm.
 *
 * When I would avoid a LinkedList:
 * - When I need frequent random access via get(index).
 * - When memory efficiency and iteration speed are critical.
 * - When an ArrayDeque can satisfy the queue/deque requirement (which is often preferred in production).
 *
 * SAP enterprise use case:
 * Approval systems that need to prioritize urgent documents without losing the normal queue order.
 */
public class LinkedListChallenge {

    /**
     * Runs my Deque approval-priority challenge.
     *
     * @param args command-line arguments (not used in this exercise).
     */
    public static void main(String[] args) {
        Deque<String> approvalDeque = new LinkedList<>();
        
        approvalDeque.addLast("NORMAL: Manager approval for invoice");
        approvalDeque.addLast("NORMAL: Legal approval for contract");
        approvalDeque.addFirst("URGENT: CFO approval for high-value contract");

        System.out.println("Approval order:");
        while (!approvalDeque.isEmpty()) {
            System.out.println("- " + approvalDeque.removeFirst());
        }
    }
}