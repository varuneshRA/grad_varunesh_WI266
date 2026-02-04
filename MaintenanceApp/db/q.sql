CREATE TABLE site_table (
    site_id INT PRIMARY KEY,
    type VARCHAR(50),
    size INT,
    maintenance_charges INT,
    occupied BOOLEAN,
    booked BOOLEAN
);

CREATE TABLE owner_table (
    owner_id INT PRIMARY KEY,
    site_id INT REFERENCES site_table(site_id),
    owner_name VARCHAR(100),
    owner_phone_no BIGINT,
    maintenance_paid BOOLEAN DEFAULT FALSE
);

CREATE TABLE request_table (
    request_id SERIAL PRIMARY KEY,
    owner_id INT REFERENCES owner_table(owner_id),
    owner_name VARCHAR(100),
    owner_phone BIGINT
);

CREATE TABLE password (
    username VARCHAR(50) UNIQUE,
    pass VARCHAR(50),
    owner_id INT REFERENCES owner_table(owner_id),
    PRIMARY KEY (owner_id)
);

-- Sites 1 to 10 (40x60 = 2400 sqft | Charge: 14400)
INSERT INTO site_table (site_id, type, size, maintenance_charges, occupied, booked) VALUES
(1, 'VILLA', 2400, 14400, true, false),
(2, 'VILLA', 2400, 14400, true, false),
(3, 'VILLA', 2400, 14400, true, false),
(4, 'VILLA', 2400, 14400, true, false),
(5, 'VILLA', 2400, 14400, true, false),
(6, 'APARTMENT', 2400, 14400, true, false),
(7, 'APARTMENT', 2400, 14400, true, false),
(8, 'APARTMENT', 2400, 14400, true, false),
(9, 'APARTMENT', 2400, 14400, true, false),
(10, 'APARTMENT', 2400, 14400, true, false);

-- Sites 11 to 20 (30x50 = 1500 sqft | Charge: 9000)
INSERT INTO site_table (site_id, type, size, maintenance_charges, occupied, booked) VALUES
(11, 'INDEPENDENT_HOUSE', 1500, 9000, true, false),
(12, 'INDEPENDENT_HOUSE', 1500, 9000, true, false),
(13, 'INDEPENDENT_HOUSE', 1500, 9000, true, false),
(14, 'INDEPENDENT_HOUSE', 1500, 9000, true, false),
(15, 'INDEPENDENT_HOUSE', 1500, 9000, true, false),
(16, 'OPEN_SITE', 1500, 9000, false, false),
(17, 'OPEN_SITE', 1500, 9000, false, false),
(18, 'OPEN_SITE', 1500, 9000, false, false),
(19, 'OPEN_SITE', 1500, 9000, false, false),
(20, 'OPEN_SITE', 1500, 9000, false, false);

-- Sites 21 to 35 (30x40 = 1200 sqft | Charge: 7200)
INSERT INTO site_table (site_id, type, size, maintenance_charges, occupied, booked) VALUES
(21, 'OPEN_SITE', 1200, 7200, false, false),
(22, 'OPEN_SITE', 1200, 7200, false, false),
(23, 'OPEN_SITE', 1200, 7200, false, false),
(24, 'OPEN_SITE', 1200, 7200, false, false),
(25, 'OPEN_SITE', 1200, 7200, false, false),
(26, 'OPEN_SITE', 1200, 7200, false, false),
(27, 'OPEN_SITE', 1200, 7200, false, false),
(28, 'OPEN_SITE', 1200, 7200, false, false),
(29, 'OPEN_SITE', 1200, 7200, false, false),
(30, 'OPEN_SITE', 1200, 7200, false, false),
(31, 'OPEN_SITE', 1200, 7200, false, false),
(32, 'OPEN_SITE', 1200, 7200, false, false),
(33, 'OPEN_SITE', 1200, 7200, false, false),
(34, 'OPEN_SITE', 1200, 7200, false, false),
(35, 'OPEN_SITE', 1200, 7200, false, false);

select * from site_table;

select * from owner_table;

select * from request_table;
select * from password;

truncate site_table, owner_table, request_table, password;

drop table password;