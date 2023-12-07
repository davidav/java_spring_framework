CREATE SCHEMA IF NOT EXISTS cache;

create table cache.entities
    (
        id uuid not null
    primary key,
    date timestamp(6) with time zone,
    name varchar(255)
);

INSERT INTO entities(id, name, date) VALUES ('59577dde-eff2-4db5-8f0b-304565123c65', 'testName_1', '2100-03-03 00:00:00.782169 +00:00');
INSERT INTO entities(id, name, date) VALUES ('59577dde-eff2-4db5-8f0b-304565123c66', 'testName_2', '2100-03-03 00:00:00.782169 +00:00' );
INSERT INTO entities(id, name, date) VALUES ('59577dde-eff2-4db5-8f0b-304565123c67', 'testName_3', '2100-03-03 00:00:00.782169 +00:00' );