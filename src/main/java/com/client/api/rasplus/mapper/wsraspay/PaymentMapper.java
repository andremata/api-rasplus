package com.client.api.rasplus.mapper.wsraspay;

import com.client.api.rasplus.dto.wsraspay.CreditCardDTO;
import com.client.api.rasplus.dto.wsraspay.PaymentDTO;

public class PaymentMapper {

    public static PaymentDTO build(String customerId, String orderId, CreditCardDTO dto) {
        return PaymentDTO.builder()
                .customerId(customerId)
                .orderId(orderId)
                .creditCard(dto)
                .build();
    }
}
