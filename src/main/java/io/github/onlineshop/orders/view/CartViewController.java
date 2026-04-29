package io.github.onlineshop.orders.view;

import io.github.onlineshop.orders.api.dto.OrderAddToCartRequest;
import io.github.onlineshop.orders.api.dto.OrderAddToCartResponse;
import io.github.onlineshop.orders.api.dto.OrderCartDto;
import io.github.onlineshop.orders.domain.OrderService;
import io.github.onlineshop.security.domain.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartViewController {
    private final OrderService orderService;
    private final CurrentUserService currentUserService;

    @GetMapping
    public String viewCart(Model model) {
        Long userId = currentUserService.getUserId();
        OrderCartDto cart = orderService.getCart(userId);
        model.addAttribute("cart", cart);
        return "orders/cart";
    }

    @PostMapping("/add")
    public String addToCart(
        @RequestParam Long productId,
        @RequestParam(defaultValue = "1") Long quantity,
        RedirectAttributes redirectAttributes
    ) {
        Long userId = currentUserService.getUserId();
        OrderAddToCartRequest request =
            new OrderAddToCartRequest(productId, quantity);
        OrderAddToCartResponse response =
            orderService.addItemToCart(request, userId);

        redirectAttributes.addFlashAttribute(
            "message",
            "Товар добавлен в корзину (всего: " + response.getTotalItems() + ")"
        );

        return "redirect:/products";
    }

    @PostMapping("/update/{lineId}")
    public String updateQuantity(
        @PathVariable Long lineId,
        @RequestParam Long quantity,
        RedirectAttributes redirectAttributes
    ) {
        Long userId = currentUserService.getUserId();

        orderService.updateQuantityOfProductInCart(userId, lineId, quantity);
        redirectAttributes.addFlashAttribute("message", "Количество обновлено");

        return "redirect:/cart";
    }

    @PostMapping("/remove/{lineId}")
    public String removeFromCart(
        @PathVariable Long lineId,
        RedirectAttributes redirectAttributes
    ) {
        Long userId = currentUserService.getUserId();

        orderService.deleteItemFromCart(userId, lineId);
        redirectAttributes.addFlashAttribute("message", "Товар удалён из корзины");

        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart(RedirectAttributes redirectAttributes) {
        Long userId = currentUserService.getUserId();

        orderService.clearCart(userId);
        redirectAttributes.addFlashAttribute("message", "Корзина очищена");

        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkout(RedirectAttributes redirectAttributes) {
        Long userId = currentUserService.getUserId();

        orderService.checkout(userId);
        redirectAttributes.addFlashAttribute("message", "Заказ оформлен!");

        return "redirect:/orders";
    }
}
