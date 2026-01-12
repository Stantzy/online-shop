package io.github.onlineshop.orders.domain.exception;

public class InsufficientStockException extends RuntimeException {
    private final static String DEFAULT_MESSAGE = "Not enough products in stock";

    public InsufficientStockException() {
        super(DEFAULT_MESSAGE);
    }

    public InsufficientStockException(String message) {
        super(message);
    }

    public InsufficientStockException(
        Long productId,
        String productName,
        Long requested,
        Long available
    ) {
        super(
            String.format(
                "Not enough %s products with id=%d in stock. " +
                    "Requested = %d, available = %d",
                productName,
                productId,
                requested,
                available
            )
        );
    }
}
