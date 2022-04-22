-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Apr 07, 2021 at 06:55 PM
-- Server version: 5.7.31
-- PHP Version: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `magasin`
--

-- --------------------------------------------------------

--
-- Table structure for table `commande`
--

DROP TABLE IF EXISTS `commande`;
CREATE TABLE IF NOT EXISTS `commande` (
  `numCommande` int(11) NOT NULL,
  `nom` varchar(30) NOT NULL,
  `adresse` varchar(100) NOT NULL,
  `numTelClient` varchar(14) NOT NULL,
  `emailClient` varchar(50) NOT NULL,
  `iconeURL` varchar(50) NOT NULL,
  `idLivreur` int(11) NOT NULL,
  `dateRecuperation` datetime NOT NULL,
  `delivred` int(11) NOT NULL,
  PRIMARY KEY (`numCommande`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `commande`
--

INSERT INTO `commande` (`numCommande`, `nom`, `adresse`, `numTelClient`, `emailClient`, `iconeURL`, `idLivreur`, `dateRecuperation`, `delivred`) VALUES
(0, 'command2', 'cite 08 1945 benceur Touggourt', '0558654354', 'khalifa.serraye@gmail.com', 'urelimage', 0, '2021-04-01 23:55:00', 0),
(1, 'cmd1', 'Cite universitair bouraoui amar - El harrach', '0659066875', 'exemple.exemple@gmail.com', 'notYetDefined', 0, '2020-04-28 00:00:00', 0),
(2, 'cmd2', 'Cite universitair el alia - babz', '0666666666', 'exemple.exemple@gmail.com', 'notYetDefined', 1, '2021-04-02 22:49:31', 0),
(3, 'command5', 'cite 08 1945 babz', '4563745637', 'khalifa.343@gmail.com', 'urelimage', 0, '2021-04-14 00:00:00', 0);

-- --------------------------------------------------------

--
-- Table structure for table `livraison`
--

DROP TABLE IF EXISTS `livraison`;
CREATE TABLE IF NOT EXISTS `livraison` (
  `nbrLivraison` int(11) NOT NULL,
  `totalEncaissement` int(11) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`date`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `livraison`
--

INSERT INTO `livraison` (`nbrLivraison`, `totalEncaissement`, `date`) VALUES
(12, 333, '2021-04-14'),
(3, 80220, '2021-04-07'),
(142, 443, '2021-04-11'),
(212, 340, '2021-04-13'),
(6, 3, '2021-04-05'),
(8, 203, '2021-04-06');

-- --------------------------------------------------------

--
-- Table structure for table `livreur`
--

DROP TABLE IF EXISTS `livreur`;
CREATE TABLE IF NOT EXISTS `livreur` (
  `username` varchar(30) NOT NULL,
  `firstName` varchar(30) NOT NULL,
  `lastName` varchar(30) NOT NULL,
  `pswd` varchar(30) NOT NULL,
  `idLivreur` int(11) NOT NULL,
  PRIMARY KEY (`idLivreur`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `livreur`
--

INSERT INTO `livreur` (`username`, `firstName`, `lastName`, `pswd`, `idLivreur`) VALUES
('serrkhal', 'khalifa', 'serraye', '111111', 0),
('feryel', 'Feryel', 'Djerboua', '222222', 1);

-- --------------------------------------------------------

--
-- Table structure for table `produit`
--

DROP TABLE IF EXISTS `produit`;
CREATE TABLE IF NOT EXISTS `produit` (
  `nomProduit` varchar(30) NOT NULL,
  `prix` int(11) NOT NULL,
  PRIMARY KEY (`nomProduit`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `produit`
--

INSERT INTO `produit` (`nomProduit`, `prix`) VALUES
('ParfumX', 2000),
('ParfumY', 3000),
('ShampoingA', 600),
('ShampoingB', 540),
('ShampoingC', 1000);

-- --------------------------------------------------------

--
-- Table structure for table `produitcommande`
--

DROP TABLE IF EXISTS `produitcommande`;
CREATE TABLE IF NOT EXISTS `produitcommande` (
  `numCommande` int(11) NOT NULL,
  `nomProduit` varchar(30) NOT NULL,
  `quantite` int(11) NOT NULL,
  PRIMARY KEY (`numCommande`,`nomProduit`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `produitcommande`
--

INSERT INTO `produitcommande` (`numCommande`, `nomProduit`, `quantite`) VALUES
(0, 'ParfumX', 3),
(0, 'ParfumY', 2),
(0, 'ShampoingA', 1),
(1, 'ParfumX', 1),
(2, 'ParfumX', 3),
(2, 'ParfumY', 3),
(3, 'ParfumY', 6),
(3, 'ShampoingA', 2),
(3, 'ShampoingB', 1),
(3, 'ShampoingC', 5);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
