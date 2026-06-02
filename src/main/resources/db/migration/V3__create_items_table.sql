CREATE TABLE items(
    id BIGSERIAL PRIMARY KEY ,
    user_id BIGINT NOT NULL ,
    name VARCHAR(100) NOT NULL ,
    quantity INTEGER NOT NULL CHECK (quantity>=0),
    category VARCHAR(20) NOT NULL ,
    location_id BIGINT NULL ,
    min_quantity INTEGER CHECK ( min_quantity >= 0 ),
    expiration_date DATE NULL,
    memo TEXT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_items_user
                  FOREIGN KEY (user_id)
                  REFERENCES users(id),

    CONSTRAINT fk_items_location
                  FOREIGN KEY (location_id)
                  REFERENCES locations(id)
                  ON DELETE SET NULL
);