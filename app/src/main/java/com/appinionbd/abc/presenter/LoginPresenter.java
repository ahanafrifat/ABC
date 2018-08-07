package com.appinionbd.abc.presenter;

import com.appinionbd.abc.appUtils.AppUtil;
import com.appinionbd.abc.interfaces.networkInterface.IAuthentication;
import com.appinionbd.abc.interfaces.presenterInterface.ILogin;
import com.appinionbd.abc.model.dataHolder.UserInfo;
import com.appinionbd.abc.model.dataModel.User;
import com.appinionbd.abc.networking.authentication.LoginAuthentication;

import io.realm.Realm;
import io.realm.RealmResults;

public class LoginPresenter implements ILogin.Presenter {
    private ILogin.View view;

    public LoginPresenter() {
    }

    public LoginPresenter(ILogin.View view) {
        this.view = view;
    }

    @Override
    public void loginVerification(String userName, String password) {
        if(userName.isEmpty()){
            view.emptyUserName();
        }
        else if(password.isEmpty()){
            view.emptyPassword();
        }
        else{
            LoginAuthentication.getAuthentication().loginAuthentication(userName, password, new IAuthentication() {
                @Override
                public void successful() {
                    view.successful();
                }

                @Override
                public void wrongUserNameOrPassword(String message) {
                    view.wrongUsernameOrPassword(message);
                }

                @Override
                public void authError(String message) {
                    view.loginError(message);
                }

                @Override
                public void authConnectionError(String message) {
                    view.loginError(message);
                }

                @Override
                public void loginAuthError(String message) {
                    view.loginError(message);
                }

                @Override
                public void loginAuthConnectionError(String message) {
                    view.loginError(message);
                }

                @Override
                public void userInfoError(String message) {
                    view.loginError(message);
                }

                @Override
                public void userInfoConnectionError(String message) {
                    view.loginError(message);
                }
            });
        }
    }

    @Override
    public void checkUserExist() {
        int userSize = 0;
        int userInfoSize = 0;
        try(Realm realm = Realm.getDefaultInstance()){
            RealmResults<User> userRealmResults = realm.where(User.class).findAll();
            AppUtil.log("LoginPresenter" , "Total number of users : " + userRealmResults.size());

            userSize = userRealmResults.size();

            RealmResults<UserInfo> userInfoRealmResults = realm.where(UserInfo.class).findAll();
            AppUtil.log("LoginPresenter" , "Total number of users : " + userInfoRealmResults.size());

            userInfoSize = userInfoRealmResults.size();
        }

        if(userSize == 0 && userInfoSize == 0){
            view.emptyUser();
        }
        else
            view.userExist();
    }
}












