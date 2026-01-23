package io.github.onlineshop.orders.api;

import io.github.onlineshop.constants.PathConstants;
import io.github.onlineshop.orders.api.dto.*;
import io.github.onlineshop.orders.domain.OrderService;
import io.github.onlineshop.security.domain.CurrentUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PathConstants.ORDER)
@RequiredArgsConstructor
public class OrderController {
    private static final Logger log =
        LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;
    private final CurrentUserService currentUserService;

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
        @RequestBody @Valid OrderAddToCartRequest addToCartRequest
    ) {
        log.info(
            "Called method addItemToCart: productId={}, quantity={}",
            addToCartRequest.getProductId(),
            addToCartRequest.getQuantity()
        );

        Long userId = currentUserService.getUserId();
        OrderAddToCartResponse response =
            orderService.addItemToCart(addToCartRequest, userId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/cart/items")
    public ResponseEntity<Void> clearCart() {
        log.info(
            "Called method clearCart"
        );

        Long userId = currentUserService.getUserId();
        orderService.clearCart(userId);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/cart/items/{lineId}")
    public ResponseEntity<OrderCartDto> updateQuantity(
        @PathVariable @NotNull Long lineId,
        @RequestParam @NotNull Long newQuantity
    ) {
        log.info(
            "Called method updateQuantity: lineId={}, newQuantity={}",
            lineId,
            newQuantity
        );

        Long userId = currentUserService.getUserId();

        OrderCartDto response =
            orderService.updateQuantityOfProductInCart(
                userId,
                lineId,
                newQuantity
            );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/cart/items/{lineId}")
    public ResponseEntity<Void> deleteItemFromCart(
        @PathVariable @NotNull Long lineId
    ) {
        log.info("Called method deleteItemFromCart: lineId={}", lineId);

        Long userId = currentUserService.getUserId();
        orderService.deleteItemFromCart(userId, lineId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteOrderById(
        @PathVariable @NotNull Long id
    ) {
        log.info("Called deleteOrderById: id={}", id);

        orderService.deleteOrderById(id);

        return ResponseEntity.ok().build();
    }
}
