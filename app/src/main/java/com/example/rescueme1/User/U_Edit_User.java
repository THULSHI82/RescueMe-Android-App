package com.example.rescueme1.User;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.DB.UserModel;
import com.example.rescueme1.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class U_Edit_User extends AppCompatActivity {

    private EditText etName, etEmail, etContact, etDob, etPassword, etPassword1;
    private RadioGroup rgGender;
    private RadioButton rbMale, rbFemale;
    private ImageView imgProfile;
    private Button btnEdit;
    private DBHelper dbHelper;
    private int userId;
    private byte[] profileImage;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uedit_user);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etContact = findViewById(R.id.etContact);
        etDob = findViewById(R.id.etDob);
        etPassword = findViewById(R.id.etPassword);
        etPassword1 = findViewById(R.id.etPassword1);
        rgGender = findViewById(R.id.rgGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        imgProfile = findViewById(R.id.imgProfile);
        btnEdit = findViewById(R.id.btnedituser);

        dbHelper = new DBHelper(this);

        userId = getSharedPreferences("user_prefs", MODE_PRIVATE).getInt("uid", -1);

        if (userId == -1) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadUserData();

        etDob.setOnClickListener(v -> showDatePicker());

        imgProfile.setOnClickListener(v -> openImageChooser());

        btnEdit.setOnClickListener(v -> updateUser());
    }

    private void loadUserData() {
        UserModel user = dbHelper.getUserById(String.valueOf(userId));

        if (user != null) {
            etName.setText(user.getName());
            etEmail.setText(user.getEmail());
            etContact.setText(user.getContact());
            etDob.setText(user.getDob());

            if (user.getGender().equalsIgnoreCase("Male")) {
                rbMale.setChecked(true);
            } else {
                rbFemale.setChecked(true);
            }

            if (user.getProfileImage() != null) {
                profileImage = user.getProfileImage();
                imgProfile.setImageBitmap(BitmapFactory.decodeByteArray(
                        profileImage, 0, profileImage.length));
            }
        } else {
            Toast.makeText(this, "Failed to load user data", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, month1, dayOfMonth) -> {
                    String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                    etDob.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imgProfile.setImageBitmap(bitmap);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                profileImage = stream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateUser() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String contact = etContact.getText().toString().trim();
        String dob = etDob.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etPassword1.getText().toString().trim();

        int selectedGenderId = rgGender.getCheckedRadioButtonId();
        String gender = "";

        if (selectedGenderId == R.id.rbMale) {
            gender = "Male";
        } else if (selectedGenderId == R.id.rbFemale) {
            gender = "Female";
        }

        if (name.isEmpty() || email.isEmpty() || contact.isEmpty() || dob.isEmpty() || gender.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.isEmpty() && !password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean success = dbHelper.updateUser(userId, name, email, contact,
                dob, gender, password, profileImage);

        if (success) {
            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
        }
    }
}