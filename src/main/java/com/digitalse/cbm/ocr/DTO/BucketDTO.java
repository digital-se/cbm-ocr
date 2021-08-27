package com.digitalse.cbm.ocr.DTO;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BucketDTO {

    private Long id;
    private String mime;
    private Long tamanho;
    private byte[] dados;
    
}
