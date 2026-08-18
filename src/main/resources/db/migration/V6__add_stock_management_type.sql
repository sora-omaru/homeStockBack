--在庫の管理方法を追加
ALTER TABLE items
    ADD COLUMN stock_type VARCHAR(20) NOT NULL DEFAULT 'QUANTITY';

--割合管理の値を追加
ALTER TABLE items
    ADD COLUMN stock_percentage INTEGER;

--割合管理の最低在庫値を追加
ALTER TABLE items
    ADD COLUMN min_percentage INTEGER
        CHECK (min_percentage BETWEEN 0 AND 100);

--quantityをNull許可
ALTER TABLE items
    ALTER COLUMN quantity DROP NOT NULL;

--割合は0~100のみ
ALTER TABLE items
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
                    AND stock_percentage BETWEEN 0 AND 100
                )
            );