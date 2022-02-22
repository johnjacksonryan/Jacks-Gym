package com.johnjacksonryan.display.workoutcreation;

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

public class AttributeSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attribute_selection);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        AppCompatTextView title = (AppCompatTextView) findViewById(R.id.tvTitle);
        title.setText("Attributes");

        Button workoutBtn = (Button)findViewById(R.id.goToWorkout);
        workoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText numSSText = (EditText) findViewById(R.id.numSuperSets);
                EditText numDSText = (EditText) findViewById(R.id.numDropSets);
                EditText numSRText = (EditText) findViewById(R.id.numSpecialReps);
                int numSuperSet = 0;
                int numDropSet = 0;
                int numSpecialRep = 0;
                if (!numSSText.getText().toString().equals("")) {
                    numSuperSet = Integer.parseInt(numSSText.getText().toString());
                }
                if (numSuperSet < 0) {
                    numSuperSet = 0;
                }
                if (!numDSText.getText().toString().equals("")) {
                    numDropSet = Integer.parseInt(numDSText.getText().toString());
                }
                if (numDropSet < 0) {
                    numDropSet = 0;
                }
                if (!numSRText.getText().toString().equals("")) {
                    numSpecialRep = Integer.parseInt(numSRText.getText().toString());
                }
                if (numSpecialRep < 0) {
                    numSpecialRep = 0;
                }
                Switch beginner = (Switch) findViewById(R.id.beginner2);
                Switch intermediate = (Switch) findViewById(R.id.intermediate2);
                Switch advanced = (Switch) findViewById(R.id.advanced2);
                int difficulty = 1;
                if (beginner.isChecked()) {
                    difficulty = 0;
                }
                if (intermediate.isChecked()) {
                    difficulty = 1;
                }
                if (advanced.isChecked()) {
                    difficulty = 2;
                }
                Intent workoutDisplayIntent = new Intent(getApplicationContext(), WorkoutDisplayActivity.class);
                ArrayList<Integer> musclesIndex = getIntent().getExtras().getIntegerArrayList("muscles");
                workoutDisplayIntent.putExtra("muscles", musclesIndex);
                ArrayList<Integer> typeNum = getIntent().getExtras().getIntegerArrayList("typeNum");
                workoutDisplayIntent.putExtra("typeNum", typeNum);
                ArrayList<Integer> equipment = getIntent().getExtras().getIntegerArrayList("equipment");
                workoutDisplayIntent.putExtra("equipment", equipment);
                workoutDisplayIntent.putExtra("difficulty", difficulty);
                workoutDisplayIntent.putExtra("SS", numSuperSet);
                workoutDisplayIntent.putExtra("DS", numDropSet);
                workoutDisplayIntent.putExtra("SR", numSpecialRep);
                workoutDisplayIntent.putExtra("id", -1);
                startActivity(workoutDisplayIntent);


            }
        });


    }
}