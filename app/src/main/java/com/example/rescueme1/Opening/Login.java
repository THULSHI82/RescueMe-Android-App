package com.example.rescueme1.Opening;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rescueme1.Admin.Admin;
import com.example.rescueme1.DB.ShelterModel;
import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.R;
import com.example.rescueme1.Shelter.Shelter;
import com.example.rescueme1.User.User;

public class Login extends AppCompatActivity {

    EditText etEmail, etPassword;
    TextView tvLogin;
    Button btnLogin;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sessionPrefs = getSharedPreferences("app_session", MODE_PRIVATE);
        SharedPreferences userPrefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences shelterPrefs = getSharedPreferences("shelter_data", MODE_PRIVATE);
//        boolean isLoggedIn = sessionPrefs.getBoolean("isLoggedIn", false);
//        String userType = sessionPrefs.getString("userType", "");

//        if (isLoggedIn) {
//            Intent intent = null;
//            switch (userType) {
//                case "u":
//                    intent = new Intent(Login.this, User.class);
//                    break;
//                case "a":
//                    intent = new Intent(Login.this, Admin.class);
//                    break;
//                case "s":
//                    intent = new Intent(Login.this, Shelter.class);
//                    break;
//            }
//            if (intent != null) {
//                startActivity(intent);
//                finish();
//                return;
//            }
//        }

        if (userPrefs.getBoolean("isLoggedIn", false)) {
            startActivity(new Intent(Login.this, User.class));
            finish();
            return;
        } else if (sessionPrefs.getBoolean("isLoggedIn", false)) {
            startActivity(new Intent(Login.this, Admin.class));
            finish();
            return;
        } else if (shelterPrefs.getBoolean("isLoggedIn", false)) {
            startActivity(new Intent(Login.this, Shelter.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvLogin = findViewById(R.id.tvLogin);

        dbHelper = new DBHelper(this);

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finishAffinity();
            }
        });

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(Login.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM appuser WHERE email = ?", new String[]{email});

            if (cursor.moveToFirst()) {
                String userTypeFromDb = cursor.getString(cursor.getColumnIndexOrThrow("user_type"));
                Cursor checker = null;

                switch (userTypeFromDb) {
                    case "u":
                        checker = db.rawQuery("SELECT uid FROM users WHERE user_email = ? AND user_password = ?", new String[]{email, password});
                        if (checker.moveToFirst()) {
                            int userId = checker.getInt(checker.getColumnIndexOrThrow("uid"));

                            SharedPreferences.Editor editor = getSharedPreferences("user_prefs", MODE_PRIVATE).edit();
                            editor.putBoolean("isLoggedIn", true);
                            editor.putString("userType", "u");
                            editor.putInt("uid", userId);
                            editor.apply();

                            Toast.makeText(Login.this, "User Login Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this, User.class));
                            finish();
                        }
                        break;

                    case "a":
                        checker = db.rawQuery("SELECT * FROM admin WHERE admin_email = ? AND admin_password = ?", new String[]{email, password});
                        if (checker.moveToFirst()) {
                            SharedPreferences.Editor editor = getSharedPreferences("app_session", MODE_PRIVATE).edit();
                            editor.putBoolean("isLoggedIn", true);
                            editor.putString("userType", "a");
                            editor.apply();

                            Toast.makeText(Login.this, "Admin Login Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this, Admin.class));
                            finish();
                        }
                        break;

                    case "s":
                        checker = db.rawQuery("SELECT * FROM shelter WHERE shelter_email = ? AND shelter_password = ?", new String[]{email, password});
                        if (checker.moveToFirst()) {
                            int sid = checker.getInt(checker.getColumnIndexOrThrow("sid"));
                            int appUserId = checker.getInt(checker.getColumnIndexOrThrow("appuser_id"));
                            String name = checker.getString(checker.getColumnIndexOrThrow("shelter_name"));
                            String owner = checker.getString(checker.getColumnIndexOrThrow("owner_name"));
                            String shelterEmail = checker.getString(checker.getColumnIndexOrThrow("shelter_email"));
                            String contact = checker.getString(checker.getColumnIndexOrThrow("shelter_contact"));
                            String lat = checker.getString(checker.getColumnIndexOrThrow("lat"));
                            String lon = checker.getString(checker.getColumnIndexOrThrow("lon"));
                            String regNo = checker.getString(checker.getColumnIndexOrThrow("registration_number"));
                            String estDate = checker.getString(checker.getColumnIndexOrThrow("established_date"));
                            String description = checker.getString(checker.getColumnIndexOrThrow("description"));
                            byte[] image = checker.getBlob(checker.getColumnIndexOrThrow("profile_shelter_image"));

                            SharedPreferences.Editor editor = getSharedPreferences("shelter_data", MODE_PRIVATE).edit();
                            editor.putBoolean("isLoggedIn", true);
                            editor.putString("userType", "s");
                            editor.putInt("sid", sid);
                            editor.putInt("uid", appUserId);
                            editor.apply();

                            ShelterModel shelterModel = new ShelterModel(
                                    sid, name, owner, shelterEmail, contact,
                                    lat, lon, regNo, estDate, description, null
                            );

                            Toast.makeText(Login.this, "Shelter Login Success", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Login.this, Shelter.class);
                            intent.putExtra("shelter_data", shelterModel);
                            startActivity(intent);
                            finish();
                        }
                        break;
                }

                if (checker != null && checker.getCount() == 0) {
                    Toast.makeText(Login.this, "Incorrect password.", Toast.LENGTH_SHORT).show();
                }

                if (checker != null) checker.close();
            } else {
                Toast.makeText(Login.this, "Email not found.", Toast.LENGTH_SHORT).show();
            }

            cursor.close();
            db.close();
        });

        tvLogin.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Singup.class);
            startActivity(intent);
            finish();
        });

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
