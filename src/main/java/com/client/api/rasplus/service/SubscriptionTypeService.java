package com.client.api.rasplus.service;

import com.client.api.rasplus.dto.SubscriptionTypeDTO;
import com.client.api.rasplus.model.jpa.SubscriptionsType;
import java.util.List;

public interface SubscriptionTypeService {

    List<SubscriptionsType> findAll();

    SubscriptionsType findById(Long id);

    SubscriptionsType create(SubscriptionTypeDTO dto);

    SubscriptionsType update(Long id, SubscriptionTypeDTO dto);

    void delete(Long id);
}
