package com.johnjacksonryan.workout;

import com.johnjacksonryan.exercise.Exercise;
import com.johnjacksonryan.exercise.ExerciseManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The use case class for workout entities
 */

public class WorkoutManager {
    private ArrayList<Workout> workouts;
    private ExerciseManager exerciseManager;
    private int id = -1;

    /**
     * The default constructor
     */
    public WorkoutManager(ExerciseManager exerciseManager) {
        this.workouts = new ArrayList<>();
        this.exerciseManager = exerciseManager;
    }

    public ArrayList<Integer> getSavedWorkoutsWithExercise(int id) {
        ArrayList<Integer> list = new ArrayList<>();
        for (Workout workout : workouts) {
            if (workout.getExercises().contains(id)){
                list.add(id);
            }
        }
        return list;
    }
    /**
     * Add a new workout to the workout manager
     */
    public int addWorkout( ArrayList<Integer> exerciseIds, int duration) {
        int id = this.id + 1;
        Workout workout = new Workout(id, exerciseIds, duration);
        workouts.add(workout);
        return workout.getId();
    }
    /**
     * Create a new workout based on user input
     */
    public int createWorkout(ArrayList<Integer> exerciseIds, int duration) {
        return addWorkout(exerciseIds, duration);
    }

    /**
     * Deletes a workout
     * @param id the id of the workout
     */
    public void deleteWorkout(int id) {
        this.workouts.remove(getWorkout(id));
    }

    public String getOldWorkoutString(int id) {
        Workout workout = getWorkout(id);
        String s;
        if (workout == null) {
            s = "This workout could not be generated";
        }
        else {
            String name = getName(id);
            ArrayList<Integer> workoutExerciseIds = workout.getExercises();
            ArrayList<Exercise> exercises = exerciseManager.getExercisesFromIds(workoutExerciseIds);
            ArrayList<Exercise> sortedExercises = exerciseManager.sortExercises(exercises);
            int duration = workout.getDuration();
            if (!getComplete(id)) {
                s = name + "\n" + String.format(" ID: %s", id) + "\n" + String.format(" Estimated time to complete: %s minutes", duration)
                        + "\n" + exerciseManager.getExercisesString(sortedExercises);
            }
            else {
                s = name + "\n" + String.format(" ID: %s", id) + "\n" + String.format(" Estimated time to complete: %s minutes", duration)
                        + "\n" + exerciseManager.getExercisesString(sortedExercises);
                //+ " Completed on " + getWorkoutDate(id).toString() + "\n"
            }
        }
        return s;
    }

    /**
     * @param id the id of the workout
     * @return the string of this workout
     */
    public ArrayList<ArrayList<String>> getWorkoutString(int id) {
        Workout workout = getWorkout(id);
        ArrayList<ArrayList<String>> workoutString = new ArrayList<>();
        String s;
        if (workout == null) {
            s = "This workout could not be generated";
            ArrayList<String> str = new ArrayList<>();
            str.add(s);
            workoutString.add(str);
        }
        else {
            String name = getName(id);
            ArrayList<Integer> workoutExerciseIds = workout.getExercises();
            ArrayList<Exercise> exercises = exerciseManager.getExercisesFromIds(workoutExerciseIds);
            ArrayList<Exercise> sortedExercises = exerciseManager.sortExercises(exercises);
            int duration = workout.getDuration();
            ArrayList<String> wStr = new ArrayList<>();
            wStr.add(name);
            wStr.add(String.format("ID: %s", id));
            wStr.add(String.format("Estimated time to complete: %s minutes", duration));
            //if (!getComplete(id)) {
            //    wStr.add("");
            //}
            //else {
            //    wStr.add(" Completed on " + getWorkoutDate(id).toString());
            //}
            workoutString.add(wStr);
            if (sortedExercises == null) {
                ArrayList<String> nullList = new ArrayList<>();
                workoutString.add(nullList);
            }
            else {
                for (Exercise exercise : sortedExercises) {
                    workoutString.add(exerciseManager.getExerciseStringList(exercise));
                }
            }
        }
        return workoutString;
    }

    /**
     * @param id the id of the workout
     * @return the name of this workout
     */
    public String getName(int id) {
        return getWorkout(id).getName();
    }

    /**
     * Sets the name of this workout
     * @param id the id of the workout
     * @param name the new name for this workout
     */
    public void setName(int id, String name) {
        getWorkout(id).setName(name);
    }

    /**
     * Complete an existing workout
     */
    public void completeWorkout(int id, String name) {
        getWorkout(id).setComplete();
        setName(id, name);
    }

    /**
     * @param completed true iff we want completed workouts
     * @return the list of ids of completed/uncompleted workouts
     */
    public ArrayList<Integer> getCompletedWorkouts(boolean completed) {
        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        for (Workout workout : this.workouts) {
            if (workout.getComplete()) {
                list1.add(workout.getId());
            } else {
                list2.add(workout.getId());
            }
        }
        if (completed) {
            return list1;
        } else {
            return list2;
        }
    }

    /**
     * @param completed true iff we want completed workout
     * @return a map of ids to names of workouts
     */
    public HashMap<Integer, String> getWorkoutMap(boolean completed) {
        HashMap<Integer, String> map = new HashMap<>();
        ArrayList<Integer> workoutIds = getCompletedWorkouts(completed);
        for (int id : workoutIds) {
            map.put(id, getName(id));
        }
        return map;
    }

    /**
     * @param completed true iff the list contains completed workouts
     * @return a list of lists representing the strings for workouts
     */
    public ArrayList<ArrayList<String>> getWorkoutList(boolean completed) {
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        ArrayList<Integer> workoutIds = getCompletedWorkouts(completed);
        for (int id : workoutIds) {
            ArrayList<String> info = new ArrayList<>();
            String name = getName(id);
            String idString = String.format("ID: %s", id);
            String duration = String.format("Estimated time to complete: %s", getWorkoutDuration(id));
            ArrayList<Integer> workoutExerciseIds = getWorkout(id).getExercises();
            ArrayList<Exercise> exercises = exerciseManager.getExercisesFromIds(workoutExerciseIds);
            ArrayList<Exercise> sortedExercises = exerciseManager.sortExercises(exercises);
            String workoutString = exerciseManager.getExercisesString(sortedExercises);
            info.add(name);
            info.add(idString);
            info.add(duration);
            info.add(workoutString);
            list.add(info);
        }
        return list;
    }

    /**
     * @return a list of lists representing workouts above rating 8.
     */
    public ArrayList<ArrayList<String>> getWorkoutRatingList() {
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        ArrayList<Integer> workoutIds = getRatingWorkouts(8);
        for (int id : workoutIds) {
            ArrayList<String> info = new ArrayList<>();
            String name = getName(id);
            String idString = String.format("ID: %s", id);
            String duration = String.format("Estimated time to complete: %s", getWorkoutDuration(id));
            ArrayList<Integer> workoutExerciseIds = getWorkout(id).getExercises();
            ArrayList<Exercise> exercises = exerciseManager.getExercisesFromIds(workoutExerciseIds);
            ArrayList<Exercise> sortedExercises = exerciseManager.sortExercises(exercises);
            String workoutString = exerciseManager.getExercisesString(sortedExercises);
            info.add(name);
            info.add(idString);
            info.add(duration);
            info.add(workoutString);
            list.add(info);
        }
        return list;
    }

    /**
     * Get the workout with a given id
     *
     * @param id the id of the workout
     * @return the workout corresponding to the given id
     */
    public Workout getWorkout(int id) {
        for (Workout workout : this.workouts) {
            if (workout.getId() == id) {
                return workout;
            }
        }
        return null;
    }

    /**
     * @return a list of all workouts
     */
    public ArrayList<Workout> getWorkouts() {
        return this.workouts;
    }

    /**
     * Get the duration of a workout of a given id
     *
     * @param id the id of the workout
     * @return the duration of the workout
     */
    public int getWorkoutDuration(int id) {
        if (getWorkout(id) != null) {
            return getWorkout(id).getDuration();
        }
        return 0;
    }

    /**
     * Loads a saved workout
     * @param id the id of the workout
     * @param exerciseIds the ids of the exercise
     * @param complete 0 iff complete, 1 otherwise
     */
    public void loadWorkout(int id, ArrayList<Integer> exerciseIds, int complete, String name, int rating) {
        int duration = exerciseManager.getWorkoutDuration(exerciseIds);
        this.id = id;
        Workout workout = new Workout(id, exerciseIds, duration);
        workout.setName(name);
        workout.setRating(rating);
        if (complete == 0) {workout.setComplete();}
        this.workouts.add(workout);
    }

    /**
     * @param rating the rating threshold
     * @return a map of ids to workout names with rating above rating
     */
    public HashMap<Integer, String> getWorkoutRatingMap(int rating) {
        HashMap<Integer, String> map = new HashMap<>();
        for (Workout workout : this.workouts) {
            if (workout.getRating() >= rating) {
                map.put(workout.getId(), workout.getName());
            }
        }
        return map;
    }

    /**
     *
     * @param id the id of the workout
     * @return true iff this workout is complete
     */
    public boolean getComplete(int id) {
        if (getWorkout(id) != null) { return getWorkout(id).getComplete(); }
        else { return false; }
    }

    /**
     *
     * @param id the id of the workout
     * @return the list of exercise ids of this workout
     */
    public ArrayList<Integer> getWorkoutExerciseIds(int id) {
        ArrayList<Integer> ids = new ArrayList<>();
        if (getWorkout(id) != null) {
            ArrayList<Exercise> exercises = getWorkoutExercises(id);
            for (Exercise e : exercises) {
                ids.add(e.getId());
            }
        }
        return ids;
    }

    /**
     * Get the exercises in a workout of a given id
     *
     * @param id the id of the workout
     * @return the exercise list of the workout
     */
    public ArrayList<Exercise> getWorkoutExercises(int id) {
        if (getWorkout(id) != null) {
            return exerciseManager.getExercisesFromIds(getWorkout(id).getExercises());
        }
        return null;
    }

    /**
     * Get the weights used in a workout of a given id
     *
     * @param id the id of the workout
     * @return a list of weights
     */
    public ArrayList<Integer> getWorkoutWeights(int id) {
        if (getWorkout(id) != null) {
            return getWorkout(id).getWeights();
        }
        return null;
    }

    /**
     * Get the reps reached in a workout of a given id
     *
     * @param id the id of the workout
     * @return a list of reps
     */
    public ArrayList<Integer> getWorkoutReps(int id) {
        if (getWorkout(id) != null) {
            return getWorkout(id).getReps();
        }
        return null;
    }

    /**
     * Get the date of a workout of a given id
     *
     * @param id the id of the workout
     * @return the date of the workout
     */
    public LocalDateTime getWorkoutDate(int id) {
        if (getWorkout(id) != null) {
            return getWorkout(id).getDate();
        }
        return null;
    }

    /**
     * Get the body weight of the user during a workout of a given id
     *
     * @param id the id of the workout
     * @return the users body weight
     */
    public int getWorkoutBodyWeight(int id) {
        if (getWorkout(id) != null) {
            return getWorkout(id).getBodyWeight();
        }
        return -1;
    }

    /**
     * Get the body fat percentage of the user during a workout of a given id
     *
     * @param id the id of the workout
     * @return the users body fat percentage
     */
    public int getWorkoutBodyFatPercentage(int id) {
        if (getWorkout(id) != null) {
            return getWorkout(id).getBodyFatPercentage();
        }
        return -1;
    }

    /**
     * Get the rating of a workout of a given id
     *
     * @param id the id of the workout
     * @return the rating
     */
    public int getWorkoutRating(int id) {
        if (getWorkout(id) != null) {
            return getWorkout(id).getRating();
        }
        return -1;
    }

    /**
     * Set weights in a workout
     *
     * @param weights the list of weights used
     * @param id the id of the workout
     * @return True iff weights successfully added
     */
    public boolean setWorkoutWeights(ArrayList<Integer> weights, int id) {
        if (getWorkout(id) != null && weights.size() == getWorkoutExercises(id).size()) {
            getWorkout(id).setWeight(weights);
            return true;
        }
        return false;
    }

    /**
     * Set reps in a workout
     *
     * @param reps the list of reps reached
     * @param id the id of the workout
     * @return True iff reps successfully added
     */
    public boolean setWorkoutReps(ArrayList<Integer> reps, int id) {
        if (getWorkout(id) != null && reps.size() == getWorkoutExercises(id).size()) {
            getWorkout(id).setReps(reps);
            return true;
        }
        return false;
    }

    /**
     * Set body weight of the user during a workout
     *
     * @param bodyWeight the weight of the user during the workout
     * @param id the id of the workout
     * @return True iff body weight successfully added
     */
    public boolean setWorkoutBodyWeight(int bodyWeight, int id) {
        if (getWorkout(id) != null) {
            getWorkout(id).setBodyWeight(bodyWeight);
            return true;
        }
        return false;
    }

    /**
     * Set body fat percentage weight of the user during a workout
     *
     * @param bodyFatPercentage the body fat percentage of the user during the workout
     * @param id the id of the workout
     * @return True iff body fat percentage successfully added
     */
    public boolean setWorkoutBodyFatPercentage(int bodyFatPercentage, int id) {
        if (getWorkout(id) != null) {
            getWorkout(id).setBodyFatPercentage(bodyFatPercentage);
            return true;
        }
        return false;
    }

    /**
     * Set the rating of a workout
     *
     * @param rating the rating of the workout
     * @param id the id of the workout
     * @return True iff rating successfully added
     */
    public boolean setWorkoutRating(int rating, int id) {
        if (getWorkout(id) != null) {
            getWorkout(id).setRating(rating);
            return true;
        }
        return false;
    }

    public boolean isWorkoutSaved(int id) {
        if (getWorkout(id) != null) {
            return true;
        }
        return false;
    }

    /**
     * Get a list of all workouts at or above a certain rating
     *
     * @param rating the rating to be used
     * @return a list of all workouts above or at rating
     */
    public ArrayList<Integer> getRatingWorkouts(int rating) {
        ArrayList<Integer> list = new ArrayList<>();
        for (Workout workout : this.workouts){
            if (workout.getRating() >= rating) {
                list.add(workout.getId());
            }
        }
        return list;
    }

    /**
     * Get a list of all workouts with duration at or below a certain duration
     *
     * @param duration the duration to be used
     * @return a list of all workouts with duration at or below duration
     */
    public ArrayList<Workout> getDurationWorkouts(int duration) {
        ArrayList<Workout> list = new ArrayList<>();
        for (Workout workout : this.workouts){
            if (workout.getDuration() >= duration) {
                list.add(workout);
            }
        }
        return list;
    }
}
