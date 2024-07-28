-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 28-07-2024 a las 06:48:26
-- Versión del servidor: 8.2.0
-- Versión de PHP: 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `desafio_db`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `insumos_medicos`
--

DROP TABLE IF EXISTS `insumos_medicos`;
CREATE TABLE IF NOT EXISTS `insumos_medicos` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `Cantidad` int NOT NULL,
  `Precio` double(4,2) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `insumos_medicos`
--

INSERT INTO `insumos_medicos` (`Id`, `Nombre`, `Cantidad`, `Precio`) VALUES
(1, 'Mascarillas quirúrgicas', 40, 3.50),
(2, 'Alcohol gel', 22, 2.45),
(3, 'El Contexto', 69, 9.50),
(4, 'Void meme', 1, 99.99);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
