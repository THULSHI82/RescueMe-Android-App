package com.example.rescueme1.Shelter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

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
import android.widget.Toast;

import com.example.rescueme1.DB.AdoptionModel;
import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.R;

import java.util.ArrayList;
import java.util.List;

public class S_AdoptionFragment extends Fragment {

    private static final int REQUEST_EDIT_ADOPTION = 1001;

    private Button btnadoption;
    private RecyclerView recyclerView;
    private EditText searchBox;
    private DBHelper dbHelper;
    private List<AdoptionModel> adoptionList;
    private AdoptionPetAdepter adapter;

    private int currentShelterId = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_s__adoption, container, false);

        btnadoption = view.findViewById(R.id.btnadoption);
        recyclerView = view.findViewById(R.id.s_adoption_recyclerShelters);
        searchBox = view.findViewById(R.id.s_adoption_search_box);

        SharedPreferences prefs = requireContext().getSharedPreferences("shelter_data", getContext().MODE_PRIVATE);
        currentShelterId = prefs.getInt("sid", -1);

        if (currentShelterId == -1) {
            Toast.makeText(getContext(), "Error: Shelter not logged in!", Toast.LENGTH_LONG).show();
            return view;
        }

        dbHelper = new DBHelper(requireContext());
        adoptionList = dbHelper.getAdoptionsByShelter(currentShelterId);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new AdoptionPetAdepter(requireContext(), adoptionList, this::openEditAdoption);
        recyclerView.setAdapter(adapter);

        btnadoption.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), Add_Adoption_Pet.class);
            startActivity(intent);
        });

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    private void refreshAdoptionList() {
        adoptionList = dbHelper.getAdoptionsByShelter(currentShelterId);
        adapter.setData(adoptionList);
        adapter.notifyDataSetChanged();
    }

    private void filterList(String keyword) {
        List<AdoptionModel> filtered = new ArrayList<>();
        for (AdoptionModel model : adoptionList) {
            if (model.getAdopterName().toLowerCase().contains(keyword.toLowerCase())) {
                filtered.add(model);
            }
        }
        adapter.setData(filtered);
        adapter.notifyDataSetChanged();
    }

    private void openEditAdoption(int adoptionId) {
        Intent intent = new Intent(requireContext(), S_Adoption_Edit.class);
        intent.putExtra(S_Adoption_Edit.EXTRA_ADOPTION_ID, adoptionId);
        startActivityForResult(intent, REQUEST_EDIT_ADOPTION);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_EDIT_ADOPTION && resultCode == getActivity().RESULT_OK) {
            refreshAdoptionList();
            Toast.makeText(getContext(), "Adoption list updated", Toast.LENGTH_SHORT).show();
        }
    }
}
