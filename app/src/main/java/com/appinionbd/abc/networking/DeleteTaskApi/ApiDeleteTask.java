package com.appinionbd.abc.networking.DeleteTaskApi;

import com.appinionbd.abc.interfaces.deleteInterface.IDeleteInterface;
import com.appinionbd.abc.model.dataModel.ResponseModel;
import com.appinionbd.abc.networking.retrofit.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiDeleteTask {
    private static ApiDeleteTask apiDeleteTask;

    private ApiDeleteTask(){

    }
    public static ApiDeleteTask getApiDeleteTask(){
        if(apiDeleteTask == null)
            apiDeleteTask = new ApiDeleteTask();
        return apiDeleteTask;
    }

    public static void setApiDeleteTask (String token , String taskId , IDeleteInterface iDeleteInterface){
        deleteTask(token , taskId , iDeleteInterface);
    }

    private static void deleteTask(String token, String taskId, IDeleteInterface iDeleteInterface) {
        Call<ResponseModel> call = ApiClient.getApiInterface().deleteTaskCall(token , taskId);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.code() == 200)
                    iDeleteInterface.successful("Delete task Successful : " +taskId);
                else
                    iDeleteInterface.error("Server error : " +response.code() + "Message : " + response.message());
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                iDeleteInterface.connectionError("Connection error : " + t.getMessage());
            }
        });
    }

}
