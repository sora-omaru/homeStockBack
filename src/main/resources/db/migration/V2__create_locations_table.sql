CREATE TABLE locations(
    id BIGSERIAL PRIMARY KEY ,
    user_id BIGINT NOT NULL ,
    name VARCHAR(100) NOT NULL ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_locations_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
);