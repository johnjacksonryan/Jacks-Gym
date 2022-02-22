package com.johnjacksonryan.gateway;

import android.content.Context;

import com.johnjacksonryan.exercise.ExerciseManager;
import com.johnjacksonryan.location.LocationManager;
import com.johnjacksonryan.workout.WorkoutManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * The class that updates the info specific to this device
 */
public class Updater {
    private final ExerciseManager exerciseManager;
    private final WorkoutManager workoutManager;
    private final LocationManager locationManager;
    private Context context;

    /**
     * Default constructor
     * @param exerciseManager this updaters exercise manager
     * @param workoutManager this updaters workout manager
     * @param locationManager this updater location manager
     */
    public Updater(ExerciseManager exerciseManager, WorkoutManager workoutManager, LocationManager locationManager, Context context) {
        this.exerciseManager = exerciseManager;
        this.workoutManager = workoutManager;
        this.locationManager = locationManager;
        this.context = context;
        File deleted = new File( context.getFilesDir(),"deleted.txt");
        File custom = new File(context.getFilesDir(),"custom.txt");
        File workouts = new File(context.getFilesDir(), "workouts.txt");
        File locations = new File(context.getFilesDir(),"locations.txt");
        File multipliers = new File(context.getFilesDir(), "multipliers.txt");
    }

    /**
     * Saves this workout to the users file
     * @param id the id of the workout to be saved
     */
    public void saveWorkout(int id) {
        ArrayList<Integer> exerciseIds = workoutManager.getWorkoutExerciseIds(id);
        String name = workoutManager.getName(id);
        int rating = workoutManager.getWorkoutRating(id);
        try {
            File workouts = new File(context.getFilesDir(), "workouts.txt");
            FileWriter writer = new FileWriter(workouts, true);
            String s = String.format("%s,", id);
            for (int e : exerciseIds) {
                s = s + String.format("%s,", e);
            }
            s = s + String.format("%s,", rating);
            s = s + name + ",";
            if (workoutManager.getComplete(id)) { s = s + "0"; }
            else { s = s + "1"; }
            writer.write(s + "\n");
            writer.flush();
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void saveSavedWorkout(int id) {
        deleteSavedWorkout(id);
        saveWorkout(id);
    }

    /**
     * Removes all workouts
     */
    public void clearSaved() {
        try {
            File workouts = new File(context.getFilesDir(), "workouts.txt");
            FileWriter writer = new FileWriter(workouts, false);
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Adds a location to this device
     * @param id the id of the location
     */
    public void addLocation(int id) {
        ArrayList<String> equipment = locationManager.getEquipment(id);
        String name = locationManager.getName(id);
        try {
            File locations = new File(context.getFilesDir(), "locations.txt");
            FileWriter writer = new FileWriter(locations, true);
            String s = String.format("%s,", id);
            for (String e : equipment) {
                s = s + e + ",";
            }
            s = s + name + ",";
            writer.write(s + "\n");
            writer.flush();
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }



    /**
     * Clears the location file
     */
    public void clearLocations() {
        try {
            File locations = new File(context.getFilesDir(),"locations.txt");
            FileWriter writer = new FileWriter(locations, false);
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Deletes a location from this device
     * @param id the id of the location
     */
    public void deleteLocation(int id) {
        try {
            File locations = new File(context.getFilesDir(),"locations.txt");
            if (locations.createNewFile()) {
            } else {
                ArrayList<String[]> locals = new ArrayList<>();
                Scanner reader = new Scanner(locations);
                while (reader.hasNextLine()) {
                    String l = reader.nextLine();
                    locals.add(l.split(","));
                }
                clearLocations();
                for (String[] parts : locals) {
                    if (Integer.parseInt(parts[0]) != id) {
                        addLocation(Integer.parseInt(parts[0]));
                    }
                }
            }


        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Loads this devices locations
     */
    public void loadLocations() {
        try {
            File locations = new File(context.getFilesDir(),"locations.txt");
            if (locations.createNewFile()) {
            } else {
                Scanner reader = new Scanner(locations);
                while (reader.hasNextLine()) {
                    String location = reader.nextLine();
                    String[] parts = location.split(",");
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[parts.length - 1];
                    ArrayList<String> equipment = new ArrayList<>();
                    for (int i = 1; i < parts.length - 1; i++ ) {
                        equipment.add(parts[i]);
                    }
                    locationManager.loadLocation(id, name, equipment);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void loadDefaultMultipliers() {
        try {
            File multipliers = new File(context.getFilesDir(), "multipliers.txt");
            if(multipliers.createNewFile()) {
            } else {
                Scanner reader = new Scanner(multipliers);
                while (reader.hasNextLine()) {
                    String multiplier = reader.nextLine();
                    String[] parts = multiplier.split(",");
                    int id = Integer.parseInt(parts[0]);
                    int m = Integer.parseInt(parts[1]);
                    exerciseManager.setMultiplier(id, m);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void clearMultipliers() {
        try {
            File multipliers = new File(context.getFilesDir(),"multipliers.txt");
            FileWriter writer = new FileWriter(multipliers, false);
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void addMultipliers() {
        try {
            clearMultipliers();
            File multipliers = new File(context.getFilesDir(), "multipliers.txt");
            FileWriter writer = new FileWriter(multipliers, true);
            for (ArrayList<Integer> pair : exerciseManager.getUpdaterMultipliers()) {
                String s = String.format("%s,%s", pair.get(0), pair.get(1));
                writer.write(s + "\n");
                writer.flush();
            }
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    /**
     * Completes a saved workout
     * @param id the id of the workout
     */
    public void completeSavedWorkout(int id) {
        deleteSavedWorkout(id);
        saveWorkout(id);
    }

    /**
     * Recovers a deleted exercise
     * @param id the id of the exercise to be recovered
     */
    public void deleteSavedWorkout(int id) {
        try {
            File deleted = new File(context.getFilesDir(),"workouts.txt");
            if (deleted.createNewFile()) {
            } else {
                ArrayList<String[]> deletes = new ArrayList<>();
                Scanner reader = new Scanner(deleted);
                while (reader.hasNextLine()) {
                    String delete = reader.nextLine();
                    deletes.add(delete.split(","));
                }
                clearSaved();
                for (String[] parts : deletes) {
                    if (Integer.parseInt(parts[0]) != id) {
                        saveWorkout(Integer.parseInt(parts[0]));
                    }
                }
            }


        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Updates the workout rating
     * @param id the id of the workout
     */
    public void setWorkoutRating(int id) {
        deleteSavedWorkout(id);
        saveWorkout(id);
    }

    /**
     * Loads all the workouts from this device
     */
    public void loadWorkouts() {
        try {
            File deleted = new File(context.getFilesDir(), "workouts.txt");
            if (deleted.createNewFile()) {
            } else {
                Scanner reader = new Scanner(deleted);
                while (reader.hasNextLine()) {
                    String workout = reader.nextLine();
                    String[] parts = workout.split(",");
                    int id = Integer.parseInt(parts[0]);
                    int complete = Integer.parseInt(parts[parts.length - 1]);
                    String name = parts[parts.length - 2];
                    int rating = Integer.parseInt(parts[parts.length - 3]);
                    ArrayList<Integer> exerciseIds = new ArrayList<>();
                    for (int i = 1; i < parts.length - 3; i++ ) {
                        exerciseIds.add(Integer.parseInt(parts[i]));
                    }
                    workoutManager.loadWorkout(id, exerciseIds, complete, name, rating);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Deletes the deleted exercises in this users device
     */
    public void loadDeleted() {
        try {
            File deleted = new File(context.getFilesDir(),"deleted.txt");
            if (deleted.createNewFile()) {
            } else {
                Scanner reader = new Scanner(deleted);
                while (reader.hasNextLine()) {
                    String delete = reader.nextLine();
                    String[] parts = delete.split(",");
                    if (exerciseManager.removeExercise(Integer.parseInt(parts[0]))) {
                    }
                    else {recover(Integer.parseInt(parts[0])); }
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Loads all the custom exercises stored on this device
     */
    public void loadCustom() {
        try {
            File custom = new File(context.getFilesDir(),"custom.txt");
            if (custom.createNewFile()) {
            } else {
                Scanner reader = new Scanner(custom);
                while (reader.hasNextLine()) {
                    String cust = reader.nextLine();
                    if (cust != "") {
                        createCustom(cust);
                    }
                }
            }
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    /**
     * Adds the custom exercise to the exercise manager
     * @param custom the string representing the custom
     */
    public void createCustom(String custom) {
        String[] parts = custom.split(",");
        int t = Integer.parseInt(parts[0]);
        String name = parts[1];
        ArrayList<Integer> muscles = new ArrayList<>();
        String[] musc = parts[2].split("[.]");
        for (String m : musc){
            muscles.add(Integer.parseInt(m));
        }
        boolean superSet = false;
        if (parts[3] == "0") { superSet = true; }
        boolean dropSet = false;
        if (parts[4] == "0") { dropSet = true; }
        boolean specialRep = false;
        if (parts[5] == "0") { specialRep = true; }
        ArrayList<ArrayList<Integer>> equipment = new ArrayList<>();
        String[] equip = parts[6].split("[/]");
        for (String eq : equip) {
            ArrayList<Integer> list = new ArrayList<>();
            String[] e = eq.split("[.]");
            for (String k : e) {
                list.add(Integer.parseInt(k));
            }
            equipment.add(list);
        }
        int duration = Integer.parseInt(parts[7]);
        int reps = Integer.parseInt(parts[8]);
        int sets = Integer.parseInt(parts[9]);
        ArrayList<Integer> difficulty = new ArrayList<>();
        String[] diff = parts[10].split("[.]");
        for (String d : diff) {
            difficulty.add(Integer.parseInt(d));
        }
        String notes = "";
        if (parts.length == 12) {
            notes = parts[11];
        }
        exerciseManager.addExercise(t, name, muscles, superSet, dropSet, specialRep, equipment, duration, reps, sets, difficulty, notes);
    }

    /**
     * Removes all deleted exercises
     */
    public void clearDeleted() {
        try {
            File deleted = new File(context.getFilesDir(), "deleted.txt");
            FileWriter writer = new FileWriter(deleted, false);
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Recovers a deleted exercise
     * @param id the id of the exercise to be recovered
     */
    public void recover(int id) {
        try {
            File deleted = new File(context.getFilesDir(),"deleted.txt");
            if (deleted.createNewFile()) {
            } else {
                ArrayList<String[]> deletes = new ArrayList<>();
                Scanner reader = new Scanner(deleted);
                while (reader.hasNextLine()) {
                    String delete = reader.nextLine();
                    deletes.add(delete.split(","));
                }
                clearDeleted();
                for (String[] parts : deletes) {
                    if (Integer.parseInt(parts[0]) != id) {
                        addDeleted(Integer.parseInt(parts[0]), parts[1]);
                    }
                }
            }


        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     *
     * @return the list of deleted ids
     */
    public HashMap<Integer, String> getDeleted() {
        try {
            HashMap<Integer, String> d = new HashMap<>();
            File deleted = new File(context.getFilesDir(),"deleted.txt");
            if (deleted.createNewFile()) {
            } else {
                Scanner reader = new Scanner(deleted);
                while (reader.hasNextLine()) {
                    String delete = reader.nextLine();
                    String[] parts = delete.split(",");
                    d.put(Integer.parseInt(parts[0]), parts[1]);
                }
            }
            return d;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adds a deleted exercise to the list of deleted exercises
     * @param id the exercise to be deleted
     * @throws IOException if this file is not found
     */
    public void addDeleted(int id, String name) {
        try {
            File deleted = new File(context.getFilesDir(), "deleted.txt");
            FileWriter writer = new FileWriter(deleted, true);
            writer.write(String.format("%s,", id) + name + "\n");
            writer.flush();
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Adds a custom exercise to this device
     */
    public void addCustom(int t, String name, ArrayList<Integer> muscles, boolean superSet, boolean dropSet,
                          boolean specialRep, ArrayList<ArrayList<Integer>> equipment, int duration, int reps, int sets,
                          ArrayList<Integer> difficulty, String notes) {
        try {
            File custom = new File(context.getFilesDir(), "custom.txt");
            FileWriter writer = new FileWriter(custom, true);
            String equip = "";
            String musc = "";
            String diff = "";
            for (int i : muscles) {
                musc = musc + String.format("%s.", i);
            }
            for (ArrayList<Integer> group : equipment) {
                String e = "";
                for (int j : group) {
                    e = e + String.format("%s.", j);
                }
                equip = equip + e + "/";
            }
            for (int k : difficulty) {
                diff = diff + String.format("%s.", k);
            }
            String ss = "1,";
            if (superSet) {
                ss = "0,";
            }
            String ds = "1,";
            if (dropSet) {
                ds = "0,";
            }
            String sr = "1,";
            if (specialRep) {
                sr = "0,";
            }
            String customS = String.format("%s,", t) + name + "," + musc + "," + ss + ds + sr + equip + "," +
                    String.format("%s,", duration) + String.format("%s,", reps) + String.format("%s,", sets) + diff + "," + notes + "\n";
            writer.write(customS);
            writer.flush();
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
