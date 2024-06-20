CREATE TABLE reviews (
                         id BIGSERIAL PRIMARY KEY,
                         date TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         rating SMALLINT NOT NULL,
                         description VARCHAR(500),
                         doctor_id BIGINT NOT NULL,
                         client_id BIGINT NOT NULL,
                         CONSTRAINT fk_doctor_id FOREIGN KEY (doctor_id) REFERENCES doctors (id),
                         CONSTRAINT fk_client_id FOREIGN KEY (client_id) REFERENCES clients (id)
);
