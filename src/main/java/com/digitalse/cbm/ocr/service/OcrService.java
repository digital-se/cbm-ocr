package com.digitalse.cbm.ocr.service;

import com.digitalse.cbm.ocr.TO.BucketOcrDTO;
import com.digitalse.cbm.ocr.service.utils.BackHttpConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OcrService {
    @Autowired
    TesseractService tessService;
    @Autowired
    QueueService queueService;
    @Autowired
    BackHttpConnection backConnection;

    public void realizarScan() throws Exception {
        while (!queueService.queue.equals(null) && !queueService.queue.isEmpty()) {
            try {
                BucketOcrDTO bucketdto = queueService.getImage();
                System.out.println("Scanning text on bucket: " + bucketdto.getId());
                String texto = tessService.recognize(bucketdto.getDados());
                backConnection.postToBack(bucketdto, texto);
                realizarScan();
            } catch (Exception e) {
                throw new Exception("Error: "+e.getMessage()+"\n");
            }
            
        }
        System.out.println("Getting idle...");
    }
}
