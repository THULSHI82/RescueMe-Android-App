package com.example.rescueme1.User;

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

import com.example.rescueme1.DB.AppointmentModel;
import com.example.rescueme1.R;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {

    private Context context;
    private List<AppointmentModel> appointmentList;
    private OnViewMoreClickListener listener;

    public interface OnViewMoreClickListener {
        void onViewMoreClick(int position);
    }

    public AppointmentAdapter(Context context, List<AppointmentModel> appointmentList, OnViewMoreClickListener listener) {
        this.context = context;
        this.appointmentList = appointmentList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.u_appoinmet_cardview, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppointmentModel appointment = appointmentList.get(position);

        holder.petName.setText(appointment.getPetName());
        holder.shelterName.setText(appointment.getShelterName());
        holder.date.setText(appointment.getDate());
        holder.time.setText(appointment.getTime());

        String status = appointment.getAppointmentStatus().toUpperCase();
        holder.status.setText(status);

        int statusColor;
        switch (appointment.getAppointmentStatus().toLowerCase()) {
            case "approved":
                statusColor = R.color.status_approved;
                break;
            case "rejected":
                statusColor = R.color.status_rejected;
                break;
            case "completed":
                statusColor = R.color.status_completed;
                break;
            default:
                statusColor = R.color.status_pending;
        }
        holder.status.setBackgroundTintList(
                ColorStateList.valueOf(ContextCompat.getColor(context, statusColor))
        );

        byte[] imageBlob = appointment.getPetImageBlob();
        if (imageBlob != null && imageBlob.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.length);
            holder.petImage.setImageBitmap(bitmap);
        } else {
            holder.petImage.setImageResource(R.drawable.ic_launcher_background);
        }
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView petImage;
        TextView petName, shelterName, date, time, status;
        Button btnViewMore;

        public ViewHolder(@NonNull View itemView, OnViewMoreClickListener listener) {
            super(itemView);
            petImage = itemView.findViewById(R.id.petImage);
            petName = itemView.findViewById(R.id.petName);
            shelterName = itemView.findViewById(R.id.sheltername);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            status = itemView.findViewById(R.id.status);
            btnViewMore = itemView.findViewById(R.id.btnViewMore);

            btnViewMore.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onViewMoreClick(getAdapterPosition());
                }
            });
        }
    }
}