SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+01:00";

CREATE SCHEMA IF NOT EXISTS event_database;
USE event_database;

CREATE TABLE IF NOT EXISTS `events` (
    `name` VARCHAR(100) NOT NULL,
    `seats` INT NOT NULL,
    `max_seats` INT NOT NULL,
    PRIMARY KEY(`name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci AUTO_INCREMENT=1;


INSERT INTO `events` (
    `name`, `seats`, `max_seats`
) VALUES ( "Il lago dei Cigni, regia Enzo Paolo Turchi" , 241, 2000);
INSERT INTO `events` (
    `name`, `seats`, `max_seats`
) VALUES ( "Chi ha incastrato Massimo Boldi (il fango)" , 226, 240);
INSERT INTO `events` (
    `name`, `seats`, `max_seats`
) VALUES ( "Benvenuti nella cucina di Iginio Massari" , 12, 30);
INSERT INTO `events` (
    `name`, `seats`, `max_seats`
) VALUES ( "Gero Caldarelli, realta' o finzione" , 350, 350);
INSERT INTO `events` (
    `name`, `seats`, `max_seats`
) VALUES ( "Wanna Marchi, abile Lupin o commerciante inplacabile" , 10, 10000);
