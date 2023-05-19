# ToDo-app
este es un proyecto personal de una API diseñada para una To-Do app utilizando spring boot 3.0.5

utilizo tecnologias y librerias como: spring security implementando jwt, hibernate para el mapeo de las entidades a una base de datos mysql,
log4j para el logeo de informacion sobre las consultas que son realizadas a la api

# Para correr la app
1- tener mysql instalado porque la base de datos esta hosteada en el localhost corriendo en el puerto 3306(por defecto de mysql)
2- correr el siguiente script en la cli de mysql para generar la db la cual es mapeada por hibernate:

CREATE SCHEMA IF NOT EXISTS `to-do_app` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `to-do_app` ;

CREATE TABLE `to-do_app`.`users` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL UNIQUE,
  `first_name` VARCHAR(255) NOT NULL,
  `last_name` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `role` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_r53o2ojjw4fikudfnsuuga336` (`password` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `to-do_app`.`tasks` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `completed` BIT(1) NOT NULL,
  `created_at` DATETIME(6) NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `finished_at` DATETIME(6) DEFAULT NULL,
  `user_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK6s1ob9k4ihi75xbxe2w0ylsdh` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FK6s1ob9k4ihi75xbxe2w0ylsdh`
    FOREIGN KEY (`user_id`)
    REFERENCES `to-do_app`.`users` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

3- ejecutar el metodo main (tener java e intellij instalado)

PD: la app (tomcat) corre en el puerto 8080 del localhost
