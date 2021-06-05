package com.digitalse.cbm.ocr.utils;

import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.Mat;
import org.springframework.web.multipart.MultipartFile;
import java.awt.image.BufferedImage;

public class ProProcessor {
    
    public static void testes(MultipartFile img){

    }


    public static Mat bufferedImageToMat(BufferedImage bi) {
        OpenCVFrameConverter.ToMat cv = new OpenCVFrameConverter.ToMat();
        return cv.convertToMat(new Java2DFrameConverter().convert(bi)); 
    }

    public static BufferedImage matToBufferedImage(Mat frame) {       
        int type = 0;
        if (frame.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if (frame.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        BufferedImage image = new BufferedImage(frame.arrayWidth(),frame.arrayHeight(), type);
        return image;
    }
}
