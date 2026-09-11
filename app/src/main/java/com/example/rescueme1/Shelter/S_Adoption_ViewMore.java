package com.example.rescueme1.Shelter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rescueme1.DB.AdoptionModel;
import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.R;

public class S_Adoption_ViewMore extends AppCompatActivity {

    TextView tvpetid, tvpetname, tvpetcategory, tvpetage, tvpetgender, tvpetdescription;
    ImageView imgpetProfile, imgadoptionphoto;
    TextView etadoptionName, etadoptionage, etadoptioncontact, etadoptionnic, etadoptionaddress, etadoptiondate;
    RadioGroup rgadoptionGender;
    RadioButton rbMale, rbFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sadoption_view_more);

        // Bind Views
        tvpetid = findViewById(R.id.tvpetid);
        tvpetname = findViewById(R.id.tvpetname);
        tvpetcategory = findViewById(R.id.tvpetcategory);
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

        int adoptionId = getIntent().getIntExtra("adoptionId", -1);

        if (adoptionId != -1) {
            DBHelper dbHelper = new DBHelper(this);
            AdoptionModel model = dbHelper.getAAdoptionById(adoptionId);

            if (model != null) {
                tvpetid.setText(String.valueOf(model.getPetId()));
                tvpetname.setText(model.getPetName());
                tvpetcategory.setText(model.getPetCategory());
                tvpetage.setText(model.getPetAge());
                tvpetgender.setText(model.getPetGender());
                tvpetdescription.setText(model.getPetDescription());

                if (model.getPetProfileImage() != null) {
                    Bitmap petBitmap = BitmapFactory.decodeByteArray(model.getPetProfileImage(), 0, model.getPetProfileImage().length);
                    imgpetProfile.setImageBitmap(petBitmap);
                }

                etadoptionName.setText(model.getAdopterName());
                etadoptionage.setText(model.getAdopterAge());
                etadoptioncontact.setText(model.getAdopterContact());
                etadoptionnic.setText(model.getAdopterNIC());
                etadoptionaddress.setText(model.getAdopterAddress());
                etadoptiondate.setText(model.getAdoptionDate());

                if ("Male".equalsIgnoreCase(model.getAdopterGender())) {
                    rbMale.setChecked(true);
                } else if ("Female".equalsIgnoreCase(model.getAdopterGender())) {
                    rbFemale.setChecked(true);
                }

                if (model.getAdopterPhoto() != null) {
                    Bitmap adopterBitmap = BitmapFactory.decodeByteArray(model.getAdopterPhoto(), 0, model.getAdopterPhoto().length);
                    imgadoptionphoto.setImageBitmap(adopterBitmap);
                }
            }
        }
    }
}
