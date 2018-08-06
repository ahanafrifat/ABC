package com.appinionbd.abc.interfaces.presenterInterface;

public interface ISignUp {
    interface View{
        void errorPasswordDidNotMatch();
    }
    interface Presenter{
        void signUp(String name, String email, String password, String retypePassword);
    }
}
