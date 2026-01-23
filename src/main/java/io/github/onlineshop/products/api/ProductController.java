package io.github.onlineshop.products.api;

import io.github.onlineshop.constants.PathConstants;
import io.github.onlineshop.products.api.dto.ProductDto;
import io.github.onlineshop.products.api.dto.ProductPaginationRequest;
import io.github.onlineshop.products.domain.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PathConstants.PRODUCT)
@Validated
@RequiredArgsConstructor
public class ProductController {
    private static final Logger log =
        LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

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
        @PathVariable @NotNull Long id
    ) {
        log.info("Called getProductById: id={}", id);

        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
        @RequestBody @Valid ProductDto productToCreate
    ) {
        log.info("Called method createProduct");
        return ResponseEntity.ok(productService.createProduct(productToCreate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
        @PathVariable @NotNull Long id,
        @RequestBody @Valid ProductDto productToUpdate
    ) {
        log.info("Called method updateProduct: id={}", id);

        ProductDto updatedProduct =
            productService.updateProduct(id, productToUpdate);

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteProduct(
        @PathVariable @NotNull Long id
    ) {
        log.info("Called method deleteProduct: id={}", id);
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
