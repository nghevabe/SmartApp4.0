package com.example.smartapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.smartapp.DeviceController.ProcessMQTT;
import com.example.smartapp.Main.TabAdapter;
import com.example.smartapp.Main.TabAssistant;
import com.example.smartapp.Main.TabDeviceController;
import com.example.smartapp.Main.TabHomeController;
import com.example.smartapp.Main.TabProfile;
import com.example.smartapp.Utils.SetupPermission;
import com.google.android.material.tabs.TabLayout;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    SetupPermission setupPermission = new SetupPermission();

    public static ProcessMQTT processMQTT = new ProcessMQTT();
   // public static ProcessMQTT processMQTT2 = new ProcessMQTT();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new TabProfile(), "Profile");
        adapter.addFragment(new TabAssistant(), "Assistant");
        adapter.addFragment(new TabHomeController(), "House");
        adapter.addFragment(new TabDeviceController(), "Device");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        View tab1 = getLayoutInflater().inflate(R.layout.custom_tab, null);
        tab1.findViewById(R.id.ivIcon).setBackgroundResource(R.drawable.ic_person_outline);
        tabLayout.getTabAt(0).setCustomView(tab1);

        View tab2 = getLayoutInflater().inflate(R.layout.custom_tab, null);
        tab2.findViewById(R.id.ivIcon).setBackgroundResource(R.drawable.ic_face_outline);
        tabLayout.getTabAt(1).setCustomView(tab2);

        View tab3 = getLayoutInflater().inflate(R.layout.custom_tab, null);
        tab3.findViewById(R.id.ivIcon).setBackgroundResource(R.drawable.ic_home_outline);
        tabLayout.getTabAt(2).setCustomView(tab3);

        View tab4 = getLayoutInflater().inflate(R.layout.custom_tab, null);
        tab4.findViewById(R.id.ivIcon).setBackgroundResource(R.drawable.ic_developer_board_outline);
        tabLayout.getTabAt(3).setCustomView(tab4);

        processMQTT.startMqtt(MainActivity.this);

//        String userToken = getIntent().getStringExtra("EXTRA_TOKEN");
//
//        Toast.makeText(MainActivity.this.getApplicationContext(), userToken, Toast.LENGTH_SHORT).show();


        setupPermission.SetupAudio(MainActivity.this);


    }

    public void TestSending(final String mes){

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





    }

    public void Send(final String messeger){


        String clientId = MqttClient.generateClientId();
        final MqttAndroidClient client =
                new MqttAndroidClient(MainActivity.this.getApplicationContext(), "tcp://broker.mqttdashboard.com:1883",
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
                    Toast.makeText(MainActivity.this.getApplicationContext(), "FAIL", Toast.LENGTH_SHORT).show();

                }




            });
        } catch (MqttException e) {
            e.printStackTrace();
        }





    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setupPermission.SetupCalender(MainActivity.this);
                    //ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_CALENDAR}, 2);
                }else {
                    return;
                }
                break;
        }
    }

}
