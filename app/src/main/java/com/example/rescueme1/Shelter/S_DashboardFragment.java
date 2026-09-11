package com.example.rescueme1.Shelter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.R;


public class S_DashboardFragment extends Fragment {
    private TextView txtAppointment, txtPetNum, txtAdoptionNum, txtreport;
    private DBHelper dbHelper;
    private int shelterId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_s__dashboard, container, false);

        txtAppointment = view.findViewById(R.id.txtapoiment);
        txtPetNum = view.findViewById(R.id.txtPetnum);
        txtAdoptionNum = view.findViewById(R.id.txtadoptionnum);
        txtreport = view.findViewById(R.id.txtreport);

        SharedPreferences prefs = requireActivity().getSharedPreferences(
                "shelter_data", Context.MODE_PRIVATE);

        shelterId = prefs.getInt("sid", -1);

        Log.d("S_Dashboard", "Retrieved shelter ID: " + shelterId);

        if (shelterId == -1) {
            Toast.makeText(getContext(),
                    "Shelter data not found. Please login again.",
                    Toast.LENGTH_LONG).show();
        } else {
            dbHelper = new DBHelper(getContext());
            loadCounts();
        }

        return view;
    }

    private void loadCounts() {
        try {
            Log.d("S_Dashboard", "Loading counts for shelter: " + shelterId);

            int appointmentCount = dbHelper.getAppointmentCountForShelter(shelterId);
            Log.d("S_Dashboard", "Appointments: " + appointmentCount);
            txtAppointment.setText(String.valueOf(appointmentCount));

            int petCount = dbHelper.getPetCountForShelter(shelterId);
            Log.d("S_Dashboard", "Pets: " + petCount);
            txtPetNum.setText(String.valueOf(petCount));

            int adoptionCount = dbHelper.getAdoptionCountForShelter(shelterId);
            Log.d("S_Dashboard", "Adoptions: " + adoptionCount);
            txtAdoptionNum.setText(String.valueOf(adoptionCount));

            int reportCount = dbHelper.getReportCountForShelter(shelterId);
            Log.d("S_Dashboard", "Reports: " + reportCount);
            txtreport.setText(String.valueOf(reportCount));

        } catch (Exception e) {
            Log.e("S_Dashboard", "Error loading counts", e);
            Toast.makeText(getContext(),
                    "Error loading shelter data",
                    Toast.LENGTH_SHORT).show();
        }
    }
}