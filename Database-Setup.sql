-- creating database--

CREATE DATABASE registrationdb;
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    semester VARCHAR(50) NOT NULL
);


