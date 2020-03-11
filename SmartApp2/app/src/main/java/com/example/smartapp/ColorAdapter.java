package com.example.smartapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
    private ArrayList<ColorModel> imageModelArrayList;
    private MyRecyclerViewAdapter.ItemClickListener mClickListener;
    private Context context;

    public ColorAdapter(Context ctx, ArrayList<ColorModel> imageModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.imageModelArrayList = imageModelArrayList;
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


        holder.colorname.setText(imageModelArrayList.get(position).getColorName());
        String color = imageModelArrayList.get(position).getColorName();

        if(color.equals("Red"))
        {
            holder.cl.setBackgroundResource(R.color.colorRed);
        }

        if(color.equals("Green"))
        {
            holder.cl.setBackgroundResource(R.color.colorGreen);
        }

        if(color.equals("Blue"))
        {
            holder.cl.setBackgroundResource(R.color.colorBlue);
        }

        if(color.equals("Yellow"))
        {
            holder.cl.setBackgroundResource(R.color.colorYellow);
        }

        if(color.equals("Violet"))
        {
            holder.cl.setBackgroundResource(R.color.colorViolet);
        }

        if(color.equals("Aqua"))
        {
            holder.cl.setBackgroundResource(R.color.colorAqua);
        }

        if(color.equals("White"))
        {
            holder.cl.setBackgroundResource(R.color.colorCard);
        }


    }

    @Override
    public int getItemCount() {
        return imageModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView colorname;
        ImageView cl;

        public MyViewHolder(View itemView) {
            super(itemView);

            colorname = (TextView) itemView.findViewById(R.id.tv);
            cl = (ImageView) itemView.findViewById(R.id.colorImg);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

            ColorModel colorModel = new ColorModel();
            colorModel = imageModelArrayList.get(getAdapterPosition());




            Log.d("ramlan","Clicked On: "+colorModel.getColorName());

            color_custom = 0;

            ColorDevider(colorModel.getColorName());


            processMQTT.SentMessege("ESP_01",mes,context);


            //tv_color.setText("Red");
            Log.d("qoobee","mes: "+mes);

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


