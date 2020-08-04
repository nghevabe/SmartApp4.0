package com.example.smartapp.Authorization;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartapp.ApiUtils;
import com.example.smartapp.Data.Model.SOAnswersResponse;
import com.example.smartapp.Data.remote.SOService;
import com.example.smartapp.R;

import java.util.List;

import retrofit2.Call;
//import retrofit2.Callback;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button buttonLogin;
    private SOService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        mService = ApiUtils.getSOService();

        buttonLogin = findViewById(R.id.btnLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                LoginActivity.this.startActivity(intent);

                //Log.d("XXX", "Call");
                loadAnswers();
            }
        });

    }


    public void loadAnswers() {
        mService.getAnswers().enqueue(new Callback<SOAnswersResponse>() {
            @Override
            public void onResponse(Call<SOAnswersResponse> call, Response<SOAnswersResponse> response) {

                if(response.isSuccessful()) {
                    //mAdapter.updateAnswers(response.body().getItems());
                    Log.d("XXX", "data Api: "+response.body().getItems().get(0).getOwner().getDisplayName());
                }else {
                    int statusCode  = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<SOAnswersResponse> call, Throwable t) {
                //showErrorMessage();
                Log.d("XXX", "error loading from API");

            }
        });
    }

}
