ALTER TABLE items
    DROP CONSTRAINT items_min_percentage_check,
    DROP CONSTRAINT chk_stock_value_by_type,
    DROP CONSTRAINT chk_min_stock_value_by_type;

ALTER TABLE items
    ADD CONSTRAINT items_min_percentage_check
        CHECK (min_percentage BETWEEN 0 AND 200),
    ADD CONSTRAINT chk_stock_value_by_type
        CHECK (
            (
                stock_type = 'QUANTITY'
                    AND quantity IS NOT NULL
                    AND stock_percentage IS NULL
            )
            OR
            (
                stock_type = 'PERCENTAGE'
                    AND quantity IS NULL
                    AND stock_percentage BETWEEN 0 AND 200
            )
        ),
    ADD CONSTRAINT chk_min_stock_value_by_type
        CHECK (
            (
                stock_type = 'QUANTITY'
                    AND min_percentage IS NULL
            )
            OR
            (
                stock_type = 'PERCENTAGE'
                    AND min_quantity IS NULL
                    AND (min_percentage IS NULL OR min_percentage BETWEEN 0 AND 200)
            )
        );
