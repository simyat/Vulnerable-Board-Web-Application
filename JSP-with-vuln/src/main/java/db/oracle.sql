-- users
CREATE TABLE users (
  user_id VARCHAR2(50) NOT NULL,
  name VARCHAR2(100) NOT NULL,
  password VARCHAR2(150) not null,
  address VARCHAR2(150) not null,
  regidate date default sysdate not null,
  primary key(user_id)
);

-- drop sequence
drop sequence seq_users_id

-- board
create table board (
  id number not null primary key,
  user_id varchar2(50) not null,
  name varchar(100) not null,
  title varchar(200) not null,
  content CLOB not null,
  postdate date default sysdate not null,
  original_file varchar2(200),
  save_file varchar2(40),
  visit_count number(6) default 0 not null,
  like_count number(6) default 0 not null
);

-- drop sequence
drop sequence seq_board_id

-- sequence
create sequence seq_board_id
  increment by 1
  start with 1
  minvalue 1
  nomaxvalue
  nocycle
  nocache;

-- foreign key
alter table board
  add constraint board_users_fk foreign key (user_id)
  references users(user_id);

-- postcode
CREATE TABLE POSTCODE (
  POSTCODE CHAR(5) NOT NULL,
  SIDO VARCHAR(20),
  SIDO_ENG VARCHAR(20),
  SIGUNGU VARCHAR(20),
  SIGUNGU_ENG VARCHAR(20),
  EUPMYEON VARCHAR(20),
  EUPMYEON_ENG VARCHAR(20),
  DORO_CODE CHAR(19),
  DORO_NAME VARCHAR(80),
  DORO_NAME_ENG VARCHAR(80),
  UNDER_YN CHAR(1),
  BUILDING_MAIN_NO CHAR(4),
  BUILDING_SUB_NO CHAR(4),
  BUILDING_ID CHAR(30),
  DELIVERY_NAME VARCHAR(100),
  SIGUNGU_BUILDING_NAME VARCHAR(100),
  BJDONG_CODE CHAR(30),
  BJDONG_NAME VARCHAR(80),
  RI_NAME VARCHAR(80),
  HJDONG_NAME VARCHAR(80),
  SAN_YN CHAR(1),
  JIBUN_MAIN_NO CHAR(4),
  JIBUN_SUB_NO CHAR(4),
  JIBUN_SEQ_NO CHAR(5),
  OLD_POSTCODE CHAR(5),
  POSTCODE_SEQ_NO CHAR(2)
);

