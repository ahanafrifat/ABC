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
import com.appinionbd.abc.interfaces.presenterInterface.ITrackMe;
import com.appinionbd.abc.presenter.TrackMePresenter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrackMeFragment extends Fragment implements ITrackMe.View{

    private ImageView imageViewTrackMyQr;
    
    private ITrackMe.Presenter trackMePresenter;

    public TrackMeFragment() {
        // Required empty public constructor
        
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_track_me, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showTrackQRCode();
    }

    private void showTrackQRCode() {
        trackMePresenter = new TrackMePresenter(this);
        imageViewTrackMyQr = getActivity().findViewById(R.id.imageView_track_my_qr);
        trackMePresenter.generateQR();
    }

    @Override
    public void showQR(Bitmap bitmap) {
        imageViewTrackMyQr.setImageBitmap(bitmap);
    }
}
