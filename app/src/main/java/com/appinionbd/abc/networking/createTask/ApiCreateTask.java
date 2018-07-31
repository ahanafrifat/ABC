package com.appinionbd.abc.networking.createTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.appinionbd.abc.interfaces.createInterface.ICreate;
import com.appinionbd.abc.model.dataModel.ResponseModel;
import com.appinionbd.abc.model.dataModel.Task;
import com.appinionbd.abc.networking.retrofit.ApiClient;

public class ApiCreateTask {
    private static ApiCreateTask apiCreateTask;

    private ApiCreateTask(){

    }
    public static ApiCreateTask getApiCreateTask(){
        if(apiCreateTask == null)
            apiCreateTask = new ApiCreateTask();
        return apiCreateTask;
    }
    public void setApiCreateTask(Task task , String token , ICreate iCreate){
        apiSetCreateTask(task , token , iCreate);
    }

    private void apiSetCreateTask(Task task, String token , ICreate iCreate) {
        Call<ResponseModel> call = ApiClient.getApiInterface().createTask(token , task);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.code() == 201) {
                    ResponseModel responseModel = response.body();
                    iCreate.successful(responseModel.getMessage());
                }
                else if(response.code() == 500){
                    String error = " Internal Server Error";
                    iCreate.error(error);
                }
                else if(response.code() == 401){
                    String error =  "Unauthorized";
                    iCreate.unAuthorized(error);
                }
                else {
                    String error =  "Internal Error";
                    iCreate.error(error);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                String error =  "Connection problem";
                iCreate.connectionProblem(error);
            }
        });
    }
}
