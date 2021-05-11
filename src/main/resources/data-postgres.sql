INSERT INTO users(user_id, login, password, role)
VALUES (1, 'Admin', 'Administrator', 0),
       (2, 'Trop', 'Pass', 1),
       (4, 'Imad', 'Giods', 1),
       (5, 'Valeron', 'Fioida', 1),
       (6, 'Petrgov', 'Petete', 1);

INSERT INTO user_details(user_id, birthday, city, language, registration, sex, registr_date)
VALUES (1, '1999 - 01 - 04 00:00:00', 'Odessa', 1, '2020 - 01 - 10 20:01:02', 'MAN', '2020-04-27 14:53:59'),
       (2, '1987 - 08 - 14 00:00:00', 'Moscow', 0, '2019 - 02 - 02 19:13:12', 'MAN', '2021-04- 27 14:53:59'),
       (3, '1957 - 11 - 10 00:00:00', 'St.Peterburg', 0, '2018 - 04 - 09 17:55:34', 'MAN', '2021-04-27 14:53:59'),
       (4, '1997 - 09 - 02 00:00:00', 'Odessa', 0, '2021 - 02 - 12 19:33:21', 'WOMAN', '2021-04-27 14:53:59'),
       (5, '2002 - 11 - 10 00:00:00', 'Kyiv', 1, '2020-11-10 18:32:12', 'WOMAN', '2021-04-27 14:53:59'),
       (6, '1999-01-04 00:00:00', 'Odessa', 1, '2020-01-10 20:01:02', 'MAN', '2021 - 04 - 27 14:53:59');

INSERT INTO user_contacts(user_id, address, card, email, name, phone, surname)
VALUES (1, 'Odessa', '434234324', 'A@i.ua', 'Vasya', '2233321', 'Penrenko'),
       (2, 'Moscow', '323233000', 'D@gmail.com', 'Petro', '9832934', 'Hluchenko'),
       (3, 'St.Peterburg', '234341230', 'HDi@gmail.com', 'Dima', '9878783', 'Shutin'),
       (4, 'Kyiv', '324234243', 'GJ@i.ru', 'Galya', '3432423', 'Fedina'),
       (5, 'Mocow', '546454532', 'VLiz@mail.ru', 'Valya', '3343234', 'Lizona'),
       (6, 'Odessa', '434234324', 'A@i.ua', 'Vasya', '2233321', 'Penrenko');

INSERT INTO booking(id, create_date, pay, user_id)
VALUES (1, '2021-05-24 23:09:22', 1, 1),
       (3, '2021-05-25 13:09:10', 0, 1),
       (4, '2021-05-09 14:10:10', 0, 2),
       (2, '2021-05-20 13:10:10', 0, 4);

INSERT INTO ticket(id, price, session_id, booking, seat_id)
VALUES (1, 55, 1, 1, 1),
       (2, 120, 2, 1, 2),
       (3, 120, 3, 3, 3),
       (4, 120, 4, 4, 4),
       (5, 55, 1, 4, 2),
       (6, 65, 2, 1, 3),
       (7, 240, 8, 4, 4);
