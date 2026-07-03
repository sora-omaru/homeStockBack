ALTER TABLE items
    ADD CONSTRAINT chk_items_category
        CHECK (category IN (
            'FOOD',
            'DRINK',
            'DAILY_GOODS',
            'SEASONING',
            'MEDICINE',
            'OTHER'
        ));
