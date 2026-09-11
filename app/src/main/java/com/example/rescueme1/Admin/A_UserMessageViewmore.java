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

public class A_UserMessageViewmore extends AppCompatActivity {
    private TextView tvName, tvContact, tvMessage;
    private Button btnHold, btnDelete;
    private DBHelper dbHelper;
    private int messageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auser_message_viewmore);

        dbHelper = new DBHelper(this);

        tvName = findViewById(R.id.tvname);
        tvContact = findViewById(R.id.tvcontact);
        tvMessage = findViewById(R.id.tvpetdescription);
        btnHold = findViewById(R.id.btnhold);
        btnDelete = findViewById(R.id.btndelet);

        Intent intent = getIntent();
        messageId = intent.getIntExtra("id", -1);
        String name = intent.getStringExtra("name");
        String contact = intent.getStringExtra("contact");
        String message = intent.getStringExtra("message");

        tvName.setText(name);
        tvContact.setText(contact);
        tvMessage.setText(message);

        // Button click listeners
        btnHold.setOnClickListener(v -> {
            finish();
        });

        btnDelete.setOnClickListener(v -> {
            boolean isDeleted = dbHelper.deleteUHelpMessage(messageId);
            if (isDeleted) {
                Toast.makeText(this, "Message Complete successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to Complete message", Toast.LENGTH_SHORT).show();
            }
        });
    }
}