package com.example.smartapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.smartapp.LightController.electricDeviceNode;
import static com.example.smartapp.LightController.tv_color;
import static com.example.smartapp.MainActivity.processMQTT;
import static com.example.smartapp.LightController.mes;
import static com.example.smartapp.LightController.color_custom;
import static com.example.smartapp.LightController.valuePower;
import static com.example.smartapp.LightController.electricDeviceId;
import static com.example.smartapp.LightController.electricDeviceName;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.MyViewHolder> {


    private LayoutInflater inflater;
    private ArrayList<ColorModel> lstColor;
    private MyRecyclerViewAdapter.ItemClickListener mClickListener;
    private Context context;

    public ColorAdapter(Context ctx, ArrayList<ColorModel> lstColor){

        inflater = LayoutInflater.from(ctx);
        this.lstColor = lstColor;
        context = ctx;
    }

    @Override
    public ColorAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.color_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ColorAdapter.MyViewHolder holder, int position) {


        holder.colorname.setText(lstColor.get(position).getColorName());
        String color = lstColor.get(position).getColorName();

        if(color.equals("Red"))
        {
            holder.cl.setBackgroundResource(R.color.colorRed);
            holder.colorname.setText("Red");

        }

        if(color.equals("Green"))
        {
            holder.cl.setBackgroundResource(R.color.colorGreen);
            holder.colorname.setText("Green");

        }

        if(color.equals("Blue"))
        {
            holder.cl.setBackgroundResource(R.color.colorBlue);
            holder.colorname.setText("Blue");

        }

        if(color.equals("Yellow"))
        {
            holder.cl.setBackgroundResource(R.color.colorYellow);
            holder.colorname.setText("Yellow");

        }

        if(color.equals("Violet"))
        {
            holder.cl.setBackgroundResource(R.color.colorViolet);
            holder.colorname.setText("Violet");

        }

        if(color.equals("Aqua"))
        {
            holder.cl.setBackgroundResource(R.color.colorAqua);
            holder.colorname.setText("Aqua");

        }

        if(color.equals("White"))
        {
            holder.cl.setBackgroundResource(R.color.colorCard);
            holder.colorname.setText("White");

        }


    }

    @Override
    public int getItemCount() {
        return lstColor.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView colorname;
        ImageView cl;

        public MyViewHolder(View itemView) {
            super(itemView);

            colorname = (TextView) itemView.findViewById(R.id.tvColor);
            cl = (ImageView) itemView.findViewById(R.id.colorImg);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

            ColorModel colorModel = new ColorModel();
            colorModel = lstColor.get(getAdapterPosition());




            Log.d("ramlan","Clicked On: "+colorModel.getColorName());

            color_custom = 0;

            ColorDevider(colorModel.getColorName());


            processMQTT.SentMessege(electricDeviceNode,mes,context);

            LightController.tv_color.setText(colorModel.getColorName());


            //tv_color.setText("Red"); electricDeviceNode
            Log.d("qoobee","mes: "+mes);
            Log.d("yyy","node: "+electricDeviceNode);

        }
    }


    public void ColorDevider(String color){

        if(color.equals("Red")){

            String strValue = Integer.toString((int)valuePower);

            if(strValue.length() == 1){
                strValue = "00" + strValue;
            }

            if(strValue.length() == 2){
                strValue = "0" + strValue;
            }

            mes = strValue + "000000" + electricDeviceId;
        }


        if(color.equals("Green")){

            String strValue = Integer.toString((int)valuePower);

            if(strValue.length() == 1){
                strValue = "00" + strValue;
            }

            if(strValue.length() == 2){
                strValue = "0" + strValue;
            }

            mes = "000" + strValue + "000" + electricDeviceId;
        }

        if(color.equals("Blue")){

            String strValue = Integer.toString((int)valuePower);

            if(strValue.length() == 1){
                strValue = "00" + strValue;
            }

            if(strValue.length() == 2){
                strValue = "0" + strValue;
            }

            mes =  "000000" + strValue + electricDeviceId;
        }


        if(color.equals("Yellow")){

            String strValue = Integer.toString((int)valuePower);

            if(strValue.length() == 1){
                strValue = "00" + strValue;
            }

            if(strValue.length() == 2){
                strValue = "0" + strValue;
            }

            mes = strValue + strValue + "000" + electricDeviceId;
        }


        if(color.equals("Violet")){

            String strValue = Integer.toString((int)valuePower);

            if(strValue.length() == 1){
                strValue = "00" + strValue;
            }

            if(strValue.length() == 2){
                strValue = "0" + strValue;
            }

            mes = strValue + "000" + strValue + electricDeviceId;
        }


        if(color.equals("Aqua")){

            String strValue = Integer.toString((int)valuePower);

            if(strValue.length() == 1){
                strValue = "00" + strValue;
            }

            if(strValue.length() == 2){
                strValue = "0" + strValue;
            }

            mes = "000" + strValue + strValue + electricDeviceId;
        }


        if(color.equals("White")){

            String strValue = Integer.toString((int)valuePower);

            if(strValue.length() == 1){
                strValue = "00" + strValue;
            }

            if(strValue.length() == 2){
                strValue = "0" + strValue;
            }

            mes = strValue + strValue + strValue + electricDeviceId;
        }
    }

}


