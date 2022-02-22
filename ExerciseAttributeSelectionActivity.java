package com.johnjacksonryan.display.exercisemenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.johnjacksonryan.R;

import java.util.ArrayList;

public class ExerciseAttributeSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_attribute_selection);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        AppCompatTextView title = (AppCompatTextView) findViewById(R.id.tvTitle);
        title.setText("Attributes");

        Button goToTypeExerciseSelection = (Button) findViewById(R.id.goToTypeExerciseSelectionBtn);
        goToTypeExerciseSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameEditText = (EditText) findViewById(R.id.giveExerciseNameEditText);
                String name = nameEditText.getText().toString();
                EditText setsEditText = (EditText) findViewById(R.id.setsExerciseEditText);
                int sets = 3;
                if (!setsEditText.getText().toString().equals("")){
                    sets = Integer.parseInt(setsEditText.getText().toString());
                }
                if (sets < 0) {
                    sets = 3;
                }
                EditText repsEditText = (EditText) findViewById(R.id.repsExerciseEditText);
                int reps = 10;
                if (!setsEditText.getText().toString().equals("")){
                    reps = Integer.parseInt(repsEditText.getText().toString());
                }
                if (reps < -1) {
                    reps = 10;
                }
                EditText durationEditText = (EditText) findViewById(R.id.durationExerciseEditText);
                int duration = 5;
                if (!setsEditText.getText().toString().equals("")){
                    duration = Integer.parseInt(durationEditText.getText().toString());
                }
                if (duration < 0) {
                    duration = 5;
                }
                EditText notesEditText = (EditText) findViewById(R.id.notesExerciseEditText);
                String notes = notesEditText.getText().toString();

                boolean superSet = false;
                Switch superSetSwitch = (Switch) findViewById(R.id.superSetExerciseSwitch);
                if (superSetSwitch.isChecked()) {
                    superSet = true;
                }
                boolean dropSet = false;
                Switch dropSetSwitch = (Switch) findViewById(R.id.dropSetExerciseSwitch);
                if (dropSetSwitch.isChecked()) {
                    dropSet = true;
                }
                boolean specialRep = false;
                Switch specialRepSwitch = (Switch) findViewById(R.id.specialRepExerciseSwitch);
                if (specialRepSwitch.isChecked()) {
                    specialRep = true;
                }

                ArrayList<Integer> difficulty = new ArrayList<>();
                Switch beginnerSwitch = (Switch) findViewById(R.id.beginnerExerciseSwitch);
                if (beginnerSwitch.isChecked()) {
                    difficulty.add(0);
                }
                Switch intermediateSwitch = (Switch) findViewById(R.id.intermediateExerciseSwitch);
                if (intermediateSwitch.isChecked()) {
                    difficulty.add(1);
                }
                Switch advancedSwitch = (Switch) findViewById(R.id.advancedExerciseSwitch);
                if (advancedSwitch.isChecked()){
                    difficulty.add(2);
                }
                if (difficulty.isEmpty()) {
                    difficulty.add(1);
                }

                Intent exerciseTypeSelectionIntent = new Intent(getApplicationContext(), ExerciseTypeSelectionActivity.class);
                exerciseTypeSelectionIntent.putExtra("name", name);
                exerciseTypeSelectionIntent.putExtra("duration", duration);
                exerciseTypeSelectionIntent.putExtra("reps", reps);
                exerciseTypeSelectionIntent.putExtra("sets", sets);
                exerciseTypeSelectionIntent.putExtra("difficulty", difficulty);
                exerciseTypeSelectionIntent.putExtra("notes", notes);
                exerciseTypeSelectionIntent.putExtra("SS", superSet);
                exerciseTypeSelectionIntent.putExtra("DS", dropSet);
                exerciseTypeSelectionIntent.putExtra("SR", specialRep);
                exerciseTypeSelectionIntent.putExtra("view", false);
                startActivity(exerciseTypeSelectionIntent);
            }
        });


    }

}