DROP DATABASE IF EXISTS  electrostore;

CREATE DATABASE electrostore;
USE electrostore;


CREATE TABLE countries (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE regions (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name VARCHAR(100),
    country_id INT,
    FOREIGN KEY (country_id) REFERENCES countries(id)
);

CREATE TABLE cities (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name VARCHAR(100),
    region_id INT,
    FOREIGN KEY (region_id) REFERENCES regions(id)
);

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
    sale_price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    min_stock INT NOT NULL,
    category_id INT,
    brand_id INT,
    FOREIGN KEY (category_id) REFERENCES categories(id),
    FOREIGN KEY (brand_id) REFERENCES brands(id),
    CHECK (stock >= 0),
    CHECK (min_stock >= 0),
    CHECK (sale_price >= 0)
);

CREATE TABLE id_types(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR (20) NOT NULL
);

CREATE TABLE client_types(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR (20) NOT NULL
);

CREATE TABLE clients (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type_id INT,
    client_type INT,
    email VARCHAR(255) NULL,
    city_id INT,
    address_details VARCHAR(150), 
    FOREIGN KEY (type_id) REFERENCES id_types(id),
    FOREIGN KEY (client_type) REFERENCES client_types(id),
    FOREIGN KEY (city_id) REFERENCES cities(id)
);



CREATE TABLE suppliers(
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NULL,
    city_id INT,
    address_details VARCHAR(150),
    FOREIGN KEY (city_id) REFERENCES cities(id)

);

CREATE TABLE payment_methods(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL
);

CREATE TABLE sale_status(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE sales (
    id INT PRIMARY KEY AUTO_INCREMENT,
    sale_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    client_id VARCHAR(50),
    total DECIMAL(10,2),
    payment_method INT NOT NULL,
    discount_amount DECIMAL(10,2) NULL,
    discount_percent DECIMAL(5,2) NULL,
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
    subtotal DECIMAL(10,2),
    FOREIGN KEY (sale_id) REFERENCES sales(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE order_status(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    supplier_id VARCHAR(50),
    status_id INT NOT NULL,
    payment_method INT NOT NULL,
    total DECIMAL(10,2),
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
    subtotal DECIMAL(10,2),
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


DELIMITER //

CREATE TRIGGER calculateSubtotalTotalAndDiscount
AFTER INSERT ON sales_details
FOR EACH ROW
BEGIN
    DECLARE v_total DECIMAL(10,2);
    DECLARE v_discount_amount DECIMAL(10,2);
    DECLARE v_discount_percent DECIMAL(5,2);

    SELECT SUM(subtotal) INTO v_total
    FROM sales_details
    WHERE sale_id = NEW.sale_id;

    UPDATE sales
    SET total = v_total
    WHERE id = NEW.sale_id;

    SELECT discount_amount, discount_percent
    INTO v_discount_amount, v_discount_percent
    FROM sales
    WHERE id = NEW.sale_id;

    UPDATE products
    SET stock = stock - NEW.quantity
    WHERE id = NEW.product_id;

    IF v_discount_percent IS NOT NULL THEN
        SET v_discount_amount = (v_total * v_discount_percent / 100);
        SET v_total = v_total - v_discount_amount;

        UPDATE sales
        SET discount_amount = v_discount_amount,
            total = v_total
        WHERE id = NEW.sale_id;
    END IF;
END//

DELIMITER ;

DELIMITER //
CREATE TRIGGER setUnitPriceFromProducts
BEFORE INSERT ON sales_details
FOR EACH ROW
BEGIN
    SET NEW.unit_price = (SELECT sale_price FROM products WHERE id = NEW.product_id);
END//

DELIMITER ;

DELIMITER //

CREATE TRIGGER update_saledetail_subtotal
BEFORE UPDATE ON sales_details
FOR EACH ROW
BEGIN
    SET NEW.subtotal = NEW.unit_price * NEW.quantity;
END//

DELIMITER ;





DELIMITER //

CREATE TRIGGER calculateSalesDetailSubtotal
BEFORE INSERT ON sales_details
FOR EACH ROW
BEGIN
    SET NEW.subtotal = NEW.unit_price * NEW.quantity;
END//

DELIMITER ;

DELIMITER //

CREATE TRIGGER after_delete_sales_detail
AFTER DELETE ON sales_details
FOR EACH ROW
BEGIN
    DECLARE v_total DECIMAL(10,2);
    
    -- Restar el subtotal del detalle de venta eliminado al total de la venta relacionada
    SELECT total INTO v_total
    FROM sales
    WHERE id = OLD.sale_id;

    UPDATE sales
    SET total = v_total - OLD.subtotal
    WHERE id = OLD.sale_id;

    -- Sumar la cantidad eliminada al stock del producto relacionado
    UPDATE products
    SET stock = stock + OLD.quantity
    WHERE id = OLD.product_id;
END//

DELIMITER ;


DELIMITER //


CREATE TRIGGER updateOrderTotal
AFTER INSERT ON order_details
FOR EACH ROW
BEGIN
    DECLARE v_total DECIMAL(10,2);
    DECLARE v_current_total DECIMAL(10,2);
    DECLARE os_status_name VARCHAR(50);


    SELECT SUM(subtotal) INTO v_total
    FROM order_details
    WHERE order_id = NEW.order_id;
    
    UPDATE orders
    SET total = v_total
    WHERE id = NEW.order_id;
  
    SELECT name INTO os_status_name
    FROM order_status
    WHERE id = (SELECT status_id FROM orders WHERE id = NEW.order_id);

    IF os_status_name = 'Delivered' THEN
        UPDATE products
        SET stock = stock + NEW.quantity
        WHERE id = NEW.product_id;
    END IF;
END//

DELIMITER ;

DELIMITER //

CREATE TRIGGER calculateOrderDetailSubtotal
BEFORE INSERT ON order_details
FOR EACH ROW
BEGIN
    SET NEW.subtotal = NEW.unit_price * NEW.quantity;
END//

DELIMITER ;

DELIMITER //

CREATE TRIGGER updateStockDelivered
AFTER UPDATE ON orders
FOR EACH ROW
BEGIN
    DECLARE delivered_status_id INT;

    SELECT id INTO delivered_status_id
    FROM order_status
    WHERE name = 'Delivered';

    IF NEW.status_id != OLD.status_id AND NEW.status_id = delivered_status_id THEN
        UPDATE products p
        INNER JOIN order_details od ON p.id = od.product_id
        SET p.stock = p.stock + od.quantity
        WHERE od.order_id = NEW.id;
    END IF;
END//

DELIMITER ;

DELIMITER //

CREATE TRIGGER updateOrderSubtotal
BEFORE UPDATE ON order_details
FOR EACH ROW
BEGIN
    DECLARE v_total DECIMAL(10,2);

    SET NEW.subtotal = NEW.unit_price * NEW.quantity;

END//

DELIMITER ;

DELIMITER //

CREATE TRIGGER after_delete_order_detail
AFTER DELETE ON order_details
FOR EACH ROW
BEGIN
    DECLARE v_total DECIMAL(10,2);
    
    -- Restar el subtotal del detalle de orden eliminado al total de la orden relacionada
    SELECT total INTO v_total
    FROM orders
    WHERE id = OLD.order_id;

    UPDATE orders
    SET total = v_total - OLD.subtotal
    WHERE id = OLD.order_id;

    -- Restar la cantidad eliminada del stock del producto relacionado
    UPDATE products
    SET stock = stock - OLD.quantity
    WHERE id = OLD.product_id;
END//

DELIMITER ;
