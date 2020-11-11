package com.example.smartapp.Notify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartapp.R;

import java.util.ArrayList;

public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.ViewHolder> {

    private ArrayList<Notificationer> lstNotify;
    private LayoutInflater mInflater;
    private Context context;


    public NotifyAdapter(Context context, ArrayList<Notificationer> data){
        this.mInflater = LayoutInflater.from(context);
        this.lstNotify = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.notify_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifyAdapter.ViewHolder holder, int position) {

        Notificationer notificationer = new Notificationer();
        notificationer = lstNotify.get(position);

        holder.tvType.setText(notificationer.type);
        holder.tvContend.setText(notificationer.contend);

    }

    @Override
    public int getItemCount() {
        return lstNotify.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvType;
        TextView tvContend;



        ViewHolder(final View itemView) {
            super(itemView);
            tvType = itemView.findViewById(R.id.textType);
            tvContend = itemView.findViewById(R.id.textContend);





            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
