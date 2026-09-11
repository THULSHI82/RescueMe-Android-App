package com.example.rescueme1.User;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.R;

public class U_ContactUs extends AppCompatActivity {
    private EditText etName, etContact, etMessage;
    private Button btnSubmit;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ucontact_us);

        dbHelper = new DBHelper(this);

        etName = findViewById(R.id.etuName);
        etContact = findViewById(R.id.etucontact);
        etMessage = findViewById(R.id.etmessage);
        btnSubmit = findViewById(R.id.btnusubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitHelpRequest();
            }
        });
    }

    private void submitHelpRequest() {
        String name = etName.getText().toString().trim();
        String contact = etContact.getText().toString().trim();
        String message = etMessage.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError("Name is required");
            etName.requestFocus();
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

        boolean isInserted = dbHelper.insertUHelp(name, contact, message);

        if (isInserted) {
            Toast.makeText(this, "Your message has been submitted successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Toast.makeText(this, "Failed to submit your message", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        etName.setText("");
        etContact.setText("");
        etMessage.setText("");
    }
}