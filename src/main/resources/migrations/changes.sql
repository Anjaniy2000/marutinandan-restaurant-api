--------------------------------------------------------
-- Author: You (Anjaniy Salekar)
-- Table: items
-- Description: Stores basic information of food items.

CREATE TABLE IF NOT EXISTS item (
    id SERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL UNIQUE,
    price NUMERIC(8,2) NOT NULL
);
-------------------------------------------------------
-- Author: You (Anjaniy Salekar)
-- Table: membership
-- Description: Stores membership information of users.

CREATE TABLE IF NOT EXISTS membership (
    id SERIAL PRIMARY KEY,
    customer_name VARCHAR(20) NOT NULL,
    phone_number VARCHAR(15) NOT NULL UNIQUE,
    registration_date DATE NOT NULL,
    expiry_date DATE NOT NULL,
    duration VARCHAR(10) NOT NULL,
    membership_status VARCHAR(15) DEFAULT 'ACTIVE'
);

ALTER TABLE membership
ADD CONSTRAINT phone_number_format CHECK (phone_number ~ '^[0-9]{10}$');
--------------------------------------------------------'
