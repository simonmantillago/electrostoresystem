DROP DATABASE IF EXISTS  electrostore;

CREATE DATABASE electrostore;
USE electrostore;

CREATE TABLE categories (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE brands (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    min_stock INT NOT NULL,
    category_id INT,
    brand_id INT,
    FOREIGN KEY (category_id) REFERENCES categories(id),
    FOREIGN KEY (brand_id) REFERENCES brands(id)
);

CREATE TABLE id_types(
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_name VARCHAR (20) NOT NULL
);

CREATE TABLE client_types(
    id INT PRIMARY KEY AUTO_INCREMENT,
    type_name VARCHAR (20) NOT NULL
);

CREATE TABLE clients (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type_id INT,
    client_type INT,
    email VARCHAR(255) NULL,
    address VARCHAR(255) NULL,
    FOREIGN KEY (type_id) REFERENCES id_types(id),
    FOREIGN KEY (client_type) REFERENCES client_types(id)
);

CREATE TABLE suppliers(
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NULL
);

CREATE TABLE payment_methods(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL
);

CREATE TABLE sale_status(
    id INT PRIMARY KEY AUTO_INCREMENT,
    status_name VARCHAR(50) NOT NULL
);

CREATE TABLE sales (
    id INT PRIMARY KEY AUTO_INCREMENT,
    sale_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    client_id VARCHAR(50),
    total DECIMAL(10,2) NOT NULL,
    payment_method INT NOT NULL,
    discount DECIMAL(5,2) NULL,
    status_id INT NOT NULL,
    FOREIGN KEY (client_id) REFERENCES clients(id),
    FOREIGN KEY (payment_method) REFERENCES payment_methods(id),
    FOREIGN KEY (status_id) REFERENCES sale_status(id)

);

CREATE TABLE sales_details (
    id INT PRIMARY KEY AUTO_INCREMENT,
    sale_id INT,
    product_id INT,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (sale_id) REFERENCES sales(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE order_status(
    id INT PRIMARY KEY AUTO_INCREMENT,
    status_name VARCHAR(50) NOT NULL
);

CREATE TABLE orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    supplier_id VARCHAR(50),
    status_id INT NOT NULL,
    payment_method INT NOT NULL,
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id),
    FOREIGN KEY (status_id) REFERENCES order_status(id),
    FOREIGN KEY (payment_method) REFERENCES payment_methods(id)

);

CREATE TABLE order_details (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    product_id INT,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE supplier_phones(
    phone VARCHAR(20) PRIMARY KEY NOT NULL,
    supplier_id VARCHAR(50),
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id)
);

CREATE TABLE client_phones(
    phone VARCHAR(20) PRIMARY KEY NOT NULL,
    client_id VARCHAR(50) NOT NULL,
    FOREIGN KEY (client_id) REFERENCES clients(id)
);
