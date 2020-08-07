package com.example.smartapp.Data.remote;

import com.example.smartapp.Data.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
//eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1OTY0MTY2MjksInVzZXJuYW1lIjoibGluaHRyYW4ifQ.Yy3DSQ_iJ6w2pgbUgQhT9-m5k3qSdSd1pMMThfsnMeE
public interface ApiService {

    @GET("/rest/users/{id}")
    Call<User> getUserDetail(@Path("id") int userId);

//    @GET("rest/user_detail")
//    Call<User> getUserDetailAuthor(@Header("Authorization") String token);

    @GET("rest/user_detail")
    Call<User> getUserDetailAuthor();

}
