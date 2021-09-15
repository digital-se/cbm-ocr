package com.digitalse.cbm.ocr.service.utils;


import java.util.ArrayList;
import java.util.List;

import com.digitalse.cbm.ocr.TO.BucketOcrDTO;
import com.digitalse.cbm.ocr.TO.BucketOcrRF;
import com.digitalse.cbm.ocr.service.QueueService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BackHttpConnection {
    @Autowired
    QueueService queueService;

    private String GET_IMAGE_LIST_URL;
    private String GET_IMAGE_DATA_URL;
    private String POST_IMAGE_TEXT_URL;

    private BackHttpConnection(@Value("${back-urn}") String BACK_URN){
        GET_IMAGE_LIST_URL = BACK_URN+"/ocr/imagem/list";
        GET_IMAGE_DATA_URL = BACK_URN+"/ocr/imagem/";
        POST_IMAGE_TEXT_URL = BACK_URN+"/ocr/receberDados";

        System.out.println(GET_IMAGE_LIST_URL);
    }

    public void getListFromBack() throws Exception {
        List<Long> array = new ArrayList<>();
        try {
    
            String jsonString = new RestTemplate().getForObject(GET_IMAGE_LIST_URL, String.class);
            JsonNode jsonNode = new ObjectMapper().readTree(jsonString);

            if (jsonNode.isArray()) {
                for (final JsonNode objNode : jsonNode) {
                    array.add(objNode.asLong());
                }
            }
            queueService.queueAtualizar(array);
            System.out.println(queueService.queue.toString());
        } catch (Exception e) {
            //throw new Exception("Error: "+e.getMessage()+"\n");
        }
        
    }

    public BucketOcrDTO getImageFromBack(Long id_imagem) throws Exception {
        BucketOcrDTO bucketdto;

        try {
            String jsonString = new RestTemplate().getForObject(GET_IMAGE_DATA_URL+id_imagem, String.class);
            bucketdto = new ObjectMapper().readValue(jsonString, BucketOcrDTO.class);

            return bucketdto;
        } catch (Exception e) {
            throw new Exception("Error: "+e.getMessage()+"\n");
        }
    }

    public void postToBack(BucketOcrDTO bucketDTO, String text) throws Exception {
        BucketOcrRF brf = new BucketOcrRF(bucketDTO.getId(), bucketDTO.getArquivo_id(), text);

        try {
            new RestTemplate().postForObject(POST_IMAGE_TEXT_URL, brf, ResponseEntity.class);
            
        } catch (Exception e) {
            throw new Exception("Error: "+e.getMessage()+"\n");
        }

    }
}
