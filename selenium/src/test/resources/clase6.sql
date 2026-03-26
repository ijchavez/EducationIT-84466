CREATE DATABASE IF NOT EXISTS mydb;
USE mydb;

CREATE TABLE IF NOT EXISTS Personas (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    edad INT,
    email VARCHAR(100),
    telefono VARCHAR(15),
    PRIMARY KEY (id)
);

INSERT INTO Personas (nombre, apellido, edad, email, telefono) VALUES
    ('Juan', 'Perez', 25, 'juan.perez@email.com', '123-456-7890'),
    ('Maria', 'Gomez', 30, 'maria.gomez@email.com', '987-654-3210'),
    ('Carlos', 'Rodriguez', 22, 'carlos.rodriguez@email.com', '456-789-0123'),
    ('Ana', 'Lopez', 28, 'ana.lopez@email.com', '111-222-3333'),
    ('Pedro', 'Martinez', 35, 'pedro.martinez@email.com', '444-555-6666'),
    ('Laura', 'Fernandez', 29, 'laura.fernandez@email.com', '777-888-9999'),
    ('Roberto', 'Hernandez', 32, 'roberto.hernandez@email.com', '101-202-3030'),
    ('Isabel', 'Sanchez', 27, 'isabel.sanchez@email.com', '404-505-6060'),
    ('Miguel', 'Gutierrez', 31, 'miguel.gutierrez@email.com', '707-808-9090'),
    ('Carmen', 'Diaz', 26, 'carmen.diaz@email.com', '111-222-3333'),
    ('Francisco', 'Torres', 33, 'francisco.torres@email.com', '444-555-6666'),
    ('Sofia', 'Ramirez', 30, 'sofia.ramirez@email.com', '777-888-9999'),
    ('Javier', 'Vargas', 29, 'javier.vargas@email.com', '101-202-3030'),
    ('Elena', 'Moreno', 34, 'elena.moreno@email.com', '404-505-6060'),
    ('Alejandro', 'Castro', 28, 'alejandro.castro@email.com', '707-808-9090'),
    ('Natalia', 'Ruiz', 25, 'natalia.ruiz@email.com', '111-222-3333'),
    ('Hugo', 'Ortega', 32, 'hugo.ortega@email.com', '444-555-6666'),
    ('Adriana', 'Luna', 27, 'adriana.luna@email.com', '777-888-9999'),
    ('Diego', 'Fuentes', 29, 'diego.fuentes@email.com', '101-202-3030'),
    ('Valentina', 'Soto', 31, 'valentina.soto@email.com', '404-505-6060'),
    ('Fernando', 'Jimenez', 26, 'fernando.jimenez@email.com', '707-808-9090'),
    ('Carolina', 'Garcia', 33, 'carolina.garcia@email.com', '111-222-3333'),
    ('Raul', 'Mendoza', 30, 'raul.mendoza@email.com', '444-555-6666');
