package com.digitalse.cbm.ocr.controller;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import com.digitalse.cbm.ocr.utils.TokenExtract;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@RestController
@RequestMapping("/ocr")
public class OcrController {

	@ApiOperation(value = "Retorna texto lido na imagem enviada")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Retornou o texto contido na imagem"),
            @ApiResponse(code = 404, message = "Não encontrado"),
			@ApiResponse(code = 405, message = "GET não suportado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção") })
	@PostMapping("/extrair")
	public ResponseEntity<String> extrair(@RequestParam(name = "file") MultipartFile file) throws Exception {
		BufferedImage img = ImageIO.read(file.getInputStream());
    	ITesseract tess4j = new Tesseract();

		tess4j.setTessVariable("user_defined_dpi", "300");
		tess4j.setDatapath("src/main/resources/tess/tessdata");
		tess4j.setLanguage("por");

		// try {
		// 	resultado = tess4j.doOCR(img);
		// } catch (TesseractException e) {
		// 	throw new Exception("Erro na tentativa de ler o arquivo");
		// }

		try {
            String result = tess4j.doOCR(img);
            List<String> listResult = Arrays.asList(TokenExtract.token(result));
            //Identico ao for abaixo
            // String res[] = te.token(result);
            //Arrays.stream(res).forEach(r -> {System.out.println(r);});
            listResult.forEach(r -> {System.out.println(r);});
            for(String r: listResult) {
            	System.out.println(r);
            }
			return ResponseEntity.ok(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


		
	}

}
