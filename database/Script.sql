create table users (
	id SERIAL primary KEY,
	email VARCHAR(255) unique not null,
	password VARCHAR(255) not null,
	created_at TIMESTAMP default CURRENT_TIMESTAMP
);


insert into users (password, email) values ('dusko123','dusko@gmail.com')


CREATE TABLE refresh_token (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    hashed_token TEXT NOT NULL UNIQUE,
    expires_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_refresh_token_user_id
ON refresh_token(user_id);

CREATE INDEX idx_refresh_token_expires_at
ON refresh_token(expires_at);


create table movie (
	id SERIAL primary KEY,
	title VARCHAR(255) not null,
	description VARCHAR(255) not null,
	genre VARCHAR(255) not null,
	duration integer not null,
	releaseYear integer not null,
	thumbnailUrl VARCHAR(255) not null,
	videoUrl VARCHAR(255) not null
);


INSERT INTO movie (title, description, genre, duration, releaseYear, thumbnailUrl, videoUrl) VALUES
('Zootopia 2', 'After cracking the biggest case, rookie cops Judy Hopps and Nick Wilde find themselves on the twisting trail of a great mystery when Gary De Snake arrives and turns the animal metropolis upside down.', 'Animation', 108, 2025, 'https://media.themoviedb.org/t/p/original/oJ7g2CifqpStmoYQyaLQgEU32qO.jpg', 'http://192.168.0.14:8080/videos/.mp4'),

('"Wuthering Heights"', 'Tragedy strikes when Heathcliff falls in love with Catherine Earnshaw, a woman from a wealthy family in 18th-century England.', 'Romance', 136, 2026, 'https://media.themoviedb.org/t/p/original/3YBce6dTh1D5oCMITXk2S5QhPt.jpg', 'http://192.168.0.14:8080/videos/.mp4'),

('Spider-Man: No Way Home', 'Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero.', 'Action', 148, 2021, 'https://media.themoviedb.org/t/p/original/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg', 'http://192.168.0.14:8080/videos/.mp4'),

('Titanic', 'A love story aboard the Titanic.', 'DRAMA', 195, 1997, 'https://media.themoviedb.org/t/p/original/9xjZS2rlVxm8SFx8kPC3aIGCOYQ.jpg', 'http://192.168.0.14:8080/videos/.mp4'),

('The Matrix', 'A hacker discovers the true nature of reality.', 'SCIFI', 136, 1999, 'https://media.themoviedb.org/t/p/original/aOIuZAjPaRIE6CMzbazvcHuHXDc.jpg', 'http://192.168.0.14:8080/videos/.mp4'),

('Gladiator', 'A Roman general seeks revenge.', 'ACTION', 155, 2000, 'https://media.themoviedb.org/t/p/original/wN2xWp1eIwCKOD0BHTcErTBv1Uq.jpg', 'http://192.168.0.14:8080/videos/.mp4'),

('The Hangover', 'Three friends wake up after a wild bachelor party.', 'COMEDY', 100, 2009, 'https://media.themoviedb.org/t/p/original/A0uS9rHR56FeBtpjVki16M5xxSW.jpg', 'http://192.168.0.14:8080/videos/.mp4'),

('The Conjuring', 'Paranormal investigators help a family.', 'HORROR', 112, 2013, 'https://media.themoviedb.org/t/p/original/wVYREutTvI2tmxr6ujrHT704wGF.jpg', 'http://192.168.0.14:8080/videos/.mp4'),

('The Drama', 'A happily engaged couple is put to the test when an unexpected turn sends their wedding week off the rails.', 'Romance', 105, 2026, 'https://media.themoviedb.org/t/p/original/ikcNOWB6Qo1ER1H1BJL6Vf0W22s.jpg', 'http://192.168.0.14:8080/videos/.mp4'),

('The Godfather', 'Spanning the years 1945 to 1955, a chronicle of the fictional Italian-American Corleone crime family.', 'Drama', 175, 1972, 'https://media.themoviedb.org/t/p/original/3bhkrj58Vtu7enYsRolD1fZdja1.jpg', 'http://192.168.0.14:8080/videos/.mp4'),

('Joker', 'Origin story of the Joker.', 'DRAMA', 122, 2019, 'https://media.themoviedb.org/t/p/original/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg', 'http://192.168.0.14:8080/videos/.mp4');


INSERT INTO movie (title, description, genre, duration, releaseYear, thumbnailUrl, videoUrl) values
('Fight Club', 'A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy.', 'Drama', 139, 1999, 'https://media.themoviedb.org/t/p/original/jSziioSwPVrOy9Yow3XhWIBDjq1.jpg', 'http://192.168.0.14:8080/videos/.mp4');
