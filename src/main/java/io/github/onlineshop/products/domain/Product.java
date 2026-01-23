package io.github.onlineshop.products.domain;

import io.github.onlineshop.orders.domain.exception.InsufficientStockException;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private String name;
    private Long quantity;
    private BigDecimal price;

    public void checkProductAvailability(
        Long requestedQuantity
    ) {
        if(quantity < requestedQuantity) {
            throw new InsufficientStockException(
                id,
                name,
                requestedQuantity,
                quantity
            );
        }
    }

    public void decreaseQuantity(Long amount) {
        if(amount > this.quantity) {
            throw new InsufficientStockException(
                "Cannot decrease more than available"
            );
        }
        this.quantity -= amount;
    }
}
