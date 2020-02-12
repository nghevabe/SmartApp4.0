package com.example.smartapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {


    private ArrayList<ElectricDevice> lstDevice;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    public static String idLight;



    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, ArrayList<ElectricDevice> data) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ElectricDevice electricDevice = new ElectricDevice();
        electricDevice = lstDevice.get(position);

        holder.tvName.setText(electricDevice.name);
        if(electricDevice.type.contains("light")) {
            holder.devicePhoto.setImageResource(R.drawable.led_icon);
        }

        if(electricDevice.type.contains("fan")){
            holder.devicePhoto.setImageResource(R.drawable.fan_icon);
        }
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

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.device_name);
            devicePhoto = itemView.findViewById(R.id.device_photo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

            ElectricDevice electricDevice = new ElectricDevice();
            electricDevice = lstDevice.get(getAdapterPosition());

            if(!electricDevice.type.contains("device_")) {

                if(electricDevice.type.contains("light")) {

                    Intent intent = new Intent(context, LightController.class);
                    Bundle bundle = new Bundle();

                    bundle.putString("ID", electricDevice.id);
                    bundle.putString("NAME", electricDevice.name);

                    intent.putExtras(bundle);


                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);
                }

                if(electricDevice.type.contains("fan")) {
                    Intent intent = new Intent(context, FanController.class);
                    Bundle bundle = new Bundle();

                    bundle.putString("ID", electricDevice.id);
                    bundle.putString("NAME", electricDevice.name);

                    intent.putExtras(bundle);


                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);
                }


            }



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

