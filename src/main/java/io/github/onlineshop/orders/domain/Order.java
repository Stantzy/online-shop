package io.github.onlineshop.orders.domain;

import io.github.onlineshop.orders.OrderStatus;
import io.github.onlineshop.users.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private OrderStatus orderStatus;
    private User user;
    private List<OrderLine> orderLines;

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;

        for(OrderLine line : orderLines) {
            BigDecimal productPrice = line.getPriceAtTime();
            BigDecimal productQuantity = BigDecimal.valueOf(line.getQuantity());
            BigDecimal lineTotalPrice = productPrice.multiply(productQuantity);
            totalPrice = totalPrice.add(lineTotalPrice) ;
        }

        return totalPrice;
    }

    public Long getTotalItems() {
        Long result = 0L;

        for(OrderLine orderLine : orderLines)
            result += orderLine.getQuantity();

        return result;
    }
}
