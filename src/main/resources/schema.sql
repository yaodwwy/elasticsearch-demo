DROP TABLE IF EXISTS detail CASCADE;
create table detail
(
  id        varchar2(255) primary key,
  name      varchar2(255),
  order_id varchar2(255)
);

DROP TABLE IF EXISTS orders CASCADE;
create table orders
(
  id   varchar2(255) primary key,
  name varchar2(255),
  time timestamp(0)
);

DROP TABLE IF EXISTS es_last_timestamp CASCADE;
create table es_last_timestamp
(
  id   varchar2(255) primary key,
  name varchar2(255),
  last timestamp(0)
);