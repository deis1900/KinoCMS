
INSERT IGNORE INTO cinema(id, main_photo, name)
VALUES (1, 'https://media-cdn.tripadvisor.com/media/photo-s/06/41/fd/0f/cinema-kiev.jpg', 'Odessa'),
       (2, 'https://lh3.googleusercontent.com/proxy/uHKrUSIU8xBINQsKF5h7FbWs2mzu5snlYLBwkE8i_EMEu-IYx7gkNbMeckB3uJsVvj86Dg3YVsxgnOmvRCU2iMKVSiaHkg', 'Rodina'),
       (3, 'https://media-cdn.tripadvisor.com/media/photo-s/06/41/fd/0f/cinema-kiev.jpg', 'Kyiv'),
       (4, 'https://media-cdn.tripadvisor.com/media/photo-s/06/41/fd/0f/cinema-kiev.jpg', 'St.Petersburg');

INSERT IGNORE INTO rooms(room_id, banner, info, name, room_schema, cinema_id, place_series, seat_place)
VALUES (1, 'https://tioi.com/dsff', 'asd', '1', 'https://tioi.com/dsff', 1, 27, 30),
       (2, 'https://tioi.com/dsff', 'ьт от ', 'Blue Rodina', 'http://i.i.ua/kino/zal/65.jpg', 2, 30, 20),
       (3, 'https://tioi.com/dsff', 'asd', 'Main', 'https://tioi.com/dsff', 4, 30, 100),
       (4, 'https://tioi.com/dsff', 'asd', 'Imax Room', 'https://tioi.com/dsff', 4, 21, 119),
       (5, 'https://tioi.com/dsff', 'титои', 'RED Rodin',
        'https://tickets.od.ua/images/places/3a5bd619c332c6bfc838e82a0248e4af', 2, 15, 20),
       (6, 'https://tioi.com/dsff', 'asd', 'Blue', 'https://tioi.com/dsff', 3, 22, 30),
       (7, 'https://tioi.com/dsff', 'asd', 'Red', 'https://tioi.com/dsff', 3, 28, 30);

INSERT IGNORE INTO news(id, finish_time, info, newsType, start_time, state)
VALUES (1, '2021-05-23 19:30:00', 'Welcome to the \'Night session\'.', 1, '2021-03-23 07:00:00', 1),
       (2, '2021-04-15 15:09:00', 'В прокат вышел фантастический боевик под названием \"Мортал Комбат\", основанный на серии
популярных файтингов, прославившихся в том числе и благодаря крайней натуралистичности членовредительства.
Это уже не первая попытка экранизировать видеоигру, но, вероятно, самая масштабная их них. Удастся ли создателям
картины избежать известного \"проклятия\", которое обрушивается на всех, кто пытается перенести на экраны события
компьютерных игр, узнаем у читателей издания \"Новости кино\", которые, как известно, самые объективные и подкованные.
Орфография и пунктуация авторов сохранена неизменной.\n\n\"В целом совсем не плохо для такого бюджета,начало фильма бомбичное.
Боев хотелось бы больше конечно.Практически весь фильм говорили о турнире,но его так и не показали, как это было в фильме
1995 года.Надеюсь что будет сиквел так как первый фильм это скажем был чисто знакомство и пролог задуманного.
Надеюсь создатели не подведут и учтут мелкие ошибки первого фильма выдав на достойный сиквел\".
Не скучное зрелище, но как кто-то уже выразился - не чувствуется духа приключения; хотя на экране нам показывают магию,
диковинных персонажей и даже мельком другие миры. Если хотите посмотреть драки, то их здесь хватает и хорошо реализованный
рейтинг R впридачу. Но если вы хотите увидеть интересно рассказанную историю, то её здесь нет + к сценарию кое какие вопросы.
Короче фильм на один раз, но продолжение я бы глянул\". John23\n\n\"Экранизация файтинга получилось в целом неплохой,
но звёзд с неба не хватает. Сюжет здесь не самая сильная сторона фильма(хотя сами игры никогда этим и не могли похвастаться),
но компенсирует неплохими поединками. Хотя они меркнут на фоне сражении Саб-зиро и Скорпиона, у которых пожалуй получилась
лучшая драка за весь фильм. Персонажи неплохие но не до конца раскрыты. Решение сделать главного героя фильма, не мелькавшего
до этого в игр серии хоть и понятно, но достаточно спорна. Фаталити тоже присутствуют, как и непосредственно сами отсылки,
коих их тут хватает. Иногда фильм сквозит своей бюджетностью из-за локации и не самого лучшей компьютерной графики.
Но при этом видно, что создатели потратили весь выданный им бюджет куда нужно. В целом не лучше и не хуже экранизации 95-го.
Он просто стоит где-то посередине\". Godzilla1\n\n\"Вполне добротная экранизация игры, именно игры
А не сохнущих по каким-то музыкальным трекам образца 1995 года. Сама игра сильно отличается от фильма 1995 года.
И тот кто играет в игры это полностью осознает. По идее, сама игра должна тщательно взвешивать способности противников,
их плюсы и минусы, но этого не всегда происходит. В этом огромный минус игр по Морталу Комбату. В кино сами бои проходят
абсолютно где угодно, что собственно логично. Зло не собирается ждать и не должно следовать навязанным правилам Добра,
ведь эти поединки и есть Смертельная битва\". HUNTERX\n\n\"Фильм больше похож на аператив, чем на настоящий Мортал Комбат.
Т.е. он всего лишь дразнит, пробуждает в тебе аппетит, вот только основная проблема заключается в том, что главное блюдо
решили приберечь для сиквела, и это удручает. Как бы странно это ни прозвучало, картине было мало рейтинга R, ей явно
не достаёт NC-17, чтобы схватки заиграли более яркими и \"кровавыми\" красками. Понятно, что с таким рейтингом ленту бы
не пустили в прокат, и потому стоит отдать должное режиссёру и всем остальным создателям. С тем бюджетом, что им выделила
студия WB, чувствуется, с какой большой любовью они подошли к своей работе, они сделали всё, чтобы фильм смотрелся очень
круто, это заметно. Но удалось ли им это? Не сказал бы: у фильма хватает и других технических проблем в силу очевидной
неопытности постановщика. Но всё равно хочется верить, что фильм соберёт достаточно денег для сиквела, и ребятам выделят
достаточно средств, чтобы мы с вами когда-нибудь увидели, наконец, настоящую Смертельную битву!\". Евгений S.h.
"Бюджетность проглядывается во всем, и это касается художественной реализации, а не технических возможностей.
Фильм конечно не вызывает дикого отвращения, он довольно смотрительный, динамичный, с более-менее живыми актерами
(которые действительно играют, а не произносят пафосные речи с неподвижным выражением лица), с потоками крови и что
самое интересное, комичный и пародийный. И в то же время это абсолютно не получившаяся экранизация. Отсутствует самый
важный элемент, без которого все лишается смысла - нет турнира, происходят просто хаотичные столкновения противников
(хотя подробно описываются существование внешнего и земного миров и оглашаются правила игры) и нет никакой убедительной
атмосферы и веры в реальность происходящего (чем собственно славилась старая дилогия). Однообразный холодный цвет, нет
красок и все блекло, ужасные тяжеловесные костюмы, какие-то \"мусорные\" декорации - действие то в амбарах, то в шахтах
каких-то, то еще на какой-то свалке.\n\nСкверные типажи (особенно Лю Кэнг, какой-то подросток из секции каратэ), кроме
пожалуй Сони, банальная хореография боев и отсутствие энергичного музыкального сопровождения (все время стандартные шумы,
подчеркивающие какое-либо движение). Я уж не говорю про заглавную музыкальную тему, прозвучавшую только на титрах, без которой
это не Мортал Комбат, а сходка косплейщиков. В общем, в глаза бросаются сплошные минусы и недоработки и главный вопрос - зачем
это все было тогда? Еще и задел на сиквел\". George\n\n\"Видно, что старались играть на ностальгии. Конечно приятно видеть
знакомых персонажей, знаковые элементы из игры. Но боги, почему они не додумались сделать какой-нибудь бой под тот самый
культовый трек ? Вот это был бы мощный удар по ностальгии. Вместо этого они вставили на титрах его клубную версию.
Просто сказочные товарищи. Из битв понравилась только начальная, видно, что над ней корпели больше всего.
А все остальные действительно как-то сумбурно проходили. Визуал особо ругать не хочется, если бюджет правда 40 миллионов,
то вполне добротно. Из персонажей самый никчемный это Рейден, просто бомж какой-то безликий. Шан Цунг пойдет, если не
вспоминать Санаду. Остальные персонажи вполне себе, особенно Кано, без него было бы скучно. В общем есть над чем поработать
в сиквеле, который очевидно состоится. Бюджета бы побольше. P.S. В маску Саб Зиро две головы могут поместиться, смешно на
нем болтается )\". Scream 4\n\n\"Фильм по графике в разы превосходит предыдущие экранизации. Что не скажешь о сюжете.
К плюсам можно отнести введение новых персонажей и фаталити. Но эти факторы можно также отнести и к минусам.
Ведь основная фанбаза заточена именно на классических персонажей, а фаталити - это абсолютный фансервис в фильме.
Явный перекос в равновесии сил при схватках. Почти не объяснения по турнирным меткам. Никакого пояснения новых способностей,
которые вырастают как грибы после дождя. Введены, похоже, из-за того же перекоса в равновесии. Некоторые классические
персонажи потеряли в харизматичности и важности для истории, некоторые - наоборот. Юмор завязан на одном единственном
персонаже. Драк, которые можно назвать поединками, а не подзаголовком сиквела фильма 1995 года, ничтожно мало.
Но они настолько насыщеные экшном, что уравновешивают все другие недобои. Надеюсь, продолжение не разочарует.
Фильм, который забыли проработать, дописать и сделать фильмом. Всё, что сделали, это качественно визуализировали известных
персонажей с определённой долей иронии и своеобразия, а также добавили жёстких сцен, когда противник \"всё\"
(сцены довольно эффектны). В остальном - совершенно отсутствует атмосферность и целостность сюжета, много магии,
но нет магии кино - с выверенными планами, отточенной безукоризненной хореографией, и правильными акцентами саунда.
Действие скачет от одного персонажа к другому совсем произвольно, обретения способностей героев по типу \"оп-ля\".
Также ощущается несоразмерность способностей бойцов в различных сценах. И сильно вторичные попытки залихватства и
юмора в сценах с Кано. В итоге фильм вышел, скорее, посредственным, при том, что потенциал реально очень велик\".
Megan Fox\n\n\"Очередная ненужная экранизация игры. Лучшее что есть в фильме это Кано. Остальные скучные и деревянные
персонажи без чувства самосохранения, причём некоторые из них безбожно слитые. Ю соул оф Майн полный мискаст.
Центральный персонаж притянут за уши, сценарий писался на коленке, так-же как и посредственный саундтрек.
Сами бои ничем выдающемся похвастаться не могут, некоторые моменты напомнили убогую постановку из триквела о Бабе Яге.
Фаталити просто фансервис, причём неоправданный. Операторка в лучших стилях блокбастеров посредственного уровня.
Картинка худо бедно нормальная, хоть в некоторых местах цветокор бахнул пол бутылки абсента не лучшего качества.
Локации тоже по себе убогие и их мало. Фильм оставляет явные намёки на продолжения, но такого качества они явно не нужны.
Это не лучше Смертельной Битвы 95-го года, ну лучше ее сиквела\". Eq Fist\n\n\"Даже не знаю с чего начать.
С того, что фильм \"бомба\" или того, что фильм \"пушка\"? Я думаю, что и то и другое, это однозначные 10 и 10
помноженные на 10 из 10.\nСев в кресло и устроившись поудобнее я настроился как самый критичный человек на планете
и готовился мысленно записывать в блокнот оценки за каждую сцену фильма и такты саундрека. И вот фильм начинается.
Вступительная сцена... Шедевр. И вот фильм заканчивается... А в моем мысленном блокноте только галочки на графе \"ГОДНО\".
Фильмы 90ых хоть и уникально декорированы, но в сравнении с версией 2021, это клоунада на выезде. Схватки полновесные,
рейтинг R раскрыт полностью, актеры красавцы и красавицы. Ммммм.... Соня - шедевр. Кано - актер, он открыл сам себя и
надеюсь он появится в других фильмах. Потенциал у Джоша Лоусена есть. Музыка в фильме подчеркивает происходящее в кадре,
но всё таки, это не саундтрек 90ых, который жив до сих пор. Фан сервис присутствует и усиливает фильм. CGI нормальный\".
Rimdar\n\n\"Ну что же? Это - определённо экранизация игры, со своими плюсами и минусами.\nПлюсы - каст почти без
громких имён, твёрдо попадающий в образы своих персонажей, хорошая постановка боёв, кровавость со всеми нужными зрителю
\"фаталити\" и очень хорошая картинка за копеечный по современным меркам бюджет. Минусы - тонна экспозиции,
убивающая весь второй акт, лишняя ветка семьи главного героя, сжирающая и без того не самый большой хронометраж,
и совсем никакая музыка.\nРезюмируя - куда лучше последнего фильма, но есть куда расти. Продолжение я бы с удовольствием
посмотрела - благо, что фильм с таким бюджетом без труда окупится. Но ждать космоса хотя бы уровня \"Рейда\" не стоит\".
Кэтти-бри', 0, '2021-06-25 20:00:00', 1),
       (3, '2021-07-06 14:00:00', 'Кто купит билетов пачку, тот получит супер тачку', 1, '2021-05-05 09:00:00', 1),
       (4, '2020-03-03 00:00:00', 'for delete', 0, '2021-04-04 09:00:00', 0),
       (5, '2021-07-06 14:00:00',
        'Marvel Pushes Blade Filming Back To 2022 Following Marvel\'s big Phase 4/celebrate the movies trailer, several people were wondering why there was no mention of Blade the new take on the vampire-battling daywalker that is set to star Mahershala Ali. One reason is that the film really won\'t be part of that Phase, especially since production has been shifted from this September to July next year.\n\nAccording to a piece in The Hollywood Reporter that is mostly focused on DC\'s search for a Black director for its new Black Superman film produced by JJ Abrams and written by Ta-Nehisi Coates.\n\nAs for Blade, Marvel has Watchmen TV veteran Stacy Osei-Kuffour writing the script, and the studio is pushing the production start so she can work on further drafts. The MCU team is also committed to finding a Black director for the film, though the process is at a very early stage, especially considering that the script is yet to be locked. Still, if a 2022 shoot works out, the movie could end up premiering later in 2023.Following Marvel\'s big Phase 4/celebrate the movies trailer, several people were wondering why there was no mention of Blade the new take on the vampire-battling daywalker that is set to star Mahershala Ali. One reason is that the film really won\'t be part of that Phase, especially since production has been shifted from this September to July next year.\n\nAccording to a piece in The Hollywood Reporter that is mostly focused on DC\'s search for a Black director for its new Black Superman film produced by JJ Abrams and written by Ta-Nehisi Coates.\n\nAs for Blade, Marvel has Watchmen TV veteran Stacy Osei-Kuffour writing the script, and the studio is pushing the production start so she can work on further drafts. The MCU team is also committed to finding a Black director for the film, though the process is at a very early stage, especially considering that the script is yet to be locked. Still, if a 2022 shoot works out, the movie could end up premiering later in 2023.Following Marvel\'s big Phase 4/celebrate the movies trailer, several people were wondering why there was no mention of Blade the new take on the vampire-battling daywalker that is set to star Mahershala Ali. One reason is that the film really won\'t be part of that Phase, especially since production has been shifted from this September to July next year.\n\nAccording to a piece in The Hollywood Reporter that is mostly focused on DC\'s search for a Black director for its new Black Superman film produced by JJ Abrams and written by Ta-Nehisi Coates.\n\nAs for Blade, Marvel has Watchmen TV veteran Stacy Osei-Kuffour writing the script, and the studio is pushing the production start so she can work on further drafts. The MCU team is also committed to finding a Black director for the film, though the process is at a very early stage, especially considering that the script is yet to be locked. Still, if a 2022 shoot works out, the movie could end up premiering later in 2023.Following Marvel\'s big Phase 4/celebrate the movies trailer, several people were wondering why there was no mention of Blade the new take on the vampire-battling daywalker that is set to star Mahershala Ali. One reason is that the film really won\'t be part of that Phase, especially since production has been shifted from this September to July next year.\n\nAccording to a piece in The Hollywood Reporter that is mostly focused on DC\'s search for a Black director for its new Black Superman film produced by JJ Abrams and written by Ta-Nehisi Coates.\n\nAs for Blade, Marvel has Watchmen TV veteran Stacy Osei-Kuffour writing the script, and the studio is pushing the production start so she can work on further drafts. The MCU team is also committed to finding a Black director for the film, though the process is at a very early stage, especially considering that the script is yet to be locked. Still, if a 2022 shoot works out, the movie could end up premiering later in 2023.Following Marvel\'s big Phase 4/celebrate the movies trailer, several people were wondering why there was no mention of Blade the new take on the vampire-battling daywalker that is set to star Mahershala Ali. One reason is that the film really won\'t be part of that Phase, especially since production has been shifted from this September to July next year.\n\nAccording to a piece in The Hollywood Reporter that is mostly focused on DC\'s search for a Black director for its new Black Superman film produced by JJ Abrams and written by Ta-Nehisi Coates.\n\nAs for Blade, Marvel has Watchmen TV veteran Stacy Osei-Kuffour writing the script, and the studio is pushing the production start so she can work on further drafts. The MCU team is also committed to finding a Black director for the film, though the process is at a very early stage, especially considering that the script is yet to be locked. Still, if a 2022 shoot works out, the movie could end up premiering later in 2023.',
        0, '2021-05-02 09:00:00', 1);

INSERT IGNORE INTO images(id, name, type, url, room_id, cinema_id, news_id)
VALUES(3, 'Star wars', 'film', 'https://starWars.com', 3, NULL, NULL),
       (4, 'Free enter', 'news', 'https://cinemaCity.com', NULL, NULL, 2),
       (5, 'Kyiv', 'cinema', 'http://Kyicv.ua//dfslfknslfks', NULL, 3, NULL),
       (6, 'Baby boom', 'news', 'https://cinemaCity.com', NULL, NULL, 3),
       (7, 'sale', 'sale', 'https://cinemaCity.com', NULL, NULL, 1),
       (9, 'Red Room Kyiv', 'room', 'https://media-cdn.tripadvisor.com/media/photo-s/06/41/fd/0f/cinema-kiev.jpg', 7, NULL, NULL),
       (10, 'Blue Room Kyiv', 'room', 'https://media-cdn.tripadvisor.com/media/photo-s/06/41/fd/0f/cinema-kiev.jpg', 6, NULL, NULL),
       (11, 'Blue Room Kyiv', 'room', 'https://img.odessa1.com/images/catalog/original/g-5-4572-2.jpg', 2, NULL, NULL),
       (12, 'Red Room Rodina', 'room', 'https://myod.info/wp-content/uploads/2018/12/3-13-620x437.jpg', 5, NULL, NULL);

INSERT IGNORE INTO cinema_info(id, address, info, location, phone)
VALUES (1, 'Odessa',
        'Первые кинотеатры "Одесса-Кино" были открыты в Южной Пальмире. Кинотеатр "Родина" после реконструкции (точнее, первый его зал на 516 мест) - в декабре 1999 года. Второй зал (184 места) - в июне 2001-го. Еще два одесских кинотеатра - "Звёздный" (540 мест) и "Москва" (595 мест) - также были открыты после реконструкции в 2001 году.',
        '50.4501° N, 30.5234° E', '+380487788823'),
       (2, 'Odesa',
        'В следующем году нашей “Родине” исполнится 50 лет. У самого старого из современных кинотеатров богатая история, которая начинается задолго до 1963 года, когда открылся большой экран. Она берет свое начало с 1913-го, прозвища “красный дом” и фамилии Лебедев. За свои полвека один из лучших кинотеатров в республике несколько раз переживал периоды заката, но вскоре упорно возрождался. Чем сейчас живет “Родина”, как изменился ее репертуар и каково состояние здания, которому почти 100 лет?',
        '46.4698° N, 30.7278° E', '+380482371717'),
       (3, 'Kyiv, Zaliznichay area',
        'Кинотеатр был открыт в 1954 году[1] под названием «Стереокино» в новопостроенном доме № 29 при послевоенном восстановлении Крещатика. Смотреть фильмы в нём можно было без очков, благодаря использованию вертикальной стереопары по системе «Стерео-35». В дальнейшем кинотеатр был переоборудован для показа стереофильмов с поляризационным разделением изображений, при котором зритель одевает специальные очки. В конце 1960-х после открытия в Киеве нового стерео кинотеатра «Днепр» в Пионерском парке, кинозал «Стереокино» на Крещатике был переименован в «Орбиту».',
        '50.4501° N, 30.5234° E', '+380447788823'),
       (4, 'St.Petersburg, Nevskii area',
        'Трёхэтажное здание на участке номер 29[4], появилось в 1842 году по проекту архитектора Р. И. Кузьмина для жены купца Штром[3]. Позднее владельцами здания были действительный статский советник Н. А. Безобразов, князь Хованский, потомственный почётный гражданин А. С. Вагенгейм при котором архитектором В. В. Виндельбандтом высота здания была увеличена на два этажа[3]. В 1901—1902 в нем жил писатель Куприн[3]. В нём располагались ресторан, магазины и меблированные комнаты. С 1911 году владельцем участка был капитан 1-го ранга Н. В. Чайковский в это время в здании открылся кинотеатр «Сатурн».[3] До революции кинотеатр принадлежал известному в Петербурге предпринимателю Я. Ф. Крынскому и назывался сначала «Биофон-Ауксетофон», а затем «Сатурн»[1] .',
        '50.4501° N, 30.5234° E', '+7918889977');
