-- ==========================================
-- 1. USERS MICROSERVICE DATABASE
-- ==========================================
CREATE DATABASE IF NOT EXISTS users_db;
USE users_db;

CREATE TABLE IF NOT EXISTS users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  username VARCHAR(100) NOT NULL,
  role VARCHAR(50) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS user_profiles (
    user_id BIGINT PRIMARY KEY,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    phone VARCHAR(20),
    address VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

INSERT IGNORE INTO users (email, password, username, role) VALUES
('estudiante@universidad.edu','$2a$10$7s9uE1Gqv1aDq1vOaFkV/OJq1u6kz3cKQXQ2Xb6sQY0QkZf1cPq2', 'estudiante1', 'ESTUDIANTE'),
('bibliotecario@universidad.edu','$2a$10$7s9uE1Gqv1aDq1vOaFkV/OJq1u6kz3cKQXQ2Xb6sQY0QkZf1cPq2', 'biblio1', 'BIBLIOTECARIO'),
('admin@universidad.edu','$2a$10$7s9uE1Gqv1aDq1vOaFkV/OJq1u6kz3cKQXQ2Xb6sQY0QkZf1cPq2', 'admin1', 'ADMIN');

-- ==========================================
-- 2. CATALOG MICROSERVICE DATABASE
-- ==========================================
CREATE DATABASE IF NOT EXISTS catalog_db;
USE catalog_db;

CREATE TABLE IF NOT EXISTS authors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    biography TEXT,
    nationality VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE IF NOT EXISTS books (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  author_id BIGINT,
  isbn VARCHAR(50) UNIQUE,
  publication_year INT,
  stock INT DEFAULT 1,
  available_stock INT DEFAULT 1,
  cover_image_url VARCHAR(255),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (author_id) REFERENCES authors(id)
);

CREATE TABLE IF NOT EXISTS book_categories (
    book_id BIGINT,
    category_id BIGINT,
    PRIMARY KEY (book_id, category_id),
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    book_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL, -- Reference to logical user ID
    rating INT CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
);

-- Seed Data
INSERT IGNORE INTO authors (name, nationality) VALUES ('Robert C. Martin', 'USA'), ('Erich Gamma', 'Switzerland');
INSERT IGNORE INTO categories (name) VALUES ('Technology'), ('Programming'), ('Design Patterns');

INSERT IGNORE INTO books (title, author_id, isbn, stock, available_stock) VALUES
('Clean Code', 1, '978-0132350884', 5, 5),
('Design Patterns', 2, '978-0201633610', 3, 3);

INSERT IGNORE INTO book_categories (book_id, category_id) VALUES (1, 1), (1, 2), (2, 1), (2, 3);


-- ==========================================
-- 3. LOAN MICROSERVICE DATABASE
-- ==========================================
CREATE DATABASE IF NOT EXISTS loans_db;
USE loans_db;

CREATE TABLE IF NOT EXISTS loans (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    loan_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    due_date TIMESTAMP NOT NULL,
    return_date TIMESTAMP NULL,
    status VARCHAR(20) DEFAULT 'ACTIVE', -- ACTIVE, RETURNED, OVERDUE
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS fines (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    loan_id BIGINT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING', -- PENDING, PAID
    generated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (loan_id) REFERENCES loans(id)
);

-- ==========================================
-- 4. RESERVATION MICROSERVICE DATABASE
-- ==========================================
CREATE DATABASE IF NOT EXISTS reserv_db;
USE reserv_db;

CREATE TABLE IF NOT EXISTS rooms (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    capacity INT NOT NULL,
    has_projector BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS reservations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    room_id BIGINT NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    status VARCHAR(20) DEFAULT 'CONFIRMED', -- CONFIRMED, CANCELLED, COMPLETED
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (room_id) REFERENCES rooms(id)
);

INSERT IGNORE INTO rooms (name, capacity, has_projector) VALUES
('Sala A', 4, TRUE),
('Sala B', 6, FALSE),
('Sala Silencio', 2, FALSE);
