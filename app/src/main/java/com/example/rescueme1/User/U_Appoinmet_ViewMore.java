package com.example.rescueme1.User;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.R;

public class U_Appoinmet_ViewMore extends AppCompatActivity {

    ImageView imgPetProfile;
    TextView tvPetCategory, tvPetName, tvPetAge, tvPetGender, tvPetDescription;
    TextView tvShelterName, tvShelterContact;

    Button btnViewLocation, btnEditAppointment, btnDeleteAppointment;

    DBHelper dbHelper;
    int appointmentId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_uappoinmet_view_more);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DBHelper(this);

        imgPetProfile = findViewById(R.id.imgpetProfile);
        tvPetCategory = findViewById(R.id.tvpetcategory);
        tvPetName = findViewById(R.id.tvpetname);
        tvPetAge = findViewById(R.id.tvpetage);
        tvPetGender = findViewById(R.id.tvpetgender);
        tvPetDescription = findViewById(R.id.tvpetdescription);

        tvShelterName = findViewById(R.id.tvsheltername);
        tvShelterContact = findViewById(R.id.tvsheltercontact);

        btnViewLocation = findViewById(R.id.btnviewlocation);
        btnEditAppointment = findViewById(R.id.btneditappoiment);
        btnDeleteAppointment = findViewById(R.id.btndeleteappoinemt);

        if (getIntent() != null) {
            appointmentId = getIntent().getIntExtra("appointmentId", -1);
            String petCategory = getIntent().getStringExtra("petCategory");
            String petName = getIntent().getStringExtra("petName");
            String petAge = getIntent().getStringExtra("petAge");
            String petGender = getIntent().getStringExtra("petGender");
            String petDescription = getIntent().getStringExtra("petDescription");
            byte[] petImageBytes = getIntent().getByteArrayExtra("petImage");

            String shelterName = getIntent().getStringExtra("shelterName");
            String shelterContact = getIntent().getStringExtra("shelterContact");

            tvPetCategory.setText(petCategory != null ? petCategory : "N/A");
            tvPetName.setText(petName != null ? petName : "N/A");
            tvPetAge.setText(petAge != null ? petAge : "N/A");
            tvPetGender.setText(petGender != null ? petGender : "N/A");
            tvPetDescription.setText(petDescription != null ? petDescription : "N/A");

            tvShelterName.setText(shelterName != null ? shelterName : "N/A");
            tvShelterContact.setText(shelterContact != null ? shelterContact : "N/A");

            if (petImageBytes != null && petImageBytes.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(petImageBytes, 0, petImageBytes.length);
                imgPetProfile.setImageBitmap(bitmap);
            } else {
                imgPetProfile.setImageResource(R.drawable.add_pet);
            }
        }

        btnDeleteAppointment.setOnClickListener(v -> {
            if (appointmentId != -1) {
                boolean deleted = dbHelper.deleteAppointment(appointmentId);
                if (deleted) {
                    Toast.makeText(U_Appoinmet_ViewMore.this, "Appointment deleted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(U_Appoinmet_ViewMore.this, "Failed to delete appointment", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(U_Appoinmet_ViewMore.this, "Invalid appointment", Toast.LENGTH_SHORT).show();
            }
        });

        btnEditAppointment.setOnClickListener(v -> {
            if (appointmentId != -1) {
                Intent intent = new Intent(U_Appoinmet_ViewMore.this, U_EdditAppoinmet.class);
                intent.putExtra("appointmentId", appointmentId);
                startActivity(intent);
            } else {
                Toast.makeText(U_Appoinmet_ViewMore.this, "Invalid appointment", Toast.LENGTH_SHORT).show();
            }
        });

        btnViewLocation.setOnClickListener(v -> {
            if (appointmentId != -1) {
                int shelterId = dbHelper.getShelterIdByAppointmentId(appointmentId);
                if (shelterId != -1) {
                    String[] latLon = dbHelper.getShelterLatLonById(shelterId);
                    if (latLon != null && latLon.length == 2) {
                        double lat = Double.parseDouble(latLon[0]);
                        double lon = Double.parseDouble(latLon[1]);

                        Intent intent = new Intent(getApplicationContext(), ShelterviewActivity.class);
                        intent.putExtra("lat", lat);
                        intent.putExtra("lon", lon);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Location not available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Shelter not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
