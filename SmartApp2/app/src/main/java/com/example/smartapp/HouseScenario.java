package com.example.smartapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class HouseScenario extends AppCompatActivity {

    Button btn_01, btn_02, btn_03, btn_04, btn_05;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_scenario);


        btn_01 = findViewById(R.id.button01);
        btn_02 = findViewById(R.id.button02);
        btn_03 = findViewById(R.id.button03);
        btn_04 = findViewById(R.id.button04);
        btn_05 = findViewById(R.id.button05);


        btn_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HouseScenario.this, "Home Coming Scenario", Toast.LENGTH_SHORT).show();
            }
        });

        btn_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HouseScenario.this, "Welcome Scenario", Toast.LENGTH_SHORT).show();
            }
        });

        btn_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HouseScenario.this, "Relax Scenario", Toast.LENGTH_SHORT).show();
            }
        });

        btn_04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HouseScenario.this, "Sleep Scenario", Toast.LENGTH_SHORT).show();
            }
        });

        btn_05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


}
