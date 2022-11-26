/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.25-log : Database - meowsauce
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `admin_menu` */

CREATE TABLE `admin_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(64) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `name_zh` varchar(64) DEFAULT NULL,
  `icon_cls` varchar(64) DEFAULT NULL,
  `component` varchar(64) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `admin_menu` */

insert  into `admin_menu`(`id`,`path`,`name`,`name_zh`,`icon_cls`,`component`,`parent_id`) values (1,'/admin','AdminIndex','首页','el-icon-s-home','AdminIndex',0),(2,'/admin/dashboard','DashboardAdmin','运行情况',NULL,'dashboard/admin/index',1),(3,'/admin','User','用户管理','el-icon-user','AdminIndex',0),(4,'/admin','Content','内容管理','el-icon-tickets','AdminIndex',0),(5,'/admin','System','系统配置','el-icon-s-tools','AdminIndex',0),(6,'/admin/user/profile','Profile','用户信息',NULL,'user/UserProfile',3),(7,'/admin/user/role','Role','角色配置',NULL,'user/Role',3),(8,'/admin/content/comment','CommentManagement','评论管理',NULL,'content/CommentManagement',4),(9,'/admin/content/friend','FriendManagement','友链管理',NULL,'content/FriendManagement',4),(10,'/admin/content/article','ArticleManagement','文章管理',NULL,'content/ArticleManagement',4),(11,'/admin/content/signature','SignatureManagement','签名管理',NULL,'content/SignatureManagement',4);

/*Table structure for table `admin_permission` */

CREATE TABLE `admin_permission` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `des` varchar(100) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `admin_permission` */

insert  into `admin_permission`(`id`,`name`,`des`,`url`) values (1,'users_management','用户管理','/api/admin/user'),(2,'roles_management','角色管理','/api/admin/role'),(3,'content_management','内容管理','/api/admin/content');

/*Table structure for table `admin_role` */

CREATE TABLE `admin_role` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `name_zh` varchar(64) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `admin_role` */

insert  into `admin_role`(`id`,`name`,`name_zh`,`enabled`) values (1,'sysAdmin','系统管理员',1),(2,'contentManager','内容管理员',1),(3,'visitor','访客',1),(4,'test','测试角色',1);

/*Table structure for table `admin_role_menu` */

CREATE TABLE `admin_role_menu` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `rid` int(50) DEFAULT NULL,
  `mid` int(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8;

/*Data for the table `admin_role_menu` */

insert  into `admin_role_menu`(`id`,`rid`,`mid`) values (1,1,1),(2,1,2),(3,1,3),(5,1,5),(6,1,6),(7,1,7),(8,1,8),(9,1,9),(10,1,10),(11,2,1),(12,2,2),(13,2,4),(14,2,8),(15,2,8),(16,2,9),(17,2,10),(18,3,1),(19,3,2),(28,1,4),(65,4,1),(66,4,2),(67,4,4),(68,4,8),(69,4,9),(70,4,10),(71,4,11),(72,1,11),(73,2,11);

/*Table structure for table `admin_role_permission` */

CREATE TABLE `admin_role_permission` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `rid` int(50) DEFAULT NULL,
  `pid` int(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `admin_role_permission` */

insert  into `admin_role_permission`(`id`,`rid`,`pid`) values (1,1,1),(2,1,2),(3,1,3);

/*Table structure for table `admin_user_role` */

CREATE TABLE `admin_user_role` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `uid` int(50) NOT NULL,
  `rid` int(50) DEFAULT NULL,
  PRIMARY KEY (`id`,`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `admin_user_role` */

insert  into `admin_user_role`(`id`,`uid`,`rid`) values (1,3,1),(2,2,2),(8,6,1),(9,7,2),(10,5,3);

/*Table structure for table `article` */

CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_title` varchar(255) DEFAULT NULL,
  `article_content_html` longtext,
  `article_content_md` longtext,
  `article_abstract` varchar(255) DEFAULT NULL,
  `article_cover` varchar(255) DEFAULT NULL,
  `article_date` datetime DEFAULT NULL,
  `article_view` int(50) DEFAULT NULL,
  `article_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `article` */

insert  into `article`(`id`,`article_title`,`article_content_html`,`article_content_md`,`article_abstract`,`article_cover`,`article_date`,`article_view`,`article_type`) values (1,'凉风有兴','凉风有兴，秋月无边，而我思乡的情绪好比度日如年。虽然我风流倜傥玉树临风，但我还是有聪明的头脑和强健的臂腕。','凉风有兴，秋月无边，而我思乡的情绪好比度日如年。虽然我风流倜傥玉树临风，但我还是有聪明的头脑和强健的臂腕。','凉风有兴，秋月无边，而我思乡的情绪好比度日如年。','https://meowsauce.oss-cn-hangzhou.aliyuncs.com/blogImg%20/2022-11-17/05ae7c98bc7b4137becbea805144e076-.jpg','2021-03-13 00:00:00',8,'生活向'),(2,'爱你一万年','<p>曾经有份真挚的爱情摆在我的面前，我没有珍惜，等到失去的时候才后悔莫急，人世间最痛苦的事莫过余此，如果上天在给我一次机会，我会对那个女孩说我爱你，如果要在这份爱上加个期限，我希望是一万年。</p>\n','曾经有份真挚的爱情摆在我的面前，我没有珍惜，等到失去的时候才后悔莫急，人世间最痛苦的事莫过余此，如果上天在给我一次机会，我会对那个女孩说我爱你，如果要在这份爱上加个期限，我希望是一万年。','曾经有份真挚的爱情摆在我的面前，我没有珍惜，等到失去的时候才后悔莫及，人世间最痛苦的事莫过于此。','https://meowsauce.oss-cn-hangzhou.aliyuncs.com/blogImg%20/2022-11-17/eed7fbf26cda4e3fbab4c7a71bd24006-.jpg','2021-03-03 00:00:00',2,'技术向'),(4,'测试文章功能','<p>密码管理这件事我从来没有在意过，很久之前就白嫖到朋友的1Password家庭账户但是使用频率极低，前段时间在社工库查了一下自己，发现很多密码泄露，而且都是同一个密码的时候我慌了，这个问题足够严重但现在补救也不算晚。</p>\n<p>个人使用要求：</p>\n<p>开源免费<br />\n跨平台<br />\n安全<br />\n方便管理<br />\n最终找到了 Bitwarden</p>\n<p>我没有使用Bitwarden官方镜像，因 Bitwarden 服务器使用.Net开发，Docker 镜像体积过大，此外它使用MSSQL数据库，这个数据库对服务器性能要求比较高。</p>\n<p>所以有人利用Rust重写了一遍，根据官方项目同步更新，这个项目的Docker镜像叫做vaultwarden，这个镜像体积比较小，对服务器性能要求比较低，所以采用该镜像。</p>\n<p>部署<br />\n宝塔安装docker<br />\n宝塔面板选择软件商店 - docker管理器 - 安装</p>\n<h2><a id=\"_17\"></a>你好</h2>\n<p><code>HelloWorld</code></p>\n','密码管理这件事我从来没有在意过，很久之前就白嫖到朋友的1Password家庭账户但是使用频率极低，前段时间在社工库查了一下自己，发现很多密码泄露，而且都是同一个密码的时候我慌了，这个问题足够严重但现在补救也不算晚。\n\n个人使用要求：\n\n开源免费\n跨平台\n安全\n方便管理\n最终找到了 Bitwarden\n\n我没有使用Bitwarden官方镜像，因 Bitwarden 服务器使用.Net开发，Docker 镜像体积过大，此外它使用MSSQL数据库，这个数据库对服务器性能要求比较高。\n\n所以有人利用Rust重写了一遍，根据官方项目同步更新，这个项目的Docker镜像叫做vaultwarden，这个镜像体积比较小，对服务器性能要求比较低，所以采用该镜像。\n\n部署\n宝塔安装docker\n宝塔面板选择软件商店 - docker管理器 - 安装\n## 你好\n```HelloWorld```','测试文章功能','https://meowsauce.oss-cn-hangzhou.aliyuncs.com/blogImg /2022-11-17/1536ffb3ee5a4cbea437852f47ea23ac-.jpg','2022-11-09 00:00:00',40,'非原创');

/*Table structure for table `article_label` */

CREATE TABLE `article_label` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `lid` int(50) DEFAULT NULL COMMENT '标签ID',
  `aid` int(50) DEFAULT NULL COMMENT '文章ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Data for the table `article_label` */

insert  into `article_label`(`id`,`lid`,`aid`) values (1,1,4),(2,2,4),(3,6,4),(4,1,1),(8,2,1),(9,6,1),(10,1,2),(11,2,2),(12,6,2);

/*Table structure for table `comments` */

CREATE TABLE `comments` (
  `id` int(50) NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `article_id` int(50) DEFAULT NULL COMMENT '评论博文ID',
  `comment_date` datetime DEFAULT NULL COMMENT '评论日期',
  `comment_content` longtext NOT NULL COMMENT '评论内容',
  `parent_comment_id` int(50) NOT NULL COMMENT '父评论ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

/*Data for the table `comments` */

insert  into `comments`(`id`,`article_id`,`comment_date`,`comment_content`,`parent_comment_id`) values (1,1,'2022-11-12 18:00:00','测试评论功能',0),(2,1,'2022-11-12 09:00:00','测试子评论功能',1),(20,1,'2022-11-12 19:15:54','评论功能第十四次测试',0),(21,1,'2022-11-12 19:16:51','评论功能第十五次测试',0),(22,1,'2022-11-12 19:18:15','评论功能16次测试',0),(23,1,'2022-11-12 19:19:33','评论功能第十七次测试',0),(24,1,'2022-11-12 19:22:09','给我过阿啊啊啊啊啊',0),(25,1,'2022-11-12 19:34:37','给爷过',1),(32,4,'2022-11-15 20:09:38','测试',0),(33,4,'2022-11-15 20:31:23','测试评论',0),(34,-1,'2022-11-16 18:43:19','测试评论',0),(35,4,'2022-11-22 15:41:17','测试二级评论',32);

/*Table structure for table `comments_visitor` */

CREATE TABLE `comments_visitor` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `cid` int(50) DEFAULT NULL,
  `vid` int(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

/*Data for the table `comments_visitor` */

insert  into `comments_visitor`(`id`,`cid`,`vid`) values (1,1,1),(2,2,2),(10,12,12),(11,13,13),(12,14,14),(13,15,15),(14,16,16),(15,17,17),(16,18,18),(17,19,19),(18,20,20),(19,21,21),(20,22,22),(21,23,23),(22,24,24),(23,25,25),(30,32,32),(31,33,33),(32,34,34),(33,35,35);

/*Table structure for table `friends` */

CREATE TABLE `friends` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `friend_name` varchar(255) DEFAULT NULL COMMENT '好友网站名称',
  `friend_address` varchar(255) DEFAULT NULL COMMENT '好友网站地址',
  `friend_icon` varchar(255) DEFAULT NULL COMMENT '好友网站图标',
  `friend_introduce` varchar(255) DEFAULT NULL COMMENT '好友网站介绍',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `friends` */

insert  into `friends`(`id`,`friend_name`,`friend_address`,`friend_icon`,`friend_introduce`) values (1,'Meowsauce\'s Blog','https://www.bilibili.com','https://q1.qlogo.cn/g?b=qq&nk=3232054541&s=100','喵酱的小屋'),(2,'Meowsauce\'s Blog','https://www.bilibili.com','https://q1.qlogo.cn/g?b=qq&nk=3232054541&s=100','喵酱的小站'),(3,'Meowsauce\'s Blog','https://www.bilibili.com','https://q1.qlogo.cn/g?b=qq&nk=3232054541&s=100','喵酱的小站'),(5,'天台','https://www.bilibili.com','https://q1.qlogo.cn/g?b=qq&nk=3125017548&s=100','喵酱的老站');

/*Table structure for table `labels` */

CREATE TABLE `labels` (
  `id` int(50) NOT NULL AUTO_INCREMENT COMMENT '标签ID',
  `label_name` varchar(50) NOT NULL COMMENT '标签名称',
  `label_date` datetime DEFAULT NULL COMMENT '标签时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `labels` */

insert  into `labels`(`id`,`label_name`,`label_date`) values (1,'教程','2022-11-16 19:03:58'),(2,'碎碎念','2022-11-16 21:12:24'),(6,'测试文章','2022-11-17 14:49:32');

/*Table structure for table `pigeonhole` */

CREATE TABLE `pigeonhole` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `pigeonhole_date` datetime DEFAULT NULL,
  `pigeonhole_content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `pigeonhole` */

insert  into `pigeonhole`(`id`,`pigeonhole_date`,`pigeonhole_content`) values (1,'2022-10-28 11:05:26','导航栏,登录模块,卡片组件'),(2,'2022-10-29 17:07:14','轮播图,响应式优化'),(3,'2022-10-31 16:08:12','首页,最新回复组件,随便看看组件,页脚'),(4,'2022-11-01 09:34:43','登录注册实现shiro验证,注册功能实现腾讯短信验证,简单整合redis,获取Ip'),(5,'2022-11-05 15:22:12','优化代码,低配版动态加载后台菜单,用户管理之用户信息管理'),(6,'2022-11-08 16:24:38','角色管理,文章增删改查'),(7,'2022-11-11 11:13:25','完整评论功能'),(8,'2022-11-14 10:43:56','搜索功能,最新评论');

/*Table structure for table `signature` */

CREATE TABLE `signature` (
  `id` int(50) NOT NULL AUTO_INCREMENT COMMENT '签名Id',
  `signature_date` datetime DEFAULT NULL COMMENT '签名时间',
  `signature_content` varchar(255) DEFAULT NULL COMMENT '签名内容',
  `signature_icon` varchar(255) DEFAULT 'el-icon-s-goods' COMMENT '签名icon',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `signature` */

insert  into `signature`(`id`,`signature_date`,`signature_content`,`signature_icon`) values (2,'2022-11-16 14:23:40','花海盛开,燕子归来','el-icon-s-goods'),(4,'2022-11-23 15:52:04','花有重开人,人无再少年','el-icon-headset');

/*Table structure for table `user` */

CREATE TABLE `user` (
  `user_id` bigint(50) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_ip` varchar(50) DEFAULT NULL COMMENT '用户IP',
  `user_name` varchar(20) NOT NULL COMMENT '用户名',
  `user_password` varchar(255) NOT NULL COMMENT '用户密码',
  `user_email` varchar(40) DEFAULT NULL COMMENT '用户邮箱',
  `user_profile_photo` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `user_registration_time` date DEFAULT NULL COMMENT '注册时间',
  `user_birthday` date DEFAULT NULL COMMENT '用户生日',
  `user_telephone_number` varchar(50) DEFAULT NULL COMMENT '用户手机号',
  `user_nickname` varchar(20) DEFAULT NULL COMMENT '用户昵称',
  `salt` varchar(255) DEFAULT NULL COMMENT '加密盐',
  `enabled` tinyint(1) DEFAULT '1' COMMENT '授权使用',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`user_id`,`user_ip`,`user_name`,`user_password`,`user_email`,`user_profile_photo`,`user_registration_time`,`user_birthday`,`user_telephone_number`,`user_nickname`,`salt`,`enabled`) values (5,'0:0:0:0:0:0:0:1','test','e6d15688d78a826e63e7513caa526e13','13203106992@163.com',NULL,'2022-11-03',NULL,'13203106992','测试用户','NNx/EEqKk72b+nD16EPxtA==',1),(6,'0:0:0:0:0:0:0:1','meowsauce','d310e5a4f55d308bc6eb7afc4869b0a3','13203106992@163com',NULL,'2022-11-18',NULL,'13203106992','系统管理员','L48wx0/AHruLiKxVbwKuEQ==',1),(7,'0:0:0:0:0:0:0:1','meoweditor','a7938fc098480f40ebf545e1bcdd82ae','123123@qq.com',NULL,'2022-11-19',NULL,'123123123','内容管理员','Ohj/fA02avIAsqIZQnDRLQ==',1);

/*Table structure for table `visitor` */

CREATE TABLE `visitor` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `nick_name` varchar(255) DEFAULT NULL,
  `cover` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

/*Data for the table `visitor` */

insert  into `visitor`(`id`,`nick_name`,`cover`,`email`,`website`) values (1,'扣脚丫的喵酱','https://q1.qlogo.cn/g?b=qq&nk=3232054541&s=100','13203106992@163.com','com.meowsauce'),(2,'逛花楼的喵酱','https://q1.qlogo.cn/g?b=qq&nk=3125017548&s=100','15574960969@163.com','com.baidu.com'),(20,'扣脚丫的喵酱','https://q1.qlogo.cn/g?b=qq&nk=3232054541&s=100','3232054541@qq.com',''),(21,'。','https://q1.qlogo.cn/g?b=qq&nk=3125017548&s=100','3125017548@qq.com',''),(22,'扣脚丫的喵酱','https://q1.qlogo.cn/g?b=qq&nk=3232054541&s=100','3232054541@qq.com',''),(23,'。','https://q1.qlogo.cn/g?b=qq&nk=3125017548&s=100','3125017548@qq.com',''),(24,'扣脚丫的喵酱','https://q1.qlogo.cn/g?b=qq&nk=3232054541&s=100','3232054541@qq.com',''),(25,'扣脚丫的喵酱','https://q1.qlogo.cn/g?b=qq&nk=3232054541&s=100','3232054541@qq.com',''),(26,'喵酱','https://q1.qlogo.cn/g?b=qq&nk=2904455860&s=100','2904455860@qq.com',''),(32,'扣脚丫的喵酱','https://q1.qlogo.cn/g?b=qq&nk=3232054541&s=100','3232054541@qq.com',''),(33,'扣脚丫的喵酱','https://q1.qlogo.cn/g?b=qq&nk=3232054541&s=100','3232054541@qq.com',''),(34,'扣脚丫的喵酱','https://q1.qlogo.cn/g?b=qq&nk=3232054541&s=100','3232054541@qq.com',''),(35,'昔日','https://q1.qlogo.cn/g?b=qq&nk=576175265&s=100','576175265@qq.com','');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
