package com.example.smartapp.DeviceController;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.smartapp.MQTTHelper;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class ProcessMQTT {


    MQTTHelper mqttHelper;


    public void SentMessege(final String Node,final String messeger, final Context context){



        try {
            IMqttToken token = mqttHelper.mqttAndroidClient.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected

                    String topic = "SMART_PROJECT/"+Node;
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
                    Toast.makeText(context, "FAIL", Toast.LENGTH_SHORT).show();

                }




            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    public void Disconnect(){
         if(mqttHelper.mqttAndroidClient.isConnected()) {
                  Log.d("memay","KET NOI");
             try {
                 mqttHelper.mqttAndroidClient.disconnect();
             } catch (MqttException e) {
                 e.printStackTrace();
             }
         }

    }

    public void startMqtt(Context context){

            mqttHelper = new MQTTHelper(context);


            mqttHelper.setCallback(new MqttCallbackExtended() {
                @Override
                public void connectComplete(boolean b, String s) {
                    Log.d("Squirting1", s.toString());
                }

                @Override
                public void connectionLost(Throwable throwable) {

                }

                @Override
                public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                    Log.d("Squirting1", "Mes: " + mqttMessage.toString());
                    //dataReceived.setText(mqttMessage.toString());
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

                }
            });

    }

}
