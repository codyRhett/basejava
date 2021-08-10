create table resume
(
    uuid      char(36) not null
        constraint resume_pk
            primary key,
    full_name text
);

alter table resume
    owner to postgres;

create table if not exists contact
(
    id          serial   not null
        constraint contact_pk
            primary key,
    type        text     not null,
    value       text     not null,
    resume_uuid char(36) not null
        constraint contact_resume_uuid_fk
            references resume
            on delete cascade
);

create unique index contact_uuid_type_index
    on contact (resume_uuid, type);

-- auto-generated definition
create table achievement
(
    id          serial
        constraint achievement_pk
            primary key,
    value       text     not null,
    resume_uuid char(36) not null
        constraint achievement_resume_uuid_fk
            references resume
            on delete cascade
);

alter table achievement
    owner to postgres;

create unique index achievement_id_uindex
    on achievement (id);

-- auto-generated definition
create table personal
(
    id          serial
        constraint personal_pk
            primary key,
    resume_uuid char(36) not null
        constraint personal_resume_uuid_fk
            references resume
            on delete cascade,
    value       text     not null
);

alter table personal
    owner to postgres;

create unique index personal_id_uindex
    on personal (id);

-- auto-generated definition
create table position
(
    id          serial
        constraint position_pk
            primary key,
    value       text     not null,
    resume_uuid char(36) not null
        constraint position_resume_uuid_fk
            references resume
            on delete cascade
);

alter table position
    owner to postgres;

create unique index position_id_uindex
    on position (id);

-- auto-generated definition
create table qualification
(
    id          integer default nextval('experience_id_seq'::regclass) not null
        constraint experience_pk
            primary key,
    value       text                                                   not null,
    resume_uuid char(36)                                               not null
        constraint experience_resume_uuid_fk
            references resume
            on delete cascade
);

alter table qualification
    owner to postgres;

create unique index experience_id_uindex
    on qualification (id);

