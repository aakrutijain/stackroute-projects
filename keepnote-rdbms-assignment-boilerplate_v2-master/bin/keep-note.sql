create table note(
    note_id int(6) primary key, 
    note_title varchar(30), 
    note_content varchar(300), 
    note_status varchar(10), 
    note_creation_date date
);

create table category(
    category_id int(6) primary key,
    category_name varchar(30),
    category_descr varchar(300),
    category_creation_date date,
    category_creator varchar(30)
);

create table reminder(
    reminder_id int(6) primary key,
    reminder_name varchar(30),
    reminder_descr varchar(300),
    reminder_type varchar(10),
    reminder_creation_date date,
    reminder_creator varchar(30)
);

create table user(
    user_id int(6) primary key,
    user_name varchar(30),
    user_added_date date,
    user_password varchar(30),
    user_mobile int(10)
);

create table usernote(
    usernote_id int(6) primary key,
    user_id int(6),
    note_id int(6)
);

create table notereminder(
    notereminder_id int(6) primary key,
    note_id int(6),
    reminder_id int(6)
);

create table notecategory(
    notecategory_id int(6) primary key,
    note_id int(6),
    category_id int(6)
);

insert into note values (1, 'note 1', 'note content 1', 'complete', date '2020-01-20'),
(2, 'note 2', 'note content 2', 'complete', date '2020-01-20');

insert into category values (1, 'category 1', 'category desc 1', date '2020-01-20', 'category creator 1'),
(2, 'category 2', 'category desc 2', date '2020-01-20', 'category creator 2');

insert into reminder values (1, 'reminder 1', 'reminder desc 1', 'type 1', date '2020-01-20', 'reminder creator 1'),
(2, 'reminder 2', 'reminder desc 2', 'type 2', date '2020-01-20', 'reminder creator 2');

insert into user values (1, 'user 1', date '2020-01-20', 'password 1', 9999999999),
(2, 'user 2', date '2020-01-20', 'password 2', 8888888888);

insert into usernote values (1, 1, 1), (2, 1, 2), (3, 2, 2);

insert into notereminder(1, 1, 1), (2, 2, 2);

insert into notecategory(1, 1, 1), (2, 2, 2);

select * from user where user_id=1 and password='password 1';

select * from note where note_creation_date=date '2020-01-20';

select * from category where category_creation_date>date '2020-01-19';

select note_id from usernote where user_id=1;

update note set note_status='pending' where note_id=2;

select n.note_id, n.note_title, n.note_content, n.note_status, n.note_creation_date from 
note n, usernote un where un.user_id=1 and n.note_id=un.note_id;

select n.note_id, n.note_title, n.note_content, n.note_status, n.note_creation_date from 
note n, notecategory nc where nc.category_id=1 and n.note_id=nc.note_id;

select r.reminder_id, r.reminder_name, r.reminder_descr, r.reminder_type, r.reminder_creation_date, 
r.reminder_creator from reminder r, notereminder nr where nr.note_id=1 and r.reminder_id=nr.reminder_id;

select * from reminder where reminder_id=1;

insert into note values (3, 'note 3', 'note content 3', 'complete', date '2020-01-20');
insert into usernote values (4, 2, 3);

insert into note values (4, 'note 4', 'note content 4', 'complete', date '2020-01-20');
insert into notecategory values (3, 4, 2);

insert into reminder values (3, 'reminder 3', 'reminder desc 3', 'type 3', date '2020-01-20', 'reminder creator 3');
insert into notereminder values (3, 4, 3);

delete note, usernote from note inner join usernote on note.note_id=usernote.note_id where usernote.user_id=1;

delete note, notecategory from note inner join notecategory on note.note_id=notecategory.note_id where notecategory.category_id=1;

delete note, notecategory, notereminder, usernote from note n 
left join notecategory nc on n.note_id=nc.note_id
left join notereminder nr on n.note_id=nr.note_id
left join usernote un on n.note_id=un.note_id
where n.note_id=3;

delete user, note, notecategory, notereminder, usernote from note n 
left join notecategory nc on n.note_id=nc.note_id
left join notereminder nr on n.note_id=nr.note_id
left join usernote un on n.note_id=un.note_id
left join user u on un.user_id=u.user_id
where u.user_id=2;
