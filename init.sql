-- Creating table structure

DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS schedule;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
	id SERIAL PRIMARY KEY,
	name VARCHAR(200),
	password VARCHAR(200),
	email VARCHAR(200) UNIQUE,
	role NUMERIC(1)	
);

CREATE TABLE schedule (
	id SERIAL PRIMARY KEY,
	users_id INT REFERENCES users(id),
	name VARCHAR(200)
);

CREATE TABLE task (
	id SERIAL PRIMARY KEY,
	schedule_id INT REFERENCES schedule(id),
	name VARCHAR(200),
	date DATE,
	hour_start NUMERIC(2),
	hour_end NUMERIC(2)
);


CREATE INDEX task_date_index ON task(id);


--Filling tables with stock data

INSERT INTO users (email, password, name, role) VALUES
('hectorbrown@codecool.com', '1234', 'Hector Brown jr.', 0),  --1
('janeklawovsky', '1234', 'Jane Klawovsky', 0),  --2
('horvath@codecool.hu', '1234', 'Horváth Ödön', 0),  --3
('kucslubta@codecool.hu', '1234', 'Kucslubta Sándorné', 0)  --4
;

INSERT INTO schedule (users_id, name) VALUES
(1, 'Experimentation week'),  --1
(1, 'Shakespeare art project'),  --2
(2, 'SCRUM Refactoring Tournament'),  --3
(2, 'Summer holiday'),  --4
(2, 'Refurbishing living room'),  --5
(3, 'Philosophy workshop'),  --6
(4, 'Schedule Master week #1'),  --7
(4, 'Schedule Master week #2'),  --8
(4, 'Schedule Master week #3')  --9
;

INSERT INTO task (schedule_id, name, date, hour_start, hour_end) VALUES
(7, 'Implementing skeleton code', '2019-04-23', 9, 12),  --1
(7, 'Database init script', '2019-04-23', 12, 15),  --2
(1, 'Git tests', '2019-03-11', 9, 15),  --3
(1, 'Java module reconfiguration', '2019-03-12', 8, 18),  --4
(1, 'Parsing strings', '2019-03-12', 18, 19),  --5
(1, 'Implementing authorization', '2019-03-13', 9, 11),  --6
(1, 'Doctor''s appointment', '2019-03-13', 11, 12),  --7
(1, 'Refactoring', '2019-03-13', 12, 18)  --8
;




