package com.johnjacksonryan.display.exercisemenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class ExerciseViewingRecoverActivity extends AppCompatActivity implements WorkoutAdapter.OnNoteListener {

    RecyclerView workoutDisplay;
    String[] names;
    String[] ids;
    String[] subNames;
    String[] info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_viewing_recover);
        ExerciseManager exerciseManager = new ExerciseManager();
        WorkoutManager workoutManager = new WorkoutManager(exerciseManager);
        LocationManager locationManager = new LocationManager();
        Updater updater = new Updater(exerciseManager, workoutManager, locationManager, this);
        UserManagerFacade userManagerFacade = new UserManagerFacade(exerciseManager, workoutManager, updater, locationManager);
        userManagerFacade.loadDefault();
        userManagerFacade.loadCustom();
        userManagerFacade.loadDeleted();

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        AppCompatTextView title = (AppCompatTextView) findViewById(R.id.tvTitle);
        title.setText("Trash");


        ArrayList<ArrayList<String>> exerciseList = userManagerFacade.getDeletedExerciseList();
        if (exerciseList.size() == 0 || exerciseList.get(0).size()!=4) {
            TextView selectToViewOptions = (TextView) findViewById(R.id.recoverExerciseTextView);
            String label = "Could not find any exercises";
            selectToViewOptions.setText(label);
        }
        else {
            names = new String[exerciseList.size()];
            ids = new String[exerciseList.size()];
            subNames = new String[exerciseList.size()];
            info = new String[exerciseList.size()];
            for (int i = 0; i < exerciseList.size(); i++) {
                ArrayList<String> exercise = exerciseList.get(i);
                names[i] = exercise.get(0);
                ids[i] = exercise.get(1);
                subNames[i] = exercise.get(2);
                info[i] = exercise.get(3);
            }
            workoutDisplay = (RecyclerView) findViewById(R.id.recoverExercisesRecyclerView);
            workoutDisplay.setLayoutManager(new LinearLayoutManager(this));
            workoutDisplay.setHasFixedSize(true);
            WorkoutAdapter workoutAdapter = new WorkoutAdapter(names, ids, subNames, info, this);
            workoutDisplay.setAdapter(workoutAdapter);
        }

        Button exitToMainBtn = (Button) findViewById(R.id.exitToMainRecoverBtn);
        exitToMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainMenuIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(mainMenuIntent);
            }
        });
    }

    @Override
    public void OnNoteClick(int position, String exerciseInfo) {
        ExerciseManager exerciseManager = new ExerciseManager();
        WorkoutManager workoutManager = new WorkoutManager(exerciseManager);
        LocationManager locationManager = new LocationManager();
        Updater updater = new Updater(exerciseManager, workoutManager, locationManager, this);
        UserManagerFacade userManagerFacade = new UserManagerFacade(exerciseManager, workoutManager, updater, locationManager);
        userManagerFacade.loadDefault();
        userManagerFacade.loadCustom();
        userManagerFacade.loadDeleted();

        String idString = ids[position];
        int id = Integer.parseInt(idString.substring(idString.length()-3, idString.length()).replaceAll("\\s+",""));
        userManagerFacade.recover(id);
        Intent mainMenuIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
        startActivity(mainMenuIntent);
    }
}