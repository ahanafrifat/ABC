package com.appinionbd.abc.interfaces.presenterInterface;

import com.appinionbd.abc.model.dataModel.TaskCategory;

import java.util.List;

public interface IHome {

    interface View{

        void taskList(List<TaskCategory> taskCategories);

        void taskListEmpty(String message);

        void alarmOn(String message, TaskCategory taskCategory);

        void alarmOff(String message, TaskCategory taskCategory);

        void alarmStatusChangeError(String message);

        void alarmStatusChangeConnectionError(String message);
//
//        void notificationAndAlarmOff(String id, ImageView imageViewTime, Button buttonDone);
//
//        void notificationAndAlarmON(String id, ImageView imageViewTime, Button buttonDone, String tempTime);

    }

    interface Presenter{

        void sendDate(String time);

        void checkReminder(String reminderTime, String id, String taskId, String reminderStatus);

        void reminderOnAndSaveInDatabase(TaskCategory taskCategory, int alarmOn);

        void reminderOffAndSaveInDatabase(TaskCategory taskCategory, int alarmOff);

//        void taskDone(String id);
    }
}
