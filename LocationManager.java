package com.johnjacksonryan.location;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Use case class managing the location entity
 */
public class LocationManager {
    ArrayList<Location> locations;
    int id = -1;

    /**
     * Default constructor
     */
    public LocationManager() {
        this.locations = new ArrayList<>();
    }

    /**
     * @return a map of location id to name
     */
    public HashMap<Integer, String> getLocationMap() {
        HashMap<Integer, String> map = new HashMap<>();
        for (Location location : locations) {
            map.put(location.getId(), location.getName());
        }
        return map;
    }

    public ArrayList<ArrayList<String>> getLocationList() {
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        for (Location location: locations) {
            ArrayList<String> l = new ArrayList<>();
            l.add(location.getName());
            l.add(String.format("ID:  %s", location.getId()));
            l.add(location.getEquipmentAsString());
            l.add("No Info");
            list.add(l);
        }
        return list;
    }

    public ArrayList<String> getLocationStringList(int id) {
        String name = getName(id);
        ArrayList<String> equipment = getEquipment(id);
        ArrayList<String> list = new ArrayList<>();
        list.add(name);
        for (String e : equipment) {
            list.add(e);
        }
        return list;
    }

    /**
     * @return a map of location id to string
     */
    public HashMap<Integer, String> getLocationMapString() {
        HashMap<Integer, String> map = new HashMap<>();
        for (Location location : locations) {
            map.put(location.getId(), location.toString());
        }
        return map;
    }

    public void deleteLocation(int id) {
        this.locations.remove(getLocation(id));
    }

    public Location getLocation(int id) {
        for (Location location : locations) {
            if ( location.getId() == id) {
                return location;
            }
        }
        return null;
    }

    /**
     * Adds a new location
     * @param name the name of this location
     * @param equipment the equipment at this location
     * @return the id of this location
     */
    public int addLocation(String name, ArrayList<String> equipment) {
        id++;
        locations.add(new Location(id, name, equipment));
        return id;
    }

    /**
     * @param id the id of the location
     * @return this locations name
     */
    public String getName(int id) {
        for (Location location : locations) {
            if (location.getId() == id) {
                return location.getName();
            }
        }
        return null;
    }

    /**
     * @param id the id of the location
     * @return the equipment at this location
     */
    public ArrayList<String> getEquipment(int id) {
        for (Location location : locations) {
            if (location.getId() == id) {
                return location.getEquipment();
            }
        }
        return null;
    }

    /**
     * Loads an existing location
     * @param id the id of this location
     * @param name the name of this location
     * @param equipment the equipment at this location
     */
    public void loadLocation(int id, String name, ArrayList<String> equipment) {
        this.id = id - 1;
        addLocation(name, equipment);
    }

}
