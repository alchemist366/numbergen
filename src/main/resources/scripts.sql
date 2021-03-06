create table regions
(
    id   serial not null
        constraint regions_pk
            primary key,
    name varchar,
    code integer unique
);

create table car_numbers
(
    id bigserial
        constraint car_numbers_pk
            primary key,
    number_part integer,
    letter_part varchar(3),
    region_code integer
        constraint car_numbers_regions_id_fk
            references regions
);

insert into regions (name, code)
values ('Татарстан', 116);

alter table car_numbers
    add constraint car_numbers_regions_code_fk
        foreign key (region_code) references regions (code);

-- Only integer types can be auto increment
alter table car_numbers
    add created timestamp;