-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le: Lun 05 Janvier 2026 à 21:03
-- Version du serveur: 5.6.12-log
-- Version de PHP: 5.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `gestion_etudiant`
--
CREATE DATABASE IF NOT EXISTS `gestion_etudiant` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `gestion_etudiant`;

-- --------------------------------------------------------

--
-- Structure de la table `classroom`
--

CREATE TABLE IF NOT EXISTS `classroom` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `niveau` varchar(100) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_classroom_nom` (`nom`),
  KEY `idx_classroom_niveau` (`niveau`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=13 ;

--
-- Contenu de la table `classroom`
--

INSERT INTO `classroom` (`id`, `nom`, `niveau`, `created_at`, `updated_at`) VALUES
(1, '6ème A', 'Collège', '2025-12-18 02:15:35', '2025-12-18 02:15:35'),
(4, '5ème B', 'Collège', '2025-12-18 02:15:35', '2025-12-18 02:15:35'),
(6, '3ème A', 'Collège', '2025-12-18 02:15:35', '2025-12-18 02:15:35'),
(7, 'Seconde A', 'Lycée', '2025-12-18 02:15:35', '2025-12-18 02:15:35'),
(8, 'Seconde B', 'Lycée', '2025-12-18 02:15:35', '2025-12-18 02:15:35'),
(11, 'Terminale C', 'Lycée', '2025-12-18 02:15:35', '2025-12-18 02:15:35'),
(12, 'Terminale D', 'Lycée', '2025-12-18 02:15:35', '2025-12-18 02:15:35');

-- --------------------------------------------------------

--
-- Structure de la table `enrollment`
--

CREATE TABLE IF NOT EXISTS `enrollment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` varchar(50) NOT NULL,
  `classroom_id` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  `date_inscription` date NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_enrollment` (`student_id`,`classroom_id`,`year`),
  KEY `classroom_id` (`classroom_id`),
  KEY `idx_enrollment_year` (`year`),
  KEY `idx_enrollment_date` (`date_inscription`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- Structure de la table `personnel`
--

CREATE TABLE IF NOT EXISTS `personnel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(200) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=8 ;

--
-- Contenu de la table `personnel`
--

INSERT INTO `personnel` (`id`, `nom`, `created_at`, `updated_at`) VALUES
(7, 'Demanou', '2025-12-18 02:46:39', '2025-12-18 02:46:39');

-- --------------------------------------------------------

--
-- Structure de la table `student`
--

CREATE TABLE IF NOT EXISTS `student` (
  `id` varchar(50) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  `email` varchar(150) NOT NULL,
  `date_naissance` date NOT NULL,
  `sexe` char(1) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `idx_student_nom` (`nom`),
  KEY `idx_student_prenom` (`prenom`),
  KEY `idx_student_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Contenu de la table `student`
--

INSERT INTO `student` (`id`, `nom`, `prenom`, `email`, `date_naissance`, `sexe`, `created_at`, `updated_at`) VALUES
('2026002', 'fosso', 'careme', 'abchgf@gmail.com', '2023-04-06', 'M', '2026-01-05 21:02:30', '2026-01-05 21:02:30');

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `enrollment`
--
ALTER TABLE `enrollment`
  ADD CONSTRAINT `enrollment_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `enrollment_ibfk_2` FOREIGN KEY (`classroom_id`) REFERENCES `classroom` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
