CREATE TABLE Users
(
    Id int NOT NULL AUTO_INCREMENT,
    username varchar(50),
    Password varchar(255),
    FirstName varchar(50),
    LastName varchar(50),
    Email varchar(255),
    role varchar(50),
    primary key (id),
    UNIQUE (username),
    UNIQUE (Email)
);