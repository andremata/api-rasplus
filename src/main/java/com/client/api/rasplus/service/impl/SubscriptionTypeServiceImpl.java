package com.client.api.rasplus.service.impl;

import com.client.api.rasplus.dto.SubscriptionTypeDTO;
import com.client.api.rasplus.exception.BadRequestException;
import com.client.api.rasplus.exception.NotFoundException;
import com.client.api.rasplus.mapper.SubscriptionTypeMapper;
import com.client.api.rasplus.model.jpa.SubscriptionsType;
import com.client.api.rasplus.repository.jpa.SubscriptionsTypeRepository;
import com.client.api.rasplus.service.SubscriptionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SubscriptionTypeServiceImpl implements SubscriptionTypeService {

    @Autowired
    private SubscriptionsTypeRepository subscriptionsTypeRepository;

    @Cacheable(value = "subscriptionType")
    @Override
    public List<SubscriptionsType> findAll() {
        return subscriptionsTypeRepository.findAll();
    }

    @Cacheable(value = "subscriptionType", key = "#id")
    @Override
    public SubscriptionsType findById(Long id) {
        return getSubscriptionsType(id);
    }

    @CacheEvict(value = "subscriptionType", allEntries = true)
    @Override
    public SubscriptionsType create(SubscriptionTypeDTO dto) {
        if(Objects.nonNull(dto.getId())) {
            throw new BadRequestException("The ID must be null.");
        }

        return subscriptionsTypeRepository.save(SubscriptionTypeMapper.fromDtoToEntity(dto));
    }

    @CacheEvict(value = "subscriptionType", allEntries = true)
    @Override
    public SubscriptionsType update(Long id, SubscriptionTypeDTO dto) {
        getSubscriptionsType(id);
        dto.setId(id);
        return subscriptionsTypeRepository.save(SubscriptionTypeMapper.fromDtoToEntity(dto));
    }

    @CacheEvict(value = "subscriptionType", allEntries = true)
    @Override
    public void delete(Long id) {
        getSubscriptionsType(id);
        subscriptionsTypeRepository.deleteById(id);
    }

    private SubscriptionsType getSubscriptionsType(Long id) {
        Optional<SubscriptionsType> subscriptionsType = subscriptionsTypeRepository.findById(id);

        if(subscriptionsType.isEmpty()) {
            throw new NotFoundException("Subscription Type not found!");
        }

        return subscriptionsType.get();
    }
}
