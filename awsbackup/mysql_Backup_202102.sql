-- MySQL dump 10.17  Distrib 10.3.25-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: common
-- ------------------------------------------------------
-- Server version	10.3.25-MariaDB-0ubuntu0.20.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `cid` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `create_date` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`cid`),
  KEY `pid` (`pid`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `post` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (3,3,'pureheart8@naver.com','수정 중..','2021-02-01 13:47:35'),(11,3,'pureheart8@naver.com','','2021-02-01 15:23:37'),(12,3,'pureheart8@naver.com','fdsf','2021-02-01 15:24:03'),(13,17,'b@b.com',NULL,'2021-02-01 15:29:21'),(14,17,'b@b.com',NULL,'2021-02-01 15:30:13'),(15,17,'b@b.com','다시다시','2021-02-01 15:30:52'),(16,17,'b@b.com','댓글댓글댓글','2021-02-01 15:55:37'),(17,17,'b@b.com','댓글 테스트','2021-02-01 15:56:27'),(18,17,'b@b.com','댓글 테스트트','2021-02-01 15:56:55'),(19,17,'b@b.com','댓글을 다섯자','2021-02-01 15:57:58'),(32,22,'b@b.com','딧글댓글댓','2021-02-03 20:23:32'),(33,22,'b@b.com','딧글댓글댓','2021-02-03 20:23:34'),(34,22,'b@b.com','딧글댓글댓','2021-02-03 20:23:36'),(37,23,'b@b.com','수정해보자','2021-02-04 12:24:53'),(39,23,'b@b.com','안뇽안뇽안','2021-02-04 13:31:23'),(42,29,'b@b.com','댓글 테스트','2021-02-04 13:55:06'),(44,29,'','23555','2021-02-04 14:50:30'),(45,24,'b@b.com','안뇽아뇽','2021-02-04 15:24:20'),(46,23,'b@b.com','???','2021-02-04 17:28:56'),(53,38,'ssafy@ssafy.com','감사합니다!!','2021-02-05 12:22:51'),(54,49,'opererer@naver.com','좋은 글 보고 갑니다^^*','2021-02-05 13:01:25'),(55,50,'opererer@naver.com','좋아요^^&','2021-02-05 13:05:03'),(56,51,'opererer@naver.com','멋있어요^^v','2021-02-05 13:11:33'),(57,31,'midkyle1216@gmail.com','ㄴㄴㅇㄴㅇ','2021-02-05 13:17:13'),(58,51,'midkyle1216@gmail.com','어 형 오늘은 진짜 풍성해보여!!!','2021-02-05 13:17:29'),(59,49,'midkyle1216@gmail.com','내가 더 빛남;','2021-02-05 13:17:42'),(60,53,'midkyle1216@gmail.com','기현 힘좀 내','2021-02-05 13:27:34');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `member` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `create_date` datetime DEFAULT current_timestamp(),
  `introduce` varchar(200) DEFAULT NULL,
  `profileimage` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `user_idx_unique_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (3,'a@a.com','','a',NULL,NULL,NULL),(4,'fghgfhfghgfh','gfhgfhfghfgh','ghgfhfgh',NULL,NULL,NULL),(5,'string','string','string',NULL,NULL,NULL),(6,'opertdfdd15fddf4334','dsffdddfdsda','ffddffdhi',NULL,NULL,NULL),(8,'opertdfdd15fdddddf4334','','ffddffdhi',NULL,NULL,NULL),(9,'aa@aa.com','asdf1234','woori',NULL,NULL,NULL),(11,'woori@gmail.com','asdf1234','woori',NULL,NULL,NULL),(12,'d@d.com','asdf1234','d',NULL,NULL,NULL),(13,'f@f.com','asdf1234','f',NULL,NULL,NULL),(14,'dnfl531@naver.com','asdf1234','woori',NULL,NULL,NULL),(15,'g@g.com','asdf1234','g',NULL,NULL,NULL),(16,'h@h.com','asdf1234','h',NULL,NULL,NULL),(17,'n@n.com','asdf1234','n',NULL,NULL,NULL),(18,'tjdwjddnr296@naver.com','Earth527510!','성정욱',NULL,NULL,NULL),(19,'tjdwjddnr2968@gmail.com','Earth527510!','블루팡',NULL,NULL,NULL),(20,'migerere@naver.com','asdfasdf123','비비',NULL,NULL,NULL),(21,'jasmin445512@gmail.com','asdfasdf123','김싸피',NULL,NULL,NULL),(22,'akamflalfm@gmail.com','Earth527510!','경림짱',NULL,NULL,NULL),(26,'opert1564@naver.com2','a1a2s1s2','sdfsdf',NULL,NULL,NULL),(30,'jasmin445534@gmail.com','asdfasdf123','김싸피',NULL,NULL,NULL),(31,'opert1564@naver.com222','a1a2s1s2','test',NULL,NULL,NULL),(32,'t@t.tt','tttt','tttt',NULL,NULL,NULL),(33,'k@k.com','asdf1234','k',NULL,NULL,NULL),(34,'m@m.com','asdf1234','m',NULL,NULL,NULL),(35,'diffkfl1111@naver.com','e21ac044039452dbe1e686da1371a0321bf3b79ac032efccb31864242e90831a','woori',NULL,'하하하','3b0b2f27-5efb-44c5-ad45-978cdc3f139d_펭수.jpg'),(37,'q@q.qq','1f92e11896582a18eba3bb23906a0257db6e1753ad4c8b535931d96456bb5271','최동식','2021-01-25 07:29:17','null','119d4a62-c8ff-4ab7-89ec-2957dcca8fc1_ap.jpg'),(38,'opererer@naver.com','','최동식','2021-01-25 08:19:40','dsfsdfsd',NULL),(39,'f93256f53f00091a779445ffff8d9e02f77fc77b46461d7f53ec11da10488404','최동식','opert1564@naver.com','2021-01-25 19:00:30','sdasddddggdddddddddd',NULL),(40,'pureheart8@naver.com','',' 정욱성','2021-01-26 12:33:36','null','73613ed9-049d-4e50-b4f6-4e32cee84e16_딸1.PNG'),(41,'n@nn.nnnn','312433c28349f63c4f387953ff337046e794bea0f9b9ebfcb08e90046ded9c76','n','2021-01-26 14:08:30',NULL,NULL),(42,'k@k.kkk','','k','2021-01-26 14:09:02','aa','f351a381-7d18-4323-86a0-cadaddf44558_캡처.PNG'),(44,'o_racle@naver.com','a1a2s1s2','최동식','2021-01-27 03:44:46','dgdgdg','4aa293cf-2acf-4954-930d-deb59fbd65ec_co.jpg'),(45,'e@e.com','','e','2021-01-27 11:22:24',NULL,NULL),(46,'j@j.com','','j','2021-01-27 11:30:49',NULL,NULL),(47,'n@n.nnn','','n','2021-01-27 13:37:49','asdf',NULL),(48,'o@o.com','asdf1234','o','2021-01-27 14:37:24',NULL,NULL),(49,'y@y.com','asdf1234','y','2021-01-27 14:47:42',NULL,NULL),(50,'x@x.com','asdf1234','x','2021-01-27 14:56:55',NULL,NULL),(51,'r@r.com','','r','2021-01-28 10:35:50','안녕안녕','ac563be1-7bb9-46a9-9623-588a06631989_펭수.jpg'),(52,'opert1564@naver.com','','최동식','2021-01-28 17:06:00','sdfdsfdsf','6e5817d7-68d9-4ac8-a44d-422d2f72137a_co.jpg'),(53,'s@s.com','','s','2021-01-28 17:44:44','','824b5559-608c-4fc4-813f-b04174829eba_펭수.jpg'),(54,'i@i.com','','i','2021-01-29 11:14:59',NULL,NULL),(55,'test@test.com','','test','2021-01-29 11:15:10',NULL,NULL),(56,'dfdfd@sdsd.com','wqer1234','dfdf','2021-01-29 11:16:33',NULL,NULL),(57,'test@t.com','a1a2s1s2','test','2021-01-29 11:18:36',NULL,NULL),(58,'eee@e.com','asdf1234','eee','2021-01-29 12:14:06',NULL,NULL),(59,'dsfdsf@dsfdsf.com','','dsfdsf@dsfdsf.com','2021-01-29 12:31:14',NULL,NULL),(60,'ttest@naver.com','','최동식','2021-01-29 13:10:33','erefdfsadsd','http://i4b103.p.ssafy.io/profileimg/7e983410-23aa-4c57-97f0-39e25f0a9171_co.jpg'),(61,'ssafy@ssafy.com','','ssafy','2021-01-29 13:13:57','안녕 나는 마스터','http://i4b103.p.ssafy.io/profileimg/f71e9d0c-d738-4ba5-abe5-cd7213db9f27_1234.jpg'),(62,'t@t.com','','t','2021-01-29 16:21:43',NULL,NULL),(63,'testtt','991f50165ea9a626e86ae12845939415f154001ce4154f0b60aca47f5230c12','a1a2s1s2','2021-01-30 02:10:34',NULL,NULL),(64,'b@b.com','','b','2021-02-01 10:02:52',NULL,'http://i4b103.p.ssafy.io/profileimg/232dc95b-211a-4f93-a37d-07b3100574c8_펭수.jpg'),(65,'111111@111111.com','','1111','2021-02-04 13:16:18',NULL,NULL),(66,'222@222.com','','222@222.com','2021-02-04 13:21:38',NULL,NULL),(67,'yys409@naver.com','','유석균','2021-02-04 18:55:14',NULL,NULL),(68,'v@v.com','','v','2021-02-04 19:54:03',NULL,'http://i4b103.p.ssafy.io/profileimg/d1bb4a49-d60d-4eb0-80e4-68325c84354b_다운로드.jfif'),(69,'midkyle1216@gmail.com','739ec448edaf7f4d91eb443462ed320074862935d2c799b76c6ed42ec6edd78c','유석균','2021-02-04 23:30:06','null','http://i4b103.p.ssafy.io/profileimg/9bf4c652-7c43-4f0b-b2d4-49df732eb1a7_minions.jpg'),(70,'ww@ww.com','','ww','2021-02-05 11:13:32',NULL,NULL),(71,'nn@nn.com','312433c28349f63c4f387953ff337046e794bea0f9b9ebfcb08e90046ded9c76','nn','2021-02-05 11:27:53',NULL,'http://i4b103.p.ssafy.io/profileimg/26ef838e-5d64-4530-a107-0adc09c5620b_펭수.jpg'),(72,'bb@bb.com','312433c28349f63c4f387953ff337046e794bea0f9b9ebfcb08e90046ded9c76','bb@bb.com','2021-02-05 13:56:49',NULL,'http://i4b103.p.ssafy.io/profileimg/61d6a386-d41b-40df-82a4-5cccb377ffe9_다운로드.jfif');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post` (
  `pid` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `content` varchar(5000) DEFAULT NULL,
  `likes` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT current_timestamp(),
  `postimage` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`pid`),
  KEY `email` (`email`),
  CONSTRAINT `post_ibfk_1` FOREIGN KEY (`email`) REFERENCES `member` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (3,'안녕하십니까','pureheart8@naver.com','',4,NULL,NULL),(8,'테스트','r@r.com','# 임시 에디터 \n### 임시 에디터\n\n테스트',0,'2021-01-28 10:41:00',NULL),(10,'테스트글','s@s.com','테슽트텟트\n글머리 앞에 # 어떻게 처리할까 생각중',0,'2021-01-28 18:05:13',NULL),(13,'수정 완료','i@i.com','글 수정 잘 작동하나요',0,'2021-01-29 12:01:00',NULL),(15,'무한 스크롤 테스트용 ','s@s.com','# 임시 에디터 \n### 임시 에디터\n무한 스크롤 테스트용 ',2,'2021-01-29 23:26:15',NULL),(16,'무한 스크롤 테스트용 ','s@s.com','# 임시 에디터 \n### 임시 에디터\n무한 스크롤 테스트용 ',0,'2021-01-29 23:26:21',NULL),(17,'무한 스크롤 테스트용 ','s@s.com','# 임시 에디터 \n### 임시 에디터\n무한 스크롤 테스트용 ',0,'2021-01-29 23:26:27',NULL),(18,'무한 스크롤 테스트용 ','s@s.com','# 임시 에디터 \n### 임시 에디터\n무한 스크롤 테스트용 ',0,'2021-01-29 23:27:05',NULL),(19,'무한 스크롤 테스트용 ','s@s.com','# 임시 에디터 \n### 임시 에디터\n무한 스크롤 테스트용 ',1,'2021-01-29 23:27:11',NULL),(20,'무한 스크롤 테스트용 ','s@s.com','# 임시 에디터 \n### 임시 에디터\n무한 스크롤 테스트용 ',1,'2021-01-29 23:27:16',NULL),(21,'fsd','opererer@naver.com','# 임시 에디터 \n### 임시 에디터gh\n![Description](http://)',1,'2021-01-31 19:03:35',NULL),(22,'임시 test','opererer@naver.com','# 임시 에디터 \n### 임시 에디터\n![](https://images.velog.io/images/pertus123/post/cd41ea84-7fe2-406e-a662-3fc16b1280ed/co.jpg)\ntest\n![](https://images.velog.io/images/pertus123/post/699f0f4c-c4e6-4f94-aee5-d0285fd9639a/ap.jpg)\ntest2',0,'2021-01-31 19:05:33',NULL),(23,'sdfsdfsd','opererer@naver.com','# 임시 에디터 \n### 임시 에디터\n![](https://images.velog.io/images/pertus123/post/7be5bc90-2b1a-4426-be93-3f5f008f07de/ap.jpg)\n![](https://images.velog.io/images/pertus123/post/7be5bc90-2b1a-4426-be93-3f5f008f07de/ap.jpg)',1,'2021-01-31 21:15:59',NULL),(24,'string','string','string',1,'2021-02-02 12:37:17',NULL),(29,'123213','111111@111111.com','# 임시 에디터 \n### 임시 에디터\n122132',1,'2021-02-04 13:16:33',NULL),(30,'','222@222.com','# 임시 에디터 \n### 임시 에디터qwwqeq',4,'2021-02-04 13:33:12',NULL),(31,'','yys409@naver.com','# 임시 에디터 \n### 임시 에디터\n\nfdfdf',3,'2021-02-04 18:55:53',NULL),(32,'string','string','string',1,'2021-02-04 21:39:32',NULL),(34,'테스트','v@v.com','# 임시 에디터 \n### 임시 에디터',0,'2021-02-04 21:45:58',NULL),(35,'테스트','v@v.com','# 임시 에디터 \n### 임시 에디터',3,'2021-02-04 21:46:14',NULL),(36,'테스트','v@v.com','# 임시 에디터 \n### 임시 에디터',3,'2021-02-04 21:46:30',NULL),(37,'테스트','v@v.com','# 임시 에디터 \n### 임시 에디터',2,'2021-02-04 21:47:52',NULL),(38,'마크다운 사용법','v@v.com','# 1. 마크다운에 관하여\n## 1.1. 마크다운이란?\n[**Markdown**](http://whatismarkdown.com/)은 텍스트 기반의 마크업언어로 2004년 존그루버에 의해 만들어졌으며 쉽게 쓰고 읽을 수 있으며 HTML로 변환이 가능하다. 특수기호와 문자를 이용한 매우 간단한 구조의 문법을 사용하여 웹에서도 보다 빠르게 컨텐츠를 작성하고 보다 직관적으로 인식할 수 있다.\n마크다운이 최근 각광받기 시작한 이유는 깃헙([https://github.com](https://github.com)) 덕분이다. 깃헙의 저장소Repository에 관한 정보를 기록하는 README.md는 깃헙을 사용하는 사람이라면 누구나 가장 먼저 접하게 되는 마크다운 문서였다. 마크다운을 통해서 설치방법, 소스코드 설명, 이슈 등을 간단하게 기록하고 가독성을 높일 수 있다는 강점이 부각되면서 점점 여러 곳으로 퍼져가게 된다.\n\n## 1.2. 마크다운의 장-단점\n### 1.2.1. 장점\n	1. 간결하다.\n	2. 별도의 도구없이 작성가능하다.\n	3. 다양한 형태로 변환이 가능하다.\n	3. 텍스트(Text)로 저장되기 때문에 용량이 적어 보관이 용이하다.\n	4. 텍스트파일이기 때문에 버전관리시스템을 이용하여 변경이력을 관리할 수 있다.\n	5. 지원하는 프로그램과 플랫폼이 다양하다.\n### 1.2.2. 단점\n	1. 표준이 없다.\n	2. 표준이 없기 때문에 도구에 따라서 변환방식이나 생성물이 다르다.\n	3. 모든 HTML 마크업을 대신하지 못한다.\n\n****\n# 2. 마크다운 사용법(문법)\n## 2.1. 헤더Headers\n* 큰제목: 문서 제목\n    ```\n    This is an H1\n    =============\n    ```\n    This is an H1\n    =============\n\n* 작은제목: 문서 부제목\n    ```\n    This is an H2\n    -------------\n    ```\n    This is an H2\n    -------------\n',5,'2021-02-04 21:50:58',NULL),(44,'아 좀 ','ww@ww.com','# 임시 에디터 \n### 임시 에디터',3,'2021-02-05 11:22:08','http://i4b103.p.ssafy.io/thumbimg/fd6ca055-7159-4719-9041-a75b4e019b8f_펭수.jpg'),(46,'펭수','nn@nn.com','# 임시 에디터 \n### 임시 에디터![desc](http://i4b103.p.ssafy.io/postimg/c2c00987-8ff7-4ab5-ba88-dbcb33c01fb3_펭수.jpg){{{width=\"auto\" height=\"auto\"}}}',1,'2021-02-05 11:36:57','http://i4b103.p.ssafy.io/thumbimg/16278e05-a256-4f73-8de9-aaad0d5afc88_펭수.jpg'),(49,'빛나는종성','midkyle1216@gmail.com','# 나는 매일 빛난다',3,'2021-02-05 12:51:56','http://i4b103.p.ssafy.io/thumbimg/8bfdbf3d-c5d2-4cfe-a634-2a0d4cca85f1_noonsmile2.PNG'),(50,'Lorem Ipsum is simply dummy text','midkyle1216@gmail.com','__Advertisement :)__\n\n- __[pica](https://nodeca.github.io/pica/demo/)__ - high quality and fast image\n  resize in browser.\n- __[babelfish](https://github.com/nodeca/babelfish/)__ - developer friendly\n  i18n with plurals support and easy syntax.\n\nYou will like those projects!\n\n---\n\n# h1 Heading 8-)\n## h2 Heading\n### h3 Heading\n#### h4 Heading\n##### h5 Heading\n###### h6 Heading\n\n\n## Horizontal Rules\n\n___\n\n---\n\n***',1,'2021-02-05 12:52:41',NULL),(51,'얘들아 오늘은 덜 빛나지?','midkyle1216@gmail.com','# 오늘은 신경좀 썼어',3,'2021-02-05 13:00:52','http://i4b103.p.ssafy.io/thumbimg/9f6ed9b6-80ab-4287-9200-caa8966a8cef_쌍우형.png'),(52,'미니언즈','midkyle1216@gmail.com','# 미니언즈',0,'2021-02-05 13:14:44','http://i4b103.p.ssafy.io/thumbimg/e2c822ac-82e6-46db-a5c1-edf05b98899c_minions.jpg'),(53,'이건 또 왜 안되는거야','midkyle1216@gmail.com','# 이건 또 왜 안되는거야',1,'2021-02-05 13:27:17','http://i4b103.p.ssafy.io/thumbimg/76ff133f-693a-4641-a0fe-c47e785b4fcc_123unknown.png'),(54,'테스트글','midkyle1216@gmail.com','# 임시 에디터 \n### 임시 에디터![desc](http://i4b103.p.ssafy.io/postimg/e36a9ebc-01b3-468b-9513-0b7a1385e718_test.jpg){{{width=\"auto\" height=\"auto\"}}}',2,'2021-02-05 13:47:31','http://i4b103.p.ssafy.io/thumbimg/2f81896e-fd25-4ce3-aeb3-ad7f79d8ab7b_test.jpg'),(55,'사진 수정 테스트','nn@nn.com','# 임시 에디터 \n### 임시 에디터\n![desc](http://i4b103.p.ssafy.io/postimg/709cb109-7e1b-476d-8232-f6894edbbbc2_다운로드.jfif){{{width=\"auto\" height=\"auto\"}}}',0,'2021-02-05 14:22:58','http://i4b103.p.ssafy.io/thumbimg/8ace6ebc-25b3-4212-9c4b-37ab164e185b_다운로드.jfif'),(56,'한번 보시죠','ssafy@ssafy.com','# 프로필대로 사진이 나오는지 확인중입니다.\n',0,'2021-02-05 15:35:44','http://i4b103.p.ssafy.io/thumbimg/5e30af16-98ce-4dc5-965b-7965bd6ecaf1_1234.jpg');
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_like`
--

DROP TABLE IF EXISTS `post_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post_like` (
  `lid` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) NOT NULL,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY (`lid`),
  KEY `pid` (`pid`),
  KEY `uid` (`uid`),
  CONSTRAINT `post_like_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `post` (`pid`),
  CONSTRAINT `post_like_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `member` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_like`
--

LOCK TABLES `post_like` WRITE;
/*!40000 ALTER TABLE `post_like` DISABLE KEYS */;
INSERT INTO `post_like` VALUES (5,8,8),(6,3,4),(8,30,64),(10,8,3),(40,15,64),(41,19,64),(43,21,64),(45,29,64),(46,24,64),(47,23,64),(48,3,64),(49,20,64),(51,31,68),(58,15,60),(59,35,60),(67,36,68),(70,35,68),(75,38,60),(76,30,68),(78,36,60),(79,3,68),(81,37,68),(82,32,60),(83,30,60),(84,38,61),(85,35,61),(86,37,61),(87,36,61),(89,30,61),(90,38,68),(91,31,60),(92,44,70),(93,3,70),(94,38,70),(95,31,61),(97,44,61),(100,46,71),(101,44,38),(102,38,71),(104,49,69),(105,49,38),(106,51,69),(107,50,61),(109,51,61),(110,49,61),(112,50,38),(114,51,38),(115,54,69),(116,53,69),(117,50,71),(119,54,71);
/*!40000 ALTER TABLE `post_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_tag`
--

DROP TABLE IF EXISTS `post_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post_tag` (
  `ptid` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) NOT NULL,
  `tid` bigint(20) DEFAULT NULL,
  `uid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ptid`),
  KEY `pid` (`pid`),
  KEY `tag_id` (`tid`),
  KEY `uid` (`uid`),
  CONSTRAINT `post_tag_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `post` (`pid`),
  CONSTRAINT `post_tag_ibfk_2` FOREIGN KEY (`tid`) REFERENCES `tag` (`tid`),
  CONSTRAINT `post_tag_ibfk_3` FOREIGN KEY (`uid`) REFERENCES `member` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_tag`
--

LOCK TABLES `post_tag` WRITE;
/*!40000 ALTER TABLE `post_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `post_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag` (
  `tid` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `temppost`
--

DROP TABLE IF EXISTS `temppost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `temppost` (
  `tid` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `content` varchar(5000) DEFAULT NULL,
  `create_date` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`tid`),
  KEY `email` (`email`),
  CONSTRAINT `temppost_ibfk_1` FOREIGN KEY (`email`) REFERENCES `member` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `temppost`
--

LOCK TABLES `temppost` WRITE;
/*!40000 ALTER TABLE `temppost` DISABLE KEYS */;
/*!40000 ALTER TABLE `temppost` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-05  7:08:59
