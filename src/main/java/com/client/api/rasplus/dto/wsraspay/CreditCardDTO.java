package com.client.api.rasplus.dto.wsraspay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditCardDTO {

    private Long cvv;
    private String documentNumber;
    private Integer installments;
    private Integer month;
    private String number;
    private Integer year;
}
