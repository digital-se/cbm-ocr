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
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping
public class OcrController {

	@Autowired
	private QueueService queueService;

	@PostMapping("/ocr/queue/atualizar")
	public ResponseEntity<String> queueAtualizarQueue(@RequestBody List<Long> ids) {
		try {
			System.out.println("Ids recebidos... " + ids.toString());
			queueService.queueAtualizar(ids);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (NullPointerException e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
		}
	}

}
