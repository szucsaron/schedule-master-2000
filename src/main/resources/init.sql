-- Creating table structure

DROP TRIGGER IF EXISTS schedule_task_check ON schedule_task;
DROP FUNCTION IF EXISTS schedule_task_check;
DROP TABLE IF EXISTS schedule_task;
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
	title VARCHAR(200),
	content TEXT
);

CREATE TABLE schedule_task (
	schedule_id INT REFERENCES schedule(id),
	task_id INT REFERENCES task(id),
	date DATE,
	hour_start NUMERIC(2),
	hour_end NUMERIC(2)
);


-- Trigger functions

CREATE INDEX task_date_index ON schedule_task(date);

CREATE FUNCTION schedule_task_check() RETURNS trigger 
AS '
	BEGIN
		IF NEW.hour_start >= 23 OR NEW.hour_start < 0 THEN
			RAISE EXCEPTION ''hour_start must be between 0 and 23'';
		END IF;
		IF NEW.hour_end >= 24 OR NEW.hour_end < 1 THEN
			RAISE EXCEPTION ''hour_end must be between 1 and 24'';
		END IF;
		IF NEW.hour_end <= NEW.hour_start THEN
			RAISE EXCEPTION ''hour_end be greater than hour_start'';
		END IF;
		RETURN NEW;
	END; '
LANGUAGE plpgsql;

CREATE TRIGGER schedule_task_check BEFORE INSERT OR UPDATE ON schedule_task
	FOR EACH ROW EXECUTE PROCEDURE schedule_task_check();


--Filling tables with stock data

INSERT INTO users (email, password, name, role) VALUES
('hectorbrown@codecool.com', '1234', 'Hector Brown jr.', 0),  --1
('janeklawovsky', '1234', 'Jane Klawovsky', 0),  --2
('horvath@codecool.hu', '1234', 'Horváth Ödön', 0),  --3
('kucslubta@codecool.hu', '1234', 'Kucslubta Sándorné', 0),  --4
('a', 'a', 'a', 0) --5
;

INSERT INTO schedule (users_id, name) VALUES
(1, 'Experimentation week'),  --1
(1, 'Shakespeare art project'),  --2
(2, 'SCRUM Refactoring Tournament'),  --3
(2, 'Summer holidays'),  --4
(2, 'Refurbishing living room'),  --5
(3, 'Philosophy workshop'),  --6
(4, 'Schedule Master week #1'),  --7
(4, 'Schedule Master week #2'),  --8
(4, 'Schedule Master week #3')  --9
;

INSERT INTO task (title, content) VALUES
('Implementing skeleton code', 'Implementation of base skeleton code'),  --1
('Database init script', 'Creating an init script for db operations'),  --2
('Git tests', 'Testing git and resolving possible errors'),  --3
('Java module reconfiguration', 'Reconfiguring java module for web use'),  --4
('Parsing strings', 'Parsing file based content into sql strings'),  --5
('Implementing authorization', 'User priviliges and access creating'),  --6
('Doctor''s appointment', 'Regular checkup'),  --7
('Refactoring', 'Keeping old code up to date with new standards')  --8
;

INSERT INTO schedule_task(schedule_id, task_id, date, hour_start, hour_end) VALUES
(7, 1, '2019-04-23', 9, 12),
(7, 2, '2019-04-23', 12, 15),
(7, 7, '2019-04-23', 12, 13),
(1, 3, '2019-03-11', 9, 15),
(1, 4, '2019-03-12', 8, 18),
(1, 5, '2019-03-12', 18, 19),
(1, 6, '2019-03-13', 9, 11),
(1, 7, '2019-03-13', 11, 12),
(1, 8, '2019-03-13', 12, 18)
;



