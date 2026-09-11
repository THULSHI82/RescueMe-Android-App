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

import com.example.rescueme1.DB.SPetModel;
import com.example.rescueme1.R;

import java.util.List;

public class U_PetAdapter extends RecyclerView.Adapter<U_PetAdapter.ViewHolder> {

    private Context context;
    private List<SPetModel> petList;

    public U_PetAdapter(Context context, List<SPetModel> petList) {
        this.context = context;
        this.petList = petList;
    }

    public void updateList(List<SPetModel> newList) {
        petList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public U_PetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.u_pet_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull U_PetAdapter.ViewHolder holder, int position) {
        SPetModel pet = petList.get(position);

        holder.petName.setText(pet.getPetName());
        holder.petCategory.setText(pet.getPetCategory());
        holder.petAge.setText("Age: " + pet.getPetAge());
        holder.petGender.setText("Gender: " + pet.getPetGender());
        holder.shelterName.setText("Shelter: " + pet.getShelterName());

        byte[] imageBytes = pet.getPetImage();
        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.petImage.setImageBitmap(bitmap);
        } else {
            holder.petImage.setImageResource(R.drawable.ic_launcher_background);
        }

        holder.btnViewMore.setOnClickListener(v -> {
            Intent intent = new Intent(context, U_PetViewMore.class);
            intent.putExtra("petId", pet.getPetId());
            intent.putExtra("petName", pet.getPetName());
            intent.putExtra("petCategory", pet.getPetCategory());
            intent.putExtra("petAge", pet.getPetAge());
            intent.putExtra("petGender", pet.getPetGender());
            intent.putExtra("shelterName", pet.getShelterName());
            intent.putExtra("shelterContact", pet.getShelterContact());
            intent.putExtra("petDescription", pet.getPetDescription());
            intent.putExtra("petImage", pet.getPetImage());
            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView petName, petCategory, petAge, petGender, shelterName;
        ImageView petImage;
        Button btnViewMore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            petName = itemView.findViewById(R.id.petName);
            petCategory = itemView.findViewById(R.id.petCategory);
            petAge = itemView.findViewById(R.id.petAge);
            petGender = itemView.findViewById(R.id.petgender);
            shelterName = itemView.findViewById(R.id.sheltername);
            petImage = itemView.findViewById(R.id.petImage);
            btnViewMore = itemView.findViewById(R.id.btnViewMore);
        }
    }
}
