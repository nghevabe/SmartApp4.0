package com.example.smartapp.SmsController;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.example.smartapp.DateTimeProcess;
import com.example.smartapp.DeviceController.SmartProcess;
import com.example.smartapp.StringProcess;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.util.Charsets;
import com.koushikdutta.ion.Ion;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static com.example.smartapp.MainActivity.processMQTT;

public class SmsReceiver extends BroadcastReceiver {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SMS_RECEIVED)) {

            //pdus để lấy gói tin nhắn
            String sms_extra = "pdus";
            Bundle bundle = intent.getExtras();
            //bundle trả về tập các tin nhắn gửi về cùng lúc
            Object[] objArr = (Object[]) bundle.get(sms_extra);
            String format = bundle.getString("format");

            String sms = "";
            //duyệt vòng lặp để đọc từng tin nhắn
            for (int i = 0; i < objArr.length; i++) {
                //lệnh chuyển đổi về tin nhắn createFromPdu

                SmsMessage smsMsg = SmsMessage.
                        createFromPdu((byte[]) objArr[i]);
                //lấy nội dung tin nhắn
                String body = smsMsg.getMessageBody();
                //lấy số điện thoại tin nhắn
                String address = smsMsg.getDisplayOriginatingAddress();
                sms = body;
            }

            Log.d("anal","sms: |"+sms+"| format: " + format);

            SmsProcess(sms,context);


        }
    }





    public void SmsProcess(final String mes, final Context context){


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String request = mes.toString();
                StringProcess stringProcess = new StringProcess();
                String request_decoded = stringProcess.DecoderRequest(request);
                Log.d("kimochi","x: "+request_decoded);
                SendRequest(request_decoded, context);
            }
        }, 1000);



    }


    public void SendRequest(final String request, final Context context) {
//http://assistanthome.somee.com/api/assistant?request=
        Ion.with(context)
                .load("http://assistanthome.somee.com/api/assistant?request=" + request+"&fbclid=IwAR0I_dhyPZ8NgVWFbNCOhH5uHbMqL8A8KxtEKxzFfJNtViOSt8Br0MahEnQ")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {


                        StringProcess stringProcess = new StringProcess();
                        Log.d("anal","THIS_01: |"+request+"|");
//bật%20đèn%20phòng%20khách
//http://assistanthome.somee.com/api/assistant?request=bật%20đèn%20phòng%20khách
//http://assistanthome.somee.com/api/assistant?request=bật%20đèn%20phòng%20khách

                            if (stringProcess.getType(result).contains("Controller")) {
                                Log.d("anal","THIS_02");


                                //SentMessege("2550000001");
                                SmartProcess smartProcess = new SmartProcess();
                                String command_type = stringProcess.getTitle(result);
                                String device_name = stringProcess.getContend(result);

                                String mes = smartProcess.SendMesseger(command_type,device_name);
                                if(device_name.contains("quạt"))
                                {
                                    processMQTT.SentMessege("ESP_02",mes, context);
                                }

                                if(device_name.contains("đèn"))
                                {
                                    processMQTT.SentMessege("ESP_01", mes, context);
                                }



                            }






                    }
                });

    }

}