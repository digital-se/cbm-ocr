package InformationExtractAPI.api;

import java.io.File;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import preprocessing.core.PreProcessing;

@SpringBootApplication
public class SpringBootRestApiApplication {
    public static void main(String[] args) throws IOException {
    	PreProcessing.setFilesPath(new File("C:\\Users\\werli\\projects\\tess"));
    	
        SpringApplication.run(SpringBootRestApiApplication.class, args);
    }
}
