package com.appinionbd.abc.networking.updatePersonalInformation;

import com.appinionbd.abc.interfaces.networkInterface.IUpdatePersonalInformation;
import com.appinionbd.abc.model.dataModel.ResponseModel;
import com.appinionbd.abc.model.dataModel.UpdatePersonalInfoBody;
import com.appinionbd.abc.networking.retrofit.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiUpdatePersonalInformation {
    private static ApiUpdatePersonalInformation apiUpdatePersonalInformation;

    private ApiUpdatePersonalInformation(){

    }
    public static ApiUpdatePersonalInformation getApiUpdatePersonalInformation(){
        if(apiUpdatePersonalInformation == null)
            apiUpdatePersonalInformation = new ApiUpdatePersonalInformation();
        return apiUpdatePersonalInformation;
    }
    public void setApiUpdatePersonalInformation(String token, UpdatePersonalInfoBody updatePersonalInfoBody, IUpdatePersonalInformation iUpdatePersonalInformation){
        Call<ResponseModel> call = ApiClient.getApiInterface().updatePersonalInformation(token , updatePersonalInfoBody);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                if(response.isSuccessful() &&response.code() == 200) {
                    ResponseModel responseModel = response.body();
                    iUpdatePersonalInformation.successful();
                }
                else if(response.code() == 500){
                    iUpdatePersonalInformation.noNewInfo("No new Information");
                }
                else if(response.code() == 401){
                    iUpdatePersonalInformation.unAuthorized("Someone else logged in");
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                iUpdatePersonalInformation.connectionProblem("Connection Problem ! ");
            }
        });
    }
}
