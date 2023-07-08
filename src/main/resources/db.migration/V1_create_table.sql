CREATE TABLE Products (
id VARCHAR(50),
name VARCHAR(20),
price NUMERIC(10,2),
creation_date TIMESTAMP
);

CREATE TABLE Users (
id SERIAL PRIMARY KEY,
role_name VARCHAR(10),
email VARCHAR(50),
password VARCHAR(50),
salt VARCHAR(50)
);