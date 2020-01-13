package com.example.smartapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class DeviceDetail extends AppCompatActivity {

    Button Red, Green, Blue, Yellow, Violet, Aqua, White, Power, More;
    public int clicked = 0;


    MQTTHelper mqttHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_detail);


        Red = (Button) findViewById(R.id.btnRed);
        Green = (Button) findViewById(R.id.btnGreen);
        Blue = (Button) findViewById(R.id.btnBlue);
        Yellow = (Button) findViewById(R.id.btnYellow);
        Violet = (Button) findViewById(R.id.btnViolet);
        Aqua = (Button) findViewById(R.id.btnAqua);
        White = (Button) findViewById(R.id.btnWhite);
        Power = (Button) findViewById(R.id.btnPower);
        More = (Button) findViewById(R.id.btnMore);






        startMqtt();


        Red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String ids = intent.getStringExtra("ID");
                String messeger = "255000000" + ids;




                    SentMessege(messeger);



                Log.d("qoobee","mes: "+messeger);
            }
        });


        Green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String ids = intent.getStringExtra("ID");
                String messeger = "000255000" + ids;

                SentMessege(messeger);
                Log.d("qoobee","mes: "+messeger);
            }
        });


        Blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String ids = intent.getStringExtra("ID");
                String messeger = "000000255" + ids;

                SentMessege(messeger);
                Log.d("qoobee","mes: "+messeger);
            }
        });

        Yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String ids = intent.getStringExtra("ID");
                String messeger = "255255000" + ids;

                SentMessege(messeger);
                Log.d("qoobee","mes: "+messeger);
            }
        });

        Yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String ids = intent.getStringExtra("ID");
                String messeger = "255000255" + ids;

                SentMessege(messeger);
                Log.d("qoobee","mes: "+messeger);
            }
        });

        Aqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String ids = intent.getStringExtra("ID");
                String messeger = "000255255" + ids;

                SentMessege(messeger);
                Log.d("qoobee","mes: "+messeger);
            }
        });

        White.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String ids = intent.getStringExtra("ID");
                String messeger = "255255255" + ids;

                SentMessege(messeger);
                Log.d("qoobee","mes: "+messeger);
            }
        });

        Power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String ids = intent.getStringExtra("ID");
                String messeger = "000000000" + ids;


                SentMessege(messeger);
                Log.d("qoobee","mes: "+messeger);
            }
        });


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
                    Toast.makeText(DeviceDetail.this, "FAIL", Toast.LENGTH_SHORT).show();

                }




            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    private void startMqtt(){
        mqttHelper = new MQTTHelper(DeviceDetail.this);



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
