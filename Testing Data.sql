DROP TABLE BORROW_RECORD;
DROP TABLE RESERVE;
DROP TABLE COLLECTION;
DROP TABLE PATRON;
DROP TABLE STAFF;
DROP Table BOOK_INFORMATION;




CREATE TABLE STAFF (STAFF_ID CHAR(9) NOT NULL, PASSWORD CHAR(15)NOT NULL,STAFF_NAME CHAR(20)NOT NULL, POSITION CHAR(15)NOT NULL, PHONE CHAR(8) NOT NULL, E_MAIL CHAR(30) NOT NULL);

INSERT INTO STAFF
VALUES('82647821s', '1657AMA1657', 'LILY_CATHRINE', 'MANAGER', '31729270','DUZILONG@163.COM');
INSERT INTO STAFF
VALUES('38741897s', 'OPG842175833', 'ALICE_CABBAGE', 'LIBRARIAN', '56276470','DDECHR798@163.COM'); 



INSERT INTO STAFF
VALUES('64273878s', 'ILOVE520', 'PETER_S_HARRY', 'BOSS', '12647216','KAIYUANN@163.COM');
INSERT INTO STAFF
VALUES('21789256s', 'M1234567M', 'LUCAS_BUSH', 'PROGRAMMER', '76412458','WEINANMING88@163.COM');
INSERT INTO STAFF
VALUES('72164737s', 'ABC817761', 'PETER_JESON', 'BOSS', '12647216','GGJJTING29@163.COM');
INSERT INTO STAFF
VALUES('81743988s', '17632546ST', 'KIM_ROBBINS', 'LIBRARIAN', '82147574','KONGDONG1988@163.COM');
INSERT INTO STAFF
VALUES('27164767s', 'RES123456', 'JIM_BIDEN', 'VICE_BOSS', '23164567','BANGKOKMAN@163.COM');
INSERT INTO STAFF
VALUES('12874662s', 'BBC542290', 'MARY_BEAUTY', 'PROGRAMMER', '37674882','KULIIISIKI@163.COM');



CREATE TABLE BOOK_INFORMATION (ISBN CHAR(13) NOT NULL, BOOK_NAME CHAR(30) NOT NULL, CATEGORY CHAR(20), AUTHOR_NAME CHAR(30), PUBLISHER CHAR(30)  ,LANGUAGE CHAR(15), PUBLISH_TIME CHAR(10), DESCRIPTION CHAR(50),PRIMARY KEY(ISBN));

--Ôö¼ÓÏàÍ¬ÊéÃû£¬²»Í¬ISBNÇé¿ö£¨µ«¶¼ÓÐcopy£¬Í¬ÃûÈý±¾²»Í¬µÄÊé£©£¬
--ÔÙÔö¼ÓÐÂÊéµÄÐÅÏ¢£¬µ«Ã»ÓÐ¶ÔÓ¦µÄcollection

INSERT INTO BOOK_INFORMATION
VALUES('9787810441063', 'HARRY_POTTER_7', 'TEENAGER_FANTASY', 'J_K_ROLIN','U_K_PUBLISHER','ENGLISH', '1999-09-09','WORTH_READING');
INSERT INTO BOOK_INFORMATION
VALUES('2475947892847', 'HARRY_POTTER_7', 'TEENAGER_FANTASY', 'J_K_ROLIN','U_K_PUBLISHER','ENGLISH', '1999-09-09','WORTH_READING');
INSERT INTO BOOK_INFORMATION
VALUES('8573692876859', 'HARRY_POTTER_7', 'TEENAGER_FANTASY', 'J_K_ROLIN','U_K_PUBLISHER','ENGLISH', '1999-09-09','WORTH_READING');
INSERT INTO BOOK_INFORMATION
VALUES('4298748728748', 'HARRY_POTTER_7', 'TEENAGER_FANTASY', 'J_K_ROLIN','U_K_PUBLISHER','ENGLISH', '1999-09-09','WORTH_READING');

INSERT INTO BOOK_INFORMATION
VALUES('57387T5782577', 'STAR_WARS', 'SCIENCE_FICTION', 'BENJAMIN_LUCAS', 'USA_CENTRAL_PUBLISHER','SPANISH','1997-08-07','BORING');
INSERT INTO BOOK_INFORMATION
VALUES('7231648727464', 'STAR_WARS', 'SCIENCE_FICTION', 'BENJAMIN_LUCAS', 'USA_CENTRAL_PUBLISHER','SPANISH','1997-08-07','BORING');
INSERT INTO BOOK_INFORMATION
VALUES('8723657843675', 'STAR_WARS', 'SCIENCE_FICTION', 'BENJAMIN_LUCAS', 'USA_CENTRAL_PUBLISHER','SPANISH','1997-08-07','BORING');
INSERT INTO BOOK_INFORMATION
VALUES('4641233494645', 'STAR_WARS', 'SCIENCE_FICTION', 'BENJAMIN_LUCAS', 'USA_CENTRAL_PUBLISHER','SPANISH','1997-08-07','BORING');





INSERT INTO BOOK_INFORMATION
VALUES('7218475981205', 'SAN_LUCIA', 'HORROR', 'LUCAS_BUSH_WHITE', 'FRANCE_PUBLISHER','FRENCH','2001-06-23','VERY_RECOMMEND_TO_READ');
INSERT INTO BOOK_INFORMATION
VALUES('8296587296274', 'NARNIA_LEGEND_2', 'JOURNAL_FANTASY', 'ALICE_MENLOW','LUXENBERG_PUBLISHER','CANADIAN', '1950-11-19','HAVE_MANY_INTERESTING_SENCES');
INSERT INTO BOOK_INFORMATION
VALUES('0764397506748', 'INFORMAL', 'STORY', 'BUSH_JOHN', 'SALT_LAKE_PUBLISHER','SPANISH','1877-12-28','CONFUSING');
INSERT INTO BOOK_INFORMATION
VALUES('9365827626587', 'BIG_CUCUMEMBER', 'HORROR', 'LILY_BLACK', 'AYSTRILIA_QUEEN_PUBLISHER','POLYNISIAN','2000-11-21','A_WASTE_OF_TIME');
INSERT INTO BOOK_INFORMATION
VALUES('0983246598726', 'DISAPPEARING_TOMATO', 'NON_FICTION', 'P_Q_PERCY','JAPAN_PUBLISHER','JAPANESE', '1990-02-25','QUITE_MEANINGFUL');
INSERT INTO BOOK_INFORMATION
VALUES('3698167615698', 'JEEP_IN_DESERT', 'COURAGE_STORY', 'JOE_CATON', 'RUSSIA_MOSCOW_PUBLISHER','RUSSIAN','2006-04-22','NOTHING_EDUCATIONAL');


CREATE TABLE PATRON(PATRON_ID CHAR(9) NOT NULL ,USER_PASSWORD CHAR(15) NOT NULL,PATRON_NAME CHAR(30) NOT NULL ,AGE CHAR(3)NOT NULL,ACTIVITY_STATE NUMBER(1), E_MAIL CHAR(30) NOT NULL,GENDER CHAR(6)NOT NULL,ADDRESS CHAR(50),ILLEGAL_TYPE CHAR(30),PHONE CHAR(8) NOT NULL,PRIMARY KEY(PATRON_ID));
--first person
INSERT INTO PATRON
VALUES('64273878p', '8216YK7501', 'PAT_WILL', '23', '1','ZHOUZHOU343@163.COM','MALE','GARDEN_ROAD_14TH',NULL,'71645899');
--second person
INSERT INTO PATRON
VALUES('21789256p', 'TT742187', 'COOK_BUSH', '9', '1','COOKFOREVER@163.COM','MALE','MERDER_STREET_92RD',NULL,'51276812');

--ÔÙÕâÒ»¶ÑÓÃ»§ÀïÉèÖÃÁ½¸ö×´Ì¬ÎªinavtivatedeµÄÓÃ»§¡£


INSERT INTO PATRON
VALUES('72647867p', 'KK19770202', 'BEN_JOHN', '78', '0','BIGBEN@163.COM','MALE','PRINCE_ROAD_33RD',NULL,'73678291');
INSERT INTO PATRON
VALUES('83748773p', 'TV2505050', 'LILY_WHITE', '93', '0','LILYBEAUTY@163.COM','FEMALE','HANBERGER_STREET_17TH',NULL,'47920184');

INSERT INTO PATRON
VALUES('82647821p', 'MM7671885', 'MAX_BLACK', '102', '1','MAXBCD777@163.COM','MALE','MARY_ROAD_87TH',NULL,'82163410');
INSERT INTO PATRON
VALUES('90278775p', 'DONGS99887', 'JOHN_SMITH', '44', '1','JSJS@163.COM','MALE','GOODMAN_ROAD_25TH',NULL,'84217593');
INSERT INTO PATRON
VALUES('71264897p', '12345999JJ', 'EMMA_JOE', '46', '1','EMMAGOOD11@163.COM','FEMALE','PHILIPHIES_STREET_88TH',NULL,'82417983');
INSERT INTO PATRON
VALUES('82762993p', '87654321SS', 'CATHRINE_MOMO', '119', '1','DUHONGHUA@163.COM','FEMALE','KING_ROAD_45TH',NULL,'09428384');
INSERT INTO PATRON
VALUES('65488722p', 'LOG123456', 'MERGE_HANS', '98', '1','SIHAFG656@163.COM','MALE','PKU_ROAD_98TH',NULL,'48297193');
INSERT INTO PATRON
VALUES('27482165p', 'PPT123490', 'AUTHOR_BARBECUE', '27', '1','BSGFIYUYI@163.COM','MALE','FORG_STREET_33RD',NULL,'73647821');



CREATE TABLE COLLECTION (BOOK_ID CHAR(15) NOT NULL, ISBN CHAR(13) NOT NULL ,STATE NUMBER(1), LOCATION CHAR(30) ,PRIMARY KEY(BOOK_ID),FOREIGN KEY(ISBN)REFERENCES BOOK_INFORMATION(ISBN));
--1, xiang tong ISBN 5 ben copy
INSERT INTO COLLECTION
VALUES('716481174119742','57387T5782577', '0', 'POLYULIBRARY_2ND_FLOOR');
INSERT INTO COLLECTION
VALUES('673647739963676','57387T5782577', '1', 'CITYULIBRARY_12ND_FLOOR');
INSERT INTO COLLECTION
VALUES('273487236476219','57387T5782577', '1', 'KANGTERROOM_15TH_FLOOR');
INSERT INTO COLLECTION
VALUES('374628376479283','57387T5782577', '1', 'PKULIBRARY_13RD_FLOOR');
INSERT INTO COLLECTION
VALUES('421756784675923','57387T5782577', '1', 'LONGLIBRARY_BASEMENT');




INSERT INTO COLLECTION
VALUES('817491084782184','9787810441063', '1', 'HKULIBRARY_1ST_FLOOR');
INSERT INTO COLLECTION
VALUES('127346723876762','9787810441063', '0', 'TSINGHUALIBRARY_28TH_FLOOR');
INSERT INTO COLLECTION
VALUES('317643546137654','9787810441063', '1', 'GOODLIBRARY_BASEMENT');
INSERT INTO COLLECTION
VALUES('237468723623678','9787810441063', '0', 'NICELIBRARY_7TH_FLOOR');
INSERT INTO COLLECTION
VALUES('736738123764782','9787810441063', '1', 'EASTLIBRARY_12ND_FLOOR');
--collection 2: 3ben xiang tong


INSERT INTO COLLECTION
VALUES('082758275764655', '7218475981205','0', 'CENTRAL_LIBRARY_4TH_FLOOR');
INSERT INTO COLLECTION
VALUES('857682938576859', '7218475981205','0', 'CINAMA_LIBRARY_5TH_FLOOR');
INSERT INTO COLLECTION
VALUES('238974839274838', '7218475981205','0', 'KANTEEN_LIBRARY_9TH_FLOOR');
INSERT INTO COLLECTION
VALUES('476728476892746', '7218475981205','1', 'CENTRAL_LIBRARY_13TH_FLOOR');
INSERT INTO COLLECTION
VALUES('716473876473876', '7218475981205','1', 'CENTRAL_LIBRARY_11ST_FLOOR');



INSERT INTO COLLECTION
VALUES('872762679275667','8296587296274', '0', 'NEW_FINLAND_CENTRAL_LIBRARY');
INSERT INTO COLLECTION
VALUES('423976982002757','0764397506748', '0', 'BEIJING_LIBRARY_18TH_FLOOR');
INSERT INTO COLLECTION
VALUES('343976927667759','9365827626587' ,'0', 'OPERA_LIBRARY_15TH_FLOOR');
INSERT INTO COLLECTION
VALUES('839476930803878','0983246598726', '0', 'CARTOON_LIBRARY_7TH_FLOOR');
INSERT INTO COLLECTION
VALUES('278676473829763','3698167615698' ,'0', 'MIAMI_LIBRARY_3RD_FLOOR');
INSERT INTO COLLECTION
VALUES('716381174119742','57387T5782577', '1', 'HSBC_LIBRARY_1ST_FLOOR');
INSERT INTO COLLECTION
VALUES('843175265783746','57387T5782577', '1', 'MYTH_LIBRARY_12ND_FLOOR');
INSERT INTO COLLECTION
VALUES('819427981847657','8296587296274', '1', 'TIBET_LIBRARY_BASEMENT');
INSERT INTO COLLECTION
VALUES('217465782194672','0764397506748', '1', 'SHANXI_BOOKROOM_7TH_FLOOR');
INSERT INTO COLLECTION
VALUES('198745423657898','9365827626587' ,'1', 'NEW_MEXICO_LIBRARY_5TH_FLOOR');
INSERT INTO COLLECTION
VALUES('781467813764732','3698167615698' ,'1', 'KANSARS_STATE_33RD_FLOOR');
INSERT INTO COLLECTION
VALUES('287492874898279','9365827626587' ,'0', 'NEW_YORK_LIBRARY_15TH_FLOOR');




CREATE TABLE RESERVE(BOOK_ID CHAR(15), ISBN CHAR(13)NOT NULL, PATRON_ID CHAR(9)NOT NULL, DEADLINE CHAR(10) NOT NULL, BOOK_STATE NUMBER(1),FOREIGN KEY(BOOK_ID) REFERENCES COLLECTION (BOOK_ID),FOREIGN KEY(ISBN) REFERENCES BOOK_INFORMATION(ISBN),FOREIGN KEY(PATRON_ID)REFERENCES PATRON(PATRON_ID));

-- 2, ISBN gaicheng butong


INSERT INTO RESERVE
VALUES('817491084782184','9787810441063' , '64273878p' , '2022-11-27', '1');--1b
INSERT INTO RESERVE
VALUES(null,'57387T5782577' , '64273878p' , '2022-12-21', '0');--0d

INSERT INTO RESERVE
VALUES('716481174119742', '57387T5782577', '21789256p', '2022-05-05', '1'); --1 : c--
INSERT INTO RESERVE
VALUES(null,'9787810441063', '21789256p', '2022-05-08', '0'); --0 :a
INSERT INTO RESERVE
VALUES(null,'2475947892847', '21789256p', '2022-12-08', '0');--e


INSERT INTO RESERVE
VALUES('082758275764655', '7218475981205', '82647821p', '2019-05-05', '1');--b
INSERT INTO RESERVE
VALUES('423976982002757','0764397506748', '71264897p', '2019-04-08', '1');--b
INSERT INTO RESERVE
VALUES('343976927667759', '9365827626587', '82762993p', '2019-06-05', '1');--b
INSERT INTO RESERVE
VALUES('839476930803878','0983246598726' , '82762993p' , '2020-10-19', '1');--b
INSERT INTO RESERVE
VALUES('278676473829763','3698167615698', '27482165p', '2018-02-01', '1');--d




CREATE TABLE BORROW_RECORD(RECORD_ID CHAR(10)NOT NULL,BOOK_ID CHAR(15)NOT NULL, PATRON_ID CHAR(9)NOT NULL, ISBN CHAR(13)NOT NULL,START_TIME CHAR(10)NOT NULL,END_TIME CHAR(10),PRIMARY KEY(RECORD_ID),FOREIGN KEY(BOOK_ID)REFERENCES COLLECTION (BOOK_ID),FOREIGN KEY(PATRON_ID)REFERENCES PATRON(PATRON_ID),FOREIGN KEY(ISBN)REFERENCES BOOK_INFORMATION(ISBN));
--3, (1)in progress ISBN bu tong. -- different people can be the same (2) dui ying copy inactivated
INSERT INTO BORROW_RECORD
VALUES('6718467810','716473876473876', '64273878p', '7218475981205','2019-09-22', NULL); --overdue in progress borrow
INSERT INTO BORROW_RECORD
VALUES('8241578940','127346723876762', '64273878p', '9787810441063','2022-09-22', null); -- normal in progress borrow
INSERT INTO BORROW_RECORD
VALUES('3485679380','421756784675923', '64273878p', '57387T5782577','2019-11-22', '2020-01-06');



INSERT INTO BORROW_RECORD
VALUES('7182461870','716481174119742', '21789256p', '57387T5782577','2022-09-11', null);
INSERT INTO BORROW_RECORD
VALUES('8192347810','237468723623678', '21789256p', '9787810441063', '2020-04-11', null);
INSERT INTO BORROW_RECORD
VALUES('7164871640','716473876473876', '21789256p', '7218475981205','2017-08-13', null);



INSERT INTO BORROW_RECORD
VALUES('6234787367','819427981847657', '82647821p', '8296587296274','2022-11-18', null);
INSERT INTO BORROW_RECORD
VALUES('1067487146','423976982002757', '82647821p', '0764397506748','2022-09-27', null);
INSERT INTO BORROW_RECORD
VALUES('7246762876','278676473829763', '65488722p', '3698167615698','2022-11-06', null);
INSERT INTO BORROW_RECORD
VALUES('7632487628','287492874898279', '65488722p', '9365827626587','2018-10-29', '2018-11-21');
INSERT INTO BORROW_RECORD
VALUES('8247598432','287492874898279', '65488722p', '9365827626587','2022-02-28', null);






INSERT INTO BORROW_RECORD
VALUES('7216438716','423976982002757', '71264897p', '0764397506748', '2019-02-28', '2019-04-22');
INSERT INTO BORROW_RECORD
VALUES('8937629657','343976927667759', '82762993p', '9365827626587', '2018-11-29', '2020-03-15');
INSERT INTO BORROW_RECORD
VALUES('4817496165','839476930803878', '82762993p', '0983246598726', '2020-05-24', '2020-09-16');
INSERT INTO BORROW_RECORD
VALUES('8275902508','278676473829763', '27482165p', '3698167615698', '2018-10-07', '2019-02-03');
INSERT INTO BORROW_RECORD
VALUES('7164871646','716481174119742', '21789256p', '57387T5782577','2020-08-13', '2020-08-28');
INSERT INTO BORROW_RECORD
VALUES('7214976574','843175265783746', '21789256p', '57387T5782577','2021-09-18', '2021-11-24');
INSERT INTO BORROW_RECORD
VALUES('7983641546','082758275764655', '82647821p', '7218475981205','2021-02-16', '2021-04-30');
INSERT INTO BORROW_RECORD
VALUES('8192048767','819427981847657', '65488722p', '8296587296274','2020-11-04', '2020-12-01');
INSERT INTO BORROW_RECORD
VALUES('8213579412','217465782194672', '71264897p', '0764397506748', '2018-04-07', '2018-11-03');
INSERT INTO BORROW_RECORD
VALUES('8743816477','198745423657898', '82762993p', '9365827626587', '2019-07-22', '2019-04-23');
INSERT INTO BORROW_RECORD
VALUES('8921643718','781467813764732', '27482165p', '3698167615698', '2019-08-19', '2021-03-25');
INSERT INTO BORROW_RECORD
VALUES('8364721864','198745423657898', '65488722p', '8296587296274', '2019-12-14', '2020-05-09');
INSERT INTO BORROW_RECORD
VALUES('6421784368','278676473829763', '27482165p', '3698167615698', '2019-02-28', '2020-04-22');
INSERT INTO BORROW_RECORD
VALUES('1284789724','423976982002757', '71264897p', '0764397506748', '2018-06-17', '2018-12-06');
INSERT INTO BORROW_RECORD
VALUES('8731642876','423976982002757', '71264897p', '0764397506748', '2019-08-18', '2019-09-27');

commit;