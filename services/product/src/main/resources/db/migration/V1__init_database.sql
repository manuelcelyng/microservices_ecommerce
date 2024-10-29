CREATE TABLE IF NOT EXISTS  category
(
    id INTEGER NOT NULL primary key,
    description VARCHAR(255),
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS product
(
    id INTEGER NOT NULL primary key,
    description VARCHAR(255),
    name VARCHAR(255),
    available_quantity DOUBLE PRECISION NOT NULL,
    price numeric(38, 2),
    category_id INTEGER
            CONSTRAINT fk1aslkdañsdm references category
);

CREATE SEQUENCE IF NOT EXISTS  category_seq INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS  product_seq INCREMENT BY 50;
