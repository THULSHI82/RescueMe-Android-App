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

import com.example.rescueme1.DB.AdoptionModel;
import com.example.rescueme1.R;

import java.util.List;

public class AdoptionPetAdepter extends RecyclerView.Adapter<AdoptionPetAdepter.ViewHolder> {

    private Context context;
    private List<AdoptionModel> adoptionList;
    private OnEditClickListener editClickListener;

    public interface OnEditClickListener {
        void onEditClick(int adoptionId);
    }

    public AdoptionPetAdepter(Context context, List<AdoptionModel> adoptionList, OnEditClickListener listener) {
        this.context = context;
        this.adoptionList = adoptionList;
        this.editClickListener = listener;
    }

    public void setData(List<AdoptionModel> newList) {
        this.adoptionList = newList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.s_adoption_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AdoptionModel model = adoptionList.get(position);

        holder.name.setText(model.getAdopterName());
        holder.petId.setText("Pet ID: " + model.getPetId());
        holder.petName.setText("Pet Name: " +model.getPetName());
        holder.contact.setText(model.getAdopterContact());
        holder.address.setText(model.getAdopterAddress());

        if (model.getPetProfileImage() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(model.getPetProfileImage(), 0, model.getPetProfileImage().length);
            holder.imageView.setImageBitmap(bitmap);
        } else {
            holder.imageView.setImageResource(R.drawable.user);
        }

        holder.btnViewMore.setOnClickListener(v -> {
            Intent intent = new Intent(context, S_Adoption_ViewMore.class);
            intent.putExtra("adoptionId", model.getAdoptionId());
            context.startActivity(intent);
        });

        holder.btnEdit.setOnClickListener(v -> {
            if (editClickListener != null) {
                editClickListener.onEditClick(model.getAdoptionId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return adoptionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, petName, contact, address,petId;
        Button btnViewMore, btnEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.adoptionimg);
            name = itemView.findViewById(R.id.adoptionname);
            petName = itemView.findViewById(R.id.adoptionPetname);
            petId = itemView.findViewById(R.id.adoptionPetId);
            contact = itemView.findViewById(R.id.adoptioncontect);
            address = itemView.findViewById(R.id.adoptionadders);
            btnViewMore = itemView.findViewById(R.id.btnViewMore);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
}
