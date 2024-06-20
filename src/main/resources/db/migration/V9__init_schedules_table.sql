CREATE TABLE schedules (
                       id BIGSERIAL PRIMARY KEY,
                       monday_start TIME,
                       monday_end TIME,
                       tuesday_start TIME,
                       tuesday_end TIME,
                       wednesday_start TIME,
                       wednesday_end TIME,
                       thursday_start TIME,
                       thursday_end TIME,
                       friday_start TIME,
                       friday_end TIME,
                       saturday_start TIME,
                       saturday_end TIME,
                       sunday_start TIME,
                       sunday_end TIME
);
