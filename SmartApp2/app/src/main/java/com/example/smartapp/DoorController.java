package com.example.smartapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.smartapp.MainActivity.processMQTT;

public class DoorController extends AppCompatActivity {

    Button btnOn, btnOff;
    ImageView btnBack;
    TextView txtStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.door_controller);


        btnOn =  findViewById(R.id.buttonOn);
        btnOff =  findViewById(R.id.buttonOff);
        btnBack =  findViewById(R.id.buttonBack);
        txtStatus = findViewById(R.id.tvStatus);




        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processMQTT.SentMessege("ESP_05","ON0",DoorController.this);
                txtStatus.setText("ON");
            }
        });


        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processMQTT.SentMessege("ESP_05","OFF0",DoorController.this);
                txtStatus.setText("OFF");
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
