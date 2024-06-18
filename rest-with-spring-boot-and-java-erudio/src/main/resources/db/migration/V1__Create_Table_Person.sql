create table person
(
    id         bigint auto_increment primary key,
    first_name varchar(80)  not null,
    last_name  varchar(80)  not null,
    address    varchar(100) not null,
    gender     varchar(6)   not null
);

