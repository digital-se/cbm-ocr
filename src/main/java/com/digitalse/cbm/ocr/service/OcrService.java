package com.digitalse.cbm.ocr.service;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Service
public class OcrService {
   
    public String ocrScanImage(MultipartFile image) throws Exception{
        String resultado;
        BufferedImage img = ImageIO.read(image.getInputStream());

		ITesseract tesseract = new Tesseract();

		tesseract.setDatapath("src/main/resources/tess/tessdata");
		tesseract.setLanguage("por");

		try {
			resultado = tesseract.doOCR(img);
		} catch (TesseractException e) {
			throw new Exception("Erro na tentativa de ler o arquivo");
		}
        return resultado;
    }
}
