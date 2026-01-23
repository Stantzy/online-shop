package io.github.onlineshop.orders.api.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderCartDto {
    List<OrderLineDto> orderLineDtoList;
    BigDecimal totalPrice;
    Long itemsQuantity;
}
