package com.example.home_stock_api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private int status;//フロントに数字だけ返したいため
    private String code;
    private String message;
}
