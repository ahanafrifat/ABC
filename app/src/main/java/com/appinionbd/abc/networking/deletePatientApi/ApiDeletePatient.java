package com.appinionbd.abc.networking.deletePatientApi;

import com.appinionbd.abc.model.dataModel.ResponseModel;
import com.appinionbd.abc.networking.retrofit.ApiClient;

import retrofit2.Call;

public class ApiDeletePatient {
    private static ApiDeletePatient apiDeletePatient;
    
    private ApiDeletePatient(){
        
    }
    
    public static ApiDeletePatient getApiDeletePatient(){
        if(apiDeletePatient == null)
            apiDeletePatient = new ApiDeletePatient();
        return apiDeletePatient;
    }
    
    public static void setApiDeletePatient(String token , String patientId){
        deletePatient(token , patientId);
    }

    private static void deletePatient(String token, String patientId) {
        Call<ResponseModel> call = ApiClient.getApiInterface().deletePatientCall(token ,patientId);
//        call.
    }
}
