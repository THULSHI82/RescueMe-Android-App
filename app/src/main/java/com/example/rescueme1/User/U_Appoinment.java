package com.example.rescueme1.User;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class U_Appoinment extends AppCompatActivity {

    DBHelper dbHelper;
    DatePicker datePicker;
    TimePicker timePicker;
    Button btnSubmit, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_uappoinment);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);

        dbHelper = new DBHelper(this);

        Calendar today = Calendar.getInstance();
        datePicker.setMinDate(today.getTimeInMillis());

        timePicker.setHour(8);
        timePicker.setMinute(0);

        int petId = getIntent().getIntExtra("petId", -1);
        int userId = getIntent().getIntExtra("userId", -1);

        if (petId == -1 || userId == -1) {
            Toast.makeText(this, "Missing Pet or User ID!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        btnSubmit.setOnClickListener(v -> {
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth();
            int year = datePicker.getYear();
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();

            if (hour < 8 || hour >= 16) {
                Toast.makeText(this, "Please select a time between 08:00 and 16:00", Toast.LENGTH_SHORT).show();
                return;
            }

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day, hour, minute);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

            String appointmentDate = dateFormat.format(calendar.getTime());
            String appointmentTime = timeFormat.format(calendar.getTime());

            boolean inserted = dbHelper.insertAppointment(userId, petId, appointmentDate, appointmentTime, "pending");

            if (inserted) {
                Toast.makeText(this, "Appointment request submitted successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error saving appointment", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(v -> finish());
    }
}