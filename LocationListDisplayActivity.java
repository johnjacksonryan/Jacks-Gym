package com.johnjacksonryan.display.locationmenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.johnjacksonryan.R;
import com.johnjacksonryan.adapters.WorkoutAdapter;
import com.johnjacksonryan.display.MainMenuActivity;
import com.johnjacksonryan.display.exercisemenu.ExerciseDisplayActivity;
import com.johnjacksonryan.display.workoutcreation.MuscleSelectionActivity;
import com.johnjacksonryan.display.workoutcreation.PresetSelectionActivity;
import com.johnjacksonryan.exercise.ExerciseManager;
import com.johnjacksonryan.gateway.Updater;
import com.johnjacksonryan.gateway.UserManagerFacade;
import com.johnjacksonryan.location.LocationManager;
import com.johnjacksonryan.workout.WorkoutManager;

import java.util.ArrayList;

public class LocationListDisplayActivity extends AppCompatActivity implements WorkoutAdapter.OnNoteListener {

    RecyclerView workoutDisplay;
    String[] names;
    String[] ids;
    String[] subNames;
    String[] info;
    boolean viewing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list_display);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        AppCompatTextView title = (AppCompatTextView) findViewById(R.id.tvTitle);
        title.setText("Locations");

        Button exitLocationListBtn = (Button) findViewById(R.id.locationListExitBtn);
        exitLocationListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainMenuIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(mainMenuIntent);
            }
        });

        viewing = getIntent().getExtras().getBoolean("viewing");

        ExerciseManager exerciseManager = new ExerciseManager();
        WorkoutManager workoutManager = new WorkoutManager(exerciseManager);
        LocationManager locationManager = new LocationManager();
        Updater updater = new Updater(exerciseManager, workoutManager, locationManager, this);
        UserManagerFacade userManagerFacade = new UserManagerFacade(exerciseManager, workoutManager, updater, locationManager);
        userManagerFacade.loadLocations();

        ArrayList<ArrayList<String>> locationList = userManagerFacade.getLocationList();
        if (locationList.size() == 0 || locationList.get(0).size()!=4) {
            TextView selectToViewOptions = (TextView) findViewById(R.id.selectALocationToViewOptions);
            String label = "Could not find any locations";
            selectToViewOptions.setText(label);
        }
        else {
            names = new String[locationList.size()];
            ids = new String[locationList.size()];
            subNames = new String[locationList.size()];
            info = new String[locationList.size()];
            for (int i = 0; i < locationList.size(); i++) {
                ArrayList<String> location = locationList.get(i);
                names[i] = location.get(0);
                ids[i] = location.get(1);
                subNames[i] = location.get(2);
                info[i] = location.get(3);
            }
            workoutDisplay = (RecyclerView) findViewById(R.id.locationListRecyclerView);
            workoutDisplay.setLayoutManager(new LinearLayoutManager(this));
            workoutDisplay.setHasFixedSize(true);
            WorkoutAdapter workoutAdapter = new WorkoutAdapter(names, ids, subNames, info, this);
            workoutDisplay.setAdapter(workoutAdapter);
        }
    }

    @Override
    public void OnNoteClick(int position, String exerciseInfo) {
        if (viewing) {
            Intent locationViewingIntent = new Intent(this, LocationDisplayActivity.class);
            locationViewingIntent.putExtra("new", false);
            locationViewingIntent.putExtra("name", names[position]);
            locationViewingIntent.putExtra("id", ids[position]);
            startActivity(locationViewingIntent);
        }
        else {
            ExerciseManager exerciseManager = new ExerciseManager();
            WorkoutManager workoutManager = new WorkoutManager(exerciseManager);
            LocationManager locationManager = new LocationManager();
            Updater updater = new Updater(exerciseManager, workoutManager, locationManager, this);
            UserManagerFacade userManagerFacade = new UserManagerFacade(exerciseManager, workoutManager, updater, locationManager);
            userManagerFacade.loadLocations();

            boolean exercise = getIntent().getExtras().getBoolean("exercise");
            boolean preset = getIntent().getExtras().getBoolean("preset");
            String idString = ids[position];
            int id = Integer.parseInt(idString.substring(idString.length()-3, idString.length()).replaceAll("\\s+",""));
            ArrayList<Integer> equipmentIndex = userManagerFacade.getLocationEquipmentIndex(id);

            if (!exercise) {
                if (!preset) {
                    Intent muscleSelectionIntent = new Intent(getApplicationContext(), MuscleSelectionActivity.class);
                    muscleSelectionIntent.putExtra("equipment", equipmentIndex);
                    ArrayList<Integer> typeNum = getIntent().getExtras().getIntegerArrayList("typeNum");
                    muscleSelectionIntent.putExtra("typeNum", typeNum);
                    muscleSelectionIntent.putExtra("exercise", false);
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

    }
}