CREATE database if NOT EXISTS `reminder`;

use `reminder`;
-- 2017-8-12
CREATE TABLE if NOT EXISTS `movie` (
	`id` bigint primary key auto_increment,
	`name` varchar(50) not null,
	`score` varchar(15),
	`releaseTime` datetime,
	`duration` varchar(20),
	`actors` varchar(50),
	`years` varchar(15),
	`director` varchar(30),
	`type` varchar(20),
	`area` varchar(15),
	`summary` text,
	`cover` varchar(75),
	INDEX(`releaseTime`),
	INDEX(`name`)
);