import java.math.BigDecimal;

/**
 * My Objective: Understand why generic classes exist and how they help me store SAP business objects safely.
 *
 * Concepts I am exploring:
 * - Generic classes
 * - Enforcing type safety at compile time
 * - Understanding the risks of using raw Object-based containers
 *
 * Expected output:
 * Stored invoice: INV-1001 | Supplier: ACME Logistics | Amount: 1299.99
 * Stored employee: E-501 | Name: Ana Souza | Department: SAP Ariba Invoicing
 *
 * My step-by-step logic:
 * 1. I define Box<T> to accept a type parameter named T.
 * 2. I use Box<Invoice> to ensure it can store only Invoice objects.
 * 3. I use Box<Employee> to ensure it can store only Employee objects.
 * 4. This way, the compiler protects my code before the program even runs.
 *
 * My ideas for future improvement:
 * - Add validation logic before storing a value.
 * - Evolve this Box into a Repository pattern when I need search, save, and remove operations.
 *
 * SAP enterprise use case:
 * SAP systems move numerous domain objects (invoices, contracts, employees, POs).
 * I am learning Generics to build reusable infrastructure while keeping each business type strictly safe.
 */
public class GenericsExercise01 {

    /**
     * Runs my beginner generic class example.
     *
     * @param args command-line arguments (not used in this exercise)
     */
    public static void main(String[] args) {
        Box<Invoice> invoiceBox = new Box<>(new Invoice("INV-1001", "ACME Logistics", new BigDecimal("1299.99")));
        Box<Employee> employeeBox = new Box<>(new Employee("E-501", "Ana Souza", "SAP Ariba Invoicing"));

        System.out.println("Stored invoice: " + invoiceBox.getValue().formatForDisplay());
        System.out.println("Stored employee: " + employeeBox.getValue().formatForDisplay());
    }

    private static final class Box<T> {
        private final T value;

        private Box(T value) {
            this.value = value;
        }

        private T getValue() {
            return value;
        }
    }

    private record Invoice(String invoiceNumber, String supplierName, BigDecimal amount) {
        private String formatForDisplay() {
            return invoiceNumber + " | Supplier: " + supplierName + " | Amount: " + amount;
        }
    }

    private record Employee(String employeeId, String name, String department) {
        private String formatForDisplay() {
            return employeeId + " | Name: " + name + " | Department: " + department;
        }
    }
}