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
import com.johnjacksonryan.display.exercisemenu.ExerciseDisplayActivity;
import com.johnjacksonryan.display.locationmenu.LocationDisplayActivity;
import com.johnjacksonryan.display.locationmenu.LocationListDisplayActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class EquipmentSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_selection);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        AppCompatTextView title = (AppCompatTextView) findViewById(R.id.tvTitle);
        title.setText("Equipment");

        boolean preset = getIntent().getExtras().getBoolean("preset");
        boolean exercise = getIntent().getExtras().getBoolean("exercise");
        boolean location = getIntent().getExtras().getBoolean("location");

        if (location) {
            TextView selectEquipment = (TextView) findViewById(R.id.selectEquipment);
            String label = "What equipment does your location have?";
            selectEquipment.setText(label);
        }

        if (exercise) {
            TextView selectEquipment = (TextView) findViewById(R.id.selectEquipment);
            String label = "What equipment does your exercise require?";
            selectEquipment.setText(label);
        }

        Button fullGymBtn = (Button) findViewById(R.id.fullGymBtn);
        fullGymBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (location) {
                    Intent locationDisplayIntent = new Intent(getApplicationContext(), LocationDisplayActivity.class);
                    String name = getIntent().getExtras().getString("name");
                    locationDisplayIntent.putExtra("name", name);
                    locationDisplayIntent.putExtra("equipment", new ArrayList<>(Arrays.asList(0)));
                    locationDisplayIntent.putExtra("new", true);
                    startActivity(locationDisplayIntent);
                }
                else if (!exercise) {
                    if (!preset) {
                        Intent muscleSelectionIntent = new Intent(getApplicationContext(), MuscleSelectionActivity.class);
                        muscleSelectionIntent.putExtra("equipment", new ArrayList<>(Arrays.asList(0)));
                        ArrayList<Integer> typeNum = getIntent().getExtras().getIntegerArrayList("typeNum");
                        muscleSelectionIntent.putExtra("typeNum", typeNum);
                        muscleSelectionIntent.putExtra("exercise", false);
                        startActivity(muscleSelectionIntent);
                    } else {
                        Intent presetSelectionIntent = new Intent(getApplicationContext(), PresetSelectionActivity.class);
                        presetSelectionIntent.putExtra("equipment", new ArrayList<>(Arrays.asList(0)));
                        ;
                        startActivity(presetSelectionIntent);
                    }
                }
                else {
                    Intent exerciseDisplayIntent = new Intent(getApplicationContext(), ExerciseDisplayActivity.class);
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
                    ArrayList<Integer> musclesIndex = getIntent().getExtras().getIntegerArrayList("muscles");

                    exerciseDisplayIntent.putExtra("name", name);
                    exerciseDisplayIntent.putExtra("duration", duration);
                    exerciseDisplayIntent.putExtra("reps", reps);
                    exerciseDisplayIntent.putExtra("sets", sets);
                    exerciseDisplayIntent.putExtra("difficulty", difficulty);
                    exerciseDisplayIntent.putExtra("notes", notes);
                    exerciseDisplayIntent.putExtra("SS", superSet);
                    exerciseDisplayIntent.putExtra("DS", dropSet);
                    exerciseDisplayIntent.putExtra("SR", specialRep);
                    exerciseDisplayIntent.putExtra("type", type);
                    exerciseDisplayIntent.putExtra("exercise", true);
                    exerciseDisplayIntent.putExtra("muscles", musclesIndex);
                    exerciseDisplayIntent.putExtra("equipment", new ArrayList<>(Arrays.asList(0)));
                    startActivity(exerciseDisplayIntent);
                }
            }
        });

        Button musclesBtn = (Button) findViewById(R.id.goToMuscles);
        musclesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Integer> equipmentIndex = new ArrayList<>();
                Switch dumbbell = (Switch) findViewById(R.id.dumbbell);
                if(dumbbell.isChecked()) {
                    equipmentIndex.add(1);
                }
                Switch barbell = (Switch) findViewById(R.id.barbell);
                if(barbell.isChecked()) {
                    equipmentIndex.add(2);
                }
                Switch squatRack = (Switch) findViewById(R.id.squat);
                if(squatRack.isChecked()) {
                    equipmentIndex.add(3);
                }
                Switch benchPress = (Switch) findViewById(R.id.benchPress);
                if(benchPress.isChecked()) {
                    equipmentIndex.add(4);
                }
                Switch band = (Switch) findViewById(R.id.resistance);
                if(band.isChecked()) {
                    equipmentIndex.add(5);
                }
                Switch pullUp = (Switch) findViewById(R.id.pullup);
                if(pullUp.isChecked()) {
                    equipmentIndex.add(6);
                }
                Switch dip = (Switch) findViewById(R.id.dip);
                if(dip.isChecked()) {
                    equipmentIndex.add(7);
                }
                Switch bench = (Switch) findViewById(R.id.bench);
                if(bench.isChecked()) {
                    equipmentIndex.add(8);
                }
                Switch adjBench = (Switch) findViewById(R.id.adjustable);
                if(adjBench.isChecked()) {
                    equipmentIndex.add(9);
                }
                Switch cable = (Switch) findViewById(R.id.cable);
                if(cable.isChecked()) {
                    equipmentIndex.add(10);
                }
                Switch latPulldown = (Switch) findViewById(R.id.latPulldown);
                if(latPulldown.isChecked()) {
                    equipmentIndex.add(11);
                }
                Switch legPress = (Switch) findViewById(R.id.legPress);
                if(legPress.isChecked()) {
                    equipmentIndex.add(12);
                }
                Switch legExtension = (Switch) findViewById(R.id.extension);
                if(legExtension.isChecked()) {
                    equipmentIndex.add(13);
                }
                Switch legCurl = (Switch) findViewById(R.id.curl);
                if(legCurl.isChecked()) {
                    equipmentIndex.add(14);
                }
                Switch adduction = (Switch) findViewById(R.id.adduction);
                if(adduction.isChecked()) {
                    equipmentIndex.add(15);
                }
                Switch abduction = (Switch) findViewById(R.id.abduction);
                if(abduction.isChecked()) {
                    equipmentIndex.add(16);
                }
                Switch calfRaise = (Switch) findViewById(R.id.calfRaise);
                if(calfRaise.isChecked()) {
                    equipmentIndex.add(17);
                }
                Switch pecFly = (Switch) findViewById(R.id.pecFly);
                if(pecFly.isChecked()) {
                    equipmentIndex.add(18);
                }
                Switch chestPress = (Switch) findViewById(R.id.chestPress);
                if(chestPress.isChecked()) {
                    equipmentIndex.add(19);
                }
                Switch gluteKickback = (Switch) findViewById(R.id.kickback);
                if(gluteKickback.isChecked()) {
                    equipmentIndex.add(20);
                }
                Switch hamRaise = (Switch) findViewById(R.id.hamRaise);
                if(hamRaise.isChecked()) {
                    equipmentIndex.add(22);
                }
                Switch preacher = (Switch) findViewById(R.id.preacher);
                if(preacher.isChecked()) {
                    equipmentIndex.add(23);
                }
                Switch floor = (Switch) findViewById(R.id.floor);
                if(floor.isChecked()) {
                    equipmentIndex.add(24);
                }
                Switch kettlebell = (Switch) findViewById(R.id.kettlebell);
                if(kettlebell.isChecked()) {
                    equipmentIndex.add(25);
                }
                Switch plates = (Switch) findViewById(R.id.plates);
                if(plates.isChecked()) {
                    equipmentIndex.add(26);
                }
                Switch medicine = (Switch) findViewById(R.id.medicine);
                if(medicine.isChecked()) {
                    equipmentIndex.add(27);
                }
                Switch box = (Switch) findViewById(R.id.box);
                if(box.isChecked()) {
                    equipmentIndex.add(28);
                }
                Switch treadmill = (Switch) findViewById(R.id.treadmill);
                if(treadmill.isChecked()) {
                    equipmentIndex.add(29);
                }
                Switch bike = (Switch) findViewById(R.id.bike);
                if(bike.isChecked()) {
                    equipmentIndex.add(30);
                }
                Switch rowing = (Switch) findViewById(R.id.rowing);
                if(rowing.isChecked()) {
                    equipmentIndex.add(31);
                }
                Switch elliptical = (Switch) findViewById(R.id.elliptical);
                if(elliptical.isChecked()) {
                    equipmentIndex.add(32);
                }
                Switch stairMaster = (Switch) findViewById(R.id.stair);
                if(stairMaster.isChecked()) {
                    equipmentIndex.add(33);
                }
                Switch pool = (Switch) findViewById(R.id.pool);
                if(pool.isChecked()) {
                    equipmentIndex.add(34);
                }
                Switch jumpRope = (Switch) findViewById(R.id.jumpRope);
                if(jumpRope.isChecked()) {
                    equipmentIndex.add(35);
                }
                Switch bag = (Switch) findViewById(R.id.bag);
                if(bag.isChecked()) {
                    equipmentIndex.add(36);
                }
                Switch track = (Switch) findViewById(R.id.track);
                if(track.isChecked()) {
                    equipmentIndex.add(37);
                }
                Switch jammer = (Switch) findViewById(R.id.jammer);
                if(jammer.isChecked()) {
                    equipmentIndex.add(38);
                }
                Switch sled = (Switch) findViewById(R.id.sled);
                if(sled.isChecked()) {
                    equipmentIndex.add(39);
                }
                Switch ball = (Switch) findViewById(R.id.ball);
                if(ball.isChecked()) {
                    equipmentIndex.add(40);
                }
                Switch landmine = (Switch) findViewById(R.id.landmine);
                if(landmine.isChecked()) {
                    equipmentIndex.add(41);
                }
                Switch ezBar = (Switch) findViewById(R.id.ezbar);
                if(ezBar.isChecked()) {
                    equipmentIndex.add(42);
                }
                if (!equipmentIndex.contains(24) && !exercise) {
                    equipmentIndex.add(24);
                }
                if (location) {
                    Intent locationDisplayIntent = new Intent(getApplicationContext(), LocationDisplayActivity.class);
                    String name = getIntent().getExtras().getString("name");
                    locationDisplayIntent.putExtra("name", name);
                    locationDisplayIntent.putExtra("equipment", equipmentIndex);
                    locationDisplayIntent.putExtra("new", true);
                    startActivity(locationDisplayIntent);
                }
                else if(!exercise) {
                    if (!preset) {
                        Intent muscleSelectionIntent = new Intent(getApplicationContext(), MuscleSelectionActivity.class);
                        muscleSelectionIntent.putExtra("equipment", equipmentIndex);
                        ArrayList<Integer> typeNum = getIntent().getExtras().getIntegerArrayList("typeNum");
                        muscleSelectionIntent.putExtra("typeNum", typeNum);
                        startActivity(muscleSelectionIntent);
                    } else {
                        Intent presetSelectionIntent = new Intent(getApplicationContext(), PresetSelectionActivity.class);
                        presetSelectionIntent.putExtra("equipment", equipmentIndex);
                        startActivity(presetSelectionIntent);
                    }
                }
                else {
                    Intent exerciseDisplayIntent = new Intent(getApplicationContext(), ExerciseDisplayActivity.class);
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
                    ArrayList<Integer> musclesIndex = getIntent().getExtras().getIntegerArrayList("muscles");
                    equipmentIndex.add(0);

                    exerciseDisplayIntent.putExtra("name", name);
                    exerciseDisplayIntent.putExtra("duration", duration);
                    exerciseDisplayIntent.putExtra("reps", reps);
                    exerciseDisplayIntent.putExtra("sets", sets);
                    exerciseDisplayIntent.putExtra("difficulty", difficulty);
                    exerciseDisplayIntent.putExtra("notes", notes);
                    exerciseDisplayIntent.putExtra("SS", superSet);
                    exerciseDisplayIntent.putExtra("DS", dropSet);
                    exerciseDisplayIntent.putExtra("SR", specialRep);
                    exerciseDisplayIntent.putExtra("type", type);
                    exerciseDisplayIntent.putExtra("exercise", true);
                    exerciseDisplayIntent.putExtra("muscles", musclesIndex);
                    exerciseDisplayIntent.putExtra("equipment", equipmentIndex);
                    startActivity(exerciseDisplayIntent);
                }
            }
        });

        Button myLocationsBtn = (Button) findViewById(R.id.myLocationsBtn);
        myLocationsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (location) {
                }
                else {
                    if(!exercise) {
                        if (!preset) {
                            Intent viewLocationsIntent = new Intent(getApplicationContext(), LocationListDisplayActivity.class);
                            ArrayList<Integer> typeNum = getIntent().getExtras().getIntegerArrayList("typeNum");
                            viewLocationsIntent.putExtra("typeNum", typeNum);
                            viewLocationsIntent.putExtra("viewing", false);
                            viewLocationsIntent.putExtra("preset", false);
                            viewLocationsIntent.putExtra("exercise", false);
                            startActivity(viewLocationsIntent);
                        } else {
                            Intent viewLocationsIntent = new Intent(getApplicationContext(), LocationListDisplayActivity.class);
                            viewLocationsIntent.putExtra("viewing", false);
                            viewLocationsIntent.putExtra("preset", true);
                            viewLocationsIntent.putExtra("exercise", false);
                            startActivity(viewLocationsIntent);
                        }
                    }
                    else {
                        Intent viewLocationsIntent = new Intent(getApplicationContext(), LocationListDisplayActivity.class);
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
                        ArrayList<Integer> musclesIndex = getIntent().getExtras().getIntegerArrayList("muscles");

                        viewLocationsIntent.putExtra("name", name);
                        viewLocationsIntent.putExtra("duration", duration);
                        viewLocationsIntent.putExtra("reps", reps);
                        viewLocationsIntent.putExtra("sets", sets);
                        viewLocationsIntent.putExtra("difficulty", difficulty);
                        viewLocationsIntent.putExtra("notes", notes);
                        viewLocationsIntent.putExtra("SS", superSet);
                        viewLocationsIntent.putExtra("DS", dropSet);
                        viewLocationsIntent.putExtra("SR", specialRep);
                        viewLocationsIntent.putExtra("type", type);
                        viewLocationsIntent.putExtra("muscles", musclesIndex);
                        viewLocationsIntent.putExtra("viewing", false);
                        viewLocationsIntent.putExtra("preset", preset);
                        viewLocationsIntent.putExtra("exercise", true);
                        startActivity(viewLocationsIntent);
                    }
                }
            }
        });

    }
}