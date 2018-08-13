package com.appinionbd.abc.interfaces.presenterInterface;

public interface IPatientInfo {
    interface View{

    }

    interface Presenter{
        void getInfo(String patientId);
    }
}
