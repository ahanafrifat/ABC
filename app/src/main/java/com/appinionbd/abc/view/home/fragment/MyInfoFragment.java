package com.appinionbd.abc.view.home.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appinionbd.abc.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyInfoFragment extends Fragment {

    public final static int WHITE = 0xFFFFFFFF;
    public final static int BLACK = 0xFF000000;
    public final static int WIDTH = 400;
    public final static int HEIGHT = 400;
    public final static String STR = "A string to be encoded as QR code";
    ImageView imageViewBarCode;

    public MyInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_info, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showProfileInfo();
    }

    private void showProfileInfo() {
        imageViewBarCode = getActivity().findViewById(R.id.imageView_qrCode);
        try {
            Bitmap bitmap = encodeAsBitmap(STR);
            imageViewBarCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix bitMatrix;
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








