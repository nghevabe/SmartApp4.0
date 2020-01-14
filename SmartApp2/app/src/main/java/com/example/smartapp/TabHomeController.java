package com.example.smartapp;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class TabHomeController extends Fragment {


    RecyclerView rv;
    MQTTHelper mqttHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        MyRecyclerViewAdapter adapter;

        View view = inflater.inflate(R.layout.tab_controller, container, false);


        ArrayList<ElectricDevice> lstDevice = new ArrayList<>();


        ElectricDevice device1 = new ElectricDevice("1","Đèn ngủ","light","none");
        ElectricDevice device2 = new ElectricDevice("2","Đèn học","light","none");
        ElectricDevice device3 = new ElectricDevice("3","Đèn bếp","light","none");
        //ElectricDevice device4 = new ElectricDevice("4","Đèn sân","light","none");

        lstDevice.add(device1);
        lstDevice.add(device2);
        lstDevice.add(device3);
        //lstDevice.add(device4);

        RecyclerView recyclerView = view.findViewById(R.id.rv);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), numberOfColumns));



        adapter = new MyRecyclerViewAdapter(getActivity().getApplicationContext(), lstDevice);

        recyclerView.setAdapter(adapter);



        startMqtt();





        //startMqtt();

        return view;
    }



    public void SignalSending(final String mes){

        int timer = 400;

        for(int i=0;i<5;i++){

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Log.d("mqtt","Sending 1" );
                    Send(mes);


                }
            }, timer);

            timer = timer + 400;

        }

//dhvuddfk
        //


//home/garden/fountain

    }
    //tcp://broker.mqttdashboard.com:1883
    public void Send(final String messeger){


        String clientId = MqttClient.generateClientId();
        final MqttAndroidClient client =
                new MqttAndroidClient(getActivity().getApplicationContext(), "tcp://tailor.cloudmqtt.com:11359",
                        clientId);


        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected

                    String topic = "SMART_PROJECT/ESP_01";
                    String payload = messeger;
                    byte[] encodedPayload = new byte[0];
                    try {
                        encodedPayload = payload.getBytes("UTF-8");
                        MqttMessage message = new MqttMessage(encodedPayload);
                        client.publish(topic, message);
                    } catch (UnsupportedEncodingException | MqttException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Toast.makeText(getActivity().getApplicationContext(), "FAIL", Toast.LENGTH_SHORT).show();

                }




            });
        } catch (MqttException e) {
            e.printStackTrace();
        }





    }

    public void SentMessege(final String messeger){



        try {
            IMqttToken token = mqttHelper.mqttAndroidClient.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected

                    String topic = "SMART_PROJECT/ESP_01";
                    String payload = messeger;
                    byte[] encodedPayload = new byte[0];
                    try {
                        encodedPayload = payload.getBytes("UTF-8");
                        MqttMessage message = new MqttMessage(encodedPayload);
                        mqttHelper.mqttAndroidClient.publish(topic, message);
                    } catch (UnsupportedEncodingException | MqttException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Toast.makeText(getActivity().getApplicationContext(), "FAIL", Toast.LENGTH_SHORT).show();

                }




            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

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

}
