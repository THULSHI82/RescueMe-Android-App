package com.example.rescueme1.User;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.DB.SPetModel;
import com.example.rescueme1.R;

import java.util.ArrayList;
import java.util.List;

public class PetFragment extends Fragment {

    private RecyclerView recyclerView;
    private U_PetAdapter petAdapter;
    private List<SPetModel> petList;
    private List<SPetModel> filteredPetList;
    private EditText tvSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.s_userrecyclerShelters);
        tvSearch = view.findViewById(R.id.tvsearch);

        DBHelper dbHelper = new DBHelper(getContext());
        petList = dbHelper.getAllPets();
        filteredPetList = new ArrayList<>(petList);

        petAdapter = new U_PetAdapter(getContext(), filteredPetList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(petAdapter);

        tvSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterPets(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        ImageView imgbell2 = view.findViewById(R.id.imgbell2);
        imgbell2.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), U_Messages.class);
            startActivity(intent);
        });
    }

    private void filterPets(String searchText) {
        filteredPetList.clear();

        if (searchText.isEmpty()) {
            filteredPetList.addAll(petList);
        } else {
            searchText = searchText.toLowerCase();
            for (SPetModel pet : petList) {
                if (pet.getPetName().toLowerCase().contains(searchText) ||
                        pet.getPetCategory().toLowerCase().contains(searchText)) {
                    filteredPetList.add(pet);
                }
            }
        }

        petAdapter.updateList(filteredPetList);
    }
}