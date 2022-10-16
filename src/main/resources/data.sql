DROP TABLE IF EXISTS users_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

insert into PUBLIC.MEALS(code, name, price)
values ('0001', 'pilotes', 1.33)
;
insert into PUBLIC.CLIENTS(first_name, last_name, telephone, email)
values ('sam', 'paul', '06101010101', 'sam@outlook.com'),
       ('john', 'lint', '0567899532', 'john@mail.com')
;
insert into PUBLIC.ADDRESSES(street, postcode, city, country)
values ('street', 'postcode', 'city', 'country'),
       ('lae', '20000', 'casablanca', 'morocco')
;

CREATE TABLE users
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(250) NOT NULL,
    password VARCHAR(250) NOT NULL,
    enabled  BOOLEAN
);

INSERT INTO users (username, password, enabled)
VALUES ('manager', '$2a$10$It4uiZDQJVVk3oq2.U2sHOJVsfelxnl6zkDytBY.roS/mTO9F2dOm', true),
       ('admin', '$2a$10$It4uiZDQJVVk3oq2.U2sHOJVsfelxnl6zkDytBY.roS/mTO9F2dOm', true),
       ('guest', '$2a$10$It4uiZDQJVVk3oq2.U2sHOJVsfelxnl6zkDytBY.roS/mTO9F2dOm', true);

CREATE TABLE roles
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL
);

INSERT INTO roles (name)
VALUES ('USER'),
       ('ADMIN'),
       ('MANAGER');


CREATE TABLE users_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id),
    PRIMARY KEY (user_id, role_id)
);

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 3),
       (2, 1),
       (2, 2),
       (2, 3),
       (3, 1);