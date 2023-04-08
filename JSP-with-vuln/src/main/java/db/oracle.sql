-- users
CREATE TABLE users (
  user_id VARCHAR2(15) NOT NULL,
  name VARCHAR2(20) NOT NULL,
  password VARCHAR2(20) not null,
  address VARCHAR2(50) not null,
  regidate date default sysdate not null,
  primary key(user_id)
);

-- drop sequence
drop sequence seq_users_id

-- board
create table board (
  id number not null primary key,
  user_id varchar2(15) not null,
  name varchar(20) not null,
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
