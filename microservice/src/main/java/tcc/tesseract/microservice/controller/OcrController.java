package tcc.tesseract.microservice.controller;


import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.sourceforge.tess4j.Tesseract;

@RestController
@RequestMapping("/ocr")
public class OcrController {
    
    @PostMapping("/extrair")    
	public ResponseEntity<String> extrair(@RequestParam(name="file") MultipartFile file) throws Exception{
		
		String resultado = "";
		
		try {
			BufferedImage img = ImageIO.read(file.getInputStream());
			
	        Tesseract tesseract = new Tesseract();
	        
	        tesseract.setDatapath("C:\\Program Files (x86)\\Tesseract-OCR\\tessdata");
	        resultado = "";	     
	        
            tesseract.setLanguage("por");
			resultado = tesseract.doOCR(img);	        
		} catch (IOException e) {
			throw new Exception("Erro na tentativa de ler o arquivo");
		}
		return ResponseEntity.ok(resultado);						
	}

}

