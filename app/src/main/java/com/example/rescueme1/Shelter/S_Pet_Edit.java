package com.example.rescueme1.Shelter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.DB.PetModel;
import com.example.rescueme1.R;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class S_Pet_Edit extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView imgpetProfile;
    private EditText etpetName, etDescription;
    private MaterialAutoCompleteTextView petcategory, petage;
    private RadioGroup rgpetGender;
    private RadioButton rbMale, rbFemale;
    private Button btnpetEdit, btnpetDelete;

    private DBHelper dbHelper;
    private int petId;
    private PetModel pet;

    private Uri selectedImageUri = null;
    private byte[] profileImageBytes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spet_edit);

        imgpetProfile = findViewById(R.id.imgpetProfile);
        etpetName = findViewById(R.id.etpetName);
        etDescription = findViewById(R.id.etDescription);
        petcategory = findViewById(R.id.petcategory);
        petage = findViewById(R.id.petage);
        rgpetGender = findViewById(R.id.rgpetGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        btnpetEdit = findViewById(R.id.btnpetEdit);
        btnpetDelete = findViewById(R.id.btnpetDelete);

        dbHelper = new DBHelper(this);

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

        petId = getIntent().getIntExtra("petId", -1);

        if (petId != -1) {
            loadPetDetails(petId);
        } else {
            Toast.makeText(this, "Invalid Pet ID", Toast.LENGTH_SHORT).show();
            finish();
        }

        imgpetProfile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        btnpetEdit.setOnClickListener(v -> updatePet());

        btnpetDelete.setOnClickListener(v -> deletePet());
    }

    private void loadPetDetails(int petId) {
        pet = dbHelper.getPetById(petId);

        if (pet == null) {
            Toast.makeText(this, "Pet not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        etpetName.setText(pet.getPetName());
        petcategory.setText(pet.getCategory(), false);
        petage.setText(pet.getPetAge(), false);
        etDescription.setText(pet.getPetDescription());

        switch (pet.getPetGender().toLowerCase()) {
            case "male":
                rbMale.setChecked(true);
                break;
            case "female":
                rbFemale.setChecked(true);
                break;
            default:
                rgpetGender.clearCheck();
                break;
        }

        if (pet.getProfileImage() != null) {
            profileImageBytes = pet.getProfileImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(profileImageBytes, 0, profileImageBytes.length);
            imgpetProfile.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            imgpetProfile.setImageURI(selectedImageUri);

            try {
                InputStream iStream = getContentResolver().openInputStream(selectedImageUri);
                profileImageBytes = getBytes(iStream);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to read selected image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updatePet() {
        String name = etpetName.getText().toString().trim();
        String category = petcategory.getText().toString().trim();
        String age = petage.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        int selectedGenderId = rgpetGender.getCheckedRadioButtonId();
        String gender = null;
        if (selectedGenderId == rbMale.getId()) {
            gender = "Male";
        } else if (selectedGenderId == rbFemale.getId()) {
            gender = "Female";
        }

        if (name.isEmpty() || category.isEmpty() || age.isEmpty() || gender == null) {
            Toast.makeText(this, "Please fill all mandatory fields", Toast.LENGTH_SHORT).show();
            return;
        }

        pet.setPetName(name);
        pet.setCategory(category);
        pet.setPetAge(age);
        pet.setPetGender(gender);
        pet.setPetDescription(description);
        pet.setProfileImage(profileImageBytes);

        boolean updated = dbHelper.updatePet(pet);
        if (updated) {
            Toast.makeText(this, "Pet updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to update pet", Toast.LENGTH_SHORT).show();
        }
    }

    private void deletePet() {
        boolean deleted = dbHelper.deletePet(petId);
        if (deleted) {
            Toast.makeText(this, "Pet deleted successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to delete pet", Toast.LENGTH_SHORT).show();
        }
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
}
