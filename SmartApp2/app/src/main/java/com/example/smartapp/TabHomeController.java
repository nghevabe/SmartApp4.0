package com.example.smartapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.example.smartapp.MainActivity.processMQTT;

public class TabHomeController extends Fragment {

    Button btnScenario, btnSetup;
    ImageView nofbtn;
    //RecyclerView rv;
    MQTTHelper mqttHelper;

    public static ImageView imgDot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        MyRecyclerViewAdapter adapter;

        View view = inflater.inflate(R.layout.tab_house_controller, container, false);

       // btnScenario = view.findViewById(R.id.);
        btnSetup = view.findViewById(R.id.buttonSetup);
        btnScenario = view.findViewById(R.id.buttonScenario);
        nofbtn = view.findViewById(R.id.notifyButton);

        ArrayList<ElectricDevice> lstDevice = new ArrayList<>();


        ElectricDevice device1 = new ElectricDevice("1","Đèn nhà tắm","light","none");
        ElectricDevice device2 = new ElectricDevice("2","Đèn phòng ngủ","light","none");
        ElectricDevice device3 = new ElectricDevice("3","Đèn phòng khách","light","none");
        ElectricDevice device4 = new ElectricDevice("4","Quạt phòng khách","fan","none");
        ElectricDevice device5 = new ElectricDevice("5","Hệ thống lọc khí","fan","none");
        ElectricDevice device6 = new ElectricDevice("6","Kính thông minh","glass","none");
        ElectricDevice device7 = new ElectricDevice("7","Cửa","door","none");
        //ElectricDevice device4 = new ElectricDevice("4","Đèn sân","light","none");

        lstDevice.add(device1);
        lstDevice.add(device2);
        lstDevice.add(device3);
        lstDevice.add(device4);
        lstDevice.add(device5);
        lstDevice.add(device6);
        lstDevice.add(device7);

        imgDot = view.findViewById(R.id.imageDot);


        if(SetupData.lstNof.size() == 0){
            imgDot.setVisibility(View.INVISIBLE);
        }

        if(SetupData.lstNof.size() > 0){
            imgDot.setVisibility(View.VISIBLE);
        }


        //imgDot.setVisibility(View.INVISIBLE);

        RecyclerView recyclerView = view.findViewById(R.id.rv);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), numberOfColumns));


        adapter = new MyRecyclerViewAdapter(getActivity().getApplicationContext(), lstDevice);

        recyclerView.setAdapter(adapter);

        btnSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), HouseSetup.class);



                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                getActivity().getApplicationContext().startActivity(intent);
            }
        });


        btnScenario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity().getApplicationContext(), HouseScenario.class);



                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                getActivity().getApplicationContext().startActivity(intent);

            }
        });

        nofbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), HouseNotify.class);



                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                getActivity().getApplicationContext().startActivity(intent);
            }
        });



        //startMqtt();

        return view;
    }



    //tcp://broker.mqttdashboard.com:1883




    private void startMqtt(){
        mqttHelper = new MQTTHelper(getActivity().getApplicationContext());



        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Log.d("Squirting1",s.toString());
            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.d("Squirting1","Mes: "+mqttMessage.toString());
                //dataReceived.setText(mqttMessage.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
// add your code here which executes when the Fragment gets visible.
        if(SetupData.lstNof.size() == 0){
            imgDot.setVisibility(View.INVISIBLE);
        }

        if(SetupData.lstNof.size() > 0){
            imgDot.setVisibility(View.VISIBLE);
        }
        Log.d("chich","Start");

    }

    @Override
    public void onPause() {
        super.onPause();
        //processMQTT.Disconnect();
// add your code here which executes when user leaving the current fragment or fragment is no longer intractable.
    }



    @Override
    public void onResume() {
        super.onResume();
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
