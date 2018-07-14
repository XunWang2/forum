/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.5.50 : Database - forum
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`forum` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `forum`;

/*Table structure for table `basic_user` */

DROP TABLE IF EXISTS `basic_user`;

CREATE TABLE `basic_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户Id',
  `username` varchar(500) NOT NULL COMMENT '用户名',
  `password` varchar(500) NOT NULL COMMENT '密码',
  `head_Img` varchar(1000) DEFAULT NULL,
  `sex` int(11) DEFAULT '0' COMMENT '性别',
  `intro` varchar(2000) DEFAULT NULL COMMENT '个人简介',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Table structure for table `child_comments` */

DROP TABLE IF EXISTS `child_comments`;

CREATE TABLE `child_comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `forum_posts_id` int(11) NOT NULL,
  `content` varchar(1000) NOT NULL,
  `create_Date` varchar(200) NOT NULL,
  `parent_comment_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `forum_posts_id` int(11) NOT NULL,
  `content` varchar(1000) NOT NULL,
  `create_Date` varchar(1000) NOT NULL,
  `like` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;

/*Table structure for table `forum_posts` */

DROP TABLE IF EXISTS `forum_posts`;

CREATE TABLE `forum_posts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL COMMENT '标题',
  `content` varchar(20000) NOT NULL COMMENT '内容',
  `cover` varchar(500) DEFAULT NULL COMMENT '封面图片地址',
  `user_id` int(11) NOT NULL COMMENT '关联用户id',
  `isShow` int(11) NOT NULL COMMENT '是否公开',
  `praise` int(11) DEFAULT '0' COMMENT '赞',
  `trample` int(11) DEFAULT '0' COMMENT '踩',
  `look_time` int(11) DEFAULT '0' COMMENT '查看次数',
  `create_date` varchar(200) NOT NULL COMMENT '创建时间',
  `text` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

/*Table structure for table `i_like_the_article` */

DROP TABLE IF EXISTS `i_like_the_article`;

CREATE TABLE `i_like_the_article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8;

/*Table structure for table `i_like_the_comment` */

DROP TABLE IF EXISTS `i_like_the_comment`;

CREATE TABLE `i_like_the_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
