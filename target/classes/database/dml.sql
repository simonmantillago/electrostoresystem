
INSERT INTO categories (name) VALUES ('Lighting'), ('Cables'), ('Switches');

INSERT INTO brands (name) VALUES ('ElectroMax'), ('BrightLight'), ('PowerLine');

INSERT INTO products (name, description, price, stock, min_stock, category_id, brand_id)
VALUES ('LED Bulb', 'Energy-saving LED light bulb', 5.99, 100, 20, 1, 2),
       ('Coaxial Cable', 'High-quality coaxial cable for data transmission', 12.50, 50, 10, 2, 1),
       ('Wall Switch', 'Durable wall switch for home use', 7.30, 200, 30, 3, 3);


INSERT INTO id_types (id_name) VALUES ('Passport'), ('Driver License'), ('National ID');

INSERT INTO client_types (type_name) VALUES ('Business'), ('Individual');

INSERT INTO clients (id, name, type_id, client_type, email, address) 
VALUES ('101', 'GreenTech Solutions', 1, 1, 'contact@greentech.co', '123 Green St'),
       ('11223344', 'Juan Lopez', 2, 2, 'juan.lopez@gmail.com', '456 Blue Ave'),
       ('103', 'Electrical Builders', 3, 2, 'vip@electrical.com', '789 Elite Blvd');

INSERT INTO suppliers (id, name, email) 
VALUES ('0908', 'EnergySource Supplies', 'sales@energysource.com'),
       ('1232', 'CableWorld', 'support@cableworld.net'),
       ('4565', 'LightPro Industries', 'info@lightpro.com');

INSERT INTO payment_methods (name) VALUES ('Credit Card'), ('Bank Transfer'), ('Cash');

INSERT INTO sale_status (status_name) VALUES ('Refund'), ('Completed'), ('Cancelled');

INSERT INTO sales (client_id, total, payment_method, discount, status_id)
VALUES ('101', 500.75, 1, 10.00, 2),
       ('11223344', 150.00, 2, 5.00, 1),
       ('103', 1200.50, 3, NULL, 3);

INSERT INTO sales_details (sale_id, product_id, quantity, unit_price, subtotal)
VALUES (1, 1, 10, 50.00, 500.00),
       (2, 2, 5, 30.00, 150.00),
       (3, 3, 20, 60.00, 1200.00);

INSERT INTO order_status (status_name) VALUES ('Ordered'),('Delivered');

INSERT INTO orders (supplier_id, status_id, payment_method)
VALUES ('0908', 1, 1),
       ('1232', 2, 2),
       ('4565', 2, 3);

INSERT INTO order_details (order_id, product_id, quantity, unit_price, subtotal)
VALUES (1, 1, 50, 45.00, 2250.00),
       (2, 2, 100, 28.00, 2800.00),
       (3, 3, 75, 55.00, 4125.00);

INSERT INTO supplier_phones (supplier_id, phone) 
VALUES ('0908', '+1-555-1234'),
       ('1232', '+1-555-5678'),
       ('4565', '+1-555-9012');

INSERT INTO client_phones (client_id, phone) 
VALUES ('101', '+15553454'),
       ('11223344', '+15557890'),
       ('103', '+15551122');


