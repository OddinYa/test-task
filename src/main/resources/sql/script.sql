
DROP DATABASE IF EXISTS sk_example_db;

DROP USER IF EXISTS sk_example_user;

CREATE USER sk_example_user PASSWORD '123';

CREATE DATABASE sk_example_db OWNER sk_example_user;







