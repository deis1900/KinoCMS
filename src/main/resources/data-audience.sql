
INSERT IGNORE INTO authority (id, role)
VALUES (1, 'ANONYMOUS'),
       (2, 'USER'),
       (3, 'ADMIN') ;

INSERT IGNORE INTO users (id, login, password, account_Non_Expired, account_Non_Locked, credentials_Non_Expired, enabled,
                   token)
VALUES (5, 'anonymous', 'anonymous',true, true, true, true, 'anonymous:anonymous'),
       (1, 'administrator', 'adminPassword', true, true, true, true, 'administrator:adminPassword'),
       (2, 'Trop', 'password', true, true, true, true, 'username:password'),
       (3, 'Petro', 'PetPass', true, true, true, true, 'Petro:PetPass'),
       (4, 'Tomkats', 'Tomkatuser', true, true, true, true, 'Tomkats:Tomkatuser');

INSERT IGNORE INTO user_authority(user_id, authority_id)
VALUES (5, 1),
       (2, 3),
       (3, 2),
       (4, 2),
       (1, 3);

INSERT IGNORE INTO customers(customer_id, login)
VALUES (1, 'Admin'),
       (2, 'Trop'),
       (3, 'Petro'),
       (4, 'Imad'),
       (5, 'Valeron'),
       (6, 'Petrgov');

INSERT IGNORE INTO customer_details(customer_id, birthday, city, language, registration, sex, registr_date)
VALUES (1, '1999-01-04 00:00:00', 'Odessa', 1, '2020-01-10 20:01:02', 'MAN', '2020-04-27 14:53:59'),
       (2, '1987-08-14 00:00:00', 'Moscow', 0, '2019-02-02 19:13:12', 'MAN', '2021-04-27 14:53:59'),
       (3, '1957-11-10 00:00:00', 'St.Peterburg', 0, '2018-04-09 17:55:34', 'MAN', '2021-04-27 14:53:59'),
       (4, '1997-09-02 00:00:00', 'Odessa', 0, '2021-02-12 19:33:21', 'WOMAN', '2021-04-27 14:53:59'),
       (5, '2002-11-10 00:00:00', 'Kyiv', 1, '2020-11-10 18:32:12', 'WOMAN', '2021-04-27 14:53:59'),
       (6, '1999-01-04 00:00:00', 'Odessa', 1, '2020-01-10 20:01:02', 'MAN', '2021-04-27 14:53:59');

INSERT IGNORE INTO customer_contacts(customer_id, address, card, email, name, phone, surname)
VALUES (1, 'Odessa', '434234324', 'A@i.ua', 'Vasya', '2233321', 'Penrenko'),
       (2, 'Moscow', '323233000', 'D@gmail.com', 'Petro', '9832934', 'Hluchenko'),
       (3, 'St.Peterburg', '234341230', 'HDi@gmail.com', 'Dima', '9878783', 'Shutin'),
       (4, 'Kyiv', '324234243', 'GJ@i.ru', 'Galya', '3432423', 'Fedina'),
       (5, 'Mocow', '546454532', 'VLiz@mail.ru', 'Valya', '3343234', 'Lizona'),
       (6, 'Odessa', '434234324', 'A@i.ua', 'Vasya', '2233321', 'Penrenko');

INSERT IGNORE INTO seat(id, series, place, free)
VALUES (1, 1, 1, true),
       (2, 2, 2, false),
       (3, 3, 3, false),
       (4, 4, 4, false);

INSERT IGNORE INTO photos(id, name, type, url, film_id)
VALUES (1, 'Avatar', 'film', 'https://i.guim.co.uk/img/media/d31ebd49b32a5aa609a584ababb1e03bc70b4942/573_213_2929_1758/master/2929.jpg?width=445&quality=45&auto=format&fit=max&dpr=2&s=a54fc963e39dd6645fce012663ed13c1', 1),
       (2, 'The guardian of galaxy', 'film', 'https://townsquare.media/site/442/files/2014/06/Guardians.png?w=980&q=75', 2),
       (3, 'Star wars', 'film', 'https://starWars.com', 3),
       (8, 'Avatar', 'film', 'https://geekvibesnation.com/wp-content/uploads/2021/03/avatar-2-story.jpg', 1);

INSERT IGNORE INTO films( id, duration, finish_date, main_photo, name, start_date, user_age, video_type, quality)
VALUES (1, '01:45:00', '2022-02-10 00:00:00', 'https://miro.medium.com/max/453/1*zWGSyuGKWgKYu-ZAf5bxiA.jpeg', 'Avatar',
        '2018-01-01 00:00:00', 12, 'Action, Comedy', 1),
       (2, '02:05:00', '2022-07-02 00:00:00',
        'https://uploadcomet.com/wp-content/uploads/2020/04/guardian-of-the-galaxy-vol-3.jpeg',
        'The Guardians of galaxy', '2021-05-10 00:00:00', 12, 'Comedy, Marvel', 2),
       (3, '01:55:00', '2021-06-04 00:00:00',
        'https://mediad.publicbroadcasting.net/p/wuis/files/styles/large/public/202002/marvel-star-wars-allegiance.jpg',
        'Star Wars', '2021-04-11 00:00:00', 18, 'Fantastic, Drama', 0),
       (4, '01:57:00', '2022-04-25 00:00:00', 'https://images-na.ssl-images-amazon.com/images/I/810TDIdVHQL._RI_.jpg',
        'The Neon Demon', '2021-03-20 00:00:00', 18, 'HORROR', 1),
       (5, '01:57:00', '2021-08-20 00:00:00',
        'https://upload.wikimedia.org/wikipedia/en/6/68/Ocean%27s_Eleven_2001_Poster.jpg', 'Ocean''s Eleven',
        '2021-04-21 00:00:00', 12, 'COMEDY', 2);

INSERT IGNORE INTO films_details(actors, budget, compositor, director, genres, info, producer, scenarist, trailer, id)
VALUES ('Sam Worthington, Zoe Saldana', 237, 'Flamenco', 'James Cameron', 2,
        'Avatar (marketed as James Cameron\'s Avatar) is a 2009 American[7][8] epic science fiction film directed, written, produced, and co-edited by James Cameron and starring Sam Worthington, Zoe Saldana, Stephen Lang, Michelle Rodriguez, and Sigourney Weaver. The film is set in the mid-22nd century when humans are colonizing Pandora, a lush habitable moon of a gas giant in the Alpha Centauri star system, in order to mine the mineral unobtanium,[9][10] a room-temperature superconductor.[11] The expansion of the mining colony threatens the continued existence of a local tribe of Na\'vi – a humanoid species indigenous to Pandora. The film\'s title refers to a genetically engineered Na\'vi body operated from the brain of a remotely located human that is used to interact with the natives of Pandora',
        'Joshua', 'Katty', 'https://youtu.be/5PSNL1qE6VY', 1),
       ('David', 232, 'Ganz Muller', 'Hira', 4,
        'Guardians of the Galaxy (retroactively referred to as Guardians of the Galaxy Vol. 1)[4][5] is a 2014 American superhero film based on the Marvel Comics superhero team of the same name. Produced by Marvel Studios and distributed by Walt Disney Studios Motion Pictures, it is the 10th film in the Marvel Cinematic Universe (MCU). Directed by James Gunn, who wrote the screenplay with Nicole Perlman, the film features an ensemble cast including Chris Pratt, Zoe Saldana, Dave Bautista, Vin Diesel, and Bradley Cooper as the titular Guardians, along with Lee Pace, Michael Rooker, Karen Gillan, Djimon Hounsou, John C. Reilly, Glenn Close, and Benicio del Toro. In the film, Peter Quill and a group of extraterrestrial criminals go on the run after stealing a powerful artifact.',
        'Huchangua', 'Torry', 'https://youtu.be/d96cjJhvlMA', 2),
       ('Sussan', 320, 'Jasmin', 'Griffin', 2,
        'Star Wars is an American epic space opera[1] media franchise created by George Lucas, which began with the eponymous 1977 film[b] and quickly became a worldwide pop-culture phenomenon. The franchise has been expanded into various films and other media, including television series, video games, novels, comic books, theme park attractions, and themed areas, comprising an all-encompassing fictional universe.[c] In 2020, its total value was estimated at US$70 billion, and it is currently the fifth-highest-grossing media franchise of all time.',
        'Piper', 'Bush', 'https://youtube.com/StarWars', 3),
       ('Elle Fanning as Jesse, Karl Glusman as Dear, Jena Malone as Ruby, Bella Heathcote as Gigi', 70,
        'Cliff Martinez', 'Nicolas Winding Refn', 3,
        'The Neon Demon is a 2016 psychological horror film[4] directed by Nicolas Winding Refn, co-written by Mary Laws, Polly Stenham, and Refn, and starring Elle Fanning. The plot follows an aspiring model in Los Angeles whose beauty and youth generate intense fascination and jealousy within the fashion industry. Supporting roles are played by Karl Glusman, Jena Malone, Bella Heathcote, Abbey Lee, Desmond Harrington, Christina Hendricks, and Keanu Reeves.\n\nAn international co-production between France, Denmark, and the United States, the film competed for the Palme d\'Or at the 2016 Cannes Film Festival,[5][6] the third consecutive film directed by Refn to do so, following Drive (2011) and Only God Forgives (2013). In the United States, the film was released theatrically on June 24, 2016 by Amazon Studios and Broad Green Pictures. It opened to polarized reviews, and ultimately grossed a little over $3 million against a $7 million budget.',
        'Lene Børglum, Nicolas Winding Refn', '	Nicolas Winding Refn', 'https://youtu.be/BsOmYpP4UDU', 4),
       ('George Clooney as Danny Ocean, Bernie Mac as Frank Catton, Brad Pitt as Robert \"Rusty\" Ryan, Elliott Gould as Reuben Tishkoff',
        85, 'David Holmes', 'Steven Soderbergh', 0,
        'Ocean\'s Eleven is a 2001 American heist comedy film directed by Steven Soderbergh and written by Ted Griffin. The first installment of the Ocean\'s franchise, it is a remake of the 1960 Rat Pack film of the same name. Ocean\'s Eleven features an ensemble cast, including George Clooney, Brad Pitt, Matt Damon, Don Cheadle, Andy García, Bernie Mac and Julia Roberts. Ocean\'s Eleven follows friends Danny Ocean (Clooney) and Rusty Ryan (Pitt), who plan a heist of $160 million from casino owner Terry Benedict (García), the lover of Ocean\'s ex-wife Tess (Roberts).',
        'Jerry Weintraub', 'George C. Johnson, Jack Golden Russell', 'https://youtu.be/imm6OR605UI', 5);


INSERT IGNORE INTO booking(id, create_date, pay, customer_id)
VALUES (1, '2021-05-24 23:09:22', true, 1),
       (3, '2021-05-25 13:09:10', false, 1),
       (4, '2021-05-09 14:10:10', false, 2),
       (2, '2021-05-20 13:10:10', false, 4);

INSERT IGNORE INTO ticket(id, price, session_id, booking_id, seat_id)
VALUES (1, 55, 1, 1, 1),
       (2, 120, 2, 1, 2),
       (3, 120, 3, 3, 3),
       (4, 120, 4, 4, 4),
       (5, 55, 1, 4, 2),
       (6, 65, 2, 1, 3),
       (7, 240, 8, 4, 4);

INSERT IGNORE INTO sessions(id, show_time, film_id, room_id)
VALUES (1, '2021-05-11 00:00:00', 1, 1),
       (2, '2021-05-20 20:00:00', 2, 2),
       (3, '2021-05-23 00:00:00', 3, 3),
       (4, '2021-05-22 21:00:00', 2, 2),
       (5, '2021-05-25 20:00:00', 3, 1),
       (6, '2021-05-27 14:00:00', 2, 2),
       (7, '2021-05-28 20:00:00', 1, 3),
       (8, '2021-05-24 13:00:00', 4, 2);
