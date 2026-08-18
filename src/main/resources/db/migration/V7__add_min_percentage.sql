-- 最低在庫値は管理方式に対応する側だけ設定できる
ALTER TABLE items
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
                    AND (min_percentage IS NULL OR min_percentage BETWEEN 0 AND 100)
            )
        );
