# Secure E-Commerce CRUD API

The **Secure E-Commerce CRUD API** is a **Spring Boot–based backend application** designed for **managing Products, Orders, and Users with Role-Based Access Control (RBAC).**

It provides **secure authentication & authorization using JWT and OAuth2 (Google & GitHub login)**, while ensuring **audit logging** for key business actions. The project also integrates **multi-database support (PostgreSQL + H2)** and is configured with **HTTPS/TLS** for secure communication.

This project is designed to showcase **enterprise-grade application design** with a focus on **security, scalability, and maintainability.**

---

## Features

### Authentication & Authorization

- **JWT-based Authentication (Stateless)** – Issue JWT tokens containing user roles & permissions.

- **OAuth2 Login (Google & GitHub)** – Session-based login for social authentication.

- **Role-Based Access Control (RBAC)** – Three roles with specific permissions:

    - **Customer** → View Products, View Orders, Add Orders.

    - **Seller** → View Products,  Create Products, Update Products, Delete Products, View Orders, View All Orders, Update Order Status

    - **Administrator** → Full access to users, products, and orders

- **Password Encryption** – User passwords secured using **BCryptPasswordEncoder.**

### Product Management API (CRUD)

- **GET /products** – View all products (Customer, Seller, Administrator).

- **GET /product/{id}** - View a particular product (Customer, Seller, Administrator).

- **POST /product** – Add new product (Seller, Administrator).

- **PUT /product** – Update product details (Seller, Administrator).

- **DELETE /product/{id}** – Delete product (Seller, Administrator).

- **Product attributes**: id, name, description, brand, price, category, releaseDate, available, quantity.

### Order Management API (CRUD)

- **GET /api/orders/my** – View all orders (Customer: see only their own orders).

- **GET /api/orders/{orderId}** - View a particular order (Customer, Seller, Administrator).

- **GET /api/orders** – View all orders (Seller, Administrator).

- **POST /api/orders** – Add new order (Customer).

- **PATCH /api/orders/{order_id}/status** - Update Order Status (Seller, Administrator)

- **Order attributes**: Order id, Product name, Quantity, Unit price, Total price, UserId, Status.

### User Management

- **POST /api/users** – Admin can create new users with roles (Customer, Seller, Administrator).

- **POST /register** – Customers can self-register.

- **POST /auth/login** - Authenticates a user and issues a JWT token based on their role (Customer, Seller, or Administrator).

- **GET /login/google** - Users can authenticate via their Google account using this OAuth2 login endpoint.

- **GET /login/github** - Users can authenticate via their GitHub account using this OAuth2 login endpoint.

- **Role enforcement** on all APIs.

### Audit Logging

- Every action is tracked:

        ADD_ORDERS, UPDATE_ORDER_STATUS, CREATE_PRODUCT, UPDATE_PRODUCT, DELETE_PRODUCT, CREATE_USER

- **Audit Log Schema**: id, userId, action, productId, timestamp.
- **Order Audit Log Schema**: id, userId, action, orderId, timestamp.

- Stored in **Postgres** for durability.

### Security

- **HTTPS Enforcement** – Redirect HTTP → HTTPS automatically.

- **TLS/SSL Certificates** for secure communication.

- **Audit Logging** for all user actions.

- **OAuth2 + JWT Hybrid Security.**

### Database Setup

- **H2 (In-memory)** → Product Data.

- **Postgres** → Orders, Users & Audit Logs.

- **data.sql** → Preloads sample products for testing.

## Tech Stack

- **Java** 

- **Spring Boot / Spring Framework**

- **Spring Web (REST API)**

- **Spring Security (JWT + OAuth2 + RBAC)**

- **Spring Data JPA** (H2 + PostgreSQL)

- **Maven** (Dependency Management)

- **Postman** (API Testing)

- **Git** (Version Control)

## Project Setup

#### Clone the Repository

```
git clone https://github.com/your-username/ECommerce-Secure-CRUD-API-SpringBoot-MultiDB-JWT-RBAC-OAuth2.git
cd ECommerce-Secure-CRUD-API-SpringBoot-MultiDB-JWT-RBAC-OAuth2
```
#### Configure Databases

- **Postgres**: Create a database library_db.

- Update application.properties:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/library_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```
- **H2**: Auto-configured for Product data (no setup needed).

#### Generate Keystore for HTTPS
```
keytool -genkeypair -alias libraryapi -keyalg RSA -keysize 2048 \
  -storetype PKCS12 -keystore keystore.p12 -validity 3650
```

- Place keystore.p12 in src/main/resources.

- Update application.properties:
```
server.ssl.enabled=true
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=your_password
server.ssl.key-store-type=PKCS12
server.port=8443
```

#### Generate Certificates for Postman Testing

- Extract public certificate:
```
openssl pkcs12 -in keystore.p12 -clcerts -nokeys -out mycert.pem
```
- import **mycert.pem** in Postman:

    - Go to **Settings → Certificates → CA Certificates**

    - Upload mycert.pem.

#### Enable Postman Settings

- Go to **Postman Settings → General.**

- Enable **Follow Original HTTP Method.**

#### Run the Project
```
mvn spring-boot:run
```
Server runs at: https://localhost:8443




## API Testing (Postman)
### Authentication

- **Login Endpoint** -> POST /auth/login

Request
```
{
  "username": "admin",
  "password": "admin123"
}
```

Response
```
{
  "access_token": "jwt_token",
  "expires_in": 3600
}
```

Use **Authorization: Bearer <token>** in all secured requests.

**Note**: Any role (Customer, Seller, Administrator) can login via /auth/login and receive a valid JWT token. The **role determines access** to other endpoints.


## Default Data

- Preloaded via data.sql.

- Example Product:
```
INSERT INTO product (name, description, brand, price, category, releaseDate, available, quantity) VALUES
('Tata Nexon', 'A compact SUV with excellent safety features and performance.', 'Tata Motors', 750000.00, 'Cars', '2024-01-15', true, 50);
```

## Audit Logs

- Stored in Postgres.

- Example record:
```
{
  "id": 1,
  "action": "CREATE_PRODUCT",
  "product_id": 10,
  "timestamp": "2025-08-20T10:15:30",
  "user_id": 3
}
```

## Highlights

- Multi-Database Integration (**H2 + PostgreSQL**)
- Secure API with **HTTPS + TLS**
- **JWT + OAuth2 Hybrid Authentication**
- Role-Based Access Control (RBAC)
- **BCrypt Password Hashing**
- Default data loading with data.sql
- Comprehensive **Audit Logging**
- **Postman Ready** with HTTPS certificate setup

## Why this project?

This project demonstrates proficiency in:

- **Spring Framework (Core, Boot, Web, Security, Data JPA)**

- **Enterprise Security (JWT, OAuth2, RBAC, HTTPS, BCrypt)**

- **Database Management (H2 for dev/test, PostgreSQL for persistence)**

- **REST API Design & Documentation**

- **Testing & API Tooling (Postman)**

- **Best Practices (Audit Logs, HTTPS, Data Preloading)**

This project reflects real-world challenges in **enterprise application development** and proves capability to handle them effectively.

### Author

**Prem Swaroop** – Java Developer passionate about **secure, scalable, and maintainable enterprise solutions.**

### License

MIT License – feel free to use, learn, and extend.
