package com.example.smartapp.Main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.smartapp.Authorization.LoginActivity;
import com.example.smartapp.Data.model.User;
import com.example.smartapp.Data.remote.ApiService;
import com.example.smartapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabProfile extends Fragment {
    private ApiService mService;

    TextView textViewUserName, textViewFullName ,textViewMail, textViewPhone, textViewAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //MyRecyclerViewAdapter adapter;

        View view = inflater.inflate(R.layout.tab_profile, container, false);

        textViewFullName = view.findViewById(R.id.tvFullName);
        textViewUserName = view.findViewById(R.id.tvUserName);
        textViewMail = view.findViewById(R.id.tvMail);
        textViewPhone = view.findViewById(R.id.tvPhone);
        textViewAddress = view.findViewById(R.id.tvAddress);

        //String userToken = getActivity().getIntent().getStringExtra("EXTRA_TOKEN");

       // mService = ApiUtils.getApiService(userToken);

        //getUserDetailAuthorApi();

        return view;
    }


    public void getUserDetailAuthorApi() {
        mService.getUserDetailAuthor().enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {

                    Log.d("MainActivity", "XXX: "+response.body().getFullname());
                    loadUserDetailAuthorData(response.body());
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

    public void loadUserDetailAuthorData(User user) {

        textViewFullName.setText(user.getFullname());
        textViewUserName.setText(user.getUsername());
        textViewMail.setText(user.getMail());
        textViewPhone.setText(user.getPhone());
        textViewAddress.setText(user.getAddress());

    }

}
