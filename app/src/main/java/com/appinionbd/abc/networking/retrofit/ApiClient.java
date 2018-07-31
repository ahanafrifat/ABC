package com.appinionbd.abc.networking.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://dev.appinionbd.com";

    private static Retrofit retrofit = null;

    private static Retrofit getApiClient(){
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.addInterceptor(logging);

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClientBuilder.build())
                    .build();
        }
        return retrofit;
    }
    public static ApiInterface getApiInterface(){
        return ApiClient.getApiClient().create(ApiInterface.class);
    }
}
