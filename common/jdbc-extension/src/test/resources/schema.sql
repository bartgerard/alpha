CREATE TABLE PERSON
(
    ID         BIGINT GENERATED BY DEFAULT AS IDENTITY ( START WITH 1 ) PRIMARY KEY,
    FIRST_NAME VARCHAR(100),
    LAST_NAME  VARCHAR(100)
);