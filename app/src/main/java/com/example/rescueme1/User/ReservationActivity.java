package com.example.rescueme1.User;

import static java.security.AccessController.getContext;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.DB.ShelterItem;
import com.example.rescueme1.R;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.util.List;

public class ReservationActivity extends AppCompatActivity {

    ImageView imgmypetProfile;
    TextView tvpetcategory, tvpetname, tvusername, tvuseremail, tvusercontact, tvuseramount;
    MaterialAutoCompleteTextView spinnerPetId;
    EditText etAddress, etStartdate, etEnddate;
    Button btnreserv;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reservation);

        dbHelper = new DBHelper(this);

        imgmypetProfile = findViewById(R.id.imgmypetProfile);
        tvpetcategory = findViewById(R.id.tvpetcategory);
        tvpetname = findViewById(R.id.tvpetname);
        tvusername = findViewById(R.id.tvusername);
        tvuseremail = findViewById(R.id.tvuseremail);
        tvusercontact = findViewById(R.id.tvusercontact);
        etAddress = findViewById(R.id.etAddress);
        spinnerPetId = findViewById(R.id.spinnerPetId);
        etStartdate = findViewById(R.id.etStartdate);
        etEnddate = findViewById(R.id.etEnddate);
        tvuseramount = findViewById(R.id.tvuseramount);
        btnreserv = findViewById(R.id.btnreserv);

        etStartdate.setOnClickListener(v -> showDatePicker(etStartdate));
        etEnddate.setOnClickListener(v -> showDatePicker(etEnddate));

        List<ShelterItem> shelterNames = dbHelper.getAllShelterItems();
        ArrayAdapter<ShelterItem> adapter = new ArrayAdapter<>(ReservationActivity.this, android.R.layout.simple_list_item_1, shelterNames);
        spinnerPetId.setAdapter(adapter);

        spinnerPetId.setOnClickListener(v -> spinnerPetId.showDropDown());
        spinnerPetId.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) spinnerPetId.showDropDown();
        });

        loadPetAndUserData();

        btnreserv.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            int userId = prefs.getInt("uid", -1);
            if (userId == -1) {
                Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
                return;
            }

            DBHelper.Pet pet = dbHelper.getLatestPetByUserId(userId);
            if (pet == null) {
                Toast.makeText(this, "No pet data found", Toast.LENGTH_SHORT).show();
                return;
            }

            String selectedName = spinnerPetId.getText().toString().trim();
            ShelterItem selectedShelter = null;
            for (int i = 0; i < spinnerPetId.getAdapter().getCount(); i++) {
                ShelterItem item = (ShelterItem) spinnerPetId.getAdapter().getItem(i);
                if (item.toString().equalsIgnoreCase(selectedName)) {
                    selectedShelter = item;
                    break;
                }
            }

            if (selectedShelter == null) {
                Toast.makeText(this, "Please select a shelter", Toast.LENGTH_SHORT).show();
                return;
            }

            String address = etAddress.getText().toString().trim();
            String start = etStartdate.getText().toString();
            String end = etEnddate.getText().toString();
            String amountStr = tvuseramount.getText().toString().replace("Rs. ", "").replace(".00", "").trim();

            if (address.isEmpty() || start.isEmpty() || end.isEmpty() || amountStr.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double amount = Double.parseDouble(amountStr);
                boolean inserted = dbHelper.insertReservation(
                        pet.id, userId, selectedShelter.getId(), address, start, end, amount
                );

                if (inserted) {
                    Toast.makeText(this, "Reservation successful!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Reservation failed", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error processing reservation", Toast.LENGTH_SHORT).show();
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loadPetAndUserData() {
        SharedPreferences prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        int userId = prefs.getInt("uid", -1);

        if (userId == -1) return;

        DBHelper.Pet pet = dbHelper.getLatestPetByUserId(userId);
        if (pet != null) {
            tvpetcategory.setText(pet.category);
            tvpetname.setText(pet.name);

            if (pet.image != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(pet.image, 0, pet.image.length);
                imgmypetProfile.setImageBitmap(bitmap);
            }
        }

        DBHelper.User user = dbHelper.getUserById(userId);
        if (user != null) {
            tvusername.setText(user.name);
            tvuseremail.setText(user.email);
            tvusercontact.setText(user.contact);
        }
    }

    private void showDatePicker(EditText targetField) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(ReservationActivity.this, (view, year1, month1, dayOfMonth) -> {
            String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
            targetField.setText(selectedDate);

            calculateAmount();

        }, year, month, day);
        dpd.show();
    }

    private void calculateAmount() {
        String start = etStartdate.getText().toString();
        String end = etEnddate.getText().toString();

        if (start.isEmpty() || end.isEmpty()) return;

        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            java.util.Date startDate = sdf.parse(start);
            java.util.Date endDate = sdf.parse(end);

            long diff = endDate.getTime() - startDate.getTime();
            long days = java.util.concurrent.TimeUnit.MILLISECONDS.toDays(diff) + 1;

            if (days <= 0) {
                tvuseramount.setText("Invalid Dates");
            } else {
                long total = days * 800;
                tvuseramount.setText("Rs. " + total + ".00");
            }

        } catch (Exception e) {
            e.printStackTrace();
            tvuseramount.setText("Error calculating amount");
        }
    }
}