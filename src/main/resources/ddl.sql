CREATE TABLE `t_order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` decimal(11,2) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;