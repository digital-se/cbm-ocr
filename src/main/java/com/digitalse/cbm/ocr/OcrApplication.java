package com.digitalse.cbm.ocr;

import javax.annotation.PostConstruct;

import com.digitalse.cbm.ocr.service.utils.BackHttpConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class OcrApplication {

	@Autowired
	BackHttpConnection backConnection;


	public static void main(String[] args) {
		SpringApplication.run(OcrApplication.class, args);
	}

	@PostConstruct
	public void updateOcr() throws Exception {
		backConnection.getListFromBack();
	}
}
