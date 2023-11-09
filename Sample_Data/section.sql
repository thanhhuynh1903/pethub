-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: pethubdb
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `sections`
--

LOCK TABLES `sections` WRITE;
/*!40000 ALTER TABLE `sections` DISABLE KEYS */;
INSERT INTO `sections` VALUES (14,'All product categories on our website',_binary '','Shopping by Categories',4,'ALL_CATEGORIES'),(15,'Most recommended products',_binary '','Featured Products',3,'PRODUCT'),(16,'',_binary '','Featured Categories',2,'CATEGORY'),(17,'We recommend these brands for you....',_binary '','Featured Brands',5,'BRAND'),(18,'',_binary '','Recommended Reading',6,'ARTICLE'),(19,'<div><div><b>We would like to announce that we will be having a Summer Clearance Sale.&nbsp;</b></div><div>We will be offering up to<b><font color=\"#ff0000\"> 50% discoun</font></b>t on all merchandise starting from June 01st to June 30th, 2023.</div></div>',_binary '','Annoucements',1,'TEXT');
/*!40000 ALTER TABLE `sections` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sections_articles`
--

LOCK TABLES `sections_articles` WRITE;
/*!40000 ALTER TABLE `sections_articles` DISABLE KEYS */;
INSERT INTO `sections_articles` VALUES (8,0,1,18),(9,1,11,18),(10,2,4,18),(11,3,9,18);
/*!40000 ALTER TABLE `sections_articles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sections_brands`
--

LOCK TABLES `sections_brands` WRITE;
/*!40000 ALTER TABLE `sections_brands` DISABLE KEYS */;
INSERT INTO `sections_brands` VALUES (12,2,9,17),(13,3,1,17),(14,4,38,17),(15,0,21,17),(16,5,39,17),(17,1,10,17);
/*!40000 ALTER TABLE `sections_brands` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sections_categories`
--

LOCK TABLES `sections_categories` WRITE;
/*!40000 ALTER TABLE `sections_categories` DISABLE KEYS */;
INSERT INTO `sections_categories` VALUES (12,0,6,16),(13,1,7,16),(14,2,10,16),(15,3,4,16);
/*!40000 ALTER TABLE `sections_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sections_products`
--

LOCK TABLES `sections_products` WRITE;
/*!40000 ALTER TABLE `sections_products` DISABLE KEYS */;
INSERT INTO `sections_products` VALUES (9,2,7,15),(11,3,76,15),(12,1,1,15),(13,0,104,15),(14,4,63,15),(15,5,86,15);
/*!40000 ALTER TABLE `sections_products` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-09 21:57:05
