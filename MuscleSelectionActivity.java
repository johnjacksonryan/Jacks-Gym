package com.johnjacksonryan.display.workoutcreation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.johnjacksonryan.R;
import com.johnjacksonryan.display.exercisemenu.ExerciseViewingDisplayActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MuscleSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muscle_selection);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        AppCompatTextView title = (AppCompatTextView) findViewById(R.id.tvTitle);
        title.setText("Muscles");

        if (getIntent().getExtras().getBoolean("exercise")) {
            TextView selectMuscles = (TextView) findViewById(R.id.selectMuscles);
            String label = "What muscles does your exercise target?";
            selectMuscles.setText(label);
        }

        if(getIntent().getExtras().getBoolean("view")) {
            TextView selectMuscles = (TextView) findViewById(R.id.selectMuscles);
            String label = "Which muscles do you want to see the exercises for?";
            selectMuscles.setText(label);
        }


        Button attributeBtn = (Button) findViewById(R.id.goToAttributes);
        attributeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Integer> musclesIndex = new ArrayList<>();
                Switch quad = (Switch) findViewById(R.id.quad);
                if(quad.isChecked()) {
                    musclesIndex.add(0);
                }
                Switch glute = (Switch) findViewById(R.id.glute);
                if(glute.isChecked()) {
                    musclesIndex.add(1);
                }
                Switch hamstring = (Switch) findViewById(R.id.hamstring);
                if(hamstring.isChecked()) {
                    musclesIndex.add(2);
                }
                Switch chest = (Switch) findViewById(R.id.chest);
                if(chest.isChecked()) {
                    musclesIndex.add(3);
                }
                Switch back = (Switch) findViewById(R.id.back);
                if(back.isChecked()) {
                    musclesIndex.add(4);
                }
                Switch lat = (Switch) findViewById(R.id.lat);
                if(lat.isChecked()) {
                    musclesIndex.add(5);
                }
                Switch trap = (Switch) findViewById(R.id.trap);
                if(trap.isChecked()) {
                    musclesIndex.add(6);
                }
                Switch shoulder = (Switch) findViewById(R.id.shoulder);
                if(shoulder.isChecked()) {
                    musclesIndex.add(7);
                }
                Switch latDelt = (Switch) findViewById(R.id.latDelt);
                if(latDelt.isChecked()) {
                    musclesIndex.add(8);
                }
                Switch antDelt = (Switch) findViewById(R.id.frontDelt);
                if(antDelt.isChecked()) {
                    musclesIndex.add(9);
                }
                Switch postDelt = (Switch) findViewById(R.id.rearDelt);
                if(postDelt.isChecked()) {
                    musclesIndex.add(10);
                }
                Switch bicep = (Switch) findViewById(R.id.bicep);
                if(bicep.isChecked()) {
                    musclesIndex.add(11);
                }
                Switch tricep = (Switch) findViewById(R.id.tricep);
                if(tricep.isChecked()) {
                    musclesIndex.add(12);
                }
                Switch forearm = (Switch) findViewById(R.id.forearm);
                if(forearm.isChecked()) {
                    musclesIndex.add(13);
                }
                Switch upperBack = (Switch) findViewById(R.id.upperBack);
                if(upperBack.isChecked()) {
                    musclesIndex.add(14);
                }
                Switch rhomboid = (Switch) findViewById(R.id.rhomboid);
                if(rhomboid.isChecked()) {
                    musclesIndex.add(15);
                }
                Switch rc = (Switch) findViewById(R.id.RC);
                if(rc.isChecked()) {
                    musclesIndex.add(16);
                }
                Switch lowBack = (Switch) findViewById(R.id.lowBack);
                if(lowBack.isChecked()) {
                    musclesIndex.add(17);
                }
                Switch adductor = (Switch) findViewById(R.id.adductor);
                if(adductor.isChecked()) {
                    musclesIndex.add(18);
                }
                Switch abductor = (Switch) findViewById(R.id.abductor);
                if(abductor.isChecked()) {
                    musclesIndex.add(19);
                }
                Switch calves = (Switch) findViewById(R.id.calves);
                if(calves.isChecked()) {
                    musclesIndex.add(20);
                }
                Switch ab = (Switch) findViewById(R.id.ab);
                if(ab.isChecked()) {
                    musclesIndex.add(21);
                }
                Switch upperAb = (Switch) findViewById(R.id.upperAb);
                if(upperAb.isChecked()) {
                    musclesIndex.add(22);
                }
                Switch lowerAb = (Switch) findViewById(R.id.lowerAb);
                if(lowerAb.isChecked()) {
                    musclesIndex.add(23);
                }
                Switch midAb = (Switch) findViewById(R.id.midAb);
                if(midAb.isChecked()) {
                    musclesIndex.add(24);
                }
                Switch oblique = (Switch) findViewById(R.id.oblique);
                if(oblique.isChecked()) {
                    musclesIndex.add(25);
                }
                Switch serratus = (Switch) findViewById(R.id.serratus);
                if(serratus.isChecked()) {
                    musclesIndex.add(26);
                }
                Switch heart = (Switch) findViewById(R.id.heart);
                if(heart.isChecked()) {
                    musclesIndex.add(27);
                }
                if (musclesIndex.isEmpty()) {
                    musclesIndex.addAll(new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,
                            13,14,15,16,17,18,19,20,21,22,23,24,25,26,27)));
                }
                boolean exercise = getIntent().getExtras().getBoolean("exercise");
                if (!exercise) {
                    Intent attributeSelectionIntent = new Intent(getApplicationContext(), AttributeSelectionActivity.class);
                    attributeSelectionIntent.putExtra("muscles", musclesIndex);
                    ArrayList<Integer> typeNum = getIntent().getExtras().getIntegerArrayList("typeNum");
                    attributeSelectionIntent.putExtra("typeNum", typeNum);
                    ArrayList<Integer> equipment = getIntent().getExtras().getIntegerArrayList("equipment");
                    attributeSelectionIntent.putExtra("equipment", equipment);
                    startActivity(attributeSelectionIntent);
                }
                else {
                    boolean viewing = getIntent().getExtras().getBoolean("view");
                    if (!viewing) {
                        Intent equipmentSelectionIntent = new Intent(getApplicationContext(), EquipmentSelectionActivity.class);
                        String name = getIntent().getExtras().getString("name");
                        String notes = getIntent().getExtras().getString("notes");
                        int duration = getIntent().getExtras().getInt("duration");
                        int reps = getIntent().getExtras().getInt("reps");
                        int sets = getIntent().getExtras().getInt("sets");
                        int type = getIntent().getExtras().getInt("type");
                        boolean superSet = getIntent().getExtras().getBoolean("SS");
                        boolean dropSet = getIntent().getExtras().getBoolean("DS");
                        boolean specialRep = getIntent().getExtras().getBoolean("SR");
                        ArrayList<Integer> difficulty = getIntent().getExtras().getIntegerArrayList("difficulty");

                        equipmentSelectionIntent.putExtra("name", name);
                        equipmentSelectionIntent.putExtra("duration", duration);
                        equipmentSelectionIntent.putExtra("reps", reps);
                        equipmentSelectionIntent.putExtra("sets", sets);
                        equipmentSelectionIntent.putExtra("difficulty", difficulty);
                        equipmentSelectionIntent.putExtra("notes", notes);
                        equipmentSelectionIntent.putExtra("SS", superSet);
                        equipmentSelectionIntent.putExtra("DS", dropSet);
                        equipmentSelectionIntent.putExtra("SR", specialRep);
                        equipmentSelectionIntent.putExtra("type", type);
                        equipmentSelectionIntent.putExtra("exercise", true);
                        equipmentSelectionIntent.putExtra("muscles", musclesIndex);
                        equipmentSelectionIntent.putExtra("location", false);

                        startActivity(equipmentSelectionIntent);
                    }
                    else {
                        int t = getIntent().getExtras().getInt("type");
                        Intent viewListExercisesIntent = new Intent(getApplicationContext(), ExerciseViewingDisplayActivity.class);
                        viewListExercisesIntent.putExtra("type", t);
                        viewListExercisesIntent.putExtra("muscles", musclesIndex);
                        startActivity(viewListExercisesIntent);
                    }

                }
            }
        });
    }
}