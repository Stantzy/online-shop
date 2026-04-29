package io.github.onlineshop.users.view;

import io.github.onlineshop.orders.api.dto.OrderDto;
import io.github.onlineshop.orders.domain.OrderService;
import io.github.onlineshop.users.api.dto.request.UserPaginationRequest;
import io.github.onlineshop.users.api.dto.response.UserDto;
import io.github.onlineshop.users.domain.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminUserViewController {
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping
    public String listUsers(
        UserPaginationRequest paginationRequest,
        Model model
    ) {
        List<UserDto> users = userService.getAllUsers(paginationRequest);

        model.addAttribute("users", users);
        model.addAttribute("currentPage", paginationRequest.pageNumber());
        model.addAttribute("pageSize", paginationRequest.pageSize());
        model.addAttribute("sortBy", paginationRequest.sortBy());
        model.addAttribute("sortDirection", paginationRequest.sortDirection());

        return "admin/users/list";
    }

    @GetMapping("/{id}")
    public String userDetails(@PathVariable Long id, Model model) {
        UserDto user = userService.getUserById(id);
        List<OrderDto> orders = orderService.getOrdersByUserId(id);
        BigDecimal total = orderService.getTotalByUserId(id);

        model.addAttribute("user", user);
        model.addAttribute("ordersCount", orders.size());
        model.addAttribute("totalSpent", total);

        return "admin/users/details";
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(
        @PathVariable Long id,
        RedirectAttributes redirectAttributes
    ) {
        try {
            userService.deleteUserById(id);
            redirectAttributes.addFlashAttribute(
                "success",
                "Пользователь успешно удалён"
            );
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute(
                "error",
                "Пользователь не найден"
            );
        } catch(IllegalStateException e) {
            redirectAttributes.addFlashAttribute(
                "error",
                "Запрещено удалять пользователя с активными заказами"
            );
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute(
                "error",
                "Ошибка при удалении: " + e.getMessage()
            );
        }

        return "redirect:/admin/users";
    }

    @GetMapping("/{id}/orders")
    public String userOrders(@PathVariable Long id, Model model) {
        UserDto userDto = userService.getUserById(id);
        List<OrderDto> orders = orderService.getOrdersByUserId(id);

        model.addAttribute("user", userDto);
        model.addAttribute("orders", orders);

        return "admin/users/orders";
    }
}
