package com.example.rescueme1.Shelter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.R;


public class S_ContactUsFragment extends Fragment {
    private EditText etShelterName, etContact, etMessage;
    private Button btnSubmit;
    private DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_s__contact_us, container, false);

        dbHelper = new DBHelper(getContext());

        etShelterName = view.findViewById(R.id.etsName);
        etContact = view.findViewById(R.id.etscontact);
        etMessage = view.findViewById(R.id.etmessage);
        btnSubmit = view.findViewById(R.id.btnusubmit);

        btnSubmit.setOnClickListener(v -> submitHelpRequest());

        return view;
    }

    private void submitHelpRequest() {
        String shelterName = etShelterName.getText().toString().trim();
        String contact = etContact.getText().toString().trim();
        String message = etMessage.getText().toString().trim();

        if (shelterName.isEmpty()) {
            etShelterName.setError("Shelter name is required");
            etShelterName.requestFocus();
            return;
        }

        if (contact.isEmpty()) {
            etContact.setError("Contact number is required");
            etContact.requestFocus();
            return;
        }

        if (message.isEmpty()) {
            etMessage.setError("Message is required");
            etMessage.requestFocus();
            return;
        }

        boolean isInserted = dbHelper.insertSHelp(shelterName, contact, message);

        if (isInserted) {
            Toast.makeText(getContext(), "Your message has been submitted successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Toast.makeText(getContext(), "Failed to submit your message", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        etShelterName.setText("");
        etContact.setText("");
        etMessage.setText("");
    }
}