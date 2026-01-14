package io.github.onlineshop.products;

import io.github.onlineshop.products.api.dto.ProductDto;
import io.github.onlineshop.products.database.ProductEntity;
import io.github.onlineshop.products.domain.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDto toProductDto(Product product) {
        return new ProductDto(
            product.getId(),
            product.getName(),
            product.getQuantity(),
            product.getPrice()
        );
    }

    public ProductDto toProductDto(ProductEntity productEntity) {
        return new ProductDto(
            productEntity.getId(),
            productEntity.getName(),
            productEntity.getQuantity(),
            productEntity.getPrice()
        );
    }

    public Product toDomainProduct(ProductDto productDto) {
        return new Product(
            productDto.id(),
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

        productEntity.setId(productDto.id());
        productEntity.setName(productDto.name());
        productEntity.setQuantity(productDto.quantity());
        productEntity.setPrice(productDto.price());

        return productEntity;
    }

    public ProductEntity toProductEntity(Product product) {
        ProductEntity productEntity = new ProductEntity();

        productEntity.setId(product.getId());
        productEntity.setName(product.getName());
        productEntity.setQuantity(product.getQuantity());
        productEntity.setPrice(product.getPrice());

        return productEntity;
    }
}
