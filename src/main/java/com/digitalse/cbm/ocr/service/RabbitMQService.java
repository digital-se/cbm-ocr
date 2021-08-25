package com.digitalse.cbm.ocr.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQService {

    public Connection connection;

    @EventListener(ApplicationReadyEvent.class)
    public void startConnection()
            throws IOException, TimeoutException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(3309);
        factory.setUsername("back");
        factory.setPassword("cbm");
        connection = factory.newConnection();
        getMessage();
    }

    public void getMessage()
            throws IOException, TimeoutException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException {
        try (Channel channel = connection.createChannel()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("durable", false);
            channel.exchangeDeclare("digital-se-cbm", "direct", true);
            channel.queueBind("ocr", "digital-se-cbm", "ocr_1");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            };

            channel.basicConsume("ocr", true, deliverCallback, consumerTag -> { });
        }

    }
}
