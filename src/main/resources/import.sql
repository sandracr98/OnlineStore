USE osis_linker;

-- Insertando roles
INSERT INTO roles (name) VALUES
('ROLE_ADMIN'),
('ROLE_USER');

-- Insertando usuarios
INSERT INTO users (name, surname, birthdate, email, pass, enabled, total_spent) VALUES
('User', 'user', '1998-03-20', 'user', '$2a$10$/I57cTVQxky9hCrESg..Uu.ozMVWLzmWK8cfzH1bwmWyvjenRmohG', 1, 245),
('Admin', 'admin', '1998-03-20', 'admin', '$2a$10$2x/R0J3I8YebbF8vfkIYF.fOWIlqkc1dqPLC0IytLBGH.POjJSlXy', 1, 503),
('Sandra', 'Checa', '1998-03-20', 'sandrachr@example.com', 'password123', 1, 29),
('Fernando', 'Alonso', '1981-07-29', 'fernandoalonso@example.com', 'password456', 1, 0),
('Carlos', 'Sainz', '1994-09-01', 'carlossainz94@example.com', 'letmein', 1, 0),
('John', 'Doe', '1990-01-01', 'john.doe@example.com', 'password123', 1, 0),
('Jane', 'Doe', '1988-05-15', 'jane.doe@example.com', 'password456', 1, 0),
('Bob', 'Smith', '1995-03-20', 'bob.smith@example.com', 'letmein', 1, 0),
('Alice', 'Johnson', '1989-12-10', 'alice.johnson@example.com', 'password789', 1, 0),
('Michael', 'Brown', '1987-07-05', 'michael.brown@example.com', '123456', 1, 0),
('Emily', 'Davis', '1992-09-25', 'emily.davis@example.com', 'qwerty', 1, 0),
('David', 'Wilson', '1998-11-15', 'david.wilson@example.com', 'pass1234', 1, 0),
('Sarah', 'Taylor', '1985-06-30', 'sarah.taylor@example.com', 'abcd1234', 1, 0),
('Ryan', 'Anderson', '1993-04-18', 'ryan.anderson@example.com', 'password12', 1, 0),
('Jennifer', 'Harris', '1991-08-22', 'jennifer.harris@example.com', 'test123', 1, 0),
('Matthew', 'Clark', '1986-10-12', 'matthew.clark@example.com', 'letmein567', 1, 0),
('Laura', 'Parker', '1997-02-28', 'laura.parker@example.com', 'password890', 1, 0),
('James', 'Jones', '1984-03-08', 'james.jones@example.com', 'hello123', 1, 0),
('Jessica', 'Evans', '1996-07-19', 'jessica.evans@example.com', '1234abcd', 1, 0),
('Daniel', 'Morris', '1983-09-02', 'daniel.morris@example.com', 'qwerty123', 1, 0),
('Natalie', 'Baker', '1994-01-05', 'natalie.baker@example.com', 'welcome123', 1, 0),
('Andrew', 'Cooper', '1990-05-29', 'andrew.cooper@example.com', 'letmeinnow', 1, 0),
('Olivia', 'Hill', '1988-08-14', 'olivia.hill@example.com', 'password7890', 1, 0),
('William', 'Wright', '1987-12-03', 'william.wright@example.com', 'testpassword', 1, 0),
('Ava', 'Lopez', '1995-06-17', 'ava.lopez@example.com', 'mypassword', 1, 0);



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
(1, 2), (2, 1), (3, 1), (4, 2), (5, 1), (6, 2), (7, 1), (8, 2), (9, 1),
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
-- Product 1
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales)
VALUES ('Product 1', 25.50, 1, '1L', 10, 'Brand A', 'Black', '2kg', '2023-10-05', true, 5);

-- Product 2
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales)
VALUES ('Product 2', 35.00, 3, 'M', 20, 'Brand B', 'Blue', '500g', '2023-10-06', true, 2);

-- Product 3
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales)
VALUES ('Product 3', 10.99, 2, '500ml', 15, 'Brand C', 'White', '1kg', '2023-10-07', true, 3);

-- Product 4
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales)
VALUES ('Product 4', 45.75, 2, '2L', 12, 'Brand D', 'Silver', '3kg', '2023-10-08', true, 9);

-- Product 5
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales)
VALUES ('Product 5', 15.99, 3, 'L', 18, 'Brand E', 'Red', '700g', '2023-10-09', true, 0);

-- Product 6
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales)
VALUES ('Product 6', 8.50, 1, '1L', 25, 'Brand F', 'Green', '800g', '2023-10-10', true, 0);

-- Product 7
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales)
VALUES ('Product 7', 19.95, 2, '500ml', 30, 'Brand G', 'Gold', '600g', '2023-10-11', true, 0);

-- Product 8
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales)
VALUES ('Product 8', 29.75, 1, 'XL', 22, 'Brand H', 'Black', '900g', '2023-10-12', true, 0);

-- Product 9
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales)
VALUES ('Product 9', 12.25, 1, '750ml', 20, 'Brand I', 'Silver', '1.2kg', '2023-10-13', true, 0);

-- Product 10
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales)
VALUES ('Product 10', 32.99, 2, '1L', 14, 'Brand J', 'White', '1.1kg', '2023-10-14', true, 0);

-- Product 11
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales)
VALUES ('Product 11', 14.50, 3, 'M', 18, 'Brand K', 'Gray', '700g', '2023-10-15', true, 0);

-- Product 12
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales)
VALUES ('Product 12', 18.75, 1, '500ml', 15, 'Brand L', 'Blue', '900g', '2023-10-16', true, 0);

-- Product 13
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales)
VALUES ('Product 13', 22.99, 2, '2L', 10, 'Brand M', 'Green', '1.3kg', '2023-10-17', true, 0);

-- Product 14
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales)
VALUES ('Product 14', 9.95, 3, 'L', 20, 'Brand N', 'Yellow', '800g', '2023-10-18', true, 0);

-- Product 15
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales)
VALUES ('Product 15', 27.25, 1, '750ml', 18, 'Brand O', 'White', '1kg', '2023-10-19', true, 0);

-- Product 16
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales)
VALUES ('Product 16', 11.50, 2, '1L', 22, 'Brand P', 'Red', '1.2kg', '2023-10-20', true, 0);

-- Product 17
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales)
VALUES ('Product 17', 16.99, 3, 'M', 25, 'Brand Q', 'Black', '900g', '2023-10-21', true, 0);

-- Product 18
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales)
VALUES ('Product 18', 24.75, 1, '500ml', 28, 'Brand R', 'Silver', '1kg', '2023-10-22', true, 0);




