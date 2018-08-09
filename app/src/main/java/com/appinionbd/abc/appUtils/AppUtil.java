package com.appinionbd.abc.appUtils;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class AppUtil {
    public static void log (String tag , String msg){
        Log.d(tag , msg);
    }

    public static Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix bitMatrix;
        int WHITE = 0xFFFFFFFF;
        int BLACK = 0xFF000000;
        int WIDTH = 400;
        int HEIGHT = 400;

        try {

            bitMatrix = new MultiFormatWriter().encode(str , BarcodeFormat.QR_CODE , WIDTH , HEIGHT , null);

        } catch (WriterException e) {
            e.printStackTrace();
            bitMatrix = null;
        }
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int[] pixels = new int[width * height];
        for(int y =0 ; y < height ; y++){
            int offset = y * width;
            for(int x =0 ; x < width ; x++){
                pixels[offset + x] = bitMatrix.get(x , y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width , height , Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels , 0 , width , 0 , 0 , width ,height);

        return bitmap;
    }
}
