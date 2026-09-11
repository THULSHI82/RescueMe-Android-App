package com.example.rescueme1.Shelter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.DB.ReportModel;
import com.example.rescueme1.R;

public class S_Report_ViewMore extends AppCompatActivity {

    private TextView tvpetcategory, tvpetdescription;
    private TextView tvusername, tvusercontact;
    private ImageView imgpetProfile;
    private Spinner spinnerStatus;
    private Button btnOk, btnviewlocation;
    private DBHelper dbHelper;
    private int reporttId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sreport_view_more);

        tvpetcategory = findViewById(R.id.tvpetcategory);
        tvpetdescription = findViewById(R.id.tvpetdescription);
        imgpetProfile = findViewById(R.id.imgpetProfile);
        tvusername = findViewById(R.id.tvusername);
        tvusercontact = findViewById(R.id.tvusercontact);
        spinnerStatus = findViewById(R.id.spinnerstutas);
        btnOk = findViewById(R.id.btnok);
        btnviewlocation = findViewById(R.id.btnviewlocation);
        dbHelper = new DBHelper(this);

        int reportId = getIntent().getIntExtra("report_id", -1);
        if (reportId == -1) {
            Toast.makeText(this, "Report ID not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        ReportModel report = dbHelper.getReportById(reportId);
        if (report == null) {
            Toast.makeText(this, "No report found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tvpetcategory.setText(report.getPetCategory());
        tvpetdescription.setText(report.getDescription());

        byte[] imageBytes = report.getImage();
        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imgpetProfile.setImageBitmap(bitmap);
        }

        setupStatusSpinner(report.getReporttStatus());

        String[] userDetails = dbHelper.getUserDetailsByUserUid(report.getReporterAppUserId());

        if (userDetails != null) {
            tvusername.setText(userDetails[0]);
            tvusercontact.setText(userDetails[1]);
        } else {
            tvusername.setText("N/A");
            tvusercontact.setText("N/A");
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnOk.setOnClickListener(v -> {
            String selectedStatus = spinnerStatus.getSelectedItem().toString();

            boolean updated = dbHelper.updateReportStatus(report.getId(), selectedStatus);

            if (updated) {
                Toast.makeText(this, "Status updated", Toast.LENGTH_SHORT).show();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("report_id", report.getId());
                resultIntent.putExtra("updated_status", selectedStatus);
                setResult(RESULT_OK, resultIntent);
                finish(); // Go back to fragment
            } else {
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
            }
        });

        btnviewlocation.setOnClickListener(v -> {
            double latitude = report.getLatitude();
            double longitude = report.getLongitude();

            String uri = "geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude + "(Reported Location)";

            Intent intent = new Intent(Intent.ACTION_VIEW, android.net.Uri.parse(uri));
            intent.setPackage("com.google.android.apps.maps");

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "Google Maps not available", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupStatusSpinner(String currentStatus) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.report_status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);

        int spinnerPosition = adapter.getPosition(currentStatus);
        spinnerStatus.setSelection(spinnerPosition);
    }
}