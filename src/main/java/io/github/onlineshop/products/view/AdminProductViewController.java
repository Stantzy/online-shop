package io.github.onlineshop.products.view;

import io.github.onlineshop.products.api.dto.ProductDto;
import io.github.onlineshop.products.api.dto.ProductPaginationRequest;
import io.github.onlineshop.products.domain.ProductService;
import io.github.onlineshop.products.domain.exception.ProductAlreadyExistsException;
import io.github.onlineshop.products.domain.exception.ProductInUseException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/admin/products")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminProductViewController {
    private final ProductService productService;

    @GetMapping
    public String listProducts(Model model) {
        List<ProductDto> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "admin/products/list";
    }

    @GetMapping("/{id}")
    public String getProductDetails(
        @PathVariable Long id,
        Model model
    ) {
        ProductDto productDto = productService.getProductById(id);
        model.addAttribute("product", productDto);
        return "admin/products/details";
    }

    @GetMapping("/new")
    public String createProductForm(Model model) {
        model.addAttribute(
            "product",
            new ProductDto(null, "", 0L, BigDecimal.ZERO)
        );
        model.addAttribute("isEdit", false);
        return "admin/products/form";
    }

    @GetMapping("/{id}/edit")
    public String editProductForm(
        @PathVariable Long id,
        Model model
    ) {
        ProductDto productToEdit = productService.getProductById(id);

        model.addAttribute("product", productToEdit);
        model.addAttribute("isEdit", true);

        return "admin/products/form";
    }

    @PostMapping
    public String createProduct(
        @ModelAttribute ProductDto productToCreate,
        RedirectAttributes redirectAttributes
    ) {
        try {
            ProductDto createdProduct =
                productService.createProduct(productToCreate);
            redirectAttributes.addFlashAttribute(
                "success",
                "Продукт успешно создан"
            );
        } catch(ProductAlreadyExistsException e) {
            redirectAttributes.addFlashAttribute(
                "error",
                "Ошибка: " + e.getMessage()
            );
        }

        return "redirect:/admin/products";
    }

    @PostMapping("/{id}")
    public String editProduct(
        @PathVariable Long id,
        @ModelAttribute ProductDto productToUpdate,
        RedirectAttributes redirectAttributes
    ) {
        try {
            ProductDto updatedProduct =
                productService.updateProduct(id, productToUpdate);
            redirectAttributes.addFlashAttribute(
                "success", "Продукт успешно обновлён"
            );
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute(
                "error",
                "Ошибка, товар не обновлён"
            );
        }

        return "redirect:/admin/products";
    }

    @PostMapping("/{id}/delete")
    public String deleteProduct(
        @PathVariable Long id,
        RedirectAttributes redirectAttributes
    ) {
        try {
            productService.deleteProduct(id);
            redirectAttributes.addFlashAttribute(
                "success",
                "Товар успешно удалён"
            );
        } catch(ProductInUseException e) {
            redirectAttributes.addFlashAttribute(
                "error",
                "Ошибка при удалении: " + e.getMessage()
            );
        }

        return "redirect:/admin/products";
    }
}
