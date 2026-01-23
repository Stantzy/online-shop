package io.github.onlineshop.products.domain;

import io.github.onlineshop.orders.database.OrderLineRepository;
import io.github.onlineshop.products.ProductMapper;
import io.github.onlineshop.products.api.dto.ProductDto;
import io.github.onlineshop.products.api.dto.ProductPaginationRequest;
import io.github.onlineshop.products.database.ProductEntity;
import io.github.onlineshop.products.database.ProductRepository;
import io.github.onlineshop.products.domain.exception.ProductAlreadyExistsException;
import io.github.onlineshop.products.domain.exception.ProductInUseException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private static final Logger log =
        LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final OrderLineRepository orderLineRepository;

    public List<ProductDto> getAllProducts() {
        log.info("Called method getAllProducts");

        List<ProductEntity> productEntities = repository.findAll();

        return productEntities.stream()
            .map(mapper::toProductDto)
            .toList();
    }

    public List<ProductDto> getAllProductsWithPaginationAndSorting(
        ProductPaginationRequest paginationRequest
    ) {
        log.info("Called method getAllProductsWithPaginationAndSorting");

        List<ProductEntity> productEntities = repository
            .findAll(paginationRequest.toPageable())
            .toList();

        return productEntities.stream()
            .map(mapper::toProductDto)
            .toList();
    }

    public ProductDto getProductById(Long id) {
        log.info("Called getProductById: id={}", id);

        ProductEntity productEntity = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
                "Not found product by id = " + id)
            );

        return mapper.toProductDto(productEntity);
    }

    public ProductDto createProduct(ProductDto productToCreate) {
        log.info("Called method createProduct");

        if(repository.existsByName(productToCreate.getName()))
            throw new ProductAlreadyExistsException(productToCreate.getName());

        ProductEntity productEntityToSave =
            mapper.toProductEntity(productToCreate);

        return mapper.toProductDto(repository.save(productEntityToSave));
    }

    public ProductDto updateProduct(Long id, ProductDto productToUpdate) {
        log.info("Called method updateProduct: id={}", id);

        ProductEntity entityToUpdate = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
                "Not found product by id = " + id)
            );

        if(productToUpdate.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price can't be negative");
        }

        if(productToUpdate.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity can't be negative");
        }

        entityToUpdate.setName(productToUpdate.getName());
        entityToUpdate.setQuantity(productToUpdate.getQuantity());
        entityToUpdate.setPrice(productToUpdate.getPrice());
        repository.save(entityToUpdate);

        return mapper.toProductDto(entityToUpdate);
    }

    public void deleteProduct(Long id) {
        log.info("Called method deleteProduct: id={}", id);

        validateProductCanBeDeleted(id);
        repository.deleteById(id);

        log.info("Product successfully deleted: id={}", id);
    }

    private void validateProductCanBeDeleted(Long id) {
        log.info("Called method validateProductCanBeDeleted: id={}", id);

        ProductEntity productEntity = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
                "Not found product by id = " + id)
            );

        boolean isInOrders =
            orderLineRepository.existsByProductEntity(productEntity);

        if(isInOrders)
            throw new ProductInUseException(productEntity.getName());
    }
}
