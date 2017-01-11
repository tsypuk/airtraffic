CREATE TABLE IF NOT EXISTS  `flights` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hex` varchar(45) DEFAULT NULL,
  `flight` varchar(45) DEFAULT NULL,
  `lat` varchar(45) DEFAULT NULL,
  `lon` varchar(45) DEFAULT NULL,
  `altitude` varchar(45) DEFAULT NULL,
  `track` varchar(45) DEFAULT NULL,
  `speed` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;