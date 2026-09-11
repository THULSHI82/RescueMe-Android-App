package com.example.rescueme1.Shelter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rescueme1.R;

public class S_Pet_ViewMore extends AppCompatActivity {

    private ImageView imgProfile;
    private TextView etpetid, etpname, etpetcategory, etpetage, etpetgender, etpetdescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_spet_view_more);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgProfile = findViewById(R.id.imgProfile);
        etpetid = findViewById(R.id.etpetid);
        etpname = findViewById(R.id.etpname);
        etpetcategory = findViewById(R.id.etpetcategory);
        etpetage = findViewById(R.id.etpetage);
        etpetgender = findViewById(R.id.etpetgender);
        etpetdescription = findViewById(R.id.etpetdescription);

        String petId = getIntent().getStringExtra("petId");
        String name = getIntent().getStringExtra("petName");
        String category = getIntent().getStringExtra("petCategory");
        String age = getIntent().getStringExtra("petAge");
        String gender = getIntent().getStringExtra("petGender");
        String description = getIntent().getStringExtra("petDescription");
        byte[] imageBytes = getIntent().getByteArrayExtra("petImage");

        etpetid.setText(petId);
        etpname.setText(name);
        etpetcategory.setText(category);
        etpetage.setText(age);
        etpetgender.setText(gender);
        etpetdescription.setText(description);

        if (imageBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imgProfile.setImageBitmap(bitmap);
        } else {
            imgProfile.setImageResource(R.drawable.add_pet);
        }
    }
}
