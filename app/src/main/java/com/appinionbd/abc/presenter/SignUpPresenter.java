package com.appinionbd.abc.presenter;

import com.appinionbd.abc.interfaces.presenterInterface.ISignUp;

public class SignUpPresenter implements ISignUp.Presenter {

    private ISignUp.View view;

    public SignUpPresenter() {
    }

    public SignUpPresenter(ISignUp.View view) {
        this.view = view;
    }

    @Override
    public void signUp(String name, String email, String password, String retypePassword) {
        if(!password.equals(retypePassword))
            view.errorPasswordDidNotMatch();

    }
}
