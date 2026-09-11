package com.example.rescueme1.Shelter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.DB.DonationModel;
import com.example.rescueme1.R;

import java.util.ArrayList;
import java.util.List;


public class S_DoneshionFragment extends Fragment {

    private RecyclerView recyclerView;
    private DonationAdapter adapter;
    private List<DonationModel> donation = new ArrayList<>();
    private DBHelper dbHelper;
    private int shelterId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_s__doneshion, container, false);

        recyclerView = view.findViewById(R.id.s_doneshion_recyclerShelters);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new DBHelper(getContext());

        SharedPreferences prefs = requireContext().getSharedPreferences("shelter_data", Context.MODE_PRIVATE);
        shelterId = prefs.getInt("sid", -1);

        if (shelterId != -1) {
            donation = dbHelper.getDonationsByShelterId(shelterId);
            adapter = new DonationAdapter(getContext(), donation);
            recyclerView.setAdapter(adapter);
        }

        return view;
    }

    private void loaddonations() {
        donation = dbHelper.getDonationsByShelterId(shelterId);
    }
}