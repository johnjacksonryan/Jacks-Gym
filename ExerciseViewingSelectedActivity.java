package com.johnjacksonryan.display.exercisemenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ExerciseViewingSelectedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_viewing_selected);

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

        TextView exerciseInfoTextView = (TextView) findViewById(R.id.infoExerciseViewedTextView);
        String s = getIntent().getExtras().getString("info");
        if (s == null) { s = "No information available"; }

        TextView nameInfoTextView = (TextView) findViewById(R.id.nameExerciseViewedTextView);
        String name = getIntent().getExtras().getString("name");
        nameInfoTextView.setText(name);
        exerciseInfoTextView.setText(s);

        TextView idExerciseTextView = (TextView) findViewById(R.id.idExerciseViewedTextView);
        String idString = getIntent().getExtras().getString("id");
        idExerciseTextView.setText(idString);
        int id = Integer.parseInt(idString.substring(idString.length()-3, idString.length()).replaceAll("\\s+",""));

        TextView currentMultiplierTextView = (TextView) findViewById(R.id.currentMultiplierTextView);
        currentMultiplierTextView.setText(String.format("Current multiplier: %s", String.valueOf(userManagerFacade.getExerciseMultiplier(id))));

        Button deleteExerciseBtn = (Button) findViewById(R.id.deleteViewingExerciseBtn);
        deleteExerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userManagerFacade.delete(id);
                Intent mainMenuIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(mainMenuIntent);
            }
        });

        Button exitToMainMenuBtn = (Button) findViewById(R.id.returnToMainMenuBtn);
        exitToMainMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent exerciseViewingIntent = new Intent(getApplicationContext(), ExerciseViewingDisplayActivity.class);
                int type = getIntent().getExtras().getInt("type");
                ArrayList<Integer> musclesIndex = getIntent().getExtras().getIntegerArrayList("muscles");
                exerciseViewingIntent.putExtra("type", type);
                exerciseViewingIntent.putExtra("muscles", musclesIndex);
                EditText multiplierEdit = (EditText) findViewById(R.id.multiplierEditText);
                if (!multiplierEdit.getText().toString().equals("")) {
                    int multiplier = Integer.parseInt(multiplierEdit.getText().toString());
                    userManagerFacade.setExerciseMultiplier(id, multiplier);
                }
                startActivity(exerciseViewingIntent);
            }
        });
    }
}