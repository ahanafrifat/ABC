package com.appinionbd.abc.networking.trackPatient;

import com.appinionbd.abc.interfaces.trackInterface.ITrackPatient;
import com.appinionbd.abc.model.dataHolder.PatientTrackModel;
import com.appinionbd.abc.model.dataModel.ResponseModel;
import com.appinionbd.abc.networking.retrofit.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiTrackPatient {
    private static ApiTrackPatient apiTrackPatient;

    private ApiTrackPatient() {
    }

    public static ApiTrackPatient getApiTrackPatient(){
        if(apiTrackPatient == null)
            apiTrackPatient = new ApiTrackPatient();
        return apiTrackPatient;
    }

    public static void setApiTrackPatient(String id , String token, ITrackPatient iTrackPatient) {
        apiPatientTracker(id , token , iTrackPatient);
    }

    private static void apiPatientTracker(String id, String token, ITrackPatient iTrackPatient) {
        PatientTrackModel patientTrackModel = new PatientTrackModel(id);
        Call<ResponseModel> call = ApiClient.getApiInterface().patientTrackModelCall(token , patientTrackModel);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.code() == 200){
                    iTrackPatient.successful();
                }
                else{
                    iTrackPatient.error();
                }

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                iTrackPatient.networkFailed();
            }
        });
    }
}
