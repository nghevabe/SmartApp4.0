package com.example.smartapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class TabDeviceController extends Fragment {

    Button buttonAdd;

    MyRecyclerViewAdapter adapter;

    RecyclerView recyclerView;

    TextView txtNumber,txtRecycleview;


    public static ArrayList<ElectricDevice> lstDeviceElectric = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //MyRecyclerViewAdapter adapter;

        View view = inflater.inflate(R.layout.tab_device_controller, container, false);

        buttonAdd = view.findViewById(R.id.btnAdd);

        txtNumber = view.findViewById(R.id.textNumber);
        txtRecycleview = view.findViewById(R.id.textRecycleview);


        recyclerView = view.findViewById(R.id.rv);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), numberOfColumns));



        adapter = new MyRecyclerViewAdapter(getActivity().getApplicationContext(), lstDeviceElectric);

        recyclerView.setAdapter(adapter);

        txtNumber.setText(""+lstDeviceElectric.size());

        if(lstDeviceElectric.size() == 0){
            txtRecycleview.setVisibility(View.VISIBLE);
        } else {
            txtRecycleview.setVisibility(View.INVISIBLE);
        }

        //startMqtt();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity().getApplicationContext(), ScanDevice.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                getActivity().getApplicationContext().startActivity(intent);

            }
        });



        //startMqtt();

        return view;
    }
    //tcp://broker.mqttdashboard.com:1883

    @Override
    public void onPause() {
        super.onPause();
        //processMQTT.Disconnect();
// add your code here which executes when user leaving the current fragment or fragment is no longer intractable.
    }



    @Override
    public void onResume() {
        super.onResume();

        txtNumber.setText(""+lstDeviceElectric.size());

        if(lstDeviceElectric.size() == 0){
            txtRecycleview.setVisibility(View.VISIBLE);
        } else {
            txtRecycleview.setVisibility(View.INVISIBLE);
        }

        adapter = new MyRecyclerViewAdapter(getActivity().getApplicationContext(), lstDeviceElectric);

        recyclerView.setAdapter(adapter);
        //processMQTT.Disconnect();
        //processMQTT.startMqtt(getActivity().getApplicationContext());

// add your code here which executes when user leaving the current fragment or fragment is no longer intractable.
    }

    @Override
    public void onStop() {
        super.onStop();

        //processMQTT.Disconnect();
// add your code here which executes Fragment going to be stopped.
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

// add your code here which executes when the final clean up for the Fragment's state is needed.
    }

}
