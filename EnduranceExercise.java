package com.johnjacksonryan.exercise;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

/**
 * A class representing endurance exercises
 */

public class EnduranceExercise extends Exercise {

    /**
     * The default constructor
     * @param id            This exercises unique id
     * @param name          This exercises name
     * @param muscle        The primary muscle group of this exercise
     * @param superSet      True iff this exercise is a superset
     * @param dropSet       True iff this exercise is a dropset
     * @param specialRep    True iff this exercise involves a special rep type
     * @param equipment     List of required equipment
     * @param duration      The estimated duration of the exercise
     * @param difficulty    The difficulty level of the exercise
     */
    public EnduranceExercise(int id, String name, ArrayList<com.johnjacksonryan.exercise.ExerciseManager.MuscleGroup> muscle,
                             boolean superSet, boolean dropSet, boolean specialRep, ArrayList<ArrayList<String>> equipment,
                             int duration, ArrayList<Integer> difficulty, String notes){
        super(id, name, muscle, superSet, dropSet, specialRep, equipment, duration, difficulty, notes);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String toString() {
        ArrayList<String> equip = new ArrayList<>();
        for (ArrayList<String> items : this.getEquipment()) {
            equip.add(String.join(" and ", items));
        }
        return this.getName() + "\n" + String.format( " ID: %s", this.getId()) + "\n" + String.format(" For %s minutes", this.getDuration()) + "\n Requires one of: "
                + String.join(", ", equip) + "\n " + this.getNotes();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<String> getExerciseStringList() {
        ArrayList<String> exerciseString = new ArrayList<>();
        ArrayList<String> equip = new ArrayList<>();
        for (ArrayList<String> items : this.getEquipment()) {
            equip.add(String.join(" and ", items));
        }
        exerciseString.add(this.getName());
        exerciseString.add(String.format("ID:  %s", this.getId()));
        exerciseString.add(String.format(" For %s minutes", this.getDuration()));
        String notes = this.getNotes();
        if (!notes.equals("")) {
            notes = "Instructions: \n" + notes;
        }
        exerciseString.add("Requires one of: \n " + String.join(", ", equip) + "\n \n"  + notes);
        return exerciseString;
    }

}
