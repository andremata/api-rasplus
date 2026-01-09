package com.client.api.rasplus.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "subscriptions_type")
public class SubscriptionsType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscriptions_type_id")
    private Long id;

    private String name;

    @Column(name = "access_months")
    private Integer accessMonths;

    private BigDecimal price;

    @Column(name = "product_key", unique = true)
    private String productKey;
}
