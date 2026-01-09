package com.client.api.rasplus.mapper.wsraspay;

import com.client.api.rasplus.dto.PaymentProcessDTO;
import com.client.api.rasplus.dto.wsraspay.OrderDTO;

public class OrderMapper {

    public static OrderDTO build(String customerId, PaymentProcessDTO dto) {
        return OrderDTO.builder()
                .customerId(customerId)
                .discount(dto.getDiscount())
                .productAcronym(dto.getProductKey())
                .build();
    }
}
