package com.example.home_stock_api.service;

import java.text.Normalizer;

//受け取ったitemのnameを正規化する
public class ItemNameNormalizer {
    public String normalize(String name) {
        String normalized = Normalizer.normalize(
                name,
                Normalizer.Form.NFKC
        ).toLowerCase();

        StringBuilder result = new StringBuilder();

        for (char c : normalized.toCharArray()) {
            if (c >= 'ァ' && c <= 'ヶ') {
                c = (char) (c - 'ァ' + 'ぁ');
            }

            result.append(c);
        }

        return result.toString();
    }
}
