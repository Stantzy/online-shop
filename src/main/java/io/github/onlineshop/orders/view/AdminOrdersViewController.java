package io.github.onlineshop.orders.view;

import io.github.onlineshop.orders.OrderStatus;
import io.github.onlineshop.orders.api.dto.OrderDto;
import io.github.onlineshop.orders.domain.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminOrdersViewController {
    private final OrderService orderService;

    @GetMapping
    public String listOrders(
        @RequestParam(required = false) OrderStatus status,
        Model model
    ) {
        List<OrderDto> orders;

        if(status != null)
            orders = orderService.getAllOrdersByStatus(status);
        else
            orders = orderService.getAllOrders();

        model.addAttribute("orders", orders);
        return "admin/orders/list";
    }

    @GetMapping("/{id}")
    public String getOrderDetailsById(
        @PathVariable Long id,
        Model model
    ) {
        OrderDto orderDto = orderService.getOrderById(id);
        model.addAttribute("order", orderDto);
        return "admin/orders/details";
    }

    @PostMapping("/{id}/delete")
    public String deleteOrder(
        @PathVariable Long id,
        RedirectAttributes redirectAttributes
    ) {
        try {
            orderService.updateOrderStatus(id, OrderStatus.DELETED);
            redirectAttributes.addFlashAttribute(
                "success",
                "Заказ успешно отмечен как удалённый"
            );
        } catch(EntityNotFoundException | IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute(
                "error",
                "Ошибка: " + e.getMessage()
            );
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute(
                "error",
                "Внутренняя ошибка сервера"
            );
        }

        return "redirect:/admin/orders";
    }

    @PostMapping("/{id}/status")
    public String updateOrderStatus(
        @PathVariable Long id,
        @RequestParam OrderStatus status,
        RedirectAttributes redirectAttributes
    )  {
        try {
            orderService.updateOrderStatus(id, status);
            redirectAttributes.addFlashAttribute(
                "success",
                "Статус заказа успешно обновлен"
            );
        } catch(EntityNotFoundException | IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute(
                "error",
                "Ошибка: " + e.getMessage()
            );
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute(
                "error",
                "Внутренняя ошибка сервера"
            );
        }

        return "redirect:/admin/orders";
    }
}
