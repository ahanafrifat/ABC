package com.appinionbd.abc.interfaces.presenterInterface;

import android.graphics.Bitmap;

public interface ITrackMe {
    interface View{
        void showQR(Bitmap bitmap);
    }
    interface Presenter{
        void generateQR();
    }
}
