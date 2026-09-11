package com.example.rescueme1.User;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rescueme1.R;
import com.example.rescueme1.DB.AdoptionModel;

import java.util.List;

public class U_AdoptionAdapter extends RecyclerView.Adapter<U_AdoptionAdapter.ViewHolder> {

    private Context context;
    private List<AdoptionModel> adoptionList;

    public U_AdoptionAdapter(Context context, List<AdoptionModel> adoptionList) {
        this.context = context;
        this.adoptionList = adoptionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.u_adoption_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AdoptionModel model = adoptionList.get(position);

        byte[] imageBytes = model.getPetProfileImage();
        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.adoptionImage.setImageBitmap(bitmap);
        } else {
            holder.adoptionImage.setImageResource(R.drawable.ic_launcher_background);
        }
    }

    @Override
    public int getItemCount() {
        return adoptionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView adoptionImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            adoptionImage = itemView.findViewById(R.id.adoptionimg);
        }
    }
}
