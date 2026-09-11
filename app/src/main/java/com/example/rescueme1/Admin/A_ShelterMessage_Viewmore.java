package com.example.rescueme1.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.R;

public class A_ShelterMessage_Viewmore extends AppCompatActivity {
    private TextView tvShelterName, tvContact, tvMessage;
    private Button btnHold, btnDelete;
    private DBHelper dbHelper;
    private int messageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ashelter_message_viewmore);

        dbHelper = new DBHelper(this);

        tvShelterName = findViewById(R.id.tvsname);
        tvContact = findViewById(R.id.tvscontact);
        tvMessage = findViewById(R.id.tvpetdescription);
        btnHold = findViewById(R.id.btnhold);
        btnDelete = findViewById(R.id.btndelet);

        Intent intent = getIntent();
        messageId = intent.getIntExtra("id", -1);
        String shelterName = intent.getStringExtra("shelterName");
        String contact = intent.getStringExtra("contact");
        String message = intent.getStringExtra("message");

        tvShelterName.setText(shelterName);
        tvContact.setText(contact);
        tvMessage.setText(message);

        btnHold.setOnClickListener(v -> {
            finish();
        });

        btnDelete.setOnClickListener(v -> {
            boolean isDeleted = dbHelper.deleteSHelpMessage(messageId);
            if (isDeleted) {
                Toast.makeText(this, "Message deleted successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to delete message", Toast.LENGTH_SHORT).show();
            }
        });
    }
}