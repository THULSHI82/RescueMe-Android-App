package com.example.rescueme1.User;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.R;

public class U_Reservation_ViewMore extends AppCompatActivity {

    ImageView imgmypetProfile;
    TextView tvmypetcategory, tvmypetname, tvsheltername, tvsheltercontact;
    Button btnViewLocation, btndeletereservation;
    DBHelper dbHelper;

    double shelterLat = 0.0;
    double shelterLon = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ureservation_view_more);

        imgmypetProfile = findViewById(R.id.imgmypetProfile);
        tvmypetcategory = findViewById(R.id.tvmypetcategory);
        tvmypetname = findViewById(R.id.tvmypetname);
        tvsheltername = findViewById(R.id.tvsheltername);
        tvsheltercontact = findViewById(R.id.tvsheltercontact);
        btnViewLocation = findViewById(R.id.btnviewlocation);
        btndeletereservation = findViewById(R.id.btndeletereservation);

        dbHelper = new DBHelper(this);

        int reservationId = getIntent().getIntExtra("reservationId", -1);
        if (reservationId != -1) {
            DBHelper.Reservation reservation = dbHelper.getReservationById(reservationId);
            if (reservation != null) {
                DBHelper.Pet pet = dbHelper.getMyPetById(reservation.petId);
                if (pet != null) {
                    tvmypetname.setText(pet.name);
                    tvmypetcategory.setText(pet.category);

                    if (pet.image != null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(pet.image, 0, pet.image.length);
                        imgmypetProfile.setImageBitmap(bitmap);
                    }
                }

                DBHelper.Shelter shelter = dbHelper.getSheltersbyId(reservation.shelterId);
                if (shelter != null) {
                    tvsheltername.setText(shelter.name);
                    tvsheltercontact.setText(shelter.contact);
                }

                btnViewLocation.setOnClickListener(v -> {
                    String lat = reservation.shelterLat;
                    String lon = reservation.shelterLon;

                    if (lat != null && lon != null && !lat.isEmpty() && !lon.isEmpty()) {
                        String uri = "geo:" + lat + "," + lon + "?q=" + lat + "," + lon + "(Shelter Location)";
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        intent.setPackage("com.google.android.apps.maps");

                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        } else {
                            Toast.makeText(this, "Google Maps app not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Location not available", Toast.LENGTH_SHORT).show();
                    }
                });

                btndeletereservation.setOnClickListener(v -> {
                    new AlertDialog.Builder(this)
                            .setTitle("Delete Confirmation")
                            .setMessage("Are you sure you want to delete this reservation?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                boolean success = dbHelper.deleteReservationById(reservationId);
                                if (success) {
                                    Toast.makeText(this, "Reservation deleted successfully!", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(this, "Failed to delete reservation.", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                });

            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}