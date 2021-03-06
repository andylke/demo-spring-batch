
create table user (
  id uuid not null,
  username varchar(100) not null,
  password varchar(200) not null,
  name varchar(200) not null,
  email varchar(200) not null,
  nationality char(2) not null,
  created_by varchar(200) not null
);

alter table user add constraint user_pk primary key (id);

alter table user add constraint user_uk_username unique key (username);
