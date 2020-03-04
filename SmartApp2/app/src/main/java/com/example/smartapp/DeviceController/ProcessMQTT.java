package com.example.smartapp.DeviceController;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.smartapp.HouseNotify;
import com.example.smartapp.MQTTHelper;
import com.example.smartapp.Notificationer;
import com.example.smartapp.NotifyAdapter;
import com.example.smartapp.R;
import com.example.smartapp.SetupData;
import com.example.smartapp.TabHomeController;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ProcessMQTT {


    MQTTHelper mqttHelper;

    Notificationer notificationer;

    public static int rev = 0;

    int id_count = 0;


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

    private void addNotification(Context context, String mes) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.iconapp)
                        .setContentTitle("MQTT Messeger")
                        .setContentText(mes);

        Intent notificationIntent = new Intent(context, ProcessMQTT.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    public void startMqtt(final Context context){

            mqttHelper = new MQTTHelper(context);
            //mqttHelper.Listener("SMART_PROJECT/ESP_02");
//

            mqttHelper.setCallback(new MqttCallbackExtended() {
                @Override
                public void connectComplete(boolean b, String s) {
                    Log.d("Squirting_connect", s.toString());

                }

                @Override
                public void connectionLost(Throwable throwable) {

                }

                @Override
                public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                    Log.d("Squirting_mes", "Mes: " + mqttMessage.toString()+ " Topic: "+topic.toString());
                    //Log.d("Squirting_mes", "OK");
                    //dataReceived.setText(mqttMessage.toString());

                   // NotifyAdapter notifyAdapter;




                    notificationer = new Notificationer(""+id_count,"Air","chất lượng không khí: "+mqttMessage.toString());
                    id_count++;

                    SetupData.lstNof.add(notificationer);

                    if(SetupData.notify_list_started == 1) {
                        SetupData.notifyAdapter = new NotifyAdapter(context, SetupData.lstNof);
                        HouseNotify.recyclerView.setAdapter(SetupData.notifyAdapter);
                    }

                    if(SetupData.lstNof.size() == 0){
                        TabHomeController.imgDot.setVisibility(View.INVISIBLE);
                    }

                    if(SetupData.lstNof.size() > 0){
                        TabHomeController.imgDot.setVisibility(View.VISIBLE);
                    }

                    //TabHomeController.imgDot.setVisibility(View.VISIBLE);

                    addNotification(context,topic+" : "+mqttMessage.toString());
                    rev = 1;
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

                }
            });

    }

}
