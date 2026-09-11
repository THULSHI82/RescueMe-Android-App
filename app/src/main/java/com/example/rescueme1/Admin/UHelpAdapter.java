package com.example.rescueme1.Admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rescueme1.DB.UHelpModel;
import com.example.rescueme1.R;

import java.util.List;

public class UHelpAdapter extends RecyclerView.Adapter<UHelpAdapter.UHelpViewHolder> {
    private List<UHelpModel> uHelpList;
    private Context context;

    public UHelpAdapter(List<UHelpModel> uHelpList, Context context) {
        this.uHelpList = uHelpList;
        this.context = context;
    }

    @NonNull
    @Override
    public UHelpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.a_ucontact_cardview, parent, false);
        return new UHelpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UHelpViewHolder holder, int position) {
        UHelpModel uHelp = uHelpList.get(position);
        holder.userName.setText(uHelp.getName());
        holder.date.setText(uHelp.getDate());
        holder.time.setText(uHelp.getTime());

        holder.btnViewMore.setOnClickListener(v -> {
            Intent intent = new Intent(context, A_UserMessageViewmore.class);
            intent.putExtra("id", uHelp.getId());
            intent.putExtra("name", uHelp.getName());
            intent.putExtra("contact", uHelp.getContact());
            intent.putExtra("message", uHelp.getMessage());
            intent.putExtra("date", uHelp.getDate());
            intent.putExtra("time", uHelp.getTime());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return uHelpList.size();
    }

    public static class UHelpViewHolder extends RecyclerView.ViewHolder {
        TextView userName, date, time;
        Button btnViewMore;

        public UHelpViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            btnViewMore = itemView.findViewById(R.id.btnViewMore);
        }
    }
}