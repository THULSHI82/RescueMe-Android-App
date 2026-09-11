package com.example.rescueme1.Admin;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.DB.SPetModel;
import com.example.rescueme1.DB.DBHelper;  // assuming you have this
import com.example.rescueme1.R;

import java.util.ArrayList;
import java.util.List;

public class A_PestFragment extends Fragment {

    private RecyclerView recyclerView;
    private EditText searchBox;
    private A_PetAdapter adapter;
    private List<SPetModel> petList = new ArrayList<>();
    private List<SPetModel> filteredList = new ArrayList<>();

    private DBHelper dbHelper;

    public A_PestFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_a__pest, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DBHelper(getContext());

        recyclerView = view.findViewById(R.id.a_petrecyclerShelters);
        searchBox = view.findViewById(R.id.a_pet_search_box);

        petList = dbHelper.getAllPets();
        filteredList.addAll(petList);

        adapter = new A_PetAdapter(filteredList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterPets(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void filterPets(String query) {
        filteredList.clear();

        if (query.isEmpty()) {
            filteredList.addAll(petList);
        } else {
            query = query.toLowerCase();
            for (SPetModel pet : petList) {
                if (pet.getPetName().toLowerCase().contains(query)) {
                    filteredList.add(pet);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}
