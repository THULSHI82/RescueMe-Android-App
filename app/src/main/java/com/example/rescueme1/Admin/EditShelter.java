package com.example.rescueme1.Admin;

import android.app.AlertDialog;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.R;

public class EditShelter extends AppCompatActivity {

    private EditText etsname, etsoname, etsemail, etscontact, etlat, etlon, etrnumber, etdate, etdescription, etsPassword, etsPassword1;
    private Button btnedit, btndelete;
    private ImageView imgProfile;
    private DBHelper dbHelper;
    private int shelterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shelter);

        dbHelper = new DBHelper(this);
        shelterId = getIntent().getIntExtra("SHELTER_ID", -1);
        if (shelterId == -1) {
            Toast.makeText(this, "Invalid shelter ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


        etsname = findViewById(R.id.etsname);
        etsoname = findViewById(R.id.etsoname);
        etsemail = findViewById(R.id.etsemail);
        etscontact = findViewById(R.id.etscontact);
        etlat = findViewById(R.id.etlat);
        etlon = findViewById(R.id.etlon);
        etrnumber = findViewById(R.id.etrnumber);
        etdate = findViewById(R.id.etdate);
        etdescription = findViewById(R.id.etdescription);
        etsPassword = findViewById(R.id.etsPassword);
        etsPassword1 = findViewById(R.id.etsPassword1);
        btnedit = findViewById(R.id.btnedit);
        btndelete = findViewById(R.id.btndelet);
        imgProfile = findViewById(R.id.imgProfile);

        loadShelterDetails(shelterId);

        btnedit.setOnClickListener(v -> updateShelter());
        btndelete.setOnClickListener(v -> confirmDelete());
    }

    private void loadShelterDetails(int id) {
        Cursor cursor = dbHelper.getShelterById(id);
        if (cursor != null && cursor.moveToFirst()) {
            etsname.setText(cursor.getString(cursor.getColumnIndexOrThrow("shelter_name")));
            etsoname.setText(cursor.getString(cursor.getColumnIndexOrThrow("owner_name")));
            etsemail.setText(cursor.getString(cursor.getColumnIndexOrThrow("shelter_email")));
            etscontact.setText(cursor.getString(cursor.getColumnIndexOrThrow("shelter_contact")));
            etlat.setText(cursor.getString(cursor.getColumnIndexOrThrow("lat")));
            etlon.setText(cursor.getString(cursor.getColumnIndexOrThrow("lon")));
            etrnumber.setText(cursor.getString(cursor.getColumnIndexOrThrow("registration_number")));
            etdate.setText(cursor.getString(cursor.getColumnIndexOrThrow("established_date")));
            etdescription.setText(cursor.getString(cursor.getColumnIndexOrThrow("description")));


            byte[] imageBytes = cursor.getBlob(cursor.getColumnIndexOrThrow("profile_shelter_image"));
            if (imageBytes != null && imageBytes.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                imgProfile.setImageBitmap(bitmap);
            } else {

                imgProfile.setImageResource(R.drawable.add_image);
            }

            cursor.close();
        } else {
            Toast.makeText(this, "Shelter not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    private void updateShelter() {
        String sname = etsname.getText().toString();
        String owner = etsoname.getText().toString();
        String email = etsemail.getText().toString();
        String contact = etscontact.getText().toString();
        String lat = etlat.getText().toString();
        String lon = etlon.getText().toString();
        String regNo = etrnumber.getText().toString();
        String estDate = etdate.getText().toString();
        String desc = etdescription.getText().toString();
        String pass1 = etsPassword.getText().toString();
        String pass2 = etsPassword1.getText().toString();

        if (!pass1.equals(pass2)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean updated = dbHelper.updateShelter(shelterId, sname, owner, email, contact, lat, lon, regNo, estDate, desc, pass1);
        if (updated) {
            Toast.makeText(this, "Shelter updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmDelete() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Shelter")
                .setMessage("Are you sure you want to delete this shelter?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    boolean deleted = dbHelper.deleteShelter(shelterId);
                    if (deleted) {
                        Toast.makeText(this, "Shelter deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Delete failed", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
