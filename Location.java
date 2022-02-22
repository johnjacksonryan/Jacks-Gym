package com.johnjacksonryan.location;

import java.util.ArrayList;

/**
 * The class representing a location
 */
public class Location {
    private int id;
    private String name;
    ArrayList<String> equipment;

    /**
     * Default constructor
     * @param id the id of this location
     * @param name the name of this location
     * @param equipment the equipment at this location
     */
    public Location(int id, String name, ArrayList<String> equipment) {
        this.id = id;
        this.name = name;
        this.equipment = equipment;
    }

    public String getEquipmentAsString() {
        String equip = "";
        for (String e : equipment) {
            equip = equip + e + ", ";
        }
        return equip;
    }

    /**
     * @return the id of this location
     */
    public int getId() { return this.id; }

    /**
     * @return the name of this location
     */
    public String getName() { return this.name; }

    /**
     * @return the equipment at this location
     */
    public ArrayList<String> getEquipment() { return this.equipment; }

    /**
     * @return the string representation of this location
     */
    public String toString() {
        String e = "";
        int i = 0;
        for (String equip : equipment) {
            if (i == 5) {
                e = e + equip + "\n";
                i = 0;
            }
            else {
                e = e + equip + ", ";
            }
            i++;
        }
        return name + "\n" + " This location has: " + e;
    }
}
