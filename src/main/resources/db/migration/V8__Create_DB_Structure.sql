create table automobile
(
    id        bigint identity not null,
    model     varchar(50),
    number    varchar(12),
    color_id  bigint not null,
    person_id bigint not null,
    primary key (id)
)

create table color
(
    id   bigint  identity not null,
    name varchar(255) not null,
    primary key (id)
)

create table division
(
    id   bigint identity not null,
    name varchar(30) not null,
    primary key (id)
)

create table job_position
(
    id                bigint identity not null,
    name_position     varchar(60)  not null,
    type_job_position varchar(255) not null,
    primary key (id)
)

create table parking
(
    id           bigint identity not null,
    description  varchar(150),
    place_number int not null,
    primary key (id)
)

create table parking_event
(
    id            bigint identity not null,
    end_time      datetime2,
    start_time    datetime2 not null,
    automobile_id bigint       not null,
    place_id      bigint       not null,
    primary key (id)
)

create table parking_place
(
    id           bigint identity not null,
    place_number int not null,
    parking_id   bigint not null,
    special_status       bit         not null,
    primary key (id)
)

create table person
(
    id             bigint identity not null,
    course         int,
    employee       bit         not null,
    first_name     varchar(60) not null,
    group_name     varchar(10),
    middle_name    varchar(60),
    pass_end_date  datetime2,
    pass_num       int,
    second_name    varchar(60),
    special_status bit         not null,
    student        bit         not null,
    job_position   bigint,
    subdivision    bigint,
    primary key (id)
)

create table subdivision
(
    id          bigint identity not null,
    name        varchar(100) not null,
    division_id bigint,
    primary key (id)
)

alter table automobile
    add constraint automobileColor_FK foreign key (color_id) references color

alter table automobile
    add constraint personAutomobile_FK foreign key (person_id) references person

alter table parking_event
    add constraint eventAutomobile_FK foreign key (automobile_id) references automobile

alter table parking_event
    add constraint evenPlace_FK foreign key (place_id) references parking_place

alter table parking_place
    add constraint placeParking_FK foreign key (parking_id) references parking

alter table person
    add constraint personJob_FK foreign key (job_position) references job_position

alter table person
    add constraint personSubdivision_FK foreign key (subdivision) references subdivision

alter table subdivision
    add constraint subdivision_division_FK foreign key (division_id) references division