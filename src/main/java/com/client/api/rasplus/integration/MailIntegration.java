package com.client.api.rasplus.integration;

public interface MailIntegration {

    void send(String mailTo, String message, String subject);
}
