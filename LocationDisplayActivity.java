package com.johnjacksonryan.display.locationmenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.johnjacksonryan.R;
import com.johnjacksonryan.display.MainMenuActivity;
import com.johnjacksonryan.exercise.ExerciseManager;
import com.johnjacksonryan.gateway.Updater;
import com.johnjacksonryan.gateway.UserManagerFacade;
import com.johnjacksonryan.location.LocationManager;
import com.johnjacksonryan.workout.WorkoutManager;

import java.util.ArrayList;

public class LocationDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_display);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        AppCompatTextView title = (AppCompatTextView) findViewById(R.id.tvTitle);
        title.setText("Location");

        boolean created = getIntent().getExtras().getBoolean("new");
        ExerciseManager exerciseManager = new ExerciseManager();
        WorkoutManager workoutManager = new WorkoutManager(exerciseManager);
        LocationManager locationManager = new LocationManager();
        Updater updater = new Updater(exerciseManager, workoutManager, locationManager, this);
        UserManagerFacade userManagerFacade = new UserManagerFacade(exerciseManager, workoutManager, updater, locationManager);
        userManagerFacade.loadLocations();

        int id;

        String name = getIntent().getExtras().getString("name");
        if (created) {
            ArrayList<Integer> equipment = getIntent().getExtras().getIntegerArrayList("equipment");

            id = userManagerFacade.createLocation(name, equipment);

            String equipmentString = userManagerFacade.getLocationEquipmentString(id);

            TextView locationName = (TextView) findViewById(R.id.newLocationNameTextView);
            locationName.setText(name);

            TextView locationEquipment = (TextView) findViewById(R.id.locationEquipmentListTextView);
            locationEquipment.setText(equipmentString);
        }
        else {
            String idString = getIntent().getExtras().getString("id");
            id = Integer.parseInt(idString.substring(idString.length()-3, idString.length()).replaceAll("\\s+",""));
            String equipmentString = userManagerFacade.getLocationEquipmentString(id);

            TextView locationName = (TextView) findViewById(R.id.newLocationNameTextView);
            locationName.setText(name);

            TextView locationEquipment = (TextView) findViewById(R.id.locationEquipmentListTextView);
            locationEquipment.setText(equipmentString);
        }

        Button exitBtn = (Button) findViewById(R.id.exitToMainMenuLocationBtn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainMenuIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(mainMenuIntent);
            }
        });

        Button deleteLocationBtn = (Button) findViewById(R.id.deleteLocationBtn);
        deleteLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userManagerFacade.deleteLocation(id);
                Intent MainMenuIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(MainMenuIntent);

            }
        });
    }
}