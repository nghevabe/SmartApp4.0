package com.example.smartapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
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
    SeekBar seekBar;
    public int clicked = 0;
    public int valueProgress = 0;
    public float valuePower = 255;
    public String mes;
    public String ids;
    public int counter=0;


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

        seekBar = (SeekBar) findViewById(R.id.seekValue);

        Intent intent = getIntent();
        ids = intent.getStringExtra("ID");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
               // Log.d("xbox","Value: "+i);
                valueProgress = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d("xbox","Start ");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("xbox","progress "+valueProgress);



                valuePower = (float)255 / 100 * valueProgress;
                Log.d("xbox","value: "+valuePower);

                mes = CreateMesseger(mes);

                SentMessege(mes);



            }
        });






        startMqtt();


        Red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String strValue = Integer.toString((int)valuePower);

                if(strValue.length() == 1){
                    strValue = "00" + strValue;
                }

                if(strValue.length() == 2){
                    strValue = "0" + strValue;
                }

                mes = strValue + "000000" + ids;


                SentMessege(mes);



                Log.d("qoobee","mes: "+mes);
            }
        });


        Green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strValue = Integer.toString((int)valuePower);

                if(strValue.length() == 1){
                    strValue = "00" + strValue;
                }

                if(strValue.length() == 2){
                    strValue = "0" + strValue;
                }

                mes = "000" + strValue + "000" + ids;

                SentMessege(mes);
                Log.d("qoobee","mes: "+mes);
            }
        });


        Blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strValue = Integer.toString((int)valuePower);

                if(strValue.length() == 1){
                    strValue = "00" + strValue;
                }

                if(strValue.length() == 2){
                    strValue = "0" + strValue;
                }

                mes =  "000000" + strValue + ids;

                SentMessege(mes);
                Log.d("qoobee","mes: "+mes);
            }
        });

        Yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strValue = Integer.toString((int)valuePower);

                if(strValue.length() == 1){
                    strValue = "00" + strValue;
                }

                if(strValue.length() == 2){
                    strValue = "0" + strValue;
                }

                mes = strValue + strValue + "000" + ids;

                SentMessege(mes);
                Log.d("qoobee","mes: "+mes);
            }
        });

        Violet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strValue = Integer.toString((int)valuePower);

                if(strValue.length() == 1){
                    strValue = "00" + strValue;
                }

                if(strValue.length() == 2){
                    strValue = "0" + strValue;
                }

                mes = strValue + "000" + strValue + ids;

                SentMessege(mes);
                Log.d("qoobee","mes: "+mes);
            }
        });

        Aqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strValue = Integer.toString((int)valuePower);

                if(strValue.length() == 1){
                    strValue = "00" + strValue;
                }

                if(strValue.length() == 2){
                    strValue = "0" + strValue;
                }

                mes = "000" + strValue + strValue + ids;

                SentMessege(mes);
                Log.d("qoobee","mes: "+mes);
            }
        });

        White.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strValue = Integer.toString((int)valuePower);

                if(strValue.length() == 1){
                    strValue = "00" + strValue;
                }

                if(strValue.length() == 2){
                    strValue = "0" + strValue;
                }

                mes = strValue + strValue + strValue + ids;

                SentMessege(mes);
                Log.d("qoobee","mes: "+mes);
            }
        });

        Power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String messeger = "000000000" + ids;


                SentMessege(messeger);
                Log.d("qoobee","mes: "+messeger);
            }
        });


    }

    public String CreateMesseger(String mes){

        String result = "";

        String R = mes.substring(0,3);
        String G = mes.substring(3,6);
        String B = mes.substring(6,9);


        Log.d("xbox","mes: "+mes);
        Log.d("xbox","R: "+R);
        Log.d("xbox","G: "+G);
        Log.d("xbox","B: "+B);

        int valueR = Integer.parseInt(R);
        if(valueR > 0){
            valueR = (int)valuePower;

            R = Integer.toString(valueR);

            if(R.length() == 1){
                R = "00" + R;
            }

            if(R.length() == 2){
                R = "0" + R;
            }


        }

        int valueG = Integer.parseInt(G);
        if(valueG > 0){
            valueG = (int)valuePower;

            G = Integer.toString(valueG);

            if(G.length() == 1){
                G = "00" + G;
            }

            if(G.length() == 2){
                G = "0" + G;
            }


        }



        int valueB = Integer.parseInt(B);
        if(valueB > 0){
            valueB = (int)valuePower;

            B = Integer.toString(valueB);

            if(B.length() == 1){
                B = "00" + B;
            }

            if(B.length() == 2){
                B = "0" + B;
            }


        }


        result = R+G+B+ids;

        Log.d("xbox","HERE");

        Log.d("xbox","final mes: "+mes);

        return result;

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
                        //mqttHelper.mqttAndroidClient.disconnect();
                        Log.d("mabu","send");

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


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("lifecycle","onStart invoked");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifecycle","onResume invoked");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifecycle","onPause invoked");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lifecycle","onStop invoked");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("lifecycle","onRestart invoked");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mqttHelper.mqttAndroidClient.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        Log.d("lifecycle","onDestroy invoked");
    }

}
