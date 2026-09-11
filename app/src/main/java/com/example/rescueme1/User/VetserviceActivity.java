package com.example.rescueme1.User;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rescueme1.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VetserviceActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    private MapView mapView;
    private GoogleMap googleMap;
    private Button btnNavigateVetservise;
    private LatLng selectedLatLng = null;

    private final HashMap<Marker, VetLocation> markerVetMap = new HashMap<>();

    private final List<VetLocation> vetLocations = new ArrayList<VetLocation>() {{
        add(new VetLocation("Happy Paws Clinic", 6.9271, 79.8612, "011-1234567", true));
        add(new VetLocation("Animal Care Hospital", 6.9135, 79.8538, "011-7654321", false));
        add(new VetLocation("Vet Point Dehiwala", 6.8558, 79.8655, "011-4567890", true));
        add(new VetLocation("Colombo Pet Clinic", 6.9215, 79.8663, "011-9876543", false));
        add(new VetLocation("Kandy Animal Hospital", 7.2906, 80.6337, "081-2345678", true));
        add(new VetLocation("Pet Zone Kurunegala", 7.4868, 80.3625, "037-2233445", false));
        add(new VetLocation("Negombo Vet Care", 7.2095, 79.8356, "031-4455667", true));
        add(new VetLocation("Southern Pet Clinic", 6.0353, 80.2170, "041-9988776", false));
        add(new VetLocation("Gampaha Animal Hospital", 7.0882, 79.9949, "033-5566778", false));
        add(new VetLocation("Nugegoda Pet Clinic", 6.8720, 79.8895, "011-5566778", true));
        add(new VetLocation("Kelaniya Vet Center", 6.9551, 79.9227, "011-6677889", false));
        add(new VetLocation("Wellawatte Pet Medics", 6.8782, 79.8615, "011-4433221", true));
        add(new VetLocation("Rathmalana Animal Clinic", 6.8362, 79.8687, "011-2299448", false));
        add(new VetLocation("Matara Pet Hospital", 5.9485, 80.5353, "041-2277228", true));
        add(new VetLocation("Panadura Vet Services", 6.7136, 79.9033, "038-2345566", false));
        add(new VetLocation("Batticaloa Pet Clinic", 7.7187, 81.7038, "065-4455998", true));
        add(new VetLocation("Ampara Animal Hospital", 7.2909, 81.6718, "063-4455223", false));
        add(new VetLocation("Trinco Vet Center", 8.5874, 81.2152, "026-3344556", true));
        add(new VetLocation("Jaffna Pet Clinic", 9.6615, 80.0255, "021-3344556", false));
        add(new VetLocation("Anuradhapura Animal Med", 8.3114, 80.4037, "025-3344556", true));
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vetservice);

        mapView = findViewById(R.id.vetMap);
        btnNavigateVetservise = findViewById(R.id.btnNavigateVetservise);

        Bundle mapViewBundle = savedInstanceState != null ? savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY) : null;
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        btnNavigateVetservise.setOnClickListener(v -> {
            if (selectedLatLng != null) {
                String uri = "google.navigation:q=" + selectedLatLng.latitude + "," + selectedLatLng.longitude;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            );
            return insets;
        });
    }

    private void setupCustomInfoWindow() {
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(@NonNull Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(@NonNull Marker marker) {
                View view = getLayoutInflater().inflate(R.layout.custom_info_window, null);

                TextView tvTitle = view.findViewById(R.id.tvTitle);
                TextView tvDetails = view.findViewById(R.id.tvDetails);

                VetLocation location = markerVetMap.get(marker);
                if (location != null) {
                    tvTitle.setText(location.name);
                    tvDetails.setText("📞 " + location.phone + "\n🕓 Open 24/7: " + (location.isOpen24h ? "Yes" : "No"));
                }

                return view;
            }
        });

        googleMap.setOnInfoWindowClickListener(marker -> {
            VetLocation location = markerVetMap.get(marker);
            if (location != null) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + location.phone));
                startActivity(dialIntent);
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        this.googleMap = map;

        setupCustomInfoWindow();

        for (VetLocation location : vetLocations) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(new LatLng(location.lat, location.lng))
                    .title(location.name)
                    .icon(BitmapDescriptorFactory.defaultMarker(
                            location.isOpen24h ? BitmapDescriptorFactory.HUE_GREEN : BitmapDescriptorFactory.HUE_RED
                    ));

            Marker marker = googleMap.addMarker(markerOptions);
            markerVetMap.put(marker, location);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);

            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    LatLng userLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 13f));
                }
            });

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }

        googleMap.setOnMarkerClickListener(marker -> {
            selectedLatLng = marker.getPosition();
            marker.showInfoWindow();
            return true;
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED && googleMap != null) {
                    googleMap.setMyLocationEnabled(true);
                }
            }
        }
    }

    @Override protected void onResume() { super.onResume(); mapView.onResume(); }
    @Override protected void onPause() { super.onPause(); mapView.onPause(); }
    @Override protected void onDestroy() { super.onDestroy(); mapView.onDestroy(); }
    @Override public void onLowMemory() { super.onLowMemory(); mapView.onLowMemory(); }

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

    static class VetLocation {
        String name;
        double lat;
        double lng;
        String phone;
        boolean isOpen24h;

        VetLocation(String name, double lat, double lng, String phone, boolean isOpen24h) {
            this.name = name;
            this.lat = lat;
            this.lng = lng;
            this.phone = phone;
            this.isOpen24h = isOpen24h;
        }
    }
}
