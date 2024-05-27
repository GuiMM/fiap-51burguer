-- Bebidas
INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Water', 'DRINK', 2.5, 'Bottled water', 5, NULL);

INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Soda', 'DRINK', 3.0, 'Coca-Cola', 5, NULL);

INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Juice', 'DRINK', 3.5, 'Orange juice', 5, NULL);

INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Beer', 'DRINK', 4.0, 'Local beer', 5, NULL);

INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Smoothie', 'DRINK', 4.5, 'Strawberry smoothie', 5, NULL);

-- Lanches
INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Cheeseburger', 'SNACK', 8.0, 'Classic cheeseburger', 10, NULL);

INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Chicken Sandwich', 'SNACK', 8.5, 'Grilled chicken sandwich', 10, NULL);

INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Vegetarian Wrap', 'SNACK', 7.5, 'Vegetarian wrap', 8, NULL);

INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('French Fries', 'SNACK', 4.0, 'Classic french fries', 5, NULL);

INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Onion Rings', 'SNACK', 4.5, 'Crispy onion rings', 5, NULL);

-- Acompanhamentos
INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Onion Rings', 'SIDE_DISH', 4.5, 'Crispy onion rings', 5, NULL);

INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Mozzarella Sticks', 'SIDE_DISH', 5.0, 'Fried mozzarella sticks', 6, NULL);

INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Garlic Bread', 'SIDE_DISH', 3.5, 'Toasted garlic bread', 4, NULL);

INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Loaded Fries', 'SIDE_DISH', 6.0, 'Fries with cheese and bacon', 7, NULL);

INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Cheddar Jalapeno Poppers', 'SIDE_DISH', 5.5, 'Spicy cheddar poppers', 6, NULL);

-- Sobremesas
INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Ice Cream Sundae', 'DESSERT', 6.0, 'Classic ice cream sundae', 7, NULL);

INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Chocolate Cake', 'DESSERT', 5.5, 'Decadent chocolate cake', 6, NULL);

INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Apple Pie', 'DESSERT', 5.0, 'Homemade apple pie', 6, NULL);

INSERT INTO product (name, category, price, description, preparation_time, image)
VALUES ('Cheesecake', 'DESSERT', 6.0, 'Creamy cheesecake', 6, NULL);

-- Clientes
INSERT INTO client (name, email, cpf)
VALUES ('João Silva', 'joao@example.com', '52998224725'),
       ('Maria Santos', 'maria@example.com', '06325614706'),
       ('Pedro Oliveira', 'pedro@example.com', '27798646686');
