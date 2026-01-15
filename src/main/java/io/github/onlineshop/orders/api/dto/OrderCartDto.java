package io.github.onlineshop.orders.api.dto;

import java.math.BigDecimal;
import java.util.List;

public class OrderCartDto {
    List<OrderLineDto> orderLineDtoList;
    BigDecimal totalPrice;
    Long itemsQuantity;

    public List<OrderLineDto> getOrderLineDtoList() {
        return orderLineDtoList;
    }

    public void setOrderLineDtoList(List<OrderLineDto> orderLineDtoList) {
        this.orderLineDtoList = orderLineDtoList;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getItemsQuantity() {
        return itemsQuantity;
    }

    public void setItemsQuantity(Long itemsQuantity) {
        this.itemsQuantity = itemsQuantity;
    }
}
