package com.example.rescueme1.Shelter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.R;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class Add_New_Pet extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    EditText etpetName, etDescription;
    MaterialAutoCompleteTextView petcategory, petage;
    RadioGroup rgpetGender;
    Button btnpet;
    ImageView imgpetProfile;

    DBHelper dbHelper;

    Uri selectedImageUri = null;

    int shelterId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_new_pet);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        shelterId = getIntent().getIntExtra("shelter_id", -1);

        if (shelterId == -1) {
            Toast.makeText(this, "Invalid shelter ID!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        dbHelper = new DBHelper(this);

        etpetName = findViewById(R.id.etpetName);
        petcategory = findViewById(R.id.petcategory);
        petage = findViewById(R.id.petage);
        rgpetGender = findViewById(R.id.rgpetGender);
        etDescription = findViewById(R.id.etDescription);
        btnpet = findViewById(R.id.btnpet);
        imgpetProfile = findViewById(R.id.imgpetProfile);

        String[] petCategories = getResources().getStringArray(R.array.petcategory);
        String[] petAges = getResources().getStringArray(R.array.age);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, petCategories);
        petcategory.setAdapter(categoryAdapter);

        ArrayAdapter<String> ageAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, petAges);
        petage.setAdapter(ageAdapter);

        petcategory.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) petcategory.showDropDown();
        });

        petage.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) petage.showDropDown();
        });

        imgpetProfile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        btnpet.setOnClickListener(v -> {
            String petName = etpetName.getText().toString().trim();
            String category = petcategory.getText().toString().trim();
            String age = petage.getText().toString().trim();
            String description = etDescription.getText().toString().trim();

            int selectedGenderId = rgpetGender.getCheckedRadioButtonId();
            String gender = null;
            if (selectedGenderId == R.id.rbMale) {
                gender = "Male";
            } else if (selectedGenderId == R.id.rbFemale) {
                gender = "Female";
            }

            if (petName.isEmpty()) {
                etpetName.setError("Enter pet name");
                etpetName.requestFocus();
                return;
            }

            if (category.isEmpty()) {
                petcategory.setError("Select pet category");
                petcategory.requestFocus();
                return;
            }

            if (age.isEmpty()) {
                petage.setError("Select pet age");
                petage.requestFocus();
                return;
            }

            if (gender == null) {
                Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show();
                return;
            }

            if (description.length() > 300) {
                etDescription.setError("Description too long (max 300 characters)");
                etDescription.requestFocus();
                return;
            }

            if (selectedImageUri == null) {
                Toast.makeText(this, "Please select a profile image for the pet", Toast.LENGTH_SHORT).show();
                return;
            }

            byte[] profileImage = null;
            try {
                InputStream iStream = getContentResolver().openInputStream(selectedImageUri);
                profileImage = getBytes(iStream);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to read image data", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean inserted = dbHelper.insertPet(shelterId, profileImage, category, petName, age, gender, description);

            if (inserted) {
                Toast.makeText(this, "Pet added successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to add pet", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public byte[] getBytes(InputStream inputStream) throws Exception {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }

        return byteBuffer.toByteArray();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            imgpetProfile.setImageURI(selectedImageUri);
        }
    }
}
