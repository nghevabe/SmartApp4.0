package com.example.smartapp;

import android.content.Intent;
import android.content.SharedPreferences;
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


import com.example.smartapp.DeviceController.ProcessMQTT;

import java.util.ArrayList;
import java.util.Map;

import static com.example.smartapp.MainActivity.processMQTT;
//import static com.example.smartapp.MainActivity.processMQTT2;

public class TabDeviceController extends Fragment {

    Button btnAdd, btnMore;

    MyRecyclerViewAdapter adapter;

    RecyclerView recyclerView;

    TextView txtNumber,txtRecycleview;

    public static int turnIt = 0;




    public static ArrayList<ElectricDevice> lstDeviceElectric = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //MyRecyclerViewAdapter adapter;

        View view = inflater.inflate(R.layout.tab_device_controller, container, false);

        btnAdd = view.findViewById(R.id.buttonAdd);
        btnMore = view.findViewById(R.id.buttonMore);

        txtNumber = view.findViewById(R.id.textNumber);
        txtRecycleview = view.findViewById(R.id.textRecycleview);

        getAllKey();

        turnIt = 1;

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
        //processMQTT2.startMqtt(getActivity().getApplicationContext());

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity().getApplicationContext(), ScanDevice.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                getActivity().getApplicationContext().startActivity(intent);

            }
        });
        //processMQTT.startMqtt(getActivity().getApplicationContext());


        //startMqtt();

        return view;
    }

    public void getAllKey(){

        lstDeviceElectric.clear();

        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        Map<String,?> keys = pref.getAll();



        for(Map.Entry<String,?> entry : keys.entrySet()){
            //Log.d("squirting",entry.getKey() + ": " + entry.getValue().toString());
            String data = entry.getValue().toString();
            String[] data_cut = data.split("-");
            Log.d("squirting","id: "+data_cut[0] + " name: "+data_cut[1] + " node: "+entry.getKey());

            ElectricDevice electricDevice = new ElectricDevice(data_cut[0],data_cut[1],"device_light","none",entry.getKey());

            TabDeviceController.lstDeviceElectric.add(electricDevice);
        }

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

        turnIt = 1;

        txtNumber.setText(""+lstDeviceElectric.size());

        if(lstDeviceElectric.size() == 0){
            txtRecycleview.setVisibility(View.VISIBLE);
        } else {
            txtRecycleview.setVisibility(View.INVISIBLE);
        }

        getAllKey();

        adapter = new MyRecyclerViewAdapter(getActivity().getApplicationContext(), lstDeviceElectric);

        recyclerView.setAdapter(adapter);
        //processMQTT.Disconnect();
        //processMQTT.startMqtt(getActivity().getApplicationContext());

// add your code here which executes when user leaving the current fragment or fragment is no longer intractable.
    }

    @Override
    public void onStop() {
        super.onStop();

        turnIt = 0;
        //processMQTT.Disconnect();
// add your code here which executes Fragment going to be stopped.
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        turnIt = 0;

// add your code here which executes when the final clean up for the Fragment's state is needed.
    }

}
