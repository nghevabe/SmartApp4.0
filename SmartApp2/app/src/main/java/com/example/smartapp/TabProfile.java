package com.example.smartapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class TabProfile extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //MyRecyclerViewAdapter adapter;

        View view = inflater.inflate(R.layout.tab_profile, container, false);

//        String userToken = getActivity().getIntent().getStringExtra("EXTRA_TOKEN");
//        Toast.makeText(getActivity().getApplicationContext(), userToken, Toast.LENGTH_SHORT).show();


        return view;
    }


}
