package com.appinionbd.abc.presenter;

import com.appinionbd.abc.interfaces.presenterInterface.IMyInfo;
import com.appinionbd.abc.model.dataModel.User;

import io.realm.Realm;

public class MyInfoPresenter implements IMyInfo.Presenter {
    private IMyInfo.View view;

    public MyInfoPresenter() {
    }

    public MyInfoPresenter(IMyInfo.View view) {
        this.view = view;
    }

    @Override
    public void dataFromRealm() {

        String id;
        String name;
        String email;
        String dateOfBirth;
        String height;
        String weight;
        String gender;

        try(Realm realm = Realm.getDefaultInstance()){
            User user = realm.where(User.class).findFirst();

            id = user.getUserId();
            name = user.getUserName();
            email = user.getUserEmail();
            dateOfBirth = user.getDob();
            height = user.getHeight();
            weight = user.getWeight();
            gender = user.getGender();
        }

        view.showMyInfo(id , name , email , dateOfBirth , height , weight , gender);
    }
}
