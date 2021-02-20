package com.example.bangsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onHideStatusAndNavigation(View v) {
        startActivity(new Intent(this, HideStatusAndNavigationActivity.class));
    }

    public void onFullScreen(View v) {
        startActivity(new Intent(this, FullScreenActivity.class));
    }

}