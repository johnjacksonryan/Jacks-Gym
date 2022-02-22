package com.johnjacksonryan.exercise;

import java.util.ArrayList;

/**
 * A class representing an exercise
 */

public abstract class Exercise {
    private final int id;
    private String name;
    private ArrayList<com.johnjacksonryan.exercise.ExerciseManager.MuscleGroup> muscle;
    private boolean superSet;
    private boolean dropSet;
    private boolean specialRep;
    private ArrayList<ArrayList<String>> equipment;
    private int duration;
    private ArrayList<Integer> difficulty;
    private String notes;
    private int multiplier;

    /**
     * The default constructor
     * @param id            This exercises unique id
     * @param name          This exercises name
     * @param muscle        The primary muscles of this exercise
     * @param superSet      True iff this exercise is a superset
     * @param dropSet       True iff this exercise is a dropset
     * @param specialRep    True iff this exercise involves a special rep type
     * @param equipment     List of required equipment
     * @param duration      The estimated duration of the exercise in minutes
     * @param difficulty    The difficulty range of the exercise
     * @param notes         The notes on this exercise
     */
    public Exercise(int id, String name, ArrayList<com.johnjacksonryan.exercise.ExerciseManager.MuscleGroup> muscle, boolean superSet, boolean dropSet,
                    boolean specialRep, ArrayList<ArrayList<String>> equipment, int duration, ArrayList<Integer> difficulty, String notes){
        this.id = id;
        this.name = name;
        this.muscle = muscle;
        this.superSet = superSet;
        this.dropSet = dropSet;
        this.specialRep = specialRep;
        this.equipment = equipment;
        this.duration = duration;
        this.difficulty = difficulty;
        this.notes = notes;
        this.multiplier = 1;
    }
    /**
     * Getters
     */
    public int getMultiplier() { return this.multiplier; }
    /**
     * @return the id of the exercise
     */
    public int getId() { return this.id; }
    /**
     * @return the name of the exercise
     */
    public String getName() { return this.name; }
    /**
     * @return the main muscle group of the exercise
     */
    public ArrayList<com.johnjacksonryan.exercise.ExerciseManager.MuscleGroup> getMuscle() { return this.muscle; }
    /**
     * @return True iff this exercise is a superset
     */
    public boolean getSuperSet() { return this.superSet; }
    /**
     * @return True iff this exercise is a dropset
     */
    public boolean getDropSet() { return this.dropSet; }
    /**
     * @return True iff this exercise has a special rep type
     */
    public boolean getSpecialRep() { return this.specialRep; }
    /**
     * @return the required equipment of the exercise
     */
    public ArrayList<ArrayList<String>> getEquipment() { return this.equipment; }
    /**
     * @return the duration of the exercise
     */
    public int getDuration() { return this.duration; }
    /**
     * @return the difficulty of the exercise
     */
    public ArrayList<Integer> getDifficulty() { return this.difficulty; }
    /**
     * @return the notes of this exercise
     */
    public String getNotes() { return this.notes; }
    /**
     * Setters
     */
    /**
     * Set the name of the exercise
     */
    public void setMultiplier(int mult) { this.multiplier = mult;}

    public void setName(String name) { this.name = name; }
    /**
     * Set the main muscle group of the exercise
     */
    public void setMuscle(ArrayList<com.johnjacksonryan.exercise.ExerciseManager.MuscleGroup> muscle) { this.muscle = muscle; }
    /**
     * Set True iff this exercise is a superset
     */
    public void setSuperSet(boolean superSet) { this.superSet = superSet; }
    /**
     * Set True iff this exercise is a dropset
     */
    public void setDropSet(boolean dropSet) { this.dropSet = dropSet; }
    /**
     * Set True iff this exercise has a special rep type
     */
    public void setSpecialRep(boolean specialRep) { this.specialRep = specialRep; }
    /**
     * Set the required equipment of the exercise
     */
    public void setEquipment(ArrayList<ArrayList<String>> equipment) { this.equipment = equipment; }
    /**
     * Set the duration of the exercise
     */
    public void setDuration(int duration) { this.duration = duration; }
    /**
     * Set the difficulty of the exercise
     */
    public void setDifficulty(ArrayList<Integer> difficulty) { this.difficulty = difficulty; }
    /**
     * Set the notes of this exercise
     */
    public void setNotes(String notes) { this.notes = notes; }
    /**
     *
     */
    @Override
    public abstract String toString();

    public abstract ArrayList<String> getExerciseStringList();
















}
