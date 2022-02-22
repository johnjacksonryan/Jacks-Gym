package com.johnjacksonryan.display.workoutcreation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.johnjacksonryan.R;

import java.util.ArrayList;
import java.util.Arrays;

public class TypeSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_selection);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        AppCompatTextView title = (AppCompatTextView) findViewById(R.id.tvTitle);
        title.setText("Exercise Types");

        Button next = (Button)findViewById(R.id.goToEquipment);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText strength = (EditText) findViewById(R.id.numStrength);
                EditText hypertrophy = (EditText) findViewById(R.id.numHypertrophy);
                EditText endurance = (EditText) findViewById(R.id.numEndurance);
                EditText calisthenic = (EditText) findViewById(R.id.numCalisthenic);
                EditText conditioning = (EditText) findViewById(R.id.numConditioning);
                EditText ab = (EditText) findViewById(R.id.numAb);

                int numStrength = 0;
                int numHypertrophy = 0;
                int numEndurance = 0;
                int numCalisthenic = 0;
                int numConditioning = 0;
                int numAb = 0;
                if (!strength.getText().toString().equals("")) {
                    numStrength = Integer.parseInt(strength.getText().toString());
                }
                if (numStrength < 0) {
                    numStrength = 0;
                }
                if (!hypertrophy.getText().toString().equals("")) {
                    numHypertrophy = Integer.parseInt(hypertrophy.getText().toString());
                }
                if (numHypertrophy < 0) {
                    numHypertrophy = 0;
                }
                if (!endurance.getText().toString().equals("")) {
                    numEndurance = Integer.parseInt(endurance.getText().toString());
                }
                if (numEndurance < 0) {
                    numEndurance = 0;
                }
                if (!calisthenic.getText().toString().equals("")) {
                    numCalisthenic = Integer.parseInt(calisthenic.getText().toString());
                }
                if (numCalisthenic < 0) {
                    numCalisthenic = 0;
                }
                if (!conditioning.getText().toString().equals("")) {
                    numConditioning = Integer.parseInt(conditioning.getText().toString());
                }
                if (numConditioning < 0) {
                    numConditioning = 0;
                }
                if (!ab.getText().toString().equals("")) {
                    numAb = Integer.parseInt(ab.getText().toString());
                }
                if (numAb < 0) {
                    numAb = 0;
                }
                ArrayList<Integer> typeNum = new ArrayList<>();
                typeNum.add(numStrength);
                typeNum.add(numHypertrophy);
                typeNum.add(numEndurance);
                typeNum.add(numCalisthenic);
                typeNum.add(numConditioning);
                typeNum.add(numAb);
                if (allZero(typeNum)) {
                    typeNum = new ArrayList<>(Arrays.asList(0,1,0,0,0,0));
                }

                boolean preset = getIntent().getExtras().getBoolean("preset");

                Intent equipmentSelectionIntent = new Intent(getApplicationContext(), EquipmentSelectionActivity.class);
                equipmentSelectionIntent.putExtra("typeNum", typeNum);
                equipmentSelectionIntent.putExtra("preset", preset);
                equipmentSelectionIntent.putExtra("exercise", false);
                equipmentSelectionIntent.putExtra("location", false);
                startActivity(equipmentSelectionIntent);
            }
        });
    }

    public boolean allZero(ArrayList<Integer> list) {
        for (int i : list) {
            if (i > 0) {
                return false;
            }
        }
        return true;
    }
}