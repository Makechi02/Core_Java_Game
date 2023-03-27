CREATE DATABASE core_java_game;

USE core_java_game;

CREATE TABLE players(
    id INT AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    score INT,
    PRIMARY KEY(id)
);