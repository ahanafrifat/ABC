package com.appinionbd.abc.interfaces.presenterInterface;

public interface IMyInfo {
    interface View{
        void showMyInfo(String id, String name, String email, String dateOfBirth, String height, String weight, String gender);
    }

    interface Presenter{
        void dataFromRealm();
    }
}
