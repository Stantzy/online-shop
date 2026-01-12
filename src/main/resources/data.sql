-- Test Data
-- Users
INSERT INTO users (username, email, hash, registration_date, role) VALUES
    ('admin', 'admin@mail.com', '$2a$10$9wgrQL3pS.CoSPQXTO4kj.ZkO2.gRBTjlzT73pXSOiVC7URycRoTy', '2025-01-01', 'ADMIN'),
    ('alice', 'alice@mail.com', '$2a$10$9wgrQL3pS.CoSPQXTO4kj.ZkO2.gRBTjlzT73pXSOiVC7URycRoTy', '2025-10-08', 'USER'),
    ('bob', 'bob@mail.com', '$2a$10$9wgrQL3pS.CoSPQXTO4kj.ZkO2.gRBTjlzT73pXSOiVC7URycRoTy', '2025-10-07', 'USER'),
    ('charlie', 'charlie@mail.com', '$2a$10$9wgrQL3pS.CoSPQXTO4kj.ZkO2.gRBTjlzT73pXSOiVC7URycRoTy', '2025-10-06', 'USER'),
    ('david', 'david@mail.com', '$2a$10$9wgrQL3pS.CoSPQXTO4kj.ZkO2.gRBTjlzT73pXSOiVC7URycRoTy', '2025-10-05', 'USER'),
    ('eva', 'eva@mail.com', '$2a$10$9wgrQL3pS.CoSPQXTO4kj.ZkO2.gRBTjlzT73pXSOiVC7URycRoTy', '2025-10-04', 'USER'),
    ('frank', 'frank@mail.com', '$2a$10$9wgrQL3pS.CoSPQXTO4kj.ZkO2.gRBTjlzT73pXSOiVC7URycRoTy', '2025-10-03', 'USER'),
    ('grace', 'grace@mail.com', '$2a$10$9wgrQL3pS.CoSPQXTO4kj.ZkO2.gRBTjlzT73pXSOiVC7URycRoTy', '2025-10-02', 'USER'),
    ('henry', 'henry@mail.com', '$2a$10$9wgrQL3pS.CoSPQXTO4kj.ZkO2.gRBTjlzT73pXSOiVC7URycRoTy', '2025-10-01', 'USER'),
    ('isabel', 'isabel@mail.com', '$2a$10$9wgrQL3pS.CoSPQXTO4kj.ZkO2.gRBTjlzT73pXSOiVC7URycRoTy', '2025-09-30', 'USER'),
    ('jack', 'jack@mail.com', '$2a$10$9wgrQL3pS.CoSPQXTO4kj.ZkO2.gRBTjlzT73pXSOiVC7URycRoTy', '2025-09-29', 'USER');

-- Products
INSERT INTO products (id, name, price, quantity) VALUES
    (1, 'T-shirt', 20.0, 50),
    (2, 'Jeans', 50.0, 30),
    (3, 'Sneakers', 80.0, 20),
    (4, 'Jacket', 100.0, 15),
    (5, 'Hat', 15.0, 60),
    (6, 'Socks', 5.0, 100),
    (7, 'Belt', 25.0, 40),
    (8, 'Scarf', 18.0, 35),
    (9, 'Gloves', 22.0, 25),
    (10, 'Dress', 70.0, 20),
    (11, 'Skirt', 45.0, 25),
    (12, 'Blouse', 35.0, 30),
    (13, 'Sweater', 55.0, 20),
    (14, 'Shorts', 30.0, 40),
    (15, 'Coat', 120.0, 10),
    (16, 'Boots', 90.0, 15),
    (17, 'Sandals', 35.0, 30),
    (18, 'Shirt', 40.0, 25),
    (19, 'Jeans Jacket', 85.0, 20),
    (20, 'Hoodie', 60.0, 25);

-- Orders
INSERT INTO orders (id, user_id, status) VALUES
    (1, 1, 'CART'),
    (2, 2, 'CART'),
    (3, 3, 'CREATED'),
    (4, 4, 'APPROVED'),
    (5, 5, 'CANCELLED'),
    (6, 6, 'APPROVED');

-- Order Lines
INSERT INTO order_lines (id, product_id, price_at_time, quantity, order_id) VALUES
    (1, 1, 20.0, 5, 1),
    (2, 2, 50.0, 10, 2);