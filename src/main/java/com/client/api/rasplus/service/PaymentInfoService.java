package com.client.api.rasplus.service;

import com.client.api.rasplus.dto.PaymentProcessDTO;

public interface PaymentInfoService {

    Boolean process(PaymentProcessDTO dto);
}
