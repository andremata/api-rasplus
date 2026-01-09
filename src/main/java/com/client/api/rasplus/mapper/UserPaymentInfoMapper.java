package com.client.api.rasplus.mapper;

import com.client.api.rasplus.dto.UserPaymentInfoDTO;
import com.client.api.rasplus.model.User;
import com.client.api.rasplus.model.UserPaymentInfo;

public class UserPaymentInfoMapper {

    public static UserPaymentInfo fromDtoToEntity(UserPaymentInfoDTO dto, User user) {
        return UserPaymentInfo.builder()
                .id(dto.getId())
                .cardNumber(dto.getCardNumber())
                .cardExpirationMonth(dto.getCardExpirationMonth())
                .cardExpirationYear(dto.getCardExpirationYear())
                .cardSecurityCode(dto.getCardSecurityCode())
                .price(dto.getPrice())
                .dtPayment(dto.getDtPayment())
                .installments(dto.getInstallments())
                .user(user)
                .build();
    }
}
