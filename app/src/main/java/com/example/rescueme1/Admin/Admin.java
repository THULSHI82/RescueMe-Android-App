package com.example.rescueme1.Admin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // correct toolbar
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.rescueme1.Opening.Login;
import com.example.rescueme1.R;
import com.example.rescueme1.Shelter.Shelter;
import com.google.android.material.navigation.NavigationView;

public class Admin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Admin");
        }

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.open_nav, R.string.close_nav
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new DashbordFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.dashboard);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.dashboard) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new DashbordFragment())
                    .commit();
        } else if (id == R.id.shelter) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new A_ShelterFragment())
                    .commit();
        } else if (id == R.id.pet) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new A_PestFragment())
                    .commit();
        } else if (id == R.id.adoption) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new A_AdoptionFragment())
                    .commit();
        } else if (id == R.id.promotions) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new PromotionsFragment())
                    .commit();
        } else if (id == R.id.user) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new A_UserFragment())
                    .commit();
        } else if (id == R.id.ucontct) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new A_UContactFragment())
                    .commit();
        } else if (id == R.id.scontct) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new A_SContactFragment())
                    .commit();
        }  else if (id == R.id.nav_logout) {
            getSharedPreferences("app_session", MODE_PRIVATE)
                    .edit()
                    .clear()
                    .apply();

            Toast.makeText(this, "Logout successful", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Admin.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            finish();
        }


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



    @SuppressWarnings("MissingSuperCall")
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

            if (!(currentFragment instanceof DashbordFragment)) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new DashbordFragment())
                        .commit();
            }
        }
    }



}
