package com.johnjacksonryan.workout;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * A class representing a workout
 */

public class Workout {
    private final int id;
    private ArrayList<Integer> exerciseIds;
    private ArrayList<Integer> weights;
    private ArrayList<Integer> reps;
    private int duration;
    private LocalDateTime date;
    private int bodyWeight;
    private int bodyFatPercentage;
    private int rating;
    private boolean complete;
    private String name;

    /**
     * The default constructor
     * @param id            The id of this workout
     * @param exerciseIds     The exercises in this workout
     * @param duration      The estimated duration of this workout
     */
    public Workout(int id, ArrayList<Integer> exerciseIds, int duration){
        this.id = id;
        this.exerciseIds = exerciseIds;
        this.duration = duration;
        this.complete = false;
        this.name = "Workout";
        this.rating = -1;
    }
    /**
     * Getters
     */
    /**
     * @return the id of this workout
     */
    public int getId() { return this.id; }
    /**
     * @return the exercises of this workout
     */
    public ArrayList<Integer> getExercises() { return this.exerciseIds; }
    /**
     * @return the weights of this workout
     */
    public ArrayList<Integer> getWeights() { return this.weights; }
    /**
     * @return the reps of this workout
     */
    public ArrayList<Integer> getReps() { return this.reps; }
    /**
     * @return the duration of this workout
     */
    public int getDuration() { return this.duration; }
    /**
     * @return the date this workout was created
     */
    public LocalDateTime getDate() { return this.date; }
    /**
     * @return the users body weight during this workout
     */
    public int getBodyWeight() { return this.bodyWeight; }
    /**
     * @return the users body fat percentage during this workout
     */
    public int getBodyFatPercentage() { return this.bodyFatPercentage; }
    /**
     * @return the rating of this workout
     */
    public int getRating() { return this.rating; }
    /**
     * @return True iff this workout is complete
     */
    public boolean getComplete() { return this.complete; }

    public String getName() { return this.name; }
    /**
     * Setters
     */
    /**
     * Sets the exercises of this workout
     */
    public void setExercises(ArrayList<Integer> exercises) { this.exerciseIds = exercises; }
    /**
     * Sets the weights of this workout
     */
    public void setWeight(ArrayList<Integer> weights) { this.weights = weights; }

    public void setName(String name) { this.name = name; }
    /**
     * Sets the reps of this workout
     */
    public void setReps(ArrayList<Integer> reps) { this.reps = reps; }
    /**
     * Sets the duration of this workout
     */
    public void setDuration(int duration) { this.duration = duration; }
    /**
     * Sets the users body weight during this workout
     */
    public void setBodyWeight(int bodyWeight) { this.bodyWeight = bodyWeight; }
    /**
     * Sets the users body fat percentage during this workout
     */
    public void setBodyFatPercentage(int bodyFatPercentage) { this.bodyFatPercentage = bodyFatPercentage; }
    /**
     * Sets the rating of this workout
     */
    public void setRating(int rating) { this.rating = rating; }
    /**
     * Sets the complete status to true
     */
    public void setComplete() {
        this.complete = true;
    }



}
