CREATE TABLE if not exists runs(
    run_id int not null AUTO_INCREMENT,
    user_id int not null,
    km_number double not null,
    seconds int not null,
    date date not null,
    PRIMARY KEY (run_id)
);

CREATE TABLE if not exists user(
    id int not null AUTO_INCREMENT,
    username int not null,
    password double not null,
    PRIMARY KEY (id)
);