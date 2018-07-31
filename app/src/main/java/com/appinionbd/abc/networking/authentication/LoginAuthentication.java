package com.appinionbd.abc.networking.authentication;

import com.appinionbd.abc.appUtils.AppUtil;
import com.appinionbd.abc.interfaces.networkInterface.IAuthentication;
import com.appinionbd.abc.model.dataHolder.UserInfo;
import com.appinionbd.abc.model.dataModel.APIAuth;
import com.appinionbd.abc.model.dataModel.LoginAuth;
import com.appinionbd.abc.model.dataModel.User;
import com.appinionbd.abc.networking.retrofit.ApiClient;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginAuthentication {
    private static LoginAuthentication authentication;

    private LoginAuthentication(){

    }

    public static LoginAuthentication getAuthentication(){
        if(authentication == null)
            authentication = new LoginAuthentication();
        return authentication;
    }

    public void loginAuthentication(String userName , String password , IAuthentication iAuthentication){
        apiAuthentication(userName , password , iAuthentication);
    }

    private void apiAuthentication(String userName, String password, IAuthentication iAuthentication) {
        Call<APIAuth> call = ApiClient.getApiInterface().apiAuth("abcApp_api" , "abc_Appinion");
        call.enqueue(new Callback<APIAuth>() {
            @Override
            public void onResponse(Call<APIAuth> call, Response<APIAuth> response) {
                if(response.isSuccessful()){
                    APIAuth apiAuth = response.body();
                    if(apiAuth != null && apiAuth.getStatus() == 200 && !apiAuth.getAuthorization().isEmpty()){
                        gotoLoginAuth(apiAuth.getAuthorization() , userName , password , iAuthentication);
                    }
                }
            }

            @Override
            public void onFailure(Call<APIAuth> call, Throwable t) {

                AppUtil.log("LoginAuthentication" , "apiAuthentication Error : " + t.getMessage());
            }
        });
    }

    private void gotoLoginAuth(String authorization, String userName, String password, IAuthentication iAuthentication) {
        Call<LoginAuth> call = ApiClient.getApiInterface().loginAuth(authorization , userName , password);
        call.enqueue(new Callback<LoginAuth>() {
            @Override
            public void onResponse(Call<LoginAuth> call, Response<LoginAuth> response) {
                if (response.isSuccessful()){
                    LoginAuth loginAuth = response.body();
                    if(loginAuth != null && loginAuth.getStatus() == 200 && !loginAuth.getToken().isEmpty()){
                        gotoGetUserInfo(loginAuth.getToken() , iAuthentication);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginAuth> call, Throwable t) {
                AppUtil.log("LoginAuthentication" , "gotoLoginAuth Error : " + t.getMessage());
            }
        });
    }

    private void gotoGetUserInfo(String token, IAuthentication iAuthentication) {
        Call<User> call = ApiClient.getApiInterface().userAuth(token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User user = response.body();
                    if(user != null){
                        iAuthentication.successful();

                        User userSave = new User();
                        UserInfo userInfo = new UserInfo();
                        userSave.setUserId(user.getUserId());
                        userSave.setUserName(user.getUserName());
                        userSave.setUserEmail(user.getUserEmail());
                        userSave.setDob(user.getDob());
                        userSave.setHeight(user.getHeight());
                        userSave.setWeight(user.getWeight());
                        userSave.setGender(user.getGender());
                        userSave.setUserRefCode(user.getUserRefCode());

                        userInfo.setUserId(user.getUserId());
                        userInfo.setToken(token);

                        try(Realm realmInstance = Realm.getDefaultInstance()){
                            realmInstance.executeTransaction(realm -> realm.insertOrUpdate(userSave));
                            realmInstance.executeTransaction(realm -> realm.insertOrUpdate(userInfo));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                AppUtil.log("LoginAuthentication" , "gotoGetUserInfo Error : " + t.getMessage());
                iAuthentication.connectionError();
            }
        });
    }
}
