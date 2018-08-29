package com.appinionbd.abc.view.patientCode;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.appinionbd.abc.R;
import com.appinionbd.abc.appUtils.AppUtil;
import com.appinionbd.abc.extraLibrary.barCodeScanner.BarCodeScanner;
import com.appinionbd.abc.extraLibrary.imageLibrary.GlideApp;
import com.appinionbd.abc.interfaces.trackInterface.ITrackPatient;
import com.appinionbd.abc.model.dataHolder.UserInfo;
import com.appinionbd.abc.networking.trackPatient.ApiTrackPatient;
import com.appinionbd.abc.view.capturePicture.BarcodeCaptureActivity;
import com.appinionbd.abc.view.home.HomeActivity;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.FileNotFoundException;
import java.io.InputStream;

import io.realm.Realm;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class PatientCodeActivity extends AppCompatActivity {

    TextInputEditText textInputEditTextPatientCode;
    TextInputEditText textInputEditTextrelationwithpatient;

    ImageView imageViewBarcode;
    CardView cardViewBarcodeScanner;

    Button buttonSave;

    private static int RESULT_LOAD_IMAGE = 1;
    private static final int RC_BARCODE_CAPTURE = 9001;

    private String[] activities = {"Capture Image" , "Open Gallery"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_code);
        Realm.init(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        textInputEditTextPatientCode = findViewById(R.id.textInputEditText_patient_code);
        textInputEditTextrelationwithpatient = findViewById(R.id.textInputEditText_relation_with_patient);
        imageViewBarcode = findViewById(R.id.imageView_barcode);
        cardViewBarcodeScanner = findViewById(R.id.cardView_barcode_scanner);
        cardViewBarcodeScanner.setOnClickListener(v -> showDialogBox());

        buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trackPatient();
            }
        });

    }

    private void trackPatient() {
        Toast.makeText(this , "Loading ...." , Toast.LENGTH_LONG).show();
        String token;
        try(Realm realm =Realm.getDefaultInstance()){
            UserInfo userInfo = realm.where(UserInfo.class).findFirst();
            token = userInfo.getToken();
        }
        if(!textInputEditTextPatientCode.getText().toString().isEmpty())
            ApiTrackPatient.getApiTrackPatient().setApiTrackPatient(textInputEditTextPatientCode.getText().toString(), token, new ITrackPatient() {
                @Override
                public void successful() {
                    gotoHomeMonitorActivity();
                }

                @Override
                public void error() {
                    errorShow();
                }

                @Override
                public void networkFailed() {
                    badNetwork();
                }
            });
        else
            Toast.makeText(this,"Please type or scan an ID !" ,Toast.LENGTH_LONG).show();
    }

    private void badNetwork() {
        Toast.makeText(this , "Network error !" , Toast.LENGTH_LONG).show();
    }

    private void errorShow() {
        Toast.makeText(this , "There is an error !" , Toast.LENGTH_LONG).show();
    }


    private void gotoHomeMonitorActivity() {
        Toast.makeText(this , "Tracking Started !" , Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this , HomeActivity.class);
        intent.putExtra("patientOrMonitor" , "monitor");
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this , HomeActivity.class);
        intent.putExtra("patientOrMonitor" , "monitor");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        super.onBackPressed();
    }

    private void showDialogBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select");
        builder.setItems(activities, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    gotoCaptureFromCamera();
                }
                else if(which == 1){
                    gotoOpenGallery();
                }
            }
        });
        builder.create();
        builder.show();
    }

    private void gotoCaptureFromCamera() {

        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
        intent.putExtra(BarcodeCaptureActivity.UseFlash, false);
        intent.putExtra(BarcodeCaptureActivity.AutoCapture, true);
        startActivityForResult(intent, RC_BARCODE_CAPTURE);
    }

    private void gotoOpenGallery() {

        Intent intentGallery = new Intent();
        intentGallery.setType("image/*");
        intentGallery.setAction(Intent.ACTION_PICK);
//        Intent intentGallery = new Intent(Intent.ACTION_GET_CONTENT , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intentGallery , "Select Picture") , RESULT_LOAD_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AppUtil.log("BarcodeText" , String.valueOf(resultCode));
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS)
            {

                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    Toast.makeText(this, R.string.barcode_success, Toast.LENGTH_LONG).show();
                    textInputEditTextPatientCode.setText(barcode.displayValue);
                    Log.d("BarcodeText", "Barcode read: " + barcode.displayValue);
                }
                else {
                    Toast.makeText(this, R.string.barcode_failure, Toast.LENGTH_LONG).show();
                    Log.d("BarcodeText", "No barcode captured, intent data is null");
                }
            }
            else {
                Toast.makeText(this, R.string.barcode_error + " : " + CommonStatusCodes.getStatusCodeString(resultCode), Toast.LENGTH_LONG).show();
            }
        }
        else if (requestCode == RESULT_LOAD_IMAGE) {
            if (resultCode == RESULT_OK) {
                Uri selectedImageURI = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(selectedImageURI);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                    Bitmap bitmap = BitmapFactory.decodeResource(this.getResources() , R.drawable.barcodeedit);

                    String barCodeText = BarCodeScanner.getBarCodeScanner().getBarCodeOrQrCodeDetector(this, bitmap);
                    textInputEditTextPatientCode.setText(barCodeText);

                    GlideApp.with(this)
                            .load(selectedImageURI)
                            .fitCenter()
                            .override(600, 200)
                            .into(imageViewBarcode);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
