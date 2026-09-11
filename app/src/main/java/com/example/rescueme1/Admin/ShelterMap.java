package com.example.rescueme1.Admin;

import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import com.example.rescueme1.DB.DBHelper;
import com.example.rescueme1.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ShelterMap extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap gMap;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_map);

        dbHelper = new DBHelper(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        Cursor cursor = dbHelper.getAllShelters();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("shelter_name"));
                String latStr = cursor.getString(cursor.getColumnIndexOrThrow("lat"));
                String lonStr = cursor.getString(cursor.getColumnIndexOrThrow("lon"));

                if (latStr != null && lonStr != null) {
                    double lat = Double.parseDouble(latStr);
                    double lon = Double.parseDouble(lonStr);

                    LatLng shelterLocation = new LatLng(lat, lon);
                    gMap.addMarker(new MarkerOptions()
                            .position(shelterLocation)
                            .title(name));
                }

            } while (cursor.moveToNext());


            cursor.moveToFirst();
            String latStr = cursor.getString(cursor.getColumnIndexOrThrow("lat"));
            String lonStr = cursor.getString(cursor.getColumnIndexOrThrow("lon"));
            if (latStr != null && lonStr != null) {
                double lat = Double.parseDouble(latStr);
                double lon = Double.parseDouble(lonStr);
                LatLng firstShelter = new LatLng(lat, lon);
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstShelter, 10));
            }

            cursor.close();
        }
    }
}