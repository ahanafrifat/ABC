package com.appinionbd.abc.barCodeScanner;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

public class BarCodeScanner {

    private static BarCodeScanner barCodeScanner;

    private BarCodeScanner() {

    }

    public static BarCodeScanner getBarCodeScanner() {
        if(barCodeScanner == null)
            barCodeScanner= new BarCodeScanner();
        return barCodeScanner;
    }

    public String getBarCodeOrQrCodeDetector(Context context , Bitmap bitmap) {

        String barcodeString = "";
        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(context)
                .build();

        if(barcodeDetector.isOperational()){
            SparseArray<Barcode> sparseArray = barcodeDetector.detect(frame);
            if(sparseArray != null && sparseArray.size() > 0){
                for (int i = 0; i < sparseArray.size(); i++){
                    Log.d("BarCodeScanner", "Value: " + sparseArray.valueAt(i).rawValue + "----" + sparseArray.valueAt(i).displayValue);
                    barcodeString = sparseArray.valueAt(i).rawValue ;
                }
            }else {
                barcodeString = "SparseArray null or empty";
                Log.e("BarCodeScanner","SparseArray null or empty");
            }

        }else{
            barcodeString = "Detector dependencies are not yet downloaded";
            Log.e("BarCodeScanner", "Detector dependencies are not yet downloaded");
        }
        return barcodeString;
    }
}
