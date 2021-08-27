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

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessagingService {

    /* public final Connection connection; */

    /* @Autowired
    public MessagingService(@Value("${rabbitmq.host}") String host, @Value("${rabbitmq.port}") int port,
            @Value("${rabbitmq.username}") String username, @Value("${rabbitmq.password}") String password)
            throws IOException, TimeoutException, KeyManagementException, NoSuchAlgorithmException, URISyntaxException {
        System.out.println("======== " + host + "======== ");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        this.connection = factory.newConnection();
        
    } */

    /* @RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "ocr", durable = "true") , 
        exchange = @Exchange(value = "digital-se-cbm"), 
        key = "ocr_1") )
    public void getMessage(Message message) {
            System.out.println("Received: " + message);
            /* String teste;
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("durable", false);
            channel.exchangeDeclare("digital-se-cbm", "direct", true);
            channel.queueBind("ocr", "digital-se-cbm", "ocr_1");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                
            };

            channel.basicConsume("ocr", true, deliverCallback, consumerTag -> { });
            /* System.out.println(result); */
            //RFBucketRabbitMQ itemWithOwner = new ObjectMapper().readValue(result, RFBucketRabbitMQ.class); */
        

    /*} */
}
