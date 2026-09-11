package com.example.rescueme1.Admin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rescueme1.DB.AdoptionModel;
import com.example.rescueme1.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class A_AdoptionAdapter extends RecyclerView.Adapter<A_AdoptionAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<AdoptionModel> adoptionList;
    private List<AdoptionModel> fullAdoptionList;

    public A_AdoptionAdapter(Context context, List<AdoptionModel> adoptionList) {
        this.context = context;
        this.adoptionList = adoptionList;
        this.fullAdoptionList = new ArrayList<>(adoptionList);
    }

    @NonNull
    @Override
    public A_AdoptionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.a_adoption_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull A_AdoptionAdapter.ViewHolder holder, int position) {
        AdoptionModel adoption = adoptionList.get(position);

        holder.adoptionName.setText("Name: " + adoption.getAdopterName());
        holder.petId.setText("Pet ID: " + adoption.getPetId());
        holder.petName.setText("Pet Name: " + adoption.getPetName());
        holder.adopterContact.setText("Contact: " + adoption.getAdopterContact());
        holder.adopterAddress.setText("Address: " + adoption.getAdopterAddress());

        byte[] petImg = adoption.getPetProfileImage();
        if (petImg != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(petImg, 0, petImg.length);
            holder.adoptionImage.setImageBitmap(bitmap);
        } else {
            holder.adoptionImage.setImageResource(R.drawable.ic_launcher_background);
        }

        holder.btnViewMore.setOnClickListener(v -> {
            Intent intent = new Intent(context, A_Adoption_ViewMore.class);
            intent.putExtra("adoptionId", adoption.getAdoptionId());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return adoptionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView adoptionImage;
        TextView adoptionName, petName, adopterContact, adopterAddress,petId;
        Button btnViewMore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            adoptionImage = itemView.findViewById(R.id.adoptionimg);
            adoptionName = itemView.findViewById(R.id.adoptionname);
            petName = itemView.findViewById(R.id.adoptionPetname);
            petId = itemView.findViewById(R.id.adoptionPetId);
            adopterContact = itemView.findViewById(R.id.adoptioncontect);
            adopterAddress = itemView.findViewById(R.id.adoptionadders);
            btnViewMore = itemView.findViewById(R.id.btnViewMore);
        }
    }


    @Override
    public Filter getFilter() {
        return adoptionFilter;
    }

    private final Filter adoptionFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<AdoptionModel> filteredList = new ArrayList<>();

            if (TextUtils.isEmpty(constraint)) {
                filteredList.addAll(fullAdoptionList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (AdoptionModel item : fullAdoptionList) {
                    if (item.getAdopterName().toLowerCase().contains(filterPattern) ||
                            String.valueOf(item.getPetId()).contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adoptionList.clear();
            adoptionList.addAll((List<AdoptionModel>) results.values);
            notifyDataSetChanged();
        }
    };

}
