package com.example.rescueme1.Shelter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.rescueme1.DB.ShelterModel;
import com.example.rescueme1.Opening.Login;
import com.example.rescueme1.R;
import com.google.android.material.navigation.NavigationView;

public class Shelter extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout shelter_layout;
    private ShelterModel shelter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shelter);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        shelter_layout = findViewById(R.id.shelter_layout);
        NavigationView navigationView = findViewById(R.id.nav_view1);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView txtName = headerView.findViewById(R.id.txtname);
        TextView txtEmail = headerView.findViewById(R.id.txtemail);

        shelter = (ShelterModel) getIntent().getSerializableExtra("shelter_data");

        if (shelter != null) {
            txtName.setText(shelter.getName());
            txtEmail.setText(shelter.getEmail());

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(shelter.getName());
            }

            SharedPreferences sharedPreferences = getSharedPreferences("shelter_session", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("sid", shelter.getId());
            editor.apply();
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, shelter_layout, toolbar,
                R.string.open_nav, R.string.close_nav
        );
        shelter_layout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            loadFragment(new S_DashboardFragment());
            navigationView.setCheckedItem(R.id.s_dashboard);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.s_dashboard) {
            loadFragment(new S_DashboardFragment());
        } else if (id == R.id.s_pet) {
            loadFragmentWithShelterData(new S_PetFragment());
        } else if (id == R.id.s_adoption) {
            loadFragmentWithShelterData(new S_AdoptionFragment());
        } else if (id == R.id.s_report) {
            loadFragmentWithShelterData(new S_ReportFragment());
        } else if (id == R.id.s_appoiment) {
            loadFragmentWithShelterData(new S_AppoimentFragment());
        } else if (id == R.id.s_doneshion) {
            loadFragmentWithShelterData(new S_DoneshionFragment());
        } else if (id == R.id.s_reservation) {
            loadFragmentWithShelterData(new S_ReservationFragment());
        } else if (id == R.id.s_account) {
            loadFragmentWithShelterData(new S_AccountFragment());
        } else if (id == R.id.s_contact) {
            loadFragmentWithShelterData(new S_ContactUsFragment());
        }else if (id == R.id.nav_logout) {
            getSharedPreferences("shelter_data", MODE_PRIVATE)
                    .edit()
                    .clear()
                    .apply();

            Toast.makeText(this, "Logout successful", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Shelter.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            finish();
        }


        shelter_layout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container1, fragment)
                .commit();
    }

    private void loadFragmentWithShelterData(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("shelter_data", shelter);
        fragment.setArguments(bundle);
        loadFragment(fragment);
    }

    @SuppressWarnings("MissingSuperCall")
    @Override
    public void onBackPressed() {
        if (shelter_layout.isDrawerOpen(GravityCompat.START)) {
            shelter_layout.closeDrawer(GravityCompat.START);
        } else {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container1);
            if (!(currentFragment instanceof S_DashboardFragment)) {
                loadFragment(new S_DashboardFragment());
            }
        }
    }
}
