create table "customer"
(
    id         serial
        constraint user_pk
            primary key,
    first_name varchar(100) not null,
    last_name  varchar(100) not null,
    email      varchar(80)  not null
);

comment on table customer is 'Table to storage the users information';

