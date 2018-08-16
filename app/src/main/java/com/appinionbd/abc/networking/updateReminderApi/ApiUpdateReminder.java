package com.appinionbd.abc.networking.updateReminderApi;

import com.appinionbd.abc.interfaces.UpdateReminderInterface.IUpdateReminder;
import com.appinionbd.abc.model.dataModel.ResponseModel;
import com.appinionbd.abc.model.uploadDataModel.UpdateReminderModel;
import com.appinionbd.abc.networking.retrofit.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiUpdateReminder {
    private static ApiUpdateReminder apiUpdateReminder;

    private ApiUpdateReminder(){

    }

    public static ApiUpdateReminder getApiUpdateReminder(){
        if(apiUpdateReminder == null)
            apiUpdateReminder = new ApiUpdateReminder();
        return apiUpdateReminder;
    }
    public void setApiUpdateReminder(String token , UpdateReminderModel updateReminderModel , IUpdateReminder iUpdateReminder){
        updateReminder(token , updateReminderModel , iUpdateReminder);
    }

    private void updateReminder(String token, UpdateReminderModel updateReminderModel, IUpdateReminder iUpdateReminder) {
        Call<ResponseModel> call = ApiClient.getApiInterface().updateReminderCall(token , updateReminderModel);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.code() == 200) {
                    ResponseModel responseModel = response.body();
                    if(responseModel.getStatus() == 202){
                        iUpdateReminder.successfullyUpdated("Successfully Updated");
                    }
                    else{
                        iUpdateReminder.errorInUpdateReminder(responseModel.getMessage());
                    }
                }
                else{
                    iUpdateReminder.errorInUpdateReminder("Server Error : " + response.code() + " Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                iUpdateReminder.connectionErrorInUpdateReminder("Connection Error : " + t.getMessage());
            }
        });
    }


}
