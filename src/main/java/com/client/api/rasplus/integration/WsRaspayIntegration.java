package com.client.api.rasplus.integration;

import com.client.api.rasplus.dto.wsraspay.CustomerDTO;
import com.client.api.rasplus.dto.wsraspay.OrderDTO;
import com.client.api.rasplus.dto.wsraspay.PaymentDTO;

public interface WsRaspayIntegration {

    CustomerDTO createCustomer(CustomerDTO dto);

    OrderDTO createOrder(OrderDTO dto);

    Boolean processPayment(PaymentDTO dto);
}
