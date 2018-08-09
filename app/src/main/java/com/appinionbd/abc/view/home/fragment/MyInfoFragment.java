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
import android.widget.TextView;

import com.appinionbd.abc.R;
import com.appinionbd.abc.appUtils.AppUtil;
import com.appinionbd.abc.extraLibrary.imageLibrary.GlideApp;
import com.appinionbd.abc.interfaces.presenterInterface.IMyInfo;
import com.appinionbd.abc.model.dataModel.User;
import com.appinionbd.abc.presenter.MyInfoPresenter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyInfoFragment extends Fragment implements IMyInfo.View{

    public final static int WHITE = 0xFFFFFFFF;
    public final static int BLACK = 0xFF000000;
    public final static int WIDTH = 400;
    public final static int HEIGHT = 400;
    public final static String STR = "A string to be encoded as QR code";
    ImageView imageViewBarCode;
    ImageView imageViewMyInfoProfileImage;

    TextView textViewMyInfoName;
    TextView textViewMyInfoProfileId;

    TextView textViewMyInfoEMail;
    TextView textViewMyInfoDateOfBirth;
    TextView textViewMyInfoHeight;
    TextView textViewMyInfoWeight;
    TextView textViewMyInfoGender;
    TextView textViewMyInfoProfileCreatedDate;

    private String id;

    private IMyInfo.Presenter myInfoPresenter;

    public MyInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myInfoPresenter = new MyInfoPresenter(this);
        return inflater.inflate(R.layout.fragment_my_info, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showProfileInfo();
    }

    private void showProfileInfo() {

        imageViewMyInfoProfileImage = getActivity().findViewById(R.id.imageView_my_info_profile_image);
        imageViewBarCode = getActivity().findViewById(R.id.imageView_qrCode);

        textViewMyInfoName = getActivity().findViewById(R.id.textView_my_info_name);
        textViewMyInfoProfileId = getActivity().findViewById(R.id.textView_my_info_profile_id);
        textViewMyInfoEMail = getActivity().findViewById(R.id.textView_my_info_e_mail);
        textViewMyInfoDateOfBirth = getActivity().findViewById(R.id.textView_my_info_date_of_birth);
        textViewMyInfoHeight = getActivity().findViewById(R.id.textView_my_info_height);
        textViewMyInfoWeight = getActivity().findViewById(R.id.textView_my_info_weight);
        textViewMyInfoGender = getActivity().findViewById(R.id.textView_my_info_gender);

        myInfoPresenter.dataFromRealm();

    }


    @Override
    public void showMyInfo(String id, String name, String email, String dateOfBirth, String height, String weight, String gender) {

        GlideApp.with(getActivity()).load(R.drawable.profile_1).circleCrop().into(imageViewMyInfoProfileImage);


        textViewMyInfoName.setText(name);
        textViewMyInfoProfileId.setText(id);
        textViewMyInfoEMail.setText(email);
        textViewMyInfoDateOfBirth.setText(dateOfBirth);
        textViewMyInfoHeight.setText(height);
        textViewMyInfoWeight.setText(weight);
        textViewMyInfoGender.setText(gender);

        Bitmap bitmap = null;
        try {
            bitmap = AppUtil.encodeAsBitmap(id);
            imageViewBarCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }
}








