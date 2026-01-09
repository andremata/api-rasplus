package com.client.api.rasplus.integration.impl;

import com.client.api.rasplus.dto.wsraspay.CustomerDTO;
import com.client.api.rasplus.dto.wsraspay.OrderDTO;
import com.client.api.rasplus.dto.wsraspay.PaymentDTO;
import com.client.api.rasplus.integration.WsRaspayIntegration;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WsRaspayIntegrationImpl implements WsRaspayIntegration {

    @Value("${ws.raspay.host}")
    private String raspayHost;

    private final RestTemplate restTemplate;
    private final HttpHeaders headers;

    public WsRaspayIntegrationImpl() {
        restTemplate = new RestTemplate();
        headers = getHttpHeaders();
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO dto) {
        try {
            HttpEntity<CustomerDTO> request = new HttpEntity<>(dto, this.headers);
            ResponseEntity<CustomerDTO> response =
                    restTemplate.exchange(raspayHost + "v1/customer",
                            HttpMethod.POST,
                            request,
                            CustomerDTO.class);

            return response.getBody();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OrderDTO createOrder(OrderDTO dto) {
        try {
            HttpEntity<OrderDTO> request = new HttpEntity<>(dto, this.headers);
            ResponseEntity<OrderDTO> response =
                    restTemplate.exchange(raspayHost + "v1/order",
                            HttpMethod.POST,
                            request,
                            OrderDTO.class);

            return response.getBody();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean processPayment(PaymentDTO dto) {
        try {
            HttpEntity<PaymentDTO> request = new HttpEntity<>(dto, this.headers);
            ResponseEntity<Boolean> response =
                    restTemplate.exchange(raspayHost + "v1/payment/credit-card",
                            HttpMethod.POST,
                            request,
                            Boolean.class);

            return response.getBody();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static HttpHeaders getHttpHeaders() {
        String credential = "rasmooplus:r@sm00";
//        String base64 = new String (Base64.encodeBase64(credential.getBytes()));
        String base64 = "";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64);

        return headers;
    }
}