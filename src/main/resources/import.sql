
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
INSERT INTO clients_Addresses (country, city, postal_Code, street, home, apartment)
VALUES ('España', 'Barcelona', 08001, 'Calle de la Princesa', '123', '1A');

INSERT INTO clients_Addresses (country, city, postal_Code, street, home, apartment)
VALUES ('Estados Unidos', 'Nueva York', 10001, 'Broadway', '456', '2B');

INSERT INTO clients_Addresses (country, city, postal_Code, street, home, apartment)
VALUES ('Francia', 'París', 75001, 'Avenida de los Campos Elíseos', '789', '3C');

INSERT INTO clients_Addresses (country, city, postal_Code, street, home, apartment)
VALUES ('Italia', 'Roma', 00118, 'Via del Corso', '1011', '4D');

INSERT INTO clients_Addresses (country, city, postal_Code, street, home, apartment)
VALUES ('Alemania', 'Berlín', 10115, 'Unter den Linden', '1213', '5E');

INSERT INTO clients_Addresses (country, city, postal_Code, street, home, apartment)
VALUES ('Reino Unido', 'Londres', 'SW1A 1AA', 'Downing Street', '10', '1');

INSERT INTO clients_Addresses (country, city, postal_Code, street, home, apartment)
VALUES ('Japón', 'Tokio', 100-8111, 'Chiyoda', '1-1-1', '101');


-- Insertando métodos de pago
INSERT INTO payment_Methods (type) VALUES ('Cash');
INSERT INTO payment_Methods (type) VALUES ('By Card');


-- Insertando productos
INSERT INTO products (title, price, category, volume, stock, brand, color, weight, date)
VALUES ('Producto 1', 25.50, 'Electrónica', '1L', 10, 'Marca A', 'Negro', '2kg', '2023-10-05');

INSERT INTO products (title, price, category, volume, stock, brand, color, weight, date)
VALUES ('Producto 2', 35.00, 'Ropa', 'M', 20, 'Marca B', 'Azul', '500g', '2023-10-06');

INSERT INTO products (title, price, category, volume, stock, brand, color, weight, date)
VALUES ('Producto 3', 10.99, 'Hogar', '500ml', 15, 'Marca C', 'Blanco', '1kg', '2023-10-07');


-- Insertando líneas de recibo
INSERT INTO receipt_Lines (amount, product_id) VALUES (5, 1);
INSERT INTO receipt_Lines (amount, product_id) VALUES (3, 2);
INSERT INTO receipt_Lines (amount, product_id) VALUES (2, 3);


-- Insertando órdenes
INSERT INTO orders (user_id_user, clients_Address_id, payment_Method_id, delivery_Method, goods, payment_Status, order_Status, sum, description, date)
VALUES (1, 1, 1, 'Envío a domicilio', 'Productos varios', 0, 'En proceso', 150.0, 'Descripción de la orden 1', '2023-10-03');

INSERT INTO orders (user_id_user, clients_Address_id, payment_Method_id, delivery_Method, goods, payment_Status, order_Status, sum, description, date)
VALUES (2, 2, 2, 'Recogida en tienda', 'Productos de electrónica', 1, 'Pendiente', 200.0, 'Descripción de la orden 2', '2023-10-04');

