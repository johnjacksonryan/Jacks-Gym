package com.johnjacksonryan.display.workoutcreation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.johnjacksonryan.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class AttributePresetSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attribute_preset_selection);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        AppCompatTextView title = (AppCompatTextView) findViewById(R.id.tvTitle);
        title.setText("Attributes");

        String presetType = getIntent().getExtras().getString("presetType");

        Button generateWorkoutBtn = (Button) findViewById(R.id.createWorkoutPresetBtn);
        generateWorkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<Integer> equipmentIndex = getIntent().getExtras().getIntegerArrayList("equipment");

                boolean superSet = false;
                Switch superSetSwitch = (Switch) findViewById(R.id.superSet);
                if (superSetSwitch.isChecked()) {superSet = true;}
                boolean dropSet = false;
                Switch dropSetSwitch = (Switch) findViewById(R.id.dropSet);
                if (dropSetSwitch.isChecked()) {dropSet = true;}
                boolean specialRep = false;
                Switch specialRepSwitch = (Switch) findViewById(R.id.specialRep);
                if(specialRepSwitch.isChecked()) {specialRep = true;}

                int t = 1;
                Switch strengthPresetSwitch = (Switch) findViewById(R.id.strengthPresetSwitch);
                Switch hypertrophyPresetSwitch = (Switch) findViewById(R.id.hypertrophyPresetSwitch);
                Switch calisthenicPresetSwitch = (Switch) findViewById(R.id.calisthenicPresetSwitch);
                if (strengthPresetSwitch.isChecked()) {t = 0;}
                if (calisthenicPresetSwitch.isChecked()) {t = 2;}
                if (hypertrophyPresetSwitch.isChecked()) {t = 1;}

                if (t == 0) {
                    specialRep = false;
                    superSet = false;
                    dropSet = false;
                }

                if (t == 2) {
                    dropSet = false;
                }


                int difficulty = 1;
                Switch beginnerSwitch = (Switch) findViewById(R.id.beginner);
                if (beginnerSwitch.isChecked()) {difficulty = 0;}
                Switch intermediateSwitch = (Switch) findViewById(R.id.intermediate);
                if (intermediateSwitch.isChecked()) {difficulty = 1;}
                Switch advancedSwitch = (Switch) findViewById(R.id.advanced);
                if (advancedSwitch.isChecked()) {difficulty = 2;}

                if (presetType.equals("push")) {
                    ArrayList<Integer> muscleList = new ArrayList<>(Arrays.asList(3, 3, 7, 8, 10, 12, 12));
                    ArrayList<Integer> musclesIndex = randomizeList(muscleList);
                    int numSuperSet = 0;
                    int numDropSet = 0;
                    int numSpecialRep = 0;
                    if (superSet) {numSuperSet = getRandom(new ArrayList<>(Arrays.asList(1, 1, 2)));}
                    if (dropSet) {numDropSet = getRandom(new ArrayList<>(Arrays.asList(1, 1, 2)));}
                    if (specialRep) {numSpecialRep = getRandom(new ArrayList<>(Arrays.asList(1, 1, 2)));}
                    ArrayList<Integer> typeNum = new ArrayList<>(Arrays.asList(0, 6, 0, 0, 0, 0));
                    if (t == 0) { typeNum = new ArrayList<>(Arrays.asList(4, 2, 0, 0, 0, 0));}
                    if (t == 2) { typeNum = new ArrayList<>(Arrays.asList(0, 0, 0, 6, 0, 0));}

                    Intent workoutDisplayIntent = new Intent(getApplicationContext(), WorkoutDisplayActivity.class);
                    workoutDisplayIntent.putExtra("typeNum", typeNum);
                    workoutDisplayIntent.putExtra("equipment", equipmentIndex);
                    workoutDisplayIntent.putExtra("muscles", musclesIndex);
                    workoutDisplayIntent.putExtra("difficulty", difficulty);
                    workoutDisplayIntent.putExtra("SS", numSuperSet);
                    workoutDisplayIntent.putExtra("DS", numDropSet);
                    workoutDisplayIntent.putExtra("SR", numSpecialRep);
                    workoutDisplayIntent.putExtra("id", -1);
                    startActivity(workoutDisplayIntent);
                }

                if (presetType.equals("pull")) {
                    ArrayList<Integer> muscleList = new ArrayList<>(Arrays.asList(4, 5, 6, 11, 11, 14, 15));
                    ArrayList<Integer> musclesIndex = randomizeList(muscleList);
                    int numSuperSet = 0;
                    int numDropSet = 0;
                    int numSpecialRep = 0;
                    if (superSet) {numSuperSet = getRandom(new ArrayList<>(Arrays.asList(1, 1, 2)));}
                    if (dropSet) {numDropSet = getRandom(new ArrayList<>(Arrays.asList(1, 1, 2)));}
                    if (specialRep) {numSpecialRep = getRandom(new ArrayList<>(Arrays.asList(1, 1, 2)));}
                    ArrayList<Integer> typeNum = new ArrayList<>(Arrays.asList(0, 6, 0, 0, 0, 0));
                    if (t == 0) { typeNum = new ArrayList<>(Arrays.asList(4, 2, 0, 0, 0, 0));}
                    if (t == 2) { typeNum = new ArrayList<>(Arrays.asList(0, 0, 0, 6, 0, 0));}
                    Intent workoutDisplayIntent = new Intent(getApplicationContext(), WorkoutDisplayActivity.class);
                    workoutDisplayIntent.putExtra("typeNum", typeNum);
                    workoutDisplayIntent.putExtra("equipment", equipmentIndex);
                    workoutDisplayIntent.putExtra("muscles", musclesIndex);
                    workoutDisplayIntent.putExtra("difficulty", difficulty);
                    workoutDisplayIntent.putExtra("SS", numSuperSet);
                    workoutDisplayIntent.putExtra("DS", numDropSet);
                    workoutDisplayIntent.putExtra("SR", numSpecialRep);
                    workoutDisplayIntent.putExtra("id", -1);
                    startActivity(workoutDisplayIntent);
                }

                if (presetType.equals("legs")) {
                    ArrayList<Integer> muscleList = new ArrayList<>(Arrays.asList(0, 1, 2, 18, 19, 20));
                    ArrayList<Integer> musclesIndex = randomizeList(muscleList);
                    int numSuperSet = 0;
                    int numDropSet = 0;
                    int numSpecialRep = 0;
                    if (superSet) {numSuperSet = getRandom(new ArrayList<>(Arrays.asList(1, 1, 2)));}
                    if (dropSet) {numDropSet = getRandom(new ArrayList<>(Arrays.asList(1, 1, 2)));}
                    if (specialRep) {numSpecialRep = getRandom(new ArrayList<>(Arrays.asList(1, 1, 2)));}
                    ArrayList<Integer> typeNum = new ArrayList<>(Arrays.asList(0, 6, 0, 0, 0, 0));
                    if (t == 0) { typeNum = new ArrayList<>(Arrays.asList(4, 2, 0, 0, 0, 0));}
                    if (t == 2) { typeNum = new ArrayList<>(Arrays.asList(0, 0, 0, 6, 0, 0));}
                    Intent workoutDisplayIntent = new Intent(getApplicationContext(), WorkoutDisplayActivity.class);
                    workoutDisplayIntent.putExtra("typeNum", typeNum);
                    workoutDisplayIntent.putExtra("equipment", equipmentIndex);
                    workoutDisplayIntent.putExtra("muscles", musclesIndex);
                    workoutDisplayIntent.putExtra("difficulty", difficulty);
                    workoutDisplayIntent.putExtra("SS", numSuperSet);
                    workoutDisplayIntent.putExtra("DS", numDropSet);
                    workoutDisplayIntent.putExtra("SR", numSpecialRep);
                    workoutDisplayIntent.putExtra("id", -1);
                    startActivity(workoutDisplayIntent);
                }


                if (presetType.equals("upper")) {
                    ArrayList<Integer> muscleList = new ArrayList<>(Arrays.asList(3, 4, 7, 11, 12, 15, 8));
                    ArrayList<Integer> musclesIndex = randomizeList(muscleList);
                    int numSuperSet = 0;
                    int numDropSet = 0;
                    int numSpecialRep = 0;
                    if (superSet) {numSuperSet = getRandom(new ArrayList<>(Arrays.asList(1, 1, 2)));}
                    if (dropSet) {numDropSet = getRandom(new ArrayList<>(Arrays.asList(1, 1, 2)));}
                    if (specialRep) {numSpecialRep = getRandom(new ArrayList<>(Arrays.asList(1, 1, 2)));}
                    ArrayList<Integer> typeNum = new ArrayList<>(Arrays.asList(0, 7, 0, 0, 0, 0));
                    if (t == 0) { typeNum = new ArrayList<>(Arrays.asList(4, 2, 0, 0, 0, 0));}
                    if (t == 2) { typeNum = new ArrayList<>(Arrays.asList(0, 0, 0, 7, 0, 0));}
                    Intent workoutDisplayIntent = new Intent(getApplicationContext(), WorkoutDisplayActivity.class);
                    workoutDisplayIntent.putExtra("typeNum", typeNum);
                    workoutDisplayIntent.putExtra("equipment", equipmentIndex);
                    workoutDisplayIntent.putExtra("muscles", musclesIndex);
                    workoutDisplayIntent.putExtra("difficulty", difficulty);
                    workoutDisplayIntent.putExtra("SS", numSuperSet);
                    workoutDisplayIntent.putExtra("DS", numDropSet);
                    workoutDisplayIntent.putExtra("SR", numSpecialRep);
                    workoutDisplayIntent.putExtra("id", -1);
                    startActivity(workoutDisplayIntent);
                }


                if (presetType.equals("total")) {
                    ArrayList<Integer> legsList = new ArrayList<>(Arrays.asList(0, 0, 1, 2, 18, 19, 20));
                    ArrayList<Integer> pushList = new ArrayList<>(Arrays.asList(3, 7, 8));
                    ArrayList<Integer> pullList = new ArrayList<>(Arrays.asList(4, 5, 6, 10));
                    ArrayList<Integer> legsIndex = randomizeList(legsList);
                    ArrayList<Integer> pushIndex = randomizeList(pushList);
                    ArrayList<Integer> pullIndex = randomizeList(pullList);
                    ArrayList<Integer> mIndex = new ArrayList<>();
                    mIndex.add(legsIndex.get(0));
                    mIndex.add(legsIndex.get(1));
                    mIndex.add(pushIndex.get(0));
                    mIndex.add(pushIndex.get(1));
                    mIndex.add(pullIndex.get(0));
                    mIndex.add(pullIndex.get(1));
                    mIndex.add(12);
                    mIndex.add(11);
                    ArrayList<Integer> musclesIndex = randomizeList(mIndex);
                    int numSuperSet = 0;
                    int numDropSet = 0;
                    int numSpecialRep = 0;
                    if (superSet) {numSuperSet = getRandom(new ArrayList<>(Arrays.asList(1, 1, 2)));}
                    if (dropSet) {numDropSet = getRandom(new ArrayList<>(Arrays.asList(1, 1, 2)));}
                    if (specialRep) {numSpecialRep = getRandom(new ArrayList<>(Arrays.asList(1, 1, 2)));}
                    ArrayList<Integer> typeNum = new ArrayList<>(Arrays.asList(0, 8, 0, 0, 0, 0));
                    if (t == 0) { typeNum = new ArrayList<>(Arrays.asList(4, 3, 0, 0, 0, 0));}
                    if (t == 2) { typeNum = new ArrayList<>(Arrays.asList(0, 0, 0, 8, 0, 0));}
                    Intent workoutDisplayIntent = new Intent(getApplicationContext(), WorkoutDisplayActivity.class);
                    workoutDisplayIntent.putExtra("typeNum", typeNum);
                    workoutDisplayIntent.putExtra("equipment", equipmentIndex);
                    workoutDisplayIntent.putExtra("muscles", musclesIndex);
                    workoutDisplayIntent.putExtra("difficulty", difficulty);
                    workoutDisplayIntent.putExtra("SS", numSuperSet);
                    workoutDisplayIntent.putExtra("DS", numDropSet);
                    workoutDisplayIntent.putExtra("SR", numSpecialRep);
                    workoutDisplayIntent.putExtra("id", -1);
                    startActivity(workoutDisplayIntent);
                }

                if (presetType.equals("conditioning")) {
                    ArrayList<Integer> muscleList = new ArrayList<>(Arrays.asList(27));
                    ArrayList<Integer> musclesIndex = randomizeList(muscleList);
                    Intent workoutDisplayIntent = new Intent(getApplicationContext(), WorkoutDisplayActivity.class);
                    int numSuperSet = 0;
                    int numDropSet = 0;
                    int numSpecialRep = 0;
                    ArrayList<Integer> typeNum = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 6, 0));
                    workoutDisplayIntent.putExtra("typeNum", typeNum);
                    workoutDisplayIntent.putExtra("equipment", equipmentIndex);
                    workoutDisplayIntent.putExtra("muscles", musclesIndex);
                    workoutDisplayIntent.putExtra("difficulty", difficulty);
                    workoutDisplayIntent.putExtra("SS", numSuperSet);
                    workoutDisplayIntent.putExtra("DS", numDropSet);
                    workoutDisplayIntent.putExtra("SR", numSpecialRep);
                    workoutDisplayIntent.putExtra("id", -1);
                    startActivity(workoutDisplayIntent);
                }

                if (presetType.equals("ab")) {
                    ArrayList<Integer> muscleList = new ArrayList<>(Arrays.asList(21, 22, 23, 24, 25));
                    ArrayList<Integer> musclesIndex = randomizeList(muscleList);
                    int numSuperSet = 0;
                    int numDropSet = 0;
                    int numSpecialRep = 0;
                    ArrayList<Integer> typeNum = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 4));
                    Intent workoutDisplayIntent = new Intent(getApplicationContext(), WorkoutDisplayActivity.class);
                    workoutDisplayIntent.putExtra("typeNum", typeNum);
                    workoutDisplayIntent.putExtra("equipment", equipmentIndex);
                    workoutDisplayIntent.putExtra("muscles", musclesIndex);
                    workoutDisplayIntent.putExtra("difficulty", difficulty);
                    workoutDisplayIntent.putExtra("SS", numSuperSet);
                    workoutDisplayIntent.putExtra("DS", numDropSet);
                    workoutDisplayIntent.putExtra("SR", numSpecialRep);
                    workoutDisplayIntent.putExtra("id", -1);
                    startActivity(workoutDisplayIntent);
                }

                if (presetType.equals("chest")) {
                    ArrayList<Integer> muscleList = new ArrayList<>(Arrays.asList(3, 3, 3, 12, 12));
                    ArrayList<Integer> musclesIndex = randomizeList(muscleList);
                    int numSuperSet = 0;
                    int numDropSet = 0;
                    int numSpecialRep = 0;
                    if (superSet) {numSuperSet = getRandom(new ArrayList<>(Arrays.asList(1, 1, 2)));}
                    if (dropSet) {numDropSet = getRandom(new ArrayList<>(Arrays.asList(1, 1, 2)));}
                    if (specialRep) {numSpecialRep = getRandom(new ArrayList<>(Arrays.asList(1, 1, 2)));}
                    ArrayList<Integer> typeNum = new ArrayList<>(Arrays.asList(0, 5, 0, 0, 0, 0));
                    if (t == 0) { typeNum = new ArrayList<>(Arrays.asList(2, 3, 0, 0, 0, 0));}
                    if (t == 2) { typeNum = new ArrayList<>(Arrays.asList(0, 0, 0, 5, 0, 0));}
                    Intent workoutDisplayIntent = new Intent(getApplicationContext(), WorkoutDisplayActivity.class);
                    workoutDisplayIntent.putExtra("typeNum", typeNum);
                    workoutDisplayIntent.putExtra("equipment", equipmentIndex);
                    workoutDisplayIntent.putExtra("muscles", musclesIndex);
                    workoutDisplayIntent.putExtra("difficulty", difficulty);
                    workoutDisplayIntent.putExtra("SS", numSuperSet);
                    workoutDisplayIntent.putExtra("DS", numDropSet);
                    workoutDisplayIntent.putExtra("SR", numSpecialRep);
                    workoutDisplayIntent.putExtra("id", -1);
                    startActivity(workoutDisplayIntent);
                }

                if (presetType.equals("back")) {
                    ArrayList<Integer> muscleList = new ArrayList<>(Arrays.asList(4, 5, 6, 14, 15));
                    ArrayList<Integer> musclesIndex = randomizeList(muscleList);
                    int numSuperSet = 0;
                    int numDropSet = 0;
                    int numSpecialRep = 0;
                    if (superSet) {numSuperSet = getRandom(new ArrayList<>(Arrays.asList(1, 1, 2)));}
                    if (dropSet) {numDropSet = getRandom(new ArrayList<>(Arrays.asList(1, 1, 2)));}
                    if (specialRep) {numSpecialRep = getRandom(new ArrayList<>(Arrays.asList(1, 1, 2)));}
                    ArrayList<Integer> typeNum = new ArrayList<>(Arrays.asList(0, 5, 0, 0, 0, 0));
                    if (t == 0) { typeNum = new ArrayList<>(Arrays.asList(3, 2, 0, 0, 0, 0));}
                    if (t == 2) { typeNum = new ArrayList<>(Arrays.asList(0, 0, 0, 5, 0, 0));}
                    Intent workoutDisplayIntent = new Intent(getApplicationContext(), WorkoutDisplayActivity.class);
                    workoutDisplayIntent.putExtra("typeNum", typeNum);
                    workoutDisplayIntent.putExtra("equipment", equipmentIndex);
                    workoutDisplayIntent.putExtra("muscles", musclesIndex);
                    workoutDisplayIntent.putExtra("difficulty", difficulty);
                    workoutDisplayIntent.putExtra("SS", numSuperSet);
                    workoutDisplayIntent.putExtra("DS", numDropSet);
                    workoutDisplayIntent.putExtra("SR", numSpecialRep);
                    workoutDisplayIntent.putExtra("id", -1);
                    startActivity(workoutDisplayIntent);
                }

                if (presetType.equals("arms")) {
                    ArrayList<Integer> muscleList = new ArrayList<>(Arrays.asList(7, 8, 11, 11, 12, 12));
                    ArrayList<Integer> musclesIndex = randomizeList(muscleList);
                    int numSuperSet = 0;
                    int numDropSet = 0;
                    int numSpecialRep = 0;
                    if (superSet) {numSuperSet = getRandom(new ArrayList<>(Arrays.asList(1, 1, 2)));}
                    if (dropSet) {numDropSet = getRandom(new ArrayList<>(Arrays.asList(1, 1, 2)));}
                    if (specialRep) {numSpecialRep = getRandom(new ArrayList<>(Arrays.asList(1, 1, 2)));}
                    ArrayList<Integer> typeNum = new ArrayList<>(Arrays.asList(0, 6, 0, 0, 0, 0));
                    if (t == 0) { typeNum = new ArrayList<>(Arrays.asList(4, 2, 0, 0, 0, 0));}
                    if (t == 2) { typeNum = new ArrayList<>(Arrays.asList(0, 0, 0, 6, 0, 0));}
                    Intent workoutDisplayIntent = new Intent(getApplicationContext(), WorkoutDisplayActivity.class);
                    workoutDisplayIntent.putExtra("typeNum", typeNum);
                    workoutDisplayIntent.putExtra("equipment", equipmentIndex);
                    workoutDisplayIntent.putExtra("muscles", musclesIndex);
                    workoutDisplayIntent.putExtra("difficulty", difficulty);
                    workoutDisplayIntent.putExtra("SS", numSuperSet);
                    workoutDisplayIntent.putExtra("DS", numDropSet);
                    workoutDisplayIntent.putExtra("SR", numSpecialRep);
                    workoutDisplayIntent.putExtra("id", -1);
                    startActivity(workoutDisplayIntent);
                }
            }
        });



    }

    /**
     * Picks a random integer from list
     */
    public Integer getRandom(ArrayList<Integer> list) {
        int rnd = new Random().nextInt(list.size());
        return list.get(rnd);
    }

    /**
     * Randomizes the list of int
     * @param muscles the list of muscle ints
     * @return the randomized list
     */
    public ArrayList<Integer> randomizeList(ArrayList<Integer> muscles) {
        ArrayList<Integer> randomized = new ArrayList<>();
        while (!muscles.isEmpty()) {
            int i = getRandom(muscles);
            randomized.add(i);
            muscles = removeItem(i, muscles);
        }
        return randomized;
    }

    /**
     * Removes the item from the list
     * @param i the item to be removed
     * @param muscles the list of ints
     * @return the list without i
     */
    public ArrayList<Integer> removeItem(int i, ArrayList<Integer> muscles) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int j : muscles) {
            if (i != j) {
                list.add(j);
            }
        }
        return list;
    }
}