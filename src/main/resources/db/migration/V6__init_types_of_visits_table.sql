CREATE TABLE types_of_visits (
                        id BIGSERIAL PRIMARY KEY,
                        type VARCHAR(100) NOT NULL,
                        price NUMERIC(10, 2) NOT NULL,
                        currency VARCHAR(5) NOT NULL,
                        duration INTEGER NOT NULL,
                        is_active BOOLEAN NOT NULL,
                        doctor_id BIGSERIAL NOT NULL,
                        CONSTRAINT fk_doctor_id FOREIGN KEY (doctor_id) REFERENCES doctors (id)
);
