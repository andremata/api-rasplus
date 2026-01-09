package com.client.api.rasplus.integration;

import com.client.api.rasplus.dto.wsraspay.CreditCardDTO;
import com.client.api.rasplus.dto.wsraspay.CustomerDTO;
import com.client.api.rasplus.dto.wsraspay.OrderDTO;
import com.client.api.rasplus.dto.wsraspay.PaymentDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;

@SpringBootTest
public class WsRaspayIntegrationImplTest {

    @Autowired
    private WsRaspayIntegration wsRaspayIntegration;

    @Test
    void createCustomerWhenDtoOK() {
        CustomerDTO dto = new CustomerDTO(null, "andre@email.com", "28773224839", "Andre", "Mata");

        wsRaspayIntegration.createCustomer(dto);
    }

    @Test
    void createOrderWhenDtoOK() {
        OrderDTO dto = new OrderDTO(null,"", BigDecimal.ZERO,"");

        wsRaspayIntegration.createOrder(dto);
    }

    @Test
    void createProcessPaymentWhenDtoOK() {
        CreditCardDTO creditCardDTO = new CreditCardDTO(123L, "", 1L, 12L, "1234567899992514", 2030L);
        PaymentDTO paymentDTO = new PaymentDTO(creditCardDTO, "", "");

        wsRaspayIntegration.processPayment(paymentDTO);
    }
}
