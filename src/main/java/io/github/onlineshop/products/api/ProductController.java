package io.github.onlineshop.products.api;

import io.github.onlineshop.constants.PathConstants;
import io.github.onlineshop.products.api.dto.ProductDto;
import io.github.onlineshop.products.api.dto.ProductPaginationRequest;
import io.github.onlineshop.products.domain.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(PathConstants.PRODUCT)
@Validated
public class ProductController {
    private static final Logger log =
        LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(
        ProductService productService
    ) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(
        @Valid ProductPaginationRequest paginationRequest
    ) {
        log.info(
            "Called method getAllProducts: sortedBy={}, orderedBy={}, " +
            "page={}, pageSize={}",
            paginationRequest.sortBy(),
            paginationRequest.sortDirection(),
            paginationRequest.pageNumber(),
            paginationRequest.pageSize()
        );

        return ResponseEntity.ok(
            productService.getAllProductsWithPaginationAndSorting(
                paginationRequest
            )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(
        @PathVariable Long id
    ) {
        log.info("Called getProductById: id={}", id);

        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
        @RequestBody ProductDto productToCreate
    ) {
        log.info("Called method createProduct");
        return ResponseEntity.ok(productService.createProduct(productToCreate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
        @PathVariable Long id,
        @RequestBody ProductDto productToUpdate
    ) {
        log.info("Called method updateProduct: id={}", id);

        ProductDto updatedProduct =
            productService.updateProduct(id, productToUpdate);

        return ResponseEntity.ok(updatedProduct);
    }

    @PutMapping("/{id}/update_price")
    public ResponseEntity<ProductDto> updateProductPrice(
        @PathVariable Long id,
        @RequestParam(name = "newPrice") BigDecimal newPrice
    ) {
        log.info("Called updateProductPrice: id={}, newPrice={}", id, newPrice);

        return ResponseEntity
            .ok(productService.updateProductPrice(id, newPrice));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteProduct(
        @PathVariable Long id
    ) {
        log.info("Called method deleteProduct: id={}", id);
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
