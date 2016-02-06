create table creatives (
  id int PRIMARY KEY,
  description varchar(256) not null,
  url varchar(256) not null,
  os array not null,
  countries array not null,
  excluded_os array not null,
  excluded_countries array not null
);