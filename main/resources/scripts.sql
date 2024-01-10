create table student
(
    id    serial primary key,
    name varchar(128) NOT NULL,
    course_id int references course (id) ON DELETE CASCADE
);

create table course
(
    id    serial primary key,
    name varchar(128) NOT NULL
);

insert into course (name)
values ('Java Core'),
       ('Java Enterprise'),
       ('Java Spring');

insert into student (name, course_id)
values ('Иван Кожемякин', 1),
       ('Андрей Буйнов', 2),
       ('Дмитрий Трусцов', 2),
       ('Максим Комсомольцев', 3),
       ('Эдуард Щеглов', 1),
       ('Игорь Беркутов', 3);

alter table student
    add profile_id int references student_profile (id) ON DELETE CASCADE;

create table student_profile
(
    id    serial primary key,
    assessment decimal NOT NULL
);

insert into student_profile (assessment)
values (4.0),
       (5.4),
       (6.2),
       (7.0),
       (5.9),
       (4.8);

create table trainer
(
    id    serial primary key,
    name varchar(128) NOT NULL
);
insert into trainer (name)
values ('Иван Богатырев'),
       ('Андрей Лентяев'),
       ('Дмитрий Муромцев');

create table course_trainer
(
    course_id    int references course (id) ON DELETE CASCADE,
    trainer_id  int references trainer (id) ON DELETE CASCADE,
    primary key (course_id, trainer_id)
);

insert into course_trainer (course_id, trainer_id)
values (1, 2),
       (1, 3),
       (2, 2),
       (3, 3);