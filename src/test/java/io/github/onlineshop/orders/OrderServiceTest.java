package io.github.onlineshop.orders;

import io.github.onlineshop.orders.database.OrderLineRepository;
import io.github.onlineshop.orders.database.OrderRepository;
import io.github.onlineshop.orders.domain.OrderService;
import io.github.onlineshop.products.ProductMapper;
import io.github.onlineshop.products.database.ProductRepository;
import io.github.onlineshop.security.jwt.JwtTokenUtils;
import io.github.onlineshop.users.UserMapper;
import io.github.onlineshop.users.database.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    private OrderMapper orderMapper;
    private OrderLineMapper orderLineMapper;
    private ProductMapper productMapper;
    private OrderRepository orderRepository;
    private OrderLineRepository orderLineRepository;
    private UserRepository userRepository;
    private UserMapper userMapper;
    private ProductRepository productRepository;
    private JwtTokenUtils jwtTokenUtils;

    @InjectMocks
    private OrderService orderService;

    @Test
    void addItemToCart_validData_shouldAddItemToCart() {
        // Arrange

        // Act

        // Assert
    }

    /*
        public OrderAddToCartResponse addItemToCart(
        OrderAddToCartRequest request,
        String authHeader
    ) {
        log.info(
            "Called method addItemToCart: productId={}, quantity={}",
            request.productId(), request.quantity()
        );

        Long userId = getUserIdFromAuthHeader(authHeader);

        // getUserOrThrow()
        UserEntity userEntity = userRepository.findById(userId)
            .orElseThrow(
                () -> new EntityNotFoundException(
                    "Not found user by id=" + userId
                )
            );
        User userDomain = userMapper.toDomainUser(userEntity);

        Order cart = findOrCreateCart(userEntity);

        // getProductOrThrow()
        ProductEntity productEntity =
            productRepository.findById(request.productId())
                .orElseThrow(
                    () -> new EntityNotFoundException(
                        "Not found product with id=" + request.productId()
                    )
                );
        Product domainProduct = productMapper.toDomainProduct(productEntity);

        domainProduct.checkProductAvailability(request.quantity());

        OrderLine orderLine = new OrderLine(
            null,
            domainProduct,
            domainProduct.getPrice(),
            request.quantity(),
            cart
        );

        List<OrderLine> lines = new ArrayList<>(cart.getOrderLines());
        lines.add(orderLine);
        cart.setOrderLines(lines);

        domainProduct.decreaseQuantity(request.quantity());

        orderRepository.save(orderMapper.toOrderEntity(cart, userEntity));
        productRepository.save(productMapper.toProductEntity(domainProduct));

        List<ProductDto> productDtoList = cart.getOrderLines().stream()
            .map(OrderLine::getProduct)
            .map(productMapper::toProductDto)
            .toList();

        return new OrderAddToCartResponse(
            cart.getId(),
            cart.getTotalItems(),
            cart.getTotalPrice(),
            productDtoList
        );
    }
     */
}
