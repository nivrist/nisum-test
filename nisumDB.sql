CREATE DATABASE  IF NOT EXISTS `nisumdb` ;

USE `nisumdb`;


CREATE TABLE `users` (
  `id` varchar(36) NOT NULL,
  `name` varchar(150) DEFAULT NULL,
  `password` varchar(150) DEFAULT NULL,
  `email` varchar(150) DEFAULT NULL,
  `token` varchar(4000) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `last_login` datetime DEFAULT NULL,
  `isactive` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE `user_phones` (
  `id` int NOT NULL,
  `id_user` varchar(36) DEFAULT NULL,
  `number` varchar(25) DEFAULT NULL,
  `city_code` varchar(10) DEFAULT NULL,
  `contry_code` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_idx` (`id_user`),
  CONSTRAINT `fk_user` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`)
);


