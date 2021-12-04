CREATE TYPE user_role AS ENUM ('ADMINISTRATOR', 'TEACHER', 'STUDENT');

CREATE CAST (character varying as user_role) WITH INOUT AS IMPLICIT;

CREATE TABLE IF NOT EXISTS users
(
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(64) NOT NULL,
    last_name VARCHAR(64) NOT NULL,
    role user_role NOT NULL,
    login VARCHAR(64) UNIQUE NOT NULL,
    password TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS courses
(
    id BIGSERIAL PRIMARY KEY,
    start_date TIMESTAMP WITHOUT TIME ZONE,
    end_date TIMESTAMP WITHOUT TIME ZONE,
    name VARCHAR(64) NOT NULL,
    teacher_id INTEGER REFERENCES users (id),
    description VARCHAR(1000)
);

CREATE TABLE IF NOT EXISTS lessons
(
    id BIGSERIAL PRIMARY KEY,
    start_time TIMESTAMP WITHOUT TIME ZONE,
    end_time TIMESTAMP WITHOUT TIME ZONE,
    day_of_week VARCHAR(64) NOT NULL,
    teacher_id INTEGER REFERENCES users (id),
    courses_id INTEGER REFERENCES courses (id)
);

CREATE TABLE IF NOT EXISTS students_courses
(
    student_id INTEGER REFERENCES users (id),
    courses_id INTEGER REFERENCES courses (id)
);
