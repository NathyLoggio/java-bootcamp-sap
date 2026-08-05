package model;

/**
 * Represents a bank customer.
 *
 * @param customerId unique customer identifier
 * @param fullName customer's full name
 * @param vip whether the customer has VIP status
 */
public record Customer(String customerId, String fullName, boolean vip) {

    /**
     * Validates customer data when the record is created.
     */
    public Customer {
        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("customerId must not be blank");
        }
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("fullName must not be blank");
        }
    }
}