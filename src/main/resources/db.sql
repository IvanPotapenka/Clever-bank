DROP TABLE IF EXISTS bank_client;
DROP TABLE IF EXISTS transaction;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS client;
DROP TABLE IF EXISTS bank;

CREATE TABLE bank
(
    id   INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    code VARCHAR(15) NOT NULL UNIQUE
);

CREATE TABLE client
(
    id        INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    full_name VARCHAR(50)  NOT NULL,
    email     VARCHAR(50)  NOT NULL UNIQUE,
    password  VARCHAR(256) NOT NULL
);

CREATE TABLE account
(
    id               INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    account_type     VARCHAR(20)      NOT NULL,
    currency         VARCHAR(5)       NOT NULL,
    iban             VARCHAR(30)      NOT NULL UNIQUE,
    client_id        INT              NOT NULL REFERENCES client (id),
    bank_id          INT              NOT NULL REFERENCES bank (id),
    balance          DOUBLE PRECISION NOT NULL,
    interest_amount  DOUBLE PRECISION NOT NULL,
    date_of_creation VARCHAR(20)      NOT NULL
);

CREATE TABLE transaction
(
    id                   INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    type                 VARCHAR(50)      NOT NULL,
    amount               DOUBLE PRECISION NOT NULL CHECK (amount >= 0.0),
    account_send_id      INT              NOT NULL REFERENCES account (id),
    account_recipient_id INT              NOT NULL REFERENCES account (id),
    date                 VARCHAR(20)      NOT NULL,
    time                 VARCHAR(20)      NOT NULL
);


CREATE TABLE bank_client
(
    bank_id   INT REFERENCES bank (id),
    client_id INT REFERENCES client (id) ON DELETE CASCADE,
    PRIMARY KEY (bank_id, client_id)
);

INSERT INTO bank(name, code)
VALUES ('CLEVER', 'CLEVBY2X');

INSERT INTO bank(name, code)
VALUES ('IRON', 'IRONBY2X');

INSERT INTO bank(name, code)
VALUES ('VTB', 'NBRBBY2X');

INSERT INTO bank(name, code)
VALUES ('ALFA', 'ALFABY2X');

INSERT INTO bank(name, code)
VALUES ('TECHNO', 'TECNBY22');

INSERT INTO client(full_name, email, password)
VALUES ('Киркман Александр Владимирович', 'aleks@mail.ru', 'password');
INSERT INTO client(full_name, email, password)
VALUES ('Григорян Наира Грачивна', 'naira@mail.ru', 'password');
INSERT INTO client(full_name, email, password)
VALUES ('Петровои Свитлани Михайливни', 'svitlan@mail.ru', 'password');
INSERT INTO client(full_name, email, password)
VALUES ('Землянуха Елена Викторовна', 'elena@mail.ru', 'password');
INSERT INTO client(full_name, email, password)
VALUES ('Лоскорих Юрий Антонович', 'yuri@mail.ru', 'password');
INSERT INTO client(full_name, email, password)
VALUES ('Закалюжний Ростислав Анатольевич', 'rostic@mail.ru', 'password');
INSERT INTO client(full_name, email, password)
VALUES ('Буркацкий Руслан Мирославович', 'ruslan@mail.ru', 'password');
INSERT INTO client(full_name, email, password)
VALUES ('Бахмет Наталья Ивановна', 'nate@mail.ru', 'password');
INSERT INTO client(full_name, email, password)
VALUES ('Репешко Евгений Васильевич', 'evgeni@mail.ru', 'password');
INSERT INTO client(full_name, email, password)
VALUES ('Чиж Владимир Кириллович', 'vladimir@mail.ru', 'password');
INSERT INTO client(full_name, email, password)
VALUES ('Константинова Алла Анатольевна', 'alla@mail.ru', 'password');
INSERT INTO client(full_name, email, password)
VALUES ('Пагас Лилия Михайловна', 'lilya@mail.ru', 'password');
INSERT INTO client(full_name, email, password)
VALUES ('Алимова Виктория Алексеевна', 'viktoria@mail.ru', 'password');
INSERT INTO client(full_name, email, password)
VALUES ('Борисюк Жанна Ивановна', 'janna@mail.ru', 'password');
INSERT INTO client(full_name, email, password)
VALUES ('Сапожков Яков Николаевич', 'yakov@mail.ru', 'password');
INSERT INTO client(full_name, email, password)
VALUES ('Очеретько Лилия Ивановна', 'lilia@mail.ru', 'password');
INSERT INTO client(full_name, email, password)
VALUES ('Житник Игорь Алексеевич', 'igor@mail.ru', 'password');
INSERT INTO client(full_name, email, password)
VALUES ('Якубович Надежда Ивановна', 'nadya@mail.ru', 'password');
INSERT INTO client(full_name, email, password)
VALUES ('Близнюк Диана Игоревна', 'diana@mail.ru', 'password');
INSERT INTO client(full_name, email, password)
VALUES ('Полупан Эдуард Павлович', 'eduard@mail.ru', 'password');

INSERT INTO bank_client(bank_id, client_id)
VALUES (1, 1);

INSERT INTO bank_client(bank_id, client_id)
VALUES (1, 2);

INSERT INTO bank_client(bank_id, client_id)
VALUES (1, 3);

INSERT INTO bank_client(bank_id, client_id)
VALUES (1, 4);

INSERT INTO bank_client(bank_id, client_id)
VALUES (2, 5);
INSERT INTO bank_client(bank_id, client_id)
VALUES (2, 6);

INSERT INTO bank_client(bank_id, client_id)
VALUES (2, 7);

INSERT INTO bank_client(bank_id, client_id)
VALUES (2, 8);

INSERT INTO bank_client(bank_id, client_id)
VALUES (3, 9);

INSERT INTO bank_client(bank_id, client_id)
VALUES (3, 10);
INSERT INTO bank_client(bank_id, client_id)
VALUES (3, 11);

INSERT INTO bank_client(bank_id, client_id)
VALUES (3, 12);

INSERT INTO bank_client(bank_id, client_id)
VALUES (4, 13);

INSERT INTO bank_client(bank_id, client_id)
VALUES (4, 14);

INSERT INTO bank_client(bank_id, client_id)
VALUES (4, 15);
INSERT INTO bank_client(bank_id, client_id)
VALUES (4, 16);

INSERT INTO bank_client(bank_id, client_id)
VALUES (5, 17);

INSERT INTO bank_client(bank_id, client_id)
VALUES (5, 18);

INSERT INTO bank_client(bank_id, client_id)
VALUES (5, 19);

INSERT INTO bank_client(bank_id, client_id)
VALUES (5, 20);


