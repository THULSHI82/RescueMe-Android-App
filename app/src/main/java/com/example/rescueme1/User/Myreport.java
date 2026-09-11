package com.example.rescueme1.User;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

public class Myreport extends AppCompatActivity implements OnMapReadyCallback {

    private static final int PICK_IMAGE_REQUEST = 1;

    private MapView mapView;

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    ImageView imgpetReport;

    EditText etDescription;

    Button btnreport;

    MaterialAutoCompleteTextView petcategory, spinnerPetId;

    DBHelper dbHelper;

    private GoogleMap googleMap;

    private double selectedLat = 0.0, selectedLng = 0.0;

    Uri selectedImageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_myreport);

        dbHelper = new DBHelper(this);
        petcategory = findViewById(R.id.petcategory);
        spinnerPetId = findViewById(R.id.spinnerPetId);
        etDescription = findViewById(R.id.etDescription);
        mapView = findViewById(R.id.branchMap);
        btnreport = findViewById(R.id.btnreport);
        imgpetReport = findViewById(R.id.imgpetReport);

        String[] petCategories = getResources().getStringArray(R.array.petcategory);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, petCategories);
        petcategory.setAdapter(categoryAdapter);

        List<String> shelterNames = dbHelper.getAllShelterNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, shelterNames);
        spinnerPetId.setAdapter(adapter);

        petcategory.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) petcategory.showDropDown();
        });

        spinnerPetId.setOnClickListener(v -> spinnerPetId.showDropDown());

        spinnerPetId.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) spinnerPetId.showDropDown();
        });

        imgpetReport.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        if (mapView != null) {
            Bundle mapViewBundle = null;
            if (savedInstanceState != null) {
                mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
            }
            mapView.onCreate(mapViewBundle);
            mapView.getMapAsync(this);
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(20, systemBars.top, 20, systemBars.bottom);
            return insets;
        });

        btnreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = petcategory.getText().toString().trim();
                String selectedShelterName = spinnerPetId.getText().toString().trim();
                String description = etDescription.getText().toString().trim();

                if (category.isEmpty() || selectedShelterName.isEmpty() || description.isEmpty() || selectedLat == 0.0 || selectedLng == 0.0 || selectedImageUri == null){
                    Toast.makeText(Myreport.this, "Please fill all fields and select a location on the map", Toast.LENGTH_SHORT).show();
                    return;
                }

                int shelterId = dbHelper.getShelterIdByName(selectedShelterName);
                if (shelterId == -1) {
                    Toast.makeText(Myreport.this, "Invalid shelter selected", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
                int appUserId = prefs.getInt("uid", -1);
                if (appUserId == -1) {
                    Toast.makeText(Myreport.this, "User not logged in", Toast.LENGTH_SHORT).show();
                    return;
                }

                byte[] image = null;
                try {
                    InputStream iStream = getContentResolver().openInputStream(selectedImageUri);
                    image = getBytes(iStream);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Myreport.this, "Failed to read image data", Toast.LENGTH_SHORT).show();
                    return;
                }

                Boolean success = dbHelper.insertReport(category, shelterId, appUserId, description, selectedLat, selectedLng, image, "pending");
                if (success){
                    Toast.makeText(Myreport.this, "Report submitted successfully!", Toast.LENGTH_SHORT).show();
                    petcategory.setText("");
                    spinnerPetId.setText("");
                    etDescription.setText("");
                    googleMap.clear();
                    selectedLat = 0.0;
                    selectedLng = 0.0;
                    selectedImageUri = null;

                    imgpetReport.setImageResource(R.drawable.add_pet);
                }else {
                    Toast.makeText(Myreport.this, "Failed to submit report", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private byte[] getBytes(InputStream inputStream) throws Exception{
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }

        return byteBuffer.toByteArray();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        LatLng sriLanka = new LatLng(6.9271, 79.8612);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sriLanka, 15));

        googleMap.setOnMapClickListener(latLng -> {
            googleMap.clear();
            googleMap.addMarker(new MarkerOptions().position(latLng).title("Selected Location"));

            selectedLat = latLng.latitude;
            selectedLng = latLng.longitude;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        mapView.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }
        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            imgpetReport.setImageURI(selectedImageUri);
        }
    }

}