package com.digitalse.cbm.ocr.service;

import java.util.LinkedList;
import java.util.List;

import com.digitalse.cbm.ocr.TO.BucketOcrDTO;
import com.digitalse.cbm.ocr.service.utils.BackHttpConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueueService {
    @Autowired
    OcrService ocrService;
    @Autowired
    BackHttpConnection backConnection;

    public LinkedList<Long> queue;

    public QueueService() {
        queue = new LinkedList<Long>();
    }

    public void queueAtualizar(List<Long> lista) throws Exception {
        try {
            queue.clear();
            queue.addAll(lista);
            System.out.println("Queue atualizada: " + queue.toString());
            ocrService.realizarScan();
        } catch (Exception e) {
            //throw new Exception("Error: " + e.getMessage() + "\n");
        }
    }

    public BucketOcrDTO getImage() throws Exception {
        return backConnection.getImageFromBack(queue.pop());
    }

    public void receiveIdFromBack(Long id_imagem) throws Exception {
        queue.add(id_imagem);
        ocrService.realizarScan();
    }

}
