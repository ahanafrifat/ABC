package com.appinionbd.abc.presenter;

import com.appinionbd.abc.interfaces.networkInterface.IAuthentication;
import com.appinionbd.abc.interfaces.presenterInterface.ILogin;
import com.appinionbd.abc.networking.authentication.LoginAuthentication;

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
                public void authFailed() {

                }

                @Override
                public void wrongUserNameOrPassword() {

                }

                @Override
                public void connectionError() {

                }
            });
        }
    }
}
