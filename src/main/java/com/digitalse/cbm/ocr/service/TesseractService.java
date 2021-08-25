package com.digitalse.cbm.ocr.service;

import java.awt.image.BufferedImage;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Service
public class TesseractService {
    
    private final String datapath;
    private final String language;
    private final Tesseract tesseract;

    public TesseractService(
        @Value("${tesseract.datapath}") String datapath, 
        @Value("${tesseract.language}") String language) {

        tesseract     = new Tesseract();
        this.language = language;
        this.datapath = Paths.get(datapath).toAbsolutePath().toString();
        
        loadDataSource();
    }

    private void loadDataSource() {
        tesseract.setTessVariable("user_defined_dpi", "300");
        tesseract.setDatapath(datapath);
        tesseract.setLanguage(language);
    }

    public String recognize(BufferedImage image) throws Exception {
        try {
            return tesseract.doOCR(image);
        } catch (TesseractException ex) {
            throw new TesseractException(ex);
        }
    }
}
