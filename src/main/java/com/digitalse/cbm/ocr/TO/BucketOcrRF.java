package com.digitalse.cbm.ocr.TO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BucketOcrRF {
    private Long id;
    private Long arquivo_id;
    private String texto;
}
