package com.example.smartapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.example.smartapp.MainActivity.processMQTT;

public class DoorController extends AppCompatActivity {

    Button btnOn, btnOff, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.door_controller);

        btnOn = (Button) findViewById(R.id.buttonOn);
        btnOff = (Button) findViewById(R.id.buttonOff);
        btnBack = (Button) findViewById(R.id.buttonBack);

        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processMQTT.SentMessege("ESP_04","ON0",DoorController.this);
            }
        });


        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processMQTT.SentMessege("ESP_04","OFF0",DoorController.this);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
