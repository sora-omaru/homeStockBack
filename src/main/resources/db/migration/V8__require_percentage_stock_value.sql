-- CHECK制約ではNULLの比較結果がUNKNOWNになり通過するため、明示的に必須化する
-- 既存の不整合データを保持しつつ、今後のINSERT・UPDATEには制約を適用する
ALTER TABLE items
    ADD CONSTRAINT chk_percentage_stock_not_null
        CHECK (
            stock_type <> 'PERCENTAGE'
                OR stock_percentage IS NOT NULL
        ) NOT VALID;
