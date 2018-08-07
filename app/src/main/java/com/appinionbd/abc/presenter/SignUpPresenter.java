package com.appinionbd.abc.presenter;

import com.appinionbd.abc.interfaces.createInterface.ICreate;
import com.appinionbd.abc.interfaces.presenterInterface.ISignUp;
import com.appinionbd.abc.networking.signUpApi.ApiSignUp;

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
        else
            ApiSignUp.getApiSignUp().setApiSignUp(name, email, password, new ICreate() {
                @Override
                public void successful(String message) {
                    view.successful(message);
                }

                @Override
                public void noNewInfo(String message) {
                    view.alreadyAccountCreated(message);
                }

                @Override
                public void unAuthorized(String message) {

                }

                @Override
                public void error(String message) {
                    view.signUpError(message);
                }

                @Override
                public void connectionProblem(String message) {
                    view.networkProblem(message);
                }
            });

    }
}
