package com.digitalse.cbm.ocr.controller;

import java.util.List;

import com.digitalse.cbm.ocr.service.QueueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ocr")
public class OcrController {

	@Autowired
	private QueueService queueService;

	@PostMapping("/queue/atualizar")
	public ResponseEntity<String> queueAtualizarQueue(@RequestBody List<Long> ids) throws Exception {
		System.out.println("Ids recebidos... "+ids);
		queueService.queueAtualizar(ids);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
