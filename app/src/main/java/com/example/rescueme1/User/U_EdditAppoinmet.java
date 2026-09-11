package com.example.rescueme1.User;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.R;

import java.util.Calendar;

public class U_EdditAppoinmet extends AppCompatActivity {

    DatePicker datePicker;
    TimePicker timePicker;
    Button btnCancel, btnSubmit;
    DBHelper dbHelper;
    int appointmentId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ueddit_appoinmet); 

        dbHelper = new DBHelper(this);

        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        btnCancel = findViewById(R.id.btnCancel);
        btnSubmit = findViewById(R.id.btnSubmit);

        appointmentId = getIntent().getIntExtra("appointmentId", -1);
        if (appointmentId == -1) {
            Toast.makeText(this, "Invalid Appointment ID", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnCancel.setOnClickListener(v -> finish());

        btnSubmit.setOnClickListener(v -> {
            int year = datePicker.getYear();
            int month = datePicker.getMonth();
            int day = datePicker.getDayOfMonth();

            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();

            String selectedDate = String.format("%04d-%02d-%02d", year, month + 1, day);
            String selectedTime = String.format("%02d:%02d", hour, minute);

            boolean updated = dbHelper.updateAppointment(appointmentId, selectedDate, selectedTime);
            if (updated) {
                Toast.makeText(this, "Appointment Updated", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
