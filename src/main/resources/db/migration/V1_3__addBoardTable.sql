CREATE TABLE IF NOT EXISTS  `boards` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hex` varchar(45) DEFAULT NULL,
  `flight` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;