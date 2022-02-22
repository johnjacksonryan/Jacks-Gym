package com.johnjacksonryan.display.locationmenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.johnjacksonryan.R;
import com.johnjacksonryan.display.workoutcreation.EquipmentSelectionActivity;

public class LocationNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_name);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        AppCompatTextView title = (AppCompatTextView) findViewById(R.id.tvTitle);
        title.setText("Location");

        Button goToEquipmentBtn = (Button) findViewById(R.id.goToEquipmentLocationBtn);
        goToEquipmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameEditText = (EditText) findViewById(R.id.newLocationNameEditText);
                String name = nameEditText.getText().toString();
                Intent equipmentSelectionIntent = new Intent(getApplicationContext(), EquipmentSelectionActivity.class);
                equipmentSelectionIntent.putExtra("location", true);
                equipmentSelectionIntent.putExtra("exercise", false);
                equipmentSelectionIntent.putExtra("preset", false);
                equipmentSelectionIntent.putExtra("name", name);
                startActivity(equipmentSelectionIntent);
            }
        });

    }
}