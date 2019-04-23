DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS schedule;

CREATE TABLE schedule (
	id SERIAL PRIMARY KEY,
	name VARCHAR(200)
);


CREATE TABLE task (
	id SERIAL PRIMARY KEY,
	name VARCHAR(200),
	date DATE
);

CREATE INDEX task_date_index ON task(id);


