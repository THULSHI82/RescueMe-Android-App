package com.example.rescueme1.Shelter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.R;

public class S_Reservation_ViewMore extends AppCompatActivity {

    ImageView imgmypetProfile;
    TextView tvmypetcategory, tvmypetname, tvusername, tvusercontact, tvuseramount;
    EditText etAddress, etStartdate, etEnddate;
    Button btnokreservation;
    DBHelper dbHelper;

    double shelterLat = 0.0;
    double shelterLon = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sreservation_view_more);

        imgmypetProfile = findViewById(R.id.imgmypetProfile);
        tvmypetcategory = findViewById(R.id.tvmypetcategory);
        tvmypetname = findViewById(R.id.tvmypetname);
        tvusername = findViewById(R.id.tvusername);
        tvusercontact = findViewById(R.id.tvusercontact);
        etAddress = findViewById(R.id.etAddress);
        etStartdate = findViewById(R.id.etStartdate);
        etEnddate = findViewById(R.id.etEnddate);
        tvuseramount = findViewById(R.id.tvuseramount);
        btnokreservation = findViewById(R.id.btnokreservation);

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
                    tvusername.setText(shelter.name);
                    tvusercontact.setText(shelter.contact);
                }

                etAddress.setText(reservation.address);
                etAddress.setFocusable(false);
                etAddress.setClickable(false);
                etStartdate.setText(reservation.startDate);
                etEnddate.setText(reservation.endDate);
                tvuseramount.setText(String.format("Amount: Rs. %.2f", reservation.amount));

                DBHelper.User user = dbHelper.getUserById(reservation.userId);
                if (user != null) {
                    tvusername.setText(user.name);
                    tvusercontact.setText(user.contact);
                }

            }

            btnokreservation.setOnClickListener(v -> {
                finish();
            });
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}