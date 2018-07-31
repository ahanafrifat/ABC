package com.appinionbd.abc.barCodeScanner;

import com.appinionbd.abc.camera.GraphicOverlay;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

public class BarcodeTrackerFactory implements MultiProcessor.Factory<Barcode> {
    private BarcodeGraphicTracker.BarcodeDetectorListener mDetectorListener;
    private GraphicOverlay<BarcodeGraphic> mGraphicOverlay;

    public BarcodeTrackerFactory(GraphicOverlay<BarcodeGraphic> barcodeGraphicOverlay, BarcodeGraphicTracker.BarcodeDetectorListener detectorListener) {
        mGraphicOverlay = barcodeGraphicOverlay;
        mDetectorListener = detectorListener;
    }

    @Override
    public Tracker<Barcode> create(Barcode barcode) {
        BarcodeGraphic graphic = new BarcodeGraphic(mGraphicOverlay);
        BarcodeGraphicTracker graphicTracker = new BarcodeGraphicTracker(mGraphicOverlay, graphic);
        if(mDetectorListener != null)
            graphicTracker.setDetectorListener(mDetectorListener);
        return graphicTracker;
    }
}
