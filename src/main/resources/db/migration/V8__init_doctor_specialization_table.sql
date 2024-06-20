CREATE TABLE doctor_specialization (
                        doctor_id BIGSERIAL NOT NULL,
                        specialization_id INTEGER NOT NULL,
                        CONSTRAINT fk_doctor_id FOREIGN KEY (doctor_id) REFERENCES doctors (id),
                        CONSTRAINT fk_specialization_id FOREIGN KEY (specialization_id) REFERENCES specializations (id)
);