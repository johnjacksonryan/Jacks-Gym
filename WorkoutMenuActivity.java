package com.johnjacksonryan.display.workoutmenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.johnjacksonryan.R;
import com.johnjacksonryan.display.MainMenuActivity;

public class WorkoutMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_menu);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        AppCompatTextView title = (AppCompatTextView) findViewById(R.id.tvTitle);
        title.setText("Workouts");

        Button uncompletedWorkoutsBtn = (Button) findViewById(R.id.uncompletedWorkoutsBtn);
        uncompletedWorkoutsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent savedWorkoutsIntent = new Intent(getApplicationContext(), SavedWorkoutsActivity.class);
                savedWorkoutsIntent.putExtra("completed", false);
                savedWorkoutsIntent.putExtra("rated", false);
                startActivity(savedWorkoutsIntent);
            }
        });

        Button completedWorkoutsBtn = (Button) findViewById(R.id.completedWorkoutsBtn);
        completedWorkoutsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent savedWorkoutsIntent = new Intent(getApplicationContext(), SavedWorkoutsActivity.class);
                savedWorkoutsIntent.putExtra("completed", true);
                savedWorkoutsIntent.putExtra("rated", false);
                startActivity(savedWorkoutsIntent);
            }
        });

        Button ratingWorkoutsBtn = (Button) findViewById(R.id.ratedWorkoutsBtn);
        ratingWorkoutsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent savedWorkoutsIntent = new Intent(getApplicationContext(), SavedWorkoutsActivity.class);
                savedWorkoutsIntent.putExtra("completed", true);
                savedWorkoutsIntent.putExtra("rated", true);
                startActivity(savedWorkoutsIntent);
            }
        });

        Button exitBtn = (Button) findViewById(R.id.exitToMainMenuWorkoutBtn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainMenuIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(mainMenuIntent);
            }
        });

    }
}