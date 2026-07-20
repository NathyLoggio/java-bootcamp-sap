import java.util.LinkedList;
import java.util.Queue;

/**
 * My Objective: Practice using a LinkedList as a Queue to process invoices, 
 * sharpening my core Java data structure skills.
 *
 * Concepts I am exploring:
 * - Queue behavior and interface
 * - offer(), poll(), and peek() methods
 * - Implementing FIFO (First-In, First-Out) logic
 *
 * Expected output:
 * Next invoice waiting: INV-2001
 * Processing invoice: INV-2001
 * Processing invoice: INV-2002
 * Processing invoice: INV-2003
 * Queue empty? true
 *
 * My step-by-step logic:
 * 1. I declare a Queue interface to enforce FIFO behavior.
 * 2. I instantiate a LinkedList to provide the underlying implementation.
 * 3. I use offer() to safely add work to the back of the queue.
 * 4. I use poll() to retrieve and remove work from the front.
 *
 * Big-O Complexity (My notes):
 * - offer(element): O(1)
 * - poll(): O(1)
 * - peek(): O(1)
 * - contains(element): O(n)
 *
 * Memory & Performance considerations:
 * I understand that LinkedList has per-node object overhead. While this is a great learning 
 * exercise, I would consider ArrayDeque for production systems if I need a faster, more 
 * memory-friendly simple queue.
 *
 * SAP enterprise use case:
 * Simulating invoice processing jobs where the oldest waiting document must be handled first.
 */
public class LinkedListExercise02 {

    /**
     * Runs my LinkedList queue example.
     *
     * @param args command-line arguments (not used in this exercise).
     */
    public static void main(String[] args) {
        Queue<String> invoiceQueue = new LinkedList<>();
        
        invoiceQueue.offer("INV-2001");
        invoiceQueue.offer("INV-2002");
        invoiceQueue.offer("INV-2003");

        System.out.println("Next invoice waiting: " + invoiceQueue.peek());

        while (!invoiceQueue.isEmpty()) {
            System.out.println("Processing invoice: " + invoiceQueue.poll());
        }

        System.out.println("Queue empty? " + invoiceQueue.isEmpty());
    }
}