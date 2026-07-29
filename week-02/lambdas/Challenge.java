package lambdas;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * My Objective: Introduce Optional and Streams in approval workflow analysis to safely 
 * query and aggregate financial data.
 *
 * Concepts I am exploring:
 * - Stream pipelines.
 * - Handling missing values safely with Optional.
 * - Aggregating values using reduce().
 *
 * Expected console output:
 * First pending high-value report: EXP-5002
 * Total approved amount: 850.00
 *
 * My step-by-step logic:
 * 1. I call stream() to create a pipeline over the expense reports.
 * 2. I apply filter() to keep only the matching reports.
 * 3. I use findFirst(), which returns an Optional because a matching item may not exist.
 * 4. I use reduce() to calculate a BigDecimal total safely.
 *
 * Big-O Complexity (My notes):
 * - findFirst() after filtering: best case O(1), worst case O(n).
 * - Total calculation: O(n).
 *
 * SAP enterprise use case:
 * SAP backend services often transform large collections into business answers: totals, 
 * filtered results, first matching items, and grouped summaries.
 */
public class Challenge {
    /**
     * Runs my Optional and Streams challenge.
     *
     * @param args command-line arguments (not used in this exercise)
     */
    public static void main(String[] args) {
        List<ExpenseReport> reports = List.of(
                new ExpenseReport("EXP-5001", "Ana", new BigDecimal("850.00"), Status.APPROVED),
                new ExpenseReport("EXP-5002", "Bruno", new BigDecimal("6100.00"), Status.PENDING),
                new ExpenseReport("EXP-5003", "Carla", new BigDecimal("180.00"), Status.PENDING));

        BigDecimal highValueThreshold = new BigDecimal("5000.00");

        Optional<ExpenseReport> firstPendingHighValueReport = reports.stream()
                .filter(report -> report.status() == Status.PENDING)
                .filter(report -> report.amount().compareTo(highValueThreshold) > 0)
                .findFirst();

        BigDecimal approvedTotal = reports.stream()
                .filter(report -> report.status() == Status.APPROVED)
                .map(ExpenseReport::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        firstPendingHighValueReport
                .map(ExpenseReport::reportId)
                .ifPresentOrElse(
                        reportId -> System.out.println("First pending high-value report: " + reportId),
                        () -> System.out.println("No pending high-value report found."));

        System.out.println("Total approved amount: " + approvedTotal);
    }

    private enum Status {
        APPROVED,
        PENDING,
        REJECTED
    }

    private record ExpenseReport(String reportId, String employeeName, BigDecimal amount, Status status) {
    }
}