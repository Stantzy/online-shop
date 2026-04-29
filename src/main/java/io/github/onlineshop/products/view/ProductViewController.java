package io.github.onlineshop.products.view;

import io.github.onlineshop.products.api.dto.ProductDto;
import io.github.onlineshop.products.api.dto.ProductPaginationRequest;
import io.github.onlineshop.products.domain.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductViewController {

    private final ProductService productService;

    @GetMapping
    public String listProducts(
        ProductPaginationRequest paginationRequest,
        Model model
    ) {

        List<ProductDto> products = productService.getAllProductsWithPaginationAndSorting(paginationRequest);

        model.addAttribute("products", products);
        model.addAttribute("currentPage", paginationRequest.pageNumber());
        model.addAttribute("pageSize", paginationRequest.pageSize());
        model.addAttribute("sortBy", paginationRequest.sortBy());
        model.addAttribute("sortDirection", paginationRequest.sortDirection());

        return "products/list";
    }

    @GetMapping("/{id}")
    public String productDetails(@PathVariable Long id, Model model) {
        ProductDto product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "products/details";
    }
}
