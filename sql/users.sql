create table users (
id serial primary key,
email varchar(20) not null,
password varchar(200) not null
)