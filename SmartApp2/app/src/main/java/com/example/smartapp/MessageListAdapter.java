package com.example.smartapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessageListAdapter extends RecyclerView.Adapter {

    ArrayList<Message> lstMes = new ArrayList<>();
    Context context;

    public static int typer;

    public MessageListAdapter(ArrayList<Message> lstMes, Context context){

        this.lstMes = lstMes;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());

        View itemView1 = layoutInflater.inflate(R.layout.item_message_sent, viewGroup, false);

        if(typer==0)
        {
            i=0;
        }

        if(typer==1)
        {
            i=1;
        }

        Log.v("TEST","Data: "+i);

        if(i==0){
            itemView1 = layoutInflater.inflate(R.layout.item_message_sent, viewGroup, false);
            return new ViewHolder(itemView1);
        }
        else {
            itemView1 = layoutInflater.inflate(R.layout.item_message_received, viewGroup, false);
            return new ViewHolder(itemView1);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        //viewHolder.txtName.setText(lstMes.get(i).getContend());
        ((ViewHolder) viewHolder).txtName.setText(lstMes.get(i).getContend());

        Log.v("MES","Data: "+lstMes.get(i).getContend());

        if(i<lstMes.size()-1) {

            if (lstMes.get(i + 1).getType().contains("send")) {
                typer = 0;
            }

            if (lstMes.get(i + 1).getType().contains("receive")) {
                typer = 1;
            }
        }
    }

    @Override
    public int getItemCount() {
        return lstMes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;


        public ViewHolder(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.text_message_body);
        }
    }



}
