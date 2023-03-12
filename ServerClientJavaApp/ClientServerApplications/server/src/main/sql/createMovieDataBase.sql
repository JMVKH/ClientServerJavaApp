-- Anlegen der Movie Datenbank incl. eines Benutzers
-- Anmelden als root erforderlich

CREATE DATABASE MovieDB;

CREATE USER 'MovieDBUser'@'localhost' IDENTIFIED BY 'janik';

GRANT ALL PRIVILEGES ON MovieDB.* TO 'MovieDBUser'@'localhost';

CREATE TABLE MovieDB.Movies (
    ID int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    Title varchar(256) NOT NULL,
    Director varchar(256),
    Rating int,
    Genre varchar(256),
    ReleaseDate datetime
);

INSERT INTO MovieDB.Movies (Title, Director, Rating, Genre, ReleaseDate) VALUES ('Avatar', 'Steven Spielberg', 5, 'Fantasy, Science Fiction', '2009-12-17');
INSERT INTO MovieDB.Movies (Title, Director, Rating, Genre, ReleaseDate) VALUES ('War of the Worlds', 'Steven Spielberg', 4, 'Fantasy, Science Fiction', '2019-10-28');
INSERT INTO MovieDB.Movies (Title, Director, Rating, Genre, ReleaseDate) VALUES ('Warcraft', 'Duncan Jones', 4, 'Fantasy', '2016-05-26');
INSERT INTO MovieDB.Movies (Title, Director, Rating, Genre, ReleaseDate) VALUES ('Alien', 'Ridley Scott', 5, 'Science Fiction, Horror', '1979-05-25');
