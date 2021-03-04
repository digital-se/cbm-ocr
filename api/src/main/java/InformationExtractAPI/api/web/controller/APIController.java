package InformationExtractAPI.api.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.TesseractException;
import preprocessing.core.PreProcessing;
import preprocessing.core.ProcessingOptions;

@RestController
@RequestMapping("/process")
@Slf4j
public class APIController {
	@PostMapping(value = "", produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] enqueue(@RequestParam MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) throws IllegalStateException, IOException, TesseractException {
		File arquivo = new File("C:\\Users\\werli\\OneDrive\\Imagens\\imagens\\bgoBombeiro.jpg");
		File arquivo2 = new File("C:\\pastaSaida\\saida2.jpg");

		file.transferTo(arquivo);

		ProcessingOptions options = new ProcessingOptions();
		PreProcessing.processNewImage(arquivo, arquivo2, options);

		InputStream is =  new FileInputStream(arquivo2);
		return IOUtils.toByteArray(is);
	}
}
