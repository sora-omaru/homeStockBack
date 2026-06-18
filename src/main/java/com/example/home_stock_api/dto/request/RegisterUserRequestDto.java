package com.example.home_stock_api.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserRequestDto {


    @Email
    @Size(max = 255)
    @NotBlank(message = "メールアドレスは必須です")
    private String email;

    @NotBlank
    @Size(min = 8, max = 255)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",message = "パスワードは英字と数字を含めてください")
    private String password;

    //確認用のパスワード
    @NotBlank
    @Size(min = 8, max = 255)
    private String passwordConfirm;

    @NotBlank
    @Size(max = 100)
    private String displayName;
}
