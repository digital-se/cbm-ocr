package com.digitalse.cbm.ocr.service;

import java.io.IOException;

import com.digitalse.cbm.rabbitmqtransferenceobjects.RTOBucket;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class RabbitmqService {
    
    @Autowired
    private TesseractService ts;

    @Autowired
    private RabbitTemplate rt;

    public void sendImage(RTOBucket rtoBucket) throws AmqpException, IOException {
        rt.convertAndSend("back", rtoBucket);
    }

    @RabbitListener(queues = "ocr")
    public RTOBucket listener(RTOBucket rtoBucket) throws Exception {
        byte[] imagem_processada = rtoBucket.getDados_processados();
        String texto = ts.recognize(imagem_processada);
        rtoBucket.setTexto(texto);
        rtoBucket.setDados_processados(null);
        return rtoBucket;
    }
}
