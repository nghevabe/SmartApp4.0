package com.example.smartapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class TabDeviceController extends Fragment {

    Button buttonAdd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MyRecyclerViewAdapter adapter;

        View view = inflater.inflate(R.layout.tab_device_controller, container, false);

        buttonAdd = view.findViewById(R.id.btnAdd);

        ArrayList<ElectricDevice> lstDevice = new ArrayList<>();


        ElectricDevice device1 = new ElectricDevice("5","LED sáng đục","device_light","none");
        ElectricDevice device2 = new ElectricDevice("5","Đèn bàn học","device_light","none");
        ElectricDevice device3 = new ElectricDevice("5","LED siêu sáng","device_light","none");
        ElectricDevice device4 = new ElectricDevice("5","Đèn đọc sách","device_light","none");

        ElectricDevice device5 = new ElectricDevice("5","LED 1","device_light","none");
        ElectricDevice device6 = new ElectricDevice("5","LED 2","device_light","none");
        ElectricDevice device7 = new ElectricDevice("5","LED 3","device_light","none");
        //ElectricDevice device4 = new ElectricDevice("4","Đèn sân","light","none");

        lstDevice.add(device1);
        lstDevice.add(device2);
        lstDevice.add(device3);
        lstDevice.add(device4);
        lstDevice.add(device5);
        lstDevice.add(device6);
        lstDevice.add(device7);
        //lstDevice.add(device4);

        RecyclerView recyclerView = view.findViewById(R.id.rv);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), numberOfColumns));



        adapter = new MyRecyclerViewAdapter(getActivity().getApplicationContext(), lstDevice);

        recyclerView.setAdapter(adapter);



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


}
