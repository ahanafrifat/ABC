package com.appinionbd.abc.interfaces.presenterInterface;

public interface IPersonalInformation {
    interface View{
        void successfullyUploaded();
        void noNewInfoToUpload(String message);
        void unAuthorizeAccess(String message);
        void networkProblem(String message);
    }
    interface Presenter{
        void uploadAndSavePersonalInformation(String dob, String feet, String inches, String kg, boolean checked);
    }
}
