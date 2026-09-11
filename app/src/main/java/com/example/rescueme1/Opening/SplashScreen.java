package com.example.rescueme1.Opening;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rescueme1.R;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_SCREEN = 3000;

    ImageView imgsp;
    TextView txtsp;
    Animation top, bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        imgsp = findViewById(R.id.imgsp);
        txtsp = findViewById(R.id.txtsp);

        top = AnimationUtils.loadAnimation(this, R.anim.top);
        bottom = AnimationUtils.loadAnimation(this, R.anim.bottom);

        imgsp.setAnimation(top);
        txtsp.setAnimation(bottom);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, Welcome.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);
    }
}
