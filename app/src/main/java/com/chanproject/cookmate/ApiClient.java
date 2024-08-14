package com.chanproject.cookmate;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "https://dummyjson.com";
    public static Retrofit retrofit = null;

     public static Retrofit getRetrofit(){

         HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
         interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
         OkHttpClient okHttpClient = new OkHttpClient.Builder()
                 .connectTimeout(5, TimeUnit.MINUTES)
                 .writeTimeout(5,TimeUnit.MINUTES)
                 .readTimeout(5,TimeUnit.MINUTES)
                 .addInterceptor(interceptor).build();


         if (retrofit == null){
             retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                     .client(okHttpClient)
                     .addConverterFactory(GsonConverterFactory.create()).build();
         }

         return retrofit;

     }

}
