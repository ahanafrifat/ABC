package com.appinionbd.abc.interfaces.presenterInterface;

public interface IAlarmDone {
    interface View{

        void successfulAlarmDone(String alarmId ,String message);

        void error(String message);

        void errorInAlarmDone(String message);

        void connectionErrorInAlarmdone(String message);
    }
    interface Presenter{

        void alarmDone(String reminderId , String alarmId);

    }
}
