-- Creating table structure

DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS schedule;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
	id SERIAL PRIMARY KEY,
	name VARCHAR(200),
	password VARCHAR(200),
	email VARCHAR(200) UNIQUE,
	role INT
	
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
	date DATE
);
CREATE INDEX task_date_index ON task(id);


--Filling tables with stock data

INSERT INTO users (email, password, name, role) VALUES
('hectorbrown@codecool.com', '1234', 'Hector Brown jr.', 0),   --1
('janeklawovsky', '1234', 'Jane Klawovsky', 0),				   --2
('horvath@codecool.hu', '1234', 'Horváth Ödön', 0),			   --3
('kucslubta@codecool.hu', '1234', 'Kucslubta Sándorné', 0)	   --4
;



