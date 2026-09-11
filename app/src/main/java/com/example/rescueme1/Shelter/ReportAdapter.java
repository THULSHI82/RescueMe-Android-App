package com.example.rescueme1.Shelter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rescueme1.DB.ReportModel;
import com.example.rescueme1.R;
import com.example.rescueme1.User.AppointmentAdapter;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    private final OnViewMoreClickListener listener;
    private Context context;
    private List<ReportModel> reportList;

    public interface OnViewMoreClickListener {
        void onReportClick(int position);
    }

    public ReportAdapter(Context context, List<ReportModel> reportList, OnViewMoreClickListener listener) {
        this.context = context;
        this.reportList = reportList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReportAdapter.ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_report, parent, false);
        return new ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        ReportModel report = reportList.get(position);

        holder.tvBranch.setText("Branch: " + report.getShelterName());
        holder.tvCategory.setText("Category: " + report.getPetCategory());
        holder.tvDescription.setText("Description: " + report.getDescription());

        String status = report.getReporttStatus().toUpperCase();
        holder.status.setText(status);

        int statusColor;
        switch (report.getReporttStatus().toLowerCase()) {
            case "completed":
                statusColor = R.color.status_completed;
                break;
            default:
                statusColor = R.color.status_pending;
        }
        holder.status.setBackgroundTintList(
                ColorStateList.valueOf(ContextCompat.getColor(context, statusColor))
        );

        byte[] imageBytes = report.getImage();
        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.imgReportPet.setImageBitmap(bitmap);
        } else {
            holder.imgReportPet.setImageResource(R.drawable.ic_launcher_background);
        }

        holder.btnViewMorereport.setOnClickListener(v -> {
            if (listener != null) {
                listener.onReportClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public class ReportViewHolder extends RecyclerView.ViewHolder {

        TextView tvBranch, tvCategory, tvDescription, status;
        ImageView imgReportPet;
        Button btnViewMorereport;
        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBranch = itemView.findViewById(R.id.tvBranch);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            imgReportPet = itemView.findViewById(R.id.imgReportPet);
            status = itemView.findViewById(R.id.status);
            btnViewMorereport = itemView.findViewById(R.id.btnViewMorereport);
        }
    }

    public interface OnReportClickListener {
        void onReportClick(int position);
    }
}
