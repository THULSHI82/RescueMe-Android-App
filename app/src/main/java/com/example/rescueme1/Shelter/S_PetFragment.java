package com.example.rescueme1.Shelter;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.DB.PetModel;
import com.example.rescueme1.DB.ShelterModel;
import com.example.rescueme1.R;

import java.util.ArrayList;
import java.util.List;

public class S_PetFragment extends Fragment {

    private ShelterModel shelter;
    private Button btnaddpet;
    private RecyclerView recyclerView;
    private S_PetAdapter adapter;
    private List<PetModel> petList;
    private EditText searchBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_s__pet, container, false);

        if (getArguments() != null) {
            shelter = (ShelterModel) getArguments().getSerializable("shelter_data");
        }

        btnaddpet = view.findViewById(R.id.btnaddpet);
        searchBox = view.findViewById(R.id.s_pet_search_box);
        recyclerView = view.findViewById(R.id.s_petrecyclerShelters);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        btnaddpet.setOnClickListener(v -> {
            if (shelter != null) {
                Intent intent = new Intent(requireContext(), Add_New_Pet.class);
                intent.putExtra("shelter_id", shelter.getSid());
                startActivity(intent);
            }
        });

        loadPetData();

        adapter = new S_PetAdapter(requireContext(), petList);
        recyclerView.setAdapter(adapter);

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPetData();
        if (adapter != null) {
            adapter.setPetList(petList);
        }
    }

    private void loadPetData() {
        DBHelper dbHelper = new DBHelper(requireContext());
        if (shelter != null) {
            petList = dbHelper.getAllPetsByShelterId(shelter.getSid());
        } else {
            petList = new ArrayList<>();
        }
    }
}
