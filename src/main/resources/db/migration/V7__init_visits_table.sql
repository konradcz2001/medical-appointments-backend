CREATE TABLE visits (
                        id BIGSERIAL PRIMARY KEY,
                        date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                        notes VARCHAR(500),
                        is_cancelled BOOLEAN NOT NULL,
                        type_id BIGINT NOT NULL,
                        client_id BIGINT NOT NULL,
                        CONSTRAINT fk_type_id FOREIGN KEY (type_id) REFERENCES types_of_visits (id),
                        CONSTRAINT fk_client_id FOREIGN KEY (client_id) REFERENCES clients (id)
);
