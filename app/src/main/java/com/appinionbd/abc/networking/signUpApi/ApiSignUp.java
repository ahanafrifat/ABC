package com.appinionbd.abc.networking.signUpApi;

import com.appinionbd.abc.appUtils.AppUtil;
import com.appinionbd.abc.interfaces.createInterface.ICreate;
import com.appinionbd.abc.model.dataModel.APIAuth;
import com.appinionbd.abc.model.dataModel.ResponseModel;
import com.appinionbd.abc.model.dataModel.SignUpModel;
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

    public void setApiSignUp(String name , String email , String password , ICreate iCreate){
        getAuthLogin( name ,  email ,  password , iCreate);
    }

    private void getAuthLogin(String name, String email, String password, ICreate iCreate){
        Call<APIAuth> call = ApiClient.getApiInterface().apiAuth("abcApp_api" , "abc_Appinion");
        call.enqueue(new Callback<APIAuth>() {
            @Override
            public void onResponse(Call<APIAuth> call, Response<APIAuth> response) {
                if(response.isSuccessful()){
                    APIAuth apiAuth = response.body();
                    if(apiAuth != null && apiAuth.getStatus() == 200 && !apiAuth.getAuthorization().isEmpty()){
                        gotoSignUp(apiAuth.getAuthorization() , name , email , password , iCreate);
                        AppUtil.log("ApiSignUp", "getAuthLogin : " + apiAuth.getAuthorization());
                    }
                }
                else {
                    iCreate.error("Auth error : " + response.code());
                    AppUtil.log("ApiSignUp", "getAuthLogin not done Error : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<APIAuth> call, Throwable t) {
                iCreate.error("Auth network error : " + t.getMessage());
                AppUtil.log("ApiSignUp", "getAuthLogin onFailure : " + t.getMessage());
            }
        });
    }

    private void gotoSignUp(String authorization, String name, String email, String password, ICreate iCreate) {
        SignUpModel signUpModel = new SignUpModel();

        signUpModel.setUserName(name);
        signUpModel.setUserEmail(email);
        signUpModel.setPassword(password);

        Call<ResponseModel> call = ApiClient.getApiInterface().signUpCall(authorization , signUpModel);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.isSuccessful() && response.code() == 200){
                    iCreate.successful("Account Created !");
                    AppUtil.log("ApiSignUp", "gotoSignUp : " + response.code());
                }
                else {
                    iCreate.noNewInfo("Already exist ! : " + response.code() + " " + response.message());
                    AppUtil.log("ApiSignUp", "gotoSignUp error : " + response.code() + " Name : " + name + " email : " + email + " Password : " + password);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                iCreate.connectionProblem("Connection error ! : " + t.getMessage());
                AppUtil.log("ApiSignUp", "gotoSignUp onFailure error : " + t.getMessage());
            }
        });
    }
}
