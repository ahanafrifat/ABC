package com.appinionbd.abc.interfaces.presenterInterface;

import com.appinionbd.abc.model.dataModel.ReminderList;

import java.util.List;

public interface ITaskInfo {
    interface View{

        void showReminderList(List<ReminderList> reminderLists, String taskTitle, String category);
        void confirmDeleteTask(String message);
        void deleteError(String message);
        void connectionError(String message);
    }

    interface Presenter{
        void reminderList(String taskId);
        void deleteTask(String taskId);
    }
}
