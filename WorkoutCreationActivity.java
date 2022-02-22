package com.johnjacksonryan.display.workoutcreation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.johnjacksonryan.R;
import com.johnjacksonryan.display.MainMenuActivity;

public class WorkoutCreationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_creation);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        AppCompatTextView title = (AppCompatTextView) findViewById(R.id.tvTitle);
        title.setText("Workout Creation");

        Button originalWorkoutBtn = (Button)findViewById(R.id.originalWorkoutBtn);
        originalWorkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent typeSelectionIntent = new Intent(getApplicationContext(), TypeSelectionActivity.class);
                typeSelectionIntent.putExtra("preset", false);
                startActivity(typeSelectionIntent);
            }
        });

        Button presetWorkoutBtn = (Button) findViewById(R.id.presetWorkoutBtn);
        presetWorkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent equipmentSelectionIntent = new Intent(getApplicationContext(), EquipmentSelectionActivity.class);
                equipmentSelectionIntent.putExtra("preset", true);
                equipmentSelectionIntent.putExtra("exercise", false);
                equipmentSelectionIntent.putExtra("location", false);
                startActivity(equipmentSelectionIntent);
            }
        });

        Button exitBtn = (Button) findViewById(R.id.exitToMainMenuCreationBtn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainMenuIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(mainMenuIntent);
            }
        });

    }
}