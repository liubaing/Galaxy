create database galaxy default charset utf8;

DROP TABLE IF EXISTS `t_galaxy_account`;
CREATE TABLE `t_galaxy_account` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL DEFAULT '' COMMENT '邮箱',
  `password` varchar(50) NOT NULL DEFAULT '' COMMENT '密码',
  `balance` decimal(12,2) DEFAULT '0.00' COMMENT '余额',
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `phone` varchar(50) DEFAULT NULL COMMENT '联系电话',
  `state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态：激活、未激活',
  `date_created` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `date_edited` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `邮箱不允许重复` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户';
