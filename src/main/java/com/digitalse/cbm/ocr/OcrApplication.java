package com.digitalse.cbm.ocr;

import java.io.IOException;

import javax.annotation.PostConstruct;

import com.digitalse.cbm.ocr.service.OcrService;
import com.digitalse.cbm.ocr.service.QueueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class OcrApplication {

	@Autowired
	QueueService qs = new QueueService();
	@Autowired
	OcrService os;

	public static void main(String[] args) {
		SpringApplication.run(OcrApplication.class, args);
	}

	@PostConstruct
	public void updateOcr() throws Exception {
		qs.getListFromBack();
	}
}
