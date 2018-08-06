package com.appinionbd.abc.networking.signUpApi;

import com.appinionbd.abc.appUtils.AppUtil;
import com.appinionbd.abc.model.dataModel.APIAuth;
import com.appinionbd.abc.networking.retrofit.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiSignUp {
    private static ApiSignUp apiSignUp;

    private ApiSignUp(){
    }

    public static ApiSignUp getApiSignUp(){
        if(apiSignUp == null)
            apiSignUp = new ApiSignUp();
        return apiSignUp;
    }

    public void setApiSignUp(){

    }

    private void apiSignUp (String name , String email , String password){
        Call<APIAuth> call = ApiClient.getApiInterface().apiAuth("abcApp_api" , "abc_Appinion");
        call.enqueue(new Callback<APIAuth>() {
            @Override
            public void onResponse(Call<APIAuth> call, Response<APIAuth> response) {
                if(response.isSuccessful()){
                    APIAuth apiAuth = response.body();
                    if(apiAuth != null && apiAuth.getStatus() == 200 && !apiAuth.getAuthorization().isEmpty()){
                        gotoSignUp(apiAuth.getAuthorization() , name , email , password);
                    }
                }
            }

            @Override
            public void onFailure(Call<APIAuth> call, Throwable t) {

                AppUtil.log("LoginAuthentication" , "apiAuthentication Error : " + t.getMessage());
            }
        });
    }

    private void gotoSignUp(String authorization, String name, String email, String password) {

    }
}
