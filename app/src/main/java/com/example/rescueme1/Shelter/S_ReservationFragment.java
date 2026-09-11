package com.example.rescueme1.Shelter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.R;

import java.util.List;

public class S_ReservationFragment extends Fragment {

    RecyclerView recyclerView;
    DBHelper dbHelper;
    S_ReservationAdapter adapter;
    private int appUserId;

    public S_ReservationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_s__reservation, container, false);

        recyclerView = view.findViewById(R.id.s_appoinmet_recyclerShelters);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new DBHelper(getContext());

        int shelterAppUserId = getLoggedInShelterAppUserId();
        List<DBHelper.Reservation> reservations = dbHelper.getReservationsByShelterAppUserId(shelterAppUserId);

        adapter = new S_ReservationAdapter(getContext(), reservations);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private int getLoggedInShelterAppUserId() {
        SharedPreferences prefs = requireContext().getSharedPreferences("shelter_data", Context.MODE_PRIVATE);
        return prefs.getInt("uid", -1);
    }

    private int getLoggedInShelterId() {
        return 1;
    }
}