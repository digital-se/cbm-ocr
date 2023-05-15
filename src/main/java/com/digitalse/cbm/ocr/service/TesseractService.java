package com.digitalse.cbm.ocr.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Service
public class TesseractService {
    private final String datapath;
    private final String language;
    private final Tesseract tesseract;

    public TesseractService(@Value("${tesseract.datapath}") String datapath,
            @Value("${tesseract.language}") String language) {

        tesseract = new Tesseract();
        this.language = language;
        this.datapath = Paths.get(datapath).toAbsolutePath().toString();

        loadDataSource();
    }

    private void loadDataSource() {
        tesseract.setTessVariable("user_defined_dpi", "300");
        tesseract.setDatapath(datapath);
        tesseract.setLanguage(language);
    }

    public String recognize(byte[] dadosImagem) throws Exception {
        try {
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(dadosImagem));
            String texto = tesseract.doOCR(img);
            return texto;
        } catch (TesseractException ex) {
            throw new TesseractException(ex);
        }
    }

    

    
}
