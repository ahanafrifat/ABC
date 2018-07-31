package com.appinionbd.abc.interfaces.presenterInterface;

import java.util.List;

public interface ICreateTask {
    interface View{
        void successfullySubmitted(String message);
        void unAuthorized(String message);
        void error(String message);
        void connectionProblem(String message);
    }
    interface Presenter{
        void createReminder();
        void submitTask(String name, String noOfDays, String date, List<String> reminderList, int selectedItemPosition);
    }
}
