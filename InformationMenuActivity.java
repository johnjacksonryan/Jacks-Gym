package com.johnjacksonryan.display.information;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.johnjacksonryan.R;
import com.johnjacksonryan.display.MainMenuActivity;

public class InformationMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_menu);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        AppCompatTextView title = (AppCompatTextView) findViewById(R.id.tvTitle);
        title.setText("Information");

        Button strengthBtn = (Button) findViewById(R.id.strengthInfoBtn);
        strengthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent informationDisplayIntent = new Intent(getApplicationContext(), InformationDisplayActivity.class);
                informationDisplayIntent.putExtra("title", "Strength Exercises");
                informationDisplayIntent.putExtra("info", "These exercises are designed " +
                        "to increase the amount of weight you can lift. They typically involve lower reps (5-7)" +
                        " and higher sets (4-5) with longer rest periods (2-3 minutes). They do not include " +
                        "any super sets, drop sets, or special rep exercises. I would recommend doing these exercises if you are interested " +
                        "in increasing your 1-5 rep maxes on powerlifting exercises such as bench press, " +
                        "squat and dead lift.");
                startActivity(informationDisplayIntent);
            }
        });

        Button hypertrophyBtn = (Button) findViewById(R.id.hypertrophyInfoBtn);
        hypertrophyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent informationDisplayIntent = new Intent(getApplicationContext(), InformationDisplayActivity.class);
                informationDisplayIntent.putExtra("title", "Hypertrophy Exercises");
                informationDisplayIntent.putExtra("info", "These exercises are designed " +
                        "to optimize your muscle growth. They typically involve midrange reps (8-15)" +
                        " and sets of 3 with rest periods between 1-2 minutes. They include " +
                        "super sets, drop sets, and special rep exercises and make up the bulk of the default exercises stored on the app." +
                        " I would recommend doing these exercises if you are interested " +
                        "in building muscle mass." );
                startActivity(informationDisplayIntent);
            }
        });

        Button enduranceBtn = (Button) findViewById(R.id.enduranceInfoBtn);
        enduranceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent informationDisplayIntent = new Intent(getApplicationContext(), InformationDisplayActivity.class);
                informationDisplayIntent.putExtra("title", "Endurance Exercises");
                informationDisplayIntent.putExtra("info", "These exercises are designed " +
                        "to increase the length of time you can perform an exercise as well as increase cardiovascular health." +
                        " They are timed exercises, typically 30 minutes long. They do not include " +
                        "super sets, drop sets, or special rep exercises." +
                        " I would recommend doing these exercises if you are interested in " +
                        "performing exercises for extended periods of time, increasing your cardiovascular health, or burning calories.");
                startActivity(informationDisplayIntent);
            }
        });

        Button calisthenicBtn = (Button) findViewById(R.id.calisthenicInfoBtn);
        calisthenicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent informationDisplayIntent = new Intent(getApplicationContext(), InformationDisplayActivity.class);
                informationDisplayIntent.putExtra("title", "Calisthenic Exercises");
                informationDisplayIntent.putExtra("info", "These exercises are designed " +
                        "to be performed with only your body weight and sometimes a pull up bar. " +
                        "They typically involve sets of 3 to failure with rest periods between 1-2 minutes. They include " +
                        "super sets and special rep exercises." +
                        " I would recommend doing these exercises if you have no equipment or are interested " +
                        "in developing control over your body.");
                startActivity(informationDisplayIntent);
            }
        });

        Button conditioningBtn = (Button) findViewById(R.id.conditioningInfoBtn);
        conditioningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent informationDisplayIntent = new Intent(getApplicationContext(), InformationDisplayActivity.class);
                informationDisplayIntent.putExtra("title", "Conditioning Exercises");
                informationDisplayIntent.putExtra("info", "These exercises are designed " +
                        "to burn calories quickly and increase your cardiovascular health. " +
                        "They typically involve exercises performed for 30-60 for 3-5 rounds with 15 seconds of rest between rounds. " +
                        "They do not include " +
                        "super sets, drop sets, or special rep exercises."+
                        " I would recommend doing these exercises if you are interested " +
                        "in burning calories or increasing your cardiovascular health.");
                startActivity(informationDisplayIntent);
            }
        });

        Button abBtn = (Button) findViewById(R.id.abInfoBtn);
        abBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent informationDisplayIntent = new Intent(getApplicationContext(), InformationDisplayActivity.class);
                informationDisplayIntent.putExtra("title", "Ab Exercises");
                informationDisplayIntent.putExtra("info", "These exercises are designed " +
                        "to build your abs. They typically involve exercises performed for 45 seconds" +
                        " for 3 rounds with 15 second rest periods. They do not include " +
                        "super sets, drop sets, or special rep exercises."+
                        " I would recommend doing these exercises if you are interested " +
                        "in building a strong core or good looking beach body.");
                startActivity(informationDisplayIntent);
            }
        });

        Button superSetBtn = (Button) findViewById(R.id.superSetInfoBtn);
        superSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent informationDisplayIntent = new Intent(getApplicationContext(), InformationDisplayActivity.class);
                informationDisplayIntent.putExtra("title", "Super Set Exercises");
                informationDisplayIntent.putExtra("info", "These exercises involve two exercises performed back to back." +
                        " The goal of the super set is to provide additional stress to your muscles by switching exercises halfway through a set." +
                        " It allows you to hit the same muscle group in a different way, or train past failure by switching to a similar, but easier exercise." +
                        " I would recommend selecting only a few super set exercises per workout because the additional exercise can add too much" +
                        " volume to workout if you do too many.");
                startActivity(informationDisplayIntent);
            }
        });

        Button dropSetBtn = (Button) findViewById(R.id.dropSetInfoBtn);
        dropSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent informationDisplayIntent = new Intent(getApplicationContext(), InformationDisplayActivity.class);
                informationDisplayIntent.putExtra("title", "Drop Set Exercises");
                informationDisplayIntent.putExtra("info", "These exercises involve lowering the weight of an exercise after reaching failure on" +
                        " the original weight. The goal of the drop set is to allow you to train past failure by lowering the weight" +
                        " on an exercise after you failed at a previous weight. I would recommend selecting only a few drop set exercises per workout because training to failure and past it can add too much" +
                        " stress and volume to workout if you do too many." );
                startActivity(informationDisplayIntent);
            }
        });

        Button specialRepBtn = (Button) findViewById(R.id.specialRepInfoBtn);
        specialRepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent informationDisplayIntent = new Intent(getApplicationContext(), InformationDisplayActivity.class);
                informationDisplayIntent.putExtra("title", "Special Rep Exercises");
                informationDisplayIntent.putExtra("info", "These exercises involve a rep style other than the 'straight' rep format." +
                        " The goal of the special rep is to provide a new stress to the muscle to illicit muscle growth. Some examples of 'special' reps" +
                        " are paused reps (pausing at the most difficult point of the rep), 1 & 1/2 reps (performing each rep 1 & 1/2 times), and tempo reps (performing each rep very slowly)." +
                        " I would recommend this technique to more experienced lifters as the new stimulus provided to the muscle is really only needed " +
                        "once the muscle has gotten too used to the standard rep style.");
                startActivity(informationDisplayIntent);
            }
        });

        Button repsBtn = (Button) findViewById(R.id.repsInfoBtn);
        repsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent informationDisplayIntent = new Intent(getApplicationContext(), InformationDisplayActivity.class);
                informationDisplayIntent.putExtra("title", "Reps and Sets Info");
                informationDisplayIntent.putExtra("info", "The sets and reps formats of each exercise type are discussed in each" +
                        " exercises info section. There are three types of sets and reps format. The first is a number of reps and sets," +
                        " for example, 3 sets of 10 reps format. This means you should select a weight that would cause you to fail at around 11-12 reps. " +
                        "The second is a timed and rounds structure, for example, 3 rounds of 45 seconds with 15 seconds of rest between rounds. " +
                        "In this format perform as many reps as you can in the time frame. The third is sets to failure. This means you perform reps until " +
                        "you reach a form failure. Do not sacrifice form to achieve more reps, stop once your form has been compromised.");
                startActivity(informationDisplayIntent);
            }
        });

        Button workoutBtn = (Button) findViewById(R.id.workoutInfoBtn);
        workoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent informationDisplayIntent = new Intent(getApplicationContext(), InformationDisplayActivity.class);
                informationDisplayIntent.putExtra("title", "Workout Info");
                informationDisplayIntent.putExtra("info", "There are two options for workout creation: an original workout and a preset workout." +
                        " The original workout allows for more selection, choosing the number of each type of exercise, the muscle groups, and the number of super sets, drop sets, and special rep exercises." +
                        " The preset workout gives you a selection of push, pull, legs, upper, lower, total body, conditioning, ab, chest, back or arms workouts where those choices given " +
                        "in the original workout option have already been made. When a workout is generated it will randomly select exercises based on your specifications; those which " +
                        "fit your equipment and difficulty requirements. It cycles through the chosen muscle groups choosing one exercise at a time to evenly distribute the selection.");
                startActivity(informationDisplayIntent);
            }
        });

        Button moreBtn = (Button) findViewById(R.id.moreInfoBtn);
        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent informationDisplayIntent = new Intent(getApplicationContext(), InformationDisplayActivity.class);
                informationDisplayIntent.putExtra("title", "More Info");
                informationDisplayIntent.putExtra("info", "Exercise creation allows you to add custom exercises to the app. The rep entry is different for the different types of exercises. " +
                        "For strength, hypertrophy and calisthenic exercises it corresponds to reps. For endurance exercises it corresponds to minutes." +
                        " For conditioning and ab exercises it corresponds to seconds. If you wish for your exercise to be performed to failure enter -1 for reps. " +
                        "Location creation allows you to add a location where you workout to the app to make it easier to select your equipment." +
                        " Each exercise has a 'multiplier' value which dictates the weight it has in the selection process when workouts are generated." +
                        " The default value is 1, but it can be changed under the 'View Exercises' tab. Higher values mean the exercise will be more likely to be selected. " +
                        "For example, an exercise with a multiplier of 2 is twice as likely to be selected than an exercise with a multiplier of 1." );
                startActivity(informationDisplayIntent);
            }
        });

        Button exitBtn = (Button) findViewById(R.id.exitToMainMenuInformationBtn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainMenuIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(mainMenuIntent);
            }
        });

    }
}