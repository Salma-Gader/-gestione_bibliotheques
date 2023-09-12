-- Author Table
DROP TABLE IF EXISTS `reservations`;
DROP TABLE IF EXISTS `books`;
DROP TABLE IF EXISTS `authors`;

DROP TABLE IF EXISTS `borrowers`;

CREATE TABLE `authors` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO `authors` VALUES (1,'jamal'),(2,'JAMAL23');

-- Books Table 



CREATE TABLE `books` (
  `isbn` varchar(45) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `author_id` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`isbn`),
  CONSTRAINT `fk_author_id` FOREIGN KEY (`author_id`) REFERENCES `authors` (`id`)
);

INSERT INTO `books` VALUES ('0','0000',1,45),('1213','23123',1,23),('123','123',1,1234);

-- Borrowers Table 



CREATE TABLE `borrowers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
);


INSERT INTO `borrowers` VALUES (1,'jamal','05346546546');

-- Reservations Table





CREATE TABLE `reservations` (
  `isbn` varchar(45) NOT NULL,
  `borrower_id` int NOT NULL,
  `borrowing_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `duration` int DEFAULT NULL,
  `status` varchar(45) DEFAULT 'Borrowed',
  CONSTRAINT `fk_isbn` FOREIGN KEY (`isbn`) REFERENCES `books` (`isbn`),
  CONSTRAINT `fk_borrower_id` FOREIGN KEY (`borrower_id`) REFERENCES `borrowers` (`id`)
);


INSERT INTO `reservations` VALUES ('0',1,'2023-09-04 16:12:59',7,'Borrowed'),('123',1,'2023-09-04 16:25:44',1,'returned'),('123',1,'2023-09-04 16:26:26',4,'returned');