package com.client.api.rasplus.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SubscriptionTypeDTO {

    private Long id;

    @NotBlank(message = "O campo nome não pode ser nulo ou vazio!")
    @Size(min = 5, max = 45, message = "O campo nome tem que ter entre 5 e 45 caracteres!")
    private String name;

    @Max(value = 12)
    private Integer accessMonths;

    @NotNull(message = "O preço não pode ser nulo!")
    private BigDecimal price;

    @NotBlank(message = "O campo produto não pode ser nulo ou vazio!")
    @Size(min = 5, max = 12, message = "O campo produto deve ter entre 5 e 12 caracteres!")
    private String productKey;
}
