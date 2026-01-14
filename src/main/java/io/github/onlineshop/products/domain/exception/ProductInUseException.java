package io.github.onlineshop.products.domain.exception;

public class ProductInUseException extends RuntimeException {
    private static final String DEFAULT_MESSAGE =
        "The product cannot be deleted because it is used in orders";

    public ProductInUseException() { super(DEFAULT_MESSAGE); }

    public ProductInUseException(String productName) {
        super(
            String.format(
                "The product %s cannot be deleted because it is used in orders",
                productName
            )
        );
    }
}
