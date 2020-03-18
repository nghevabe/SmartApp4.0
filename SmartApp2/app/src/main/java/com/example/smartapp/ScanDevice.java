package com.example.smartapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

//import com.example.smartapp.DeviceController.ProcessMQTT;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScanDevice extends AppCompatActivity {

    Button buttonScan, buttonSend;
    ListView listView;
    WifiManager mainWifiObj;
    ImageView btnBack;

    String idDevice;
    String nodeDevice;

    ArrayList<String> lstDevice = new ArrayList<>();


    ListView list;
    String wifis[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_device);

        buttonScan = (Button) findViewById(R.id.btnConnect);

        listView = (ListView) findViewById(R.id.listDevice);

        btnBack = findViewById(R.id.buttonBack);

        //
        mainWifiObj = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        SetupPermission setupPermission = new SetupPermission();
        setupPermission.SetupLocation(ScanDevice.this);





        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               //ConnectToAccessPoint("QooBee","12345678");


                scanWifi();
                //SaveData("save_anal","xxx");
                //getAllKey();

/*
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();

                //editor.remove("save_anal"); // will delete key name
                //editor.remove("save1"); // will delete key email
                editor.clear();
                editor.commit();
*/


                }


        });




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //ConnectToAccessPoint("QooBee","12345678");

                String name = lstDevice.get(i).toString();

                idDevice = getIdDevice(name);
                nodeDevice = name;

                ShowDialogSharedWifi();

                Toast.makeText(ScanDevice.this, "Name: "+name, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void SaveData(String node, String data){

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(node, data); // Storing string
        editor.commit(); // commit changes


    }

    public void getAllKey(){

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        Map<String,?> keys = pref.getAll();



        for(Map.Entry<String,?> entry : keys.entrySet()){
            //Log.d("squirting",entry.getKey() + ": " + entry.getValue().toString());
            String data = entry.getValue().toString();
            String[] data_cut = data.split("-");
            Log.d("squirting","id: "+data_cut[0] + " name: "+data_cut[1] + " node: "+entry.getKey());

            ElectricDevice electricDevice = new ElectricDevice(data_cut[0],data_cut[1],"device_light","none",entry.getKey());

            TabDeviceController.lstDeviceElectric.add(electricDevice);
        }

    }

    public void ShowDialogSharedWifi(){

        LayoutInflater li = LayoutInflater.from(ScanDevice.this);
        View DialogView = li.inflate(R.layout.send_dialog, null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                ScanDevice.this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(DialogView);

        final EditText nameWifiInput =  DialogView
                .findViewById(R.id.edId);

        final EditText passWifiInput =  DialogView
                .findViewById(R.id.edPass);

        final EditText nameDeviceInput =  DialogView
                .findViewById(R.id.edName);

        final Button btnConnect =  DialogView
                .findViewById(R.id.buttonConnect);

        final Button btnCancel =  DialogView
                .findViewById(R.id.buttonCancel);

        final ImageView btnClose =  DialogView
                .findViewById(R.id.buttonClose);

        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();


        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                MainActivity.processMQTT.Disconnect();

                String nameWifi = nameWifiInput.getText().toString();
                String passWifi = passWifiInput.getText().toString();
                String nameDevice = nameDeviceInput.getText().toString();

                //ConnectToAccessPoint(nodeDevice,"12345678");

                WaitingConnect(nameWifi,passWifi,nameDevice);

                alertDialog.cancel();



            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();

            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();

            }
        });



    }

    public void WaitingConnect(final String id, final String pass, final String name){

        final ProgressDialog loading_dialog = ProgressDialog.show(ScanDevice.this, "",
                "Waiting to connect...", true);

        ConnectToAccessPoint(nodeDevice,"12345678");



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                ShareWifi(id,pass);



                //ReConnectWifi(id,pass);


            }
        }, 10000);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loading_dialog.cancel();

                ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                //WifiInfo wifiInfo = connManager.getCon
                WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ssid = wifiInfo.getSSID();

                if (mWifi.isConnected() && !ssid.contains("ESP")) {
                    MainActivity.processMQTT.startMqtt(ScanDevice.this);
                    finish();
                    Toast.makeText(ScanDevice.this, "Connection Ready", Toast.LENGTH_SHORT).show();

                    ElectricDevice electricDevice = new ElectricDevice(idDevice,name,"device_light","none",nodeDevice);

                    TabDeviceController.lstDeviceElectric.add(electricDevice);

                    String data = idDevice + "-" + name + "-" + "device_light"+ "-" +nodeDevice;

                    SaveData(nodeDevice,data);

                } else {
                    Toast.makeText(ScanDevice.this, "Connection Fail, please re-check id and pass", Toast.LENGTH_SHORT).show();
                }



            }
        }, 28000);

    }

    public void ReConnectWifi(final String id,final String pass){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ConnectToAccessPoint(id,pass);
            }
        }, 6000);



    }



    public void ConnectToAccessPoint(String id, String pass){

        String networkSSID = id;
        String networkPass = pass;

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(true);
        }

        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = String.format("\"%s\"", networkSSID);
        wifiConfiguration.preSharedKey = String.format("\"%s\"", networkPass);
        int wifiID = wifiManager.addNetwork(wifiConfiguration);
        wifiManager.enableNetwork(wifiID, true);

/*
        String networkSSID = id;
        String networkPass = pass;

        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = String.format("\"%s\"", networkSSID);
        wifiConfig.preSharedKey = String.format("\"%s\"", networkPass);

        // remember id
        int netId = mainWifiObj.addNetwork(wifiConfig);
        mainWifiObj.disconnect();
        mainWifiObj.enableNetwork(netId, true);
        mainWifiObj.reconnect();

        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\"\"" + networkSSID + "\"\"";
        conf.preSharedKey = "\"" + networkPass + "\"";
        mainWifiObj.addNetwork(conf);

        */

    }



    protected void onPause() {
        //unregisterReceiver(wifiReciever);
        super.onPause();
    }

    protected void onResume() {
        //registerReceiver(wifiReciever, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }




    protected void makeRequest() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE}, 1);
    }

    private void scanWifi() {



        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        mainWifiObj.startScan();

        Toast.makeText(this, "Scanning Device ...", Toast.LENGTH_SHORT).show();

    }

    public void ShareWifi(final String nameWifi,final String passWifi){





        Ion.with(this)
                .load("http://192.168.4.1/wifi?id="+nameWifi+"&pass="+passWifi)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {


                        // Demo();
                        if(result != null) {

                            Toast.makeText(ScanDevice.this, "Connecting to " + result, Toast.LENGTH_SHORT).show();
                            ReConnectWifi(nameWifi,passWifi);
                        }else {
                            Toast.makeText(ScanDevice.this, "Connecting Fail To ESP " , Toast.LENGTH_SHORT).show();
                        }
                        //Log.d("nnn" ,result);

                        //ReConnectWifi(nameWifi,passWifi);





                    }
                });





    }

    public String getIdDevice(String devicename){

        String result = "";
        int lastIndex = devicename.length() - 1;
        char id = devicename.charAt(lastIndex);

        result = result + id;

        return result;
    }


    BroadcastReceiver wifiReceiver = new BroadcastReceiver() {

        @Override

        public void onReceive(Context context, Intent intent) {

            List<ScanResult> wifiScanList = mainWifiObj.getScanResults();


            unregisterReceiver(this);

            Log.d("ESPSCAN","LIST: "+wifiScanList.size());

            for(int i=0;i<wifiScanList.size();i++){

                if(wifiScanList.get(i).SSID.contains("ESP")) {


                    lstDevice.add(wifiScanList.get(i).SSID);
                }
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ScanDevice.this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, lstDevice);


            // Assign adapter to ListView
            listView.setAdapter(adapter);





        };

    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 3:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //ActivityCompat.requestPermissions(ScanDevice.this, new String[]{Manifest.permission.WRITE_CALENDAR}, 2);

                    //mainWifiObj.startScan();
                } else {
                    //Toast.makeText(ScanDevice.this, "permission not granted", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
        }
    }

}
