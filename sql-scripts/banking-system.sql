CREATE DATABASE IF NOT EXISTS `banking_system`;
USE `banking_system`;

--   table creation section
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `first_name` varchar(255) DEFAULT NULL,
    `last_name` varchar(255) DEFAULT NULL,
    `other_name` varchar(255) DEFAULT NULL,
    `email` varchar(255) UNIQUE,
    `phone_number` varchar(20) ,
    `alternative_phone_num` varchar(20),
    `address` varchar(255),
    `gender` ENUM('male','female','other'),
    `account_number` varchar(50) UNIQUE,
    `account_balance` DECIMAL(15,2),
    `place_of_birth` varchar(255),
    `status` varchar(50),
	`created_at`TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	`modified_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- **************** create new table for the transactions ********************
--   table creation section
DROP TABLE IF EXISTS `transactions`;
CREATE TABLE `transactions`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `source_account_number` varchar(50) NOT NULL,
    `destination_account_number` varchar(50) DEFAULT NULL,
    `amount` DECIMAL(15,2) NOT NULL,
    `transaction_type` varchar(255) NOT NULL,
    `status` varchar(255) DEFAULT 'success',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `modified_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (`id`)

)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

