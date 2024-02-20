CREATE TABLE ticket
(
    id SERIAL PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    description VARCHAR(100),
    status VARCHAR(20),
    comments VARCHAR(100),
    created_at TIMESTAMP,
    last_updated_at TIMESTAMP
);