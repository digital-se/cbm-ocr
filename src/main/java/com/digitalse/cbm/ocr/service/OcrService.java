package com.digitalse.cbm.ocr.service;

import com.digitalse.cbm.ocr.TO.BucketOcrDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OcrService {
    @Autowired
    TesseractService tessService;
    @Autowired
    QueueService queueService;

    public void realizarScan() throws Exception {
        while (!queueService.queue.equals(null) && !queueService.queue.isEmpty()) {
            try {
                BucketOcrDTO bucketdto = queueService.getImage();
                System.out.println("Scanning text on bucket: " + bucketdto.getId());
                String texto = tessService.recognize(bucketdto.getDados());
                queueService.postToBack(bucketdto, texto);
            } catch (Exception e) {
                System.out.println("\tErro ao processar e enviar imagem");
            }
            realizarScan();
        }
        System.out.println("Getting idle...");
    }
}
