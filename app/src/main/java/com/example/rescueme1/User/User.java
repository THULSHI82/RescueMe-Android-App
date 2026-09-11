package com.example.rescueme1.User;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.rescueme1.databinding.ActivityMainBinding;

import com.example.rescueme1.R;
import com.example.rescueme1.databinding.ActivityUserBinding;

public class User extends AppCompatActivity {
    ActivityUserBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, new HomeFragment())
                    .commit();
        }

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {


                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout,new HomeFragment())
                        .commit();

            } else if (itemId == R.id.pet) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, new PetFragment())
                        .commit();

            } else if (itemId == R.id.donation) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, new DonationFragment())
                        .commit();

            } else if (itemId == R.id.mypet) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, new MyPetFragment())
                        .commit();

            } else if (itemId == R.id.account) {



                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout,new AccountFragment())
                        .commit();
            }
            return true;
        });

        View rootView = getWindow().getDecorView().getRootView();
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect();
            rootView.getWindowVisibleDisplayFrame(r);
            int screenHeight = rootView.getRootView().getHeight();
            int keypadHeight = screenHeight - r.bottom;

            if (keypadHeight > screenHeight * 0.15) {
                binding.bottomNavigationView.setVisibility(View.GONE);
            } else {
                binding.bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });

    }
    @SuppressWarnings("MissingSuperCall")
    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout);

        if (!(currentFragment instanceof HomeFragment)) {
            binding.bottomNavigationView.setSelectedItemId(R.id.home);
        }
    }
}