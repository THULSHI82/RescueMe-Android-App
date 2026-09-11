package com.example.rescueme1.Admin;

import android.content.Intent;
import android.database.Cursor;
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
import com.example.rescueme1.DB.ShelterModel;
import com.example.rescueme1.R;

import java.util.ArrayList;

public class A_ShelterFragment extends Fragment {

    private Button btnaddshelter, btnviewmap;
    private RecyclerView recyclerShelters;
    private ShelterAdapter shelterAdapter;
    private DBHelper dbHelper;
    private ArrayList<ShelterModel> shelterList;
    private EditText searchBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a__shelter, container, false);

        btnaddshelter = view.findViewById(R.id.btnaddshelter);
        btnviewmap = view.findViewById(R.id.btnviewmap);
        recyclerShelters = view.findViewById(R.id.recyclerShelters);
        searchBox = view.findViewById(R.id.search_box);

        dbHelper = new DBHelper(requireContext());

        btnaddshelter.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), AddNewShelter.class);
            startActivity(intent);
        });

        btnviewmap.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ShelterMap.class);
            startActivity(intent);
        });

        setupRecyclerView();

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (shelterAdapter != null) {
                    shelterAdapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshShelterList();
    }

    private void setupRecyclerView() {
        shelterList = new ArrayList<>();
        shelterAdapter = new ShelterAdapter(requireContext(), shelterList);
        recyclerShelters.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerShelters.setAdapter(shelterAdapter);

        refreshShelterList();
    }

    private void refreshShelterList() {
        ArrayList<ShelterModel> newList = new ArrayList<>();

        Cursor cursor = dbHelper.getAllShelters();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int sid = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_SHELTER_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_SHELTER_NAME));
                String owner = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_OWNER_NAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_SHELTER_EMAIL));
                String contact = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_SHELTER_CONTACT));
                String lat = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_SHELTER_LAT));
                String lon = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_SHELTER_LON));
                String regNo = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_REGISTRATION_NUMBER));
                String estDate = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ESTABLISHED_DATE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_DESCRIPTION));
                byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PROFILE_SHELTER_IMAGE));

                ShelterModel shelter = new ShelterModel(sid, name, owner, email, contact, lat, lon, regNo, estDate, description, image);
                newList.add(shelter);
            } while (cursor.moveToNext());

            cursor.close();
        }

        shelterAdapter.updateList(newList);
    }
}
