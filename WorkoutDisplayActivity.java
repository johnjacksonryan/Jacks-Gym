package com.johnjacksonryan.display.workoutcreation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class WorkoutDisplayActivity extends AppCompatActivity implements WorkoutAdapter.OnNoteListener {

    RecyclerView workoutDisplay;
    String[] names;
    String[] ids;
    String[] subNames;
    String[] info;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_display);

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

        ArrayList<Integer> typeNum = getIntent().getExtras().getIntegerArrayList("typeNum");
        ArrayList<Integer> musclesIndex = getIntent().getExtras().getIntegerArrayList("muscles");
        ArrayList<Integer> equipment = getIntent().getExtras().getIntegerArrayList("equipment");
        int difficulty = getIntent().getExtras().getInt("difficulty");
        int numSuperSet = getIntent().getExtras().getInt("SS");
        int numDropSet = getIntent().getExtras().getInt("DS");
        int numSpecialRep = getIntent().getExtras().getInt("SR");
        int existingId = getIntent().getExtras().getInt("id");

        if (existingId == -1) {
            id = userManagerFacade.createWorkout(typeNum, musclesIndex, equipment, difficulty, numSuperSet, numDropSet, numSpecialRep);
            EditText nameEditText = (EditText) findViewById(R.id.workoutNameEditText);
            String name = nameEditText.getText().toString();
            userManagerFacade.saveWorkout(id, name);
        }
        else {
            id = existingId;
        }
        ArrayList<ArrayList<String>> workout = userManagerFacade.getWorkoutString(id);
        names = new String[workout.size()];
        ids = new String[workout.size()];
        subNames = new String[workout.size()];
        info = new String[workout.size()];
        String workoutName = workout.get(0).get(0);
        String workoutId;
        String workoutDuration;
        if (workoutName.equals("This workout could not be generated") || workout.get(1).isEmpty()) {
            workoutId = "";
            workoutDuration = "Please try again";
            for (int i = 1; i < workout.size(); i++) {
                names[i] = "  ";
                ids[i] = "    ";
                subNames[i] = "  ";
                info[i] = "  ";
            }
        }
        else {
            workoutId = workout.get(0).get(1);
            workoutDuration = workout.get(0).get(2);
            for (int i = 1; i < workout.size(); i++) {
                ArrayList<String> exercise = workout.get(i);
                names[i] = exercise.get(0);
                ids[i] = exercise.get(1);
                subNames[i] = exercise.get(2);
                info[i] = exercise.get(3);
            }
        }
        EditText nameEditText = (EditText) findViewById(R.id.workoutNameEditText);
        nameEditText.setText(workoutName);
        TextView idText = (TextView) findViewById(R.id.workoutIdText);
        idText.setText(workoutId);
        TextView durationText = (TextView) findViewById(R.id.workoutDurationText);
        durationText.setText(workoutDuration);
        workoutDisplay = (RecyclerView) findViewById(R.id.workoutDisplay);
        workoutDisplay.setLayoutManager(new LinearLayoutManager(this));
        workoutDisplay.setHasFixedSize(true);
        WorkoutAdapter workoutAdapter = new WorkoutAdapter(names, ids, subNames, info, this);
        workoutDisplay.setAdapter(workoutAdapter);


        Button deleteWorkoutBtn = (Button) findViewById(R.id.deleteWorkoutBtn);
        deleteWorkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userManagerFacade.deleteSavedWorkout(id);
                Intent mainMenuIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(mainMenuIntent);
            }
        });

        Button saveWorkoutBtn = (Button) findViewById(R.id.saveWorkoutBtn);
        saveWorkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameEditText = (EditText) findViewById(R.id.workoutNameEditText);
                String name = nameEditText.getText().toString();
                EditText ratingEditText = (EditText) findViewById(R.id.workoutRatingEditText);
                int rating = -1;
                if (!ratingEditText.getText().toString().equals("")) {
                    rating = Integer.parseInt(ratingEditText.getText().toString());
                }
                if (!(0<=rating && rating<=10)) {
                    rating = -1;
                }
                userManagerFacade.rateWorkout(id, rating);
                userManagerFacade.saveWorkout(id, name);
                Intent mainMenuIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(mainMenuIntent);
            }
        });

        Button completeWorkoutBtn = (Button) findViewById(R.id.completeWorkoutBtn);
        completeWorkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameEditText = (EditText) findViewById(R.id.workoutNameEditText);
                String name = nameEditText.getText().toString();
                EditText ratingEditText = (EditText) findViewById(R.id.workoutRatingEditText);
                int rating = -1;
                if (!ratingEditText.getText().toString().equals("")) {
                    rating = Integer.parseInt(ratingEditText.getText().toString());
                }
                if (!(0<=rating && rating<=10)) {
                    rating = -1;
                }
                userManagerFacade.rateWorkout(id, rating);
                userManagerFacade.completeWorkout(id, name);
                userManagerFacade.saveWorkout(id, name);
                Intent mainMenuIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(mainMenuIntent);
            }
        });
    }

    @Override
    public void OnNoteClick(int position, String exerciseInfo) {
        Intent exerciseInfoIntent = new Intent(this, ExerciseInfoActivity.class);
        exerciseInfoIntent.putExtra("info", exerciseInfo);
        exerciseInfoIntent.putExtra("name", names[position]);
        exerciseInfoIntent.putExtra("id", id);
        startActivity(exerciseInfoIntent);
    }
}