package com.example.rescueme1.Shelter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rescueme1.DB.AppointmentModel1;
import com.example.rescueme1.R;

import java.util.List;

public class ShelterAppointmentAdapter extends RecyclerView.Adapter<ShelterAppointmentAdapter.ViewHolder> {
    private Context context;
    private List<AppointmentModel1> appointmentList;
    private OnViewMoreClickListener listener;

    public interface OnViewMoreClickListener {
        void onViewMoreClick(int position);
    }

    public ShelterAppointmentAdapter(Context context, List<AppointmentModel1> appointmentList, OnViewMoreClickListener listener) {
        this.context = context;
        this.appointmentList = appointmentList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.s_appoinmet_cardview, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppointmentModel1 appointment = appointmentList.get(position);

        holder.petName.setText(appointment.getPetName());
        holder.date.setText(appointment.getDate());
        holder.time.setText(appointment.getTime());
        holder.userName.setText(appointment.getUserName());

        byte[] imageBlob = appointment.getPetImageBlob();
        if (imageBlob != null && imageBlob.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.length);
            holder.petImage.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView petImage;
        TextView petName, date, time, userName;
        Button btnViewMore;

        public ViewHolder(@NonNull View itemView, OnViewMoreClickListener listener) {
            super(itemView);
            petImage = itemView.findViewById(R.id.petImage);
            petName = itemView.findViewById(R.id.petName);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            userName = itemView.findViewById(R.id.username);
            btnViewMore = itemView.findViewById(R.id.btnViewMore);

            btnViewMore.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onViewMoreClick(getAdapterPosition());
                }
            });
        }
    }
}
