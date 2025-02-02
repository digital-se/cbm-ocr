package com.digitalse.cbm.ocr.TO;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BucketOcrDTO implements Serializable {
    private Long id;
    private Long arquivo_id;
    private String nome;
    private String mime;
    private Long tamanho;
    private byte[] dados;
}
