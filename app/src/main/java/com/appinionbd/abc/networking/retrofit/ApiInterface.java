package com.appinionbd.abc.networking.retrofit;

import com.appinionbd.abc.model.dataModel.APIAuth;
import com.appinionbd.abc.model.dataModel.LoginAuth;
import com.appinionbd.abc.model.dataModel.PatientWithDate;
import com.appinionbd.abc.model.dataModel.ResponseModel;
import com.appinionbd.abc.model.dataModel.ResponseTask;
import com.appinionbd.abc.model.dataModel.Task;
import com.appinionbd.abc.model.dataModel.UpdatePersonalInfoBody;
import com.appinionbd.abc.model.dataModel.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiInterface {

    @Headers({"Client-Service: appinion-client",
    "Auth-Key: 523f260e015519f3a6da69f9ae1a94de",
    "Content-Type: application/json"})
    @POST("/abcApp_api/Auth/login")
    Call<APIAuth> apiAuth(@Query("username") String username,
                          @Query("password") String password);

    @POST("/abcApp_api/api/app_auth/login")
    Call<LoginAuth> loginAuth(@Header("Authorization") String authorization ,
                              @Query("app_user_id") String username,
                              @Query("app_password") String password);

    @GET("/abcApp_api/api/user/get_user_info")
    Call<User> userAuth(@Header("token") String token);

    @Headers({"Content-Type: application/json"})
    @PUT("/abcApp_api/api/user/update_user_info")
    Call<ResponseModel> updatePersonalInformation(@Header("token") String token,
                                                  @Body UpdatePersonalInfoBody updatePersonalInfoBody);

    @Headers({"Content-Type: application/json"})
    @POST("/abcApp_api/api/task/create_task")
    Call<ResponseModel> createTask(@Header("token") String token,
                                   @Body Task task);

    @POST("/abcApp_api/api/task/date_wise_patient_task_list/5")
    Call<ResponseTask> responseTaskCall(@Header("token") String token,
                                        @Body PatientWithDate patientWithDate);


}
