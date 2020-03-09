package com.example.smartapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
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

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class ScanDevice extends AppCompatActivity {

    Button buttonScan, buttonSend;
    ListView listView;
    WifiManager mainWifiObj;
    ImageView btnBack;

    ArrayList<String> lstDevice = new ArrayList<>();

    private final int MY_PERMISSIONS_ACCESS_COARSE_LOCATION = 1;

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

        Check();

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



                }


        });




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //ConnectToAccessPoint("QooBee","12345678");

                String name = lstDevice.get(i).toString();

                ConnectToAccessPoint(name,"12345678");

                ShowDialogSharedWifi();

                Toast.makeText(ScanDevice.this, "Name: "+name, Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void ShowDialogSharedWifi(){

        LayoutInflater li = LayoutInflater.from(ScanDevice.this);
        View DialogView = li.inflate(R.layout.send_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                ScanDevice.this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(DialogView);

        final EditText nameWifiInput = (EditText) DialogView
                .findViewById(R.id.edId);

        final EditText passWifiInput = (EditText) DialogView
                .findViewById(R.id.edPass);

        alertDialogBuilder
                .setCancelable(false)
                .setTitle("Share Wifi with Device to Connect")
                .setPositiveButton("Connect",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                //result.setText(userInput.getText());
                                String nameWifi = nameWifiInput.getText().toString();
                                String passWifi = passWifiInput.getText().toString();

                                ShareWifi(nameWifi,passWifi);
                                ReConnectWifi();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();


    }

    public void ReConnectWifi(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ConnectToAccessPoint("K2","2910311770");
            }
        }, 2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ScanDevice.this, "Connection Ready", Toast.LENGTH_SHORT).show();
            }
        }, 6000);

    }



    public void ConnectToAccessPoint(String id, String pass){



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

    }



    protected void onPause() {
        //unregisterReceiver(wifiReciever);
        super.onPause();
    }

    protected void onResume() {
        //registerReceiver(wifiReciever, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }


    public void Check() {

        int permission_wifi1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_WIFI_STATE);

        int permission_wifi2 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CHANGE_WIFI_STATE);

        int permission_wifi3 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_NETWORK_STATE);

        if (permission_wifi1 != PackageManager.PERMISSION_GRANTED
                || permission_wifi2 != PackageManager.PERMISSION_GRANTED
                || permission_wifi3 != PackageManager.PERMISSION_GRANTED) {
            makeRequest();
        }

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

    public void ShareWifi(String nameWifi, String passWifi){



        Ion.with(this)
                .load("http://192.168.4.1/wifi?id="+nameWifi+"&pass="+passWifi)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {


                        // Demo();

                         Toast.makeText(ScanDevice.this, "Connecting to "+result, Toast.LENGTH_SHORT).show();

                        //Log.d("nnn" ,result);





                    }
                });



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
            case MY_PERMISSIONS_ACCESS_COARSE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(ScanDevice.this, "permission granted", Toast.LENGTH_SHORT).show();
                    mainWifiObj.startScan();
                } else {
                    Toast.makeText(ScanDevice.this, "permission not granted", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
        }
    }

}
