CREATE TABLE IF NOT EXISTS person.person_table (
	id INTEGER PRIMARY KEY,
   	firstname varchar NOT NULL,
	surname varchar DEFAULT 0,
);

insert into person_table (firstName, surname) values ('default', 'user')