package com.johnjacksonryan.display.locationmenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.johnjacksonryan.R;
import com.johnjacksonryan.display.MainMenuActivity;

public class LocationMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_menu);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        AppCompatTextView title = (AppCompatTextView) findViewById(R.id.tvTitle);
        title.setText("Locations");

        Button createLocationBtn = (Button) findViewById(R.id.createLocationBtn);
        createLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent locationNameIntent = new Intent(getApplicationContext(), LocationNameActivity.class);
                startActivity(locationNameIntent);
            }
        });

        Button viewLocationsBtn = (Button) findViewById(R.id.viewLocationsBtn);
        viewLocationsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewLocationIntent = new Intent(getApplicationContext(), LocationListDisplayActivity.class);
                viewLocationIntent.putExtra("viewing", true);
                startActivity(viewLocationIntent);
            }
        });

        Button exitBtn = (Button) findViewById(R.id.exitToMainLocationMenuBtn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainMenuIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(mainMenuIntent);
            }
        });
    }
}