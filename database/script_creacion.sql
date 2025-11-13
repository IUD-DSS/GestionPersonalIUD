-- CREACIÓN DE BASE Y TABLAS (MariaDB)
CREATE DATABASE IF NOT EXISTS gestion_funcionarios
  CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE gestion_funcionarios;

DROP TABLE IF EXISTS formacion_academica;
DROP TABLE IF EXISTS grupo_familiar;
DROP TABLE IF EXISTS funcionarios;

CREATE TABLE funcionarios (
  id_funcionario INT AUTO_INCREMENT PRIMARY KEY,
  tipo_identificacion ENUM('CC','CE','PASAPORTE','NIT') NOT NULL,
  numero_identificacion INT NOT NULL UNIQUE,
  nombres VARCHAR(100) NOT NULL,
  apellidos VARCHAR(100) NOT NULL,
  estado_civil VARCHAR(20),
  sexo ENUM('M','F','O'),
  direccion VARCHAR(255),
  telefono VARCHAR(20),
  fecha_nacimiento DATE
) ENGINE=InnoDB;

CREATE TABLE grupo_familiar (
  id_familiar INT AUTO_INCREMENT PRIMARY KEY,
  id_funcionario INT NOT NULL,
  rol VARCHAR(50),
  nombres VARCHAR(100) NOT NULL,
  apellidos VARCHAR(100) NOT NULL,
  fecha_nacimiento DATE,
  FOREIGN KEY (id_funcionario) REFERENCES funcionarios(id_funcionario)
    ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE formacion_academica (
  id_formacion INT AUTO_INCREMENT PRIMARY KEY,
  id_funcionario INT NOT NULL,
  universidad VARCHAR(150),
  nivel_estudio VARCHAR(150),
  titulo_estudio VARCHAR(150),
  FOREIGN KEY (id_funcionario) REFERENCES funcionarios(id_funcionario)
    ON DELETE CASCADE
) ENGINE=InnoDB;
