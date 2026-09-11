package com.example.rescueme1.User;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.DB.UserModel;
import com.example.rescueme1.Opening.Login;
import com.example.rescueme1.R;

public class U_Account_View extends AppCompatActivity {

    private TextView etName, etEmail, etContact, etDob, etGender;
    private ImageView imgProfile;
    private Button btnEditUser, btnDeleteUser;
    private DBHelper dbHelper;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uaccount_view);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etContact = findViewById(R.id.etContact);
        etDob = findViewById(R.id.etDob);
        etGender = findViewById(R.id.etgender);
        imgProfile = findViewById(R.id.imgProfile);
        btnEditUser = findViewById(R.id.btnEditUser);
        btnDeleteUser = findViewById(R.id.btndeleteUser);

        dbHelper = new DBHelper(this);

        userId = getSharedPreferences("user_prefs", MODE_PRIVATE).getInt("uid", -1);

        if (userId == -1) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadUserData();

        btnEditUser.setOnClickListener(v -> {
            Intent intent = new Intent(U_Account_View.this, U_Edit_User.class);
            startActivity(intent);
        });

        btnDeleteUser.setOnClickListener(v -> deleteUser());
    }

    private void loadUserData() {
        UserModel user = dbHelper.getUserById(String.valueOf(userId));

        if (user != null) {
            etName.setText(user.getName());
            etEmail.setText(user.getEmail());
            etContact.setText(user.getContact());
            etDob.setText(user.getDob());
            etGender.setText(user.getGender());

            if (user.getProfileImage() != null) {
                imgProfile.setImageBitmap(BitmapFactory.decodeByteArray(
                        user.getProfileImage(), 0, user.getProfileImage().length));
            }
        } else {
            Toast.makeText(this, "Failed to load user data", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteUser() {
        boolean success = dbHelper.deleteUser(userId);

        if (success) {
            Toast.makeText(this, "Account deleted successfully", Toast.LENGTH_SHORT).show();

            getSharedPreferences("user_prefs", MODE_PRIVATE).edit().clear().apply();
            startActivity(new Intent(this, Login.class));
            finish();
        } else {
            Toast.makeText(this, "Failed to delete account", Toast.LENGTH_SHORT).show();
        }
    }
}