drop table if exists blogger;
drop table if exists blog;

create table blogger (
	id identity,
	username varchar(20) unique not null,
	password varchar(20) not null,
	first_name varchar(30) not null,
	last_name varchar(30) not null,
	email varchar(30) not null
);

create table blog (
    id integer identity primary key,
    blogger integer not null,
    message varchar(140) not null,
	created_at timestamp not null,
	foreign key (blogger) references blogger(id)
);
