package com.johnjacksonryan.display;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.johnjacksonryan.R;
import com.johnjacksonryan.display.exercisemenu.ExerciseMenuActivity;
import com.johnjacksonryan.display.information.InformationMenuActivity;
import com.johnjacksonryan.display.locationmenu.LocationMenuActivity;
import com.johnjacksonryan.display.workoutcreation.WorkoutCreationActivity;
import com.johnjacksonryan.display.workoutmenu.WorkoutMenuActivity;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        Button createWorkoutBtn = (Button)findViewById(R.id.createWorkoutBtn);
        createWorkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createWorkoutIntent = new Intent(getApplicationContext(), WorkoutCreationActivity.class);
                startActivity(createWorkoutIntent);
            }
        });

        Button workoutMenuBtn = (Button)findViewById(R.id.workoutsBtn);
        workoutMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent workoutMenuIntent = new Intent(getApplicationContext(), WorkoutMenuActivity.class);
                startActivity(workoutMenuIntent);
            }
        });

        Button exerciseMenuBtn = (Button)findViewById(R.id.exercisesBtn);
        exerciseMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent exerciseMenuIntent = new Intent(getApplicationContext(), ExerciseMenuActivity.class);
                startActivity(exerciseMenuIntent);
            }
        });

        Button locationMenuBtn = (Button)findViewById(R.id.locationsBtn);
        locationMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent locationMenuIntent = new Intent(getApplicationContext(), LocationMenuActivity.class);
                startActivity(locationMenuIntent);
            }
        });

        Button informationBtn = (Button)findViewById(R.id.informationBtn);
        informationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent informationMenuIntent = new Intent(getApplicationContext(), InformationMenuActivity.class);
                startActivity(informationMenuIntent);
            }
        });
    }
}