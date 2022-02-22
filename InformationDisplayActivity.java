package com.johnjacksonryan.display.information;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.johnjacksonryan.R;

public class InformationDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_display);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        AppCompatTextView tvTitle = (AppCompatTextView) findViewById(R.id.tvTitle);
        tvTitle.setText("Information");

        String title = getIntent().getExtras().getString("title");
        TextView titleTextView = (TextView) findViewById(R.id.informationTitleTextView);
        titleTextView.setText(title);

        String info = getIntent().getExtras().getString("info");
        TextView infoTextView = (TextView) findViewById(R.id.informationBodyTextView);
        infoTextView.setText(info);


        Button backToInformationBtn = (Button) findViewById(R.id.backToInformationBtn);
        backToInformationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent informationMenuIntent = new Intent(getApplicationContext(), InformationMenuActivity.class);
                startActivity(informationMenuIntent);
            }
        });
    }
}