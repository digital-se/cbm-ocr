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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQService {

    public Connection connection;

    @EventListener(ApplicationReadyEvent.class)
    public void startConnection(@Value("${rabbit.host}") String host, @Value("${rabbit.port}") int port,
            @Value("${rabbit.username}") String username, @Value("${rabbit.password}") String password)
            throws IOException, TimeoutException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
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
