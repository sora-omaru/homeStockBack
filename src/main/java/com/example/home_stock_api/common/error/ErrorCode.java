package com.example.home_stock_api.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    //エラー情報群
    EMAIL_ALREADY_EXISTS(
            HttpStatus.CONFLICT,
            "このメールアドレスは既に登録されています"
    ),
    PASSWORD_MISMATCH(
            HttpStatus.BAD_REQUEST,
            "入力されたパスワードが一致しません"
    ),
    VALIDATION_ERROR(
            HttpStatus.BAD_REQUEST,
            "入力内容に誤りがあります"
    );

    private final HttpStatus status;
    private final String message;


    //コンストラクタ作成したため、改修時に変更しなくてもコンパイルが通る
    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
