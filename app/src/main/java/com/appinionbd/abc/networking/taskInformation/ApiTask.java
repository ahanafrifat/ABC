package com.appinionbd.abc.networking.taskInformation;

import com.appinionbd.abc.interfaces.createInterface.ICreate;
import com.appinionbd.abc.interfaces.homeInterface.IHomeFragmentInterface;
import com.appinionbd.abc.model.dataHolder.AlarmModel;
import com.appinionbd.abc.model.dataModel.PatientWithDate;
import com.appinionbd.abc.model.dataModel.ResponseTask;
import com.appinionbd.abc.model.dataModel.TaskCategory;
import com.appinionbd.abc.networking.retrofit.ApiClient;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiTask {
    private static ApiTask apiTask;
    private ApiTask(){

    }
    public static ApiTask getApiTask(){
        if(apiTask == null)
            apiTask = new ApiTask();
        return apiTask;
    }

    public void setApiTask(String token , PatientWithDate patientWithDate , IHomeFragmentInterface iHomeFragmentInterface){
        apitaskSort(token , patientWithDate , iHomeFragmentInterface);
    }

    private void apitaskSort(String token, PatientWithDate patientWithDate, IHomeFragmentInterface iHomeFragmentInterface) {

        Call<ResponseTask> call = ApiClient.getApiInterface().responseTaskCall(token , patientWithDate);
        call.enqueue(new Callback<ResponseTask>() {
            @Override
            public void onResponse(Call<ResponseTask> call, Response<ResponseTask> response) {
                if(response.code() == 200) {

                    ResponseTask responseTask = response.body();

                    if(responseTask.getTaskCategory() != null){

                    List<TaskCategory> taskCategories = new ArrayList<>();
                    List<AlarmModel> alarmModels = new ArrayList<>();

                    try (Realm realm = Realm.getDefaultInstance()) {

                        for (TaskCategory category : responseTask.getTaskCategory()) {

                            TaskCategory taskCategory = new TaskCategory();
                            taskCategory.setId(category.getId());
                            taskCategory.setTaskName(category.getTaskName());
                            taskCategory.setTaskId(category.getTaskId());
                            taskCategory.setTblTaskCategorySerId(category.getTblTaskCategorySerId());
                            taskCategory.setStartDate(category.getStartDate());
                            taskCategory.setReminder(category.getReminder());

                            taskCategory.setReminder(category.getReminderStatus());

                            taskCategory.setTaskNotes(category.getTaskNotes());
                            taskCategory.setTaskStatus(category.getTaskStatus());
                            taskCategory.setCompletDate(category.getCompletDate());
                            taskCategory.setTaskCategory(category.getTaskCategory());
                            taskCategory.setReminderTime(category.getReminderTime() + " " + category.getStartDate());

                            taskCategories.add(taskCategory);

//                            AlarmModel alarmModel = new AlarmModel();
//                            alarmModel.setAlarmId(category.getId());
//                            alarmModel.setTime(category.getReminderTime() + " " + category.getStartDate());
//
//                            AlarmModel relamAlarmModel = realm.where(AlarmModel.class)
//                                    .equalTo("alarmId", category.getId())
//                                    .equalTo("state", "no")
//                                    .or()
//                                    .equalTo("state", "yes")
//                                    .findFirst();
//                            if (relamAlarmModel == null) {
//                                alarmModel.setState("no");
//                            } else
//                                alarmModel.setState("yes");
//
//                            alarmModels.add(alarmModel);

                            realm.executeTransaction(realm1 -> {
                                realm1.insertOrUpdate(taskCategory);
//                                realm1.insertOrUpdate(alarmModel);
                            });

                        }
                    }
                    iHomeFragmentInterface.successful(taskCategories);

                }
                else {
                        iHomeFragmentInterface.noContent("There is No Content ! ");
                    }
                }
                else if(response.code() == 401){
                    iHomeFragmentInterface.unAuthorized("Un Authorized");
                }
                else{
                    iHomeFragmentInterface.error("There is an ERROR ! ");
                }
            }

            @Override
            public void onFailure(Call<ResponseTask> call, Throwable t) {
                iHomeFragmentInterface.connectionProblem("Connection problem : "+ t.getMessage());
            }
        });
    }
}






















