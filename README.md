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
## Screenshots

Menu page      |  Product page
:------------------------:|:-------------------------:
<img width="1903" height="928" alt="menu_page" src="https://github.com/user-attachments/assets/798da064-1aea-4830-9c53-0a139ea7047d" /> |  <img width="1903" height="928" alt="product_page" src="https://github.com/user-attachments/assets/e44f36b9-09b6-4d17-936d-878e95120ddd" />

Cart  |  Admin Panel
:------------------------:|:-------------------------:
<img width="1920" height="926" alt="cart_page" src="https://github.com/user-attachments/assets/17221848-7572-49da-b566-a30508eedd79" /> | <img width="1920" height="926" alt="admin_panel" src="https://github.com/user-attachments/assets/e6efb02c-c581-45b2-9cff-b6d675f1f392" />

Login page  |  Register page
:------------------------:|:-------------------------:
<img width="1920" height="929" alt="login_page" src="https://github.com/user-attachments/assets/c0c3f184-3fa8-4e5c-9243-7ea219f25605" /> | <img width="1920" height="926" alt="register_page" src="https://github.com/user-attachments/assets/170315ce-83a2-4f99-8589-38e49a84ed55" />

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
