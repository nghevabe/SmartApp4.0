package com.example.smartapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import static com.example.smartapp.MainActivity.processMQTT;

public class GlassController extends AppCompatActivity {

    Button btnOn, btnOff;
    SeekBar powerBar;
    TextView txtStatus;
    ImageView btnBack;

    public int valueProgress = 0;
    public float valuePower = 999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.glass_controller);


        btnOn =  findViewById(R.id.buttonOn);
        btnOff =  findViewById(R.id.buttonOff);
        btnBack =  findViewById(R.id.buttonBack);
        txtStatus = findViewById(R.id.tvStatus);

        powerBar = (SeekBar) findViewById(R.id.seekBar);



        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processMQTT.SentMessege("ESP_04","ON0",GlassController.this);
                txtStatus.setText("ON");
            }
        });


        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processMQTT.SentMessege("ESP_04","OFF0",GlassController.this);
                txtStatus.setText("OFF");
            }
        });

           btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        powerBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                valueProgress = i;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                valuePower = (float) 999 / 100 * valueProgress;
                Log.d("xbox", "value: " + valuePower);

                String mes = (int)valuePower + "0";
                processMQTT.SentMessege("ESP_04",mes,GlassController.this);

            }
        });



    }
}
