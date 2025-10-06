package io.github.onlineshop.products;

import io.github.onlineshop.products.api.dto.ProductDto;
import io.github.onlineshop.products.database.ProductEntity;
import io.github.onlineshop.products.domain.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDto toProductDto(Product product) {
        return new ProductDto(
                product.name(),
                product.quantity(),
                product.price()
        );
    }

    public ProductDto toProductDto(ProductEntity productEntity) {
        return new ProductDto(
                productEntity.getName(),
                productEntity.getQuantity(),
                productEntity.getPrice()
        );
    }

    public Product toDomainProduct(ProductDto productDto) {
        return new Product(
                null,
                productDto.name(),
                productDto.quantity(),
                productDto.price()
        );
    }

    public Product toDomainProduct(ProductEntity productEntity) {
        return new Product(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getQuantity(),
                productEntity.getPrice()
        );
    }

    public ProductEntity toProductEntity(ProductDto productDto) {
        ProductEntity productEntity = new ProductEntity();

        productEntity.setId(null);
        productEntity.setName(productDto.name());
        productEntity.setQuantity(productDto.quantity());
        productEntity.setPrice(productDto.price());

        return productEntity;
    }

    public ProductEntity toProductEntity(Product product) {
        ProductEntity productEntity = new ProductEntity();

        productEntity.setId(product.id());
        productEntity.setName(product.name());
        productEntity.setQuantity(product.quantity());
        productEntity.setPrice(product.price());

        return productEntity;
    }
}
