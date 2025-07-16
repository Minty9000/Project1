-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: myprojectdb
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `schedule_stops`
--

DROP TABLE IF EXISTS `schedule_stops`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schedule_stops` (
  `stop_id` int NOT NULL AUTO_INCREMENT,
  `schedule_id` int DEFAULT NULL,
  `station_id` int DEFAULT NULL,
  `stop_time` datetime DEFAULT NULL,
  PRIMARY KEY (`stop_id`),
  KEY `schedule_id` (`schedule_id`),
  KEY `station_id` (`station_id`),
  CONSTRAINT `schedule_stops_ibfk_1` FOREIGN KEY (`schedule_id`) REFERENCES `train_schedules` (`schedule_id`),
  CONSTRAINT `schedule_stops_ibfk_2` FOREIGN KEY (`station_id`) REFERENCES `stations` (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule_stops`
--

LOCK TABLES `schedule_stops` WRITE;
/*!40000 ALTER TABLE `schedule_stops` DISABLE KEYS */;
INSERT INTO `schedule_stops` VALUES (1,1,2,'2025-07-16 07:12:00'),(2,1,3,'2025-07-16 07:24:00'),(3,1,4,'2025-07-16 07:40:00'),(4,1,6,'2025-07-16 08:05:00'),(5,1,5,'2025-07-16 08:30:00'),(6,2,6,'2025-07-16 18:15:00'),(7,2,4,'2025-07-16 18:35:00'),(8,2,3,'2025-07-16 18:50:00'),(9,2,2,'2025-07-16 19:10:00'),(10,2,1,'2025-07-16 19:30:00'),(11,3,6,'2025-07-16 08:45:00'),(12,4,2,'2025-07-16 09:12:00'),(13,4,3,'2025-07-16 09:24:00'),(14,4,4,'2025-07-16 09:40:00'),(15,4,6,'2025-07-16 10:05:00'),(16,4,5,'2025-07-16 10:30:00'),(17,5,2,'2025-07-16 11:12:00'),(18,5,3,'2025-07-16 11:24:00'),(19,5,4,'2025-07-16 11:40:00'),(20,5,6,'2025-07-16 12:05:00'),(21,5,5,'2025-07-16 12:25:00'),(22,6,2,'2025-07-16 15:12:00'),(23,6,3,'2025-07-16 15:24:00'),(24,6,4,'2025-07-16 15:40:00'),(25,6,6,'2025-07-16 16:05:00'),(26,6,5,'2025-07-16 16:25:00');
/*!40000 ALTER TABLE `schedule_stops` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stations`
--

DROP TABLE IF EXISTS `stations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stations` (
  `sid` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `city` varchar(100) NOT NULL,
  `state` varchar(50) NOT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stations`
--

LOCK TABLES `stations` WRITE;
/*!40000 ALTER TABLE `stations` DISABLE KEYS */;
INSERT INTO `stations` VALUES (1,'New York Penn Station','New York','NY'),(2,'Secaucus Junction','Secaucus','NJ'),(3,'Newark Penn Station','Newark','NJ'),(4,'Metropark','Iselin','NJ'),(5,'Trenton Transit Center','Trenton','NJ'),(6,'Princeton Junction','Princeton','NJ'),(7,'Philadelphia 30th Street','Philadelphia','PA');
/*!40000 ALTER TABLE `stations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `train_schedules`
--

DROP TABLE IF EXISTS `train_schedules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `train_schedules` (
  `schedule_id` int NOT NULL AUTO_INCREMENT,
  `train_id` int DEFAULT NULL,
  `transit_line_id` int DEFAULT NULL,
  `origin_station_id` int DEFAULT NULL,
  `destination_station_id` int DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `fare` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`schedule_id`),
  KEY `train_id` (`train_id`),
  KEY `transit_line_id` (`transit_line_id`),
  KEY `origin_station_id` (`origin_station_id`),
  KEY `destination_station_id` (`destination_station_id`),
  CONSTRAINT `train_schedules_ibfk_1` FOREIGN KEY (`train_id`) REFERENCES `trains` (`train_id`),
  CONSTRAINT `train_schedules_ibfk_2` FOREIGN KEY (`transit_line_id`) REFERENCES `transit_lines` (`transit_line_id`),
  CONSTRAINT `train_schedules_ibfk_3` FOREIGN KEY (`origin_station_id`) REFERENCES `stations` (`sid`),
  CONSTRAINT `train_schedules_ibfk_4` FOREIGN KEY (`destination_station_id`) REFERENCES `stations` (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `train_schedules`
--

LOCK TABLES `train_schedules` WRITE;
/*!40000 ALTER TABLE `train_schedules` DISABLE KEYS */;
INSERT INTO `train_schedules` VALUES (1,1,1,1,5,'2025-07-16 07:00:00','2025-07-16 08:30:00',18.50),(2,2,1,5,1,'2025-07-16 18:00:00','2025-07-16 19:30:00',18.50),(3,3,2,6,6,'2025-07-16 08:45:00','2025-07-16 09:00:00',3.00),(4,1,1,1,5,'2025-07-16 09:00:00','2025-07-16 10:30:00',20.00),(5,2,1,1,5,'2025-07-16 11:00:00','2025-07-16 12:25:00',16.50),(6,1,1,1,5,'2025-07-16 15:00:00','2025-07-16 16:25:00',19.50);
/*!40000 ALTER TABLE `train_schedules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trains`
--

DROP TABLE IF EXISTS `trains`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trains` (
  `train_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`train_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trains`
--

LOCK TABLES `trains` WRITE;
/*!40000 ALTER TABLE `trains` DISABLE KEYS */;
INSERT INTO `trains` VALUES (1,'Northeast Corridor 3823'),(2,'Northeast Corridor 3955'),(3,'NJ Transit Princeton Shuttle');
/*!40000 ALTER TABLE `trains` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transit_lines`
--

DROP TABLE IF EXISTS `transit_lines`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transit_lines` (
  `transit_line_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`transit_line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transit_lines`
--

LOCK TABLES `transit_lines` WRITE;
/*!40000 ALTER TABLE `transit_lines` DISABLE KEYS */;
INSERT INTO `transit_lines` VALUES (1,'Northeast Corridor Line'),(2,'Princeton Branch');
/*!40000 ALTER TABLE `transit_lines` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','admin','admin'),(2,'rep','rep','rep'),(3,'customer','customer','customer');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-16  8:18:27
