-- users
CREATE TABLE users (
  id number not null,
  user_id VARCHAR2(20) NOT NULL,
  name VARCHAR2(20) NOT NULL,
  password VARCHAR2(20) not null,
  address VARCHAR2(30) not null,
  regidate date default sysdate not null,
  constraint users_user_id unique(user_id),
  PRIMARY KEY(user_id, name)
);

-- sequence
create sequence seq_users_id
  increment by 1
  start with 1
  minvalue 1
  nomaxvalue
  nocycle
  nocache;

-- board
create table board (
 id number not null primary key,
user_id varchar2(20) not null,
name varchar(20) not null,
title varchar(200) not null,
content CLOB not null,
postdate date default sysdate not null,
original_file varchar2(200),
save_file varchar2(40),
down_count number(6) default 0 not null,
visit_count number(6) default 0 not null,
like_count number(6) default 0 not null,
CONSTRAINT fk_board_users FOREIGN KEY (user_id, name) REFERENCES users(user_id, name)
);

-- sequence
create sequence seq_board_id
  increment by 1
  start with 1
  minvalue 1
  nomaxvalue
  nocycle
  nocache;