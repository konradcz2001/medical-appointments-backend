CREATE TABLE leaves (
                        id BIGSERIAL PRIMARY KEY,
                        start_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                        end_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                        doctor_id BIGINT NOT NULL,
                        CONSTRAINT fk_doctor_id FOREIGN KEY (doctor_id) REFERENCES doctors (id)
);
