package com.appinionbd.abc.interfaces.presenterInterface;

import com.appinionbd.abc.model.dataModel.ReminderList;

import io.realm.RealmList;

public interface ITaskInfo {
    interface View{

        void showReminderList(RealmList<ReminderList> reminderList, String taskName, String taskCategory);
    }

    interface Presenter{
        void reminderList(String taskId);
    }
}
