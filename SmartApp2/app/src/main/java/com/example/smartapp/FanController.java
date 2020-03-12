package com.example.smartapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import static com.example.smartapp.MainActivity.processMQTT;

public class FanController extends AppCompatActivity {

    //Button btnOn, btnOff, btnBack;
    SeekBar powerBar;

    public int valueProgress = 0;
    public float valuePower = 700;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fan_controller);

        /*
        btnOn = (Button) findViewById(R.id.buttonOn);
        btnOff = (Button) findViewById(R.id.buttonOff);
        btnBack = (Button) findViewById(R.id.buttonBack);
        */
        powerBar = (SeekBar) findViewById(R.id.seekBar);

        /*
        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processMQTT.SentMessege("ESP_02","ON0",FanController.this);
            }
        });


        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processMQTT.SentMessege("ESP_02","OFF0",FanController.this);
            }
        });

          btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
*/

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

                if(valueProgress == 0){
                    valuePower = 0;
                }

                if(valueProgress != 0){
                    valuePower = (float) 250 + (700 / 100 * valueProgress);
                }

                Log.d("xbox", "value: " + valuePower);

                String mes = (int)valuePower + "0";
                processMQTT.SentMessege("ESP_02",mes,FanController.this);

            }
        });


    }
}
