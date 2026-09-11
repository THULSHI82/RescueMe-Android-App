package com.example.rescueme1.Shelter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rescueme1.DB.DonationModel;
import com.example.rescueme1.R;

import java.util.List;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.ViewHolder> {

    private Context context;
    private List<DonationModel> donations;

    public DonationAdapter(Context context, List<DonationModel> donations) {
        this.context = context;
        this.donations = donations;
    }

    @NonNull
    @Override
    public DonationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_donation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationAdapter.ViewHolder holder, int position) {
        DonationModel model = donations.get(position);
        holder.tvName.setText(model.getFirstName() + " " + model.getLastName());
        holder.tvAmount.setText("Amount: Rs. " + model.getAmount());
        holder.tvRemark.setText("Remark: " + model.getRemark());

        byte[] imgBytes = model.getUserImage();
        if (imgBytes != null && imgBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
            holder.imgUser.setImageBitmap(bitmap);
        } else {
            holder.imgUser.setImageResource(R.drawable.user);
        }
    }

    @Override
    public int getItemCount() {
        return donations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgUser;
        TextView tvName, tvAmount, tvRemark;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.imgUser);
            tvName = itemView.findViewById(R.id.tvName);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvRemark = itemView.findViewById(R.id.tvRemark);
        }
    }
}
