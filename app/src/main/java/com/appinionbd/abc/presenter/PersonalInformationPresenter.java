package com.appinionbd.abc.presenter;

import com.appinionbd.abc.interfaces.networkInterface.IUpdatePersonalInformation;
import com.appinionbd.abc.interfaces.presenterInterface.IPersonalInformation;
import com.appinionbd.abc.model.dataHolder.UserInfo;
import com.appinionbd.abc.model.dataModel.UpdatePersonalInfoBody;
import com.appinionbd.abc.model.dataModel.User;
import com.appinionbd.abc.networking.updatePersonalInformation.ApiUpdatePersonalInformation;

import io.realm.Realm;

public class PersonalInformationPresenter implements IPersonalInformation.Presenter{
    private IPersonalInformation.View view;

    public PersonalInformationPresenter() {
    }

    public PersonalInformationPresenter(IPersonalInformation.View view) {
        this.view = view;
    }

    @Override
    public void uploadAndSavePersonalInformation(String dob, String feet, String inches, String kg, boolean checked) {
        User saveUser = new User();
        UserInfo saveUserInfo = new UserInfo();
        UpdatePersonalInfoBody updatePersonalInfoBody = new UpdatePersonalInfoBody();

        int FEET = Integer.parseInt(feet) *12 ;
        int totalFeet = FEET + Integer.parseInt(inches);
        try(Realm realm = Realm.getDefaultInstance()){
            User user = realm.where(User.class).findFirst();
            UserInfo userInfo = realm.where(UserInfo.class).findFirst();

            saveUser.setUserId(user.getUserId());
            saveUser.setUserName(user.getUserName());
            saveUser.setUserEmail(user.getUserEmail());
            saveUser.setDob(dob);
            saveUser.setHeight(String.valueOf(totalFeet));
            saveUser.setWeight(kg);
            if(checked)
                saveUser.setGender("1");
            else
                saveUser.setGender("0");

            saveUserInfo.setUserId(userInfo.getUserId());
            saveUserInfo.setToken(userInfo.getToken());

            updatePersonalInfoBody.setDob(dob);
            updatePersonalInfoBody.setHeight(totalFeet);
            updatePersonalInfoBody.setWeight(Integer.valueOf(kg));
            if(checked)
                updatePersonalInfoBody.setGender(1);
            else
                updatePersonalInfoBody.setGender(0);

            realm.executeTransaction(realm1 -> realm1.insertOrUpdate(saveUser));
        }

        ApiUpdatePersonalInformation.getApiUpdatePersonalInformation().setApiUpdatePersonalInformation(saveUserInfo.getToken(),
                updatePersonalInfoBody, new IUpdatePersonalInformation() {
                    @Override
                    public void successful() {
                        view.successfullyUploaded();
                    }

                    @Override
                    public void noNewInfo(String message) {
                        view.noNewInfoToUpload(message);
                    }

                    @Override
                    public void unAuthorized(String message) {
                        view.unAuthorizeAccess(message);
                    }

                    @Override
                    public void connectionProblem(String message) {
                        view.networkProblem(message);
                    }
                });
    }

}
