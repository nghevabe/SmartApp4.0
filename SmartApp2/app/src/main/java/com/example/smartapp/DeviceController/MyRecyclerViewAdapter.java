package com.example.smartapp.DeviceController;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartapp.Model.ElectricDevice;
import com.example.smartapp.Main.TabDeviceController;
import com.example.smartapp.R;

import java.util.ArrayList;

import static com.example.smartapp.DeviceController.LightController.electricDeviceNode;
import static com.example.smartapp.MainActivity.processMQTT;
import static com.example.smartapp.DeviceController.LightController.electricDeviceId;
import static com.example.smartapp.DeviceController.LightController.electricDeviceName;
//import static com.example.smartapp.MainActivity.processMQTT2;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {


    private ArrayList<ElectricDevice> lstDevice;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    public static String idLight;



    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, ArrayList<ElectricDevice> data) {
        this.mInflater = LayoutInflater.from(context);
        this.lstDevice = data;
        this.context = context;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.card_item, parent, false);
        return new ViewHolder(view);
    }


    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        ElectricDevice electricDevice = new ElectricDevice();
        electricDevice = lstDevice.get(position);

        final String device_id = electricDevice.id;
        final String type_device = electricDevice.type;
        final String node = electricDevice.node;

        holder.tvName.setText(electricDevice.name);
        if(electricDevice.type.contains("light")) {
            holder.devicePhoto.setImageResource(R.drawable.led_icon);
        }

        if(electricDevice.type.contains("fan")){
            holder.devicePhoto.setImageResource(R.drawable.fan_icon);
        }

        if(electricDevice.type.contains("purifier")){
            holder.devicePhoto.setImageResource(R.drawable.purifier);
        }

        if(electricDevice.type.contains("glass")){
            holder.devicePhoto.setImageResource(R.drawable.glass);
        }

        if(electricDevice.type.contains("door")){
            holder.devicePhoto.setImageResource(R.drawable.door);
        }

        holder.turn_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(type_device.contains("light")) {

                    if (b == true) {
                        Log.d("SEX", "ID: " + position + " Turn On " + node + " id: "+device_id);
                        processMQTT.SentMessege(node, "255255255" + device_id, context);
                        if(TabDeviceController.turnIt == 1){
                           // processMQTT2.SentMessege(node, "255255255" + device_id, context);
                        }
                    }

                    if (b == false) {
                        Log.d("SEX", "ID: " + position + " Turn Off");
                        processMQTT.SentMessege(node, "000000000" + device_id, context);
                        if(TabDeviceController.turnIt == 1){
                            //processMQTT2.SentMessege(node, "000000000" + device_id, context);
                        }
                    }
                    //Log.d("SEX","ID: "+position);
                }

            }
        });
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return lstDevice.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName;
        ImageView devicePhoto;
        Switch turn_switch;

        ViewHolder(final View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.device_name);
            devicePhoto = itemView.findViewById(R.id.device_photo);
            turn_switch = itemView.findViewById(R.id.switch_turn);



            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

            ElectricDevice electricDevice = new ElectricDevice();
            electricDevice = lstDevice.get(getAdapterPosition());

            //if(!electricDevice.type.contains("device_")) {


                if(electricDevice.type.contains("light")) {

                    if(electricDevice.type.contains("device_")){
                        TabDeviceController.turnIt = 1;
                    } else{
                        TabDeviceController.turnIt = 0;
                    }

                    Intent intent = new Intent(context, LightController.class);
                    Bundle bundle = new Bundle();

                    bundle.putString("ID", electricDevice.id);
                    bundle.putString("NAME", electricDevice.name);
                    intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    electricDeviceId = electricDevice.id;
                    electricDeviceName = electricDevice.name;
                    electricDeviceNode = electricDevice.node;

                    context.startActivity(intent);
                }

                if(electricDevice.type.contains("fan")) {
                    Intent intent = new Intent(context, FanController.class);
                    Bundle bundle = new Bundle();

                    bundle.putString("ID", electricDevice.id);
                    bundle.putString("NAME", electricDevice.name);
                    intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    electricDeviceId = electricDevice.id;
                    electricDeviceName = electricDevice.name;

                    context.startActivity(intent);
                }

                if(electricDevice.type.contains("purifier")) {
                   Intent intent = new Intent(context, PurifierController.class);
                   Bundle bundle = new Bundle();

                   bundle.putString("ID", electricDevice.id);
                   bundle.putString("NAME", electricDevice.name);
                   intent.putExtras(bundle);
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                   electricDeviceId = electricDevice.id;
                   electricDeviceName = electricDevice.name;

                   context.startActivity(intent);
                 }

                if(electricDevice.type.contains("glass")) {
                    Intent intent = new Intent(context, GlassController.class);
                    Bundle bundle = new Bundle();

                    bundle.putString("ID", electricDevice.id);
                    bundle.putString("NAME", electricDevice.name);
                    intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    electricDeviceId = electricDevice.id;
                    electricDeviceName = electricDevice.name;

                    context.startActivity(intent);
                }

                if(electricDevice.type.contains("door")) {
                    Intent intent = new Intent(context, DoorController.class);
                    Bundle bundle = new Bundle();

                    bundle.putString("ID", electricDevice.id);
                    bundle.putString("NAME", electricDevice.name);
                    intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    electricDeviceId = electricDevice.id;
                    electricDeviceName = electricDevice.name;

                    context.startActivity(intent);
                }
           // }



            Log.d("Squirting","Device ID: "+electricDevice.id + " - " +"Device Name: "+electricDevice.name);
        }
    }




    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

