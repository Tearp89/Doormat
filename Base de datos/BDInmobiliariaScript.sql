-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema Doormat
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Doormat
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Doormat` DEFAULT CHARACTER SET utf8 ;
USE `Doormat` ;

-- -----------------------------------------------------
-- Table `Doormat`.`Agente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Doormat`.`Agente` (
  `usuarioAgente` VARCHAR(30) NOT NULL,
  `contraseña` VARCHAR(256) NULL,
  `valoración` INT NULL,
  PRIMARY KEY (`usuarioAgente`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Doormat`.`Cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Doormat`.`Cliente` (
  `usuarioCliente` VARCHAR(25) NOT NULL,
  `correoElectrónico` VARCHAR(45) NOT NULL,
  `contraseña` VARCHAR(256) NOT NULL,
  `preferencias` TEXT NOT NULL,
  `nombre` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`usuarioCliente`),
  UNIQUE INDEX `correoElectrónico_UNIQUE` (`correoElectrónico` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Doormat`.`Propiedad`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Doormat`.`Propiedad` (
  `idPropiedad` INT NOT NULL AUTO_INCREMENT,
  `dirección` VARCHAR(45) NOT NULL,
  `descripción` TEXT NOT NULL,
  `estadoPropiedad` ENUM("Disponible", "Vendida", "Rentada") NOT NULL,
  `Agente_Usuario` VARCHAR(30) NOT NULL,
  `Cliente_usuarioCliente` VARCHAR(25) NULL,
  `Ciudad` VARCHAR(100) NULL,
  `Zona` VARCHAR(45) NULL,
  `PropiedadEn` ENUM("Renta", "Venta") NULL,
  `Precio` INT NULL,
  `NoHabitaciones` INT NULL,
  `NoEstancias` INT NULL,
  `NoBaños` INT NULL,
  `Cochera` INT NULL,
  `Tamaño` VARCHAR(45) NULL,
  `Resumen` VARCHAR(50) NULL,
  `Tipo` ENUM('Departamento', 'Casa', 'Cuarto') NULL,
  PRIMARY KEY (`idPropiedad`),
  INDEX `fk_Propiedad_Agente_idx` (`Agente_Usuario` ASC) VISIBLE,
  INDEX `fk_Propiedad_Cliente1_idx` (`Cliente_usuarioCliente` ASC) VISIBLE,
  CONSTRAINT `fk_Propiedad_Agente`
    FOREIGN KEY (`Agente_Usuario`)
    REFERENCES `Doormat`.`Agente` (`usuarioAgente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Propiedad_Cliente1`
    FOREIGN KEY (`Cliente_usuarioCliente`)
    REFERENCES `Doormat`.`Cliente` (`usuarioCliente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Doormat`.`Visita`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Doormat`.`Visita` (
  `idVisita` INT NOT NULL AUTO_INCREMENT,
  `fecha` DATE NOT NULL,
  `Agente_Usuario` VARCHAR(30) NOT NULL,
  `Cliente_usuarioCliente` VARCHAR(25) NOT NULL,
  `Propiedad_idPropiedad` INT NOT NULL,
  PRIMARY KEY (`idVisita`),
  INDEX `fk_Visita_Agente1_idx` (`Agente_Usuario` ASC) VISIBLE,
  INDEX `fk_Visita_Cliente1_idx` (`Cliente_usuarioCliente` ASC) VISIBLE,
  INDEX `fk_Visita_Propiedad1_idx` (`Propiedad_idPropiedad` ASC) VISIBLE,
  CONSTRAINT `fk_Visita_Agente1`
    FOREIGN KEY (`Agente_Usuario`)
    REFERENCES `Doormat`.`Agente` (`usuarioAgente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Visita_Cliente1`
    FOREIGN KEY (`Cliente_usuarioCliente`)
    REFERENCES `Doormat`.`Cliente` (`usuarioCliente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Visita_Propiedad1`
    FOREIGN KEY (`Propiedad_idPropiedad`)
    REFERENCES `Doormat`.`Propiedad` (`idPropiedad`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Doormat`.`CalificacionesPropiedad`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Doormat`.`CalificacionesPropiedad` (
  `calificaciónPropiedadId` INT NOT NULL AUTO_INCREMENT,
  `calificacion` INT NOT NULL,
  `Propiedad_idPropiedad` INT NOT NULL,
  INDEX `fk_Calificaciones_Propiedad1_idx` (`Propiedad_idPropiedad` ASC) VISIBLE,
  PRIMARY KEY (`calificaciónPropiedadId`),
  CONSTRAINT `fk_Calificaciones_Propiedad1`
    FOREIGN KEY (`Propiedad_idPropiedad`)
    REFERENCES `Doormat`.`Propiedad` (`idPropiedad`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Doormat`.`CalificacionesAgente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Doormat`.`CalificacionesAgente` (
  `calificacionesAgenteId` INT NOT NULL AUTO_INCREMENT,
  `calificación` INT NULL,
  `Agente_usuarioAgente` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`calificacionesAgenteId`),
  INDEX `fk_CalificacionesAgente_Agente1_idx` (`Agente_usuarioAgente` ASC) VISIBLE,
  CONSTRAINT `fk_CalificacionesAgente_Agente1`
    FOREIGN KEY (`Agente_usuarioAgente`)
    REFERENCES `Doormat`.`Agente` (`usuarioAgente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
