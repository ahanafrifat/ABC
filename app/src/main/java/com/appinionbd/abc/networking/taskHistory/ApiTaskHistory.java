package com.appinionbd.abc.networking.taskHistory;


import com.appinionbd.abc.appUtils.AppUtil;
import com.appinionbd.abc.interfaces.taskInfoInterface.ITaskAndReminderInterface;
import com.appinionbd.abc.model.dataModel.PatientHistory;
import com.appinionbd.abc.model.dataModel.PatientWiseTaskList;
import com.appinionbd.abc.model.dataModel.ReminderList;
import com.appinionbd.abc.networking.retrofit.ApiClient;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiTaskHistory {
    private static ApiTaskHistory apiTaskHistory;

    public ApiTaskHistory() {
    }

    public static ApiTaskHistory getApiTaskHistory(){
        if(apiTaskHistory == null)
            apiTaskHistory = new ApiTaskHistory();
        return apiTaskHistory;
    }

    public void setApiTaskHistory(String token , String patient_id , ITaskAndReminderInterface iTaskAndReminderInterface){
        taskHistory(token ,patient_id , iTaskAndReminderInterface);
    }

    private void taskHistory(String token, String patient_id, ITaskAndReminderInterface iTaskAndReminderInterface){
        Call<PatientHistory> call = ApiClient.getApiInterface().patientHistoryCall(token , patient_id);
        call.enqueue(new Callback<PatientHistory>() {
            @Override
            public void onResponse(Call<PatientHistory> call, Response<PatientHistory> response) {
                if(response.code() == 200){
                    PatientHistory patientHistory = response.body();

//                    AppUtil.log("ApiTaskHistory" , "PatientWiseTaskList size : " + patientHistory.getPatientWiseTaskList().size());

                    if(patientHistory.getPatientWiseTaskList() != null){
                        List<PatientWiseTaskList> patientWiseTaskLists = new ArrayList<>();

                        for(PatientWiseTaskList patientWiseTaskList : patientHistory.getPatientWiseTaskList()){

                            PatientWiseTaskList savePatientWiseTaskList = new PatientWiseTaskList();

                            savePatientWiseTaskList.setTaskId(patientWiseTaskList.getTaskId());
                            savePatientWiseTaskList.setTaskName(patientWiseTaskList.getTaskName());
                            savePatientWiseTaskList.setStartDate(patientWiseTaskList.getStartDate());
                            savePatientWiseTaskList.setEndDate(patientWiseTaskList.getEndDate());
                            savePatientWiseTaskList.setNumOfDays(patientWiseTaskList.getNumOfDays());
                            savePatientWiseTaskList.setCompleteDate(patientWiseTaskList.getCompleteDate());
                            savePatientWiseTaskList.setStatus(patientWiseTaskList.getStatus());
                            savePatientWiseTaskList.setTaskNotes(patientWiseTaskList.getTaskNotes());
                            savePatientWiseTaskList.setTaskCategory(patientWiseTaskList.getTaskCategory());

                            RealmList<ReminderList> reminderLists = new RealmList<>();

                            AppUtil.log("ApiTaskHistory" , "reminderList size : " + patientWiseTaskList.getReminderList().size());

                            for(ReminderList reminderList : patientWiseTaskList.getReminderList()){

                                ReminderList saveReminderList = new ReminderList();
                                saveReminderList.setReminderId(reminderList.getReminderId());
                                saveReminderList.setReminderDate(reminderList.getReminderDate());
                                saveReminderList.setReminderTime(reminderList.getReminderTime());
                                saveReminderList.setCompleteDate(reminderList.getCompleteDate());
                                saveReminderList.setStatus(reminderList.getStatus());

                                reminderLists.add(saveReminderList);

                            }

                            savePatientWiseTaskList.setReminderList(reminderLists);


                            patientWiseTaskLists.add(savePatientWiseTaskList);

                        }

                        try(Realm realm = Realm.getDefaultInstance()){
                            realm.executeTransaction(realm1 -> realm1.insertOrUpdate(patientWiseTaskLists));
                        }
                        iTaskAndReminderInterface.patientTaskList(patientWiseTaskLists);
                    }
                    else{
                        iTaskAndReminderInterface.emptyPatientTaskList("Task List Empty !");
                    }

                }
                else{
                    iTaskAndReminderInterface.serverError("Server Error : " + response.code() + " Message : " + response.message());
                }
            }

            @Override
            public void onFailure(Call<PatientHistory> call, Throwable t) {
                iTaskAndReminderInterface.connectionError("Connection error : " + t.getMessage());
            }
        });

    }
}
