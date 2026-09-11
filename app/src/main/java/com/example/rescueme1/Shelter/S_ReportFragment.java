package com.example.rescueme1.Shelter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rescueme1.DB.AppointmentModel1;
import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.DB.ReportModel;
import com.example.rescueme1.R;

import java.util.ArrayList;
import java.util.List;

public class S_ReportFragment extends Fragment {

    private RecyclerView recyclerView;
    private ReportAdapter adapter;

    private List<ReportModel> reportlist = new ArrayList<>();
    private List<ReportModel> reports = new ArrayList<>();
    private DBHelper dbHelper;

    private int appUserId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_s__report, container, false);
        recyclerView = view.findViewById(R.id.s_report_recyclerShelters);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new DBHelper(getContext());

        SharedPreferences prefs = requireContext().getSharedPreferences("shelter_data", Context.MODE_PRIVATE);
        Log.d("S_ReportFragment", "All shelter_data: " + prefs.getAll().toString());

        int shelterId = prefs.getInt("sid", -1);
        if (shelterId == -1) {
            shelterId = prefs.getInt("uid", -1);
        }

        Log.d("S_ReportFragment", "Retrieved shelter ID: " + shelterId);

        if (shelterId != -1) {
            reports = dbHelper.getReportsByShelterId(shelterId);
            Log.d("S_ReportFragment", "Found reports: " + reports.size());

            adapter = new ReportAdapter(getContext(), reports, position -> {
                ReportModel selectedReport = reports.get(position);
                Intent intent = new Intent(getContext(), S_Report_ViewMore.class);
                intent.putExtra("report_id", selectedReport.getId());
                startActivityForResult(intent, 101);
            });
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(getContext(), "Shelter not properly logged in", Toast.LENGTH_LONG).show();
            Log.e("S_ReportFragment", "No shelter ID found in SharedPreferences");
        }

        return view;
    }
}