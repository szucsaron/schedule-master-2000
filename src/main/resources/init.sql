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
	date DATE,
	max_days NUMERIC(1),
	name VARCHAR(200)
);

CREATE TABLE task (
	id SERIAL PRIMARY KEY,
	user_id INT REFERENCES users(id),
	title VARCHAR(200),
	content TEXT
);

CREATE TABLE schedule_task (
	schedule_id INT REFERENCES schedule(id) ON DELETE CASCADE,
	task_id INT REFERENCES task(id) ON DELETE CASCADE,
	day NUMERIC(1),
	hour_start NUMERIC(2),
	hour_end NUMERIC(2),
	PRIMARY KEY (schedule_id, task_id)
);


-- Trigger functions

CREATE INDEX schedule_date_index ON schedule(date);

CREATE FUNCTION schedule_task_check() RETURNS trigger 
AS '
	BEGIN
		IF NEW.hour_start >= 23 OR NEW.hour_start < 0 THEN
			RAISE EXCEPTION ''hour_start must be between 0 and 23'';
		END IF;
		IF NEW.hour_end > 24 OR NEW.hour_end < 1 THEN
			RAISE EXCEPTION ''hour_end must be between 1 and 24'';
		END IF;
		IF NEW.hour_end <= NEW.hour_start THEN
			RAISE EXCEPTION ''hour_end must be greater than hour_start'';
		END IF;
		IF NEW.day > (SELECT max_days FROM schedule WHERE NEW.schedule_id = schedule.id) THEN
			RAISE EXCEPTION ''Task day can''''t be greater than schedule max days'';
		END IF;
		IF (SELECT count(*) FROM schedule_task WHERE schedule_id = NEW.schedule_id AND day = NEW.day AND hour_start < NEW.hour_end AND hour_end > NEW.hour_start) > 0 THEN
			RAISE EXCEPTION ''Overlapping hours!'';
		END IF;
		IF (SELECT user_id FROM task WHERE id = NEW.task_id) != (SELECT users_id FROM schedule WHERE id = NEW.schedule_id) THEN
			RAISE EXCEPTION ''Tasks cannot be attached to a schedule with different user id! '';
		END IF;
		RETURN NEW;
	END; '
LANGUAGE plpgsql;



CREATE TRIGGER schedule_task_check BEFORE INSERT OR UPDATE ON schedule_task
	FOR EACH ROW EXECUTE PROCEDURE schedule_task_check();


--Filling tables with stock data

INSERT INTO users (email, password, name, role) VALUES
('a', 'a', 'a', 0), --1
('hectorbrown@codecool.com', '1234', 'Hector Brown jr.', 0),  --2
('janeklawovsky', '1234', 'Jane Klawovsky', 0),  --3
('horvath@codecool.hu', '1234', 'Horváth Ödön', 0),  --4
('kucslubta@codecool.hu', '1234', 'Kucslubta Sándorné', 0)  --5
;

INSERT INTO schedule (users_id, name, date, max_days) VALUES
(1, 'Experimentation week', '2019-04-23', 7),  --1
(1, 'Shakespeare art project', '2019-04-30', 7),  --2
(2, 'SCRUM Refactoring Tournament', '2019-04-23', 6),  --3
(2, 'Summer holidays', '2019-04-30', 5),  --4
(2, 'Refurbishing living room', '2019-05-10', 7),  --5
(3, 'Philosophy workshop', '2019-04-23', 7),  --6
(1, 'Schedule Master week #1', '2019-04-21', 5),  --7
(4, 'Schedule Master week #2', '2019-05-02', 6),  --8
(4, 'Schedule Master week #3', '2019-06-12', 5)  --9
;

INSERT INTO task (user_id, title, content) VALUES
(1, 'Radiator building', 'Refurbishing my living room radiator'), --1
(1, 'Implementing skeleton code', 'Implementation of base skeleton code'),  --2
(1, 'Database init script', 'Creating an init script for db operations'),  --3
(1, 'Git tests', 'Testing git and resolving possible errors'),  --4
(1, 'Java module reconfiguration', 'Reconfiguring java module for web use'),  --5
(1, 'Parsing strings', 'Parsing file based content into sql strings'),  --6
(1, 'Implementing authorization', 'User priviliges and access creating'),  --7
(1, 'Doctor''s appointment', 'Regular checkup'),  --8
(2, 'Refactoring', 'Keeping old code up to date with new standards'), --9
(1, 'Cooking class', 'Learning to cook new exiting food'), --10
(1, 'Meditating', 'For inner peace') --11
;

INSERT INTO schedule_task(schedule_id, task_id, day, hour_start, hour_end) VALUES
(7, 1, 5, 9, 12),
(7, 2, 2, 12, 15),
(7, 7, 3, 15, 16),
(1, 3, 2, 9, 10),
(1, 4, 4, 10, 12),
(1, 5, 3, 12, 14),
(1, 6, 2, 14, 16),
(1, 7, 3, 16, 18),
(1, 8, 1, 18, 19)

;
