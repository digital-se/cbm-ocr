package com.digitalse.cbm.ocr.controller;

import javax.imageio.ImageIO;

import com.digitalse.cbm.ocr.service.TesseractService;
import java.awt.image.BufferedImage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/ocr")
public class OcrController {

	@Autowired
	private TesseractService tessService;

	@ApiOperation(value = "Retorna texto lido na imagem enviada")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Retornou o texto contido na imagem"),
            @ApiResponse(code = 404, message = "Não encontrado"),
			@ApiResponse(code = 405, message = "GET não suportado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção") })
	@PostMapping("/extrair")
	public ResponseEntity<String> extrair(@RequestParam(name = "file") MultipartFile file) throws Exception {
		BufferedImage img = ImageIO.read(file.getInputStream());
		String result = tessService.recognize(img);
		return ResponseEntity.ok(result);
	}

}
