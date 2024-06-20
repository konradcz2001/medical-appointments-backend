CREATE TABLE doctors (
                       id BIGSERIAL PRIMARY KEY,
                       first_name VARCHAR(100) NOT NULL,
                       last_name VARCHAR(100) NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(68) NOT NULL,
                       role VARCHAR(10) NOT NULL,
                       street VARCHAR(100),
                       city VARCHAR(100),
                       zip_code VARCHAR(10),
                       country VARCHAR(100),
                       house_number VARCHAR(10),
                       state VARCHAR(100),
                       is_verified BOOLEAN NOT NULL,
                       schedule_id BIGINT,
                       avatar BYTEA,
                       profile_description VARCHAR(10000)
);
