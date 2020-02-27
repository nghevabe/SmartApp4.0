package com.example.smartapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HouseSetup extends AppCompatActivity {

    Button btnSmart;

    int counter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_setup);

        btnSmart = findViewById(R.id.buttonSmart);

        btnSmart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                counter++;

                if(counter > 2){
                    counter = 1;
                }

                if(counter == 1){
                    btnSmart.setBackgroundResource(R.drawable.button_fill);
                    btnSmart.setTextColor(getResources().getColor(R.color.colorCard));
                    btnSmart.setText("Active");
                }

                if(counter == 2){
                    btnSmart.setBackgroundResource(R.drawable.button_nofill);
                    btnSmart.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    btnSmart.setText("Deactive");
                }


            }
        });

    }
}
