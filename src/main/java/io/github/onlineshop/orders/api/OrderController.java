package io.github.onlineshop.orders.api;

import io.github.onlineshop.constants.PathConstants;
import io.github.onlineshop.orders.api.dto.OrderCartDto;
import io.github.onlineshop.orders.api.dto.OrderAddToCartRequest;
import io.github.onlineshop.orders.api.dto.OrderAddToCartResponse;
import io.github.onlineshop.orders.api.dto.OrderDto;
import io.github.onlineshop.orders.domain.OrderService;
import io.github.onlineshop.security.domain.CurrentUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PathConstants.ORDER)
public class OrderController {
    private static final Logger log =
        LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;
    private final CurrentUserService currentUserService;

    public OrderController(
        OrderService orderService,
        CurrentUserService currentUserService
    ) {
        this.orderService = orderService;
        this.currentUserService = currentUserService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        log.info("Called method getAllOrders");

        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(
        @PathVariable Long id
    ) {
        log.info("Called method getOrderById: id={}", id);

        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/cart/items")
    public ResponseEntity<OrderCartDto> getCart() {
        log.info("Called method getCart");

        Long userId = currentUserService.getUserId();
        OrderCartDto cartDto = orderService.getCart(userId);

        return ResponseEntity.ok(cartDto);
    }

    @PostMapping("/cart/items")
    public ResponseEntity<OrderAddToCartResponse> addItemToCart(
        @RequestBody OrderAddToCartRequest addToCartRequest
    ) {
        log.info(
            "Called method addItemToCart: productId={}, quantity={}",
            addToCartRequest.productId(),
            addToCartRequest.quantity()
        );

        Long userId = currentUserService.getUserId();
        OrderAddToCartResponse response =
            orderService.addItemToCart(addToCartRequest, userId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteOrderById(
        @PathVariable Long id
    ) {
        log.info("Called deleteOrderById: id={}", id);
        orderService.deleteOrderById(id);
        return ResponseEntity.ok().build();
    }
}
