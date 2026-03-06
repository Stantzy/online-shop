# Online Shop API
## About

This project is the backend for an online shop. It was created to improve my skills in building RESTful APIs with Java and Spring Boot.

**Why this project?**
I wanted to understand how REST APIs work under the hood and gain hands-on experience building e-commerce backend from scratch.

**Important:** This project is in under active development and serves as my personal sandbox for experimenting and learning.

---
## Technologies

- Java 21
- Spring Boot (Web, Data JPA, Validation, Security)
- PostgreSQL
- Docker
- Maven
- Lombok

---
## Functionality

### Authentication

**Public:**
- `POST /api/auth/login` - user login
- `POST /api/auth/register` - user registration

### Users

**Admin:**
- `GET /api/users` - get all users (with pagination & sorting)
- `GET /api/users/{id}` - get user by ID
- `POST /api/users` - create a new user
- `PUT /api/users/{id}/update` - update a user
- `DELETE /api/users/{id}/delete` - delete a user

**Authenticated:**
- `GET /api/users/me` - get current user profile
- `PUT /api/users/me` - update current user
- `PUT /api/users/change_password` - change password

### Products

**Admin:**
- `POST /api/products` - create a new product
- `PUT /api/products/{id}` - update an existing product
- `DELETE /api/products/{id}/delete` - delete an existing product

**Public:**
- `GET /api/products` - get all products (with pagination & sorting)
- `GET /api/products/{id}` - get product by ID

### Cart & Orders

**Admin:**
- `GET /api/orders` - get all orders
- `GET /api/orders/{id}` - get order by ID
- `DELETE /api/orders/{id}` - delete order by ID

**Authenticated:**
- `GET /api/orders/cart/items` - get user's cart info
- `POST /api/orders/cart/items` - add item to cart
- `DELETE /api/orders/cart/items` - delete user's cart
- `PUT /api/orders/cart/items/{orderLineId}?newQuantity={}` -  change item quantity
- `DELETE /api/orders/cart/items/{orderLineId}` - remove item from cart

---
