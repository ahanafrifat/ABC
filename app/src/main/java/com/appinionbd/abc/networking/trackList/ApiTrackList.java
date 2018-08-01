package com.appinionbd.abc.networking.trackList;

import com.appinionbd.abc.interfaces.trackListInterface.ITrackList;
import com.appinionbd.abc.model.dataModel.Monitor;
import com.appinionbd.abc.networking.retrofit.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiTrackList {
    private static ApiTrackList apiTrackList;
    private ApiTrackList(){

    }
    public static ApiTrackList getApiTrackList(){
        if(apiTrackList == null)
            apiTrackList = new ApiTrackList();
        return apiTrackList;
    }
    public static void setApiTrackList(String token , ITrackList iTrackList){
        apiShowTrackedPatient(token , iTrackList);
    }

    private static void apiShowTrackedPatient(String token, ITrackList iTrackList) {
        Call<Monitor> call = ApiClient.getApiInterface().trackListCall(token);
        call.enqueue(new Callback<Monitor>() {
            @Override
            public void onResponse(Call<Monitor> call, Response<Monitor> response) {
                if(response.code() == 200){
                    Monitor monitor = response.body();
                    iTrackList.successful(monitor);
                }
                else
                    iTrackList.error();
            }

            @Override
            public void onFailure(Call<Monitor> call, Throwable t) {
                iTrackList.networkFailed();
            }
        });
    }
}
