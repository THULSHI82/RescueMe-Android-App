package com.example.rescueme1.Admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rescueme1.DB.SPetModel;
import com.example.rescueme1.R;

import java.util.List;

public class A_PetAdapter extends RecyclerView.Adapter<A_PetAdapter.PetViewHolder> {

    private List<SPetModel> petList;

    public A_PetAdapter(List<SPetModel> petList) {
        this.petList = petList;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.a_pet_cardview, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        SPetModel pet = petList.get(position);

        holder.petName.setText(pet.getPetName());
        holder.petCategory.setText(pet.getPetCategory());
        holder.petAge.setText(pet.getPetAge());
        holder.petGender.setText(pet.getPetGender());
        holder.shelterName.setText(pet.getShelterName());

        byte[] imageBytes = pet.getPetImage();
        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.petImage.setImageBitmap(bitmap);
        } else {
            holder.petImage.setImageResource(R.drawable.ic_launcher_background);
        }

        holder.btnViewMore.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), A_Pet_ViewMore.class);
            intent.putExtra("shelterName", pet.getShelterName());
            intent.putExtra("shelterContact", pet.getShelterContact());
            intent.putExtra("petName", pet.getPetName());
            intent.putExtra("petCategory", pet.getPetCategory());
            intent.putExtra("petAge", pet.getPetAge());
            intent.putExtra("petGender", pet.getPetGender());
            intent.putExtra("petDescription", pet.getPetDescription());
            intent.putExtra("petImage", pet.getPetImage());
            v.getContext().startActivity(intent);


    });
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    public static class PetViewHolder extends RecyclerView.ViewHolder {
        ImageView petImage;
        TextView petName, petCategory, petAge, petGender, shelterName;
        Button btnViewMore;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);

            petImage = itemView.findViewById(R.id.petImage);
            petName = itemView.findViewById(R.id.petName);
            petCategory = itemView.findViewById(R.id.petCategory);
            petAge = itemView.findViewById(R.id.petAge);
            petGender = itemView.findViewById(R.id.petgender);
            shelterName = itemView.findViewById(R.id.sheltername);
            btnViewMore = itemView.findViewById(R.id.btnViewMore);
        }
    }
}
