package com.johnjacksonryan.display.exercisemenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.johnjacksonryan.R;
import com.johnjacksonryan.display.MainMenuActivity;

public class ExerciseMenuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_menu);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        AppCompatTextView title = (AppCompatTextView) findViewById(R.id.tvTitle);
        title.setText("Exercises");


        Button createExerciseBtn = (Button) findViewById(R.id.createExerciseBtn);
        createExerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent attributeExerciseIntent = new Intent(getApplicationContext(), ExerciseAttributeSelectionActivity.class);
                startActivity(attributeExerciseIntent);
            }
        });

        Button viewExerciseBtn = (Button) findViewById(R.id.viewExercisesBtn);
        viewExerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selectExerciseTypeIntent = new Intent(getApplicationContext(), ExerciseTypeSelectionActivity.class);
                selectExerciseTypeIntent.putExtra("view", true);
                startActivity(selectExerciseTypeIntent);
            }
        });

        Button recoverDeletedExerciseBtn = (Button) findViewById(R.id.recoverExerciseBtn);
        recoverDeletedExerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent exerciseViewingIntent = new Intent(getApplicationContext(), ExerciseViewingRecoverActivity.class);
                startActivity(exerciseViewingIntent);
            }
        });

        Button exitBtn = (Button) findViewById(R.id.exitToMainExerciseBtn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainMenuIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(mainMenuIntent);
            }
        });
    }
}