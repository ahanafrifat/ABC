package com.appinionbd.abc.presenter;

import com.appinionbd.abc.interfaces.presenterInterface.IPatientInfo;

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

        try(Realm realm = Realm.getDefaultInstance()){

        }
    }
}