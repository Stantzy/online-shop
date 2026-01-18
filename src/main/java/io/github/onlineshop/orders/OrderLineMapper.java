package io.github.onlineshop.orders;

import io.github.onlineshop.orders.api.dto.OrderLineDto;
import io.github.onlineshop.orders.database.OrderEntity;
import io.github.onlineshop.orders.database.OrderLineEntity;
import io.github.onlineshop.orders.domain.OrderLine;
import io.github.onlineshop.products.ProductMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderLineMapper {
    private final ProductMapper productMapper;

    public OrderLineMapper(
        ProductMapper productMapper
    ) {
        this.productMapper = productMapper;
    }

    public OrderLine toDomainOrderLine(OrderLineEntity orderLineEntity) {
        if(orderLineEntity == null)
            return null;

        return new OrderLine(
            orderLineEntity.getId(),
            productMapper.toDomainProduct(orderLineEntity.getProductEntity()),
            orderLineEntity.getPriceAtTime(),
            orderLineEntity.getQuantity(),
            null
        );
    }

    public List<OrderLine> toDomainOrderLines(
        List<OrderLineEntity> orderLineEntities
    ) {
        if(orderLineEntities == null)
            return List.of();

        return orderLineEntities.stream()
            .map(this::toDomainOrderLine)
            .toList();
    }

    public OrderLineEntity toOrderLineEntity(
        OrderLine orderLine,
        OrderEntity orderEntity
    ) {
        if(orderLine == null)
            return null;

        OrderLineEntity orderLineEntity = new OrderLineEntity();

        orderLineEntity.setId(orderLine.getId());
        orderLineEntity.setProductEntity(
            productMapper.toProductEntity(orderLine.getProduct())
        );
        orderLineEntity.setPriceAtTime(orderLine.getPriceAtTime());
        orderLineEntity.setQuantity(orderLine.getQuantity());
        orderLineEntity.setOrder(orderEntity);

        return orderLineEntity;
    }

    public List<OrderLineEntity> toListOfOrderLineEntities(
        List<OrderLine> orderLineList,
        OrderEntity orderEntity
    ) {
        if(orderLineList == null)
            return List.of();

        return orderLineList.stream()
            .map(orderLine -> toOrderLineEntity(orderLine, orderEntity))
            .toList();
    }

    public OrderLineDto toOrderLineDto(OrderLine orderLine) {
        return new OrderLineDto(
            orderLine.getId(),
            orderLine.getProduct().getId(),
            orderLine.getPriceAtTime(),
            orderLine.getQuantity()
        );
    }
}
