package com.johnjacksonryan.exercise;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

/**
 * A class representing ab exercises
 */

public class AbExercise extends Exercise {
    private int reps;
    private int sets;

    /**
     * The default constructor
     * @param id            This exercises unique id
     * @param name          This exercises name
     * @param muscle        The primary muscle group of this exercise
     * @param superSet      True iff this exercise is a superset
     * @param dropSet       True iff this exercise is a dropset
     * @param specialRep    True iff this exercise involves a special rep type
     * @param equipment     List of required equipment
     * @param reps          The duration (seconds) that this exercise is to be performed
     * @param sets          The number of rounds
     * @param difficulty    The difficulty of the exercise
     * @param duration      The estimated duration of the exercise
     */
    public AbExercise(int id, String name, ArrayList<com.johnjacksonryan.exercise.ExerciseManager.MuscleGroup> muscle,
                      boolean superSet, boolean dropSet, boolean specialRep, ArrayList<ArrayList<String>> equipment, int reps,
                      int sets, int duration, ArrayList<Integer> difficulty, String notes){
        super(id, name, muscle, superSet, dropSet, specialRep, equipment, duration, difficulty, notes);
        this.reps = reps;
        this.sets = sets;
    }
    /**
     * Getters
     */
    /**
     * @return the reps of the exercise
     */
    public int getReps() { return this.reps; }
    /**
     * @return the sets of the exercise
     */
    public int getSets() { return this.sets; }
    /**
     * Setters
     */
    /**
     * Set the reps of the exercise
     */
    public void setReps(int reps) { this.reps = reps; }
    /**
     * Set the sets of the exercise
     */
    public void setSets(int sets) { this.sets = sets; }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String toString() {
        ArrayList<String> equip = new ArrayList<>();
        for (ArrayList<String> items : this.getEquipment()) {
            equip.add(String.join(" and ", items));
        }
        if (this.reps == -1) {
            return this.getName() + "\n" + String.format( " ID: %s", this.getId()) + "\n" + String.format(" For %s rounds ", this.sets) + "to failure"+ "\n Requires one of: "
                    + String.join(", ", equip) + "\n " + this.getNotes();
        }
        return this.getName() + "\n" + String.format( " ID: %s", this.getId()) + "\n" + String.format(" For %s rounds of %s seconds", this.sets, this.reps) + "\n Requires one of: "
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
        if (this.reps == -1) {
           exerciseString.add(String.format(" For %s rounds ", this.sets) + "to failure");
        }
        else {
            exerciseString.add(String.format(" For %s rounds of %s seconds", this.sets, this.reps));
        }
        String notes = this.getNotes();
        if (!notes.equals("")) {
            notes = "Instructions: \n" + notes;
        }
        exerciseString.add("Requires one of: \n " + String.join(", ", equip) + "\n \n"  + notes);
        return exerciseString;
    }
}
