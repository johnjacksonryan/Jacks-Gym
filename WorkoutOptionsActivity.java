package com.johnjacksonryan.display.workoutmenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.johnjacksonryan.R;
import com.johnjacksonryan.display.MainMenuActivity;
import com.johnjacksonryan.display.workoutcreation.WorkoutDisplayActivity;
import com.johnjacksonryan.exercise.ExerciseManager;
import com.johnjacksonryan.gateway.Updater;
import com.johnjacksonryan.gateway.UserManagerFacade;
import com.johnjacksonryan.location.LocationManager;
import com.johnjacksonryan.workout.WorkoutManager;

public class WorkoutOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_options);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        AppCompatTextView title = (AppCompatTextView) findViewById(R.id.tvTitle);
        title.setText("Workout");

        ExerciseManager exerciseManager = new ExerciseManager();
        WorkoutManager workoutManager = new WorkoutManager(exerciseManager);
        LocationManager locationManager = new LocationManager();
        Updater updater = new Updater(exerciseManager, workoutManager, locationManager, this);
        UserManagerFacade userManagerFacade = new UserManagerFacade(exerciseManager, workoutManager, updater, locationManager);
        userManagerFacade.loadDefault();
        userManagerFacade.loadCustom();
        userManagerFacade.loadDeleted();
        userManagerFacade.loadWorkouts();

        String info = getIntent().getExtras().getString("info");
        String name = getIntent().getExtras().getString("name");
        String idString = getIntent().getExtras().getString("id");
        int id = Integer.parseInt(idString.substring(idString.length()-2, idString.length()).replaceAll("\\s+",""));

        TextView nameTextView = (TextView) findViewById(R.id.savedWorkoutNameTextView);
        nameTextView.setText(name);
        TextView infoTextView = (TextView) findViewById(R.id.workoutStringTextView);
        infoTextView.setText(info);

        Button deleteWorkoutBtn = (Button) findViewById(R.id.deleteSavedWorkoutBtn);
        deleteWorkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userManagerFacade.deleteSavedWorkout(id);
                Intent mainMenuIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(mainMenuIntent);
            }
        });

        Button doWorkoutBtn = (Button) findViewById(R.id.doSavedWorkoutBtn);
        doWorkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent workoutDisplayIntent = new Intent(getApplicationContext(), WorkoutDisplayActivity.class);
                workoutDisplayIntent.putExtra("id", id);
                startActivity(workoutDisplayIntent);
            }
        });
    }
}