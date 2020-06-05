CREATE TABLE IF NOT EXISTS users (
    id serial PRIMARY KEY,
    username varchar(50) not null,
    password varchar(80) not null,
    email varchar(100) not null,
    activated boolean default true,
    activation_key varchar(255),
    last_login timestamp,
    authorities varchar(50) not null
);