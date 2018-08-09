package com.appinionbd.abc.presenter;

import android.graphics.Bitmap;

import com.appinionbd.abc.appUtils.AppUtil;
import com.appinionbd.abc.interfaces.presenterInterface.ITrackMe;
import com.appinionbd.abc.model.dataModel.User;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import io.realm.Realm;

public class TrackMePresenter implements ITrackMe.Presenter {
    private ITrackMe.View view;

    public TrackMePresenter() {
    }

    public TrackMePresenter(ITrackMe.View view) {
        this.view = view;
    }

    @Override
    public void generateQR() {
        try {
            String id;
            try(Realm realm = Realm.getDefaultInstance()){
                User user = realm.where(User.class).findFirst();
                id = user.getUserId();

                view.showQR(AppUtil.encodeAsBitmap(id) , id);
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

//    private void encodeAsBitmap(String str) throws WriterException {
//        BitMatrix bitMatrix;
//        int WHITE = 0xFFFFFFFF;
//        int BLACK = 0xFF000000;
//        int WIDTH = 400;
//        int HEIGHT = 400;
//
//        try {
//            bitMatrix = new MultiFormatWriter().encode(str , BarcodeFormat.QR_CODE , WIDTH , HEIGHT , null);
//        } catch (WriterException e) {
//            e.printStackTrace();
//            bitMatrix = null;
//        }
//        int width = bitMatrix.getWidth();
//        int height = bitMatrix.getHeight();
//        int[] pixels = new int[width * height];
//        for(int y =0 ; y < height ; y++){
//            int offset = y * width;
//            for(int x =0 ; x < width ; x++){
//                pixels[offset + x] = bitMatrix.get(x , y) ? BLACK : WHITE;
//            }
//        }
//
//        Bitmap bitmap = Bitmap.createBitmap(width , height , Bitmap.Config.ARGB_8888);
//        bitmap.setPixels(pixels , 0 , width , 0 , 0 , width ,height);
//
//        view.showQR(bitmap);
//    }
}
