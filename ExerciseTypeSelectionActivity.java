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
import com.johnjacksonryan.display.workoutcreation.MuscleSelectionActivity;

import java.util.ArrayList;

public class ExerciseTypeSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_type_selection);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        AppCompatTextView title = (AppCompatTextView) findViewById(R.id.tvTitle);
        title.setText("Exercise Types");

        boolean viewing = getIntent().getExtras().getBoolean("view");
        if (viewing) {
            TextView selectYourType = (TextView) findViewById(R.id.selectYourTypeTextView);
            String s = "Which type of exercises do you want to view?";
            selectYourType.setText(s);
        }

        Button strengthBtn = (Button) findViewById(R.id.strengthExerciseSelectionBtn);
        strengthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewing) {
                    int t = 0;
                    Intent muscleSelectionIntent = new Intent(getApplicationContext(), MuscleSelectionActivity.class);
                    muscleSelectionIntent.putExtra("view", true);
                    muscleSelectionIntent.putExtra("exercise", true);
                    muscleSelectionIntent.putExtra("type", t);
                    startActivity(muscleSelectionIntent);
                }
                else {
                    int type = 0;
                    String name = getIntent().getExtras().getString("name");
                    String notes = getIntent().getExtras().getString("notes");
                    int duration = getIntent().getExtras().getInt("duration");
                    int reps = getIntent().getExtras().getInt("reps");
                    int sets = getIntent().getExtras().getInt("sets");
                    boolean superSet = getIntent().getExtras().getBoolean("SS");
                    boolean dropSet = getIntent().getExtras().getBoolean("DS");
                    boolean specialRep = getIntent().getExtras().getBoolean("SR");
                    ArrayList<Integer> difficulty = getIntent().getExtras().getIntegerArrayList("difficulty");
                    Intent muscleExerciseSelectionIntent = new Intent(getApplicationContext(), MuscleSelectionActivity.class);
                    muscleExerciseSelectionIntent.putExtra("name", name);
                    muscleExerciseSelectionIntent.putExtra("duration", duration);
                    muscleExerciseSelectionIntent.putExtra("reps", reps);
                    muscleExerciseSelectionIntent.putExtra("sets", sets);
                    muscleExerciseSelectionIntent.putExtra("difficulty", difficulty);
                    muscleExerciseSelectionIntent.putExtra("notes", notes);
                    muscleExerciseSelectionIntent.putExtra("SS", false);
                    muscleExerciseSelectionIntent.putExtra("DS", false);
                    muscleExerciseSelectionIntent.putExtra("SR", false);
                    muscleExerciseSelectionIntent.putExtra("type", type);
                    muscleExerciseSelectionIntent.putExtra("exercise", true);
                    startActivity(muscleExerciseSelectionIntent);
                }
            }
        });

        Button hypertrophyBtn = (Button) findViewById(R.id.hypertrophyExerciseSelectionBtn);
        hypertrophyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewing) {
                    int t = 1;
                    Intent muscleSelectionIntent = new Intent(getApplicationContext(), MuscleSelectionActivity.class);
                    muscleSelectionIntent.putExtra("view", true);
                    muscleSelectionIntent.putExtra("exercise", true);
                    muscleSelectionIntent.putExtra("type", t);
                    startActivity(muscleSelectionIntent);

                }
                else {
                    int type = 1;
                    String name = getIntent().getExtras().getString("name");
                    String notes = getIntent().getExtras().getString("notes");
                    int duration = getIntent().getExtras().getInt("duration");
                    int reps = getIntent().getExtras().getInt("reps");
                    int sets = getIntent().getExtras().getInt("sets");
                    boolean superSet = getIntent().getExtras().getBoolean("SS");
                    boolean dropSet = getIntent().getExtras().getBoolean("DS");
                    boolean specialRep = getIntent().getExtras().getBoolean("SR");
                    ArrayList<Integer> difficulty = getIntent().getExtras().getIntegerArrayList("difficulty");
                    Intent muscleExerciseSelectionIntent = new Intent(getApplicationContext(), MuscleSelectionActivity.class);
                    muscleExerciseSelectionIntent.putExtra("name", name);
                    muscleExerciseSelectionIntent.putExtra("duration", duration);
                    muscleExerciseSelectionIntent.putExtra("reps", reps);
                    muscleExerciseSelectionIntent.putExtra("sets", sets);
                    muscleExerciseSelectionIntent.putExtra("difficulty", difficulty);
                    muscleExerciseSelectionIntent.putExtra("notes", notes);
                    muscleExerciseSelectionIntent.putExtra("SS", superSet);
                    muscleExerciseSelectionIntent.putExtra("DS", dropSet);
                    muscleExerciseSelectionIntent.putExtra("SR", specialRep);
                    muscleExerciseSelectionIntent.putExtra("type", type);
                    muscleExerciseSelectionIntent.putExtra("exercise", true);
                    startActivity(muscleExerciseSelectionIntent);
                }
            }
        });

        Button enduranceBtn = (Button) findViewById(R.id.enduranceExerciseSelectionBtn);
        enduranceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewing) {
                    int t = 2;
                    Intent muscleSelectionIntent = new Intent(getApplicationContext(), MuscleSelectionActivity.class);
                    muscleSelectionIntent.putExtra("view", true);
                    muscleSelectionIntent.putExtra("exercise", true);
                    muscleSelectionIntent.putExtra("type", t);
                    startActivity(muscleSelectionIntent);

                }
                else {
                    int type = 2;
                    String name = getIntent().getExtras().getString("name");
                    String notes = getIntent().getExtras().getString("notes");
                    int duration = getIntent().getExtras().getInt("duration");
                    int reps = getIntent().getExtras().getInt("reps");
                    int sets = getIntent().getExtras().getInt("sets");
                    boolean superSet = getIntent().getExtras().getBoolean("SS");
                    boolean dropSet = getIntent().getExtras().getBoolean("DS");
                    boolean specialRep = getIntent().getExtras().getBoolean("SR");
                    ArrayList<Integer> difficulty = getIntent().getExtras().getIntegerArrayList("difficulty");
                    Intent muscleExerciseSelectionIntent = new Intent(getApplicationContext(), MuscleSelectionActivity.class);
                    muscleExerciseSelectionIntent.putExtra("name", name);
                    muscleExerciseSelectionIntent.putExtra("duration", duration);
                    muscleExerciseSelectionIntent.putExtra("reps", reps);
                    muscleExerciseSelectionIntent.putExtra("sets", sets);
                    muscleExerciseSelectionIntent.putExtra("difficulty", difficulty);
                    muscleExerciseSelectionIntent.putExtra("notes", notes);
                    muscleExerciseSelectionIntent.putExtra("SS", false);
                    muscleExerciseSelectionIntent.putExtra("DS", false);
                    muscleExerciseSelectionIntent.putExtra("SR", false);
                    muscleExerciseSelectionIntent.putExtra("type", type);
                    muscleExerciseSelectionIntent.putExtra("exercise", true);
                    startActivity(muscleExerciseSelectionIntent);
                }
            }
        });

        Button calisthenicBtn = (Button) findViewById(R.id.calisthenicExerciseSelectionBtn);
        calisthenicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewing) {
                    int t = 3;
                    Intent muscleSelectionIntent = new Intent(getApplicationContext(), MuscleSelectionActivity.class);
                    muscleSelectionIntent.putExtra("view", true);
                    muscleSelectionIntent.putExtra("exercise", true);
                    muscleSelectionIntent.putExtra("type", t);
                    startActivity(muscleSelectionIntent);

                }
                else {
                    int type = 3;
                    String name = getIntent().getExtras().getString("name");
                    String notes = getIntent().getExtras().getString("notes");
                    int duration = getIntent().getExtras().getInt("duration");
                    int reps = getIntent().getExtras().getInt("reps");
                    int sets = getIntent().getExtras().getInt("sets");
                    boolean superSet = getIntent().getExtras().getBoolean("SS");
                    boolean dropSet = getIntent().getExtras().getBoolean("DS");
                    boolean specialRep = getIntent().getExtras().getBoolean("SR");
                    ArrayList<Integer> difficulty = getIntent().getExtras().getIntegerArrayList("difficulty");
                    Intent muscleExerciseSelectionIntent = new Intent(getApplicationContext(), MuscleSelectionActivity.class);
                    muscleExerciseSelectionIntent.putExtra("name", name);
                    muscleExerciseSelectionIntent.putExtra("duration", duration);
                    muscleExerciseSelectionIntent.putExtra("reps", reps);
                    muscleExerciseSelectionIntent.putExtra("sets", sets);
                    muscleExerciseSelectionIntent.putExtra("difficulty", difficulty);
                    muscleExerciseSelectionIntent.putExtra("notes", notes);
                    muscleExerciseSelectionIntent.putExtra("SS", superSet);
                    muscleExerciseSelectionIntent.putExtra("DS", false);
                    muscleExerciseSelectionIntent.putExtra("SR", specialRep);
                    muscleExerciseSelectionIntent.putExtra("type", type);
                    muscleExerciseSelectionIntent.putExtra("exercise", true);
                    startActivity(muscleExerciseSelectionIntent);
                }
            }
        });

        Button conditioningBtn = (Button) findViewById(R.id.conditioningExerciseSelectionBtn);
        conditioningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewing) {
                    int t = 4;
                    Intent muscleSelectionIntent = new Intent(getApplicationContext(), MuscleSelectionActivity.class);
                    muscleSelectionIntent.putExtra("view", true);
                    muscleSelectionIntent.putExtra("exercise", true);
                    muscleSelectionIntent.putExtra("type", t);
                    startActivity(muscleSelectionIntent);

                }
                else {
                    int type = 4;
                    String name = getIntent().getExtras().getString("name");
                    String notes = getIntent().getExtras().getString("notes");
                    int duration = getIntent().getExtras().getInt("duration");
                    int reps = getIntent().getExtras().getInt("reps");
                    int sets = getIntent().getExtras().getInt("sets");
                    boolean superSet = getIntent().getExtras().getBoolean("SS");
                    boolean dropSet = getIntent().getExtras().getBoolean("DS");
                    boolean specialRep = getIntent().getExtras().getBoolean("SR");
                    ArrayList<Integer> difficulty = getIntent().getExtras().getIntegerArrayList("difficulty");
                    Intent muscleExerciseSelectionIntent = new Intent(getApplicationContext(), MuscleSelectionActivity.class);
                    muscleExerciseSelectionIntent.putExtra("name", name);
                    muscleExerciseSelectionIntent.putExtra("duration", duration);
                    muscleExerciseSelectionIntent.putExtra("reps", reps);
                    muscleExerciseSelectionIntent.putExtra("sets", sets);
                    muscleExerciseSelectionIntent.putExtra("difficulty", difficulty);
                    muscleExerciseSelectionIntent.putExtra("notes", notes);
                    muscleExerciseSelectionIntent.putExtra("SS", false);
                    muscleExerciseSelectionIntent.putExtra("DS", false);
                    muscleExerciseSelectionIntent.putExtra("SR", false);
                    muscleExerciseSelectionIntent.putExtra("type", type);
                    muscleExerciseSelectionIntent.putExtra("exercise", true);
                    startActivity(muscleExerciseSelectionIntent);
                }
            }
        });

        Button abBtn = (Button) findViewById(R.id.abExerciseSelectionBtn);
        abBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewing) {
                    int t = 5;
                    Intent muscleSelectionIntent = new Intent(getApplicationContext(), MuscleSelectionActivity.class);
                    muscleSelectionIntent.putExtra("view", true);
                    muscleSelectionIntent.putExtra("exercise", true);
                    muscleSelectionIntent.putExtra("type", t);
                    startActivity(muscleSelectionIntent);

                }
                else {
                    int type = 5;
                    String name = getIntent().getExtras().getString("name");
                    String notes = getIntent().getExtras().getString("notes");
                    int duration = getIntent().getExtras().getInt("duration");
                    int reps = getIntent().getExtras().getInt("reps");
                    int sets = getIntent().getExtras().getInt("sets");
                    boolean superSet = getIntent().getExtras().getBoolean("SS");
                    boolean dropSet = getIntent().getExtras().getBoolean("DS");
                    boolean specialRep = getIntent().getExtras().getBoolean("SR");
                    ArrayList<Integer> difficulty = getIntent().getExtras().getIntegerArrayList("difficulty");
                    Intent muscleExerciseSelectionIntent = new Intent(getApplicationContext(), MuscleSelectionActivity.class);
                    muscleExerciseSelectionIntent.putExtra("name", name);
                    muscleExerciseSelectionIntent.putExtra("duration", duration);
                    muscleExerciseSelectionIntent.putExtra("reps", reps);
                    muscleExerciseSelectionIntent.putExtra("sets", sets);
                    muscleExerciseSelectionIntent.putExtra("difficulty", difficulty);
                    muscleExerciseSelectionIntent.putExtra("notes", notes);
                    muscleExerciseSelectionIntent.putExtra("SS", false);
                    muscleExerciseSelectionIntent.putExtra("DS", false);
                    muscleExerciseSelectionIntent.putExtra("SR", false);
                    muscleExerciseSelectionIntent.putExtra("type", type);
                    muscleExerciseSelectionIntent.putExtra("exercise", true);
                    startActivity(muscleExerciseSelectionIntent);
                }
            }
        });

    }
}