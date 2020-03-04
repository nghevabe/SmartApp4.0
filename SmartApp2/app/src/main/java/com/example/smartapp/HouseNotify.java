package com.example.smartapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class HouseNotify extends AppCompatActivity {

    public static RecyclerView recyclerView;
    public static TextView realtxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_notify);

        //NotifyAdapter notifyAdapter;

        SetupData.notify_list_started = 1;

        recyclerView = findViewById(R.id.rv);

        realtxt = findViewById(R.id.realtimeText);

        recyclerView.setHasFixedSize(true);

        ArrayList<Notificationer> lstNof = new ArrayList<>();


        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        SetupData.notifyAdapter = new NotifyAdapter(HouseNotify.this, SetupData.lstNof);

        recyclerView.setAdapter(SetupData.notifyAdapter);



        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                SetupData.lstNof.clear();

            }
        }, 1000);


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
        //processMQTT.Disconnect();
        Log.d("lifecycle","onPause invoked");
    }
    @Override
    protected void onStop() {
        super.onStop();
        //SetupData.lstNof.clear();
        //processMQTT.Disconnect();
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
        //processMQTT.Disconnect();
        //SetupData.lstNof.clear();
        Log.d("lifecycle","anal1");
    }
// đặt lịch ngày mai tập thể dục vào 9 giờ sáng
    // đặt lịch đi đám cưới thằng bạn của tôi vào lúc 12 giờ trưa mai tại khách sạn hà nội
    // nhắc tôi đi đám cưới thằng bạn tôi vào lúc 12 giờ trưa mai tại khách sạn hà nội
}
