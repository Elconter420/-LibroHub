CREATE DATABASE IF NOT EXISTS users_db;
CREATE DATABASE IF NOT EXISTS catalog_db;
CREATE DATABASE IF NOT EXISTS loans_db;
CREATE DATABASE IF NOT EXISTS reserv_db;

USE users_db;
CREATE TABLE IF NOT EXISTS users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(50) NOT NULL
);

INSERT IGNORE INTO users (email, password, role) VALUES
('estudiante@universidad.edu','$2a$10$7s9uE1Gqv1aDq1vOaFkV/OJq1u6kz3cKQXQ2Xb6sQY0QkZf1cPq2','ESTUDIANTE'),
('bibliotecario@universidad.edu','$2a$10$7s9uE1Gqv1aDq1vOaFkV/OJq1u6kz3cKQXQ2Xb6sQY0QkZf1cPq2','BIBLIOTECARIO'),
('admin@universidad.edu','$2a$10$7s9uE1Gqv1aDq1vOaFkV/OJq1u6kz3cKQXQ2Xb6sQY0QkZf1cPq2','ADMIN');

USE catalog_db;
CREATE TABLE IF NOT EXISTS books (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  author VARCHAR(255),
  isbn VARCHAR(50),
  available BOOLEAN DEFAULT TRUE
);

INSERT IGNORE INTO books (title, author, isbn, available) VALUES
('Fundamentos de Programación','Autor Ejemplo','978-1-23456-789-7', TRUE),
('Estructuras de Datos','Autor Ejemplo 2','978-1-23456-000-1', TRUE);
