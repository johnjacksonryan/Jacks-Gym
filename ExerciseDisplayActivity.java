package com.johnjacksonryan.display.exercisemenu;

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
import com.johnjacksonryan.exercise.ExerciseManager;
import com.johnjacksonryan.gateway.Updater;
import com.johnjacksonryan.gateway.UserManagerFacade;
import com.johnjacksonryan.location.LocationManager;
import com.johnjacksonryan.workout.WorkoutManager;

import java.util.ArrayList;

public class ExerciseDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_display);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        AppCompatTextView title = (AppCompatTextView) findViewById(R.id.tvTitle);
        title.setText("Exercise");

        ExerciseManager exerciseManager = new ExerciseManager();
        WorkoutManager workoutManager = new WorkoutManager(exerciseManager);
        LocationManager locationManager = new LocationManager();
        Updater updater = new Updater(exerciseManager, workoutManager, locationManager, this);
        UserManagerFacade userManagerFacade = new UserManagerFacade(exerciseManager, workoutManager, updater, locationManager);
        userManagerFacade.loadDefault();
        userManagerFacade.loadCustom();
        userManagerFacade.loadDeleted();
        userManagerFacade.loadWorkouts();

        TextView nameTextView = (TextView) findViewById(R.id.nameOfExerciseTextView);
        TextView idTextView  = (TextView) findViewById(R.id.idOfExerciseTextView);
        TextView durationTextView = (TextView) findViewById(R.id.durationOfExerciseTextView);
        TextView informationTextView = (TextView) findViewById(R.id.informationOfExerciseTextView);

        int id;
        boolean created = getIntent().getExtras().getBoolean("exercise");
        if (created) {
            String name = getIntent().getExtras().getString("name");
            String notes = getIntent().getExtras().getString("notes");
            int duration = getIntent().getExtras().getInt("duration");
            int reps = getIntent().getExtras().getInt("reps");
            int sets = getIntent().getExtras().getInt("sets");
            int type = getIntent().getExtras().getInt("type");
            boolean superSet = getIntent().getExtras().getBoolean("SS");
            int ss = 1;
            if (superSet) {ss = 0;}
            boolean dropSet = getIntent().getExtras().getBoolean("DS");
            int ds = 1;
            if (dropSet) {ds = 0;}
            boolean specialRep = getIntent().getExtras().getBoolean("SR");
            int sr = 1;
            if (specialRep) {sr = 0;}
            ArrayList<Integer> difficulty = getIntent().getExtras().getIntegerArrayList("difficulty");
            ArrayList<Integer> musclesIndex = getIntent().getExtras().getIntegerArrayList("muscles");
            ArrayList<Integer> equipmentIndex = getIntent().getExtras().getIntegerArrayList("equipment");
            ArrayList<ArrayList<Integer>> equipment = new ArrayList<>();
            for (int i : equipmentIndex) {
                ArrayList<Integer> e = new ArrayList<>();
                e.add(i);
                equipment.add(e);
            }
            id = userManagerFacade.createExercise(type, name, musclesIndex, ss, ds, sr, equipment, duration, reps, sets, difficulty, notes);
        }
        else {
            id = getIntent().getExtras().getInt("id");
        }

        ArrayList<String> exerciseStringList = exerciseManager.getExerciseStringList(id);

        nameTextView.setText(exerciseStringList.get(0));
        idTextView.setText(exerciseStringList.get(1));
        durationTextView.setText(exerciseStringList.get(2));
        informationTextView.setText(exerciseStringList.get(3));

        Button deleteExerciseBtn = (Button) findViewById(R.id.deleteSelectedExerciseBtn);
        deleteExerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userManagerFacade.delete(id);
                Intent mainMenuIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(mainMenuIntent);
            }
        });

        Button exitToMainMenuBtn = (Button) findViewById(R.id.exitToMainMenuBtn);
        exitToMainMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainMenuIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(mainMenuIntent);
            }
        });


    }
}