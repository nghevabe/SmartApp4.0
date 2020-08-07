package com.example.smartapp.Data.remote;

import com.example.smartapp.Data.model.User;
import com.example.smartapp.Data.model.UserParams;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiServiceNoAuth {

    @Headers("Content-Type: application/json")
    @POST("/rest/login")
    Call<String> login(@Body UserParams userParams);

    @GET("/rest/users/100")
    Call<User> getUserDetail();

}
