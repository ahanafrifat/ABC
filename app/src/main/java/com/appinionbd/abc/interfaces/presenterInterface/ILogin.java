package com.appinionbd.abc.interfaces.presenterInterface;

public interface ILogin {
    interface View{
        void emptyUserName();
        void emptyPassword();
        void successful();
    }
    interface Presenter{
        void loginVerification(String userName , String password);
    }
}
