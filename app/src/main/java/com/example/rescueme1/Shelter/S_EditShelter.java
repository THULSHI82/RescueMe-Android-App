package com.example.rescueme1.Shelter;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class S_EditShelter extends AppCompatActivity {
    private EditText etSName, etSOwner, etSEmail, etSContact, etRegNumber, etEstDate, etDescription, etPassword, etConfirmPassword;
    private ImageView imgProfile;
    private Button btnSave;
    private DBHelper dbHelper;
    private int shelterId;
    private byte[] imageBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sedit_shelter);

        etSName = findViewById(R.id.etsname);
        etSOwner = findViewById(R.id.etsoname);
        etSEmail = findViewById(R.id.etsemail);
        etSContact = findViewById(R.id.etscontact);
        etDescription = findViewById(R.id.etdescription);
        etPassword = findViewById(R.id.etsPassword);
        etConfirmPassword = findViewById(R.id.etsPassword1);
        imgProfile = findViewById(R.id.imgProfile);
        btnSave = findViewById(R.id.btnedit);

        shelterId = getIntent().getIntExtra("shelter_id", -1);
        if (shelterId == -1) {
            Toast.makeText(this, "Invalid shelter", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        dbHelper = new DBHelper(this);
        loadShelterData();

        imgProfile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 1);
        });

        btnSave.setOnClickListener(v -> updateShelter());
    }

    private void loadShelterData() {
        Cursor cursor = dbHelper.getSShelterById(shelterId);
        if (cursor != null && cursor.moveToFirst()) {
            etSName.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_SHELTER_NAME)));
            etSOwner.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_OWNER_NAME)));
            etSEmail.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_SHELTER_EMAIL)));
            etSContact.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_SHELTER_CONTACT)));
            etDescription.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_DESCRIPTION)));

            byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_PROFILE_SHELTER_IMAGE));
            if (image != null) {
                imageBytes = image;
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                imgProfile.setImageBitmap(bitmap);
            }
            cursor.close();
        }
    }

    private void updateShelter() {
        String name = etSName.getText().toString().trim();
        String owner = etSOwner.getText().toString().trim();
        String email = etSEmail.getText().toString().trim();
        String contact = etSContact.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (name.isEmpty() || owner.isEmpty() || email.isEmpty() || contact.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.isEmpty() && !password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean success;
        if (password.isEmpty()) {
            success = dbHelper.updateShelter(
                    shelterId,
                    name,
                    owner,
                    email,
                    contact,
                    "",
                    "",
                    "",
                    "",
                    description,
                    "",
                    imageBytes
            );
        } else {
            success = dbHelper.updateShelter(
                    shelterId,
                    name,
                    owner,
                    email,
                    contact,
                    "",
                    "",
                    "",
                    "",
                    description,
                    password,
                    imageBytes
            );
        }

        if (success) {
            Toast.makeText(this, "Shelter updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to update shelter", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgProfile.setImageBitmap(bitmap);

                // Convert bitmap to byte array
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                imageBytes = stream.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}