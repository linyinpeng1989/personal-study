package com.linyp.weixin.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by linyp on 2017/5/3.
 */
public class QRCodeUtil {
    /**
     * 根据路径生成对应的二维码图片
     * @param url
     */
    public static BufferedImage generateQrcode(String url) throws WriterException, IOException {
        int width = 100;
        int height = 100;
        Map hints= new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height,hints);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

}
