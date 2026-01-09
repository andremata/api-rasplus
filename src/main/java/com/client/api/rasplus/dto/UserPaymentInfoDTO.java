package com.client.api.rasplus.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserPaymentInfoDTO {

    private Long id;

    @Size(min = 16, max = 16)
    private String cardNumber;

    @Min(value = 1)
    @Max(value = 12)
    private Integer cardExpirationMonth;

    private Integer cardExpirationYear;
    private String cardSecurityCode;
    private BigDecimal price;
    private Integer installments;
    private LocalDate dtPayment = LocalDate.now();

    @NotNull(message = "Deve ser informado!")
    private Long userId;
}
