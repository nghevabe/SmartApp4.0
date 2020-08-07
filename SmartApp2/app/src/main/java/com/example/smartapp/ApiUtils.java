package com.example.smartapp;

import com.example.smartapp.Data.remote.ApiService;
import com.example.smartapp.Data.remote.ApiServiceNoAuth;
import com.example.smartapp.Data.remote.RetrofitClient;

public class ApiUtils {

    public static final String BASE_URL = "https://af3a506400e8.ngrok.io";


    public static ApiService getApiService(String token) {
        return RetrofitClient.getClient(BASE_URL, token).create(ApiService.class);
    }

    public static ApiServiceNoAuth getApiService() {
        return RetrofitClient.getClient(BASE_URL).create(ApiServiceNoAuth.class);
    }

}
