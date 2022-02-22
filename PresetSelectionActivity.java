package com.johnjacksonryan.display.workoutcreation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.johnjacksonryan.R;

import java.util.ArrayList;

public class PresetSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preset_selection);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        AppCompatTextView title = (AppCompatTextView) findViewById(R.id.tvTitle);
        title.setText("Presets");

        Button pushPreset = (Button) findViewById(R.id.pushPreset);
        pushPreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String presetType = "push";
                Intent presetAttributeIntent = new Intent(getApplicationContext(), AttributePresetSelectionActivity.class);
                ArrayList<Integer> equipmentIndex = getIntent().getExtras().getIntegerArrayList("equipment");
                presetAttributeIntent.putExtra("equipment", equipmentIndex);
                presetAttributeIntent.putExtra("presetType", presetType);
                startActivity(presetAttributeIntent);
            }
        });

        Button pullPreset = (Button) findViewById(R.id.pullPreset);
        pullPreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String presetType = "pull";
                Intent presetAttributeIntent = new Intent(getApplicationContext(), AttributePresetSelectionActivity.class);
                ArrayList<Integer> equipmentIndex = getIntent().getExtras().getIntegerArrayList("equipment");
                presetAttributeIntent.putExtra("equipment", equipmentIndex);
                presetAttributeIntent.putExtra("presetType", presetType);
                startActivity(presetAttributeIntent);
            }
        });

        Button legsPreset = (Button) findViewById(R.id.legsPreset);
        legsPreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String presetType = "legs";
                Intent presetAttributeIntent = new Intent(getApplicationContext(), AttributePresetSelectionActivity.class);
                ArrayList<Integer> equipmentIndex = getIntent().getExtras().getIntegerArrayList("equipment");
                presetAttributeIntent.putExtra("equipment", equipmentIndex);
                presetAttributeIntent.putExtra("presetType", presetType);
                startActivity(presetAttributeIntent);
            }
        });

        Button upperPreset = (Button) findViewById(R.id.upperPreset);
        upperPreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String presetType = "upper";
                Intent presetAttributeIntent = new Intent(getApplicationContext(), AttributePresetSelectionActivity.class);
                ArrayList<Integer> equipmentIndex = getIntent().getExtras().getIntegerArrayList("equipment");
                presetAttributeIntent.putExtra("equipment", equipmentIndex);
                presetAttributeIntent.putExtra("presetType", presetType);
                startActivity(presetAttributeIntent);
            }
        });

        Button lowerPreset = (Button) findViewById(R.id.lowerPreset);
        lowerPreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String presetType = "legs";
                Intent presetAttributeIntent = new Intent(getApplicationContext(), AttributePresetSelectionActivity.class);
                ArrayList<Integer> equipmentIndex = getIntent().getExtras().getIntegerArrayList("equipment");
                presetAttributeIntent.putExtra("equipment", equipmentIndex);
                presetAttributeIntent.putExtra("presetType", presetType);
                startActivity(presetAttributeIntent);
            }
        });

        Button totalPreset = (Button) findViewById(R.id.totalPreset);
        totalPreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String presetType = "total";
                Intent presetAttributeIntent = new Intent(getApplicationContext(), AttributePresetSelectionActivity.class);
                ArrayList<Integer> equipmentIndex = getIntent().getExtras().getIntegerArrayList("equipment");
                presetAttributeIntent.putExtra("equipment", equipmentIndex);
                presetAttributeIntent.putExtra("presetType", presetType);
                startActivity(presetAttributeIntent);
            }
        });

        Button conditioningPreset = (Button) findViewById(R.id.conditioningPreset);
        conditioningPreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String presetType = "conditioning";
                Intent presetAttributeIntent = new Intent(getApplicationContext(), AttributePresetSelectionActivity.class);
                ArrayList<Integer> equipmentIndex = getIntent().getExtras().getIntegerArrayList("equipment");
                presetAttributeIntent.putExtra("equipment", equipmentIndex);
                presetAttributeIntent.putExtra("presetType", presetType);
                startActivity(presetAttributeIntent);
            }
        });

        Button abPreset = (Button) findViewById(R.id.abPreset);
        abPreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String presetType = "ab";
                Intent presetAttributeIntent = new Intent(getApplicationContext(), AttributePresetSelectionActivity.class);
                ArrayList<Integer> equipmentIndex = getIntent().getExtras().getIntegerArrayList("equipment");
                presetAttributeIntent.putExtra("equipment", equipmentIndex);
                presetAttributeIntent.putExtra("presetType", presetType);
                startActivity(presetAttributeIntent);
            }
        });

        Button chestPreset = (Button) findViewById(R.id.chestPreset);
        chestPreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String presetType = "chest";
                Intent presetAttributeIntent = new Intent(getApplicationContext(), AttributePresetSelectionActivity.class);
                ArrayList<Integer> equipmentIndex = getIntent().getExtras().getIntegerArrayList("equipment");
                presetAttributeIntent.putExtra("equipment", equipmentIndex);
                presetAttributeIntent.putExtra("presetType", presetType);
                startActivity(presetAttributeIntent);
            }
        });

        Button backPreset = (Button) findViewById(R.id.backPreset);
        backPreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String presetType = "back";
                Intent presetAttributeIntent = new Intent(getApplicationContext(), AttributePresetSelectionActivity.class);
                ArrayList<Integer> equipmentIndex = getIntent().getExtras().getIntegerArrayList("equipment");
                presetAttributeIntent.putExtra("equipment", equipmentIndex);
                presetAttributeIntent.putExtra("presetType", presetType);
                startActivity(presetAttributeIntent);
            }
        });

        Button armsPreset = (Button) findViewById(R.id.armsPreset);
        armsPreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String presetType = "arms";
                Intent presetAttributeIntent = new Intent(getApplicationContext(), AttributePresetSelectionActivity.class);
                ArrayList<Integer> equipmentIndex = getIntent().getExtras().getIntegerArrayList("equipment");
                presetAttributeIntent.putExtra("equipment", equipmentIndex);
                presetAttributeIntent.putExtra("presetType", presetType);
                startActivity(presetAttributeIntent);
            }
        });


    }
}