package com.appinionbd.abc.interfaces.presenterInterface;

public interface ILogin {
    interface View{
        void emptyUserName();
        void emptyPassword();
        void successful();
        void emptyUser();
        void userExist();
        void loginError(String message);
        void wrongUsernameOrPassword(String message);
    }
    interface Presenter{
        void checkUserExist();
        void loginVerification(String userName , String password);
    }
}
