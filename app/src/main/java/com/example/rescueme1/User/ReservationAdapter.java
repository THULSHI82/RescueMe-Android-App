package com.example.rescueme1.User;

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

import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder>{

    private Context context;
    private List<DBHelper.Reservation> reservationList;

    public ReservationAdapter(Context context, List<DBHelper.Reservation> reservationList) {
        this.context = context;
        this.reservationList = reservationList;
    }

    @NonNull
    @Override
    public ReservationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reservation_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationAdapter.ViewHolder holder, int position) {
        DBHelper.Reservation r = reservationList.get(position);

        holder.tvpetName.setText(r.petName);
        holder.tvusername.setText("User: " + r.userName);
        holder.tvdate.setText("From: " + r.startDate + " To: " + r.endDate);
        holder.tvtime.setText("Shelter: " + r.shelterName);

        if (r.petImage != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(r.petImage, 0, r.petImage.length);
            holder.petImage.setImageBitmap(bitmap);
        }

        holder.btnResViewMore.setOnClickListener(v -> {
            Intent intent = new Intent(context, U_Reservation_ViewMore.class);
            intent.putExtra("reservationId", r.id);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return reservationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView petImage;
        TextView tvpetName, tvusername, tvdate, tvtime;
        Button btnResViewMore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            petImage = itemView.findViewById(R.id.petImage);
            tvpetName = itemView.findViewById(R.id.tvpetName);
            tvusername = itemView.findViewById(R.id.tvusername);
            tvdate = itemView.findViewById(R.id.tvdate);
            tvtime = itemView.findViewById(R.id.tvtime);
            btnResViewMore = itemView.findViewById(R.id.btnResViewMore);
        }
    }
}
