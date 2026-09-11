package com.example.rescueme1.Shelter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rescueme1.DB.AppointmentModel1;
import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.R;

public class S_Appoiment_ViewMore extends AppCompatActivity {
    private TextView tvPetId, tvPetCategory, tvPetName, tvPetAge, tvPetGender, tvPetDescription;
    private TextView tvUserName, tvUserContact;
    private ImageView imgPetProfile;
    private Spinner spinnerStatus;
    private Button btnOk;
    private DBHelper dbHelper;
    private int appointmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sappoiment_view_more);

        dbHelper = new DBHelper(this);
        initializeViews();

        AppointmentModel1 appointment = (AppointmentModel1) getIntent().getSerializableExtra("appointment_data");
        if (appointment == null) {
            Toast.makeText(this, "Appointment data not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        appointmentId = appointment.getAppointmentId();
        setupStatusSpinner(appointment.getAppointmentStatus());
        populateAppointmentData(appointment);
        setupOkButton();
    }

    private void initializeViews() {
        tvPetId = findViewById(R.id.tvpetid);
        tvPetCategory = findViewById(R.id.tvpetcategory);
        tvPetName = findViewById(R.id.tvpetname);
        tvPetAge = findViewById(R.id.tvpetage);
        tvPetGender = findViewById(R.id.tvpetgender);
        tvPetDescription = findViewById(R.id.tvpetdescription);
        tvUserName = findViewById(R.id.tvusername);
        tvUserContact = findViewById(R.id.tvusercontact);
        imgPetProfile = findViewById(R.id.imgpetProfile);
        spinnerStatus = findViewById(R.id.spinnerstutas);
        btnOk = findViewById(R.id.btnok);
    }

    private void setupStatusSpinner(String currentStatus) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.appointment_status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);

        // Set current status selection
        int spinnerPosition = adapter.getPosition(currentStatus);
        spinnerStatus.setSelection(spinnerPosition);
    }

    private void populateAppointmentData(AppointmentModel1 appointment) {
        tvPetId.setText(String.valueOf(appointment.getPetId()));
        tvPetCategory.setText(appointment.getPetCategory());
        tvPetName.setText(appointment.getPetName());
        tvPetAge.setText(appointment.getPetAge());
        tvPetGender.setText(appointment.getPetGender());
        tvPetDescription.setText(appointment.getPetDescription());
        tvUserName.setText(appointment.getUserName());
        tvUserContact.setText(appointment.getUserContact());

        byte[] imageBlob = appointment.getPetImageBlob();
        if (imageBlob != null && imageBlob.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.length);
            imgPetProfile.setImageBitmap(bitmap);
        } else {
            imgPetProfile.setImageResource(R.drawable.add_pet);
        }
    }

    private void setupOkButton() {
        btnOk.setOnClickListener(v -> updateAppointmentStatus());
    }

    private void updateAppointmentStatus() {
        String newStatus = spinnerStatus.getSelectedItem().toString();
        boolean success = dbHelper.updateAppointmentStatus(appointmentId, newStatus);

        if (success) {
            Toast.makeText(this, "Status updated successfully", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, "Failed to update status", Toast.LENGTH_SHORT).show();
        }
    }
}