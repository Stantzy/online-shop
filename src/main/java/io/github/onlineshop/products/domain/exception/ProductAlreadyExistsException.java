package io.github.onlineshop.products.domain.exception;

public class ProductAlreadyExistsException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Product already exists";

    public ProductAlreadyExistsException() {
        super(DEFAULT_MESSAGE);
    }

    public ProductAlreadyExistsException(String productName) {
        super(
            String.format("Product with name %s already exists", productName)
        );
    }
}
