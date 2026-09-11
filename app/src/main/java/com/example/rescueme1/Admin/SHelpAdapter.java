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

import com.example.rescueme1.DB.SHelpModel;
import com.example.rescueme1.R;

import java.util.List;

public class SHelpAdapter extends RecyclerView.Adapter<SHelpAdapter.SHelpViewHolder> {
    private List<SHelpModel> sHelpList;
    private Context context;

    public SHelpAdapter(List<SHelpModel> sHelpList, Context context) {
        this.sHelpList = sHelpList;
        this.context = context;
    }

    @NonNull
    @Override
    public SHelpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.a_scontact_cardview, parent, false);
        return new SHelpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SHelpViewHolder holder, int position) {
        SHelpModel sHelp = sHelpList.get(position);
        holder.shelterName.setText(sHelp.getShelterName());
        holder.date.setText(sHelp.getDate());
        holder.time.setText(sHelp.getTime());

        holder.btnViewMore.setOnClickListener(v -> {
            Intent intent = new Intent(context, A_ShelterMessage_Viewmore.class);
            intent.putExtra("id", sHelp.getId());
            intent.putExtra("shelterName", sHelp.getShelterName());
            intent.putExtra("contact", sHelp.getContact());
            intent.putExtra("message", sHelp.getMessage());
            intent.putExtra("date", sHelp.getDate());
            intent.putExtra("time", sHelp.getTime());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return sHelpList.size();
    }

    public static class SHelpViewHolder extends RecyclerView.ViewHolder {
        TextView shelterName, date, time;
        Button btnViewMore;

        public SHelpViewHolder(@NonNull View itemView) {
            super(itemView);
            shelterName = itemView.findViewById(R.id.shelterName);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            btnViewMore = itemView.findViewById(R.id.btnViewMore);
        }
    }
}
