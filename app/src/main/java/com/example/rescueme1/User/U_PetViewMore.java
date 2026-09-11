package com.example.rescueme1.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rescueme1.R;

public class U_PetViewMore extends AppCompatActivity {

    private TextView etSheltername, etShelterContact, etpname, etpetcategory, etpetage, etpetgender, etpetdescription;
    private ImageView imgProfile;
    private Button btnAppointment;

    private int petId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_upet_view_more);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgProfile = findViewById(R.id.imgProfile);
        etSheltername = findViewById(R.id.etSheltername);
        etShelterContact = findViewById(R.id.etSheltercontact);
        etpname = findViewById(R.id.etpname);
        etpetcategory = findViewById(R.id.etpetcategory);
        etpetage = findViewById(R.id.etpetage);
        etpetgender = findViewById(R.id.etpetgender);
        etpetdescription = findViewById(R.id.etpetdescription);
        btnAppointment = findViewById(R.id.btnappointment);

        petId = getIntent().getIntExtra("petId", -1);
        String petName = getIntent().getStringExtra("petName");
        String petCategory = getIntent().getStringExtra("petCategory");
        String petAge = getIntent().getStringExtra("petAge");
        String petGender = getIntent().getStringExtra("petGender");
        String shelterName = getIntent().getStringExtra("shelterName");
        String shelterContact = getIntent().getStringExtra("shelterContact");
        String petDescription = getIntent().getStringExtra("petDescription");
        byte[] petImage = getIntent().getByteArrayExtra("petImage");

        etSheltername.setText("Shelter: " + shelterName);
        etShelterContact.setText("Contact: " + shelterContact);
        etpname.setText("Name: " + petName);
        etpetcategory.setText("Category: " + petCategory);
        etpetage.setText("Age: " + petAge);
        etpetgender.setText("Gender: " + petGender);
        etpetdescription.setText( petDescription);

        if (petImage != null && petImage.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(petImage, 0, petImage.length);
            imgProfile.setImageBitmap(bitmap);
        }

        btnAppointment.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
            int userId = prefs.getInt("uid", -1);

            if (userId == -1) {
                Toast.makeText(U_PetViewMore.this, "User not logged in!", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(U_PetViewMore.this, U_Appoinment.class);
            intent.putExtra("petId", petId);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });
    }
}
