package com.example.rescueme1.Admin;

import static com.example.rescueme1.DB.DBHelper.TABLE_SHELTER;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class AddNewShelter extends AppCompatActivity {

    TextView etRegNo;
    EditText etName, etOwner, etEmail, etContact, etLat, etLon,  etDate, etDesc, etPassword, etPassword1;
    ImageView imgProfile;
    Button btnAdd;

    private static final int REQUEST_IMAGE_PICK = 100;
    byte[] imageBytes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_shelter);

        etName = findViewById(R.id.etsname);
        etOwner = findViewById(R.id.etsoname);
        etEmail = findViewById(R.id.etsemail);
        etContact = findViewById(R.id.etscontact);
        etLat = findViewById(R.id.etlat);
        etLon = findViewById(R.id.etlon);
        etRegNo = findViewById(R.id.etrnumber);
        etDate = findViewById(R.id.etdate);
        etDesc = findViewById(R.id.etdescription);
        etPassword = findViewById(R.id.etsPassword);
        etPassword1 = findViewById(R.id.etsPassword1);
        imgProfile = findViewById(R.id.imgProfile);
        btnAdd = findViewById(R.id.btnaddnew);

        generateRegistrationNumber();
        imgProfile.setOnClickListener(v -> selectImageFromGallery());

        etDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePicker = new DatePickerDialog(this, (view, y, m, d) -> {
                etDate.setText(y + "-" + (m + 1) + "-" + d);
            }, year, month, day);
            datePicker.show();
        });


        btnAdd.setOnClickListener(v -> saveShelter());
    }

    private void selectImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            imgProfile.setImageURI(imageUri);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                imageBytes = outputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveShelter() {
        String name = etName.getText().toString().trim();
        String owner = etOwner.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String contact = etContact.getText().toString().trim();
        String lat = etLat.getText().toString().trim();
        String lon = etLon.getText().toString().trim();
        String regNo = etRegNo.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String desc = etDesc.getText().toString().trim();
        String password = etPassword.getText().toString();
        String password1 = etPassword1.getText().toString();

        if (name.isEmpty() || owner.isEmpty() || email.isEmpty() || contact.isEmpty() ||
                lat.isEmpty() || lon.isEmpty() || regNo.isEmpty() || date.isEmpty() ||
                desc.isEmpty() || password.isEmpty() || password1.isEmpty() || imageBytes == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Invalid email address");
            etEmail.requestFocus();
            return;
        }

        if (!contact.matches("\\d{10}")) {
            etContact.setError("Contact must be 10 digits");
            etContact.requestFocus();
            return;
        }

        try {
            double latitude = Double.parseDouble(lat);
            double longitude = Double.parseDouble(lon);
            if (latitude < -90 || latitude > 90) {
                etLat.setError("Latitude must be between -90 and 90");
                etLat.requestFocus();
                return;
            }
            if (longitude < -180 || longitude > 180) {
                etLon.setError("Longitude must be between -180 and 180");
                etLon.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Latitude and Longitude must be valid numbers", Toast.LENGTH_SHORT).show();
            return;
        }



        if (!password.equals(password1)) {
            etPassword1.setError("Passwords do not match!");
            etPassword1.requestFocus();
            return;
        }

        if (password.length() < 6 || !password.matches(".*[A-Z].*") ||
                !password.matches(".*[a-z].*") || !password.matches(".*\\d.*")) {
            etPassword.setError("Password must contain uppercase, lowercase, and a digit (min 6 chars)");
            etPassword.requestFocus();
            return;
        }

        DBHelper dbHelper = new DBHelper(this);
        boolean success = dbHelper.insertShelter(name, owner, email, contact, lat, lon,
                regNo, date, desc, password, imageBytes);

        if (success) {
            Toast.makeText(this, "Shelter added successfully!", Toast.LENGTH_SHORT).show();
            clearForm();
            generateRegistrationNumber();
        } else {
            Toast.makeText(this, "Failed to add shelter. Registration number may already exist.", Toast.LENGTH_SHORT).show();
        }
    }


    private void clearForm() {
        etName.setText("");
        etOwner.setText("");
        etEmail.setText("");
        etContact.setText("");
        etLat.setText("");
        etLon.setText("");
        etRegNo.setText("");
        etDate.setText("");
        etDesc.setText("");
        etPassword.setText("");
        etPassword1.setText("");
        imgProfile.setImageResource(R.drawable.add_image);
        imageBytes = null;
    }

    private void generateRegistrationNumber() {
        DBHelper dbHelper = new DBHelper(this);
        int shelterCount = dbHelper.getCount(TABLE_SHELTER);
        String regNumber = "SH-" + String.format("%04d", shelterCount + 1);
        etRegNo.setText(regNumber);
    }


}
