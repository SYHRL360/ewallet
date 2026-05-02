CREATE DATABASE IF NOT EXISTS e_wallet;
USE e_wallet;

CREATE TABLE IF NOT EXISTS  `e_wallet`.`user` (
`id` int NOT NULL AUTO_INCREMENT,
`email` varchar(100) NOT NULL,
`first_name` varchar(50) DEFAULT NULL,
`last_name` varchar(50) DEFAULT NULL,
`password` varchar(255) NOT NULL,
CONSTRAINT user_email UNIQUE (email),
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `e_wallet`.`banner` (
`id` int NOT NULL AUTO_INCREMENT,
`banner_name` VARCHAR(255) NOT NULL,
`banner_image` VARCHAR(255) NOT NULL,
 `description` VARCHAR(255) NOT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `e_wallet`.`banner` (`banner_name`, banner_image, `description`)
VALUES ('Banner 1', 'https://minio.nutech-integrasi.com/take-home-test/banner/Banner-1.png', 'Lorem Ipsum Dolor sit amet'),
       ('Banner 2', 'https://minio.nutech-integrasi.com/take-home-test/banner/Banner-2.png', 'Lorem Ipsum Dolor sit amet'),
       ('Banner 3', 'https://minio.nutech-integrasi.com/take-home-test/banner/Banner-3.png', 'Lorem Ipsum Dolor sit amet'),
       ('Banner 4', 'https://minio.nutech-integrasi.com/take-home-test/banner/Banner-4.png', 'Lorem Ipsum Dolor sit amet'),
       ('Banner 5', 'https://minio.nutech-integrasi.com/take-home-test/banner/Banner-5.png', 'Lorem Ipsum Dolor sit amet');

CREATE TABLE IF NOT EXISTS `e_wallet`.`ppob_service` (
`id` int NOT NULL AUTO_INCREMENT,
 `service_code` VARCHAR(50) NOT NULL,
 `service_name` VARCHAR(50) NOT NULL,
 `service_icon` VARCHAR(255) NOT NULL,
 `service_tariff` INT NOT NULL,
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `e_wallet`.`ppob_service` (`service_code`, `service_name`, `service_icon`, `service_tariff`)
VALUES ('PAJAK', 'Pajak PBB', 'https://minio.nutech-integrasi.com/take-home-test/services/PBB.png', 40000),
       ('PLN', 'Listrik', 'https://minio.nutech-integrasi.com/take-home-test/services/Listrik.png', 10000),
       ('PDAM', 'PDAM Berlangganan', 'https://minio.nutech-integrasi.com/take-home-test/services/PDAM.png', 40000),
       ('PULSA', 'Pulsa', 'https://minio.nutech-integrasi.com/take-home-test/services/Pulsa.png', 40000),
       ('PGN', 'PGN Berlangganan','https://minio.nutech-integrasi.com/take-home-test/services/PGN.png', 50000),
       ('MUSIK', 'Musik Berlangganan', 'https://minio.nutech-integrasi.com/take-home-test/services/Musik.png', 50000),
       ('TV', 'TV Berlangganan', 'https://minio.nutech-integrasi.com/take-home-test/services/Televisi.png', 50000),
       ('PAKET_DATA', 'Paket Data', 'https://minio.nutech-integrasi.com/take-home-test/services/Paket-Data.png', 50000),
       ('VOUCHER_GAME', 'Voucher Game', 'https://minio.nutech-integrasi.com/take-home-test/services/Game.png', 100000),
       ('VOUCHER_MAKANAN', 'Voucher Makanan', 'https://minio.nutech-integrasi.com/take-home-test/services/Voucher-Makanan.png', 100000),
       ('QURBAN', 'Qurban', 'https://minio.nutech-integrasi.com/take-home-test/services/Qurban.png', 200000),
       ('ZAKAT', 'Zakat', 'https://minio.nutech-integrasi.com/take-home-test/services/Zakat.png', 300000);


CREATE TABLE IF NOT EXISTS `e_wallet`.`balance` (
`id` INT NOT NULL AUTO_INCREMENT,
`email` VARCHAR(100) NOT NULL,
`balance` INT NOT NULL,
 CONSTRAINT user_email UNIQUE (email),
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `e_wallet`.`transaction` (
`id` int NOT NULL AUTO_INCREMENT,
`invoice_number` VARCHAR(128) NOT NULL,
`transaction_type` VARCHAR(50) NOT NULL,
 `description` VARCHAR(255) NOT NULL,
 `total_amount` INT NOT NULL,
 `create_on` DATETIME DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;












