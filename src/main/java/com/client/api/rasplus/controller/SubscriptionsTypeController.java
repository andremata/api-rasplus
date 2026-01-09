package com.client.api.rasplus.controller;

import com.client.api.rasplus.dto.SubscriptionTypeDTO;
import com.client.api.rasplus.model.SubscriptionsType;
import com.client.api.rasplus.service.SubscriptionTypeService;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/subscription-type")
public class SubscriptionsTypeController {

    private final SubscriptionTypeService subscriptionsTypeService;

    public SubscriptionsTypeController(SubscriptionTypeService subscriptionsTypeService) {
        this.subscriptionsTypeService = subscriptionsTypeService;
    }

    @Cacheable(value = "subscriptionType")
    @GetMapping
    public ResponseEntity<List<SubscriptionsType>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionsTypeService.findAll());
    }

    @Cacheable(value = "subscriptionType", key = "#id")
    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionsType> findById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionsTypeService.findById(id));
    }

    @CacheEvict(value = "subscriptionType", allEntries = true)
    @PostMapping
    public ResponseEntity<SubscriptionsType> create(@Valid @RequestBody SubscriptionTypeDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionsTypeService.create(dto));
    }

    @CacheEvict(value = "subscriptionType", allEntries = true)
    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionsType> update(@Valid @PathVariable("id") Long id, @RequestBody SubscriptionTypeDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionsTypeService.update(id, dto));
    }

    @CacheEvict(value = "subscriptionType", allEntries = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        subscriptionsTypeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
