package com.appinionbd.abc.interfaces.presenterInterface;

public interface ISignUp {
    interface View{
        void successful(String message);
        void errorPasswordDidNotMatch();
        void networkProblem(String message);
        void alreadyAccountCreated(String message);
        void signUpError(String message);
    }
    interface Presenter{
        void signUp(String name, String email, String password, String retypePassword);
    }
}
