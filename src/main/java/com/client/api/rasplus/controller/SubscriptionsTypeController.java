package com.client.api.rasplus.controller;

import com.client.api.rasplus.dto.SubscriptionTypeDTO;
import com.client.api.rasplus.model.jpa.SubscriptionsType;
import com.client.api.rasplus.service.SubscriptionTypeService;
import jakarta.validation.Valid;
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

    @GetMapping
    public ResponseEntity<List<SubscriptionsType>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionsTypeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionsType> findById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionsTypeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SubscriptionsType> create(@Valid @RequestBody SubscriptionTypeDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionsTypeService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionsType> update(@Valid @PathVariable("id") Long id, @RequestBody SubscriptionTypeDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionsTypeService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        subscriptionsTypeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
