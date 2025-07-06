-- =========================================
-- ChangeSet: 001_create_product_table
-- Description: Create 'product' table to store product catalog details.
-- Author: Anjaniy Salekar
-- Date: 2025-07-06
-- =========================================
CREATE TABLE products (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(100) NOT NULL UNIQUE,
description VARCHAR(255) NOT NULL,
price DECIMAL(10, 2) NOT NULL CHECK (price > 0),
stock INT NOT NULL CHECK (stock > 0),
version INT DEFAULT 0 NOT NULL
);
-- =========================================
-- ChangeSet: 002_create_user_table
-- Description: Create 'user' table to store customer information
-- Author: Anjaniy Salekar
-- Date: 2025-07-06
-- =========================================
CREATE TABLE users (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
first_name VARCHAR(20) NOT NULL,
last_name VARCHAR(20) NOT NULL,
email VARCHAR(30) NOT NULL UNIQUE,
password VARCHAR(15) NOT NULL CHECK (LENGTH(password) >= 8)
);
-- =========================================
-- ChangeSet: 003_create_orders_table
-- Description: Create 'orders' table to store placed orders
-- Author: Anjaniy Salekar
-- Date: 2025-07-06
-- =========================================

CREATE TABLE orders (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
user_id BIGINT NOT NULL,
product_id BIGINT NOT NULL,
quantity INT NOT NULL CHECK (quantity > 0),
total_amount DECIMAL(10, 2) NOT NULL CHECK (total_amount > 0),
status VARCHAR(10) NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id),
CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products(id)
);
-- =========================================