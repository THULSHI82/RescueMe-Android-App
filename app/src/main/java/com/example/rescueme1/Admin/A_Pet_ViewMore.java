package com.example.rescueme1.Admin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rescueme1.R;

public class A_Pet_ViewMore extends AppCompatActivity {

    ImageView imgProfile;
    TextView etSheltername, etSheltercontact, etpname, etpetcategory, etpetage, etpetgender, etpetdescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apet_view_more);

        imgProfile = findViewById(R.id.imgProfile);
        etSheltername = findViewById(R.id.etSheltername);
        etSheltercontact = findViewById(R.id.etSheltercontact);
        etpname = findViewById(R.id.etpname);
        etpetcategory = findViewById(R.id.etpetcategory);
        etpetage = findViewById(R.id.etpetage);
        etpetgender = findViewById(R.id.etpetgender);
        etpetdescription = findViewById(R.id.etpetdescription);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            etSheltername.setText(bundle.getString("shelterName"));
            etSheltercontact.setText(bundle.getString("shelterContact"));
            etpname.setText(bundle.getString("petName"));
            etpetcategory.setText(bundle.getString("petCategory"));
            etpetage.setText(bundle.getString("petAge"));
            etpetgender.setText(bundle.getString("petGender"));
            etpetdescription.setText(bundle.getString("petDescription"));

            byte[] imageBytes = bundle.getByteArray("petImage");
            if (imageBytes != null && imageBytes.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                imgProfile.setImageBitmap(bitmap);
            } else {
                imgProfile.setImageResource(R.drawable.add_pet);
            }
        }
    }
}
