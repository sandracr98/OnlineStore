USE osis_linker;

-- Insertando roles
INSERT INTO roles (name) VALUES
('ROLE_ADMIN'),
('ROLE_USER');

-- Insertando usuarios
insert into users (name, surname, birthdate, email, pass, enabled, total_spent, photo) values
('User', 'user', '1998-03-20', 'user', '$2a$10$/I57cTVQxky9hCrESg..Uu.ozMVWLzmWK8cfzH1bwmWyvjenRmohG', 1, 0, ''),
('Admin', 'admin', '1998-03-20', 'admin', '$2a$10$2x/R0J3I8YebbF8vfkIYF.fOWIlqkc1dqPLC0IytLBGH.POjJSlXy', 1, 0, ''),
('Sandra', 'Checa', '1998-03-20', 'sandrachr@example.com', 'password123', 1, 0, ''),
('Fernando', 'Alonso', '1981-07-29', 'fernandoalonso@example.com', 'password456', 1, 0, ''),
('Carlos', 'Sainz', '1994-09-01', 'carlossainz94@example.com', 'letmein', 1, 0, ''),
('John', 'Doe', '1990-01-01', 'john.doe@example.com', 'password123', 1, 0, ''),
('Jane', 'Doe', '1988-05-15', 'jane.doe@example.com', 'password456', 1, 0, ''),
('Bob', 'Smith', '1995-03-20', 'bob.smith@example.com', 'letmein', 1, 0, ''),
('Alice', 'Johnson', '1989-12-10', 'alice.johnson@example.com', 'password789', 1, 0, ''),
('Michael', 'Brown', '1987-07-05', 'michael.brown@example.com', '123456', 1, 0, ''),
('Emily', 'Davis', '1992-09-25', 'emily.davis@example.com', 'qwerty', 1, 0, ''),
('David', 'Wilson', '1998-11-15', 'david.wilson@example.com', 'pass1234', 1, 0, ''),
('Sarah', 'Taylor', '1985-06-30', 'sarah.taylor@example.com', 'abcd1234', 1, 0, ''),
('Ryan', 'Anderson', '1993-04-18', 'ryan.anderson@example.com', 'password12', 1, 0, ''),
('Jennifer', 'Harris', '1991-08-22', 'jennifer.harris@example.com', 'test123', 1, 0, ''),
('Matthew', 'Clark', '1986-10-12', 'matthew.clark@example.com', 'letmein567', 1, 0, ''),
('Laura', 'Parker', '1997-02-28', 'laura.parker@example.com', 'password890', 1, 0, ''),
('James', 'Jones', '1984-03-08', 'james.jones@example.com', 'hello123', 1, 0, ''),
('Jessica', 'Evans', '1996-07-19', 'jessica.evans@example.com', '1234abcd', 1, 0, ''),
('Daniel', 'Morris', '1983-09-02', 'daniel.morris@example.com', 'qwerty123', 1, 0, ''),
('Natalie', 'Baker', '1994-01-05', 'natalie.baker@example.com', 'welcome123', 1, 0, ''),
('Andrew', 'Cooper', '1990-05-29', 'andrew.cooper@example.com', 'letmeinnow', 1, 0, ''),
('Olivia', 'Hill', '1988-08-14', 'olivia.hill@example.com', 'password7890', 1, 0, ''),
('William', 'Wright', '1987-12-03', 'william.wright@example.com', 'testpassword', 1, 0, ''),
('Ava', 'Lopez', '1995-06-17', 'ava.lopez@example.com', 'mypassword', 1, 0, '');


-- Inserting customer addresses
INSERT INTO clients_addresses (country, city, postal_code, street, home, apartment, user_id)
VALUES ('Spain', 'Barcelona', '08001', 'Calle de la Princesa', '123', '1A', 1);

INSERT INTO clients_addresses (country, city, postal_code, street, home, apartment, user_id)
VALUES ('United States', 'New York', '10001', 'Broadway', '456', '2B', 2);

INSERT INTO clients_addresses (country, city, postal_code, street, home, apartment, user_id)
VALUES ('France', 'Paris', '75001', 'Avenue des Champs-Élysées', '789', '3C', 3);

INSERT INTO clients_addresses (country, city, postal_code, street, home, apartment, user_id)
VALUES ('Italy', 'Rome', '00118', 'Via del Corso', '1011', '4D', 4);

INSERT INTO clients_addresses (country, city, postal_code, street, home, apartment, user_id)
VALUES ('Germany', 'Berlin', '10115', 'Unter den Linden', '1213', '5E', 5);

INSERT INTO clients_addresses (country, city, postal_code, street, home, apartment, user_id)
VALUES ('United Kingdom', 'London', 'SW1A 1AA', 'Downing Street', '10', '1', 6);

INSERT INTO clients_addresses (country, city, postal_code, street, home, apartment, user_id)
VALUES ('Japan', 'Tokyo', '100-8111', 'Chiyoda', '1-1-1', '101', 7);


insert into users_has_roles (user_id, role_id) values
(1, 2), (2, 1), (3, 1), (4, 2), (5, 1), (6, 2), (7, 1), (8, 2), (9, 1),
(10, 2), (11, 1), (12, 2), (13, 1), (14, 2), (15, 1), (16, 2), (17, 1),
(18, 2), (19, 1), (20, 2), (21, 1), (22, 2);


-- Insertando métodos de pago
insert into payment_Methods (type) values ('Cash');
insert into payment_Methods (type) values ('By Card');

-- Insert categories
-- Category: Art Supplies
INSERT INTO categories (name, status) VALUES ('Art Supplies', true);

-- Category: Painting Supplies
INSERT INTO categories (name, status) VALUES ('Painting Supplies', true);

-- Category: Drawing and Coloring Supplies
INSERT INTO categories (name, status) VALUES ('Drawing and Coloring Supplies', true);

-- Category: Studio Equipment
INSERT INTO categories (name, status) VALUES ('Studio Equipment', true);

-- Category: Sculpting Materials
INSERT INTO categories (name, status) VALUES ('Sculpting Materials', true);

-- Category: Fine Arts Materials
INSERT INTO categories (name, status) VALUES ('Fine Arts Materials', true);

-- Category: Drawing and Writing Supplies
INSERT INTO categories (name, status) VALUES ('Drawing and Writing Supplies', true);



-- Insertando productos
-- Product 1
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales, photo)
VALUES ('Wood Sculpture Set', 25.50, 1, NULL, 10, 'Winsor & Newton', 'Black', '2kg', '2023-10-05', true, 5, '');

-- Product 2
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales, photo)
VALUES ('Watercolor Box', 35.00, 2, 'M', 20, 'Rembrandt', 'Blue', '500g', '2023-10-06', true, 3, '');

-- Product 3
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales, photo)
VALUES ('Brush set', 10.99, 2, NULL, 15, 'Princeton', 'White', '1kg', '2023-10-07', true, 2, '');

-- Product 4
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales, photo)
VALUES ('Color Palettes', 45.75, 2, NULL, 12, 'Old Holland', 'Silver', '3kg', '2023-10-08', true, 0, '');

-- Product 5
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales, photo)
VALUES ('Marker Case', 15.99, 3, 'L', 18, 'Copic', 'Red', '700g', '2023-10-09', true, 0, '');

-- Product 6
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales, photo)
VALUES ('Easel', 8.50, 4, NULL, 25, 'Art Alternatives', 'Green', '800g', '2023-10-10', true, 0, '');

-- Product 7
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales, photo)
VALUES ('Oil Paint Set', 29.99, 2, NULL, 15, 'Gamblin', 'Brown', '1.5kg', '2023-10-11', true, 0, '');

-- Product 8
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales, photo)
VALUES ('Sketchbook', 12.50, 7, NULL, 30, 'Strathmore', 'White', '500g', '2023-10-12', true, 0, '');

-- Product 9
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales, photo)
VALUES ('Sculpture Clay', 19.95, 5, NULL, 8, 'Mungyo', 'Brown', '6kg', '2023-10-13', true, 0, '');

-- Product 10
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales, photo)
VALUES ('Watercolor Paper Pack', 8.99, 2, NULL, 25, 'Arches', 'White', '800g', '2023-10-14', true, 0, '');

-- Product 11
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales, photo)
VALUES ('Acrylic Brushes Set', 15.75, 2, NULL, 12, 'Daler-Rowney', 'Assorted', '700g', '2023-10-15', true, 0, '');

-- Product 12
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales, photo)
VALUES ('Canvas Roll', 24.50, 6, NULL, 18, 'Fredrix', 'White', '1.2kg', '2023-10-16', true, 0, '');

-- Product 13
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales, photo)
VALUES ('Calligraphy Pen Set', 18.25, 3, NULL, 22, 'Pentel', 'Black', '400g', '2023-10-17', true, 0, '');

-- Product 14
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales, photo)
VALUES ('Pastel Colors Set', 22.99, 3, NULL, 15, 'Mungyo', 'Assorted', '600g', '2023-10-18', true, 0, '');

-- Product 15
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales, photo)
VALUES ('Modeling Clay Kit', 14.50, 5, NULL, 20, 'Sculpey', 'Multicolor', '900g', '2023-10-19', true, 0, '');

-- Product 16
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales, photo)
VALUES ('Marker Sketchpad', 9.75, 3, NULL, 28, 'Canson', 'White', '350g', '2023-10-20', true, 0, '');

-- Product 17
INSERT INTO products (title, price, category_id, volume, stock, brand, color, weight, date, status, total_sales, photo)
VALUES ('Wooden Art Easel', 32.50, 4, NULL, 10, 'Art Advantage', 'Natural Wood', '2.8kg', '2023-10-21', true, 0, '');



-- Insertando órdenes
INSERT INTO orders (user_id_user, payment_Method_id, delivery_Method, goods, payment_Status, order_Status, sum, description, date)
VALUES (1, 1, 'Home Delivery', '', 'Pending', 'Pending payment', 150.00, 'Art set', '2023-10-03');

INSERT INTO orders (user_id_user, payment_Method_id, delivery_Method, goods, payment_Status, order_Status, sum, description, date)
VALUES (1, 2, 'In-Store Pickup', '', 'Paid', 'Delivered', 45, 'WaterColor Set', '2023-10-04');


-- Insertando líneas de recibo
INSERT INTO receipt_Lines (amount, product_id, order_id) VALUES (5, 1, 1);
INSERT INTO receipt_Lines (amount, product_id, order_id) VALUES (3, 2, 2);
INSERT INTO receipt_Lines (amount, product_id, order_id) VALUES (2, 3, 1);



