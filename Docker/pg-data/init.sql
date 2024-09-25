-- DDL

CREATE TYPE category_enum AS ENUM('DRINK','SNACK','DESSERT','SIDE_DISH');

CREATE TYPE status_enum AS ENUM('WAITINGPAYMENT','APPROVEDPAYMENT','REJECTEDPAYMENT','RECEIVED','PREPARATION','READY','FINISHED','CANCELED');

CREATE TYPE payment_status_enum AS ENUM('WAITINGPAYMENT','APPROVEDPAYMENT','REJECTEDPAYMENT','RECEIVED','PREPARATION','READY','FINISHED','CANCELED');

CREATE TABLE client (id integer primary key generated by default as identity, cpf varchar(255) NOT NULL UNIQUE, email varchar(255) NOT NULL, name varchar(255) NOT NULL);

CREATE TABLE product (id integer primary key generated by default as identity, category category_enum NOT NULL, description varchar(255) NOT NULL, image varchar(255) DEFAULT NULL, name varchar(255) NOT NULL, preparation_time int NOT NULL, price DOUBLE PRECISION NOT NULL);

CREATE TABLE "order" (id integer primary key generated by default as identity, date_created timestamp(6) DEFAULT NULL, status status_enum NOT NULL, time_waiting_order int NOT NULL, total_price DOUBLE PRECISION NOT NULL, client_id int DEFAULT NULL, FOREIGN KEY (client_id) REFERENCES client (id));

CREATE TABLE check_out (id integer primary key generated by default as identity, date_created timestamp(6) DEFAULT NULL, payment_status payment_status_enum DEFAULT NULL, total_price DOUBLE PRECISION DEFAULT NULL, transact_id varchar(255) DEFAULT NULL, order_id int DEFAULT NULL);

CREATE TABLE order_items (id bigint primary key generated by default as identity, amount int NOT NULL, description varchar(255) NOT NULL, preparation_time varchar(255) NOT NULL, product_price DOUBLE PRECISION NOT NULL, order_id int NOT NULL, product_id int NOT NULL);

CREATE INDEX idx_cpf ON client (cpf);

CREATE INDEX idx_category ON product (category);

CREATE INDEX idx_status ON "order" (status);

-- Bebidas
INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Water', 'DRINK', 2.5, 'Bottled water', 2, 'http://gangstaburguer.com/images/water.jpg');

INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Soda', 'DRINK', 3.0, 'Coca-Cola', 2, 'http://gangstaburguer.com/images/soda.jpg');

INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Juice', 'DRINK', 3.5, 'Orange juice', 2, 'http://gangstaburguer.com/images/juice.jpg');

INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Beer', 'DRINK', 4.0, 'Local beer', 4, 'http://gangstaburguer.com/images/beer.jpg');

INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Smoothie', 'DRINK', 4.5, 'Strawberry smoothie', 2, 'http://gangstaburguer.com/images/smoothie.jpg');

-- Lanches
INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Cheeseburger', 'SNACK', 8.0, 'Classic cheeseburger', 5, 'http://gangstaburguer.com/images/cheeseburger.jpg');

INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Chicken Sandwich', 'SNACK', 8.5, 'Grilled chicken sandwich', 5, 'http://gangstaburguer.com/images/chicken_sandwich.jpg');

INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Vegetarian Wrap', 'SNACK', 7.5, 'Vegetarian wrap', 4, 'http://gangstaburguer.com/images/vegetarian_wrap.jpg');

INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('French Fries', 'SNACK', 4.0, 'Classic french fries', 3, 'http://gangstaburguer.com/images/french_fries.jpg');

INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Onion Rings', 'SNACK', 4.5, 'Crispy onion rings', 3, 'http://gangstaburguer.com/images/onion_rings.jpg');

-- Acompanhamentos
INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Onion Rings', 'SIDE_DISH', 4.5, 'Crispy onion rings', 3, 'http://gangstaburguer.com/images/onion_rings.jpg');

INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Mozzarella Sticks', 'SIDE_DISH', 5.0, 'Fried mozzarella sticks', 4, 'http://gangstaburguer.com/images/mozzarella_sticks.jpg');

INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Garlic Bread', 'SIDE_DISH', 3.5, 'Toasted garlic bread', 3, 'http://gangstaburguer.com/images/garlic_bread.jpg');

INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Loaded Fries', 'SIDE_DISH', 6.0, 'Fries with cheese and bacon', 4, 'http://gangstaburguer.com/images/loaded_fries.jpg');

INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Cheddar Jalapeno Poppers', 'SIDE_DISH', 5.5, 'Spicy cheddar poppers', 4, 'http://gangstaburguer.com/images/cheddar_jalapeno_poppers.jpg');

-- Sobremesas
INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Ice Cream Sundae', 'DESSERT', 6.0, 'Classic ice cream sundae', 5, 'http://gangstaburguer.com/images/ice_cream_sundae.jpg');

INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Chocolate Cake', 'DESSERT', 5.5, 'Decadent chocolate cake', 4, 'http://gangstaburguer.com/images/chocolate_cake.jpg');

INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Apple Pie', 'DESSERT', 5.0, 'Homemade apple pie', 4, 'http://gangstaburguer.com/images/apple_pie.jpg');

INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Cheesecake', 'DESSERT', 6.0, 'Creamy cheesecake', 4, 'http://gangstaburguer.com/images/cheesecake.jpg');


-- Clientes
INSERT INTO client (name, email, cpf)
VALUES ('João Silva', 'joao@example.com', '52998224725'),
       ('Maria Santos', 'maria@example.com', '06325614706'),
       ('Pedro Oliveira', 'pedro@example.com', '27798646686');