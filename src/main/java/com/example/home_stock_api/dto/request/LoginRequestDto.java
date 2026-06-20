package com.example.home_stock_api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginRequestDto {
    @Email
    @Size(max = 255)
    @NotBlank(message = "メールアドレスは必須です")
    private String email;

    @NotBlank
    @Size(min = 8, max = 255)
    private String password;
}
