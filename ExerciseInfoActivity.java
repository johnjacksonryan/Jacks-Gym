package com.johnjacksonryan.display.workoutcreation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.johnjacksonryan.R;

public class ExerciseInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_info);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        AppCompatTextView title = (AppCompatTextView) findViewById(R.id.tvTitle);
        title.setText("Exercise");

        int id = getIntent().getExtras().getInt("id");

        TextView exerciseInfoTextView = (TextView) findViewById(R.id.infoTextView);
        String s = getIntent().getExtras().getString("info");
        if (s == null) { s = "No information available"; }

        TextView nameInfoTextView = (TextView) findViewById(R.id.nameInfoTextView);
        String name = getIntent().getExtras().getString("name");
        nameInfoTextView.setText(name);
        exerciseInfoTextView.setText(s);

        Button backToWorkoutBtn = (Button) findViewById(R.id.backToWorkoutBtn);
        backToWorkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent workoutDisplayIntent = new Intent(getApplicationContext(), WorkoutDisplayActivity.class);
                workoutDisplayIntent.putExtra("id", id);
                startActivity(workoutDisplayIntent);
            }
        });
    }
}