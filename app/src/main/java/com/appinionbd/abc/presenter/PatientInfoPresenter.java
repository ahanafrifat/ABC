package com.appinionbd.abc.presenter;

import com.appinionbd.abc.interfaces.presenterInterface.IPatientInfo;
import com.appinionbd.abc.model.dataModel.User;

import io.realm.Realm;

public class PatientInfoPresenter implements IPatientInfo.Presenter {

    private IPatientInfo.View view;

    public PatientInfoPresenter() {
    }

    public PatientInfoPresenter(IPatientInfo.View view) {
        this.view = view;
    }

    @Override
    public void getInfo(String patientId) {

        String name;
        String email;
        String dob;
        String height;
        String weight;
        String gender;

        try(Realm realm = Realm.getDefaultInstance()){
            User user = realm.where(User.class).findFirst();
            name = user.getUserName();
            dob = user.getDob();
            height = user.getHeight();
            weight = user.getWeight();
            gender = user.getGender();
        }


    }
}












