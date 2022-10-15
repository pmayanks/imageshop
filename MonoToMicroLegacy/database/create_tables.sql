/**
 * Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

CREATE SCHEMA `unishop` DEFAULT CHARACTER SET UTF8MB4 ;

CREATE TABLE  `unishop`.`unicorns` (
-- general fields 
  	`id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  	`creation_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  	`last_modified_date` timestamp NULL,
  	`created_by_user_id` int(10) unsigned DEFAULT NULL,
  	`last_modified_by_user_id` int(10) unsigned DEFAULT NULL,
  	`active` tinyint(1) DEFAULT NULL,
--	model fields  
	`uuid` varchar(64) NOT NULL,
	`name` varchar(64) DEFAULT NULL,
	`description` varchar(256) DEFAULT NULL,  	
  	`price` decimal(6,2) unsigned DEFAULT NULL,
  	`image` varchar(256) DEFAULT NULL,
  	CONSTRAINT UnicornUnique UNIQUE (uuid),
  	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE  `unishop`.`unicorns_basket` (
-- general fields 
  	`id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  	`creation_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  	`last_modified_date` timestamp NULL,
  	`created_by_user_id` int(10) unsigned DEFAULT NULL,
  	`last_modified_by_user_id` int(10) unsigned DEFAULT NULL,
  	`active` tinyint(1) DEFAULT NULL,
--	model fields  
	`uuid` varchar(64) NOT NULL,
  	`unicornUuid` varchar(64) NOT NULL,		
  	CONSTRAINT UnicornUnique UNIQUE (uuid, unicornUuid),
  	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE  `unishop`.`unicorn_user` (
-- general fields 
  	`id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  	`creation_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  	`last_modified_date` timestamp NULL,
  	`created_by_user_id` int(10) unsigned DEFAULT NULL,
  	`last_modified_by_user_id` int(10) unsigned DEFAULT NULL,
  	`active` tinyint(1) DEFAULT NULL,
--	model fields  
	`uuid` varchar(64) NOT NULL,
	`email` varchar(64) NOT NULL,
  	`first_name` varchar(64) DEFAULT NULL,
  	`last_name` varchar(64) DEFAULT NULL,
  	CONSTRAINT UserUnique UNIQUE (email),
  	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

INSERT INTO `unishop`.`unicorns` (uuid, name, description, price, image) VALUES (UUID(),'cheetah', 'Nambia Cheetah', 100, 'cheetah');
INSERT INTO `unishop`.`unicorns` (uuid, name, description, price, image) VALUES (UUID(),'dolphin', 'Jumping Dolphin', 100, 'dolphin');
INSERT INTO `unishop`.`unicorns` (uuid, name, description, price, image) VALUES (UUID(),'elephant', 'Elephent Gazelle WildLife Zebra !', 100, 'elephant');
INSERT INTO `unishop`.`unicorns` (uuid, name, description, price, image) VALUES (UUID(),'koala', 'Koala Bear', 100, 'koala');
INSERT INTO `unishop`.`unicorns` (uuid, name, description, price, image) VALUES (UUID(),'owl', 'Nature Bird Owl', 100, 'owl');
INSERT INTO `unishop`.`unicorns` (uuid, name, description, price, image) VALUES (UUID(),'penguin', 'Baby Penguin Antartic Life', 100, 'penguin');
INSERT INTO `unishop`.`unicorns` (uuid, name, description, price, image) VALUES (UUID(),'tiger', 'Wild Life Animal Tiger', 100, 'tiger');
INSERT INTO `unishop`.`unicorns` (uuid, name, description, price, image) VALUES (UUID(),'turtle', 'Superfun Bestselling Unicorn Hooded Blanket', 100, 'turtle');
INSERT INTO `unishop`.`unicorns` (uuid, name, description, price, image) VALUES (UUID(),'whalehump', 'Whale Back Hump', 100, 'whalehump');
INSERT INTO `unishop`.`unicorns` (uuid, name, description, price, image) VALUES (UUID(),'wolf', 'Wolf The Predator', 100, 'wolf');
