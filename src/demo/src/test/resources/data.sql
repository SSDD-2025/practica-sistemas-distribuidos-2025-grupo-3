-- Crear tabla Communities
CREATE TABLE IF NOT EXISTS Community (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

-- Insertar datos en Communities
INSERT INTO Community (name) VALUES
('Java'),
('Futbol'),
('Tecnología'),
('Viajes'),
('Cocina'),
('Libros'),
('Fotografía');

-- Crear tabla Users
CREATE TABLE IF NOT EXISTS User (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insertar datos en Users
INSERT INTO User (username, password, email) VALUES
('Invitado', '-', '-'),
('Juan Pérez', 'password1', 'juan.perez@example.com'),
('María García', 'password2', 'maria.garcia@example.com'),
('Carlos Rodríguez', 'password3', 'carlos.rodriguez@example.com'),
('Ana Martínez', 'password4', 'ana.martinez@example.com'),
('Luis Hernández', 'password5', 'luis.hernandez@example.com'),
('Laura López', 'password6', 'laura.lopez@example.com'),
('José González', 'password7', 'jose.gonzalez@example.com'),
('Marta Sánchez', 'password8', 'marta.sanchez@example.com'),
('David Fernández', 'password9', 'david.fernandez@example.com'),
('Elena Ruiz', 'password10', 'elena.ruiz@example.com'),
('Sergio', '454548', 's.espinosa.2020@alumnos.urjc.es');

-- Crear tabla Posts
CREATE TABLE IF NOT EXISTS Post (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    user_id BIGINT NOT NULL,
    community_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES User(id),
    FOREIGN KEY (community_id) REFERENCES Community(id)
);

-- Insertar datos en Posts (solo algunos ejemplos)
INSERT INTO Post (title, content, user_id, community_id) VALUES
('Introducción a Java', 'Java es un lenguaje de programación...', 2, 1),
('Java y la Programación Orientada a Objetos', 'La programación orientada a objetos...', 3, 1),
('Historia del Fútbol', 'El fútbol es un deporte de equipo...', 2, 2),
('El Futuro de la Inteligencia Artificial', 'La IA está transformando el mundo...', 2, 3),
('Recetas Fáciles para Principiantes', 'Si estás empezando en la cocina...', 4, 5);

-- Crear tabla Comments
CREATE TABLE IF NOT EXISTS Comment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    user_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES User(id),
    FOREIGN KEY (post_id) REFERENCES Post(id)
);

-- Insertar datos en Comments
INSERT INTO Comment (content, user_id, post_id) VALUES
('Muy interesante tu post', 6, 1),
('Gracias por compartir', 2, 1);
