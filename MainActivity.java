package com.johnjacksonryan;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.johnjacksonryan.display.MainMenuActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent mainMenuIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
        startActivity(mainMenuIntent);
    }
}