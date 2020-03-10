package com.example.smartapp;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.smartapp.DeviceController.ProcessMedia;
import com.google.android.material.tabs.TabLayout;

import static com.example.smartapp.MainActivity.processMQTT;

public class HouseScenario extends AppCompatActivity {

    //Button btn_01, btn_02, btn_03, btn_04, btn_05;
    ImageView btnBack;
    Switch swtWelcome, swtRelax, swtSleep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_scenario);

        btnBack = findViewById(R.id.buttonBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        swtWelcome = findViewById(R.id.switch_welcome);
        swtRelax = findViewById(R.id.switch_relax);
        swtSleep = findViewById(R.id.switch_sleep);

        swtWelcome.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b == true){

                    // Turn On Led White
                    processMQTT.SentMessege("ESP_01","2552552553", HouseScenario.this);


                    //

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            // Open Door
                            processMQTT.SentMessege("ESP_04","ON0", HouseScenario.this);

                        }
                    }, 1000);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            // Turn On Glass
                            processMQTT.SentMessege("ESP_03","ON0", HouseScenario.this);

                        }
                    }, 2000);

                }

                if(b == false){



                }

            }
        });


        swtRelax.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b == true){

                    // Turn on music relax
                    ProcessMedia processMedia = new ProcessMedia();
                    processMedia.initializeMediaPlayer("https://aredir.nixcdn.com/NhacCuaTui921/NhacThienTinhTamHoaSenNuocChay-VA-4428738.mp3?st=wQ1kAaGJOCZehaYStO9Tmw&e=1583828309",HouseScenario.this);


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            // Turn on Led Aqua
                            processMQTT.SentMessege("ESP_01","0001001003", HouseScenario.this);

                        }
                    }, 1000);
                    //https://aredir.nixcdn.com/NhacCuaTui921/NhacThienTinhTamHoaSenNuocChay-VA-4428738.mp3?st=wQ1kAaGJOCZehaYStO9Tmw&e=1583828309

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            // Close Door
                            processMQTT.SentMessege("ESP_04","OFF0", HouseScenario.this);

                        }
                    }, 2000);


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            // Turn off glass.
                            processMQTT.SentMessege("ESP_03","OFF0", HouseScenario.this);

                        }
                    }, 3000);
                }


                if(b == false){

                }

            }
        });


        swtSleep.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b == true){

                    // Bật nhạc ru ngủ
                    ProcessMedia processMedia = new ProcessMedia();
                    processMedia.initializeMediaPlayer("https://aredir.nixcdn.com/NhacCuaTui931/NhacKhongLoiNhacDeNgu-VA-4682853.mp3?st=CoqzQ3a69ZSrpY3YVqwxkQ&e=1583829559",HouseScenario.this);

                    //https://aredir.nixcdn.com/NhacCuaTui931/NhacKhongLoiNhacDeNgu-VA-4682853.mp3?st=CoqzQ3a69ZSrpY3YVqwxkQ&e=1583829559

                    // Xử lý đèn tối dần(Dimming) chỗ này

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            // Close Door
                            processMQTT.SentMessege("ESP_04","OFF0", HouseScenario.this);

                        }
                    }, 1000);


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            // Turn off glass.
                            processMQTT.SentMessege("ESP_03","OFF0", HouseScenario.this);

                        }
                    }, 2000);

                }

                if(b == false){

                }

            }
        });



    }


}
