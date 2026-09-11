package com.example.rescueme1.Opening;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class Singup extends AppCompatActivity {

    private EditText etName, etEmail, etContact, etDob, etPassword, etPassword1;
    private RadioGroup rgGender;
    private ImageView imgProfile;
    private CheckBox cbTerms;
    private TextView tvLogin;
    private Button btnRegister;
    private DBHelper dbHelper;
    private byte[] imageBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_singup);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etContact = findViewById(R.id.etContact);
        etDob = findViewById(R.id.etDob);
        etPassword = findViewById(R.id.etPassword);
        etPassword1 = findViewById(R.id.etPassword1);
        rgGender = findViewById(R.id.rgGender);
        imgProfile = findViewById(R.id.imgProfile);
        cbTerms = findViewById(R.id.cbTerms);
        tvLogin = findViewById(R.id.tvLogin);
        btnRegister = findViewById(R.id.btnRegister);
        dbHelper = new DBHelper(this);

        etDob.setOnClickListener(v -> showDatePicker());
        imgProfile.setOnClickListener(v -> selectImageFromGallery());
        tvLogin.setOnClickListener(v -> {
            Intent intent = new Intent(Singup.this, Login.class);
            startActivity(intent);
            finish();
        });
        btnRegister.setOnClickListener(v -> registerUser());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR), month = calendar.get(Calendar.MONTH), day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, (view, y, m, d) -> etDob.setText(d + "/" + (m + 1) + "/" + y), year, month, day);
        dialog.show();
    }

    private void selectImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, @Nullable Intent data) {
        super.onActivityResult(reqCode, resCode, data);
        if (reqCode == 100 && resCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            imgProfile.setImageURI(imageUri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                imageBytes = stream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void registerUser() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String contact = etContact.getText().toString().trim();
        String dob = etDob.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirm = etPassword1.getText().toString();
        int genderId = rgGender.getCheckedRadioButtonId();
        String gender = genderId != -1 ? ((RadioButton) findViewById(genderId)).getText().toString() : "";

        if (name.isEmpty() || email.isEmpty() || contact.isEmpty() || dob.isEmpty() ||
                password.isEmpty() || confirm.isEmpty() || gender.isEmpty() || imageBytes == null) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Invalid email address");
            etEmail.requestFocus();
            return;
        }

        if (!contact.matches("\\d{10}")) {
            etContact.setError("Contact must be 10 digits");
            etContact.requestFocus();
            return;
        }

        if (!password.equals(confirm)) {
            etPassword1.setError("Passwords do not match");
            etPassword1.requestFocus();
            return;
        }

        if (password.length() < 6 || !password.matches(".*[A-Z].*") ||
                !password.matches(".*[a-z].*") || !password.matches(".*\\d.*")) {
            etPassword.setError("Password must be at least 6 characters with uppercase, lowercase and a number");
            etPassword.requestFocus();
            return;
        }

        if (!cbTerms.isChecked()) {
            Toast.makeText(this, "Please accept Terms & Conditions", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean success = dbHelper.insertUser(name, email, contact, dob, gender, password, imageBytes);
        if (success) {
            Toast.makeText(this, "Registered successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Singup.this, Login.class));
        } else {
            Toast.makeText(this, "Registration failed. Try again.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
            );
        }
    }

}
