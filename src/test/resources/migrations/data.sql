-- =========================================
-- ChangeSet: 001_insert_sample_products
-- Description: Insert 10 sample products into 'product' table for testing
-- Author: Anjaniy Salekar
-- Date: 2025-07-06
-- =========================================
INSERT INTO products (name, description, price, stock) VALUES
('Wireless Mouse', 'Bluetooth mouse with ergonomic design', 499.99, 50),
('Mechanical Keyboard', 'RGB backlit gaming keyboard', 2999.00, 30),
('USB-C Hub', 'Multiport hub with HDMI, USB, SD card', 1599.50, 20),
('Laptop Stand', 'Adjustable aluminum stand', 999.99, 40),
('Noise Cancelling Headphones', 'Over-ear wireless headphones', 4999.00, 15),
('Smartwatch', 'Fitness tracker with heart-rate monitor', 2499.75, 25),
('External SSD', '1TB USB 3.1 Solid State Drive', 7499.00, 10),
('Webcam 1080p', 'Full HD webcam with mic', 1799.00, 18),
('Portable Monitor', '15.6-inch external display', 8999.00, 12),
('Wireless Charger', 'Fast charging pad for smartphones', 699.00, 35);
-- =========================================
-- ChangeSet: 002_insert_sample_users
-- Description: Insert 10 sample users into 'users' table for testing
-- Author: Anjaniy Salekar
-- Date: 2025-07-06
-- =========================================
INSERT INTO users (first_name, last_name, email, password) VALUES
('Amit', 'Sharma', 'amit.sharma@example.com', 'password123'),
('Neha', 'Verma', 'neha.verma@example.com', 'securepass'),
('Raj', 'Patel', 'raj.patel@example.com', 'mypassword'),
('Sneha', 'Reddy', 'sneha.reddy@example.com', 'pass@4567'),
('Vikram', 'Gupta', 'vikram.gupta@example.com', 'qwerty123');
-- =========================================