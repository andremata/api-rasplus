package com.client.api.rasplus.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailDTO {

    @Email(message = "E-mail inválido")
    private String email;

    @NotBlank(message = "A senha deve ser informada!")
    private String password;

    private String recoveryCode;
}
