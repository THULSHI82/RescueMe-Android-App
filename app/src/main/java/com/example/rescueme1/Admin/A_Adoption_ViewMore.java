package com.example.rescueme1.Admin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rescueme1.DB.AdoptionModel;
import com.example.rescueme1.DB.DBHelper;  // <-- Import your DB helper class here
import com.example.rescueme1.R;

public class A_Adoption_ViewMore extends AppCompatActivity {

    private TextView tvpetid, tvpetcategory, tvpetname, tvpetage, tvpetgender, tvpetdescription;
    private ImageView imgpetProfile, imgadoptionphoto;

    private TextView etadoptionName, etadoptionage, etadoptioncontact, etadoptionnic, etadoptionaddress, etadoptiondate;
    private RadioGroup rgadoptionGender;
    private RadioButton rbMale, rbFemale;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aadoption_view_more);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvpetid = findViewById(R.id.tvpetid);
        tvpetcategory = findViewById(R.id.tvpetcategory);
        tvpetname = findViewById(R.id.tvpetname);
        tvpetage = findViewById(R.id.tvpetage);
        tvpetgender = findViewById(R.id.tvpetgender);
        tvpetdescription = findViewById(R.id.tvpetdescription);
        imgpetProfile = findViewById(R.id.imgpetProfile);

        imgadoptionphoto = findViewById(R.id.imgadoptionphoto);

        etadoptionName = findViewById(R.id.etadoptionName);
        etadoptionage = findViewById(R.id.etadoptionage);
        etadoptioncontact = findViewById(R.id.etadoptioncontact);
        etadoptionnic = findViewById(R.id.etadoptionnic);
        etadoptionaddress = findViewById(R.id.etadoptionaddress);
        etadoptiondate = findViewById(R.id.etadoptiondate);

        rgadoptionGender = findViewById(R.id.rgadoptionGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);

        dbHelper = new DBHelper(this);

        int adoptionId = getIntent().getIntExtra("adoptionId", -1);

        if (adoptionId != -1) {
            AdoptionModel adoptionModel = dbHelper.getAAdoptionById(adoptionId);
            if (adoptionModel != null) {
                populateData(adoptionModel);
            }
        }
    }

    private void populateData(AdoptionModel model) {
        tvpetid.setText(String.valueOf(model.getPetId()));
        tvpetcategory.setText(model.getPetCategory());
        tvpetname.setText(model.getPetName());
        tvpetage.setText(model.getAdopterAge());
        tvpetgender.setText(model.getAdopterGender());
        tvpetdescription.setText(model.getPetDescription());

        if (model.getPetProfileImage() != null) {
            Bitmap petBitmap = BitmapFactory.decodeByteArray(model.getPetProfileImage(), 0, model.getPetProfileImage().length);
            imgpetProfile.setImageBitmap(petBitmap);
        } else {
            imgpetProfile.setImageResource(R.drawable.add_pet);
        }

        if (model.getAdopterPhoto() != null) {
            Bitmap adopterBitmap = BitmapFactory.decodeByteArray(model.getAdopterPhoto(), 0, model.getAdopterPhoto().length);
            imgadoptionphoto.setImageBitmap(adopterBitmap);
        } else {
            imgadoptionphoto.setImageResource(R.drawable.pet_owner);
        }

        etadoptionName.setText(model.getAdopterName());
        etadoptionage.setText(model.getAdopterAge());
        etadoptioncontact.setText(model.getAdopterContact());
        etadoptionnic.setText(model.getAdopterNIC());
        etadoptionaddress.setText(model.getAdopterAddress());
        etadoptiondate.setText(model.getAdoptionDate());

        if (model.getAdopterGender() != null) {
            if (model.getAdopterGender().equalsIgnoreCase("Male")) {
                rbMale.setChecked(true);
            } else if (model.getAdopterGender().equalsIgnoreCase("Female")) {
                rbFemale.setChecked(true);
            }
        }
    }
}
