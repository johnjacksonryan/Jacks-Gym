package com.johnjacksonryan.gateway;

import com.johnjacksonryan.exercise.ExerciseManager;
import com.johnjacksonryan.location.LocationManager;
import com.johnjacksonryan.workout.WorkoutManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class UserManagerFacade {
    private ExerciseManager exerciseManager;
    private WorkoutManager workoutManager;
    private Updater updater;
    private LocationManager locationManager;

    public UserManagerFacade(ExerciseManager exerciseManager, WorkoutManager workoutManager, Updater updater, LocationManager locationManager) {
        this.exerciseManager = exerciseManager;
        this.workoutManager = workoutManager;
        this.updater = updater;
        this.locationManager = locationManager;
    }

    public WorkoutManager getWorkoutManager() {
        return workoutManager;
    }

    public ExerciseManager getExerciseManager() {
        return exerciseManager;
    }

    public Updater getUpdater() { return this.updater; }

    //UserManager methods

    /**
     * Loads the custom exercises into the exercise manager
     */
    public void loadCustom() {
        updater.loadCustom();
        updater.loadDefaultMultipliers();
    }

    /**
     * Loads the locations into the location manager
     */
    public void loadLocations() { updater.loadLocations(); }

    public int getExerciseMultiplier(int id) {
        return exerciseManager.getExerciseMultiplier(id);
    }

    public void setExerciseMultiplier(int id, int multiplier) {
        if (0 < multiplier) {
            exerciseManager.setMultiplier(id, multiplier);
            updater.addMultipliers();
        }
    }

    /**
     * @return a map of id to location name
     */
    public HashMap<Integer, String> getLocationsMap() {
        return locationManager.getLocationMap();
    }

    /**
     * @return a map of id to location string
     */
    public HashMap<Integer, String> getLocationsMapString() {
        return locationManager.getLocationMapString();
    }

    public void deleteLocation(int id) {
        locationManager.deleteLocation(id);
        updater.deleteLocation(id);
    }

    public ArrayList<ArrayList<String>> getDeletedExerciseList() {
        HashMap<Integer, String> deletedMap = getDeleted();
        ArrayList<Integer> ids = new ArrayList<>(deletedMap.keySet());
        ArrayList<String> names = new ArrayList<>(deletedMap.values());
        ArrayList<ArrayList<String>> deletedList = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            ArrayList<String> deleted = new ArrayList<>();
            deleted.add(names.get(i));
            deleted.add(String.format("ID:  %s", ids.get(i)));
            deleted.add(".");
            deleted.add(".");
            deletedList.add(deleted);
        }
        return deletedList;
    }

    public String getLocationEquipmentString(int id) {
        ArrayList<String> equipmentList = getLocationEquipment(id);
        String equipment = "This location has: ";
        for (String e : equipmentList) {
            equipment = equipment + "\n" + e;
        }
        return equipment;
    }

    public ArrayList<Integer> getLocationEquipmentIndex(int id) {
        ArrayList<String> equipment = getLocationEquipment(id);
        ArrayList<Integer> equipmentIndex = new ArrayList<>();
        for (String e : equipment) {
            equipmentIndex.add(exerciseManager.getEquipmentIndexFromString(e));
        }
        return equipmentIndex;
    }

    public ArrayList<ArrayList<String>> getLocationList() {
        return locationManager.getLocationList();
    }

    /**
     * @param id the id of the location
     * @return this locations equipment
     */
    public ArrayList<String> getLocationEquipment(int id) {
        return locationManager.getEquipment(id);
    }

    /**
     * Deletes a saved workout
     * @param id the id of the workout
     */
    public void deleteSavedWorkout(int id) {
        updater.deleteSavedWorkout(id);
        workoutManager.deleteWorkout(id);
    }

    /**
     * Completes a saved workout
     * @param id the id of the workout
     * @param name the name of the workout
     */
    public void completeSavedWorkout(int id, String name) {
        workoutManager.completeWorkout(id, name);
        updater.completeSavedWorkout(id);
    }

    /**
     * Creates a new location
     * @param name the name of the location
     * @param equipmentIndex the equipment at this location
     */
    public int createLocation(String name, ArrayList<Integer> equipmentIndex) {
        ArrayList<String> equipment = exerciseManager.getEquipmentFromIndex(equipmentIndex);
        int id = locationManager.addLocation(name, equipment);
        updater.addLocation(id);
        return id;
    }

    /**
     *
     * @return the list of deleted exercises
     */
    public HashMap<Integer, String> getDeleted() {
        return updater.getDeleted();
    }

    /**
     * @param completed true iff the list is for completed workouts
     * @return the map of ids to workout names
     */
    public HashMap<Integer, String> getWorkoutMap(boolean completed) {
        return workoutManager.getWorkoutMap(completed);
    }

    public ArrayList<ArrayList<String>> getWorkoutList(boolean completed) {
        return workoutManager.getWorkoutList(completed);
    }

    /**
     * @param rating the rating threshold
     * @return a map of ids to workout names
     */
    public HashMap<Integer, String> getWorkoutRatingMap(int rating) {
        return workoutManager.getWorkoutRatingMap(rating);
    }

    public ArrayList<ArrayList<String>> getWorkoutRatingList() {
        return workoutManager.getWorkoutRatingList();
    }

    /**
     * Recovers a deleted workout
     * @param id the id of the workout
     */
    public void recover(int id) {
        updater.recover(id);
    }
    /**
     * Deletes the deleted exercises from the system
     */
    public void loadDeleted() {
        updater.loadDeleted();
    }

    /**
     * Removes a workout from the workout manager
     * @param id the id of this workout
     */
    public void deleteWorkout(int id) {
        workoutManager.deleteWorkout(id);
    }

    /**
     * @param id the id of the workout
     * @return the string representation of this workout
     */
    public ArrayList<ArrayList<String>> getWorkoutString(int id) {
        return workoutManager.getWorkoutString(id);
    }

    /**
     * Saves the workout to this users workouts
     * @param id the id of the workout
     */
    public void saveWorkout(int id, String name) {
        workoutManager.setName(id, name);
        if (workoutManager.isWorkoutSaved(id)) {
            updater.saveSavedWorkout(id);
        }
        else {
            updater.saveWorkout(id);
        }
    }

    /**
     * @param id the id of the workout
     * @return the name of this workout
     */
    public String getWorkoutName(int id) {
        return workoutManager.getName(id);
    }

    /**
     * Rates this workout
     * @param id the id of the workout
     * @param rating the rating given
     */
    public void rateWorkout(int id, int rating) {
        workoutManager.setWorkoutRating(rating, id);
    }

    /**
     * Sets this workout to complete
     * @param id the id of the workout to be completed
     */
    public void completeWorkout(int id, String name){
        workoutManager.completeWorkout(id, name);
    }

    /**
     * loads all the workouts
     */
    public void loadWorkouts() {
        updater.loadWorkouts();
    }

    public String getOldWorkoutString(int id) {
        return workoutManager.getOldWorkoutString(id);
    }

    /**
     * Creates a new workout
     */
    public int createWorkout(ArrayList<Integer> typeNum, ArrayList<Integer> muscleIndex,
                             ArrayList<Integer> equipmentIndex, int difficulty, int numSuperSet,
                             int numDropSet, int numSpecialRep) {
        if (typeNum.get(5) != 0 && (!muscleIndex.contains(21) || !muscleIndex.contains(22) ||
                !muscleIndex.contains(23) || !muscleIndex.contains(24) || !muscleIndex.contains(25))){
            muscleIndex.add(21);
        }
        ArrayList<ExerciseManager.MuscleGroup> muscles = exerciseManager.getMusclesFromIndex(muscleIndex);
        ArrayList<String> equipment = exerciseManager.getEquipmentFromIndex(equipmentIndex);
        ArrayList<Integer> exerciseIds = exerciseManager.selectExercises(typeNum, muscles, equipment,
                difficulty, numSuperSet, numDropSet, numSpecialRep);
        if (exerciseIds == null) {
            return -1;
        }
        int duration = exerciseManager.getWorkoutDuration(exerciseIds);
        int id = workoutManager.createWorkout(exerciseIds, duration);
        return id;
    }

    /**
     * Creates a new exercise
     */
    public int createExercise(int t, String name, ArrayList<Integer> muscles, int ss, int ds, int sr,
                               ArrayList<ArrayList<Integer>> equipment, int duration, int reps, int sets,
                               ArrayList<Integer> difficulty, String notes) {
        boolean superSet = false;
        if (ss == 0) { superSet = true;}
        boolean dropSet = false;
        if (ds == 0) { dropSet = true;}
        boolean specialRep = false;
        if (sr == 0) { specialRep = true;}
        updater.addCustom(t, name, muscles, superSet, dropSet, specialRep, equipment, duration, reps, sets, difficulty, notes);
        return exerciseManager.addExercise(t, name, muscles, superSet, dropSet, specialRep, equipment, duration, reps, sets, difficulty, notes);
    }

    /**
     * deletes an exercise
     * @param id the id of the exercise to be deleted
     */
    public void delete(int id) {
        String name = exerciseManager.getExercise(id).getName();
        exerciseManager.removeExercise(id);
        updater.addDeleted(id, name);
        ArrayList<Integer> savedIds = workoutManager.getSavedWorkoutsWithExercise(id);
        for (int i : savedIds) {
            deleteSavedWorkout(i);
        }
    }

    public ArrayList<ArrayList<String>> getExerciseList(int type, ArrayList<Integer> musclesIndex) {
        return exerciseManager.getExerciseList(type, musclesIndex);
    }

    /**
     *
     * @return the string representation of a list of exercises
     */
    public String getExerciseStrings(int type, int muscle) {
        return exerciseManager.getExercisesString(type, muscle);
    }

    /**
     * Returns a map of ids to exercise strings
     * @param type the type
     * @param muscle the muscle
     * @return
     */
    public HashMap<Integer, String> getExerciseMap(int type, int muscle) {
        return exerciseManager.getExerciseTypeMuscle(type, muscle);
    }

    public ArrayList<Integer> A(int i, int j, int k) { return new ArrayList<>(Arrays.asList(i, j, k));}
    public ArrayList<Integer> A(int i, int j) { return new ArrayList<>(Arrays.asList(i, j));}
    public ArrayList<Integer> A(int i) { return new ArrayList<>(Arrays.asList(i)); }

    /**
     * Loads the default exercises into the exercise manager
     */
    public void loadDefault() {
        exerciseManager.addExercise(1, "", new ArrayList<>(Arrays.asList()),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "");
        // Strength

        // Legs

        exerciseManager.addExercise(0, "Heavy Back Squat", new ArrayList<>(Arrays.asList(0, 1, 2)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2, 3))),
                15, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Hold the weight on your back and squat down to depth. Use a weight which would cause you to fail at 6-7 reps. Rest 2-3 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Front Squat", new ArrayList<>(Arrays.asList(0, 1, 2)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2, 3))),
                15, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Hold the weight on your collarbone and squat down to depth. Use a weight which would cause you to fail at 6-7 reps. Rest 2-3 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Forward Lunge", new ArrayList<>(Arrays.asList(0, 1, 2)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2, 3))),
                15, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Either holding dumbbells in both hands or barbell on your back, step forward into a lunge. If you have vulnerable knees step back into the lunge instead. Use a weight which would cause you to fail at 6-7 reps. Rest 2-3 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Step Back Lunge", new ArrayList<>(Arrays.asList(0, 1, 2)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2, 3))),
                15, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Either holding dumbbells in both hands or barbell on your back, step back into a lunge. Use a weight which would cause you to fail at 6-7 reps. Rest 2-3 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Barbell Hip Thrust", new ArrayList<>(Arrays.asList(1, 2)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0),A(2))),
                15, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With your upper back elevated on a bench (if available) let the barbell rest on your hips and thrust upwards. Use a weight which would cause you to fail at 6-7 reps. Rest 2-3 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Elevated Split Squat", new ArrayList<>(Arrays.asList(0, 1, 2)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 8), A(2, 8), A(1, 9), A(2, 9))),
                15, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Elevate one leg behind you on the bench and split squat. Use a weight which would cause you to fail at 6-7 reps. Rest 2-3 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Romanian Deadlifts", new ArrayList<>(Arrays.asList(1, 2, 17)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2))),
                15, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform a deadlift without bending the knees, go down as far as your hamstrings allow. Use a weight which would cause you to fail at 6-7 reps. Rest 2-3 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Deadlifts", new ArrayList<>(Arrays.asList(1, 2, 17)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2))),
                15, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Deadlift from the ground. Use a weight which would cause you to fail at 6-7 reps. Rest 2-3 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Glute/Ham Raises", new ArrayList<>(Arrays.asList(1, 2)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(22))),
                12, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Holding the weight with your arms against your chest, perform glute/ham raises. Use a weight which would cause you to fail at 6-7 reps. Rest 2-3 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Leg Press", new ArrayList<>(Arrays.asList(0, 1, 2)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(12))),
                15, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1)), "Use a weight which would cause you to fail at 6-7 reps. Rest 2-3 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Side Lunge", new ArrayList<>(Arrays.asList(18, 19)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2))), 12, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Either holding a dumbbell in a goblet position or a barbell on your back squat to the side, alternating sides each time. Use a weight which would cause you to fail at 6-7 reps. Rest 2-3 minutes between sets.");

        // Chest

        exerciseManager.addExercise(0, "Heavy Dumbbell Bench Press", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 8), A(1, 9))),
                12, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Keep your scapula depressed. Use a weight which would cause you to fail at 6-7 reps. Rest 2-3 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Barbell Bench Press", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(2, 8), A(2, 9), A(4))),
                12, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Keep your scapula depressed. Use a weight which would cause you to fail at 6-7 reps. Rest 2-3 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Incline Dumbbell Bench Press", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 9))),
                12, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Set the bench at a 30-45 degree incline. Use a weight which would cause you to fail at 6-7 reps. Rest 2-3 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Incline Barbell Bench Press", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(2, 9))),
                12, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Set the bench at a 30-45 degree incline. Use a weight which would cause you to fail at 6-7 reps. Rest 2-3 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Weighted Dips", new ArrayList<>(Arrays.asList(3, 12)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1,7))),
                12, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Keep your scapula depressed. Use a weight which would cause you to fail at 6-7 reps. Rest 2-3 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Banded Push Ups", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(5))),
                12, 7, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the band around your back and fixed on both hands perform push ups. Use a weight which would cause you to fail at 8-9 reps. Rest 2-3 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Weighted Push Ups", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(26))),
                12, 7, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the weight on your back perform push ups. Use a weight which would cause you to fail at 8-9 reps. Rest 2-3 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Landmine Press", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(41))),
                12, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Use a weight which would cause you to fail at 6-7 reps. Rest 2-3 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Chest Press", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(19))),
                12, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Use the chest press machine. Use a weight which would cause you to fail at 6-7 reps. Rest 2-3 minutes between sets.");

        // Shoulders

        exerciseManager.addExercise(0, "Heavy Barbell Overhead Press", new ArrayList<>(Arrays.asList(7, 9)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(2))),
                12, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Press the weight overhead. Make sure to move your head underneath the weight as you press overhead. Use a weight which would cause you to fail at 6-7 reps. Rest 2-3 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Dumbbell Overhead Press", new ArrayList<>(Arrays.asList(7)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))),
                12, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Press the weight overhead. Use a weight which would cause you to fail at 6-7 reps. Rest 2-3 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Banded Overhead Press", new ArrayList<>(Arrays.asList(7, 9)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(5))),
                12, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Press the weight overhead. Use a weight which would cause you to fail at 6-7 reps. Rest 2-3 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Cheat Lateral Raises", new ArrayList<>(Arrays.asList(8)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))),
                10, 7, 4,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Using a heavy weight to lateral raise, 'cheat' the weight up using momentum. Focus on lowering the weight as slowly as possible. Use a weight which would cause you to fail at 8-9 reps. Rest 2 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Banded Cheat Lateral Raises", new ArrayList<>(Arrays.asList(8)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(5))),
                10, 7, 4,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Using a heavy weight to lateral raise, 'cheat' the weight up using momentum. Focus on lowering the weight as slowly as possible. Use a weight which would cause you to fail at 8-9 reps. Rest 2 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy High Pull", new ArrayList<>(Arrays.asList(7, 8)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))),
                10, 7, 4,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Pull the weight up to your shoulders and rotate outwards leading with your hands not your elbows. Use a weight which would cause you to fail at 8-9 reps. Rest 2 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Rear Delt Row", new ArrayList<>(Arrays.asList(9)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))),
                10, 7, 4,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Row with your elbows pointed out. Use a weight which would cause you to fail at 8-9 reps. Rest 2 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Banded Rear Delt Row", new ArrayList<>(Arrays.asList(9)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(5))),
                10, 7, 4,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Row with your elbows pointed out. Use a weight which would cause you to fail at 8-9 reps. Rest 2 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Banded High Pull", new ArrayList<>(Arrays.asList(7, 8)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(5))),
                10, 7, 4,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Pull the weight up to your shoulders and rotate outwards leading with your hands not your elbows. Use a weight which would cause you to fail at 8-9 reps. Rest 2 minutes between sets.");

        // Back

        exerciseManager.addExercise(0, "Heavy Dead Bar Rows", new ArrayList<>(Arrays.asList(4, 5, 6)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2), A(42))), 12, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Bend over and row bringing your elbows behind your back. Use a weight which would cause you to fail at 6-7 reps. Rest 2-3 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy One Arm Rows", new ArrayList<>(Arrays.asList(4, 5, 6)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(25))), 12, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "In a tripod stance, row bringing one arm behind your back. Use a weight which would cause you to fail at 6-7 reps. Rest 2-3 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Banded Rows", new ArrayList<>(Arrays.asList(4, 5, 6)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(5))), 12, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Bend over and fix the band at your feet and hold in your hands. Row bringing your elbows behind your back. Use a weight which would cause you to fail at 6-7 reps. Rest 2-3 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Trap Shrugs", new ArrayList<>(Arrays.asList(4, 6)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2))), 12, 7, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Holding the weight in your hands shrug up. Use a weight which would cause you to fail at 8-9 reps. Rest 2 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Banded Trap Shrugs", new ArrayList<>(Arrays.asList(4, 6)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(5), A(5, 1))), 12, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Fixing the band at your feet, and holding it in your hands, shrug up. Use a weight which would cause you to fail at 6-7 reps. Rest 2-3 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Weighted Pull Ups", new ArrayList<>(Arrays.asList(4, 5)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 6), A(1, 26))), 12, 5, 5,
                new ArrayList<>(Arrays.asList(1, 2)), "Either hold a dumbbell with your feet, or use a weight belt, perform pull ups. Use a weight which would cause you to fail at 6-7 reps. Rest 2-3 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Lat Pull Downs", new ArrayList<>(Arrays.asList(4, 5)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(11))), 12, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Use a weight which would cause you to fail at 6-7 reps. Rest 2-3 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Banded Lat Pull Downs", new ArrayList<>(Arrays.asList(4, 5)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(5, 6))), 12, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Fix the band overhead and perform lat pull downs. Use a weight which would cause you to fail at 6-7 reps. Rest 2-3 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Wide Rows", new ArrayList<>(Arrays.asList(4, 6, 14)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2), A(42))), 12, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Bend over and row with a wide grip. Use a weight which would cause you to fail at 6-7 reps. Rest 2-3 minutes between sets.");

        // Biceps

        exerciseManager.addExercise(0, "Heavy Bicep Curl", new ArrayList<>(Arrays.asList(11)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2), A(42))), 10, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Use a weight which would cause you to fail at 6-7 reps. Rest 2 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Banded Bicep Curl", new ArrayList<>(Arrays.asList(11)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(5))), 10, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Use a weight which would cause you to fail at 6-7 reps. Rest 2 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Dumbbell Alternating Bicep Curl", new ArrayList<>(Arrays.asList(11)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 10, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Use a weight which would cause you to fail at 6-7 reps. Rest 2 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Weighted Chin Ups", new ArrayList<>(Arrays.asList(11, 5)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(26))), 10, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Use a weight which would cause you to fail at 6-7 reps. Rest 2 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Hammer Curls", new ArrayList<>(Arrays.asList(11)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 10, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Use a half way pronated 'hammer' grip. Use a weight which would cause you to fail at 6-7 reps. Rest 2 minutes between sets.");

        // Triceps

        exerciseManager.addExercise(0, "Heavy Barbell Close Grip Bench Press", new ArrayList<>(Arrays.asList(12)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(2, 8), A(4))), 10, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Bench press with your arms close to your side. Use a weight which would cause you to fail at 6-7 reps. Rest 2 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Dumbbell Close Grip Bench Press", new ArrayList<>(Arrays.asList(12)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 8))), 10, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Bench press with your arms close to your side. Use a weight which would cause you to fail at 6-7 reps. Rest 2 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Banded Close Grip Bench Press", new ArrayList<>(Arrays.asList(12)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(5))), 10, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Bench press with your arms close to your side. Fix the band beneath you. Use a weight which would cause you to fail at 6-7 reps. Rest 2 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Skull Crushers", new ArrayList<>(Arrays.asList(12)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 8), A(2, 8), A(42, 8))), 10, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Lying on the bench, hold the weight and raise your arms overhead, extend your elbows. Use a weight which would cause you to fail at 6-7 reps. Rest 2 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Overhead Triceps Extensions", new ArrayList<>(Arrays.asList(12)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(5, 6), A(10))), 10, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the band or cable fixed behind you, hold it overhead and extend your elbows. Use a weight which would cause you to fail at 6-7 reps. Rest 2 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Weighted Close Grip Push Ups", new ArrayList<>(Arrays.asList(12)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(26), A(5))), 10, -1, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Use a weight which would cause you to fail at 6-7 reps. Rest 2 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Incline Close Grip Press", new ArrayList<>(Arrays.asList(12)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 9), A(2, 9))), 10, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the bench inclined bench press with a close grip. Use a weight which would cause you to fail at 6-7 reps. Rest 2 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Triceps Kickbacks", new ArrayList<>(Arrays.asList(12)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 10, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With weight in both hands, bend over and extend the elbows and kick your arms back. Use a weight which would cause you to fail at 6-7 reps. Rest 2 minutes between sets.");
        exerciseManager.addExercise(0, "Heavy Alternating Kickbacks", new ArrayList<>(Arrays.asList(12)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 10, 5, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With weight in both hands, bend over and alternate extending the elbows and kick your arms back. Use a weight which would cause you to fail at 6-7 reps. Rest 2 minutes between sets.");

        // Hypertrophy

        // Legs

        // Regular

        exerciseManager.addExercise(1, "Back Squat", new ArrayList<>(Arrays.asList(0, 1, 2)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(2, 3), A(3))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the weight on your back, squat down to depth. Avoid leaning forward as you squat down.");
        exerciseManager.addExercise(1, "Front Squat", new ArrayList<>(Arrays.asList(0)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2, 3), A(3))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the weight on your collarbone, squat down to depth.");
        exerciseManager.addExercise(1, "Band Squat", new ArrayList<>(Arrays.asList(0)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(5))), 8, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Fix the band at your feet and wrap it around your shoulders. Squat down to depth.");
        exerciseManager.addExercise(1, "Forward Lunges", new ArrayList<>(Arrays.asList(0)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2, 3), A(3))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Either holding the dumbbells at your side or barbell on your back, step forward into lunges. If your knees are vulnerable step back into the lunge instead.");
        exerciseManager.addExercise(1, "Step Back Lunges", new ArrayList<>(Arrays.asList(0)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2, 3), A(3))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Either holding the dumbbells at your side or barbell on your back, step back into lunges.");
        exerciseManager.addExercise(1, "Goblet Squat", new ArrayList<>(Arrays.asList(0)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(25))), 8, 20, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With one dumbbell held in both hands at your chest, squat down to depth.");
        exerciseManager.addExercise(1, "Elevated Split Squat", new ArrayList<>(Arrays.asList(0, 1, 2)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 8), A(2, 8), A(1, 9), A(2, 9), A(5))),
                8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Elevate one leg behind you on the bench and split squat. Perform on both legs.");
        exerciseManager.addExercise(1, "TKE Split Squat", new ArrayList<>(Arrays.asList(0)),
                false, false, false,
                new ArrayList<>(Arrays.asList( A(5, 1))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Fix the band in front of you and loop around your knee before performing the lunges.");
        exerciseManager.addExercise(1, "Glute/Ham Raises", new ArrayList<>(Arrays.asList(1, 2)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(22))), 8, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Using the glute/ham raise machine, holding a weight if necessary.");
        exerciseManager.addExercise(1, "Glute Kickbacks", new ArrayList<>(Arrays.asList(1)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(20), A(5))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Use the machine or with a band fixed in front of you around your leg.");
        exerciseManager.addExercise(1, "Romanian Deadlifts", new ArrayList<>(Arrays.asList(2)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform deadlifts without lowering all the way to the ground, going as far down as your hamstrings will allow without bending your knees.");
        exerciseManager.addExercise(1, "Deadlifts", new ArrayList<>(Arrays.asList(1, 2, 17)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2))), 8, 8, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Deadlift from the ground.");
        exerciseManager.addExercise(1, "One Leg Romanian Deadlifts", new ArrayList<>(Arrays.asList(2)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform deadlifts on one leg without lowering all the way to the ground, going as far down as your hamstrings will allow without bending your knee. Use a hand to balance.");
        exerciseManager.addExercise(1, "Hip Thrusts", new ArrayList<>(Arrays.asList(1)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With your upper back elevated on a bench if available, position the dumbbells or barbell on your hips and thrust upwards.");
        exerciseManager.addExercise(1, "Kettlebell Swings", new ArrayList<>(Arrays.asList(1, 17)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(25))), 6, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Holding the kettlebell in both hands hinge at the hips bending down. Thrust the weight up using your hips to drive the motion. Think of pulling with your hamstrings and squeezing your glutes together.");
        exerciseManager.addExercise(1, "Nordic Hamstring Curls", new ArrayList<>(Arrays.asList(2)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(8), A(9))), 6, 10, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "In a kneeling position fix your feet behind you and fall forward, controlling your descent using your hamstrings.");
        exerciseManager.addExercise(1, "Banded Hamstring Curls", new ArrayList<>(Arrays.asList(2)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(5))), 6, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Laying prone, fix the band behind you and curl it using your feet.");
        exerciseManager.addExercise(1, "Hamstring Machine Curls", new ArrayList<>(Arrays.asList(2)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(14))), 8, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Curl your legs using the machine");
        exerciseManager.addExercise(1, "Quad Extensions", new ArrayList<>(Arrays.asList(0)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(13))), 8, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Extend your quads using the machine. Do not do this exercise if your knees are vulnerable.");
        exerciseManager.addExercise(1, "Leg Press", new ArrayList<>(Arrays.asList(0, 1, 2)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(12))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Use the leg press machine.");
        exerciseManager.addExercise(1, "Adductor Machine", new ArrayList<>(Arrays.asList(18)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(15))), 6, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Use the adductor machine.");
        exerciseManager.addExercise(1, "Abductor Machine", new ArrayList<>(Arrays.asList(19)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(16))), 6, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Use the abductor machine.");
        exerciseManager.addExercise(1, "Banded One Leg Adduction", new ArrayList<>(Arrays.asList(18)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(5))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Fix the band at your side and wrap it around your knee performing adduction. Perform on both legs.");
        exerciseManager.addExercise(1, "Banded One Leg Abduction", new ArrayList<>(Arrays.asList(19)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(5))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Fix the band at your side and wrap it around your knee performing abduction. Perform on both legs.");
        exerciseManager.addExercise(1, "Side Goblet Squats", new ArrayList<>(Arrays.asList(18, 19)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Holding the dumbbell in goblet position alternate side squats.");
        exerciseManager.addExercise(1, "Dumbbell Crab Walk", new ArrayList<>(Arrays.asList(18, 19)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Holding the dumbbell in goblet position squat down and crab walk side to side.");
        exerciseManager.addExercise(1, "Banded Crab Walk", new ArrayList<>(Arrays.asList(19)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(5))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the band wrapped around both legs squat down and crab walk side to side.");
        exerciseManager.addExercise(1, "Banded Step Out", new ArrayList<>(Arrays.asList(18)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(5))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the band fixed to your side wrap it around the other leg and step out. Perform on both legs.");
        exerciseManager.addExercise(1, "Calf Raises", new ArrayList<>(Arrays.asList(20)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2))), 8, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Holding dumbbells in your handss or the barbell on your back, raise up with your calves.");
        exerciseManager.addExercise(1, "Seated Calf Raise Machine", new ArrayList<>(Arrays.asList(20)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(17))), 8, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Use the calf raise machine.");
        exerciseManager.addExercise(1, "One Leg Calf Raises", new ArrayList<>(Arrays.asList(20)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2))), 10, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With dumbbells in your hands or barbell on your back, calf raise on one leg. Balance with one hand.");
        exerciseManager.addExercise(1, "Dumbbell Thrusters", new ArrayList<>(Arrays.asList(0, 7)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 10, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Front squatting two dumbbells, squat down and thrust upwards, overhead pressing the dumbbells at the end of each rep.");

        // Super Set

        exerciseManager.addExercise(1, "Back Squat/Scapula Pull Super Set", new ArrayList<>(Arrays.asList()),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0))), 10, 16, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "10 reps of back squat followed by 6 reps of hanging scapula pulls");
        exerciseManager.addExercise(1, "Elevated Split Squat/Plyo Super Set",
                new ArrayList<>(Arrays.asList(0, 1, 2)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 8), A(1, 9), A(2, 8), A(2, 9), A(5, 8))),
                10, 16, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "8 reps of elevated split squats followed by 8 reps of split squat jumps. Perform on both legs.");
        exerciseManager.addExercise(1, "Goblet Squat/Plyo Super Set", new ArrayList<>(Arrays.asList(0, 1, 2)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(25), A(5))), 10, 20, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "12 reps of goblet squats followed by 8 reps of squat jumps.");
        exerciseManager.addExercise(1, "Back Squat/Squeeze Super Set", new ArrayList<>(Arrays.asList(0)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2), A(3))), 10, 16, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "10 reps of back squats followed by 6 reps of quad flexes, holding for 5 seconds each time.");
        exerciseManager.addExercise(1, "Leg Press/Squeeze Super Set", new ArrayList<>(Arrays.asList(0)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(12))), 10, 16, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "10 reps of leg press followed by 6 reps of quad flexes, holding for 5 seconds each time.");
        exerciseManager.addExercise(1, "Adductor/Abductor Super Set", new ArrayList<>(Arrays.asList(18, 19)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(15, 16), A(5))), 10, 20, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "10 reps of adduction followed by 10 reps of abduction, if using bands perform on both legs.");
        exerciseManager.addExercise(1, "Calf Raise/Tibialis Raise Super Set", new ArrayList<>(Arrays.asList(20)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 8, 20, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "10 reps of calf raises followed by 10 reps of tibialis raises.");

        // Drop Set

        exerciseManager.addExercise(1, "Hamstring Curl Drop Set", new ArrayList<>(Arrays.asList(2)),
                false, true, false,
                new ArrayList<>(Arrays.asList(A(0), A(14))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Curl with the hamstring curl machine.");
        exerciseManager.addExercise(1, "Quad Extension Drop Set", new ArrayList<>(Arrays.asList(0)),
                false, true, false,
                new ArrayList<>(Arrays.asList(A(0), A(13))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Extend your legs using the quad extension machine.");
        exerciseManager.addExercise(1, "Goblet Squat Drop Set", new ArrayList<>(Arrays.asList(0, 1, 2)),
                false, true, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 8, 20, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With one dumbbell held in both hands at your chest, squat down to depth.");
        exerciseManager.addExercise(1, "Single Leg Hip Thrust Drop Set", new ArrayList<>(Arrays.asList(1, 2)),
                false, true, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With your upper back elevated on a bench if available, position the dumbbells or barbell on your hips and thrust upwards with only one leg.");

        // Special Rep

        exerciseManager.addExercise(1, "Paused Back Squats", new ArrayList<>(Arrays.asList(0, 1, 2)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(2), A(3))), 8, 8, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the weight on your back squat down. Pause at the bottom of each rep.");
        exerciseManager.addExercise(1, "Paused Front Squats", new ArrayList<>(Arrays.asList(0, 1, 2)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2), A(3))), 8, 8, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the weight on your collarbone squat down. Pause at the bottom of each rep.");
        exerciseManager.addExercise(1, "1 1/2 Back Squats", new ArrayList<>(Arrays.asList(0, 1, 2)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(2), A(3))), 8, 8, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the weight on your back squat down. At the bottom of each rep go half way up and then back down.");
        exerciseManager.addExercise(1, "1 1/2 Front Squats", new ArrayList<>(Arrays.asList(0, 1, 2)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2), A(3))), 8, 8, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the weight on your collarbone squat down. At the bottom of each rep go half way up and then back down.");
        exerciseManager.addExercise(1, "1 1/2 Hip Thrusts", new ArrayList<>(Arrays.asList()),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2))), 8, 8, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "At the bottom of each rep go half way up and then back down.");
        exerciseManager.addExercise(1, "1 1/2 Step Back Lunges", new ArrayList<>(Arrays.asList(0)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2, 3), A(3))), 8, 8, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "At the bottom of each rep go half way up and then back down.");
        exerciseManager.addExercise(1, "1 1/2 Goblet Squat", new ArrayList<>(Arrays.asList(0)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(25))), 8, 10, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "At the bottom of each rep go half way up and then back down.");
        exerciseManager.addExercise(1, "1 1/2 Elevated Split Squat", new ArrayList<>(Arrays.asList(0, 1, 2)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1, 8), A(2, 8), A(1, 9), A(2, 9), A(5))),
                8, 8, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Elevate one leg behind you on the bench and split squat. Perform on both legs. At the bottom of each rep go half way up and then back down.");

        // Chest

        // Regular

        exerciseManager.addExercise(1, "Dumbbell Bench Press", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 8), A(1, 9))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Lying on a bench, press the dumbbells up. Keep you scapula depressed as you press and your shoulders in a safe position.");
        exerciseManager.addExercise(1, "Barbell Bench Press", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(2, 8), A(2, 9), A(4))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Lying on a bench, press the barbell up. Keep you scapula depressed as you press and your shoulders in a safe position.");
        exerciseManager.addExercise(1, "Dumbbell Incline Bench Press", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 9))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Set the bench at 30-45 degrees and press.");
        exerciseManager.addExercise(1, "Barbell Incline Bench Press", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(2, 9), A(4))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Set the bench at 30-45 degrees and press.");
        exerciseManager.addExercise(1, "Weighted Dips", new ArrayList<>(Arrays.asList(3, 12)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(7))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Hold the dumbbell with your feet or with a weight belt and dip.");
        exerciseManager.addExercise(1, "Cavaliere Crossovers", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(26), A(25), A(5))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Hold the weight in one arm at your side and use your chest to pull your arm across your body. Perform on both arms.");
        exerciseManager.addExercise(1, "Floor Flies", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Lying on the floor perform dumbbell flies. With the weight positioned above your chest, spread your arms apart, lowering the weight to the ground and then bringing it back up to the starting position.");
        exerciseManager.addExercise(1, "Cross Body Raise", new ArrayList<>(Arrays.asList(3, 9)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(5))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform front raises with a dumbbell or band lifting across your body instead of in front.");
        exerciseManager.addExercise(1, "Upper Chest Pullover", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(42), A(5))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Lying flat on the ground, hold the dumbbell or band overhead and pull your arms straight over your chest pointing your elbows inwards.");
        exerciseManager.addExercise(1, "Dumbbell Sunrise Sunset", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 8), A(1, 9))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Laying on the bench with dumbbells in fly position bring them in a circular motion up above your head and then down below your chest.");
        exerciseManager.addExercise(1, "Weighted Push Ups", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(26))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "With the weight on your back perform push ups.");
        exerciseManager.addExercise(1, "Banded Push Ups", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(5))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "With the band around your back fix it under both hands and perform push ups.");
        exerciseManager.addExercise(1, "Cable Crossovers", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(10))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Step in between the cables and grab one with each arm. Cross your arms across your body and repeat.");
        exerciseManager.addExercise(1, "Cable Hi-Low Crossovers", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(10))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Step in between the cables and grab one with each arm. Cross your arms from high to low across your body and repeat.");
        exerciseManager.addExercise(1, "Cable Low-Hi Crossovers", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(10))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Step in between the cables and grab one with each arm. Cross your arms from low to high across your body and repeat.");
        exerciseManager.addExercise(1, "Single Arm Cable Crossovers", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(10))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the cable at your side grab it with one arm and pull across your body then repeat. Perform on both sides.");
        exerciseManager.addExercise(1, "Cable Chest Saw", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(10))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the cable at your side push the cable directly across your body over your chest. Perform on both sides.");
        exerciseManager.addExercise(1, "Cable Ground and Pound", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(10))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Kneeling on the ground with the cables in fly position, press down to the ground one arm at a time.");
        exerciseManager.addExercise(1, "Single Arm Band Crossovers", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(5))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the band fixed at your side grab it with one arm and pull across your body then repeat. Perform on both sides.");
        exerciseManager.addExercise(1, "Band Chest Saw", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(5))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the band fixed at your side push the band directly across your body over your chest. Perform on both sides.");
        exerciseManager.addExercise(1, "Single Arm Band Crossovers", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(10))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the band fixed at your side grab it with one arm and pull across your body then repeat. Perform on both sides.");
        exerciseManager.addExercise(1, "Chest Press", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(19))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Use the chest press machine.");
        exerciseManager.addExercise(1, "Pec Fly Machine", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(18))), 8, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Use the pec dec or pec fly machine.");
        exerciseManager.addExercise(1, "Landmine Press", new ArrayList<>(Arrays.asList(3, 9)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(41), A(2))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Landmine press using the landmine press or push a barbell into a corner and use as a landmine.");

        // Super Set

        exerciseManager.addExercise(1, "Cavaliere Crossover/UCV Raise Super Set", new ArrayList<>(Arrays.asList(3)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(5))), 10, 20, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "12 reps of cavaliere crossovers followed by 8 reps of UCV raises. Perform on both arms");
        exerciseManager.addExercise(1, "Bench Press Crossover Super Set", new ArrayList<>(Arrays.asList(3)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 8, 10), A(1, 9, 10), A(2, 8, 10), A(2, 9, 10), A(4, 10))),
                8, 16, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "8 reps of bench press followed by 8 reps of cable crossovers.");
        exerciseManager.addExercise(1, "Iron Cross DB Push Up/Floor Fly Super Set", new ArrayList<>(Arrays.asList(3)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0))), 10, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform iron cross DB Pushups to failure followed by floor flies to failure.");
        exerciseManager.addExercise(1, "Floor Fly/Press Super Set", new ArrayList<>(Arrays.asList(3)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(18, 19))), 10, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform floor flies until failure followed by floor press until failure. Dumbbell can be substituted with machine fly and machine press if available.");
        exerciseManager.addExercise(1, "Bench Press/Squeeze Super Set", new ArrayList<>(Arrays.asList(3)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 8), A(1, 9), A(2, 8), A(2, 9) )), 10, 16, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "10 reps of bench press followed by 6 reps of chest squeezes, holding for 5 seconds each time.");
        exerciseManager.addExercise(1, "Cable Crossover/Squeeze Super Set", new ArrayList<>(Arrays.asList(3)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(10))), 10, 16, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "10 reps of cable crossovers followed by 6 reps of chest squeezes, holding for 5 seconds each time.");
        exerciseManager.addExercise(1, "Weighted Push Up Mega Set", new ArrayList<>(Arrays.asList(3)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 8), A(26, 8))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Decline push ups to failure followed by flat push ups to failure followed by incline push ups to failure.");

        // Drop Set

        exerciseManager.addExercise(1, "Chest Press Drop Set", new ArrayList<>(Arrays.asList(3)),
                false, true, false,
                new ArrayList<>(Arrays.asList(A(0), A(19))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Use the chest press machine as a drop set");
        exerciseManager.addExercise(1, "Weighted Push Up Drop Set", new ArrayList<>(Arrays.asList(3)),
                false, true, false,
                new ArrayList<>(Arrays.asList(A(26), A(1))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the weight on your back perform push ups as a drop set.");
        exerciseManager.addExercise(1, "Weighted Dip Drop Set", new ArrayList<>(Arrays.asList(3)),
                false, true, false,
                new ArrayList<>(Arrays.asList(A(0), A(7, 1), A(7, 26))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Holding the weight with your feet or with a weight belt perform dips as a drop set.");
        exerciseManager.addExercise(1, "Bench Press Drop Set", new ArrayList<>(Arrays.asList(3)),
                false, true, false,
                new ArrayList<>(Arrays.asList(A(0), A(8, 1), A(9, 1), A(2, 8), A(2, 9))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform bench press (barbell or dumbbell) as a drop set.");
        exerciseManager.addExercise(1, "Floor Fly Drop Set", new ArrayList<>(Arrays.asList(3)),
                false, true, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform floor flies as a drop set.");

        // Special Rep

        exerciseManager.addExercise(1, "Floor Press & Fly", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Press the weight up off the ground and slowly lower it as a fly.");
        exerciseManager.addExercise(1, "Alternating Pause DB Bench Press", new ArrayList<>(Arrays.asList(3)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1, 8), A(1, 9))), 8, 10, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform an alternating DB bench press holding the arm not currently performing the rep in the lowered position.");
        exerciseManager.addExercise(1, "One Arm Bench Press", new ArrayList<>(Arrays.asList(3)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1, 8), A(1, 9))), 8, 10, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "");
        exerciseManager.addExercise(1, "Alternating Pause DB Incline Bench Press", new ArrayList<>(Arrays.asList(3)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1, 9))), 8, 10, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform an alternating DB incline bench press holding the arm not currently performing the rep in the lowered position.");
        exerciseManager.addExercise(1, "Pause Bench Press", new ArrayList<>(Arrays.asList(3)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1, 8), A(1, 9), A(2, 8), A(2,9), A(4))), 8, 10, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform a bench press pausing at the bottom of the rep.");
        exerciseManager.addExercise(1, "Alternating Paused Floor Flies", new ArrayList<>(Arrays.asList(3)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 8, 10, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform floor flies alternating arms, while one arm is performing the rep, pause the other arm at the bottom of the rep.");
        exerciseManager.addExercise(1, "Alternating Paused Cable Crossovers", new ArrayList<>(Arrays.asList(3)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(10))), 8, 10, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform cable crossovers alternating arms, while one arm is performing the rep, pause the other arm at the bottom of the rep.");

        // Shoulders

        // Regular

        exerciseManager.addExercise(1, "Barbell Overhead Press", new ArrayList<>(Arrays.asList(7, 9)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(2), A(42))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Press the weight overhead, make sure to move your head underneath the weight as you press overhead.");
        exerciseManager.addExercise(1, "Dumbbell Overhead Press", new ArrayList<>(Arrays.asList(7, 9)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Press the weight overhead.");
        exerciseManager.addExercise(1, "Push Press", new ArrayList<>(Arrays.asList(9)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2), A(42))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform overhead press but use your legs to provide additional momentum to press the weight up overhead.");
        exerciseManager.addExercise(1, "Scoop Press", new ArrayList<>(Arrays.asList(9)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Bring arms down into a hammer curl position before scooping up and pressing.");
        exerciseManager.addExercise(1, "Z-Press", new ArrayList<>(Arrays.asList(7)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Overhead press but sitting on the ground.");
        exerciseManager.addExercise(1, "Figure Eights", new ArrayList<>(Arrays.asList(8, 9)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(26))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Hold the weight in front of you and move in a figure eight motion.");
        exerciseManager.addExercise(1, "Front Raises", new ArrayList<>(Arrays.asList(9)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(26))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With dumbbells in both hands or holding a plate, raise the weight up and in front of you keeping your arms straight.");
        exerciseManager.addExercise(1, "Cheat Lateral Raises", new ArrayList<>(Arrays.asList(8)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform heavy lateral raises using momentum to 'cheat' the weight up and focus on lowering it as slowly as possible.");
        exerciseManager.addExercise(1, "Lateral Raises", new ArrayList<>(Arrays.asList(8)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Holding dumbbells in both hands, raise the weight out to your sides. Try to keep your shoulders depressed and use only your side delt to drive the movement and not your traps.");
        exerciseManager.addExercise(1, "One Arm Lying Lateral Raises", new ArrayList<>(Arrays.asList(8)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 9))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Lying down on an incline bench perform lateral raises, raising the weight up to your side.");
        exerciseManager.addExercise(1, "One Arm Leaning Lateral Raises", new ArrayList<>(Arrays.asList(8)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Leaning hanging from a pole perform one arm lateral raises. Perform on both sides.");
        exerciseManager.addExercise(1, "Banded Lateral Raises", new ArrayList<>(Arrays.asList(8)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(5))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Holding bands in both hands with it fixed at your feet, raise the weight out to your sides. Try to keep your shoulders depressed and use only your side delt to drive the movement and not your traps.");
        exerciseManager.addExercise(1, "One Arm Banded Lateral Raises", new ArrayList<>(Arrays.asList()),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(5))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the band fixed at your foot, hold it in one hand and raise it up to your side.");
        exerciseManager.addExercise(1, "High Pull", new ArrayList<>(Arrays.asList(8, 7)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Go heavier than you would with lateral raises, pull the weight straight up and rotate outwards at the top driving hands up before elbows.");
        exerciseManager.addExercise(1, "Halos", new ArrayList<>(Arrays.asList(7)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(26))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Hold the weight overhead moving it in a circular motion as if making a halo above your head.");
        exerciseManager.addExercise(1, "Pendulum Swings", new ArrayList<>(Arrays.asList(8)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(26), A(5))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Hold the weight in goblet position and swing the weight like a pendulum side to side focusing on lowering it slowly.");
        exerciseManager.addExercise(1, "Stir the Pot", new ArrayList<>(Arrays.asList(9)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(26))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Hold the weight in goblet position and 'stir' the weight around in circles in front of you.");
        exerciseManager.addExercise(1, "Jammer Press", new ArrayList<>(Arrays.asList(7)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(38))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Use the jammer machine and press the weight overhead quickly.");
        exerciseManager.addExercise(1, "Rear Delt Raises", new ArrayList<>(Arrays.asList(10)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Hold weight in both hands, bend over and raise them back with your elbows flared out.");
        exerciseManager.addExercise(1, "One Arm Rear Delt Raises", new ArrayList<>(Arrays.asList(10)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(25))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Hold weight in one hand, bend over and raise them back with your elbows flared out. Try to lean down and get an extra stretch on your delt.");
        exerciseManager.addExercise(1, "Rear Delt Flies", new ArrayList<>(Arrays.asList(10)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(10), A(5))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the cables or the band fixed above and in front of you perform a reverse fly spreading your arms back.");
        exerciseManager.addExercise(1, "L-Raises", new ArrayList<>(Arrays.asList(8, 9)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(5))), 6, 10, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform a lateral raise with one arm and a front raise with the other, making an 'L' with your arms.");
        exerciseManager.addExercise(1, "Kneeling Triple Raise", new ArrayList<>(Arrays.asList(7, 8, 9, 10)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 8, 8, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Kneeling down perform alternating lateral raises, then front raises, then rear raises, that counts as one rep.");
        exerciseManager.addExercise(1, "Arnold Press", new ArrayList<>(Arrays.asList(7)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 7, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Start with the dumbbells held in front of your chest and rotate outwards as you press up into an overhead press motion.");
        exerciseManager.addExercise(1, "W-Raise", new ArrayList<>(Arrays.asList(10, 14, 15, 16)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(5))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Bent over at a 45 degree angle, pull the weight or band up making a 'W' with your arms. Keep your scapula depressed and think of squeezing your mid back together.");
        exerciseManager.addExercise(1, "Y-Raise", new ArrayList<>(Arrays.asList(14, 15, 16)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(5))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Bent over at a 45 degree angle, pull the weight or band up making a 'Y' with your arms. Keep your scapula depressed.");
        exerciseManager.addExercise(1, "Door Opens", new ArrayList<>(Arrays.asList(16)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(10), A(5))), 8, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the band fixed at your side and held in one arm rotate outwards as if you are opening a door. Perform on both sides.");
        exerciseManager.addExercise(1, "Prone Press", new ArrayList<>(Arrays.asList(14, 15, 16)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(24))), 6, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Lying prone, lift your arms off the ground and perform an overhead press motion, try with nothing before moving to weights.");
        exerciseManager.addExercise(1, "Angels and Devils", new ArrayList<>(Arrays.asList(14, 15, 16)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(24))), 6, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Lying prone, stretch your arms outwards and bring them up above your head and then back around behind your back, try with nothing before moving to weights.");
        exerciseManager.addExercise(1, "Face Pulls", new ArrayList<>(Arrays.asList(10, 14, 15, 16)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(10), A(5))), 6, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Fix the band or cable in front of you at face level. Hold with both hands and pull your hands back behind your head, think about making your hands beat your elbows behind your head. Keep your scapula depressed.");
        exerciseManager.addExercise(1, "Overhead Extension Face Pulls", new ArrayList<>(Arrays.asList(10, 14, 15, 16)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(10), A(5))), 6, 15, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Fix the band or cable in front of you at face level. Hold with both hands and pull your hands back behind your head then raise your hands up in an overhead press motion.");
        exerciseManager.addExercise(1, "Sword Raises", new ArrayList<>(Arrays.asList(14, 15, 16)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(10), A(5))), 8, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the band or cable fixed below and at your side in one hand draw your arm across your body and overhead as if you are drawing a sword from it's sheath.");
        exerciseManager.addExercise(1, "Urlachers", new ArrayList<>(Arrays.asList(10, 16)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Leaning slightly over with dumbbells held at your waist pull the weight up making a 'W' with your arms as you rotate up.");
        exerciseManager.addExercise(1, "Extended One Arm Rear Delt Row", new ArrayList<>(Arrays.asList(10)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(10), A(5))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform a one arm rear delt row with the cable or band fixed in front and across your body. Try to get an extra stretch at the end of the rep by letting your body rotate in the direction of the band.");
        exerciseManager.addExercise(1, "X-Man Cross", new ArrayList<>(Arrays.asList(6, 14, 15)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(10), A(5))), 6, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the band or cables fixed in front of you hold with both arms and pull backwards making an 'X' with your arms straight out as you pull back.");
        exerciseManager.addExercise(1, "Hanging Scapula Pulls", new ArrayList<>(Arrays.asList(15)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 5, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Hanging from the bar, allow your scapula to relax hanging from the bar. To perform a rep, retract your scapula, bringing your shoulders down and back. Think about 'scooping' your shoulders down in order to pring your chest closer to the bar.");
        exerciseManager.addExercise(1, "DB Power Clean Over", new ArrayList<>(Arrays.asList(7)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Using one dumbbell, start with it on the ground at one side and clean it up to your shoulder and the press it over your shoulder bringing it down to the ground on the other side of your body.");

        // Super Set

        exerciseManager.addExercise(1, "L-Raise/OHP Super Set", new ArrayList<>(Arrays.asList(7, 8, 9)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform L-Raises to failure followed by overhead press to failure.");
        exerciseManager.addExercise(1, "Scoop Press/High Pull/Plate Eights/Press Out Ultra Set", new ArrayList<>(Arrays.asList(7, 8, 9, 10)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 10, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform scoop press to failure followed by high pull to failure followed by plate eights to failure followed by press outs to failure.");
        exerciseManager.addExercise(1, "Cheat Lateral Raise/High Pull Super Set", new ArrayList<>(Arrays.asList(7, 8)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform lateral raises to failure followed by high pulls to failure");
        exerciseManager.addExercise(1, "Lateral Raises/ISO Hold Super Set", new ArrayList<>(Arrays.asList(8)),
                true, false, false,
                new ArrayList<>(Arrays.asList( A(1), A(1,5))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform lateral raises to failure followed by an isometric squeeze against bands or a doorway.");
        exerciseManager.addExercise(1, "W-Raise/Lateral Raise Super Set", new ArrayList<>(Arrays.asList(8, 14, 15)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform W-Raises to failure followed by lateral raises to failure.");

        // Drop Set

        exerciseManager.addExercise(1, "Lateral Raise Drop Set", new ArrayList<>(Arrays.asList(8)),
                false, true, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With dumbbells held in both hands raise them side ways. Perform as a drop set.");
        exerciseManager.addExercise(1, "Front Raise Drop Set", new ArrayList<>(Arrays.asList(9)),
                false, true, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With dumbbells held in both hands raise your arms out in front of you keeping your arms straight. Perform as a drop set.");
        exerciseManager.addExercise(1, "Rear Raise Drop Set", new ArrayList<>(Arrays.asList(10)),
                false, true, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With dumbbells held in both hands, bend over, raise your arms back in a reverse fly. Perform as a drop set.");
        exerciseManager.addExercise(1, "Overhead Press Drop Set", new ArrayList<>(Arrays.asList(7)),
                false, true, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2), A(42))), 8, 16, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Overhead press and perform as a drop set.");
        exerciseManager.addExercise(1, "L-Raise Drop Set", new ArrayList<>(Arrays.asList(8, 9)),
                false, true, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform a lateral raise with one arm and a front raise with the other, making an 'L' with your arms.");

        // Special Rep

        exerciseManager.addExercise(1, "DB Curl & Press", new ArrayList<>(Arrays.asList(9, 11)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Curl the weight up and then press overhead.");
        exerciseManager.addExercise(1, "Face Pull & Raise", new ArrayList<>(Arrays.asList(14, 15, 16)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(5), A(10))), 6, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform a face pull raising your arms overhead at the top of each rep.");
        exerciseManager.addExercise(1, "Pause Lateral Raises", new ArrayList<>(Arrays.asList(8)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(5))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform lateral raises pausing at the top of each rep. Keep your scapula depressed.");
        exerciseManager.addExercise(1, "Pause Front Raises", new ArrayList<>(Arrays.asList(9)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(5))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform front raises pausing at the top of each rep.");
        exerciseManager.addExercise(1, "Pause Rear Raises", new ArrayList<>(Arrays.asList(10)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(5))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform rear raises pausing at the top of each rep.");
        exerciseManager.addExercise(1, "Alternating Hold Lateral Raises", new ArrayList<>(Arrays.asList(8)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform alternating lateral raises but while one arm performs the raise the other is paused at the top of the rep.");
        exerciseManager.addExercise(1, "Alternating Hold Rear Raises", new ArrayList<>(Arrays.asList(10)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform alternating rear raises but while one arm performs the raise the other is paused at the top of the rep.");
        exerciseManager.addExercise(1, "Alternating Hold Front Raises", new ArrayList<>(Arrays.asList(9)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform alternating front raises but while one arm performs the raise the other is paused at the top of the rep.");
        exerciseManager.addExercise(1, "Alternating Hold DB Overhead Press", new ArrayList<>(Arrays.asList(7)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 6, 10, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform overhead press but while one arm performs the press the other is paused in the extended position.");
        exerciseManager.addExercise(1, "1 1/2 Lateral Raises", new ArrayList<>(Arrays.asList(8)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform each rep as a 1 & 1/2 rep, at the top of each rep go halfway down coming back up before going down again.");
        exerciseManager.addExercise(1, "1 1/2 Rear Raises", new ArrayList<>(Arrays.asList(10)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform each rep as a 1 & 1/2 rep, at the top of each rep go halfway down coming back up before going down again.");
        exerciseManager.addExercise(1, "1 1/2 Front Raises", new ArrayList<>(Arrays.asList(9)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform each rep as a 1 & 1/2 rep, at the top of each rep go halfway down coming back up before going down again.");

        // Back

        // Regular

        exerciseManager.addExercise(1, "Bent Over Rows", new ArrayList<>(Arrays.asList(4, 6)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2), A(42))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Bent over, row the weight up bringing your elbows back behind your waist.");
        exerciseManager.addExercise(1, "One Arm Row", new ArrayList<>(Arrays.asList(4, 6)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(25))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "In a tripod stance, row one arm behind your back. Perform on both sides.");
        exerciseManager.addExercise(1, "Shrug Rows", new ArrayList<>(Arrays.asList(4, 6)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Bent at a slight angle first shrug the weight up and then complete the row motion.");
        exerciseManager.addExercise(1, "Weighted Pull Ups", new ArrayList<>(Arrays.asList(4, 5)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 6), A(26, 6))), 6, 10, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Holding a dumbbell with your feet or with a weight belt, perform pull ups.");
        exerciseManager.addExercise(1, "Trap Shrugs", new ArrayList<>(Arrays.asList(6)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2), A(42))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Holding weight at your sides shrug it up. Think about bringing your shoulders to your ears.");
        exerciseManager.addExercise(1, "One Arm Twisting Trap Shrugs", new ArrayList<>(Arrays.asList(6)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(5))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Fix the band below you and shrug as you twist up. Perform on both arms.");
        exerciseManager.addExercise(1, "Lat Pull Downs", new ArrayList<>(Arrays.asList(4, 5)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(11))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Pull downs using the lat pull down machine");
        exerciseManager.addExercise(1, "Banded Pull Downs", new ArrayList<>(Arrays.asList(4, 5)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(5, 6))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Fix the band above you and perform pull downs and pull down as you would at a lat pull down station");
        exerciseManager.addExercise(1, "Straight Arm Push Downs", new ArrayList<>(Arrays.asList(5)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(10), A(5, 6))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Fix the cable or band overhead and hold it with both arms straight in front of you, bring your arms straight down to your waist keeping your scapula depressed through the motion.");
        exerciseManager.addExercise(1, "Cable Rows", new ArrayList<>(Arrays.asList(4, 5, 6)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(10))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the cables in front of you grab them and row.");
        exerciseManager.addExercise(1, "Band Rows", new ArrayList<>(Arrays.asList(4, 5, 6)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(5))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Fix the band in front of you grab it and row.");
        exerciseManager.addExercise(1, "One Arm Cable Rows", new ArrayList<>(Arrays.asList(4, 5, 6)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(10))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the cable in front of you row with one arm. Let your body twist to get an extra stretch on your back at the end of each rep.");
        exerciseManager.addExercise(1, "One Arm Band Rows", new ArrayList<>(Arrays.asList(4, 5, 6)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(5))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the band fixed in front of you row with one arm. Let your body twist to get an extra stretch on your back at the end of each rep.");
        exerciseManager.addExercise(1, "X-Man Pull Downs", new ArrayList<>(Arrays.asList(5)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(10), A(5, 6))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the cable or band fixed above you, hold the ends with your arms out and overhead, making an 'Y' with your and perform pull downs.");
        exerciseManager.addExercise(1, "Close Grip Cable Row", new ArrayList<>(Arrays.asList(4, 6)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(10))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the cables in front of you grab them with a close grip and row.");
        exerciseManager.addExercise(1, "Close Grip Band Row", new ArrayList<>(Arrays.asList(4, 6)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(5))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the band fixed in front of you grab it with a close grip and row.");
        exerciseManager.addExercise(1, "Wide Row", new ArrayList<>(Arrays.asList(14, 6)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2), A(42))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Use a wide grip and perform rows.");
        exerciseManager.addExercise(1, "Zeus Pull Downs", new ArrayList<>(Arrays.asList(5)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(10), A(5, 6))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "In a kneeling position pull the cable/band from a close grip position to your chest.");
        exerciseManager.addExercise(1, "Chest Supported Rows", new ArrayList<>(Arrays.asList(4, 6)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 9), A(2, 9), A(42, 9))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Lay prone on an incline bench at a 30-45 degree angle, and row the weight up to your chest.");
        exerciseManager.addExercise(1, "Low Back Raise Rows", new ArrayList<>(Arrays.asList(4, 5, 6, 17)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 22), A(2, 22), A(42, 22))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "On the glute ham raise hold the dumbbells and perform a raise before each row.");
        exerciseManager.addExercise(1, "Dumbbell Pullovers", new ArrayList<>(Arrays.asList(5)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 8), A(1, 9), A(42, 8), A(42, 9))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Laying back down on a bench hold the weight overhead in both hands and bring it over your chest with your elbows pointed out.");
        exerciseManager.addExercise(1, "Bench Reverse Hyperextension", new ArrayList<>(Arrays.asList(17)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(8), A(9))), 6, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Lying prone on a bench, raise your legs up behind you, flexing your glutes and low back.");
        exerciseManager.addExercise(1, "Supermans", new ArrayList<>(Arrays.asList(17)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Lying prone on the ground, perform reps by lifting your legs and chest off the ground together. Think of superman flying through the air.");
        exerciseManager.addExercise(1, "T-Raises", new ArrayList<>(Arrays.asList(10, 14, 15)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(10), A(5))), 6, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Bent over, holding the dumbbells, cables, or band in both hands make a 'T' pose with your arms, pulling back.");
        exerciseManager.addExercise(1, "Farmer's Carries", new ArrayList<>(Arrays.asList(13)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 5, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Carrying the weight in both hands walk back and forth until you can't hold it anymore.");
        exerciseManager.addExercise(1, "Dead Hang", new ArrayList<>(Arrays.asList(13)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Keeping your scapula depressed, and tension throughout your body, hang from the bar until failure.");
        exerciseManager.addExercise(1, "Wrist Curl", new ArrayList<>(Arrays.asList(13)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 6, 16, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Curl with your wrists.");

        // Super Set

        exerciseManager.addExercise(1, "Straight Arm Push Downs/Pull Downs Super Set", new ArrayList<>(Arrays.asList(4, 5)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(5), A(10))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform straight arm push downs to failure followed by pull downs to faliure");
        exerciseManager.addExercise(1, "Row/Squeeze Super Set", new ArrayList<>(Arrays.asList(4, 5, 6)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2), A(10), A(5), A(42))), 8, 16, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "10 reps of the row followed by 6 reps of back squeezes, held for 5 seconds each.");
        exerciseManager.addExercise(1, "Trap Shrug/Pull Down Super Set", new ArrayList<>(Arrays.asList(4, 5, 6)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 11), A(2, 11), A(1, 5, 6))), 8, 20, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "10 reps of trap shrugs followed by 10 reps of lat pull downs.");

        // Drop Set

        exerciseManager.addExercise(1, "Straight Arm Push Down Drop Set", new ArrayList<>(Arrays.asList(5)),
                false, true, false,
                new ArrayList<>(Arrays.asList(A(0), A(10), A(5, 6))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Fix the cable or band overhead and hold it with both arms straight in front of you, bring your arms straight down to your waist keeping your scapula depressed through the motion.");
        exerciseManager.addExercise(1, "Pull Down Drop Set", new ArrayList<>(Arrays.asList(5)),
                false, true, false,
                new ArrayList<>(Arrays.asList(A(0), A(5, 6), A(11))), 8, 16, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Use the lat pull down machine and perform as a drop set.");
        exerciseManager.addExercise(1, "Row Drop Set", new ArrayList<>(Arrays.asList(4, 6)),
                false, true, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2), A(42), A(5))), 8, 16, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform bent over rows as a drop set.");
        exerciseManager.addExercise(1, "Trap Shrug Drop Set", new ArrayList<>(Arrays.asList(6)),
                false, true, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2), A(42), A(5))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform trap shrugs as a drop set.");
        exerciseManager.addExercise(1, "Weighted Pull Up Drop Set", new ArrayList<>(Arrays.asList(4, 5)),
                false, true, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 6), A(26, 6))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Holding the weight with your feet or using a weight belt, perform pull ups as a drop set. Go to body weight or assisted pull ups if necessary.");


        // Special Rep

        exerciseManager.addExercise(1, "Gorilla Row", new ArrayList<>(Arrays.asList(4, 6)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "In a 'tripod' stance use one heavy dumbbell set in front of you and alternate sides rowing up and the setting the dumbbell down before rowing on the other side.");
        exerciseManager.addExercise(1, "Alternating Pause Row", new ArrayList<>(Arrays.asList(4, 5, 6)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(5))), 6, 10, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform alternating rows but while one arm rows the other is paused at the top of the rep.");
        exerciseManager.addExercise(1, "Pause Rows", new ArrayList<>(Arrays.asList(4, 5, 6)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2), A(5), A(42), A(10))), 8, 10, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform rows pausing at the top of each rep.");
        exerciseManager.addExercise(1, "Pause Weighted Pull Ups", new ArrayList<>(Arrays.asList(4, 5)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Perform pull ups pausing at the top of each rep");
        exerciseManager.addExercise(1, "Trap Shrug Ladder", new ArrayList<>(Arrays.asList(6)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2), A(42))), 8, 7, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform sets as follows: 1 rep - 1 second pause, 2 reps - 2 second pause, ..., 7 reps-7second pause.");
        exerciseManager.addExercise(1, "Banded DB Rows", new ArrayList<>(Arrays.asList(4, 5, 6)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(1, 5))), 6, 10, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform DB rows with the band wrapped around them and anchored at your feet.");
        exerciseManager.addExercise(1, "Rocking Pull Downs", new ArrayList<>(Arrays.asList(5)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(5, 6), A(11))), 6, 10, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform pull downs by alternating which side you rock towards, pulling the weight further back on that side.");
        exerciseManager.addExercise(1, "Pause Pull Downs", new ArrayList<>(Arrays.asList(5)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(11), A(5, 6))), 6, 10, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform pull downs pausing at the bottom of each rep");
        exerciseManager.addExercise(1, "1 1/2 Rows", new ArrayList<>(Arrays.asList(4, 5, 6)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2), A(5), A(42))), 6, 10, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform each rep as a 1 & 1/2 rep, going up half way and back down before going all the way back up.");
        exerciseManager.addExercise(1, "1 1/2 Pull Down", new ArrayList<>(Arrays.asList()),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0))), 6, 10, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform each rep as a 1 & 1/2 rep, going up half way and back down before going all the way back up.");
        exerciseManager.addExercise(1, "Door Open Step Outs", new ArrayList<>(Arrays.asList(16)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(10), A(5))), 8, 10, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the band or cable fixed at your side, holding with one arm, externally rotate as if you are opening a door, at the end of the rep, step out and then step back in, holding the 'open' position the whole time.");

        // Biceps

        // Regular

        exerciseManager.addExercise(1, "Bicep Curls", new ArrayList<>(Arrays.asList(11)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2), A(42), A(5))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Bend at the elbows and curl the weight up to your shoulders.");
        exerciseManager.addExercise(1, "Alternating Bicep Curls", new ArrayList<>(Arrays.asList(11)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Bicep curl and alternate sides.");
        exerciseManager.addExercise(1, "Alternating Hammer Curls", new ArrayList<>(Arrays.asList(11)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(5))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Use a hal way pronated 'hammer' grip and curl up.");
        exerciseManager.addExercise(1, "Dumbbell No-Money Curls", new ArrayList<>(Arrays.asList(11)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Point your thumbs out and back as you curl.");
        exerciseManager.addExercise(1, "Banded No-Money Curls", new ArrayList<>(Arrays.asList(11)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(5))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Point your thumbs out and back as you curl.");
        exerciseManager.addExercise(1, "One Arm Concentration Curls", new ArrayList<>(Arrays.asList(11)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Seated, put your arm against your leg, and curl up.");
        exerciseManager.addExercise(1, "Lip Busters", new ArrayList<>(Arrays.asList(11)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(10), A(5))), 6, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Fix the band/cable in front of you and curl");
        exerciseManager.addExercise(1, "Weighted Chin Ups", new ArrayList<>(Arrays.asList(11)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 6), A(26, 6))), 6, 10, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Holding a dumbbell with your feet or using a weight belt and perform chin ups.");
        exerciseManager.addExercise(1, "Drag Curls", new ArrayList<>(Arrays.asList(11)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(2), A(42), A(5))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Pull your elbows back as you curl, keeping your hands in line with your torso through the curl.");
        exerciseManager.addExercise(1, "Complete Curl Trifecta", new ArrayList<>(Arrays.asList(11)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 8, 7, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "For each rep perform an alternating regular curl, hammer curl, and no-money curl.");
        exerciseManager.addExercise(1, "Waiter Curls", new ArrayList<>(Arrays.asList(11)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(5))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Holding one dumbbell with your palms facing up, curl it like a waiter, keep the 'plate' flat.");
        exerciseManager.addExercise(1, "Preacher Curls", new ArrayList<>(Arrays.asList(11)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(23), A(1, 9))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Either using the preacher curl bench or an incline bench, perform curls with your arms on the incline.");
        exerciseManager.addExercise(1, "Rainbow Curls", new ArrayList<>(Arrays.asList(11)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Curl the dumbbell up and out in an arc motion.");
        exerciseManager.addExercise(1, "Incline Curls", new ArrayList<>(Arrays.asList(11)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 9))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Laying on an incline bench at a 45 degree angle, curl as normal.");
        exerciseManager.addExercise(1, "Plate Curls", new ArrayList<>(Arrays.asList(11)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(26))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Holding one plate with both arms, perform curls.");
        exerciseManager.addExercise(1, "DB Row Curl", new ArrayList<>(Arrays.asList(11)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Bent over, row the dumbbells using your biceps.");
        exerciseManager.addExercise(1, "Spider Curl", new ArrayList<>(Arrays.asList(11)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 9))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Lay prone on an incline bench and allow your arms to dangle over the bench and curl.");

        // Super Set

        exerciseManager.addExercise(1, "Alternating Curl/Hammer Curl Super Set", new ArrayList<>(Arrays.asList(11)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform alternating curls to failure followed by alternating hammer curls to failure.");
        exerciseManager.addExercise(1, "Weighted Chin Up/Chin Hold Super Set", new ArrayList<>(Arrays.asList(11)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 6), A(26, 6))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Perform weighted chin ups to failure followed by chin up holds to failure.");
        exerciseManager.addExercise(1, "Incline/Straight/Drag Curl Mega Set", new ArrayList<>(Arrays.asList(11)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 9))), 6, 21, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "7 incline curls followed by 7 standing curls followed by 7 drag curls.");

        // Drop Set

        exerciseManager.addExercise(1, "Alternating Bicep Curl Drop Set", new ArrayList<>(Arrays.asList(11)),
                false, true, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(5))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform alternating bicep curls as a drop set.");
        exerciseManager.addExercise(1, "Alternating Hammer Curl Drop Set", new ArrayList<>(Arrays.asList(11)),
                false, true, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(5))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform hammer curls as a drop set");
        exerciseManager.addExercise(1, "Weighted Chin Up Drop Set", new ArrayList<>(Arrays.asList(11)),
                false, true, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 6), A(26, 6))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Holding the weight with your feet or using a weight belt, perform weighted chin ups as a drop set, dropping to body weight or assisted.");
        exerciseManager.addExercise(1, "EZ Bar Curl Drop Set", new ArrayList<>(Arrays.asList(11)),
                false, true, false,
                new ArrayList<>(Arrays.asList(A(0), A(42))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform an EZ Bar curl drop set.");

        // Special Rep

        exerciseManager.addExercise(1, "Bicep 21 Curls", new ArrayList<>(Arrays.asList(11)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(42), A(2), A(5))), 6, 21, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "7 reps in the bottom half of the curl followed by 7 in the top half followed by 7 full reps.");
        exerciseManager.addExercise(1, "Alternating Pause Curls", new ArrayList<>(Arrays.asList(11)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(5))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform alternating dumbbell curls but while the other arm is performing the curl the other arm is holding the dumbbell at the top of the rep.");
        exerciseManager.addExercise(1, "Alternating Pause Hammer Curls", new ArrayList<>(Arrays.asList(11)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(5))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform alternating dumbbell hammer curls but while the other arm is performing the curl the other arm is holding the dumbbell at the top of the rep.");
        exerciseManager.addExercise(1, "1 1/2 Curls", new ArrayList<>(Arrays.asList(11)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(5), A(42))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform each rep as a 1 & 1/2 rep, coming back up half way before going back down to complete the full rep.");
        exerciseManager.addExercise(1, "Supination Hammer Curls", new ArrayList<>(Arrays.asList(11)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform hammer curls but at the top of each rep turn your palms outwards so they face up and then back in before going down.");
        exerciseManager.addExercise(1, "Alternating Pause Lip Busters", new ArrayList<>(Arrays.asList(11)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(10), A(5))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform alternating lip busters but while the other arm is performing the curl the other arm is holding the cable/band at the top of the rep.");
        exerciseManager.addExercise(1, "Alternating Pause Spider Curls", new ArrayList<>(Arrays.asList(11)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1, 9))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform alternating spider curls but while the other arm is performing the curl the other arm is holding the dumbbell at the top of the rep.");
        exerciseManager.addExercise(1, "Robotic Curls", new ArrayList<>(Arrays.asList(11)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(5), A(1), A(42))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform each rep by pausing at the half way point of the rep.");
        exerciseManager.addExercise(1, "Banded DB Curls", new ArrayList<>(Arrays.asList(11)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(5, 1))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "DB curls with the band around the DB and anchored at your feet.");


        // Triceps

        // Regular

        exerciseManager.addExercise(1, "Close Grip Bench Press", new ArrayList<>(Arrays.asList(12)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 8), A(2, 8), A(42, 8), A(1, 9), A(2, 9), A(42, 9), A(4))),
                8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform bench press using a close grip.");
        exerciseManager.addExercise(1, "Triceps Push Downs", new ArrayList<>(Arrays.asList(12)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(10), A(5, 6))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the cable or band fixed above you, grab it with both hands and push down against your body, extending with your elbows.");
        exerciseManager.addExercise(1, "Incline Close Grip Bench Press", new ArrayList<>(Arrays.asList(12, 9)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 9), A(2, 9), A(42, 9))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform incline bench press using a close grip.");
        exerciseManager.addExercise(1, "Skull Crushers", new ArrayList<>(Arrays.asList(12)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 8), A(2, 8), A(42, 8), A(1, 9), A(2, 9), A(42, 9), A(4))),
                8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Laying on a bench perform overhead extensions");
        exerciseManager.addExercise(1, "Alternating Skull Crushers", new ArrayList<>(Arrays.asList(12)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 8), A(1, 9))), 8, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Laying on a bench perform alternating overhead extensions");
        exerciseManager.addExercise(1, "Triceps Kickbacks", new ArrayList<>(Arrays.asList(12)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(5))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Bent over with the weight in each hand, extend the elbows pushing the weight to your waist.");
        exerciseManager.addExercise(1, "Alternating Triceps Kickbacks", new ArrayList<>(Arrays.asList(12)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Bent over with the weight in each hand, extend the elbows pushing the weight to your waist.");
        exerciseManager.addExercise(1, "Overhead Triceps Extensions", new ArrayList<>(Arrays.asList(12)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(10), A(5, 6))), 6, 12, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the band or cable fixed above and behind you, extend the elbows, pushing the weight in front and above your head.");
        exerciseManager.addExercise(1, "Weighted Close Grip Push Ups", new ArrayList<>(Arrays.asList(12)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(26))), 6, 10, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "With the weight on your back perform close grip push ups.");

        // Super Set

        exerciseManager.addExercise(1, "Skull Crusher/Close Grip Bench Press Super Set", new ArrayList<>(Arrays.asList(12)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 8), A(1, 9), A(42, 8), A(42, 8))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform skull crushers to failure followed by close grip bench press to failure.");
        exerciseManager.addExercise(1, "Weighted Tiger Press/Cobra Push Up Super Set", new ArrayList<>(Arrays.asList(12)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(26), A(1))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Tiger press is like a push up but focus on pushing back and up exclusively using elbow extension. Cobra push up starts in the push up position close to the ground and press up and extend up at the end.");
        exerciseManager.addExercise(1, "Overhead Extension/Push Downs Super Set", new ArrayList<>(Arrays.asList(12)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(10), A(5, 6))), 6, 16, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "8 reps of overhead extensions followed by 8 reps of pushdowns");

        // Drop Set

        exerciseManager.addExercise(1, "Triceps Push Down Drop Set", new ArrayList<>(Arrays.asList(12)),
                false, true, false,
                new ArrayList<>(Arrays.asList(A(0), A(10), A(5, 6))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the cable or band fixed above you push down by extending your elbows. Perform as a drop set.");
        exerciseManager.addExercise(1, "Triceps Kickback Drop Set", new ArrayList<>(Arrays.asList(12)),
                false, true, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(5))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Bent over with weight in both hands, extend your elbows kicking your arms back. Perform as a drop set.");
        exerciseManager.addExercise(1, "Triceps Close Grip Bench Press Drop Set", new ArrayList<>(Arrays.asList(12)),
                false, true, false,
                new ArrayList<>(Arrays.asList(A(0), A(1, 8), A(1, 9), A(2, 8), A(2, 9), A(42, 8), A(42, 9))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Bench press with a close grip and perform as a drop set.");
        exerciseManager.addExercise(1, "Triceps Overhead Extension Drop Set", new ArrayList<>(Arrays.asList(12)),
                false, true, false,
                new ArrayList<>(Arrays.asList(A(0), A(10), A(5, 6))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the cable or band fixed overhead, extend your elbows overhead and perform as a drop set.");

        // Special Rep

        exerciseManager.addExercise(1, "Alternating Pause Kickbacks", new ArrayList<>(Arrays.asList(12)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(5))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform alternating kickbacks but while the other arm is performing the kickback the other arm is holding the dumbbell/band at the top of the rep.");
        exerciseManager.addExercise(1, "Banded DB Kickbacks", new ArrayList<>(Arrays.asList(12)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(1, 5))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform DB kickbacks with the band wrapped around it and anchored at your feet.");
        exerciseManager.addExercise(1, "Alternating Pause Skull Crushers", new ArrayList<>(Arrays.asList(12)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(1, 8), A(1, 9))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform alternating skull crushers but while the other arm is performing the skull crusher the other arm is holding the dumbbell at the top of the rep.");
        exerciseManager.addExercise(1, "Alternating Pause Overhead Extension", new ArrayList<>(Arrays.asList(12)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(5, 6), A(10))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform alternating overhead extensions but while the other arm is performing the extension the other arm is holding the cable/band at the top of the rep.");

        // Endurance

        exerciseManager.addExercise(2, "Running", new ArrayList<>(Arrays.asList(27, 0, 1, 2, 20)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(29), A(37))), 30, 1, 1,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "On a treadmill or outside, jog for 30 minutes.");
        exerciseManager.addExercise(2, "Biking", new ArrayList<>(Arrays.asList(27, 0, 1, 2, 20)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(30))), 30, 1, 1,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "On a machine bike or outside, bike for 30 minutes.");
        exerciseManager.addExercise(2, "Elliptical", new ArrayList<>(Arrays.asList(27, 0, 1, 2, 20)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(32))), 30, 1, 1,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Use the elliptical for 30 minutes.");
        exerciseManager.addExercise(2, "Stair Master", new ArrayList<>(Arrays.asList(27, 0, 1, 2, 20)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(33))), 30, 1, 1,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Use the stair master for 30 minutes.");
        exerciseManager.addExercise(2, "Endurance Jump Rope", new ArrayList<>(Arrays.asList(27, 20)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(35))), 15, 1, 1,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Jump rope on and off for 15 minutes. Try to go for 1-2 minutes at a time before resting.");

        exerciseManager.addExercise(2, "Endurance Boxing", new ArrayList<>(Arrays.asList(27, 3, 4, 7, 12, 17)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(36))), 30, 1, 1,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Box on and off for 30 minutes. Try to go for 5-10 minutes before resting for a minute.");

        exerciseManager.addExercise(2, "Rowing Machine", new ArrayList<>(Arrays.asList(27, 4, 5, 6, 11)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(31))), 30, 1, 1,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Use the rowing machine for 30 minutes.");

        exerciseManager.addExercise(2, "Swimming", new ArrayList<>(Arrays.asList(27, 0, 1, 2, 3, 4, 5, 6, 7, 8, 17, 21)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(34))), 30, 1, 1,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Swim for 30 minutes.");

        // Calisthenic


        // Legs

        // Regular

        exerciseManager.addExercise(3, "Hamstring Floor Slides", new ArrayList<>(Arrays.asList(2)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Lying on the ground either take your shoes off or use a towel underneath your shoes and hip thrust up. To perform a rep, slide your feet down and then pull them back up using your hamstrings.");
        exerciseManager.addExercise(3, "Box Jumps", new ArrayList<>(Arrays.asList(0, 1)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(8), A(9), A(28))), 6, 20, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Jump up to the bench or box and back down, repeat.");
        exerciseManager.addExercise(3, "Squat Jumps", new ArrayList<>(Arrays.asList(0, 1)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, 20, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Squat down and jump up.");
        exerciseManager.addExercise(3, "Body Weight Squats", new ArrayList<>(Arrays.asList(0, 1, 2)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, 20, 3,
                new ArrayList<>(Arrays.asList(0)), "Squat down with body weight to depth.");
        exerciseManager.addExercise(3, "Circular Floor Slides", new ArrayList<>(Arrays.asList(18, 19)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Lying on the ground either take your shoes off or use a towel underneath your shoes and hip thrust up. To perform a rep, slide your feet down and then pull them back up in a circular motion using your hamstrings.");
        exerciseManager.addExercise(3, "Split Squat Jumps", new ArrayList<>(Arrays.asList(0, 1)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, 20, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Starting in a split squat position, jump out of that position swapping the front and back legs in the split squat position.");
        exerciseManager.addExercise(3, "Pistol Squat", new ArrayList<>(Arrays.asList(0)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24), A(8))), 6, 10, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Single leg squats, use a hand to balance if you are unable to. Perform on both legs.");
        exerciseManager.addExercise(3, "Step Up and Through", new ArrayList<>(Arrays.asList(1, 2)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(8), A(9))), 6, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Using a bench or a box, step one leg up onto it and step up, hiking the opposite leg up thrusting through to your chest. Perform on both legs.");
        exerciseManager.addExercise(3, "Nordic Hamstring Curls", new ArrayList<>(Arrays.asList(2)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(8), A(9))), 6, 10, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "In a kneeling position fix your feet behind you and fall forward, controlling your descent using your hamstrings.");
        exerciseManager.addExercise(3, "Kneeling Quad Extensions", new ArrayList<>(Arrays.asList(0)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Starting in a kneeling position, leaning your torso back, extend your knees to bring your torso up. If you have vulnerable knees do not do this exercise.");
        exerciseManager.addExercise(3, "Hip Thrusts", new ArrayList<>(Arrays.asList(1)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24), A(8))), 6, 20, 3,
                new ArrayList<>(Arrays.asList(0, 1)), "Perform elevated if a bench is available.");
        exerciseManager.addExercise(3, "Single Leg Hip Thrusts", new ArrayList<>(Arrays.asList(1)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Same as a hip thrust but with only one leg. Perform elevated if a bench is available. Perform on both legs.");
        exerciseManager.addExercise(3, "Bridge Marches", new ArrayList<>(Arrays.asList(1, 2)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24), A(8), A(9))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Bridge up squeezing your glutes and march with your legs. Perform elevated on a bench if available");
        exerciseManager.addExercise(3, "Step Up Hops", new ArrayList<>(Arrays.asList(0, 1, 2)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, 10, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Using a bench or a box, step one leg up onto it and step up, hiking the opposite leg up thrusting through to your chest hopping at the end of the rep. Perform on both legs.");
        exerciseManager.addExercise(3, "Crab Walk", new ArrayList<>(Arrays.asList(18, 19)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "In a squat position, crab walk side to side.");
        exerciseManager.addExercise(3, "Side Squat", new ArrayList<>(Arrays.asList(18, 19)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, 20, 3,
                new ArrayList<>(Arrays.asList(0, 1)), "Squat down side ways, alternating sides.");
        exerciseManager.addExercise(3, "Body Weight Step Back Lunge", new ArrayList<>(Arrays.asList(0)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, 20, 3,
                new ArrayList<>(Arrays.asList(0)), "Step back into the lunge with your body weight.");
        exerciseManager.addExercise(3, "Side Floor Slide", new ArrayList<>(Arrays.asList(18, 19)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, 10, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Standing, either take your shoes off or use a towel underneath your shoes and slide to one side in a side squat position, using your inner thigh to pull your self back up. Perform on both legs");
        exerciseManager.addExercise(3, "Calf Raises", new ArrayList<>(Arrays.asList(20)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, 25, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Standing on both or one leg, raise up using your calves.");
        exerciseManager.addExercise(3, "Walk the Box", new ArrayList<>(Arrays.asList(0, 1, 2, 18, 19)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "In a squat position, walk forwards, then crab walk side ways, backwards, and then crab walk the other way returning to your intial position, making a box.");
        exerciseManager.addExercise(3, "Frog Hops", new ArrayList<>(Arrays.asList(18, 19, 0)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, 20, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Squat jump side to side like a frog.");
        exerciseManager.addExercise(3, "Ratchet Lunge Hops", new ArrayList<>(Arrays.asList(18, 19, 0)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, 20, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Starting in a split squat position, jump out of it, swapping legs and turning sideways.");
        exerciseManager.addExercise(3, "Side Leg Lifts", new ArrayList<>(Arrays.asList(19)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Laying sideways, raise your leg up as far as you can. Perform on both legs");
        exerciseManager.addExercise(3, "Tibialis Raise", new ArrayList<>(Arrays.asList(20)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Tibialis is the antithesis to your calf. Position yourself against a wall and raise your toes up off the ground on your heels.");
        exerciseManager.addExercise(3, "Skaters", new ArrayList<>(Arrays.asList(18, 19)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Staring on one foot hop side to side as if you are ice skating.");

        // Super Set

        exerciseManager.addExercise(3, "Squat Jump/Body Weight Squat Super Set", new ArrayList<>(Arrays.asList(0, 1, 2)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, 30, 3,
                new ArrayList<>(Arrays.asList(1)), "15 squat jumps followed by 15 body weight squats");
        exerciseManager.addExercise(3, "Split Squat Jump/Walk the Box Super Set", new ArrayList<>(Arrays.asList(0, 1, 2)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, 20, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "20 split squat jumps followed by walk the box to failure.");
        exerciseManager.addExercise(3, "Lunges/Squat Super Set", new ArrayList<>(Arrays.asList(0, 1, 2)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, 20, 3,
                new ArrayList<>(Arrays.asList(0)), "10 lunges followed by 10 squats.");
        exerciseManager.addExercise(3, "Hamstring Slides/Hip Thrust Super Set", new ArrayList<>(Arrays.asList(1, 2)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform hamstring slides to failure followed by hip thrusts to failure.");
        exerciseManager.addExercise(3, "Frog Hops/Skaters Super Set", new ArrayList<>(Arrays.asList(18, 19)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform frog hops to failure followed by skaters to failure.");
        exerciseManager.addExercise(3, "Calf Raise/Tibialis Raise Super Set", new ArrayList<>(Arrays.asList(20)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, 20, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "10 reps of calf raises followed by 10 reps of tibialis raises.");


        // Special Rep

        exerciseManager.addExercise(3, "Creeping Lunges", new ArrayList<>(Arrays.asList(0, 1, 2)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Staring in a lunge position, perform forward lunges except do not come all the way up before taking your next step.");
        exerciseManager.addExercise(3, "1 1/2 Squats", new ArrayList<>(Arrays.asList(0, 1, 2)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform each rep as a 1 & 1/2 rep");
        exerciseManager.addExercise(3, "1 1/2 Hip Thrusts", new ArrayList<>(Arrays.asList(1)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform each rep as a 1 & 1/2 rep");
        exerciseManager.addExercise(3, "1 1/2 Pistol Squats", new ArrayList<>(Arrays.asList(0)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, 10, 3,
                new ArrayList<>(Arrays.asList(2)), "Perform each rep as a 1 & 1/2 rep");

        // Chest

        // Regular

        exerciseManager.addExercise(3, "Push Ups", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1)), "Perform push ups.");
        exerciseManager.addExercise(3, "Kneeling Push Ups", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0)), "Perform kneeling push ups.");
        exerciseManager.addExercise(3, "Twisting Push Ups", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Perform a push up as normal but twist to one side at the top of each rep, alternate sides.");
        exerciseManager.addExercise(3, "Decline Push Ups", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Perform push ups with your legs elevated.");
        exerciseManager.addExercise(3, "Incline Push Ups", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1)), "Perform push ups with your hands elevated.");
        exerciseManager.addExercise(3, "Wall Push Ups", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Perform push ups with your legs against the wall not on the ground.");
        exerciseManager.addExercise(3, "Dips", new ArrayList<>(Arrays.asList(3, 12)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(7))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform dips.");
        exerciseManager.addExercise(3, "Archer Push Ups", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Set up in a wide grip push up stance and as you go down move close to one side, alternating sides each rep.");
        exerciseManager.addExercise(3, "Clock Push Ups", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(2)), "Perform plyo push ups, jumping around in a semi circle, before switching directions.");
        exerciseManager.addExercise(3, "Plyo Push Ups", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Jump push ups");
        exerciseManager.addExercise(3, "One Arm Push Ups", new ArrayList<>(Arrays.asList(3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(2)), "Using one arm perform push ups. Perform on both sides.");

        // Super Set

        exerciseManager.addExercise(3, "Push Up Mega Set", new ArrayList<>(Arrays.asList(3)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform decline push ups to faliure followed by flat push ups to failure followed by incline push ups to failure.");
        exerciseManager.addExercise(3, "Push Up/Dip Super Set", new ArrayList<>(Arrays.asList(3)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(7))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "10 reps of dips followed by push ups to failure.");
        exerciseManager.addExercise(3, "Push Up/Squeeze Super Set", new ArrayList<>(Arrays.asList(3)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform push ups to failure followed by 6 reps of 5 second chest squeezes");
        exerciseManager.addExercise(3, "Archer Push Up/Push Up Super Set", new ArrayList<>(Arrays.asList(3)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(2)), "Perform 10 reps of archer push ups followed by push ups to failure.");
        exerciseManager.addExercise(3, "Push Up/ISO Hold Super Set", new ArrayList<>(Arrays.asList(3)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform push ups to failure followed by an isometric hold at the bottom of the rep until failure.");

        // Special Rep

        exerciseManager.addExercise(3, "Wide to Close Plyo Push Ups", new ArrayList<>(Arrays.asList(3, 12)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "After each push up move hands from a wide grip to a close grip or vice versa until failure");
        exerciseManager.addExercise(3, "Tempo Push Ups", new ArrayList<>(Arrays.asList(3)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Perform these at a slow pace.");
        exerciseManager.addExercise(3, "Pause Push Ups", new ArrayList<>(Arrays.asList(3)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Pause at the bottom of each rep.");
        exerciseManager.addExercise(3, "Stair Push Ups", new ArrayList<>(Arrays.asList(3)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Perform push ups going up the stairs. Each rep jump up to a higher stair until you get to the top and go back down.");
        exerciseManager.addExercise(3, "1 1/2 Push Ups", new ArrayList<>(Arrays.asList(3)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform each rep as a 1 1/2 rep.");

        // Shoulders

        // Regular

        exerciseManager.addExercise(3, "Pike Push Ups", new ArrayList<>(Arrays.asList(7, 9)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1)), "Get your torso as perpendicular to the ground as possible and perform push ups, these should feel like overhead pressing.");
        exerciseManager.addExercise(3, "Kneeling Pike Push Ups", new ArrayList<>(Arrays.asList(7, 9)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0)), "Get your torso as perpendicular to the ground as possible and perform push ups, these should feel like overhead pressing.");
        exerciseManager.addExercise(3, "Handstand Push Ups", new ArrayList<>(Arrays.asList(7, 9)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(2)), "Against a wall, get into a handstand position and perform push ups.");
        exerciseManager.addExercise(3, "Dive Bomber Push Ups", new ArrayList<>(Arrays.asList(7, 9)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Get your torso as perpendicular to the ground as possible and perform push ups, as you com down curve your self up at the end into a cobra position.");
        exerciseManager.addExercise(3, "Side Lateral Plank Raises", new ArrayList<>(Arrays.asList(8)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "In a plank position rotate into a side plank position and then do the same on the other side.");
        exerciseManager.addExercise(3, "Rear Delt Press Up", new ArrayList<>(Arrays.asList(10)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Lying back down stretch your arms out to the side and squeeze your arms against the ground pushing up.");
        exerciseManager.addExercise(3, "Side Lateral ISO Hold", new ArrayList<>(Arrays.asList(8)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "In a door way push your arms out sideways in an isometric hold. Push as hard as you can.");
        exerciseManager.addExercise(3, "Press Away", new ArrayList<>(Arrays.asList(7)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "In a push up position with your knees bent press up and back from the ground.");
        exerciseManager.addExercise(3, "Prone Press", new ArrayList<>(Arrays.asList(14, 15, 16)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Lying prone, lift your arms off the ground and perform an overhead press motion, try with nothing before moving to weights.");
        exerciseManager.addExercise(3, "Angels and Devils", new ArrayList<>(Arrays.asList(14, 15, 16)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Lying prone, stretch your arms outwards and bring them up above your head and then back around behind your back, try with nothing before moving to weights.");


        // Super Set

        exerciseManager.addExercise(3, "Pike Push Up/Press Away Super Set", new ArrayList<>(Arrays.asList(7, 9)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Perform pike push ups to failure followed by press aways to failure.");
        exerciseManager.addExercise(3, "Side Lateral Planks/ISO Hold Super Set", new ArrayList<>(Arrays.asList(8)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform 15 side lateral plank raises followed by an isometric hold to failure.");
        exerciseManager.addExercise(3, "Handstand Push Up/Pike Push Up Super Set", new ArrayList<>(Arrays.asList(7, 9)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(2)), "Perform handstand push ups to failure followed by pike push ups to failure.");


        // Special Rep

        exerciseManager.addExercise(3, "1 1/2 Handstand Push Ups", new ArrayList<>(Arrays.asList(7, 9)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(2)), "Perform each rep as a 1 & 1/2 rep.");
        exerciseManager.addExercise(3, "1 1/2 Pike Push Up", new ArrayList<>(Arrays.asList(7, 9)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Perform each rep as a 1 & 1/2 rep");
        exerciseManager.addExercise(3, "Paused Pike Push Ups", new ArrayList<>(Arrays.asList(7, 9)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Pause at the bottom of each rep.");
        exerciseManager.addExercise(3, "1 1/2 Lateral Plank Raises", new ArrayList<>(Arrays.asList(8)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform each rep as a 1 % 1/2 rep.");
        exerciseManager.addExercise(3, "Pause Rear Delt Press", new ArrayList<>(Arrays.asList(10)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Pause at the top of each rep.");
        exerciseManager.addExercise(3, "1 1/2 Prone Press", new ArrayList<>(Arrays.asList(14, 15, 16)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Lying prone, lift your arms off the ground and perform an overhead press motion, try with nothing before moving to weights. Perform each rep as a 1 & 1/2 rep.");

        // Back

        // Regular

        exerciseManager.addExercise(3, "Pull Ups", new ArrayList<>(Arrays.asList(4, 5)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Perform pull ups.");
        exerciseManager.addExercise(3, "Inverted Row", new ArrayList<>(Arrays.asList(4, 5, 6)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "On the pull up bar elevate your feet so each pull up is more of a body weight row than a pull up.");
        exerciseManager.addExercise(3, "Front Lever", new ArrayList<>(Arrays.asList(5)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Hanging from the pull up bar lever your torso up to the bar keeping it as rigid as possible.");
        exerciseManager.addExercise(3, "Headbangers", new ArrayList<>(Arrays.asList(5)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(2)), "Hanging from the bar, pull up to the contracted position and then extend out and in, 'head banging' the bar, resisting moving from the contracted position.");
        exerciseManager.addExercise(3, "Back Widows", new ArrayList<>(Arrays.asList(6, 14)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, 15, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Laying on the ground, drive your elbows into the ground elevating your scapula off the ground and then back down.");
        exerciseManager.addExercise(3, "Assisted Pull Ups", new ArrayList<>(Arrays.asList(4, 5)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(5, 6))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0)), "With a band hanging from the bar, or using the assisted pull up machine, perform pull ups.");
        exerciseManager.addExercise(3, "One Arm Inverted Row", new ArrayList<>(Arrays.asList(4, 5, 6)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "On the pull up bar elevate your feet so each pull up is more of a body weight row than a pull up. Use one arm rather than both. Perform on both sides.");
        exerciseManager.addExercise(3, "Plyo Pull Ups", new ArrayList<>(Arrays.asList(4, 5)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Pull ups but explode at the top, jumping and lifting your hands off the bar.");
        exerciseManager.addExercise(3, "Plyo Inverted Rows", new ArrayList<>(Arrays.asList(4, 5)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Inverted rows but explode at the top, jumping and lifting your hands off the bar.");
        exerciseManager.addExercise(3, "One Arm Pull Up", new ArrayList<>(Arrays.asList(4, 5)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(2)), "Pull ups with one arm.");
        exerciseManager.addExercise(3, "Commando Pull Ups", new ArrayList<>(Arrays.asList(4, 5)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Perform pull ups with your hands gripping the bar on opposite sides. When you pull up move your head to one side of the bar, alternating sides.");

        // Super Set

        exerciseManager.addExercise(3, "Pull Up/Inverted Row Super Set", new ArrayList<>(Arrays.asList(4, 5, 6)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform (assisted) pull ups to failure followed by inverted rows to failure.");
        exerciseManager.addExercise(3, "Front Lever/Inverted Row Super Set", new ArrayList<>(Arrays.asList(4, 5, 6)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Perform front levers to failure followed by inverted rows to failure.");
        exerciseManager.addExercise(3, "Inverted Row/Back Widow Super Set", new ArrayList<>(Arrays.asList(4, 6, 14)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1)), "Perform inverted rows with your feet elevated to failure and then back widows to failure.");


        // Special Rep

        exerciseManager.addExercise(3, "Muscle Ups", new ArrayList<>(Arrays.asList(5)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(2)), "Perform explosive pull ups, lifting your self up above the bar at the end of each rep.");
        exerciseManager.addExercise(3, "Paused Pull Ups", new ArrayList<>(Arrays.asList(4, 5)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Pause at the top of each rep.");
        exerciseManager.addExercise(3, "Flosser Pull Ups", new ArrayList<>(Arrays.asList(4, 5)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "At the top of each rep slide to the left and then the right before going back down.");
        exerciseManager.addExercise(3, "Around the World Pull Ups", new ArrayList<>(Arrays.asList(4, 5)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Perform each pull up in a circular motion as you go up and down.");
        exerciseManager.addExercise(3, "Tempo Pull Ups", new ArrayList<>(Arrays.asList(4, 5)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Perform these at a slow pace.");
        exerciseManager.addExercise(3, "1 1/2 Pull Ups", new ArrayList<>(Arrays.asList(4, 5)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Perform each rep as a 1 & 1/2 rep.");

        // Biceps

        // Regular

        exerciseManager.addExercise(3, "Chin Ups", new ArrayList<>(Arrays.asList(11, 5)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Perform chin ups.");
        exerciseManager.addExercise(3, "ISO Chin Hold", new ArrayList<>(Arrays.asList(11)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Bring yourself to the top of a chin up and hold that position as long as you can.");
        exerciseManager.addExercise(3, "Bicep Leg Curls", new ArrayList<>(Arrays.asList(11)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Using your leg as the weight perform one arm curls. Perform on both sides.");
        exerciseManager.addExercise(3, "Inverted Row Curls", new ArrayList<>(Arrays.asList(11)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "On the pull up bar elevate your feet so each chin up is more of a body weight curl than a chin up.");
        exerciseManager.addExercise(3, "Plyo Chin Ups", new ArrayList<>(Arrays.asList(11, 5)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(2)), "Chin ups but explode at the top, jumping and lifting your hands off the bar.");
        exerciseManager.addExercise(3, "Assisted Chin Ups", new ArrayList<>(Arrays.asList(11, 5)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0)), "With a band hanging from the bar, or using the assisted chin up machine, perform chin ups.");
        exerciseManager.addExercise(3, "One Arm Chin Ups", new ArrayList<>(Arrays.asList(11, 5)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(2)), "Perform chin ups with one arm.");

        // Super Set

        exerciseManager.addExercise(3, "Chin Ups/ISO Chin Hold Super Set", new ArrayList<>(Arrays.asList(11)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform (assisted) chin ups to failure followed by an isometric hold in the contracted position to failure.");
        exerciseManager.addExercise(3, "Inverted Row Curls/ISO Hold Super Set", new ArrayList<>(Arrays.asList(11)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform inverted row curls until failure followed by an isometric hold in the contracted position.");
        exerciseManager.addExercise(3, "Chin Ups/Inverted Row Curls Super Set", new ArrayList<>(Arrays.asList(11)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Perform chin ups to failure followed by inverted row curls to failure.");

        // Special Rep

        exerciseManager.addExercise(3, "Pause Chin Ups", new ArrayList<>(Arrays.asList(11, 5)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Pause at the top of each rep.");
        exerciseManager.addExercise(3, "Pause Inverted Row Curls", new ArrayList<>(Arrays.asList(11)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Pause at the top of each rep.");
        exerciseManager.addExercise(3, "1 1/2 Chin Ups", new ArrayList<>(Arrays.asList(11, 5)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform each rep as a 1 & 1/2 rep.");
        exerciseManager.addExercise(3, "Robotic Chin Ups", new ArrayList<>(Arrays.asList(11)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform each rep by stopping at the halfway point each time.");

        // Triceps

        // Regular

        exerciseManager.addExercise(3, "Close Grip Push Ups", new ArrayList<>(Arrays.asList(12)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform kneeling if necessary.");
        exerciseManager.addExercise(3, "Plyo Close Grip Push Ups", new ArrayList<>(Arrays.asList(12)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Explode at the top of each rep lifting your hand off the ground.");
        exerciseManager.addExercise(3, "Diamond Cutter Push Ups", new ArrayList<>(Arrays.asList(12)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Make a diamond with your hands and perform push ups. Perform kneeling if necessary.");
        exerciseManager.addExercise(3, "Tiger Press Up", new ArrayList<>(Arrays.asList(12)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Set up in the push up position with your elbows on the ground and your fists in front of you, press up using elbow extension. Perform kneeling if necessary.");
        exerciseManager.addExercise(3, "Cobra Push Ups", new ArrayList<>(Arrays.asList(12)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Start at the bottom of a close grip push up and push forward and up arching up like a snake. Perform kneeling if necessary.");
        exerciseManager.addExercise(3, "Body Weight Skull Crushers", new ArrayList<>(Arrays.asList(12)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(8), A(9))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "With your hands gripping the edge of a bench or stair and your feet on the ground. lower your head under the edge of the bench/stair and then push up to the starting position. Perform kneeling if necessary.");
        exerciseManager.addExercise(3, "Bench Dips", new ArrayList<>(Arrays.asList(12)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24), A(8))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With your hands pointed outwards on the edge of the bench, lower yourself down and back up.");

        // Super Set

        exerciseManager.addExercise(3, "Close Grip Push Ups/Cobra Push Ups Super Set", new ArrayList<>(Arrays.asList(12)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform close grip push ups to failure followed by cobra push ups to failure.");
        exerciseManager.addExercise(3, "Tiger Press Up/Cobra Push Ups Super Set", new ArrayList<>(Arrays.asList(12)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Perform tiger press ups to failure followed by cobra push ups to failure.");
        exerciseManager.addExercise(3, "Skull Crusher/Bench Dip Super Set", new ArrayList<>(Arrays.asList(12)),
                true, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(8), A(9))), 8, -1, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Perform body weight skull crushers to failure followed by bench dips to failure.");

        // Special Rep

        exerciseManager.addExercise(3, "Pause Close Grip Push Ups", new ArrayList<>(Arrays.asList(12)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Pause at the bottom of each rep.");
        exerciseManager.addExercise(3, "1 1/2 Close Grip Push Ups", new ArrayList<>(Arrays.asList(12)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform each rep as a 1 & 1/2 rep.");
        exerciseManager.addExercise(3, "Tempo Cobra Push Ups", new ArrayList<>(Arrays.asList(12)),
                false, false, true,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Start at the bottom of a close grip push up and push forward and up arching up like a snake. Perform these reps at a slow pace.");


        // Conditioning

        // Legs

        exerciseManager.addExercise(4, "Jump Rope", new ArrayList<>(Arrays.asList(27, 20)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(35))), 4, 60, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Rest 15 seconds between rounds.");
        exerciseManager.addExercise(4, "Box Jumps", new ArrayList<>(Arrays.asList(27, 0, 1, 2)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(8), A(9), A(28))), 4, 30, 4,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Rest 15 seconds between rounds.");
        exerciseManager.addExercise(4, "Split Squat Jumps", new ArrayList<>(Arrays.asList(27, 0)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 4, 30, 4,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Starting in a lunge position, hop up and swap the position of your legs. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(4, "Shuffle Shuffles", new ArrayList<>(Arrays.asList(27, 18, 19)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 4, 30, 4,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "In a squat position, shuffle from side to side, taking about 3-8 steps in each direction. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(4, "Sprints", new ArrayList<>(Arrays.asList(27, 0, 1, 2)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(37))), 5, -1, 5,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Sprint 50-100 meters.");
        exerciseManager.addExercise(4, "Running", new ArrayList<>(Arrays.asList(27, 0, 1, 2)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(37), A(0), A(24))), 5, -1, 1,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Run 1000 meters.");
        exerciseManager.addExercise(4, "Squat Jumps", new ArrayList<>(Arrays.asList(27, 0, 1, 2)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 4, 30, 4,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Rest 15 seconds between rounds.");
        exerciseManager.addExercise(4, "Skaters", new ArrayList<>(Arrays.asList(27, 18, 19)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 4, 45, 4,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Staring on one foot hop side to side as if you are ice skating. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(4, "Frog Jumps", new ArrayList<>(Arrays.asList(27, 0, 18, 19)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 4, 30, 4,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Squat jump side to side like a frog. Rest 15 seconds between rounds.");

        // Chest

        exerciseManager.addExercise(4, "Burpees", new ArrayList<>(Arrays.asList(27, 3, 0)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 4, 45, 4,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Jump up stretching your hands up as you do and then go down into the push up position and perform a push up. Repeat. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(4, "Push Up Heel Touch Jump", new ArrayList<>(Arrays.asList(27, 3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 4, 30, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Push up and bring your feet up to jump and touch your toes. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(4, "Boxing", new ArrayList<>(Arrays.asList(27, 3)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(36))), 5, 60, 4,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Box for 60 seconds. Rest 15 seconds between rounds.");

        // Back

        exerciseManager.addExercise(4, "Light Kettlebell Swings", new ArrayList<>(Arrays.asList(27, 17)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(25))), 4, 30, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Deadlift down and pick up the kettlebell and thrust your hips forward as you swing the kettlebell up. Rest 15 seconds between rounds");
        exerciseManager.addExercise(4, "Farmer's Carries", new ArrayList<>(Arrays.asList(27)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 6, -1, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Hold the weight at your sides and walk back and forth. Rest 15 seconds between rounds.");

        // Abs

        exerciseManager.addExercise(4, "Mountain Climbers", new ArrayList<>(Arrays.asList(27, 21, 24)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 4, 45, 4,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "In a plank position, hike your knees up to your chest alternating sides as if you are running in place. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(4, "Drunken Mountain Climbers", new ArrayList<>(Arrays.asList(27, 21, 25)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 4, 45, 4,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "In a plank position, hike your knees up to the other side of your chest alternating sides as if you are running in place. These are the same as mountain climbers except your knees should be hiking up at an angle. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(4, "Bear Crawl", new ArrayList<>(Arrays.asList(27)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 4, 60, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "On all fours, bear crawl up and down the space you have. Rest 15 seconds between rounds.");

        // Misc

        exerciseManager.addExercise(4, "Med Ball Toss", new ArrayList<>(Arrays.asList(27, 21)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(27))), 4, 30, 4,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Starting from a squat position with the med ball held at your chest, explode up and throw the ball at the wall, catching it on your way back into your next squat.");
        exerciseManager.addExercise(4, "Med Ball Slam", new ArrayList<>(Arrays.asList(27)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(27))), 4, 30, 4,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Holding the med ball in both hands, lift it up over your head and slam it into the ground. Deadlift it from the ground and repeat.");
        exerciseManager.addExercise(4, "Med Ball Rotating Toss", new ArrayList<>(Arrays.asList(27, 21, 25)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(27))), 4, 30, 4,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Holding the med ball at one side in both hands rotate as you toss it into the wall. As you catch the ball off the wall rotate the other direction and move into your next toss.");
        exerciseManager.addExercise(4, "High Knees", new ArrayList<>(Arrays.asList(27)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 4, 60, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Run in place, elevating your knees up to your chest. Rest for 15 seconds between rounds");
        exerciseManager.addExercise(4, "Biking", new ArrayList<>(Arrays.asList(27, 0, 1, 2)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(30))), 5, -1, 1,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Go as hard as you can for 5 minutes.");


        // Ab

        exerciseManager.addExercise(5, "Crunches", new ArrayList<>(Arrays.asList(21, 24)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Do not come up all the way or use the momentum from your upper body to perform the crunch. Focus on controlled ab contraction. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Weighted Crunches", new ArrayList<>(Arrays.asList(21, 24)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Do not come up all the way or use the momentum from your upper body to perform the crunch. Focus on controlled ab contraction. Use no weight if necessary. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Core Death March", new ArrayList<>(Arrays.asList(21, 24)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(5, 6))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Fix the band behind you and overhead. Grab it with both hands stretch overhead. Step out so the tension in the band is held. March in place keeping as rigid as possible. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Hanging Leg Raises", new ArrayList<>(Arrays.asList(21, 24, 23)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(2)), "Hanging from the bar, keeping your legs straight, raise your legs up as high as you can. Keep your scapula depressed and tension through your core. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Hanging Knee Raises", new ArrayList<>(Arrays.asList(21, 24, 23)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(1)), "Hanging from the bar, keeping your knees bent, raise your legs up as high as you can. Keep your scapula depressed and tension through your core. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Mountain Climbers", new ArrayList<>(Arrays.asList(21, 24)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "In a plank position, hike your knees up to your chest alternating sides as if you are running in place. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Plank", new ArrayList<>(Arrays.asList(21, 24)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(0)), "Standard plank position, perform on your knees if necessary. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Weighted Plank", new ArrayList<>(Arrays.asList(21, 24)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24, 26))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Standard plank position with the weight resting on your back. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Marching Plank", new ArrayList<>(Arrays.asList(21, 24)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "In a plank position, march in place. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Wall Plank", new ArrayList<>(Arrays.asList(21, 24)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Plank position except your feet are elevated on a wall instead of the ground. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Levitation Crunches", new ArrayList<>(Arrays.asList(21, 22)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Very slow and controlled crunches. Do not go all the way to the ground, or use momentum from your upper body to perform the crunch. Focus on a controlled contraction. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "X-Man Crunches", new ArrayList<>(Arrays.asList(21, 22, 23, 24)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Lie on the ground with your arms and legs spread making an 'X'. Bring your arms and legs together in the middle as you crunch up. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Weighted X-Man Crunches", new ArrayList<>(Arrays.asList(21, 22, 23, 24)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(26))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(2)), "Lie on the ground with your arms and legs spread making an 'X'. Hold weight in both hands. Bring your arms and legs together in the middle as you crunch up. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "'21' Crunches", new ArrayList<>(Arrays.asList(21, 22, 23, 24)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Lie on the ground face up. Bring your hands and legs up at the same time meeting in the middle. Return to a flat position, then bring your hands and only one leg up meeting in the middle. Perform the motion on the other side. Repeat. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Legs Bent Crunches", new ArrayList<>(Arrays.asList(21, 22, 23, 24)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Bring your legs up with your knees bent at a 90 degree angle. Hold that position as you crunch. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "V-Ups", new ArrayList<>(Arrays.asList(21, 22, 23, 24)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Lying flat, bring your arms and legs up at the same time meeting in the middle. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Weighted V-Ups", new ArrayList<>(Arrays.asList(21, 22, 23, 24)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(26), A(25))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Lying flat, bring your arms and legs up at the same time meeting in the middle, holding weight in your hands. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Otis Ups", new ArrayList<>(Arrays.asList(21, 22, 23, 24)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(0, 1)), "Lying flat, bring your entire torso all the way up. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Weighted Otis Ups", new ArrayList<>(Arrays.asList(21, 22, 23, 24)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(26))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Lying flat, bring your entire torso all the way up, holding weight overhead in your hands. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Band/Cable Crunch", new ArrayList<>(Arrays.asList(21, 22, 23, 24)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(5, 6))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the cable or band fixed overhead, crunch down. Focus on a slow controlled contraction, and be sure to use your abs and not your weight to crunch down. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Leg Lifts", new ArrayList<>(Arrays.asList(21, 23)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Lying flat, raise your legs up as high as you can. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Flutter Kicks", new ArrayList<>(Arrays.asList(21, 23)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Lying flat, raise your legs up and kick back and forth. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Scissor Kicks", new ArrayList<>(Arrays.asList(21, 23)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Lying flat, raise your legs up and cross them side to side. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Scissor V-Ups", new ArrayList<>(Arrays.asList(21, 23)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Perform scissor kicks as you perform V-ups. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Jackknifes", new ArrayList<>(Arrays.asList(21, 24)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Lying flat, raise your legs and crunch up. Bring your arms forward and crunch up to one arm, alternating sides. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Canoe Crunches", new ArrayList<>(Arrays.asList(21, 24)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Lying flat, raise your legs and crunch up. Perform reps side to side bringing your knees up and 'row' with your upper half. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Dumbbell Handoffs", new ArrayList<>(Arrays.asList(21)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Start laying down with your knees bent and elevated off the ground. Hold the dumbbell in both hands and stretch your arms above your head, bring them over head as you crunch up and place it on your feet and then crunch back down before picking the weight back up. Rest 15 seconds between rounds.");

        // Oblique

        exerciseManager.addExercise(5, "Russian Twists", new ArrayList<>(Arrays.asList(21, 25)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(0, 1)), "With your knees bent and lifted up, crunch up and twist side to side. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Weighted Russian Twists", new ArrayList<>(Arrays.asList(21, 25)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(26))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "With your knees bent and lifted up, crunch up and twist side to side, holding weight in your hands. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Side Crunches", new ArrayList<>(Arrays.asList(21, 25)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 4, 45, 4,
                new ArrayList<>(Arrays.asList(0)), "Lying on your side, slightly rotated up, side crunch. Focus on contracting your obliques to crunch up. Rest 15 seconds between rounds. Switch sides each round.");
        exerciseManager.addExercise(5, "Weighted Side Crunches", new ArrayList<>(Arrays.asList(21, 25)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(26))), 4, 45, 4,
                new ArrayList<>(Arrays.asList(1, 2)), "Lying on your side, weights in hand, slightly rotated up, side crunch. Focus on contracting your obliques to crunch up. Rest 15 seconds between rounds. Switch sides each round.");
        exerciseManager.addExercise(5, "According Crunches", new ArrayList<>(Arrays.asList(21, 25)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 4, 45, 4,
                new ArrayList<>(Arrays.asList(1, 2)), "Perform side crunches but lift your knees up to meet your upper half as you crunch. Rest 15 seconds between rounds. Switch sides each round.");
        exerciseManager.addExercise(5, "Russian Twist V-Up", new ArrayList<>(Arrays.asList(21, 22, 23, 24, 25)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Perform a V-up and a russian twist to both sides when you reach the top. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "One Arm Weighted Otis Up", new ArrayList<>(Arrays.asList(21, 25)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(26))), 4, 45, 4,
                new ArrayList<>(Arrays.asList(1, 2)), "Lying flat, hold weight in one arm overhead. Bring your entire torso up completely. Rest 15 seconds between rounds. Switch sides each round.");
        exerciseManager.addExercise(5, "Starfish Crunches", new ArrayList<>(Arrays.asList(21, 23, 24, 25)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(2)), "Lying flat, stretch your arms and legs out, making an 'X'. Bring your arms and legs together twisting to one side. Alternate sides each rep. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Hanging Leg Twist", new ArrayList<>(Arrays.asList(21, 25)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(2)), "Hanging from the bar, keeping your legs straight, bring your legs up twisting to one side. Alternate sides each rep. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Hanging Knee Twist", new ArrayList<>(Arrays.asList(21, 25)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(1)), "Hanging from the bar, keeping your knees bent, bring your knees up twisting to one side. Alternate sides each rep. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Side Plank", new ArrayList<>(Arrays.asList(21, 25)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 4, 45, 4,
                new ArrayList<>(Arrays.asList(0)), "Lying on one side, elevate on one elbow and feet. Hold this position. Rest 15 seconds between rounds. Switch sides each round.");
        exerciseManager.addExercise(5, "Side Plank Raise", new ArrayList<>(Arrays.asList(21, 25)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 4, 45, 4,
                new ArrayList<>(Arrays.asList(1)), "Lying on one side, elevate on one elbow and feet. Lower your side down and then back up. Rest 15 seconds between rounds. Switch sides each round.");
        exerciseManager.addExercise(5, "Weighted Side Plank Raise", new ArrayList<>(Arrays.asList(21, 25)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(1), A(26))), 4, 45, 4,
                new ArrayList<>(Arrays.asList(2)), "Lying on one side, with weight held on the higher side, elevate on one elbow and feet. Lower your side down and then back up. Rest 15 seconds between rounds. Switch sides each round.");
        exerciseManager.addExercise(5, "Twisting T Plank Raise", new ArrayList<>(Arrays.asList(21, 25)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Staring from a plank position, rotate to one side into a side plank position and then raise up on this side. Twist back down to a plank position and then do the same on the other side. Repeat. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Black Widow Knee Slides", new ArrayList<>(Arrays.asList(21, 22, 23, 24, 25)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Starting from a plank position, bring one knee up to your chest and then slide the knee up as far as you can by bending your back. Bring the leg back down and repeat on the other side. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Drunken Mountain Climbers", new ArrayList<>(Arrays.asList(21, 22, 23, 25)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "In a plank position, hike your knees up to the other side of your chest alternating sides as if you are running in place. These are the same as mountain climbers except your knees should be hiking up at an angle. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Tuck Planks", new ArrayList<>(Arrays.asList(21, 22, 23, 25)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "In a plank position bring one knee up to your chest and then back down. Repeat on the other side. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Cross Tuck Planks", new ArrayList<>(Arrays.asList(21, 22, 23, 25)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "In a plank position bring one knee up to your opposite side chest and then back down. Repeat on the other side. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Wood Choppers", new ArrayList<>(Arrays.asList(21, 25)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(5), A(10), A(0))), 4, 45, 4,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the band or cable fixed at your side hold with both hands. Rotate your entire torso to the side fast in a chopping motion. Rest 15 seconds between rounds. Switch sides each round.");
        exerciseManager.addExercise(5, "Oak Tree Step Outs", new ArrayList<>(Arrays.asList(21, 25)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 4, 45, 4,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the band or cable fixed at your side hold with both hands. Step out holding your torso and arms rigid, step back in and repeat. Rest 15 seconds between rounds. Switch sides each round.");
        exerciseManager.addExercise(5, "Hanging Wind Shield Wipers", new ArrayList<>(Arrays.asList(21, 25)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Hanging from the bar, bring your legs or keens up to a 90 degree angle, rotate side to side holding this position. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "Circular Crunches", new ArrayList<>(Arrays.asList(21, 25)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 4, 45, 4,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Perform crunches in a circular motion. Rest 15 seconds between rounds. Perform both clockwise and counter clockwise.");

        // Serratus

        exerciseManager.addExercise(5, "Front Lever", new ArrayList<>(Arrays.asList(26)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(24))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(1, 2)), "Hanging from the bar, keep your scapula depressed and your torso rigid. Bring your legs and torso up to the bar and back down. Rest 15 seconds between rounds.");
        exerciseManager.addExercise(5, "One Side Serratus Crunches", new ArrayList<>(Arrays.asList(26)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(5, 6), A(0), A(10))), 4, 45, 4,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "With the cable or band fixed overhead, hold it in one hand. Crunch down on one side using your serratus. Rest 15 seconds between rounds. Switch sides each round.");
        exerciseManager.addExercise(5, "Dead Hang Scapula Pulls", new ArrayList<>(Arrays.asList(26)),
                false, false, false,
                new ArrayList<>(Arrays.asList(A(0), A(6))), 3, 45, 3,
                new ArrayList<>(Arrays.asList(0, 1, 2)), "Hanging from the bar, allow your scapula to relax, then depress your scapula, pulling your shoulder blades down and back. Rest 15 seconds between rounds.");



    }
}
