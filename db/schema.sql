CREATE TABLE Products (
id SERIAL PRIMARY KEY,
name VARCHAR(20),
price NUMERIC(10,2),
creationDate TIMESTAMP
);

CREATE TABLE Users (
id SERIAL PRIMARY KEY,
email VARCHAR(50),
password VARCHAR(50),
salt VARCHAR(50)
);