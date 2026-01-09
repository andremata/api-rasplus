package com.client.api.rasplus.integration;

import com.client.api.rasplus.integration.impl.MailIntegrationImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WsMailIntegrationImplTest {

    @Autowired
    private MailIntegrationImpl mailIntegration;

    @Test
    void sendMailOk() {
        mailIntegration.send("andrermata@gmail.com","Olá, isso é um teste");
    }
}
