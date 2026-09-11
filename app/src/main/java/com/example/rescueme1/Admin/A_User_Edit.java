package com.example.rescueme1.Admin;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.DB.UserModel1;
import com.example.rescueme1.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class A_User_Edit extends AppCompatActivity {
    private ImageView imgProfile;
    private EditText etName, etEmail, etContact, etPassword, etPassword1;
    private Button btnEditUser;
    private DBHelper dbHelper;
    private int userId;
    private byte[] profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auser_edit);

        imgProfile = findViewById(R.id.imgProfile);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etContact = findViewById(R.id.etContact);
        etPassword = findViewById(R.id.etPassword);
        etPassword1 = findViewById(R.id.etPassword1);
        btnEditUser = findViewById(R.id.btnedituser);
        dbHelper = new DBHelper(this);

        userId = getIntent().getIntExtra("USER_ID", -1);
        if (userId == -1) {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadUserData();

        imgProfile.setOnClickListener(v -> selectImage());

        btnEditUser.setOnClickListener(v -> updateUser());
    }

    private void loadUserData() {
        UserModel1 user = dbHelper.getAUserById(String.valueOf(userId));
        if (user != null) {
            etName.setText(user.getName());
            etEmail.setText(user.getEmail());
            etContact.setText(user.getContact());

            if (user.getProfileImage() != null) {
                profileImage = user.getProfileImage();
                imgProfile.setImageBitmap(BitmapFactory.decodeByteArray(
                        profileImage, 0, profileImage.length));
            }
        }
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                profileImage = getBytes(inputStream);
                imgProfile.setImageURI(imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private void updateUser() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String contact = etContact.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etPassword1.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || contact.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.isEmpty() && !password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_USER_NAME, name);
        values.put(DBHelper.COLUMN_USER_EMAIL, email);
        values.put(DBHelper.COLUMN_USER_CONTACT, contact);

        if (!password.isEmpty()) {
            values.put(DBHelper.COLUMN_USER_PASSWORD, password);
        }

        if (profileImage != null) {
            values.put(DBHelper.COLUMN_PROFILE_IMAGE, profileImage);
        }

        int rowsAffected = db.update(
                DBHelper.TABLE_USERS,
                values,
                DBHelper.COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(userId)}
        );

        if (rowsAffected > 0) {
            Toast.makeText(this, "User updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to update user", Toast.LENGTH_SHORT).show();
        }
    }
}