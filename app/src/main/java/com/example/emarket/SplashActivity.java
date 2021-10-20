package com.example.emarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null)
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run()
                {
                    Intent  intent = new Intent(SplashActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    finish();
                }
            },SPLASH_TIME_OUT);
        }
        else
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run()
                {
                    Intent  intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                    Toast.makeText(SplashActivity.this, "Welcome to eMarket" , Toast.LENGTH_SHORT).show();
                }
            },SPLASH_TIME_OUT);
        }
    }
}
