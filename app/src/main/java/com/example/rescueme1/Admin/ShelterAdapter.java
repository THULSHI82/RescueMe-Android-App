package com.example.rescueme1.Admin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.example.rescueme1.DB.ShelterModel;
import com.example.rescueme1.R;

import java.util.ArrayList;
import java.util.List;

public class ShelterAdapter extends RecyclerView.Adapter<ShelterAdapter.ShelterViewHolder> implements Filterable {

    private Context context;
    private List<ShelterModel> shelterList;
    private List<ShelterModel> fullList;

    public ShelterAdapter(Context context, List<ShelterModel> shelterList) {
        this.context = context;
        this.shelterList = shelterList;
        this.fullList = new ArrayList<>(shelterList);
    }

    @NonNull
    @Override
    public ShelterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shelter_card, parent, false);
        return new ShelterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShelterViewHolder holder, int position) {
        ShelterModel shelter = shelterList.get(position);

        holder.txtName.setText(shelter.getName());
        holder.txtEmail.setText(shelter.getEmail());
        holder.txtContact.setText(shelter.getContact());

        if (shelter.getImage() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(shelter.getImage(), 0, shelter.getImage().length);
            holder.imgShelter.setImageBitmap(bitmap);
        }

        holder.btnView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ViewShelter.class);
            intent.putExtra("sid", shelter.getSid());
            intent.putExtra("name", shelter.getName());
            intent.putExtra("owner", shelter.getOwner());
            intent.putExtra("email", shelter.getEmail());
            intent.putExtra("contact", shelter.getContact());
            intent.putExtra("reg_number", shelter.getRegNo());
            intent.putExtra("date", shelter.getEstDate());
            intent.putExtra("description", shelter.getDescription());
            intent.putExtra("image", shelter.getImage());
            context.startActivity(intent);
        });

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditShelter.class);
            intent.putExtra("SHELTER_ID", shelter.getSid());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return shelterList.size();
    }

    public static class ShelterViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtEmail, txtContact;
        ImageView imgShelter;
        Button btnView, btnEdit;

        public ShelterViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.shelterName);
            txtEmail = itemView.findViewById(R.id.shelterEmail);
            txtContact = itemView.findViewById(R.id.shelterContact);
            imgShelter = itemView.findViewById(R.id.shelterImage);
            btnView = itemView.findViewById(R.id.btnViewMore);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }

    @Override
    public Filter getFilter() {
        return shelterFilter;
    }

    private Filter shelterFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ShelterModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(fullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ShelterModel item : fullList) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
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
            shelterList.clear();
            shelterList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public void updateList(List<ShelterModel> newList) {
        shelterList.clear();
        shelterList.addAll(newList);

        fullList.clear();
        fullList.addAll(newList);

        notifyDataSetChanged();
    }

}
