package com.example.smartapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.smartapp.MainActivity.processMQTT;

public class LightController extends AppCompatActivity {

    //Button Red, Green, Blue, Yellow, Violet, Aqua, White, Power, More, Back;
    TextView tv_devicename, tv_color;
    SeekBar seekBar;
    public int clicked = 0;
    public int valueProgress = 0;
    public static float valuePower = 255;
    public static String mes;
    //public String ids;
    public static int color_custom=0;
    public static String electricDeviceId;
    public static String electricDeviceName;




    MQTTHelper mqttHelper;
    //ProcessMQTT processMQTT = new ProcessMQTT();

    private RecyclerView recyclerView;
    private ArrayList<ColorModel> imageModelArrayList;
    private ColorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.light_controller);



        ArrayList<ColorModel> lstColor = new ArrayList<>();

        ColorModel colorModel1 = new ColorModel("255000000" + electricDeviceId,"Red");
        ColorModel colorModel2 = new ColorModel("000255000" + electricDeviceId,"Green");
        ColorModel colorModel3 = new ColorModel("000000255" + electricDeviceId,"Blue");
        ColorModel colorModel4 = new ColorModel("255255000" + electricDeviceId,"Yellow");
        ColorModel colorModel5 = new ColorModel("255000255" + electricDeviceId,"Violet");
        ColorModel colorModel6 = new ColorModel("000255255" + electricDeviceId,"Aqua");
        ColorModel colorModel7 = new ColorModel("255155255" + electricDeviceId,"White");

        lstColor.add(colorModel1);
        lstColor.add(colorModel2);
        lstColor.add(colorModel3);
        lstColor.add(colorModel4);
        lstColor.add(colorModel5);
        lstColor.add(colorModel6);
        lstColor.add(colorModel7);
        /*
        Red = (Button) findViewById(R.id.btnRed);
        Green = (Button) findViewById(R.id.btnGreen);
        Blue = (Button) findViewById(R.id.btnBlue);
        Yellow = (Button) findViewById(R.id.btnYellow);
        Violet = (Button) findViewById(R.id.btnViolet);
        Aqua = (Button) findViewById(R.id.btnAqua);
        White = (Button) findViewById(R.id.btnWhite);
        Power = (Button) findViewById(R.id.btnPower);
        More = (Button) findViewById(R.id.btnMore);

        Back = (Button) findViewById(R.id.btnBack);
        */

        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        adapter = new ColorAdapter(this, lstColor);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        tv_devicename = (TextView) findViewById(R.id.nameDevice);
        tv_color = (TextView) findViewById(R.id.txtColor);

        seekBar = (SeekBar) findViewById(R.id.seekValue);



        Bundle bundle = getIntent().getExtras();
        final String Id = bundle.getString("ID");
        final String Name_device = bundle.getString("NAME");
        tv_devicename.setText(Name_device);

        mes = "255255255" + Id;



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // Log.d("xbox","Value: "+i);
                valueProgress = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d("xbox","Start ");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if(color_custom==0) {
                    Log.d("xbox", "progress " + valueProgress);


                    valuePower = (float) 255 / 100 * valueProgress;
                    Log.d("xbox", "value: " + valuePower);

                    mes = CreateMesseger(mes) + Id;

                    processMQTT.SentMessege("ESP_01",mes,LightController.this);
                }

                if(color_custom==1){
                    Toast.makeText(LightController.this, "Can't set Brightness when customed color", Toast.LENGTH_SHORT).show();
                }



            }
        });

        //processMQTT.Disconnect();



        if(color_custom == 1){
            GetColorCustom(Id);
        } else {
            //processMQTT.Disconnect();

        }


        /*
        Red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                color_custom = 0;

                String strValue = Integer.toString((int)valuePower);

                if(strValue.length() == 1){
                    strValue = "00" + strValue;
                }

                if(strValue.length() == 2){
                    strValue = "0" + strValue;
                }

                mes = strValue + "000000" + Id;


                processMQTT.SentMessege("ESP_01",mes,LightController.this);


                tv_color.setText("Red");
                Log.d("qoobee","mes: "+mes);
            }
        });


        Green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                color_custom = 0;

                String strValue = Integer.toString((int)valuePower);

                if(strValue.length() == 1){
                    strValue = "00" + strValue;
                }

                if(strValue.length() == 2){
                    strValue = "0" + strValue;
                }

                mes = "000" + strValue + "000" + Id;

                processMQTT.SentMessege("ESP_01",mes,LightController.this);

                tv_color.setText("Green");
                Log.d("qoobee","mes: "+mes);
            }
        });


        Blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                color_custom = 0;

                String strValue = Integer.toString((int)valuePower);

                if(strValue.length() == 1){
                    strValue = "00" + strValue;
                }

                if(strValue.length() == 2){
                    strValue = "0" + strValue;
                }

                mes =  "000000" + strValue + Id;

                processMQTT.SentMessege("ESP_01",mes,LightController.this);

                tv_color.setText("Blue");
                Log.d("qoobee","mes: "+mes);
            }
        });

        Yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                color_custom = 0;

                String strValue = Integer.toString((int)valuePower);

                if(strValue.length() == 1){
                    strValue = "00" + strValue;
                }

                if(strValue.length() == 2){
                    strValue = "0" + strValue;
                }

                mes = strValue + strValue + "000" + Id;

                processMQTT.SentMessege("ESP_01",mes,LightController.this);

                tv_color.setText("Yellow");
                Log.d("qoobee","mes: "+mes);
            }
        });

        Violet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                color_custom = 0;

                String strValue = Integer.toString((int)valuePower);

                if(strValue.length() == 1){
                    strValue = "00" + strValue;
                }

                if(strValue.length() == 2){
                    strValue = "0" + strValue;
                }

                mes = strValue + "000" + strValue + Id;

                processMQTT.SentMessege("ESP_01",mes,LightController.this);

                tv_color.setText("Violet");
                Log.d("qoobee","mes: "+mes);
            }
        });

        Aqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                color_custom = 0;

                String strValue = Integer.toString((int)valuePower);

                if(strValue.length() == 1){
                    strValue = "00" + strValue;
                }

                if(strValue.length() == 2){
                    strValue = "0" + strValue;
                }

                mes = "000" + strValue + strValue + Id;

                processMQTT.SentMessege("ESP_01",mes,LightController.this);

                tv_color.setText("Aqua");
                Log.d("qoobee","mes: "+mes);
            }
        });

        White.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                color_custom = 0;

                String strValue = Integer.toString((int)valuePower);

                if(strValue.length() == 1){
                    strValue = "00" + strValue;
                }

                if(strValue.length() == 2){
                    strValue = "0" + strValue;
                }

                mes = strValue + strValue + strValue + Id;

                processMQTT.SentMessege("ESP_01",mes,LightController.this);

                tv_color.setText("White");
                Log.d("qoobee","mes: "+mes);
            }
        });

        Power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                color_custom = 0;

                String messeger = "000000000" + Id;


                processMQTT.SentMessege("ESP_01",messeger,LightController.this);

                tv_color.setText("Off");
                Log.d("qoobee","mes: "+messeger);
            }
        });

        More.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LightController.this, ColorPicker.class);

                Bundle bundle = new Bundle();
                bundle.putString("ID", Id);
                bundle.putString("NAME", Name_device);
                intent.putExtras(bundle);

                startActivity(intent);
                //finish();
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
*/

    }

    public void GetColorCustom(String Id){



        Bundle bundle = getIntent().getExtras();


        String R = bundle.getString("R");
        String G = bundle.getString("G");
        String B = bundle.getString("B");

        if(R.length() == 1){
            R = "00" + R;
        }

        if(R.length() == 2){
            R = "0" + R;
        }


        if(G.length() == 1){
            G = "00" + G;
        }

        if(G.length() == 2){
            G = "0" + G;
        }


        if(B.length() == 1){
            B = "00" + B;
        }

        if(B.length() == 2){
            B = "0" + B;
        }

        mes = R+G+B + Id;

        processMQTT.SentMessege("ESP_01",mes,LightController.this);

        tv_color.setText("Color Custom");

        Log.d("chich","mes: "+mes);


    }

    public String CreateMesseger(String mes){

        String result = "";

        String R = mes.substring(0,3);
        String G = mes.substring(3,6);
        String B = mes.substring(6,9);


        Log.d("xbox","mes: "+mes);
        Log.d("xbox","R: "+R);
        Log.d("xbox","G: "+G);
        Log.d("xbox","B: "+B);

        int valueR = Integer.parseInt(R);
        if(valueR > 0){
            valueR = (int)valuePower;

            R = Integer.toString(valueR);

            if(R.length() == 1){
                R = "00" + R;
            }

            if(R.length() == 2){
                R = "0" + R;
            }


        }

        int valueG = Integer.parseInt(G);
        if(valueG > 0){
            valueG = (int)valuePower;

            G = Integer.toString(valueG);

            if(G.length() == 1){
                G = "00" + G;
            }

            if(G.length() == 2){
                G = "0" + G;
            }


        }



        int valueB = Integer.parseInt(B);
        if(valueB > 0){
            valueB = (int)valuePower;

            B = Integer.toString(valueB);

            if(B.length() == 1){
                B = "00" + B;
            }

            if(B.length() == 2){
                B = "0" + B;
            }


        }


        result = R+G+B;

        Log.d("xbox","HERE");

        Log.d("xbox","final mes: "+mes);

        return result;

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
        Log.d("lifecycle","onDestroy invoked");
    }
}
