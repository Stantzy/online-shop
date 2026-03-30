CREATE TABLE IF NOT EXISTS users (
     id BIGSERIAL PRIMARY KEY,
     username VARCHAR(255) NOT NULL UNIQUE,
     email VARCHAR(255) NOT NULL UNIQUE,
     hash VARCHAR(255) NOT NULL,
     role VARCHAR(255) NOT NULL,
     registration_date DATE NOT NULL,
     CONSTRAINT users_role_check CHECK (role IN ('USER', 'ADMIN'))
);

CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    price NUMERIC(38,2) NOT NULL,
    quantity BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS orders (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    status VARCHAR(255) NOT NULL,
    CONSTRAINT orders_status_check CHECK (status IN ('CART', 'CREATED', 'APPROVED', 'CANCELLED')),
    CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS order_lines (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity BIGINT NOT NULL,
    price_at_time NUMERIC(10,2) NOT NULL,
    CONSTRAINT fk_order_lines_order FOREIGN KEY (order_id) REFERENCES orders(id),
    CONSTRAINT fk_order_lines_product FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE IF NOT EXISTS order_items (
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders(id),
    CONSTRAINT fk_order_items_product FOREIGN KEY (product_id) REFERENCES products(id)
);
