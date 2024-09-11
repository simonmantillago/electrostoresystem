INSERT INTO countries (name) VALUES ('Colombia'),('Mexico'),('Chile');

INSERT INTO regions (name, country_id) VALUES ('Santander', 1),('Cundinamarca', 1),('Antioquia', 1);

INSERT INTO cities (name, region_id) VALUES ('Bucaramanga', 1),('Piedecuesta', 1),('Giron', 1);

INSERT INTO categories (name) VALUES ('Lighting'), ('Cables'), ('Switches');

INSERT INTO brands (name) VALUES ('ElectroMax'), ('BrightLight'), ('PowerLine');

INSERT INTO products (name, description, sale_price, stock, min_stock, category_id, brand_id)
VALUES ('LED Bulb', 'Energy-saving LED light bulb', 45.00, 100, 20, 1, 2),  -- Precio de venta 45.00
       ('Coaxial Cable', 'High-quality coaxial cable for data transmission', 28.00, 50, 10, 2, 1),  -- Precio de venta 28.00
       ('Wall Switch', 'Durable wall switch for home use', 55.00, 200, 30, 3, 3);  -- Precio de venta 55.00

INSERT INTO id_types (name) VALUES ('Passport'), ('Driver License'), ('National ID');

INSERT INTO client_types (name) VALUES ('Business'), ('Individual');

INSERT INTO clients (id, name, type_id, client_type, email, city_id, address_details) 
VALUES ('101', 'GreenTech Solutions', 1, 1, 'contact@greentech.co', 1, '123 Green St'),
       ('11223344', 'Juan Lopez', 2, 2, 'juan.lopez@gmail.com', 1, '456 Blue Ave'),
       ('103', 'Electrical Builders', 3, 2, 'vip@electrical.com', 1, '789 Elite Blvd');

INSERT INTO suppliers (id, name, email, city_id, address_details) 
VALUES ('0908', 'EnergySource Supplies', 'sales@energysource.com', 1, 'Industrial Park 123'),
       ('1232', 'CableWorld', 'support@cableworld.net', 1, 'Cable Avenue 456'),
       ('4565', 'LightPro Industries', 'info@lightpro.com', 1, 'Lighting Street 789');

INSERT INTO payment_methods (name) VALUES ('Credit Card'), ('Bank Transfer'), ('Cash');

INSERT INTO sale_status (name) VALUES ('Completed'),('Refund');

INSERT INTO sales (client_id, payment_method, discount_percent, status_id)
VALUES ('101', 1, 10.00, 2),
       ('11223344', 2, 5.00, 2),
       ('103', 3, NULL, 2);

INSERT INTO sales_details (sale_id, product_id, quantity)
VALUES (1, 1, 10),
       (2, 2, 5),
       (3, 3, 20);

INSERT INTO order_status (name) VALUES('Delivered'),('Ordered');

INSERT INTO orders (supplier_id, status_id, payment_method, total)
VALUES ('0908',2, 1, 0),
       ('1232',2, 2, 0),
       ('4565',2, 3, 0);

INSERT INTO order_details (order_id, product_id, quantity, unit_price)
VALUES (1, 1, 50, 40.00), 
       (2, 2, 100, 25.00), 
       (3, 3, 75, 50.00);  

INSERT INTO supplier_phones (supplier_id, phone) 
VALUES ('0908', '+1-555-1234'),
       ('1232', '+1-555-5678'),
       ('4565', '+1-555-9012');

INSERT INTO client_phones (client_id, phone) 
VALUES ('101', '+15553454'),
       ('11223344', '+15557890'),
       ('103', '+15551122');
