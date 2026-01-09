package com.client.api.rasplus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentProcessDTO {

    @NotBlank(message = "Deve ser informado!")
    private String productKey;

    private BigDecimal discount;

    @NotNull(message = "Dados do pagamento deve ser informado!")
    @JsonProperty("userPaymentInfo")
    private UserPaymentInfoDTO userPaymentInfoDTO;
}
