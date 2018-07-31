package com.appinionbd.abc.presenter;

import com.appinionbd.abc.appUtils.AppUtil;
import com.appinionbd.abc.interfaces.createInterface.ICreate;
import com.appinionbd.abc.interfaces.presenterInterface.ICreateTask;
import com.appinionbd.abc.model.dataHolder.UserInfo;
import com.appinionbd.abc.model.dataModel.Task;
import com.appinionbd.abc.model.dataModel.User;
import com.appinionbd.abc.networking.createTask.ApiCreateTask;

import java.util.List;

import io.realm.Realm;

public class CreateTaskPresenter implements ICreateTask.Presenter {
    private ICreateTask.View view;

    public CreateTaskPresenter() {
    }

    public CreateTaskPresenter(ICreateTask.View view) {
        this.view = view;
    }

    @Override
    public void createReminder() {


    }

    @Override
    public void submitTask(String name, String noOfDays, String date, List<String> reminderList, int selectedItemPosition) {
        int userId;
        String token ;
        Task task;
        try(Realm realm = Realm.getDefaultInstance()){
            User user = realm.where(User.class).findFirst();
            userId = Integer.parseInt(user.getUserId());
            UserInfo userInfo = realm.where(UserInfo.class).findFirst();
            token = userInfo.getToken();
        }

        AppUtil.log("CreateTaskPresenter" , "Date : " + date);

        if(reminderList.size() == 0)
            task = new Task(name
                    , userId
                    , selectedItemPosition
                    , date
                    , Integer.parseInt(noOfDays)
                    , 0
                    , null
                    , null
                    , 0
                    , ""
                    , reminderList);
        else
            task = new Task(name
                    , userId
                    , selectedItemPosition
                    , date
                    , Integer.parseInt(noOfDays)
                    , 0
                    , null
                    , null
                    , 1
                    , ""
                    , reminderList);

        ApiCreateTask.getApiCreateTask().setApiCreateTask(task, token, new ICreate() {
            @Override
            public void successful(String message) {
                view.successfullySubmitted(message);
            }

            @Override
            public void noNewInfo(String message) {
                view.error(message);
            }

            @Override
            public void unAuthorized(String message) {
                view.unAuthorized(message);
            }

            @Override
            public void error(String message) {
                view.error(message);
            }

            @Override
            public void connectionProblem(String message) {
                view.connectionProblem(message);
            }
        });

    }
}


















