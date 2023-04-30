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
    username varchar(100) not null,
    password varchar(100) not null,
    PRIMARY KEY (id)
);

INSERT INTO user
VALUES(1,'runningMike', 'myPassword123');

INSERT INTO runs
VALUES(1,1, 5.7, 108000, DATE('2022-06-15')),
      (2,1, 5.75, 108170, DATE('2022-06-19')),
      (3,2, 15.09, 428900, DATE('2022-06-21')),
      (4,1, 4.75, 92260, DATE('2022-06-26'));

