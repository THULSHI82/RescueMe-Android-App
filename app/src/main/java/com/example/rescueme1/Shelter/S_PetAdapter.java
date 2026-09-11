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

import com.example.rescueme1.DB.PetModel;
import com.example.rescueme1.R;

import java.util.ArrayList;
import java.util.List;

public class S_PetAdapter extends RecyclerView.Adapter<S_PetAdapter.PetViewHolder> {

    private Context context;
    private List<PetModel> petList;
    private List<PetModel> fullPetList;

    public S_PetAdapter(Context context, List<PetModel> petList) {
        this.context = context;
        this.petList = new ArrayList<>(petList);
        this.fullPetList = new ArrayList<>(petList);
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.s_pet_cardview, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        PetModel pet = petList.get(position);

        holder.petName.setText(pet.getPetName());
        holder.petCategory.setText(pet.getCategory());
        holder.petAge.setText(pet.getPetAge());
        holder.petGender.setText(pet.getPetGender());

        if (pet.getProfileImage() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(pet.getProfileImage(), 0, pet.getProfileImage().length);
            holder.petImage.setImageBitmap(bitmap);
        } else {
            holder.petImage.setImageResource(R.drawable.ic_launcher_background);
        }

        holder.btnViewMore.setOnClickListener(v -> {
            // TODO: Add view more logic
        });

        holder.btnViewMore.setOnClickListener(v -> {
            Intent intent = new Intent(context, S_Pet_ViewMore.class);
            intent.putExtra("petId", String.valueOf(pet.getPetId()));
            intent.putExtra("petName", pet.getPetName());
            intent.putExtra("petCategory", pet.getCategory());
            intent.putExtra("petAge", pet.getPetAge());
            intent.putExtra("petGender", pet.getPetGender());
            intent.putExtra("petDescription", pet.getPetDescription());
            intent.putExtra("petImage", pet.getProfileImage());
            context.startActivity(intent);
        });


        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, S_Pet_Edit.class);
            intent.putExtra("petId", pet.getPetId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    public void filter(String keyword) {
        keyword = keyword.toLowerCase().trim();
        petList.clear();

        if (keyword.isEmpty()) {
            petList.addAll(fullPetList);
        } else {
            for (PetModel pet : fullPetList) {
                if (pet.getPetName().toLowerCase().contains(keyword) ||
                        pet.getPetAge().toLowerCase().contains(keyword) ||
                        pet.getPetGender().toLowerCase().contains(keyword) ||
                        pet.getCategory().toLowerCase().contains(keyword)) {
                    petList.add(pet);
                }
            }
        }

        notifyDataSetChanged();
    }

    public static class PetViewHolder extends RecyclerView.ViewHolder {
        ImageView petImage;
        TextView petName, petCategory, petAge, petGender;
        Button btnViewMore, btnEdit;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            petImage = itemView.findViewById(R.id.petImage);
            petName = itemView.findViewById(R.id.petName);
            petCategory = itemView.findViewById(R.id.petCategory);
            petAge = itemView.findViewById(R.id.petAge);
            petGender = itemView.findViewById(R.id.petgender);
            btnViewMore = itemView.findViewById(R.id.btnViewMore);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
    public void setPetList(List<PetModel> newPetList) {
        this.petList.clear();
        this.petList.addAll(newPetList);

        this.fullPetList.clear();
        this.fullPetList.addAll(newPetList);

        notifyDataSetChanged();
    }

}
