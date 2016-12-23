-- MySQL dump 10.13  Distrib 5.5.53, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: usp
-- ------------------------------------------------------
-- Server version	5.5.53-0ubuntu0.14.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `R_ROLE_PERMISSION`
--

DROP TABLE IF EXISTS `R_ROLE_PERMISSION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_ROLE_PERMISSION` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` bigint(20) DEFAULT NULL,
  `PERMISSION_ID` bigint(20) DEFAULT NULL,
  `DELETED` tinyint(4) DEFAULT '0',
  `CREATE_AT` datetime DEFAULT NULL,
  `UPDATE_AT` datetime DEFAULT NULL,
  `CREATE_BY` bigint(20) DEFAULT NULL,
  `UPDATE_BY` bigint(20) DEFAULT NULL,
  `DATA_SOURCE` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_ROLE_PERMISSION`
--

LOCK TABLES `R_ROLE_PERMISSION` WRITE;
/*!40000 ALTER TABLE `R_ROLE_PERMISSION` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_ROLE_PERMISSION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `R_USER_ROLE`
--

DROP TABLE IF EXISTS `R_USER_ROLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `R_USER_ROLE` (
  `ID` bigint(20) NOT NULL,
  `USER_ID` bigint(20) DEFAULT NULL,
  `ROLE_ID` bigint(20) DEFAULT NULL,
  `DELETED` tinyint(4) DEFAULT '0',
  `CREATE_AT` datetime DEFAULT NULL,
  `UPDATE_AT` datetime DEFAULT NULL,
  `CREATE_BY` bigint(20) DEFAULT NULL,
  `UPDATE_BY` bigint(20) DEFAULT NULL,
  `DATA_SOURCE` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `R_USER_ROLE`
--

LOCK TABLES `R_USER_ROLE` WRITE;
/*!40000 ALTER TABLE `R_USER_ROLE` DISABLE KEYS */;
/*!40000 ALTER TABLE `R_USER_ROLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_PERMISSION`
--

DROP TABLE IF EXISTS `T_PERMISSION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_PERMISSION` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(45) DEFAULT NULL,
  `NAME` varchar(45) NOT NULL,
  `LABEL` varchar(45) DEFAULT NULL,
  `DESCRIPTION` varchar(45) DEFAULT NULL,
  `DELETED` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_PERMISSION`
--

LOCK TABLES `T_PERMISSION` WRITE;
/*!40000 ALTER TABLE `T_PERMISSION` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_PERMISSION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_ROLE`
--

DROP TABLE IF EXISTS `T_ROLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_ROLE` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(45) DEFAULT NULL,
  `NAME` varchar(45) NOT NULL,
  `LABEL` varchar(45) DEFAULT NULL,
  `DESCRIPTION` varchar(45) DEFAULT NULL,
  `DELETED` tinyint(4) DEFAULT '0',
  `CREATE_AT` datetime DEFAULT NULL,
  `UPDATE_AT` datetime DEFAULT NULL,
  `CREATE_BY` bigint(20) DEFAULT NULL,
  `UPDATE_BY` bigint(20) DEFAULT NULL,
  `DATA_SOURCE` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_ROLE`
--

LOCK TABLES `T_ROLE` WRITE;
/*!40000 ALTER TABLE `T_ROLE` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_ROLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_TOKEN`
--

DROP TABLE IF EXISTS `T_TOKEN`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_TOKEN` (
  `TOKEN_VALUE` varchar(100) NOT NULL,
  `TERMINAL_IP` varchar(45) DEFAULT NULL,
  `ACTIVE` tinyint(4) DEFAULT '1',
  `USERNAME` varchar(45) DEFAULT NULL,
  `PASSWORD` varchar(45) DEFAULT NULL,
  `LAST_MODIFIED` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`TOKEN_VALUE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_TOKEN`
--

LOCK TABLES `T_TOKEN` WRITE;
/*!40000 ALTER TABLE `T_TOKEN` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_TOKEN` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_USER`
--

DROP TABLE IF EXISTS `T_USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_USER` (
  `ID` bigint(20) NOT NULL,
  `CODE` varchar(45) DEFAULT NULL,
  `USERNAME` varchar(45) DEFAULT NULL,
  `PASSWORD` varchar(45) DEFAULT NULL,
  `NAME` varchar(45) DEFAULT NULL,
  `GENDER` char(1) DEFAULT NULL,
  `MOBILE_PHONE` varchar(45) DEFAULT NULL,
  `TELEPHONE` varchar(45) DEFAULT NULL,
  `EMAIL` varchar(45) DEFAULT NULL,
  `ACTIVE` tinyint(4) DEFAULT '1',
  `LOCKED` tinyint(4) DEFAULT '0',
  `DELETED` tinyint(4) DEFAULT '0',
  `CREATE_AT` datetime DEFAULT NULL,
  `UPDATE_AT` datetime DEFAULT NULL,
  `CREATE_BY` bigint(20) DEFAULT NULL,
  `UPDATE_BY` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_USER`
--

LOCK TABLES `T_USER` WRITE;
/*!40000 ALTER TABLE `T_USER` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_USER` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-23 17:50:45
