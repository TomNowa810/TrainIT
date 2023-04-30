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
VALUES(1,'runningMike', 'myPassword123'),
      (2, 'MasterJürgen', 'JürgenIstDerBeste'),
      (3, 'RunningGod', 'jogging4life');

INSERT INTO runs
VALUES(1,1, 5.7, 1800, DATE('2022-06-15')),
      (2,1, 5.75, 1870, DATE('2022-06-19')),
      (3,2, 15.09, 6900, DATE('2022-06-21')),
      (4,1, 4.75, 1760, DATE('2022-06-26')),
      (5,2, 4.75, 1700, DATE('2022-07-01')),
      (6,2, 4.72, 1740, DATE('2022-07-03')),
      (7,2, 4.48, 1630, DATE('2022-07-06')),
      (8,2, 4.65, 1680, DATE('2022-07-09')),
      (9,2, 4.58, 1685, DATE('2022-07-12')),
      (10,2, 5.78, 1960, DATE('2022-07-15')),
      (11,3, 9.75, 3630, DATE('2022-07-16')),
      (12,3, 7.9, 2570, DATE('2022-07-17')),
      (13,2, 4.75, 1730, DATE('2022-07-18')),
      (14,3, 4.75, 1736, DATE('2022-07-19')),
      (15,2, 4.95, 1800, DATE('2022-07-21')),
      (16,2, 6.30, 1960, DATE('2022-07-24')),
      (17,2, 4.05, 1430, DATE('2022-07-27')),
      (18,2, 6.85, 2023, DATE('2022-07-30'));