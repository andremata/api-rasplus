package com.client.api.rasplus.dto;

import com.client.api.rasplus.model.SubscriptionsType;
import com.client.api.rasplus.model.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDTO {

    private Long id;

    @NotBlank(message = "O campo nome não pode ser nulo ou vazio!")
    @Size(min = 5, max = 45, message = "O campo nome tem que ter entre 5 e 45 caracteres!")
    private String name;

    @Email(message = "O campo e-mail não pode ser nulo ou vazio!")
    private String email;

    @Size(min = 11, message = "O campo telefone tem que ter no minimo 11 caracteres!")
    private String phone;

    @CPF(message = "O campo CPF não pode ser nulo ou vazio!")
    private String cpf;

    private LocalDate dtSubscription = LocalDate.now();

    private LocalDate dtExpiration = LocalDate.now();

    @NotNull
    private Long userTypeId;

    private Long subscriptionsTypeId;
}
