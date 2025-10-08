-- Users
INSERT INTO users (id, username, email, hash, registration_date) VALUES
    (1, 'alice', 'alice@mail.com', 'hash1', '2025-10-08'),
    (2, 'bob', 'bob@mail.com', 'hash2', '2025-10-07'),
    (3, 'charlie', 'charlie@mail.com', 'hash3', '2025-10-06'),
    (4, 'david', 'david@mail.com', 'hash4', '2025-10-05'),
    (5, 'eva', 'eva@mail.com', 'hash5', '2025-10-04'),
    (6, 'frank', 'frank@mail.com', 'hash6', '2025-10-03'),
    (7, 'grace', 'grace@mail.com', 'hash7', '2025-10-02'),
    (8, 'henry', 'henry@mail.com', 'hash8', '2025-10-01'),
    (9, 'isabel', 'isabel@mail.com', 'hash9', '2025-09-30'),
    (10, 'jack', 'jack@mail.com', 'hash10', '2025-09-29');

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
    (1, 1, 'CANCELLED'),
    (2, 2, 'CREATED'),
    (3, 3, 'CREATED'),
    (4, 4, 'APPROVED'),
    (5, 5, 'CANCELLED'),
    (6, 6, 'APPROVED');

-- Order Items
INSERT INTO order_items (order_id, product_id) VALUES
    (1, 1),
    (1, 5),
    (1, 6),
    (2, 2),
    (2, 3),
    (3, 4),
    (3, 7),
    (4, 1),
    (4, 8),
    (5, 10),
    (5, 12),
    (6, 15),
    (6, 16);