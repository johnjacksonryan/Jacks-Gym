package com.johnjacksonryan.exercise;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * The use case for exercise entities
 */

public class ExerciseManager {
    /**
     * The types of exercises that can be created
     */
    public enum ExerciseType {
        Strength,
        Hypertrophy,
        Endurance,
        Calisthenic,
        Conditioning,
        Ab,
    }
    public enum MuscleGroup {
        Quad,
        Glute,
        Hamstring,
        Chest,
        Back,
        Lat,
        Trap,
        Shoulder,
        LatDelt,
        AntDelt,
        PostDelt,
        Bicep,
        Tricep,
        Forearm,
        UpperBack,
        Rhomboid,
        RC,
        LowBack,
        Adductor,
        Abductor,
        Calves,
        Ab,
        UpperAb,
        LowerAb,
        MidAb,
        Oblique,
        Serratus,
        Heart,
    }
    private final ArrayList<String> equipment = new ArrayList<>(Arrays.asList("Full Gym", "Dumbbell", "Barbell",
            "Squat Rack", "Bench Press", "Resistance Band", "Pull Up Bar", "Dip Bar", "Bench", "Adjustable Bench",
            "Cable Machine", "Lat Pull Down", "Leg Press", "Leg Extension Machine", "Leg Curl Machine",
            "Leg Adduction Machine", "Leg Abduction Machine", "Calf Raise Machine", "Pec Fly Machine", "Chest Press",
            "Glute Kick Back Machine", "Ab Crunch Machine", "Glute/Ham Raise", "Preacher Bench", "Floor Space",
            "Kettlebell", "Weight Plates", "Medicine Ball", "Jumping Box", "Treadmill", "Bike", "Rowing Machine", "Elliptical",
            "Stair Mill", "Pool", "Jump Rope", "Punching Bag", "Track", "Jammers",
            "Sled", "Tennis Ball", "Landmine", "EZ Bar"));
    private int id = -1;
    private ArrayList<Exercise> exercises;
    private HashMap<Integer, HashMap<LocalDateTime, ArrayList<Integer>>> exerciseInfo;

    /**
     * The default constructor
     */
    public ExerciseManager() { this.exercises = new ArrayList<>(); }

    /**
     * Add a new exercise to the exercise manager
     */
    public int addExercise(int t, String name, ArrayList<Integer> muscleIndex,
                           boolean superSet, boolean dropSet, boolean specialRep,
                           ArrayList<ArrayList<Integer>> equipmentIndex, int duration, int reps, int sets,
                           ArrayList<Integer> difficulty, String notes) {
        this.id++;
        ExerciseType item = indexToExerciseType(t);
        ArrayList<MuscleGroup> muscle = new ArrayList<>();
        for (int j : muscleIndex) {
            muscle.add(indexToMuscleGroup(j));
        }
        ArrayList<ArrayList<String>> equip = new ArrayList<>();
        for (ArrayList<Integer> set :equipmentIndex){
            ArrayList<String> setEquip = new ArrayList<>();
            for(int k : set) {
                setEquip.add(indexToEquipment(k));
            }
            equip.add(setEquip);
        }
        Exercise exercise;

        switch(item) {
            case Strength:
                exercise = new StrengthExercise(this.id, name, muscle, superSet, dropSet, specialRep,
                        equip, reps, sets, duration, difficulty, notes);
                break;
            case Calisthenic:
                exercise = new CalisthenicExercise(this.id, name, muscle, superSet, dropSet, specialRep,
                        equip, reps, sets, duration, difficulty, notes);
                break;
            case Endurance:
                exercise = new EnduranceExercise(this.id, name, muscle, superSet, dropSet, specialRep,
                        equip, duration, difficulty, notes);
                break;
            case Conditioning:
                exercise = new ConditioningExercise(this.id, name, muscle, superSet, dropSet, specialRep,
                        equip, reps, sets, duration, difficulty, notes);
                break;
            case Ab:
                exercise = new AbExercise(this.id, name, muscle, superSet, dropSet, specialRep,
                        equip, reps, sets, duration, difficulty, notes);
                break;
            default:
                exercise = new HypertrophyExercise(this.id, name, muscle, superSet, dropSet, specialRep,
                        equip, reps, sets, duration, difficulty, notes);
        }
        exercises.add(exercise);
        return exercise.getId();
    }

    public ArrayList<ArrayList<Integer>> getUpdaterMultipliers() {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        for (Exercise exercise : exercises) {
            ArrayList<Integer> eList = new ArrayList<>();
            eList.add(exercise.getId());
            eList.add(exercise.getMultiplier());
            list.add(eList);
        }
        return list;
    }

    public void setMultiplier(int id, int multiplier) {
        getExercise(id).setMultiplier(multiplier);
    }

    public int getEquipmentIndexFromString(String e) {
        return equipment.indexOf(e);
    }

    /**
     * Removes the given exercise
     * @param id the id of the exercise
     * @return true iff this exercise is successfully removed.
     */
    public boolean removeExercise(int id) {
        if (this.getExercise(id) != null) {
            this.exercises.remove(getExercise(id));
            return true;
        }
        return false;
    }

    /**
     * Get the exercise with a given id
     *
     * @param id the id of the exercise
     * @return the exercise with the given id
     */
    public Exercise getExercise(int id) {
        for (Exercise exercise : this.exercises){
            if (exercise.getId() == id) {
                return exercise;
            }
        }
        return null;
    }

    /**
     * @return a list of exercises
     */
    public ArrayList<Exercise> getExercises() {
        return this.exercises;
    }

    /**
     * Get the name of the exercise
     *
     * @param id the id of the exercise
     * @return the name of the exercise corresponding to the id
     */
    public String getExerciseName(int id) {
        if (getExercise(id) !=  null) {
            return getExercise(id).getName();
        }
        return null;
    }

    /**
     * Get the main muscle of the exercise
     *
     * @param id the id of the exercise
     * @return the main muscle of the exercise corresponding to the id
     */
    public ArrayList<MuscleGroup> getExerciseMuscle(int id) {
        if (getExercise(id) !=  null) {
            return getExercise(id).getMuscle();
        }
        return null;
    }

    /**
     * Get the duration of the exercise
     *
     * @param id the id of the exercise
     * @return the duration of the exercise corresponding to the id
     */
    public int getExerciseDuration(int id) {
        if (getExercise(id) !=  null) {
            return getExercise(id).getDuration();
        }
        return 0;
    }

    /**
     * Get the notes of the exercise
     *
     * @param id the id of the exercise
     * @return the notes of the exercise corresponding to the id
     */
    public String getExerciseNotes(int id) {
        if (getExercise(id) != null) {
            return getExercise(id).getNotes();
        }
        return null;
    }

    /**
     * Get the type of the exercise
     *
     * @param id the id of the exercise
     * @return the type of the exercise corresponding to the id
     */
    public ExerciseType getExerciseType(int id) {
        if (getExercise(id) !=  null) {
            if (getExercise(id) instanceof StrengthExercise){
                return ExerciseType.Strength;
            }
            else if (getExercise(id) instanceof HypertrophyExercise){
                return ExerciseType.Hypertrophy;
            }
            else if (getExercise(id) instanceof EnduranceExercise){
                return ExerciseType.Endurance;
            }
            else if (getExercise(id) instanceof CalisthenicExercise){
                return ExerciseType.Calisthenic;
            }
            else if (getExercise(id) instanceof ConditioningExercise){
                return ExerciseType.Conditioning;
            }
            else if (getExercise(id) instanceof AbExercise){
                return ExerciseType.Ab;
            }
        }
        return null;
    }

    /**
     *
     * @param id the id of the exercise
     * @return this exercise as a string
     */
    public String getExerciseString(int id) {
        return getExercise(id).toString();
    }

    /**
     * @param exercises the list of exercises
     * @return the list of ids
     */
    public ArrayList<Integer> getExerciseIds(ArrayList<Exercise> exercises) {
        ArrayList<Integer> ids = new ArrayList<>();
        for (Exercise exercise : exercises) {
            ids.add(exercise.getId());
        }
        return ids;
    }

    public int getExerciseMultiplier(int id) {
        return getExercise(id).getMultiplier();
    }

    /**
     * @param type the type of exercise
     * @param muscle the muscle group of exercise
     * @return a map of id to exercise string
     */
    public HashMap<Integer, String> getExerciseTypeMuscle(int type, int muscle) {
        MuscleGroup mus = indexToMuscleGroup(muscle);
        ArrayList<Exercise> init = getAllTypeExercises().get(type);
        ArrayList<Exercise> exercises = getMuscleExercises(mus, init);
        return getExerciseIdString(getExerciseIds(exercises));
    }

    /**
     * Get a hash map of exercises and their corresponding ids
     *
     * @param ids a list of exercise ids
     * @return a hash map of exercises and their ids
     */
    public HashMap<Integer, String> getExerciseIdString(ArrayList<Integer> ids) {
        HashMap<Integer, String> exercises = new HashMap<>();
        for (int id: ids) {
            exercises.put(id, getExerciseString(id));
        }
        return exercises;
    }

    /**
     * Get a list of all exercises of a certain type
     * @return a list of all exercises of a certain type
     */
    public ArrayList<ArrayList<Exercise>> getAllTypeExercises() {
        ArrayList<ArrayList<Exercise>> list = arraySix();
        for (Exercise exercise : this.exercises) {
            if (exercise instanceof StrengthExercise) {
                list.get(0).add(exercise);
            }
            if (exercise instanceof HypertrophyExercise) {
                list.get(1).add(exercise);
            }
            if (exercise instanceof EnduranceExercise) {
                list.get(2).add(exercise);
            }
            if (exercise instanceof CalisthenicExercise) {
                list.get(3).add(exercise);
            }
            if (exercise instanceof ConditioningExercise) {
                list.get(4).add(exercise);
            }
            if (exercise instanceof AbExercise) {
                list.get(5).add(exercise);
            }
        }
        return list;
    }

    /**
     *
     * @param muscle the muscle group
     * @param exercises the exercises to chose from
     * @return the exercises in exercises that use muscle
     */
    public ArrayList<Exercise> getMuscleExercises(MuscleGroup muscle, ArrayList<Exercise> exercises){
        ArrayList<Exercise> list = new ArrayList<>();
        for (Exercise exercise : exercises) {
            if (exercise.getMuscle().contains(muscle)) {
                list.add(exercise);
            }
        }
        return list;
    }

    /**
     * @return A blank array list of six array lists
     */
    public ArrayList<ArrayList<Exercise>> arraySix() {
        ArrayList<ArrayList<Exercise>> list = new ArrayList<>();
        int i = 0;
        while (i<6) {
            list.add(new ArrayList<>());
            i++;
        }
        return list;
    }

    /**
     * @return A blank array list of two array lists
     */
    public ArrayList<ArrayList<Exercise>> arrayTwo() {
        ArrayList<ArrayList<Exercise>> list = new ArrayList<>();
        int i = 0;
        while (i < 2) {
            list.add(new ArrayList<>());
            i++;
        }
        return list;
    }

    /**
     *
     * @param list the list of exercises
     * @return a random exercise from the list of exercises
     */
    public Exercise getRandom(ArrayList<Exercise> list) {
        int rnd = new Random().nextInt(list.size());
        return list.get(rnd);
    }

    /**
     * @param list a list of integers
     * @return the sum of the list
     */
    public int sum(ArrayList<Integer> list) {
        int sum = 0;
        for (int i : list) {
            sum = sum + i;
        }
        return sum;
    }

    /**
     * @param exercise the exercise
     * @param muscles the muscle groups
     * @return true iff this exercise has one of the muscles
     */
    public boolean matchingMuscle(Exercise exercise, ArrayList<MuscleGroup> muscles) {
        for (MuscleGroup muscleGroup : muscles) {
            if (getExerciseMuscle(exercise.getId()).contains(muscleGroup)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param equipment the equipment list
     * @param exercise the exercise
     * @return true iff this exercise can be performed with this equipment
     */
    public boolean matchingEquipment(ArrayList<String> equipment, Exercise exercise) {
        for (ArrayList<String> e : exercise.getEquipment()) {
            if (equipment.containsAll(e)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Chooses an exercise
     * @param muscle the muscle group
     * @param exercises the list to choose from
     * @param superSet true iff super set
     * @param dropSet true iff drop set
     * @param specialRep true iff special rep
     * @param reg true iff regular
     * @return the chosen exercise
     */
    public Exercise chooseExercise(MuscleGroup muscle, ArrayList<Exercise> exercises, boolean superSet, boolean dropSet,
                               boolean specialRep, boolean reg, ArrayList<Integer> chosenIndex) {
        ArrayList<Exercise> chooseFrom = new ArrayList<>();
        if (superSet) {
            for (Exercise exercise : exercises) {
                if (exercise.getSuperSet() && exercise.getMuscle().contains(muscle) && !chosenIndex.contains(exercise.getId())){
                    for (int i = 0; i < exercise.getMultiplier(); i++) {
                        chooseFrom.add(exercise);
                    }
                }
            }
        }
        if (dropSet) {
            for (Exercise exercise : exercises) {
                if (exercise.getDropSet() && exercise.getMuscle().contains(muscle) && !chosenIndex.contains(exercise.getId())){
                    for (int i = 0; i < exercise.getMultiplier(); i++) {
                        chooseFrom.add(exercise);
                    }
                }
            }
        }
        if (specialRep) {
            for (Exercise exercise : exercises) {
                if (exercise.getSpecialRep() && exercise.getMuscle().contains(muscle) && !chosenIndex.contains(exercise.getId())){
                    for (int i = 0; i < exercise.getMultiplier(); i++) {
                        chooseFrom.add(exercise);
                    }
                }
            }
        }
        if (reg) {
            for (Exercise exercise : exercises) {
                if (!exercise.getSpecialRep() && !exercise.getSuperSet() && !exercise.getDropSet()
                        && exercise.getMuscle().contains(muscle) && !chosenIndex.contains(exercise.getId())){
                    for (int i = 0; i < exercise.getMultiplier(); i++) {
                        chooseFrom.add(exercise);
                    }
                }
            }
        }
        if (chooseFrom.isEmpty()) { return null; }
        return getRandom(chooseFrom);
    }

    /**
     * @param m the index of the muscle we are on
     * @param t the type of exercise we are choosing
     * @param numReg the number of regular
     * @param muscles the muscles we are choosing for
     * @param equipment the equipment available
     * @param difficulty the difficulty
     * @param numSuperSet the number of super sets
     * @param numDropSet the number of drop sets
     * @param numSpecialRep the number of special reps
     * @return a list of exercise ids
     */
    public ArrayList<Integer> chooseExercisesForType(int m, int t, int numReg, ArrayList<MuscleGroup> muscles,
                                                     ArrayList<String> equipment, int difficulty, int numSuperSet,
                                                     int numDropSet, int numSpecialRep) {
        if (t == 5) {
            ArrayList<MuscleGroup> musclesCopy = new ArrayList<>();
            for (MuscleGroup musc : muscles) {
                musclesCopy.add(musc);
            }
            for (MuscleGroup muscle : musclesCopy) {
                if (!muscle.equals(MuscleGroup.Ab) && !muscle.equals(MuscleGroup.MidAb) && !muscle.equals(MuscleGroup.UpperAb) && !muscle.equals(MuscleGroup.LowerAb) && !muscle.equals(MuscleGroup.Oblique)){
                    muscles.remove(muscle);
                }
            }
        }
        ArrayList<Exercise> exercises = new ArrayList<>();
        for (Exercise exercise : this.exercises) {
            int id = exercise.getId();
            if (getExerciseType(id) == indexToExerciseType(t) && matchingMuscle(exercise, muscles)
                    && matchingEquipment(equipment, exercise) && exercise.getDifficulty().contains(difficulty)) {
                exercises.add(exercise);
            }
        }
        ArrayList<Integer> chosenIndex = new ArrayList<>();
        // Choose super sets
        int j = 0;
        while (numSuperSet != 0 && j < muscles.size()) {
            MuscleGroup musc = muscles.get(m);
            Exercise selection = chooseExercise(musc, exercises, true, false, false, false, chosenIndex);
            if (selection != null) {
                numSuperSet--;
                chosenIndex.add(selection.getId());
            }
            else { j++; }
            m++;
            if (m == muscles.size()) { m = 0; }
        }
        j = 0;
        while (numDropSet != 0 && j < muscles.size()) {
            MuscleGroup musc = muscles.get(m);
            Exercise selection = chooseExercise(musc, exercises, false, true, false, false, chosenIndex);
            if (selection != null) {
                numDropSet--;
                chosenIndex.add(selection.getId());
            }
            else { j++; }
            m++;
            if (m == muscles.size()) { m = 0; }
        }
        j = 0;
        while (numSpecialRep != 0 && j < muscles.size()) {
            MuscleGroup musc = muscles.get(m);
            Exercise selection = chooseExercise(musc, exercises, false, false, true, false, chosenIndex);
            if (selection != null) {
                numSpecialRep--;
                chosenIndex.add(selection.getId());
            }
            else { j++; }
            m++;
            if (m == muscles.size()) { m = 0; }
        }
        j = 0;
        while (numReg != 0 && j < muscles.size()) {
            MuscleGroup musc = muscles.get(m);
            Exercise selection = chooseExercise(musc, exercises, false, false, false, true, chosenIndex);
            if (selection != null) {
                numReg--;
                chosenIndex.add(selection.getId());
            }
            else { j++; }
            m++;
            if (m == muscles.size()) { m = 0; }
        }
        chosenIndex.add(m);
        return chosenIndex;
    }

    /**
     * Selects exercises
     * @param typeNum the number of each type
     * @param muscles the muscles to be targeted
     * @param equipment the equipment available
     * @param difficulty the difficulty
     * @param numSuperSet the number of supersets
     * @param numDropSet the number of dropsets
     * @param numSpecialRep the number of special reps
     * @return the list of exercise ids
     */
    public ArrayList<Integer> selectExercises(ArrayList<Integer> typeNum, ArrayList<MuscleGroup> muscles,
                                              ArrayList<String> equipment, int difficulty, int numSuperSet,
                                              int numDropSet, int numSpecialRep){
        int num = sum(typeNum);
        int numStrength = typeNum.get(0);
        int numHypertrophy = typeNum.get(1);
        int numEndurance = typeNum.get(2);
        int numCalisthenic = typeNum.get(3);
        int numConditioning = typeNum.get(4);
        int numAb = typeNum.get(5);
        int m = 0;
        // Checking if the number of exercises is more than the combined number of super sets, drop sets, and special reps
        ArrayList<Integer> SSDSSR = getNumberHypCal(numHypertrophy, numCalisthenic, numSuperSet, numDropSet, numSpecialRep);
        ArrayList<Integer> chosenExercises = new ArrayList<>();
        // Choosing exercises
        // Strength
        ArrayList<Integer> strength = chooseExercisesForType(m, 0, numStrength, muscles, equipment, difficulty, 0, 0, 0);
        m = strength.remove(strength.size()-1);
        chosenExercises.addAll(strength);
        // Hypertrophy
        ArrayList<Integer> hypertrophy = chooseExercisesForType(m, 1, SSDSSR.get(0), muscles, equipment, difficulty, SSDSSR.get(1), SSDSSR.get(2), SSDSSR.get(3));
        m = hypertrophy.remove(hypertrophy.size()-1);
        chosenExercises.addAll(hypertrophy);
        // Endurance
        ArrayList<Integer> endurance = chooseExercisesForType(m, 2, numEndurance, muscles, equipment, difficulty, 0, 0, 0);
        m = endurance.remove(endurance.size()-1);
        chosenExercises.addAll(endurance);
        // Calisthenic
        ArrayList<Integer> calisthenic = chooseExercisesForType(m, 3, SSDSSR.get(4), muscles, equipment, difficulty, SSDSSR.get(5), SSDSSR.get(6), SSDSSR.get(7));
        m = calisthenic.remove(calisthenic.size()-1);
        chosenExercises.addAll(calisthenic);
        // Conditioning
        ArrayList<Integer> conditioning = chooseExercisesForType(m, 4, numConditioning, muscles, equipment, difficulty, 0, 0, 0);
        m = conditioning.remove(conditioning.size()-1);
        chosenExercises.addAll(conditioning);
        // Abs
        ArrayList<Integer> ab = chooseExercisesForType(m, 5, numAb, muscles, equipment, difficulty, 0, 0, 0);
        m = ab.remove(ab.size()-1);
        chosenExercises.addAll(ab);
        //if (chosenExercises.size() > num) {return null;}
        return chosenExercises;
    }

    public ArrayList<ArrayList<String>> getExerciseList(int type, ArrayList<Integer> musclesIndex) {
        ArrayList<ArrayList<String>> exerciseStrings = new ArrayList<>();
        ArrayList<Exercise> init = getAllTypeExercises().get(type);
        ArrayList<MuscleGroup> muscles = getMusclesFromIndex(musclesIndex);
        ArrayList<Exercise> keep = new ArrayList<>();
        for (Exercise exercise : init) {
            for (MuscleGroup muscleGroup : exercise.getMuscle()) {
                if (muscles.contains(muscleGroup) && !keep.contains(exercise)) {
                    keep.add(exercise);
                }
            }
        }
        for (Exercise exercise : keep) {
            exerciseStrings.add(exercise.getExerciseStringList());
        }
        return exerciseStrings;
    }

    public ArrayList<String> getExerciseStringList(int id) {
        return getExercise(id).getExerciseStringList();
    }

    public ArrayList<String> getExerciseStringList(Exercise exercise) {
        return exercise.getExerciseStringList();
    }

    /**
     * @param muscleIndex a list of muscle indexes
     * @return the list of muscles
     */
    public ArrayList<MuscleGroup> getMusclesFromIndex(ArrayList<Integer> muscleIndex) {
        ArrayList<MuscleGroup> muscles = new ArrayList<>();
        for (int i : muscleIndex) {
            muscles.add(indexToMuscleGroup(i));
        }
        return muscles;
    }

    public ArrayList<String> getEquipmentFromIndex(ArrayList<Integer> equipmentIndex) {
        ArrayList<String> equip = new ArrayList<>();
        for (int i : equipmentIndex) {
            equip.add(equipment.get(i));
        }
        return equip;
    }

    /**
     * @return an eight length list giving the number of SS, DS, SR, and regular exercises of hypertrophy and calisthenic
     */
    public ArrayList<Integer> getNumberHypCal(int numHypertrophy, int numCalisthenic, int numSuperSet, int numDropSet,
                                              int numSpecialRep) {
        ArrayList<Integer> SSDSSR = new ArrayList<>();
        int numRegHyp;
        int numSSHyp = 0;
        int numDSHyp = 0;
        int numSRHyp = 0;
        int numRegCal;
        int numSSCal = 0;
        int numDSCal = 0;
        int numSRCal = 0;
        int i = 0;
        // Drop set division
        numDSHyp = numDropSet;
        numHypertrophy = numHypertrophy - numDropSet;
        // Super set division
        while (numSuperSet > 0) {
            if (numHypertrophy == 0) { i = 1; }
            if (numCalisthenic == 0) { i = 0; }
            if (i == 0 && numHypertrophy != 0) {
                numSSHyp ++;
                numSuperSet --;
                numHypertrophy --;
                i = 1;
            }
            else if ( i == 1 && numCalisthenic != 0) {
                numSSCal ++;
                numSuperSet --;
                numCalisthenic--;
                i = 0;
            }
        }
        // Special rep division
        while (numSpecialRep > 0) {
            if (numHypertrophy == 0) { i = 1; }
            if (numCalisthenic == 0) { i = 0; }
            if (i == 0 && numHypertrophy != 0) {
                numSRHyp ++;
                numSpecialRep --;
                numHypertrophy --;
                i = 1;
            }
            else if ( i == 1 && numCalisthenic != 0) {
                numSRCal ++;
                numSpecialRep --;
                numCalisthenic--;
                i = 0;
            }
        }
        numRegCal = numCalisthenic;
        numRegHyp = numHypertrophy;
        SSDSSR.add(numRegHyp);
        SSDSSR.add(numSSHyp);
        SSDSSR.add(numDSHyp);
        SSDSSR.add(numSRHyp);
        SSDSSR.add(numRegCal);
        SSDSSR.add(numSSCal);
        SSDSSR.add(numDSCal);
        SSDSSR.add(numSRCal);
        return SSDSSR;
    }

    /**
     * @param exercises a list of exercises
     * @return the list of exercises exercise ids
     */
    public ArrayList<Integer> getIdList(ArrayList<Exercise> exercises) {
        ArrayList<Integer> ids = new ArrayList<>();
        for (Exercise exercise : exercises) {
            ids.add(exercise.getId());
        }
        return ids;
    }

    /**
     * @param ids the list of exercise ids
     * @return the list of corresponding exercises
     */
    public ArrayList<Exercise> getExercisesFromIds(ArrayList<Integer> ids) {
        ArrayList<Exercise> exercises = new ArrayList<>();
        for (int i : ids) {
            exercises.add(getExercise(i));
        }
        return exercises;
    }

    /**
     * @param exerciseIds the list of ids of exercises in the workout
     * @return the combined duration of all exercises
     */
    public int getWorkoutDuration(ArrayList<Integer> exerciseIds) {
        ArrayList<Exercise> exercises = getExercisesFromIds(exerciseIds);
        int duration = 0;
        for (Exercise exercise : exercises) {
            duration += exercise.getDuration();
        }
        return duration;
    }

    /**
     *
     * @param index the index corresponding to the exercise type
     * @return the exercise type corresponding to index
     */
    public ExerciseType indexToExerciseType(int index) {
        switch (index) {
            case 0:
                return ExerciseType.Strength;
            case 1:
                return ExerciseType.Hypertrophy;
            case 2:
                return ExerciseType.Endurance;
            case 3:
                return ExerciseType.Calisthenic;
            case 4:
                return ExerciseType.Conditioning;
            case 5:
                return ExerciseType.Ab;
        }
        return null;
    }

    /**
     *
     * @param index the index of the muscle group
     * @return the muscle group corresponding to the index
     */
    public MuscleGroup indexToMuscleGroup(int index) {
        switch(index) {
            case 0:
                return MuscleGroup.Quad;
            case 1:
                return MuscleGroup.Glute;
            case 2:
                return MuscleGroup.Hamstring;
            case 3:
                return MuscleGroup.Chest;
            case 4:
                return MuscleGroup.Back;
            case 5:
                return MuscleGroup.Lat;
            case 6:
                return MuscleGroup.Trap;
            case 7:
                return MuscleGroup.Shoulder;
            case 8:
                return MuscleGroup.LatDelt;
            case 9:
                return MuscleGroup.AntDelt;
            case 10:
                return MuscleGroup.PostDelt;
            case 11:
                return MuscleGroup.Bicep;
            case 12:
                return MuscleGroup.Tricep;
            case 13:
                return MuscleGroup.Forearm;
            case 14:
                return MuscleGroup.UpperBack;
            case 15:
                return MuscleGroup.Rhomboid;
            case 16:
                return MuscleGroup.RC;
            case 17:
                return MuscleGroup.LowBack;
            case 18:
                return MuscleGroup.Adductor;
            case 19:
                return MuscleGroup.Abductor;
            case 20:
                return MuscleGroup.Calves;
            case 21:
                return MuscleGroup.Ab;
            case 22:
                return MuscleGroup.UpperAb;
            case 23:
                return MuscleGroup.LowerAb;
            case 24:
                return MuscleGroup.MidAb;
            case 25:
                return MuscleGroup.Oblique;
            case 26:
                return MuscleGroup.Serratus;
            case 27:
                return MuscleGroup.Heart;
        }
        return null;
    }

    /**
     *
     * @param index the index of the equipment
     * @return the equipment corresponding to the index
     */
    public String indexToEquipment(int index) {
        return equipment.get(index);
    }

    /**
     * @param t type index
     * @param m muscle index
     * @return the list of exercise strings with type t and muscle m
     */
    public String getExercisesString(int t, int m) {
        MuscleGroup muscle = indexToMuscleGroup(m);
        ArrayList<Exercise> init = getAllTypeExercises().get(t);
        ArrayList<Exercise> exercises = getMuscleExercises(muscle, init);
        String s = "";
        for (Exercise exercise : exercises) {
            s = s + "\n" + exercise.toString() + "\n";
        }
        return s;
    }

    /**
     * @param exercises a list of exercises
     * @return the string corresponding to these exercises
     */
    public String getExercisesString(ArrayList<Exercise> exercises) {
        String s = "";
        for (Exercise exercise : exercises) {
            s = s + "\n" + exercise.toString() + "\n";
        }
        return s;
    }

    /**
     * @param exercises a list of exercises
     * @return the sorted list of exercises
     */
    public ArrayList<Exercise> sortExercises(ArrayList<Exercise> exercises) {
        if (exercises.size() == 0) {
            return null;
        }
        ArrayList<Exercise> sorted = new ArrayList<>();
        ArrayList<Exercise> legs = new ArrayList<>();
        ArrayList<Exercise> chest = new ArrayList<>();
        ArrayList<Exercise> back = new ArrayList<>();
        ArrayList<Exercise> arms = new ArrayList<>();
        ArrayList<Exercise> correctives = new ArrayList<>();
        ArrayList<Exercise> abs = new ArrayList<>();
        ArrayList<Exercise> conditioning = new ArrayList<>();
        for (Exercise exercise : exercises) {
            switch(exercise.getMuscle().get(0)){
                case Quad:
                case Glute:
                case Hamstring:
                    legs.add(exercise);
                    break;
                case Chest:
                    chest.add(exercise);
                    break;
                case Back:
                case Lat:
                case Trap:
                    back.add(exercise);
                    break;
                case Shoulder:
                case LatDelt:
                case AntDelt:
                case PostDelt:
                case Bicep:
                case Tricep:
                case Forearm:
                    arms.add(exercise);
                    break;
                case UpperBack:
                case Rhomboid:
                case RC:
                case LowBack:
                case Adductor:
                case Abductor:
                case Calves:
                    correctives.add(exercise);
                    break;
                case Ab:
                case UpperAb:
                case LowerAb:
                case MidAb:
                case Oblique:
                case Serratus:
                    abs.add(exercise);
                    break;
                case Heart:
                    conditioning.add(exercise);
                    break;
            }
        }
        sorted.addAll(legs);
        sorted.addAll(chest);
        sorted.addAll(back);
        sorted.addAll(arms);
        sorted.addAll(correctives);
        sorted.addAll(abs);
        sorted.addAll(conditioning);
        return sorted;
    }
}