package com.client.api.rasplus.repository;

import com.client.api.rasplus.model.SubscriptionsType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionsTypeRepository extends JpaRepository<SubscriptionsType, Long> {

    Optional<SubscriptionsType> findByProductKey(String productkey);
}
