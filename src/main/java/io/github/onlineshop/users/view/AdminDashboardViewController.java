package io.github.onlineshop.users.view;

import io.github.onlineshop.orders.domain.OrderService;
import io.github.onlineshop.products.domain.ProductService;
import io.github.onlineshop.users.domain.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminDashboardViewController {
    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute(
            "productsCount",
            productService.getAllProducts().size()
        );
        model.addAttribute(
            "usersCount",
            userService.getAllUsers().size()
        );
        model.addAttribute(
            "ordersCount",
            orderService.getAllOrders().size()
        );
        return "admin/dashboard";
    }
}
