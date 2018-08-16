package com.appinionbd.abc.networking.deletePatientApi;

import com.appinionbd.abc.interfaces.deleteInterface.IDeleteInterface;
import com.appinionbd.abc.model.dataModel.ResponseModel;
import com.appinionbd.abc.model.uploadDataModel.DeletePatientModel;
import com.appinionbd.abc.networking.retrofit.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiDeletePatient {
    private static ApiDeletePatient apiDeletePatient;
    
    private ApiDeletePatient(){
        
    }
    
    public static ApiDeletePatient getApiDeletePatient(){
        if(apiDeletePatient == null)
            apiDeletePatient = new ApiDeletePatient();
        return apiDeletePatient;
    }
    
    public static void setApiDeletePatient(String token , String patientId, IDeleteInterface iDeleteInterface){
        deletePatient(token , patientId, iDeleteInterface);
    }

    private static void deletePatient(String token, String patientId, IDeleteInterface iDeleteInterface) {
        DeletePatientModel deletePatientModel = new DeletePatientModel();
        deletePatientModel.setPatientRelId(Integer.valueOf(patientId));
        Call<ResponseModel> call = ApiClient.getApiInterface().deletePatientCall(token ,deletePatientModel);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.code() == 200){
                    iDeleteInterface.successful("Patient successfully deleted !");
                }
                else{
                    iDeleteInterface.error("Patient delete error : " + response.code() + "message : " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                iDeleteInterface.connectionError("Patient delete connection error : " + t.getMessage());
            }
        });
//        call.
    }
}
