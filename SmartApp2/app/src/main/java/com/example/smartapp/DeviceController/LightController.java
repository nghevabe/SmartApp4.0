package com.example.smartapp.DeviceController;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartapp.ColorSelector.ColorAdapter;
import com.example.smartapp.Model.ColorModel;
import com.example.smartapp.ColorSelector.ColorPicker;
import com.example.smartapp.R;
import com.example.smartapp.Main.TabDeviceController;

import java.util.ArrayList;

import static com.example.smartapp.MainActivity.processMQTT;

public class LightController extends AppCompatActivity {

    Button btnOn, btnOff;
    ImageView More;
    ImageView Power, btnBack, btnDisconnect;
    TextView tv_devicename, txtStatus;
    public static TextView tv_color;

    SeekBar seekBar;
    public int clicked = 0;
    public int valueProgress = 0;
    public static float valuePower = 255;
    public static String mes;
    //public String ids;
    public static int color_custom=0;
    public static String electricDeviceId;
    public static String electricDeviceName;
    public static String electricDeviceNode;

    private RecyclerView recyclerView;
    private ArrayList<ColorModel> imageModelArrayList;
    private ColorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.light_controller);

        More = findViewById(R.id.btnMore);
        btnOn =  findViewById(R.id.buttonOn);
        btnOff =  findViewById(R.id.buttonOff);
        //Power = findViewById(R.id.btnPower);
        btnBack = findViewById(R.id.buttonBack);
        btnDisconnect = findViewById(R.id.buttonDisconnect);
        txtStatus = findViewById(R.id.tvStatus);

        ArrayList<ColorModel> lstColor = new ArrayList<>();

        ColorModel colorModel1 = new ColorModel("255000000" + electricDeviceId,"Red");
        ColorModel colorModel2 = new ColorModel("000255000" + electricDeviceId,"Green");
        ColorModel colorModel3 = new ColorModel("000000255" + electricDeviceId,"Blue");
        ColorModel colorModel4 = new ColorModel("255255000" + electricDeviceId,"Yellow");
        ColorModel colorModel5 = new ColorModel("255000255" + electricDeviceId,"Violet");
        ColorModel colorModel6 = new ColorModel("000255255" + electricDeviceId,"Aqua");
        ColorModel colorModel7 = new ColorModel("255255255" + electricDeviceId,"White");

        lstColor.add(colorModel1);
        lstColor.add(colorModel2);
        lstColor.add(colorModel3);
        lstColor.add(colorModel4);
        lstColor.add(colorModel5);
        lstColor.add(colorModel6);
        lstColor.add(colorModel7);
        
        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        adapter = new ColorAdapter(this, lstColor);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        tv_devicename = (TextView) findViewById(R.id.nameDevice);
        tv_color = (TextView) findViewById(R.id.txtColor);

        seekBar = (SeekBar) findViewById(R.id.seekValue);

        if(TabDeviceController.turnIt == 0) {
            btnDisconnect.setVisibility(View.GONE);
        }

        tv_devicename.setText(electricDeviceName);

        mes = "255255255" + electricDeviceId;

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

                    mes = CreateMesseger(mes) + electricDeviceId;

                    processMQTT.SentMessege(electricDeviceNode,mes,LightController.this);
                }

                if(color_custom==1){
                    Toast.makeText(LightController.this, "Can't set Brightness when customed color", Toast.LENGTH_SHORT).show();
                }



            }
        });

        if(color_custom == 1){
            GetColorCustom(electricDeviceId);
        }

        btnDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialogDisconnect();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        More.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LightController.this, ColorPicker.class);

                Bundle bundle = new Bundle();
                bundle.putString("ID", electricDeviceId);
                bundle.putString("NAME", electricDeviceName);
                intent.putExtras(bundle);

                startActivity(intent);
                //finish();
            }
        });

        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processMQTT.SentMessege(electricDeviceNode,"255255255"+electricDeviceId,LightController.this);
                txtStatus.setText("ON");
            }
        });


        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processMQTT.SentMessege(electricDeviceNode,"000000000"+electricDeviceId,LightController.this);
                txtStatus.setText("OFF");
            }
        });
/*
        Power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                color_custom = 0;

                String messeger = "000000000" + electricDeviceId;


                processMQTT.SentMessege(electricDeviceNode,messeger,LightController.this);

                tv_color.setText("Off");
                Log.d("qoobee","mes: "+messeger);
            }
        });
*/



    }

    public void ShowDialogDisconnect(){

        LayoutInflater li = LayoutInflater.from(LightController.this);
        View DialogView = li.inflate(R.layout.disconnect_dialog, null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                LightController.this);

        alertDialogBuilder.setView(DialogView);

        final Button btnDisconnect =  DialogView
                .findViewById(R.id.buttonDisconnect);

        final Button btnCancel =  DialogView
                .findViewById(R.id.buttonCancel);

        final AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

        btnDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();

                editor.remove(electricDeviceNode); // will delete key name

                editor.commit();

                alertDialog.cancel();

                finish();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });

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

        processMQTT.SentMessege(electricDeviceNode,mes,LightController.this);

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
