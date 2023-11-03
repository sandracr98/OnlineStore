USE osis_linker;

-- Insertando roles
INSERT INTO roles (name) VALUES
('Admin'),
('Client');


-- Insertando usuarios
INSERT INTO users (name, surname, birthdate, email, pass) VALUES
('Sandra', 'Checa', '1998-03-20', 'sandrachr@example.com', 'password123'),
('Fernando', 'Alonso', '1981-07-29', 'fernandoalonso@example.com', 'password456'),
('Carlos', 'Sainz', '1994-09-01', 'carlossainz94@example.com', 'letmein'),
('John', 'Doe', '1990-01-01', 'john.doe@example.com', 'password123'),
('Jane', 'Doe', '1988-05-15', 'jane.doe@example.com', 'password456'),
('Bob', 'Smith', '1995-03-20', 'bob.smith@example.com', 'letmein'),
('Alice', 'Johnson', '1989-12-10', 'alice.johnson@example.com', 'password789'),
('Michael', 'Brown', '1987-07-05', 'michael.brown@example.com', '123456'),
('Emily', 'Davis', '1992-09-25', 'emily.davis@example.com', 'qwerty'),
('David', 'Wilson', '1998-11-15', 'david.wilson@example.com', 'pass1234'),
('Sarah', 'Taylor', '1985-06-30', 'sarah.taylor@example.com', 'abcd1234'),
('Ryan', 'Anderson', '1993-04-18', 'ryan.anderson@example.com', 'password12'),
('Jennifer', 'Harris', '1991-08-22', 'jennifer.harris@example.com', 'test123'),
('Matthew', 'Clark', '1986-10-12', 'matthew.clark@example.com', 'letmein567'),
('Laura', 'Parker', '1997-02-28', 'laura.parker@example.com', 'password890'),
('James', 'Jones', '1984-03-08', 'james.jones@example.com', 'hello123'),
('Jessica', 'Evans', '1996-07-19', 'jessica.evans@example.com', '1234abcd'),
('Daniel', 'Morris', '1983-09-02', 'daniel.morris@example.com', 'qwerty123'),
('Natalie', 'Baker', '1994-01-05', 'natalie.baker@example.com', 'welcome123'),
('Andrew', 'Cooper', '1990-05-29', 'andrew.cooper@example.com', 'letmeinnow'),
('Olivia', 'Hill', '1988-08-14', 'olivia.hill@example.com', 'password7890'),
('William', 'Wright', '1987-12-03', 'william.wright@example.com', 'testpassword'),
('Ava', 'Lopez', '1995-06-17', 'ava.lopez@example.com', 'mypassword');


-- Insertando direcciones de clientes
INSERT INTO clients_Addresses (country, city, postal_Code, street, home, apartment, user_id)
VALUES ('España', 'Barcelona', '08001', 'Calle de la Princesa', '123', '1A', 1);

INSERT INTO clients_Addresses (country, city, postal_Code, street, home, apartment, user_id)
VALUES ('Estados Unidos', 'Nueva York', '10001', 'Broadway', '456', '2B', 2);

INSERT INTO clients_Addresses (country, city, postal_Code, street, home, apartment, user_id)
VALUES ('Francia', 'París', '75001', 'Avenida de los Campos Elíseos', '789', '3C', 3);

INSERT INTO clients_Addresses (country, city, postal_Code, street, home, apartment, user_id)
VALUES ('Italia', 'Roma', '00118', 'Via del Corso', '1011', '4D', 4);

INSERT INTO clients_Addresses (country, city, postal_Code, street, home, apartment, user_id)
VALUES ('Alemania', 'Berlín', '10115', 'Unter den Linden', '1213', '5E', 5);

INSERT INTO clients_Addresses (country, city, postal_Code, street, home, apartment, user_id)
VALUES ('Reino Unido', 'Londres', 'SW1A 1AA', 'Downing Street', '10', '1', 6);

INSERT INTO clients_Addresses (country, city, postal_Code, street, home, apartment, user_id)
VALUES ('Japón', 'Tokio', '100-8111', 'Chiyoda', '1-1-1', '101', 7);

INSERT INTO users_has_roles (user_id, role_id) VALUES
(1, 1), (2, 2), (3, 1), (4, 2), (5, 1), (6, 2), (7, 1), (8, 2), (9, 1),
(10, 2), (11, 1), (12, 2), (13, 1), (14, 2), (15, 1), (16, 2), (17, 1),
(18, 2), (19, 1), (20, 2), (21, 1), (22, 2);


-- Insertando métodos de pago
INSERT INTO payment_Methods (type) VALUES ('Cash');
INSERT INTO payment_Methods (type) VALUES ('By Card');

-- Insertanfo categorias

INSERT INTO categories (name, status)
VALUES ('Home', true);

INSERT INTO categories (name, status)
VALUES ('Electronics', true);

INSERT INTO categories (name, status)
VALUES ('Clothes', true);

-- Insertando productos
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status)
VALUES ('Producto 1', 25.50, 1 , '1L', 10, 'Marca A', 'Negro', '2kg', '2023-10-05', true);

INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status)
VALUES ('Producto 2', 35.00, 3, 'M', 20, 'Marca B', 'Azul', '500g', '2023-10-06', true);

INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status)
VALUES ('Producto 3', 10.99, 2, '500ml', 15, 'Marca C', 'Blanco', '1kg', '2023-10-07', true);

INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status)
VALUES ('Producto 4', 45.75, 2, '2L', 12, 'Marca D', 'Plateado', '3kg', '2023-10-08', true);

INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status)
VALUES ('Producto 5', 15.99, 3, 'L', 18, 'Marca E', 'Rojo', '700g', '2023-10-09', true);

INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status)
VALUES ('Producto 6', 8.50, 1, '1L', 25, 'Marca F', 'Verde', '800g', '2023-10-10', true);

INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status)
VALUES ('Producto 7', 19.95, 2, '500ml', 30, 'Marca G', 'Dorado', '600g', '2023-10-11', true);

INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status)
VALUES ('Producto 8', 29.75, 1, 'XL', 22, 'Marca H', 'Negro', '900g', '2023-10-12', true);

INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status)
VALUES ('Producto 9', 12.25, 1, '750ml', 20, 'Marca I', 'Plateado', '1.2kg', '2023-10-13', true);

INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status)
VALUES ('Producto 10', 32.99, 2, '1L', 14, 'Marca J', 'Blanco', '1.1kg', '2023-10-14', true);

INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status)
VALUES ('Producto 11', 14.50, 3, 'M', 18, 'Marca K', 'Gris', '700g', '2023-10-15', true);

INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status)
VALUES ('Producto 12', 18.75, 1, '500ml', 15, 'Marca L', 'Azul', '900g', '2023-10-16', true);

INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status)
VALUES ('Producto 13', 22.99, 2, '2L', 10, 'Marca M', 'Verde', '1.3kg', '2023-10-17', true);

INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status)
VALUES ('Producto 14', 9.95, 3, 'L', 20, 'Marca N', 'Amarillo', '800g', '2023-10-18', true);

INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status)
VALUES ('Producto 15', 27.25, 1, '750ml', 18, 'Marca O', 'Blanco', '1kg', '2023-10-19', true);

INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status)
VALUES ('Producto 16', 11.50, 2, '1L', 22, 'Marca P', 'Rojo', '1.2kg', '2023-10-20', true);

INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status)
VALUES ('Producto 17', 16.99, 3, 'M', 25, 'Marca Q', 'Negro', '900g', '2023-10-21', true);

INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status)
VALUES ('Producto 18', 24.75, 1, '500ml', 28, 'Marca R', 'Plateado', '1kg', '2023-10-22', true);


-- Insertando órdenes
INSERT INTO orders (user_id_user, payment_Method_id, delivery_Method, goods, payment_Status, order_Status, sum, description, date)
VALUES (1, 1, 'Envío a domicilio', 'Productos varios', 0, 'En proceso', 150.00, 'Descripción de la orden 1', '2023-10-03');

INSERT INTO orders (user_id_user, payment_Method_id, delivery_Method, goods, payment_Status, order_Status, sum, description, date)
VALUES (1, 2, 'Recogida en tienda', 'Productos de electrónica', 1, 'Pendiente', 45, 'Descripción de la orden 2', '2023-10-04');


-- Insertando líneas de recibo
INSERT INTO receipt_Lines (amount, product_id, order_id) VALUES (5, 1, 1);
INSERT INTO receipt_Lines (amount, product_id, order_id) VALUES (3, 2, 2);
INSERT INTO receipt_Lines (amount, product_id, order_id) VALUES (2, 3, 1);


