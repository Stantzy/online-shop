package io.github.onlineshop.products;

import io.github.onlineshop.products.api.dto.ProductDto;
import io.github.onlineshop.products.database.ProductEntity;
import io.github.onlineshop.products.database.ProductRepository;
import io.github.onlineshop.products.domain.ProductService;
import io.github.onlineshop.products.domain.exception.ProductAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository repository;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private ProductService productService;

    private static final Long VALID_ID = 1L;
    private static final Long INVALID_ID = -1L;
    private static final String NAME = "Hat";
    private static final Long QUANTITY = 10L;
    private static final BigDecimal PRICE = BigDecimal.valueOf(100);

    /* ==== CREATE PRODUCT ====== */
    @Test
    void createProduct_validData_productCreated() {
        // Arrange
        ProductDto inputDto = new ProductDto(NAME, QUANTITY, PRICE);

        ProductEntity entityToSave = new ProductEntity();
        entityToSave.setName(NAME);
        entityToSave.setQuantity(QUANTITY);
        entityToSave.setPrice(PRICE);

        ProductEntity savedEntity = new ProductEntity();
        entityToSave.setId(1L);
        entityToSave.setName(NAME);
        entityToSave.setQuantity(QUANTITY);
        entityToSave.setPrice(PRICE);

        ProductDto expectedDto = new ProductDto(NAME, QUANTITY, PRICE);

        when(mapper.toProductEntity(inputDto)).thenReturn(entityToSave);
        when(repository.save(entityToSave)).thenReturn(savedEntity);
        when(mapper.toProductDto(savedEntity)).thenReturn(expectedDto);

        // Act
        ProductDto result = productService.createProduct(inputDto);

        // Assert
        assertNotNull(result);
        assertEquals(NAME, result.name());
        assertEquals(QUANTITY, result.quantity());
        assertEquals(PRICE, result.price());

        verify(mapper).toProductEntity(inputDto);
        verify(repository).save(entityToSave);
        verify(mapper).toProductDto(savedEntity);
    }

    @Test
    void createProduct_duplicate_throwsException() {
        // Arrange
        ProductDto inputDto = new ProductDto(NAME, QUANTITY, PRICE);

        ProductEntity entityToSave = new ProductEntity();
        entityToSave.setName(NAME);
        entityToSave.setQuantity(QUANTITY);
        entityToSave.setPrice(PRICE);

        when(repository.existsByName(inputDto.name())).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() ->productService.createProduct(inputDto))
            .isInstanceOf(ProductAlreadyExistsException.class);

        verify(repository).existsByName(inputDto.name());
        verify(mapper, never()).toProductDto((ProductEntity) any());

    }

    /* ==== GET PRODUCT BY ID ====== */
    @Test
    void getProductById_existingId_productReturned() {
        // Arrange
        ProductEntity foundEntity = new ProductEntity();
        foundEntity.setId(VALID_ID);
        foundEntity.setName(NAME);
        foundEntity.setQuantity(QUANTITY);
        foundEntity.setPrice(PRICE);
        Optional<ProductEntity> optFoundEntity = Optional.of(foundEntity);
        ProductDto expectedDto = new ProductDto(NAME, QUANTITY, PRICE);

        when(repository.findById(VALID_ID)).thenReturn(optFoundEntity);
        when(mapper.toProductDto(optFoundEntity.get())).thenReturn(expectedDto);

        // Act
        ProductDto result = productService.getProductById(VALID_ID);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo(NAME);
        assertThat(result.quantity()).isEqualTo(QUANTITY);
        assertThat(result.price()).isEqualByComparingTo(PRICE);

        verify(repository).findById(VALID_ID);
        verify(mapper).toProductDto(optFoundEntity.get());
    }

    @Test
    void getProductById_nonExistingId_throwsException() {
        // Arrange
        when(repository.findById(INVALID_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> productService.getProductById(INVALID_ID))
            .isInstanceOf(EntityNotFoundException.class);

        verify(repository).findById(INVALID_ID);
        verify(mapper, never()).toProductDto((ProductEntity)any());
    }

    @Test
    void getProductById_nullId_throwsException() {
        // Arrange
        when(repository.findById(null))
           .thenThrow(new IllegalArgumentException());

        // Act & Assert
        assertThatThrownBy(() -> productService.getProductById(null))
            .isInstanceOf(IllegalArgumentException.class);

        verify(repository).findById(null);
        verify(mapper, never()).toProductDto((ProductEntity)any());
    }
}
