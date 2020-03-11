package com.example.smartapp;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CalendarContract;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartapp.DeviceController.ProcessMQTT;
import com.example.smartapp.DeviceController.SmartProcess;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.example.smartapp.MainActivity.processMQTT;


public class TabAssistant extends Fragment {

    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;

    Button btn, voicebtn;
    TextView tv;
    EditText ed;
    NestedScrollView s;

    RecyclerView mRecyclerView;
    MessageListAdapter mRcvAdapter;

    BroadcastReceiver receiver = null;

    protected static final int RESULT_SPEECH = 5;
    public TextToSpeech t1;
    public MediaPlayer mPlayer = new MediaPlayer();

    public int started = 0;

    ArrayList<Message> lstMes = new ArrayList<Message>();

    //MQTTHelper mqttHelper;
    //ProcessMQTT processMQTT = new ProcessMQTT();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_assistant, container, false);

        btn = (Button) view.findViewById(R.id.sendBtn);
        voicebtn = (Button) view.findViewById(R.id.voiceBtn);


        ed = (EditText) view.findViewById(R.id.edInput);



        mRecyclerView = (RecyclerView) view.findViewById(R.id.recView);
        mRecyclerView.setHasFixedSize(true);



        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);

        mRcvAdapter = new MessageListAdapter(lstMes, getActivity().getApplicationContext());

        mRecyclerView.setAdapter(mRcvAdapter);

        processMQTT.startMqtt(getActivity().getApplicationContext());





// văn mai hương là ai

        t1 = new TextToSpeech(getActivity().getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.getDefault());
                }
            }
        });




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                started = 0;

                String texter = ed.getText().toString();
                AddMes(texter, "send");


            }
        });

        SetupVoice();

        voicebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //addToCalendar(MainActivity.this,"Test Lich 2",5,7);


                started=0;
                if(mPlayer.isPlaying()){
                    mPlayer.stop();
                }

                mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                //GetVoice();

            }
        });

        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {

            @Override
            public void onBeginningOfSpeech() {
                // TODO Auto-generated method stub
                Log.d("anal","Begin");

            }

            @Override
            public void onBufferReceived(byte[] arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onEndOfSpeech() {
                // TODO Auto-generated method stub
                Log.d("anal","End");

            }

            @Override
            public void onError(int arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onEvent(int arg0, Bundle arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onReadyForSpeech(Bundle params) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onResults(Bundle results) {
                // TODO Auto-generated method stub

                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                Log.d("anal","Ket qua: "+matches.get(0).toString());


                String out = matches.get(0);

                out = out.toLowerCase();

                AddMes(out,"send");

                Log.d("anal","result|"+out+"|");

            }

            @Override
            public void onRmsChanged(float rmsdB) {
                // TODO Auto-generated method stub

            }

        });


        return view;



    }





    public void GetVoice() {
        Intent intent = new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.getDefault());
        try {
            startActivityForResult(intent, RESULT_SPEECH);

        } catch (ActivityNotFoundException a) {
            Toast.makeText(getActivity().getApplicationContext(),
                    "Your device doesn't support Speech to Text",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void SendRequest(final String request) {
//http://assistanthome.somee.com/api/assistant?request=
        Ion.with(this)
                .load("http://assistanthome.somee.com/api/assistant?request=" + request)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {


                        StringProcess stringProcess = new StringProcess();

                        if (started == 0) {
                            Log.d("xResult", result);

                            //Weather

                            if (stringProcess.getType(result).contains("Weather")) {

                                AddMes(stringProcess.getContend(result), "receive");
                                t1.speak(stringProcess.getContend(result), TextToSpeech.QUEUE_FLUSH, null);

                            }

                            if (stringProcess.getType(result).contains("Wiki")) {

                                AddMes(stringProcess.getContendInfo(result), "receive");
                                t1.speak(stringProcess.getContendInfo(result), TextToSpeech.QUEUE_FLUSH, null);

                            }

// mở cho tôi bài hát xuân đã về
                            // văn mai hương là ai
                            //Media
                            if (stringProcess.getType(result).contains("Media")) {

                                AddMes("Đang phát bài '" + stringProcess.getTitle(result) + "'", "receive");
                                //PlayMedia(stringProcess.getContend(result));
                                initializeMediaPlayer(stringProcess.getContend(result));

                            }

                            //Reminder
                            if (stringProcess.getType(result).contains("Reminder")) {

                                AddMes("Đã thêm '" + stringProcess.getContend(result) + "'" + " lúc " + stringProcess.getTimer(result) + " vào Lịch", "receive");
                                //AddEvent();
                                DateTimeProcess dateTimeProcess = new DateTimeProcess();
                                dateTimeProcess.GetTimeForEvent(stringProcess.getTimer(result));
                                Log.d("ass","year: "+dateTimeProcess.getsYear() + " month: "+dateTimeProcess.getsMonth() + " day: "+dateTimeProcess.getsDay()
                                        + " hour: "+dateTimeProcess.getsHour() + " minute: "+dateTimeProcess.getsMinute());
                                AddEvent(stringProcess.getContend(result),dateTimeProcess.getsYear(),dateTimeProcess.getsMonth(),dateTimeProcess.getsDay(),dateTimeProcess.getsHour(),dateTimeProcess.getsMinute());
                            }

                            if (stringProcess.getType(result).contains("Controller")) {

                                AddMes("Đang thực hiện", "receive");

                                //SentMessege("2550000001");
                                SmartProcess smartProcess = new SmartProcess();
                                String command_type = stringProcess.getTitle(result);
                                String device_name = stringProcess.getContend(result);

                                String mes = smartProcess.SendMesseger(command_type,device_name);
                                if(device_name.contains("quạt"))
                                {
                                    processMQTT.SentMessege("ESP_02",mes, getActivity().getApplicationContext());
                                }

                                if(device_name.contains("đèn")) {
                                    processMQTT.SentMessege("ESP_01", mes, getActivity().getApplicationContext());
                                }



                            }

                            if (stringProcess.getType(result).contains("None")) {

                                AddMes("Không thể thực hiện yêu cầu", "receive");

                            }

                        }

                        started++;


                    }
                });

    }





    private void initializeMediaPlayer(final String link) {
        String url = link;
        mPlayer = new MediaPlayer();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mPlayer.setAudioAttributes(new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                    .build());
        } else {
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
        try {
            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    Toast.makeText(getActivity().getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
                }
            });
            mPlayer.setDataSource(url);
            mPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("anal", e.toString());
        }
    }



    public void AddMes(final String mes, String type) {

        Message message = new Message(mes, type);
        lstMes.add(message);


        Log.d("anal", "count: " + lstMes.size());

        mRcvAdapter = new MessageListAdapter(lstMes, getActivity().getApplicationContext());


        mRecyclerView.setAdapter(mRcvAdapter);

        mRecyclerView.smoothScrollToPosition(lstMes.size() - 1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String request = mes.toString();
                StringProcess stringProcess = new StringProcess();
                String request_decoded = stringProcess.DecoderRequest(request);
                SendRequest(request_decoded);
            }
        }, 500);



    }

    public void AddEvent(String contend, int year, int month, int day, int hour, int minute) {
        //  1/6/2020 12:16:00 PM
        long calID = 3;
        long startMillis = 0;
        long endMillis = 0;
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(year, month -1, day , hour, minute);
        startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(year, month -1, day , hour, minute + 20);
        endMillis = endTime.getTimeInMillis();

        ContentResolver cr = getActivity().getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.TITLE, contend);
        values.put(CalendarContract.Events.DESCRIPTION, "Group workout");
        values.put(CalendarContract.Events.CALENDAR_ID, calID);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);

// get the event ID that is the last element in the Uri
        long eventID = Long.parseLong(uri.getLastPathSegment());
        Log.d("ooo","ID: "+eventID);

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == getActivity().RESULT_OK && null != data) {
                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    String out = text.get(0);

                    out = out.toLowerCase();

                    AddMes(out,"send");

                    Log.d("anal","result|"+out+"|");











                }
                break;
            }
        }
    }

    public void SetupVoice(){

        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }

        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(getActivity().getApplicationContext());
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                getActivity().getApplicationContext().getPackageName());

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
        Log.d("THEODOI","RESUME");
// add your code here which executes when user leaving the current fragment or fragment is no longer intractable.
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("THEODOI","STOP");
        //processMQTT.Disconnect();
// add your code here which executes Fragment going to be stopped.
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        processMQTT.Disconnect();
// add your code here which executes when the final clean up for the Fragment's state is needed.
    }

}