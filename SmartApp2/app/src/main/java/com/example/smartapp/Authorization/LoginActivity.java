package com.example.smartapp.Authorization;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartapp.ApiUtils;
import com.example.smartapp.Data.model.User;
import com.example.smartapp.Data.model.UserParams;
import com.example.smartapp.Data.remote.ApiService;
import com.example.smartapp.Data.remote.ApiServiceNoAuth;
import com.example.smartapp.HouseNotify;
import com.example.smartapp.MainActivity;
import com.example.smartapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public String token = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1OTY4Njk2NDEsInVzZXJuYW1lIjoibGluaHRyYW4ifQ.PlYFqMChcCZ1pke3OAB2msTW9OYoZyUMGTLhuYHRV4U";
    Button buttonLogin;
    EditText editTextUserName, editTextPassword;
    private ApiService mService;
    private ApiServiceNoAuth mServiceNoAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        mService = ApiUtils.getApiService(token);
        mServiceNoAuth = ApiUtils.getApiService();

        buttonLogin = findViewById(R.id.btnLogin);
        editTextUserName = findViewById(R.id.edUserName);
        editTextPassword = findViewById(R.id.edPassword);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = editTextUserName.getText().toString();
                String passWord = editTextPassword.getText().toString();

                UserParams userParams = new UserParams(userName,passWord);

                login(userParams);
            }
        });

    }


    public void loadUserDetailAuthor() {
        mService.getUserDetailAuthor().enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {

                    Log.d("MainActivity", "XXX: "+response.body().getFullname());
                }else {
                    int statusCode  = response.code();
                    Log.d("MainActivity", "XXX: "+statusCode);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }


    public void login(UserParams userParams) {
        mServiceNoAuth.login(userParams).enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.isSuccessful()) {

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("EXTRA_TOKEN", response.body());
                    LoginActivity.this.startActivity(intent);
                }else {
                    int statusCode  = response.code();
                    Log.d("MainActivity", "XXX "+statusCode);
                    Toast.makeText(LoginActivity.this.getApplicationContext(), "Username or Password is invalid", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("MainActivity", "XXX FAIL: "+t.getMessage());
                Toast.makeText(LoginActivity.this.getApplicationContext(), "Login Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
