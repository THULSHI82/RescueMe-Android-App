package com.example.rescueme1.Admin;

import android.content.Intent;
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

public class ViewShelter extends AppCompatActivity {

    ImageView imgProfile;
    TextView etsname, etsoname, etsemail, etscontact, etrnumber, etdate, etdescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_shelter);

        imgProfile = findViewById(R.id.imgProfile);
        etsname = findViewById(R.id.etsname);
        etsoname = findViewById(R.id.etsoname);
        etsemail = findViewById(R.id.etsemail);
        etscontact = findViewById(R.id.etscontact);
        etrnumber = findViewById(R.id.etrnumber);
        etdate = findViewById(R.id.etdate);
        etdescription = findViewById(R.id.etdescription);

        Intent intent = getIntent();

        etsname.setText(intent.getStringExtra("name"));
        etsoname.setText(intent.getStringExtra("owner"));
        etsemail.setText(intent.getStringExtra("email"));
        etscontact.setText(intent.getStringExtra("contact"));
        etrnumber.setText(intent.getStringExtra("reg_number"));
        etdate.setText(intent.getStringExtra("date"));
        etdescription.setText(intent.getStringExtra("description"));

        byte[] imageBytes = intent.getByteArrayExtra("image");
        if (imageBytes != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imgProfile.setImageBitmap(bitmap);
        }
    }
}
