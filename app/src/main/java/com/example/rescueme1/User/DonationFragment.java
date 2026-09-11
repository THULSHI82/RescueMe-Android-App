package com.example.rescueme1.User;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.DB.ShelterItem;
import com.example.rescueme1.R;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.util.List;

public class DonationFragment extends Fragment {

    MaterialAutoCompleteTextView spinnerPetId;
    EditText etFname, etLname, etAmount, etRemark;
    Button btndonation;

    DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_donation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinnerPetId = view.findViewById(R.id.spinnerPetId);
        etFname = view.findViewById(R.id.etFname);
        etLname = view.findViewById(R.id.etLname);
        etAmount = view.findViewById(R.id.etamount);
        etRemark = view.findViewById(R.id.etremark);
        btndonation = view.findViewById(R.id.btndonation);

        dbHelper = new DBHelper(getContext());

        List<ShelterItem> shelterNames = dbHelper.getAllShelterItems();
        ArrayAdapter<ShelterItem> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, shelterNames);
        spinnerPetId.setAdapter(adapter);

        spinnerPetId.setOnClickListener(v -> spinnerPetId.showDropDown());
        spinnerPetId.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) spinnerPetId.showDropDown();
        });

        btndonation.setOnClickListener(v -> {
            String fname = etFname.getText().toString().trim();
            String lname = etLname.getText().toString().trim();
            String amount = etAmount.getText().toString().trim();
            String remark = etRemark.getText().toString().trim();
            String selectedShelter = spinnerPetId.getText().toString().trim();

            if (fname.isEmpty() || lname.isEmpty() || amount.isEmpty() || selectedShelter.isEmpty()) {
                Toast.makeText(getActivity(), "Please fill all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                Double.parseDouble(amount);
            } catch (NumberFormatException e) {
                Toast.makeText(getActivity(), "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                return;
            }

            ShelterItem selectedShelterItem = null;
            for (ShelterItem item : shelterNames) {
                if (item.toString().equals(selectedShelter)) {
                    selectedShelterItem = item;
                    break;
                }
            }

            if (selectedShelterItem == null) {
                Toast.makeText(getActivity(), "Invalid shelter selected", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(getActivity(), paymentgetway.class);
            intent.putExtra("fname", fname);
            intent.putExtra("lname", lname);
            intent.putExtra("amount", amount);
            intent.putExtra("remark", remark);
            intent.putExtra("shelter", selectedShelter); // name
            intent.putExtra("shelter_id", selectedShelterItem.getId());

            SharedPreferences prefs = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            int userId = prefs.getInt("uid", -1);

            intent.putExtra("user_id", userId);
            startActivity(intent);
        });
    }
}