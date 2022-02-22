package com.johnjacksonryan.display.workoutmenu;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.johnjacksonryan.R;
import com.johnjacksonryan.adapters.WorkoutAdapter;
import com.johnjacksonryan.display.MainMenuActivity;
import com.johnjacksonryan.exercise.ExerciseManager;
import com.johnjacksonryan.gateway.Updater;
import com.johnjacksonryan.gateway.UserManagerFacade;
import com.johnjacksonryan.location.LocationManager;
import com.johnjacksonryan.workout.WorkoutManager;

import java.util.ArrayList;

public class SavedWorkoutsActivity extends AppCompatActivity implements WorkoutAdapter.OnNoteListener {

    RecyclerView workoutDisplay;
    String[] names;
    String[] ids;
    String[] subNames;
    String[] info;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_workouts);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        AppCompatTextView title = (AppCompatTextView) findViewById(R.id.tvTitle);
        title.setText("Saved Workouts");

        Button exitSavedWorkoutsBtn = (Button) findViewById(R.id.savedWorkoutsExitBtn);
        exitSavedWorkoutsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainMenuIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(mainMenuIntent);
            }
        });

        ExerciseManager exerciseManager = new ExerciseManager();
        WorkoutManager workoutManager = new WorkoutManager(exerciseManager);
        LocationManager locationManager = new LocationManager();
        Updater updater = new Updater(exerciseManager, workoutManager, locationManager, this);
        UserManagerFacade userManagerFacade = new UserManagerFacade(exerciseManager, workoutManager, updater, locationManager);
        userManagerFacade.loadDefault();
        userManagerFacade.loadCustom();
        userManagerFacade.loadDeleted();
        userManagerFacade.loadWorkouts();

        boolean complete = getIntent().getExtras().getBoolean("completed");
        boolean rated = getIntent().getExtras().getBoolean("rated");
        ArrayList<ArrayList<String>> workouts = null;

        if (!rated) {
            workouts = userManagerFacade.getWorkoutList(complete);
        }
        else {
            workouts = userManagerFacade.getWorkoutRatingList();
        }
        names = new String[workouts.size()];
        ids = new String[workouts.size()];
        subNames = new String[workouts.size()];
        info = new String[workouts.size()];
        for (int i = 0; i < workouts.size(); i++) {
            ArrayList<String> exercise = workouts.get(i);
            names[i] = exercise.get(0);
            ids[i] = exercise.get(1);
            subNames[i] = exercise.get(2);
            info[i] = exercise.get(3);
        }
        workoutDisplay = (RecyclerView) findViewById(R.id.workoutListRecyclerView);
        workoutDisplay.setLayoutManager(new LinearLayoutManager(this));
        workoutDisplay.setHasFixedSize(true);
        WorkoutAdapter workoutAdapter = new WorkoutAdapter(names, ids, subNames, info, this);
        workoutDisplay.setAdapter(workoutAdapter);
    }

    @Override
    public void OnNoteClick(int position, String exerciseInfo) {
        Intent workoutOptionsIntent = new Intent(this, WorkoutOptionsActivity.class);
        workoutOptionsIntent.putExtra("info", exerciseInfo);
        workoutOptionsIntent.putExtra("name", names[position]);
        workoutOptionsIntent.putExtra("id", ids[position]);
        startActivity(workoutOptionsIntent);
    }
}