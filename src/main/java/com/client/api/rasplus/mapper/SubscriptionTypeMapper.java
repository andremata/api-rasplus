package com.client.api.rasplus.mapper;

import com.client.api.rasplus.dto.SubscriptionTypeDTO;
import com.client.api.rasplus.model.SubscriptionsType;

public class SubscriptionTypeMapper {

    public static SubscriptionsType fromDtoToEntity(SubscriptionTypeDTO dto) {
        return SubscriptionsType
                .builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .accessMonths(dto.getAccessMonths())
                .productKey(dto.getProductKey())
                .build();
    }
}
