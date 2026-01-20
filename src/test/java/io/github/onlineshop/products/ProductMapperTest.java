package io.github.onlineshop.products;

import io.github.onlineshop.products.api.dto.ProductDto;
import io.github.onlineshop.products.database.ProductEntity;
import io.github.onlineshop.products.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductMapperTest {
    private static final Long DEFAULT_ID = 1L;
    private static final Long DEFAULT_QUANTITY = 100L;
    private static final BigDecimal DEFAULT_PRICE = BigDecimal.valueOf(50L);

    private ProductMapper productMapper;

    @BeforeEach
    void setProductMapper() {
        productMapper = new ProductMapper();
    }

    @Test
    void toProductDto_validProductDomain_shouldReturnCorrectDto() {
        Product input = initTestProduct();
        ProductDto expected = new ProductDto(
            input.getId(),
            input.getName(),
            input.getQuantity(),
            input.getPrice()
        );

        ProductDto result = productMapper.toProductDto(input);

        assertEquals(result.id(), expected.id());
        assertEquals(result.name(), expected.name());
        assertEquals(result.quantity(), expected.quantity());
        assertEquals(result.price(), expected.price());
        assertEquals(expected, result);
    }

    @Test
    void toDomainProduct_validDto_shouldReturnCorrectProductDomain() {
        ProductDto input = initTestProductDto();
        Product expected = new Product(
            input.id(),
            input.name(),
            input.quantity(),
            input.price()
        );

        Product result = productMapper.toDomainProduct(input);

        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getQuantity(), result.getQuantity());
        assertEquals(expected.getPrice(), result.getPrice());
    }

    @Test
    void toProductDto_validProductEntity_shouldReturnCorrectDto() {
        ProductEntity input = initTestProductEntity();
        ProductDto expected = new ProductDto(
            input.getId(),
            input.getName(),
            input.getQuantity(),
            input.getPrice()
        );

        ProductDto result = productMapper.toProductDto(input);

        assertEquals(result.id(), expected.id());
        assertEquals(result.name(), expected.name());
        assertEquals(result.quantity(), expected.quantity());
        assertEquals(result.price(), expected.price());
        assertEquals(expected, result);
    }

    @Test
    void toDomainProduct_validProductEntity_shouldReturnCorrectDomainProduct() {
        ProductEntity input = initTestProductEntity();
        Product expected = new Product(
            input.getId(),
            input.getName(),
            input.getQuantity(),
            input.getPrice()
        );

        Product result = productMapper.toDomainProduct(input);

        assertEquals(result.getId(), expected.getId());
        assertEquals(result.getName(), expected.getName());
        assertEquals(result.getQuantity(), expected.getQuantity());
        assertEquals(result.getPrice(), expected.getPrice());
    }

    @Test
    void toProductEntity_validDto_shouldReturnCorrectEntity() {
        ProductDto input = initTestProductDto();
        ProductEntity expected = new ProductEntity();
        expected.setId(input.id());
        expected.setName(input.name());
        expected.setQuantity(input.quantity());
        expected.setPrice(input.price());

        ProductEntity result = productMapper.toProductEntity(input);

        assertEquals(result.getId(), expected.getId());
        assertEquals(result.getName(), expected.getName());
        assertEquals(result.getQuantity(), expected.getQuantity());
        assertEquals(result.getPrice(), expected.getPrice());
    }

    @Test
    void toProductEntity_validDomain_shouldReturnCorrectEntity() {
        Product input = initTestProduct();
        ProductEntity expected = new ProductEntity();
        expected.setId(input.getId());
        expected.setName(input.getName());
        expected.setQuantity(input.getQuantity());
        expected.setPrice(input.getPrice());

        ProductEntity result = productMapper.toProductEntity(input);

        assertEquals(result.getId(), expected.getId());
        assertEquals(result.getName(), expected.getName());
        assertEquals(result.getQuantity(), expected.getQuantity());
        assertEquals(result.getPrice(), expected.getPrice());
    }

    private static ProductDto initTestProductDto() {
        return new ProductDto(
            DEFAULT_ID,
            "ProductDtoTest",
            DEFAULT_QUANTITY,
            DEFAULT_PRICE
        );
    }

    private static Product initTestProduct() {
        return new Product(
            DEFAULT_ID,
            "ProductTest",
            DEFAULT_QUANTITY,
            DEFAULT_PRICE
        );
    }

    private static ProductEntity initTestProductEntity() {
        ProductEntity productEntity = new ProductEntity();

        productEntity.setId(DEFAULT_ID);
        productEntity.setName("ProductEntityTest");
        productEntity.setQuantity(DEFAULT_QUANTITY);
        productEntity.setPrice(DEFAULT_PRICE);

        return productEntity;
    }
}
