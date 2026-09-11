package com.example.rescueme1.Shelter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rescueme1.DB.AppointmentModel1;
import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.R;

import java.util.ArrayList;
import java.util.List;


public class S_AppoimentFragment extends Fragment {
    private RecyclerView recyclerView;
    private ShelterAppointmentAdapter adapter;
    private List<AppointmentModel1> appointmentList = new ArrayList<>();
    private DBHelper dbHelper;
    private int shelterId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_s__appoiment, container, false);

        SharedPreferences prefs = requireContext().getSharedPreferences("shelter_data", Context.MODE_PRIVATE);
        shelterId = prefs.getInt("sid", -1);

        if (shelterId == -1) {
            Toast.makeText(getContext(), "Shelter not logged in", Toast.LENGTH_SHORT).show();
            return view;
        }

        recyclerView = view.findViewById(R.id.s_appoinmet_recyclerShelters);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new DBHelper(getContext());
        loadAppointments();

        return view;
    }

    private void loadAppointments() {
        appointmentList = dbHelper.getAppointmentsByShelterId(shelterId);
        adapter = new ShelterAppointmentAdapter(getContext(), appointmentList, position -> {
            // Handle view more click
            AppointmentModel1 appointment = appointmentList.get(position);
            Intent intent = new Intent(getContext(), S_Appoiment_ViewMore.class);
            intent.putExtra("appointment_data", appointment);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }
}