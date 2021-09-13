package com.digitalse.cbm.ocr.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.digitalse.cbm.ocr.TO.BucketOcrDTO;
import com.digitalse.cbm.ocr.TO.BucketOcrRF;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QueueService {
    @Autowired
    OcrService ocrService;

    public LinkedList<Long> queue;

    public QueueService() {
        queue = new LinkedList<Long>();
    }

    public void queueAtualizar(List<Long> lista) throws Exception {
        queue.clear();
        queue.addAll(lista);
        System.out.println("Queue atualizada: " + queue.toString());
        ocrService.realizarScan();
    }

    public BucketOcrDTO getImage() throws Exception {
        return getImageFromBack(queue.pop());
    }

    public void receiveIdFromBack(Long id_imagem) throws Exception {
        queue.add(id_imagem);
        ocrService.realizarScan();
    }

    public void getListFromBack() throws Exception {
        List<Long> array = new ArrayList<>();
        try {
            String url = "http://localhost:8082/ocr/imagem/list";
            String jsonString = new RestTemplate().getForObject(url, String.class);
            JsonNode jsonNode = new ObjectMapper().readTree(jsonString);

            if (jsonNode.isArray()) {
                for (final JsonNode objNode : jsonNode) {
                    array.add(objNode.asLong());
                }
            }
        } catch (Exception e) {
            System.out.println("Back-end connection failed");
        }
        queueAtualizar(array);
        System.out.println(queue.toString());
    }

    public BucketOcrDTO getImageFromBack(Long id_imagem) throws Exception {
        BucketOcrDTO bucketdto;
        String url = "http://localhost:8082/ocr/imagem/" + id_imagem;
        URL UrlObj = new URL(url);
        int responseCode;

        try {
            HttpURLConnection connection = (HttpURLConnection) UrlObj.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                // JsonNode jsonNode = new ObjectMapper().readTree(response.toString());
                bucketdto = new ObjectMapper().readValue(response.toString(), BucketOcrDTO.class);

                return bucketdto;
            } else {
                throw new Exception("Connection Error");
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public void postToBack(BucketOcrDTO bucketDTO, String text) throws Exception {
        BucketOcrRF brf = new BucketOcrRF(bucketDTO.getId(), bucketDTO.getArquivo_id(), text);
        String url = "http://localhost:8082/ocr/receberDados";
        URL UrlObj = new URL(url);

        try {
            HttpURLConnection connection = (HttpURLConnection) UrlObj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {

                byte[] input = new ObjectMapper().writeValueAsBytes(brf);
                outputStream.write(input, 0, input.length);
            }

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new Exception("Connection Error");
            }
        } catch (Exception e) {
            throw new Exception(e);
        }

    }

}
