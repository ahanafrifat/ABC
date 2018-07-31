package com.appinionbd.abc.interfaces.presenterInterface;

import android.widget.Button;
import android.widget.ImageView;

import com.appinionbd.abc.model.dataHolder.AlarmModel;
import com.appinionbd.abc.model.dataModel.TaskCategory;

import java.util.List;

public interface IHome {

    interface View{

        void taskList(List<TaskCategory> taskCategories);

        void notificationAndAlarmOff(String time, int layoutPosition, List<AlarmModel> alarmModels, ImageView imageViewTime, Button buttonDone);

        void notificationAndAlarmON(String time, int layoutPosition, List<AlarmModel> alarmModels, ImageView imageViewTime, Button buttonDone);

    }

    interface Presenter{

        void sendDate(String time);

        void saveReminder(AlarmModel alarmModel);

        void checkReminder(String time, int layoutPosition, List<AlarmModel> alarmModels, ImageView imageViewTime, Button buttonDone);

        void taskDone(String alarmId);
    }
}
