package com.example.rescueme1.Shelter;

import android.content.Context;
import android.content.Intent;
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

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.R;
import com.example.rescueme1.Shelter.S_Reservation_ViewMore;

import java.util.List;

public class S_ReservationAdapter extends RecyclerView.Adapter<S_ReservationAdapter.ResViewHolder> {

    Context context;
    List<DBHelper.Reservation> reservationList;

    public S_ReservationAdapter(Context context, List<DBHelper.Reservation> reservationList) {
        this.context = context;
        this.reservationList = reservationList;
    }

    @NonNull
    @Override
    public ResViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reservation_card, parent, false);
        return new ResViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResViewHolder holder, int position) {
        DBHelper.Reservation reservation = reservationList.get(position);

        holder.tvpetName.setText(reservation.petName);
        holder.tvusername.setText("User: " + reservation.userName);
        holder.tvdate.setText("From: " + reservation.startDate);
        holder.tvtime.setText("To: " + reservation.endDate);

        if (reservation.petImage != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(reservation.petImage, 0, reservation.petImage.length);
            holder.petImage.setImageBitmap(bmp);
        }

        holder.btnResViewMore.setOnClickListener(v -> {
            Intent intent = new Intent(context, S_Reservation_ViewMore.class);
            intent.putExtra("reservationId", reservation.id);

            if (!(context instanceof android.app.Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return reservationList.size();
    }

    public static class ResViewHolder extends RecyclerView.ViewHolder {
        TextView tvpetName, tvusername, tvdate, tvtime;
        ImageView petImage;
        Button btnResViewMore;

        public ResViewHolder(@NonNull View itemView) {
            super(itemView);
            tvpetName = itemView.findViewById(R.id.tvpetName);
            tvusername = itemView.findViewById(R.id.tvusername);
            tvdate = itemView.findViewById(R.id.tvdate);
            tvtime = itemView.findViewById(R.id.tvtime);
            petImage = itemView.findViewById(R.id.petImage);
            btnResViewMore = itemView.findViewById(R.id.btnResViewMore);
        }
    }
}
