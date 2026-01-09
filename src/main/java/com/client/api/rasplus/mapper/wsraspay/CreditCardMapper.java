package com.client.api.rasplus.mapper.wsraspay;

import com.client.api.rasplus.dto.UserPaymentInfoDTO;
import com.client.api.rasplus.dto.wsraspay.CreditCardDTO;

public class CreditCardMapper {

    public static CreditCardDTO build(UserPaymentInfoDTO dto, String documentNumber) {
        return CreditCardDTO.builder()
                .documentNumber(documentNumber)
                .cvv(Long.parseLong(dto.getCardSecurityCode()))
                .month(dto.getCardExpirationMonth())
                .year(dto.getCardExpirationYear())
                .installments(dto.getInstallments())
                .number(dto.getCardNumber())
                .build();
    }
}
